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


        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.NICKEL_BLOCK)
                .pattern("AAA").define('A', ModItems.NICKEL_INGOT).group("nickel")
                .pattern("AAA").unlockedBy("has_nickel", has(ModItems.NICKEL_INGOT))
                .pattern("AAA").save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.RAW_NICKEL_BLOCK)
                .pattern("AAA").define('A', ModItems.RAW_NICKEL).group("nickel")
                .pattern("AAA").unlockedBy("has_raw_nickel", has(ModItems.RAW_NICKEL))
                .pattern("AAA").save(pRecipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.NICKEL_INGOT)
                .pattern("AAA").define('A', ModItems.NICKEL_NUGGET).group("nickel")
                .pattern("AAA").unlockedBy("has_nickel_nugget", has(ModItems.NICKEL_NUGGET))
                .pattern("AAA").save(pRecipeOutput, "nickel_ingot_from_nickel_nuggets");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NICKEL_NUGGET, 9)
                .requires(ModItems.NICKEL_INGOT, 1).unlockedBy("has_nickel", has(ModItems.NICKEL_INGOT))
                .group("nickel").save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.NICKEL_INGOT, 9)
                .requires(ModBlocks.NICKEL_BLOCK, 1).unlockedBy("has_nickel", has(ModItems.NICKEL_INGOT))
                .group("nickel").save(pRecipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.RAW_NICKEL, 9)
                .requires(ModBlocks.RAW_NICKEL_BLOCK, 1).unlockedBy("has_raw_nickel", has(ModItems.RAW_NICKEL))
                .group("nickel").save(pRecipeOutput);

        List<ItemLike> NICKEL_ORE_SMELTING = List.of(ModItems.RAW_NICKEL, ModBlocks.NICKEL_ORE, ModBlocks.DEEPSLATE_NICKEL_ORE);
        oreSmelting(pRecipeOutput, NICKEL_ORE_SMELTING, RecipeCategory.MISC, ModItems.NICKEL_INGOT, 0.7f, 200, "nickel");
        oreBlasting(pRecipeOutput, NICKEL_ORE_SMELTING, RecipeCategory.MISC, ModItems.NICKEL_INGOT, 0.7f, 100, "nickel");
    }
}
