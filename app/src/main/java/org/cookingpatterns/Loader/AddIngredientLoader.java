package org.cookingpatterns.Loader;

import android.content.Context;
import android.os.Bundle;

import org.cookingpatterns.DAL.DataProviderManager;
import org.cookingpatterns.Model.Ingredient;

/**
 * Created by Andreas on 25.11.2015.
 */
public class AddIngredientLoader extends DataLoader<Ingredient> {
    public AddIngredientLoader(Context context, Bundle args) {
        super(context, args);
    }

    @Override
    public Ingredient call(Bundle args) {
        Ingredient ingredient = (Ingredient) args.get("ingredient");
        DataProviderManager.getInstance(mContext).getActiveDataProvider().addIngredient(ingredient);
        return ingredient;
    }

    @Override
    public void sendEvent(DataResponse<Ingredient> data) {
        //   eventManager.fire(new OnRecipeListResponseEvent(data));
        //TODO fire ingredientdded event
    }
}
