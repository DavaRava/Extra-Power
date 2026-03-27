package de.davarava.extrapower.event;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.block.entity.ModBlockEntities;
import de.davarava.extrapower.block.entity.custom.BatteryBlockEntity;
import de.davarava.extrapower.block.entity.custom.FluidTankBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

@EventBusSubscriber(modid = ExtraPower.MODID)
public class ModEvents {
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event){
        event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, ModBlockEntities.FLUID_TANK_BE.get(), FluidTankBlockEntity::getTank);
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.BATTERY_BE.get(), BatteryBlockEntity::getEnergyStorage);
    }
}
