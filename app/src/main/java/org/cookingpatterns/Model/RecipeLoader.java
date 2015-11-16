package org.cookingpatterns.Model;

import android.content.Context;
import android.os.Bundle;

import org.cookingpatterns.DAL.IDataProvider;

import java.util.List;

/**
 * Created by Andreas on 16.11.2015.
 */

public class RecipeLoader extends DataLoader<List<Recipe>, IDataProvider> {

    public RecipeLoader(Context context, IDataProvider service, Bundle args) {

        super(context, service, args);
    }

    @Override
    public List<Recipe> call(Bundle args) {

       // Log.d("IssuesLoader", "call");

        return mDataProvider.getRecipeList();
    }
}