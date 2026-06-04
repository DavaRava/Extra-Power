package de.davarava.extrapower.block.entity.custom;

import de.davarava.extrapower.block.custom.SolarPanelBlock;
import de.davarava.extrapower.block.entity.ModBlockEntities;
import de.davarava.extrapower.block.entity.energy.ModEnergyStorage;
import de.davarava.extrapower.block.entity.energy.ModEnergyUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class SolarPanelBlockEntity extends BlockEntity {
    public final int capacity = getSolarPanelBlock().getCapacity();
    private final int maxTransfer = getSolarPanelBlock().getMaxTransfer();
    private final int productionRate = getSolarPanelBlock().getProductionRate();

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

    public SolarPanelBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.SOLAR_PANE_BE.get(), pos, blockState);
    }

    public IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        return ENERGY_STORAGE;
    }

    private SolarPanelBlock getSolarPanelBlock(){
        return ((SolarPanelBlock) this.getBlockState().getBlock());
    }

    public void tick (Level lvl, BlockPos blockPos, BlockState blockState){
        if(!blockState.getValue(SolarPanelBlock.ENABLED)) return;

        if(lvl.canSeeSky(blockPos) && lvl.isDay()){
            generateEnergy(lvl, blockPos);
        }

        pushEnergyToBelowNeighbor(lvl, blockPos);
    }

    private void generateEnergy(Level lvl, BlockPos pos) {
        int toProduce = productionRate;

        if (lvl.isRainingAt(pos.above())){
            toProduce /= 2;
        }

        ENERGY_STORAGE.receiveEnergy(toProduce, false);
    }

    private void pushEnergyToBelowNeighbor(Level level, BlockPos pos) {
        if (ModEnergyUtil.doesBlockHaveEnergyStorage(pos.below(), level)) {
            ModEnergyUtil.move(pos, pos.below(), getSolarPanelBlock().getMaxTransfer(), level);
        }
    }

    // Synchronisation

    @Override
    protected void saveAdditional (CompoundTag tag, HolderLookup.Provider registries){
        tag.putInt("solar_panel.energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(tag, registries);
    }

    @Override
    protected void loadAdditional (CompoundTag tag, HolderLookup.Provider registries){
        super.loadAdditional(tag, registries);
        ENERGY_STORAGE.setEnergy(tag.getInt("solar_panel.energy"));
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
