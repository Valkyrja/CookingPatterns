package org.cookingpatterns.EventMessages;

import org.cookingpatterns.Model.Recipe;

public class OnDisplayRecipeClick
{
    private Recipe recipe;
    /*private UUID recipeID;*/

    public OnDisplayRecipeClick(Recipe res)
    {
        this.recipe = res;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    /*public UUID getRecipeID() {
        return recipeID;
    }*/
}
