package org.cookingpatterns.DAL;

import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.Parsing.RootNode;

import java.util.List;

/**
 * Created by Andreas on 10.11.2015.
 */
public interface IDataProvider {

    List<Recipe> getRecipeList();

    List<Recipe> getRecipeList(RootNode searchTree);

    void addRecipe(Recipe recipe);

    void updateRecipe(Recipe recipe);

    List<Ingredient> getIngredientList();

    void addIngredient(Ingredient ingredient);
}
