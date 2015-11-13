package org.cookingpatterns.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;

import java.util.Collection;
import java.util.List;

/**
 * Created by Andreas on 10.11.2015.
 */
public class SqlLiteDataProvider implements DataProvider {

    private Context _context;

    public SqlLiteDataProvider(Context context)
    {
        _context = context;
        //SqlLiteHelper.getInstance(context);
    }

    @Override
    public List<Recipe> getRecipeList() {
      // new SqlLiteHelper();
        return null;

    }

    @Override
    public void addRecipe(Recipe recipe) {

        SQLiteDatabase writableDatabase = SqlLiteHelper.getInstance(_context).getWritableDatabase();

        try {


            //writableDatabase.beginTransaction();
            ContentValues values = new ContentValues();

            values.put("Id", recipe.getId().toString());
            values.put("Name", recipe.getName());
            //TODO Add more

            writableDatabase.insertOrThrow("Recipe", null, values);


            for (Ingredient ingredient: recipe.getIngredients()) {
                values.clear();
                values.put("RecipeId", recipe.getId().toString());
                values.put("IngredientId", ingredient.getId().toString());
                //TODO add more
                writableDatabase.insertOrThrow("RecipeIngredient", null, values);
            }

            writableDatabase.setTransactionSuccessful();
        }
        catch (SQLException sqlException)
        {

        }
        finally {
            writableDatabase.endTransaction();
        }
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
