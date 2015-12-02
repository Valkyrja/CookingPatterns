package org.cookingpatterns.EventMessages;

import org.cookingpatterns.Loader.DataResponse;
import org.cookingpatterns.Model.Ingredient;

/**
 * Created by Andreas on 02.12.2015.
 */
public class OnAddIngredientResponseEvent {
    private DataResponse<Ingredient> response;

    public OnAddIngredientResponseEvent(DataResponse<Ingredient> dataResponse)
    {
        response = dataResponse;
    }

    public DataResponse<Ingredient> getNewIngredient()
    {
        return response;
    }
}
