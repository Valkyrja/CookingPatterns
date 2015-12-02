package org.cookingpatterns.EventMessages;

import org.cookingpatterns.Loader.DataResponse;
import org.cookingpatterns.Model.Ingredient;

import java.util.List;

/**
 * Created by Andreas on 16.11.2015.
 */
public class OnIngredientListResponseEvent {
    private DataResponse<List<Ingredient>> response;

    public OnIngredientListResponseEvent(DataResponse<List<Ingredient>> dataResponse) {
        response = dataResponse;
    }

    public DataResponse<List<Ingredient>> getIngredientList() {
        return response;
    }

}
