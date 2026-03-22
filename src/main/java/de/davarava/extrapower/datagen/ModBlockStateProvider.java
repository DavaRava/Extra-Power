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
        simpleBlockWithItem(ModBlocks.NICKEL_ORE.get(), cubeAll(ModBlocks.NICKEL_ORE.get()));
        simpleBlockWithItem(ModBlocks.DEEPSLATE_NICKEL_ORE.get(), cubeAll(ModBlocks.DEEPSLATE_NICKEL_ORE.get()));
        simpleBlockWithItem(ModBlocks.NICKEL_BLOCK.get(), cubeAll(ModBlocks.NICKEL_BLOCK.get()));
        simpleBlockWithItem(ModBlocks.RAW_NICKEL_BLOCK.get(), cubeAll(ModBlocks.RAW_NICKEL_BLOCK.get()));
    }
}
