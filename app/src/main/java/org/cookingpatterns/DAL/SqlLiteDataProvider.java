package org.cookingpatterns.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Andreas on 10.11.2015.
 */
public class SqlLiteDataProvider implements IDataProvider {

    private Context _context;

    public SqlLiteDataProvider(Context context)
    {
        _context = context;
        //SqlLiteHelper.getInstance(context);
    }

    @Override
    public List<Recipe> getRecipeList() {
        SQLiteDatabase readableDatabase = SqlLiteHelper.getInstance(_context).getReadableDatabase();

        Cursor cursor = readableDatabase.query(
                SqlLiteHelper.RecipeEntry.TABLE_NAME,                    // The table to query
                new String[]{SqlLiteHelper.RecipeEntry.COLUMN_NAME_ID, SqlLiteHelper.RecipeEntry.COLUMN_NAME_NAME},// The columns to return
                null,                                                    // The columns for the WHERE clause
                null,                                                    // The values for the WHERE clause
                null,                                                    // don't group the rows
                null,                                                    // don't filter by row groups
                SqlLiteHelper.RecipeEntry.COLUMN_NAME_NAME + " ASC"      // The sort order
        );

        List<Recipe> list = new LinkedList<>();

        if(cursor.moveToFirst())
        {
            do {
                byte[] bytes = cursor.getBlob(cursor.getColumnIndex(SqlLiteHelper.RecipeEntry.COLUMN_NAME_ID));
                UUID id = UUIDHelper.getFromByteArray(bytes);
                String name = cursor.getString(cursor.getColumnIndex(SqlLiteHelper.RecipeEntry.COLUMN_NAME_NAME));

                Recipe recipe = new Recipe(id);
                recipe.setName(name);
                list.add(recipe);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return list;
    }

    @Override
    public void addRecipe(Recipe recipe) {

        SQLiteDatabase writableDatabase = SqlLiteHelper.getInstance(_context).getWritableDatabase();

        try {


            //writableDatabase.beginTransaction();
            ContentValues values = new ContentValues();

            values.put("Id", UUIDHelper.toByteArray(recipe.getId()));
            values.put("Name", recipe.getName());
            //TODO Add more

            writableDatabase.insertOrThrow("Recipe", null, values);


            for (Ingredient ingredient: recipe.getIngredients()) {
                values.clear();
                values.put("RecipeId", UUIDHelper.toByteArray(recipe.getId()));
                values.put("IngredientId", ingredient.getId().toString());
                //TODO add more
                writableDatabase.insertOrThrow("RecipeIngredient", null, values);
            }
            //new UUID(recipe.getId().getMostSignificantBits(),recipe.getId().getMostSignificantBits());
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
        List<Ingredient> list = new LinkedList<>();

        Ingredient ingredient = new Ingredient();
        ingredient.setName("test2");

        list.add(ingredient);

        return list;
    }

    @Override
    public void addIngredient(Ingredient ingredient) {

    }
}
