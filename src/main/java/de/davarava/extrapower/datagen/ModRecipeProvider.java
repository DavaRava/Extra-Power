package de.davarava.extrapower.datagen;

import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.BASIC_FLUID_TANK)
                .pattern("ABA").define('A', Items.IRON_INGOT).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .pattern("ABA").define('B', Tags.Items.GLASS_BLOCKS).unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
                .pattern("ABA").group("fluid_tanks").save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.BASIC_ENERGY_CELL)
                .pattern("ABA").define('A', Items.IRON_INGOT).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .pattern("BCB").define('B', Items.REDSTONE).unlockedBy("has_redstone", has(Items.REDSTONE))
                .pattern("ABA").define('C', Items.GOLD_INGOT).unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
                .group("energy_cells").save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.BASIC_SOLAR_PANEL)
                .pattern("BBB").define('A', Items.IRON_INGOT).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .pattern("ACA").define('B', ModItems.SOLAR_PANEL).unlockedBy("has_solar_panel", has(ModItems.SOLAR_PANEL))
                .define('C', Items.REPEATER).unlockedBy("has_repeater", has(Items.REPEATER))
                .group("solar_panels").save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOLAR_PANEL)
                .pattern("AAA").define('A', Tags.Items.GLASS_BLOCKS).unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
                .pattern("CCC").define('C', Items.DAYLIGHT_DETECTOR).unlockedBy("has_daylight_detector", has(Items.DAYLIGHT_DETECTOR))
                .pattern("AAA").save(pRecipeOutput);

    }
}
