package de.davarava.extrapower.block.entity;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.block.entity.custom.EnergyCellBlockEntity;
import de.davarava.extrapower.block.entity.custom.FluidTankBlockEntity;
import de.davarava.extrapower.block.entity.custom.SolarPanelBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ExtraPower.MODID);

    public static final Supplier<BlockEntityType<FluidTankBlockEntity>> FLUID_TANK_BE =
            BLOCK_ENTITIES.register("fluid_tank_be", () -> BlockEntityType.Builder.of(
                    FluidTankBlockEntity::new, ModBlocks.BASIC_FLUID_TANK.get()).build(null));

    public static final Supplier<BlockEntityType<EnergyCellBlockEntity>> ENERGY_CELL_BE =
            BLOCK_ENTITIES.register("energy_cell_be", () -> BlockEntityType.Builder.of(
                    EnergyCellBlockEntity::new, ModBlocks.BASIC_ENERGY_CELL.get()).build(null));

    public static final Supplier<BlockEntityType<SolarPanelBlockEntity>> SOLAR_PANE_BE =
            BLOCK_ENTITIES.register("solar_panel_be", () -> BlockEntityType.Builder.of(
                    SolarPanelBlockEntity::new, ModBlocks.BASIC_SOLAR_PANEL.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
