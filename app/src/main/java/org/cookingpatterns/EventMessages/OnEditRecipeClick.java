package org.cookingpatterns.EventMessages;


import org.cookingpatterns.Model.Recipe;

import java.util.UUID;

public class OnEditRecipeClick
{
    private Recipe recipe;
    /*private UUID recipeID;*/

    public OnEditRecipeClick(Recipe res)
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
