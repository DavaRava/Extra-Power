package de.davarava.extrapower.block.entity.custom;

import de.davarava.extrapower.block.custom.BatteryBlock;
import de.davarava.extrapower.block.entity.ModBlockEntities;
import de.davarava.extrapower.block.entity.energy.ModEnergyStorage;
import de.davarava.extrapower.screen.custom.BatteryMenu;
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
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class BatteryBlockEntity extends BlockEntity implements MenuProvider {
    public final int capacity = getBatteryBlock().getCapacity();
    private final int maxTransfer = getBatteryBlock().getMaxTransfer();

    private final ModEnergyStorage ENERGY_STORAGE = createEnergyStorage();
    private ModEnergyStorage createEnergyStorage() {
        return new ModEnergyStorage(capacity, maxTransfer) {
            @Override
            public void onEnergyChanged() {
                setChanged();
                getLevel().sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        };
    }

    public BatteryBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.BATTERY_BE.get(), pos, blockState);
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return ENERGY_STORAGE;
    }

    private BatteryBlock getBatteryBlock(){
        return ((BatteryBlock) this.getBlockState().getBlock());
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(getBatteryBlock().getNameOfBlock() + "Battery");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new BatteryMenu(containerId, playerInventory, this);
    }

    // Synchronisation

    @Override
    protected void saveAdditional (CompoundTag tag, HolderLookup.Provider registries){
        tag.putInt("battery.energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional (CompoundTag tag, HolderLookup.Provider registries){
        super.loadAdditional(tag, registries);
        ENERGY_STORAGE.setEnergy(tag.getInt("battery.energy"));
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
