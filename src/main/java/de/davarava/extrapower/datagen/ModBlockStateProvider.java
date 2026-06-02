package de.davarava.extrapower.datagen;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ExtraPower.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlockWithItem(ModBlocks.TITANIUM_BLOCK.get(), cubeAll(ModBlocks.TITANIUM_BLOCK.get()));

        horizontalBlockWithItem(ModBlocks.COPPER_BATTERY.get());
        horizontalBlockWithItem(ModBlocks.IRON_BATTERY.get());
        horizontalBlockWithItem(ModBlocks.GOLD_BATTERY.get());
        horizontalBlockWithItem(ModBlocks.DIAMOND_BATTERY.get());
        horizontalBlockWithItem(ModBlocks.TITANIUM_BATTERY.get());
    }

    private void horizontalBlockWithItem(Block block){
        horizontalBlock(block, cubeAll(block));
        simpleBlockItem(block, cubeAll(block));
    }
}
