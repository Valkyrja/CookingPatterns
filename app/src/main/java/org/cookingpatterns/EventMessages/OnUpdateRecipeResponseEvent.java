package org.cookingpatterns.EventMessages;

import org.cookingpatterns.Loader.DataResponse;
import org.cookingpatterns.Model.Recipe;

/**
 * Created by Andreas on 02.12.2015.
 */
public class OnUpdateRecipeResponseEvent {
    private DataResponse<Recipe> response;

    public OnUpdateRecipeResponseEvent(DataResponse<Recipe> dataResponse) {
        response = dataResponse;
    }

    public DataResponse<Recipe> getRecipe() {
        return response;
    }
}
