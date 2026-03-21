package de.davarava.extrapower.block.entity;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.block.entity.custom.FluidTankBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ExtraPower.MODID);

    public static final Supplier<BlockEntityType<FluidTankBlockEntity>> FLUID_TANK_BE =
            BLOCK_ENTITIES.register("fluid_tank_be", () -> BlockEntityType.Builder.of(
                    FluidTankBlockEntity::new, ModBlocks.COPPER_FLUID_TANK.get(), ModBlocks.IRON_FLUID_TANK.get(),
                    ModBlocks.GOLD_FLUID_TANK.get(), ModBlocks.DIAMOND_FLUID_TANK.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
