package de.davarava.extrapower.block.entity.custom;

import de.davarava.extrapower.block.custom.EnergyCellBlock;
import de.davarava.extrapower.block.entity.ModBlockEntities;
import de.davarava.extrapower.block.entity.energy.ModEnergyStorage;
import de.davarava.extrapower.screen.custom.EnergyCellMenu;
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

public class EnergyCellBlockEntity extends BlockEntity implements MenuProvider {
    public final int CAPACITY = getEnergyCellBlock().getCapacity();
    private final int MAX_TRANSFER = getEnergyCellBlock().getMaxTransfer();

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

    public EnergyCellBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.ENERGY_CELL_BE.get(), pos, blockState);
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return ENERGY_STORAGE;
    }

    private EnergyCellBlock getEnergyCellBlock(){
        return ((EnergyCellBlock) this.getBlockState().getBlock());
    }

    @Override
    public Component getDisplayName() {
        return Component.literal(getEnergyCellBlock().getNameOfBlock());
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new EnergyCellMenu(containerId, playerInventory, this);
    }

    // Synchronisation

    @Override
    protected void saveAdditional (CompoundTag tag, HolderLookup.Provider registries){
        tag.putInt("energy_cell.energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional (CompoundTag tag, HolderLookup.Provider registries){
        super.loadAdditional(tag, registries);
        ENERGY_STORAGE.setEnergy(tag.getInt("energy_cell.energy"));
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
