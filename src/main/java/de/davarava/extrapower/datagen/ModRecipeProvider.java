package de.davarava.extrapower.datagen;

import de.davarava.extrapower.block.ModBlocks;
import de.davarava.extrapower.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;

import java.util.List;
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

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.TITANIUM_FLUID_TANK)
                .pattern("ABA").define('A', ModItems.TITANIUM_INGOT).group("fluid_tanks").define('C', ModBlocks.DIAMOND_FLUID_TANK)
                .pattern("ACA").define('B', Items.GLASS).unlockedBy("has_glass", has(Items.GLASS))
                .pattern("ABA").unlockedBy("has_titanium", has(ModItems.TITANIUM_INGOT)).save(pRecipeOutput);


        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.TITANIUM_BLOCK)
                .pattern("AAA").define('A', ModItems.TITANIUM_INGOT).group("titanium")
                .pattern("AAA").unlockedBy("has_titanium", has(ModItems.TITANIUM_INGOT))
                .pattern("AAA").save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.TITANIUM_INGOT)
                .pattern("AAA").define('A', ModItems.TITANIUM_NUGGET).group("titanium")
                .pattern("AAA").unlockedBy("has_titanium_nugget", has(ModItems.TITANIUM_NUGGET))
                .pattern("AAA").save(pRecipeOutput, "titanium_ingot_from_titanium_nuggets");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TITANIUM_NUGGET, 9)
                .requires(ModItems.TITANIUM_INGOT, 1).unlockedBy("has_titanium", has(ModItems.TITANIUM_INGOT))
                .group("titanium").save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TITANIUM_INGOT, 9)
                .requires(ModBlocks.TITANIUM_BLOCK, 1).unlockedBy("has_titanium", has(ModItems.TITANIUM_INGOT))
                .group("titanium").save(pRecipeOutput, "titanium_ingot_from_titanium_block");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.TITANIUM_INGOT)
                .requires(Items.DIAMOND).unlockedBy("has_titanium", has(ModItems.TITANIUM_INGOT))
                .requires(Items.IRON_INGOT).requires(Items.GOLD_INGOT)
                .group("titanium").save(pRecipeOutput);
    }
}
