package de.davarava.extrapower.datagen;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ExtraPower.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.BASIC_FLUID_TANK.get())
                .add(ModBlocks.BASIC_ENERGY_CELL.get())
                .add(ModBlocks.BASIC_SOLAR_PANEL.get())
                .add(ModBlocks.CRUSHER.get());


        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.BASIC_FLUID_TANK.get())
                .add(ModBlocks.BASIC_ENERGY_CELL.get())
                .add(ModBlocks.BASIC_SOLAR_PANEL.get())
                .add(ModBlocks.CRUSHER.get());
    }
}
