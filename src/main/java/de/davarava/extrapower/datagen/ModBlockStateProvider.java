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
        frontSideBottomTopBlockWithItem(ModBlocks.CRUSHER.get());
    }

    private void horizontalBlockWithItem(Block block){
        horizontalBlock(block, cubeAll(block));
        simpleBlockItem(block, cubeAll(block));
    }

    private void topSideBottomBlockWithItem(Block block) {

        ResourceLocation name = ResourceLocation.fromNamespaceAndPath(ExtraPower.MODID, block.asItem().builtInRegistryHolder().key().location().getPath());

        ModelFile model = models().cubeBottomTop(
                name.getPath(),
                modLoc("block/" + name.getPath() + "_side"),
                modLoc("block/" + name.getPath() + "_bottom"),
                modLoc("block/" + name.getPath() + "_top")
        );

        simpleBlock(block, model);
        simpleBlockItem(block, model);
    }

    private void frontSideBottomTopBlockWithItem(Block block) {

        ResourceLocation name = ResourceLocation.fromNamespaceAndPath(ExtraPower.MODID, block.asItem().builtInRegistryHolder().key().location().getPath());

        ModelFile model = models().withExistingParent(name.getPath(), mcLoc("block/cube"))
                .texture("particle", modLoc("block/" + name.getPath() + "_side"))
                .texture("down", modLoc("block/" + name.getPath() + "_bottom"))
                .texture("up", modLoc("block/" + name.getPath() + "_top"))
                .texture("north", modLoc("block/" + name.getPath() + "_front"))
                .texture("south", modLoc("block/" + name.getPath() + "_side"))
                .texture("east", modLoc("block/" + name.getPath() + "_side"))
                .texture("west", modLoc("block/" + name.getPath() + "_side"));

        simpleBlock(block, model);
        simpleBlockItem(block, model);
    }
}
