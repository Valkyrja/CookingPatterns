package org.cookingpatterns.EventMessages;

import org.cookingpatterns.Loader.DataResponse;
import org.cookingpatterns.Model.Recipe;

import java.util.List;

/**
 * Created by Andreas on 16.11.2015.
 */
public class OnRecipeListResponseEvent {

    private DataResponse<List<Recipe>> response;

    public OnRecipeListResponseEvent(DataResponse<List<Recipe>> dataResponse)
    {
        response = dataResponse;
    }

    public DataResponse<List<Recipe>> getRecipeList()
    {
        return response;
    }
}
