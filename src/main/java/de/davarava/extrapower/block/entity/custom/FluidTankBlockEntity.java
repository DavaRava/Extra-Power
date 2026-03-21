package de.davarava.extrapower.block.entity.custom;

import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.block.entity.ModBlockEntities;
import de.davarava.extrapower.screen.custom.FluidTankMenu;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class FluidTankBlockEntity extends BlockEntity implements MenuProvider {
    private final int capacity = getCapacity();

    public final ItemStackHandler itemHandler = new ItemStackHandler(2){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
        }

        @Override
        public int getSlotLimit(int slot) {
            return slot == 1 ? 1 : super.getSlotLimit(slot);
        }
    };
    private final FluidTank FLUID_TANK = createFluidTank();
    private FluidTank createFluidTank() {
        return new FluidTank(capacity){
            @Override
            protected void onContentsChanged() {
                setChanged();
                if(!level.isClientSide()) level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }

            @Override
            public boolean isFluidValid(FluidStack stack) {
                return true;
            }
        };
    }

    public FluidTankBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.FLUID_TANK_BE.get(), pos, blockState);
    }

    public FluidStack getFluid() {
        return FLUID_TANK.getFluid();
    }

    public IFluidHandler getTank(@Nullable Direction direction) {
        return FLUID_TANK;
    }

    public int getCapacity(){
        if(this.getBlockState().getBlock() == ModBlocks.COPPER_FLUID_TANK.get()){
            return 2000;
        } else if(this.getBlockState().getBlock() == ModBlocks.COPPER_FLUID_TANK.get()){
            return 4000;
        } else if(this.getBlockState().getBlock() == ModBlocks.COPPER_FLUID_TANK.get()){
            return 8000;
        } else if(this.getBlockState().getBlock() == ModBlocks.COPPER_FLUID_TANK.get()){
            return 16000;
        }
        return 0;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Fluid Tank");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new FluidTankMenu(containerId, playerInventory, this);
    }

    public void drops() {
        SimpleContainer inv = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inv.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inv);
    }

    public void tick(Level lvl, BlockPos blockPos, BlockState blockState) {
        if(hasFluidStackInFirstSlot()){
            transferFluidToTank();
        }

        if(hasFluidHandlerInSecondSlot()){
            transferFluidFromTankToHandler();
        }

        pushFluidToAboveNeighbor();
    }

    private void pushFluidToAboveNeighbor() { //push fluid from tank into the block above for example into a generator that uses a specific fluid
        FluidUtil.getFluidHandler(level, worldPosition.above(), null).ifPresent(iFluidHandler -> {
            FluidUtil.tryFluidTransfer(iFluidHandler, this.FLUID_TANK, Integer.MAX_VALUE, true);
        });
    }

    private void transferFluidFromTankToHandler() { //transfer fluid from tank into the fluid handler item
        FluidActionResult result = FluidUtil.tryFillContainer(itemHandler.getStackInSlot(1), this.FLUID_TANK, Integer.MAX_VALUE, null, true);
        if(result.result != ItemStack.EMPTY){
            itemHandler.setStackInSlot(1, result.result);
        }
    }

    private boolean hasFluidHandlerInSecondSlot() { //if second slot has a fluid handler item for example an empty bucket
        return !itemHandler.getStackInSlot(1).isEmpty()
                && itemHandler.getStackInSlot(1).getCapability(Capabilities.FluidHandler.ITEM, null) != null
                && (itemHandler.getStackInSlot(1).getCapability(Capabilities.FluidHandler.ITEM, null).getFluidInTank(0).isEmpty() ||
                FluidUtil.tryFluidTransfer(itemHandler.getStackInSlot(1).getCapability(Capabilities.FluidHandler.ITEM, null),
                        FLUID_TANK, Integer.MAX_VALUE, false) != FluidStack.EMPTY);
    }

    private void transferFluidToTank() { //transfer fluid from fluid stack item into the tank
        FluidActionResult result = FluidUtil.tryEmptyContainer(itemHandler.getStackInSlot(0), this.FLUID_TANK, Integer.MAX_VALUE, null, true);
        if(result.result != ItemStack.EMPTY){
            itemHandler.setStackInSlot(0, result.result);
        }
    }

    private boolean hasFluidStackInFirstSlot() { //if first slot has a fluid stack item for example a water bucket
        return !itemHandler.getStackInSlot(0).isEmpty()
                && itemHandler.getStackInSlot(0).getCapability(Capabilities.FluidHandler.ITEM, null) != null
                && !itemHandler.getStackInSlot(0).getCapability(Capabilities.FluidHandler.ITEM, null).getFluidInTank(0).isEmpty();
    }

    // Synchronisation

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        tag.put("fluid_tank.inventory", itemHandler.serializeNBT(registries));
        tag = FLUID_TANK.writeToNBT(registries, tag);

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("fluid_tank.inventory"));
        FLUID_TANK.readFromNBT(registries, tag);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt, HolderLookup.Provider lookupProvider) {
        super.onDataPacket(net, pkt, lookupProvider);
    }
}
