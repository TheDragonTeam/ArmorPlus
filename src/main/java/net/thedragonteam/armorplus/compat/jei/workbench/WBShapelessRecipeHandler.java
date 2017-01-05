/*
 * Copyright (c) TheDragonTeam 2016-2017.
 */

package net.thedragonteam.armorplus.compat.jei.workbench;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.ErrorUtil;
import mezz.jei.util.Log;
import net.minecraft.item.ItemStack;
import net.thedragonteam.armorplus.api.Constants;
import net.thedragonteam.armorplus.api.crafting.workbench.ShapelessRecipes;

public class WBShapelessRecipeHandler implements IRecipeHandler<ShapelessRecipes> {
    @Override
    public Class<ShapelessRecipes> getRecipeClass() {
        return ShapelessRecipes.class;
    }

    @Override
    public String getRecipeCategoryUid(ShapelessRecipes recipe) {
        return Constants.Compat.JEI_CATEGORY_WORKBENCH;
    }

    @Override
    public IRecipeWrapper getRecipeWrapper(ShapelessRecipes recipe) {
        return new WBShapelessRecipeWrapper(recipe);
    }

    @Override
    public boolean isRecipeValid(ShapelessRecipes recipe) {
        if (recipe.getRecipeOutput() == null) {
            String recipeInfo = ErrorUtil.getInfoFromRecipe(recipe, this);
            Log.error("Recipe has no output. {}", recipeInfo);
            return false;
        }
        int inputCount = 0;
        for (Object input : recipe.input) {
            if (input instanceof ItemStack) {
                inputCount++;
            } else {
                String recipeInfo = ErrorUtil.getInfoFromRecipe(recipe, this);
                Log.error("Recipe has an input that is not an ItemStack. {}", recipeInfo);
                return false;
            }
        }
        if (inputCount > 9) {
            String recipeInfo = ErrorUtil.getInfoFromRecipe(recipe, this);
            Log.error("Recipe has too many inputs. {}", recipeInfo);
            return false;
        }
        return inputCount > 0;
    }
}