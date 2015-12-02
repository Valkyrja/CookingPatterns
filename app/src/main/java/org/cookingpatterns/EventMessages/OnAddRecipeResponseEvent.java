package org.cookingpatterns.EventMessages;

import org.cookingpatterns.Loader.DataResponse;
import org.cookingpatterns.Model.Recipe;

/**
 * Created by Andreas on 02.12.2015.
 */
public class OnAddRecipeResponseEvent {
    private DataResponse<Recipe> response;

    public OnAddRecipeResponseEvent(DataResponse<Recipe> dataResponse)
    {
        response = dataResponse;
    }

    public DataResponse<Recipe> getNewRecipe()
    {
        return response;
    }
}
