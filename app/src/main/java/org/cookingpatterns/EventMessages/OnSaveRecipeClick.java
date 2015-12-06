package org.cookingpatterns.EventMessages;

import org.cookingpatterns.Model.Recipe;

public class OnSaveRecipeClick
{
    private Recipe recipe;
    private boolean isNewRecipe;

    public OnSaveRecipeClick(Recipe res, boolean isNewRecipe)
    {
        this.recipe = res;
        this.isNewRecipe = isNewRecipe;
    }

    public Recipe getRecipe() {
        return recipe;
    }
    public boolean getIsNew() {
        return isNewRecipe;
    }
}
