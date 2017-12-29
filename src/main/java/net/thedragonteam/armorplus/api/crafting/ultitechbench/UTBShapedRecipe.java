/*
 * Copyright (c) TheDragonTeam 2016-2017.
 */

package net.thedragonteam.armorplus.api.crafting.ultitechbench;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.thedragonteam.armorplus.api.crafting.IRecipe;
import net.thedragonteam.armorplus.api.crafting.utils.ShapedRecipeUtils;
import net.thedragonteam.armorplus.container.base.InventoryCraftingImproved;

import javax.annotation.Nonnull;
import java.util.stream.IntStream;

/**
 * @author Sokratis Fotkatzikis - TheDragonTeam
 */
public class UTBShapedRecipe implements IRecipe {
    /**
     * How many horizontal itemHandler this recipe is wide.
     */
    public final int recipeWidth;
    /**
     * How many vertical itemHandler this recipe uses.
     */
    public final int recipeHeight;
    /**
     * Is a array of ItemStack that composes the recipe.
     */
    public final ItemStack[] input;
    /**
     * Is the ItemStack that you get when craft the recipe.
     */
    private final ItemStack recipeOutput;
    private boolean copyIngredientNBT;

    public UTBShapedRecipe(int width, int height, ItemStack[] ingredientsIn, ItemStack output) {
        this.recipeWidth = width;
        this.recipeHeight = height;
        this.input = ingredientsIn;

        IntStream.range(0, this.input.length).filter(i -> this.input[i].isEmpty()).forEachOrdered(i -> this.input[i] = ItemStack.EMPTY);

        this.recipeOutput = output;
    }

    @Override
    @Nonnull
    public ItemStack getRecipeOutput() {
        return this.recipeOutput;
    }

    /**
     * Used to check if a recipe matches current crafting inventory
     */
    @Override
    public boolean matches(@Nonnull InventoryCraftingImproved inv, @Nonnull World worldIn) {
        return ShapedRecipeUtils.matches(7, recipeWidth, recipeHeight, input, inv);
    }

    /**
     * Returns an Item that is the result of this recipe
     */
    @Override
    @Nonnull
    public ItemStack getCraftingResult(@Nonnull InventoryCraftingImproved inv) {
        return ShapedRecipeUtils.getCraftingResult(getRecipeOutput(), copyIngredientNBT, inv);
    }

    public int getWidth() {
        return recipeWidth;
    }

    public int getHeight() {
        return recipeHeight;
    }

    /**
     * Returns the input for this recipe, any mod accessing this value should never
     * manipulate the values in this array as it will effect the recipe itself.
     *
     * @return The recipes input vales.
     */
    public Object[] getInput() {
        return this.input;
    }

    /**
     * Returns the size of the recipe area
     */
    @Override
    public int getRecipeSize() {
        return this.recipeWidth * this.recipeHeight;
    }
}