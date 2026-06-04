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
                .pattern("ABA")
                .pattern("ABA")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', Tags.Items.GLASS_BLOCKS)
                .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
                .group("fluid_tanks").save(pRecipeOutput);

        // Energy Cell
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.BASIC_ENERGY_CELL)
                .pattern("ABA")
                .pattern("CDC")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.REDSTONE)
                .define('C', Items.GOLD_INGOT)
                .define('D', ModBlocks.MACHINE_FRAME)
                .unlockedBy("has_machine_frame", has(ModBlocks.MACHINE_FRAME))
                .group("energy_cells").save(pRecipeOutput);

        // Solar Panel Block
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.BASIC_SOLAR_PANEL)
                .pattern("BBB")
                .pattern("ACA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModItems.SOLAR_PANEL)
                .define('C', Items.REPEATER)
                .unlockedBy("has_solar_panel", has(ModItems.SOLAR_PANEL))
                .group("solar_panels").save(pRecipeOutput);

        // Solar Panel Item
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.SOLAR_PANEL, 3)
                .pattern("AAA")
                .pattern("BBB")
                .pattern("AAA")
                .define('A', Tags.Items.GLASS_BLOCKS)
                .define('B', Items.DAYLIGHT_DETECTOR)
                .unlockedBy("has_glass", has(Tags.Items.GLASS_BLOCKS))
                .save(pRecipeOutput);

        // Machine Frame
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.MACHINE_FRAME)
                .pattern("ABA")
                .pattern("B B")
                .pattern("ABA")
                .define('A', Items.IRON_INGOT)
                .define('B', Items.COPPER_INGOT)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT))
                .unlockedBy("has_copper_ingot", has(Items.COPPER_INGOT))
                .save(pRecipeOutput);

        // Crusher
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.CRUSHER)
                .pattern("ADA")
                .pattern("CBC")
                .pattern("AEA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModBlocks.MACHINE_FRAME)
                .define('C', Items.GOLD_INGOT)
                .define('D', Blocks.STONECUTTER)
                .define('E', Items.REDSTONE)
                .unlockedBy("has_machine_frame", has(ModBlocks.MACHINE_FRAME))
                .save(pRecipeOutput);

        // Coal Generator
        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ModBlocks.BASIC_COAL_GENERATOR)
                .pattern("ACA")
                .pattern("DBD")
                .pattern("AEA")
                .define('A', Items.IRON_INGOT)
                .define('B', ModBlocks.MACHINE_FRAME)
                .define('C', Blocks.FURNACE)
                .define('D', Items.GOLD_INGOT)
                .define('E', Items.REDSTONE)
                .unlockedBy("has_machine_frame", has(ModBlocks.MACHINE_FRAME))
                .save(pRecipeOutput);
    }
}
