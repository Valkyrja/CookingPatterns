package org.cookingpatterns.Loader;

import android.content.Context;
import android.os.Bundle;

import org.cookingpatterns.DAL.DataProviderManager;
import org.cookingpatterns.EventMessages.OnAddRecipeResponseEvent;
import org.cookingpatterns.Model.Recipe;

/**
 * Created by Andreas on 25.11.2015.
 */
public class AddRecipeLoader extends DataLoader<Recipe> {
    public AddRecipeLoader(Context context, Bundle args) {
        super(context, args);
    }

    @Override
    public Recipe call(Bundle args) {
        Recipe recipe = (Recipe) args.get("recipe");
        DataProviderManager.getInstance(mContext).getActiveDataProvider().addRecipe(recipe);
        return recipe;
    }

    @Override
    public void sendEvent(DataResponse<Recipe> data) {
           eventManager.fire(new OnAddRecipeResponseEvent(data));
    }
}
