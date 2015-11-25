package org.cookingpatterns.Loader;

import android.content.Context;
import android.os.Bundle;

import org.cookingpatterns.DAL.DataProviderManager;
import org.cookingpatterns.EventMessages.OnIngredientListResponseEvent;
import org.cookingpatterns.Model.Ingredient;

import java.util.List;

/**
 * Created by Andreas on 16.11.2015.
 */
public class IngredientLoader extends DataLoader<List<Ingredient>> {

    public IngredientLoader(Context context, Bundle args) {

        super(context, args);
    }

    @Override
    public List<Ingredient> call(Bundle args) {

        return DataProviderManager.getInstance(mContext).getActiveDataProvider().getIngredientList();
    }

    @Override
    public void sendEvent(DataResponse<List<Ingredient>> data) {
        eventManager.fire(new OnIngredientListResponseEvent(data));
    }
}