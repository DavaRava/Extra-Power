package de.davarava.extrapower.datagen;

import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        // Fluid Tank
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.BASIC_FLUID_TANK)
                .pattern("ABA").define('A', Items.IRON_INGOT).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .pattern("ABA").define('B', Tags.Items.GLASS_BLOCKS).unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
                .pattern("ABA").group("fluid_tanks").save(pRecipeOutput);

        // Energy Cell
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.BASIC_ENERGY_CELL)
                .pattern("ABA").define('A', Items.IRON_INGOT).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .pattern("BCB").define('B', Items.REDSTONE).unlockedBy("has_redstone", has(Items.REDSTONE))
                .pattern("ABA").define('C', Items.GOLD_INGOT).unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
                .group("energy_cells").save(pRecipeOutput);

        // Solar Panel Block
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.BASIC_SOLAR_PANEL)
                .pattern("BBB").define('A', Items.IRON_INGOT).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .pattern("ACA").define('B', ModItems.SOLAR_PANEL).unlockedBy("has_solar_panel", has(ModItems.SOLAR_PANEL))
                .define('C', Items.REPEATER).unlockedBy("has_repeater", has(Items.REPEATER))
                .group("solar_panels").save(pRecipeOutput);

        // Solar Panel Item
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOLAR_PANEL)
                .pattern("AAA").define('A', Tags.Items.GLASS_BLOCKS).unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
                .pattern("CCC").define('C', Items.DAYLIGHT_DETECTOR).unlockedBy("has_daylight_detector", has(Items.DAYLIGHT_DETECTOR))
                .pattern("AAA").save(pRecipeOutput);

        // Crusher
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.CRUSHER)
                .pattern("ABA").define('A', Items.IRON_INGOT).unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .pattern("CDC").define('B', Items.REDSTONE).unlockedBy("has_redstone", has(Items.REDSTONE))
                .pattern("ABA").define('C', Items.GOLD_INGOT).unlockedBy("has_gold_ingot", has(Items.GOLD_INGOT))
                .define('D', Blocks.STONECUTTER).unlockedBy("has_stonecutter", has(Blocks.STONECUTTER)).save(pRecipeOutput);
    }
}
