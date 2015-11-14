package org.cookingpatterns.EventMessages;

import org.cookingpatterns.Model.Recipe;

public class OnSaveRecipeClick
{
    private Recipe recipe;

    public OnSaveRecipeClick(Recipe res)
    {
        this.recipe = res;
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
