package org.cookingpatterns.Loader;

import android.content.Context;
import android.os.Bundle;

import org.cookingpatterns.DAL.IDataProvider;
import org.cookingpatterns.EventMessages.OnIngredientListResponseEvent;
import org.cookingpatterns.Model.Ingredient;

import java.util.List;

/**
 * Created by Andreas on 16.11.2015.
 */
public class IngredientLoader extends DataLoader<List<Ingredient>> {

    public IngredientLoader(Context context, IDataProvider service, Bundle args) {

        super(context, service, args);
    }

    @Override
    public List<Ingredient> call(Bundle args) {

        // Log.d("IssuesLoader", "call");

        return mDataProvider.getIngredientList();
    }

    @Override
    public void sendEvent(DataResponse<List<Ingredient>> data) {
        eventManager.fire(new OnIngredientListResponseEvent(data));
    }
}