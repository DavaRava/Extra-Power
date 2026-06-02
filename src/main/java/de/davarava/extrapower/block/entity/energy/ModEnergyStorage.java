package de.davarava.extrapower.block.entity.energy;

import net.neoforged.neoforge.energy.EnergyStorage;

public abstract class ModEnergyStorage extends EnergyStorage {
    public ModEnergyStorage(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        int extractedEnergy = super.extractEnergy(maxExtract, simulate);
        if (extractedEnergy != 0){
            onEnergyChanged();
        }

        return extractedEnergy;
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        int receivedEnergy = super.receiveEnergy(toReceive, simulate);
        if(receivedEnergy != 0){
            onEnergyChanged();
        }

        return receivedEnergy;
    }

    public int setEnergy(int energy){
        this.energy = energy;
        return energy;
    }

    public abstract void onEnergyChanged();
}
