package org.cookingpatterns.Loader;

import android.content.Context;
import android.os.Bundle;

import org.cookingpatterns.DAL.IDataProvider;
import org.cookingpatterns.EventMessages.OnRecipeListResponseEvent;
import org.cookingpatterns.Model.Recipe;

import java.util.List;


/**
 * Created by Andreas on 16.11.2015.
 */

public class RecipeLoader extends DataLoader<List<Recipe>> {

    public RecipeLoader(Context context, IDataProvider service, Bundle args) {

        super(context, service, args);
    }

    @Override
    public List<Recipe> call(Bundle args) {

        return mDataProvider.getRecipeList();
    }

    @Override
    public void sendEvent(DataResponse<List<Recipe>> data) {
        eventManager.fire(new OnRecipeListResponseEvent(data));
    }
}