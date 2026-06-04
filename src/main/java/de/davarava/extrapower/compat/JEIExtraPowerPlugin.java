package de.davarava.extrapower.compat;

import de.davarava.extrapower.ExtraPower;
import de.davarava.extrapower.recipe.CrusherRecipe;
import de.davarava.extrapower.recipe.ModRecipes;
import de.davarava.extrapower.screen.custom.CrusherScreen;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;

import java.util.List;

@JeiPlugin
public class JEIExtraPowerPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(ExtraPower.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new CrusherRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<CrusherRecipe> crusherRecipes = recipeManager.getAllRecipesFor(ModRecipes.CRUSHER_TYPE.get()).stream().map(RecipeHolder::value).toList();
        registration.addRecipes(CrusherRecipeCategory.CRUSHER_RECIPE_TYPE, crusherRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(CrusherScreen.class, 63, 39, 14, 8, CrusherRecipeCategory.CRUSHER_RECIPE_TYPE);
    }
}
