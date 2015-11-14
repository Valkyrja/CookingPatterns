package org.cookingpatterns.EventMessages;


import org.cookingpatterns.Model.Recipe;

public class OnUpdateRecipeDataForViewEvent
{
    private Recipe recipe;

    public OnUpdateRecipeDataForViewEvent(Recipe recipe)
    {
        this.recipe = recipe;
    }

    public Recipe GetRecipe()
    {
        return recipe;
    }
}
