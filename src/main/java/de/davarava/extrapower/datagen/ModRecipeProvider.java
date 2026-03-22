package de.davarava.extrapower.datagen;

import de.davarava.extrapower.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pRegistries) {
        super(pOutput, pRegistries);
    }

    @Override
    protected void buildRecipes(RecipeOutput pRecipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.COPPER_FLUID_TANK)
                .pattern("ABA").define('A', Items.COPPER_INGOT).group("fluid_tanks")
                .pattern("ABA").define('B', Items.GLASS).unlockedBy("has_glass", has(Items.GLASS))
                .pattern("ABA").unlockedBy("has_copper", has(Items.COPPER_INGOT)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.IRON_FLUID_TANK)
                .pattern("ABA").define('A', Items.IRON_INGOT).group("fluid_tanks").define('C', ModBlocks.COPPER_FLUID_TANK)
                .pattern("ACA").define('B', Items.GLASS).unlockedBy("has_glass", has(Items.GLASS))
                .pattern("ABA").unlockedBy("has_iron", has(Items.IRON_INGOT)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.GOLD_FLUID_TANK)
                .pattern("ABA").define('A', Items.GOLD_INGOT).group("fluid_tanks").define('C', ModBlocks.IRON_FLUID_TANK)
                .pattern("ACA").define('B', Items.GLASS).unlockedBy("has_glass", has(Items.GLASS))
                .pattern("ABA").unlockedBy("has_gold", has(Items.GOLD_INGOT)).save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.DIAMOND_FLUID_TANK)
                .pattern("ABA").define('A', Items.DIAMOND).group("fluid_tanks").define('C', ModBlocks.GOLD_FLUID_TANK)
                .pattern("ACA").define('B', Items.GLASS).unlockedBy("has_glass", has(Items.GLASS))
                .pattern("ABA").unlockedBy("has_diamond", has(Items.DIAMOND)).save(pRecipeOutput);
    }
}
