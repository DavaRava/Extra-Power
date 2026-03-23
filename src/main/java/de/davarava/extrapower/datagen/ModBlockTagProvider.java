package de.davarava.extrapower.datagen;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
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
                .add(ModBlocks.COPPER_FLUID_TANK.get())
                .add(ModBlocks.IRON_FLUID_TANK.get())
                .add(ModBlocks.GOLD_FLUID_TANK.get())
                .add(ModBlocks.DIAMOND_FLUID_TANK.get())
                .add(ModBlocks.TITANIUM_FLUID_TANK.get())
                .add(ModBlocks.COPPER_BATTERY.get())
                .add(ModBlocks.IRON_BATTERY.get())
                .add(ModBlocks.GOLD_BATTERY.get())
                .add(ModBlocks.DIAMOND_BATTERY.get())
                .add(ModBlocks.TITANIUM_BATTERY.get())
                .add(ModBlocks.TITANIUM_BLOCK.get());


        this.tag(BlockTags.NEEDS_STONE_TOOL)
                .add(ModBlocks.COPPER_FLUID_TANK.get())
                .add(ModBlocks.IRON_FLUID_TANK.get())
                .add(ModBlocks.COPPER_BATTERY.get())
                .add(ModBlocks.IRON_BATTERY.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.GOLD_FLUID_TANK.get())
                .add(ModBlocks.DIAMOND_FLUID_TANK.get())
                .add(ModBlocks.GOLD_BATTERY.get())
                .add(ModBlocks.DIAMOND_BATTERY.get());

        this.tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.TITANIUM_BLOCK.get())
                .add(ModBlocks.TITANIUM_FLUID_TANK.get())
                .add(ModBlocks.TITANIUM_BATTERY.get());
    }
}
