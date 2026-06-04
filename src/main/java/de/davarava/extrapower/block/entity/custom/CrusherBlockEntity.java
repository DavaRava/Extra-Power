package de.davarava.extrapower.block.entity.custom;

import de.davarava.extrapower.block.custom.CrusherBlock;
import de.davarava.extrapower.block.entity.ModBlockEntities;
import de.davarava.extrapower.block.entity.energy.ModEnergyStorage;
import de.davarava.extrapower.screen.custom.CrusherMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class CrusherBlockEntity extends BlockEntity implements MenuProvider {
    public final int CAPACITY = getCrusherBlock().getCapacity();
    private final int MAX_TRANSFER = getCrusherBlock().getMaxTransfer();
    private final int USE_RATE = getCrusherBlock().getUseRate();

    public final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (!level.isClientSide()) level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }
    };
    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();
    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(CAPACITY, MAX_TRANSFER) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int SECONDARY_OUTPUT_SLOT = 2;

    private final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;
    private final int DEFAULT_MAX_PROGRESS = 200;

    public CrusherBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.CRUSHER_BE.get(), pos, blockState);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index){
                    case 0 -> CrusherBlockEntity.this.progress;
                    case 1 -> CrusherBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index){
                    case 0: CrusherBlockEntity.this.progress = value;
                    case 1: CrusherBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return ENERGY_STORAGE;
    }
    private CrusherBlock getCrusherBlock(){
        return ((CrusherBlock) this.getBlockState().getBlock());
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(getCrusherBlock().getNameOfBlock());
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new CrusherMenu(containerId, playerInventory, this, this.data);
    }

    public void drops () {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if(hasRecipe() && isOutputSlotEmptyOrReceivable()){
            increaseCraftingProgress();
            level.setBlockAndUpdate(pos, state.setValue(CrusherBlock.LIT, true));
            setChanged(level, pos, state);

            if(hasCraftingFinished()){
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
            level.setBlockAndUpdate(pos, state.setValue(CrusherBlock.LIT, false));
        }
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = DEFAULT_MAX_PROGRESS;
    }

    private void craftItem() {
        ItemStack output = new ItemStack(Items.GRAVEL);

        itemHandler.extractItem(INPUT_SLOT, 1, false);
        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private boolean hasCraftingFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() < this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean hasRecipe() {
        ItemStack input = new ItemStack(Items.COBBLESTONE);
        ItemStack output = new ItemStack(Items.GRAVEL);

        return canInsertAmountIntoOutputSlot(output.getCount()) && canInsertItemIntoOutputSlot(output) &&
                this.itemHandler.getStackInSlot(INPUT_SLOT).getItem() == input.getItem();
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        int currentCount = itemHandler.getStackInSlot(OUTPUT_SLOT).getCount();

        return maxCount >= currentCount + count;
    }

    // Synchronisation

    @Override
    protected void saveAdditional (CompoundTag tag, HolderLookup.Provider registries){
        tag.put("crusher.inventory", itemHandler.serializeNBT(registries));
        tag.putInt("crusher.energy", ENERGY_STORAGE.getEnergyStored());
        tag.putInt("crusher.progress", progress);
        tag.putInt("crusher.max_progress", maxProgress);
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional (CompoundTag tag, HolderLookup.Provider registries){
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("crusher.inventory"));
        ENERGY_STORAGE.setEnergy(tag.getInt("crusher.energy"));
        progress = tag.getInt("crusher.progress");
        maxProgress = tag.getInt("crusher.max_progress");
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket () {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag (HolderLookup.Provider registries){
        return saveWithoutMetadata(registries);
    }

    @Override
    public void onDataPacket (Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider){
        super.onDataPacket(net, pkt, lookupProvider);
    }
}
