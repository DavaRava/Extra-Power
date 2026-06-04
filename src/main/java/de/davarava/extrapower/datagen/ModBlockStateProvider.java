package de.davarava.extrapower.datagen;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, ExtraPower.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        horizontalBlockWithItem(ModBlocks.BASIC_ENERGY_CELL.get());
        simpleBlockWithItem(ModBlocks.MACHINE_FRAME.get(), cubeAll(ModBlocks.MACHINE_FRAME.get()));
        topSideBottomHorizontalBlockWithItem(ModBlocks.CRUSHER.get());
        generatorHorizontalBlockWithItem(ModBlocks.BASIC_COAL_GENERATOR.get());
    }

    private void horizontalBlockWithItem(Block block){
        horizontalBlock(block, cubeAll(block));
        simpleBlockItem(block, cubeAll(block));
    }

    private void topSideBottomHorizontalBlockWithItem(Block block) {

        ResourceLocation name = ResourceLocation.fromNamespaceAndPath(ExtraPower.MODID, block.asItem().builtInRegistryHolder().key().location().getPath());

        ModelFile model = models().withExistingParent(name.getPath(), mcLoc("block/cube"))
                .texture("particle", modLoc("block/" + name.getPath() + "_top"))
                .texture("down", modLoc("block/" + name.getPath() + "_bottom"))
                .texture("up", modLoc("block/" + name.getPath() + "_top"))
                .texture("north", modLoc("block/" + name.getPath() + "_side"))
                .texture("south", modLoc("block/" + name.getPath() + "_side"))
                .texture("east", modLoc("block/" + name.getPath() + "_side"))
                .texture("west", modLoc("block/" + name.getPath() + "_side"));

        horizontalBlock(block, model);
        simpleBlockItem(block, model);
    }

    private void generatorHorizontalBlockWithItem(Block block) {

        ResourceLocation name = ResourceLocation.fromNamespaceAndPath(ExtraPower.MODID, block.asItem().builtInRegistryHolder().key().location().getPath());

        ModelFile model = models().withExistingParent(name.getPath(), mcLoc("block/cube"))
                .texture("particle", modLoc("block/" + name.getPath() + "_front"))
                .texture("down", modLoc("block/" + name.getPath() + "_top"))
                .texture("up", modLoc("block/" + name.getPath() + "_top"))
                .texture("north", modLoc("block/" + name.getPath() + "_front"))
                .texture("south", modLoc("block/" + name.getPath() + "_top"))
                .texture("east", modLoc("block/" + name.getPath() + "_side"))
                .texture("west", modLoc("block/" + name.getPath() + "_side"));

        horizontalBlock(block, model);
        simpleBlockItem(block, model);
    }
}
