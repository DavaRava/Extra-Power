package de.davarava.extrapower.datagen;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ExtraPower.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(ModBlocks.TITANIUM_BLOCK.get(), cubeAll(ModBlocks.TITANIUM_BLOCK.get()));

        simpleBlockWithItem(ModBlocks.COPPER_BATTERY.get(), cubeAll(ModBlocks.COPPER_BATTERY.get()));
        simpleBlockWithItem(ModBlocks.IRON_BATTERY.get(), cubeAll(ModBlocks.IRON_BATTERY.get()));
        simpleBlockWithItem(ModBlocks.GOLD_BATTERY.get(), cubeAll(ModBlocks.GOLD_BATTERY.get()));
        simpleBlockWithItem(ModBlocks.DIAMOND_BATTERY.get(), cubeAll(ModBlocks.DIAMOND_BATTERY.get()));
        simpleBlockWithItem(ModBlocks.TITANIUM_BATTERY.get(), cubeAll(ModBlocks.TITANIUM_BATTERY.get()));
    }
}
