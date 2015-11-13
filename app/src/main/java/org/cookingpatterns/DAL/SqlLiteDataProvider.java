package org.cookingpatterns.DAL;

import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;

import java.util.List;

/**
 * Created by Andreas on 10.11.2015.
 */
public class SqlLiteDataProvider implements DataProvider {
    @Override
    public List<Recipe> getRecipeList() {
       // SqlLiteHelper.getInstance()
        return null;

    }

    @Override
    public void addRecipe(Recipe recipe) {

    }

    @Override
    public void updateRecipe(Recipe recipe) {

    }

    @Override
    public List<Ingredient> getIngredientList() {
        return null;
    }

    @Override
    public void addIngredient(Ingredient ingredient) {

    }
}
