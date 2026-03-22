package de.davarava.extrapower.block;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.block.custom.FluidTankBlock;
import de.davarava.extrapower.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ExtraPower.MODID);

    public static final DeferredBlock<Block> COPPER_FLUID_TANK = registerBlock("copper_fluid_tank",
            () -> new FluidTankBlock(BlockBehaviour.Properties.of().strength(1.5f)
                    .sound(SoundType.COPPER).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredBlock<Block> IRON_FLUID_TANK = registerBlock("iron_fluid_tank",
            () -> new FluidTankBlock(BlockBehaviour.Properties.of().strength(1.5f)
                    .sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredBlock<Block> GOLD_FLUID_TANK = registerBlock("gold_fluid_tank",
            () -> new FluidTankBlock(BlockBehaviour.Properties.of().strength(1.5f)
                    .sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredBlock<Block> DIAMOND_FLUID_TANK = registerBlock("diamond_fluid_tank",
            () -> new FluidTankBlock(BlockBehaviour.Properties.of().strength(1.5f)
                    .sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));
    public static final DeferredBlock<Block> TITANIUM_FLUID_TANK = registerBlock("titanium_fluid_tank",
            () -> new FluidTankBlock(BlockBehaviour.Properties.of().strength(2f)
                    .sound(SoundType.METAL).requiresCorrectToolForDrops().noOcclusion()));

    public static final DeferredBlock<Block> TITANIUM_BLOCK = registerBlock("titanium_block",
            () -> new Block(BlockBehaviour.Properties.of().strength(7f, 8f)
                    .sound(SoundType.METAL).requiresCorrectToolForDrops()));

    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }
    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }
    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
