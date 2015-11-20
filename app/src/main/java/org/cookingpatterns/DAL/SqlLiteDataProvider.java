package org.cookingpatterns.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.cookingpatterns.DAL.SqlLite.RecipeEntry;
import org.cookingpatterns.DAL.SqlLite.RecipeIngredientEntry;
import org.cookingpatterns.DAL.SqlLite.SqlLiteColumn;
import org.cookingpatterns.DAL.SqlLite.SqlLiteHelper;
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
        Log.d("SqlLite", "get");
        Cursor cursor = readableDatabase.query(
                RecipeEntry.TABLE_NAME,                    // The table to query
                new String[]{
                        RecipeEntry.COLUMN_ID.Name,
                        RecipeEntry.COLUMN_NAME.Name,
                        RecipeEntry.COLUMN_PORTIONS.Name,
                        RecipeEntry.COLUMN_CATEGORY.Name,
                        RecipeEntry.COLUMN_DESCRIPTION.Name,
                        RecipeEntry.COLUMN_TIME.Name,
                        RecipeEntry.COLUMN_RATING.Name
                },// The columns to return
                null,                                                    // The columns for the WHERE clause
                null,                                                    // The values for the WHERE clause
                null,                                                    // don't group the rows
                null,                                                    // don't filter by row groups
                RecipeEntry.COLUMN_NAME.Name + " ASC"      // The sort order
        );

        List<Recipe> list = new LinkedList<>();

        if(cursor.moveToFirst())
        {
            do {
                UUID id = (UUID) mapColumn(cursor, RecipeEntry.COLUMN_ID);
                String name = (String) mapColumn(cursor, RecipeEntry.COLUMN_NAME);
                String description = (String) mapColumn(cursor, RecipeEntry.COLUMN_DESCRIPTION);
                String category = (String) mapColumn(cursor, RecipeEntry.COLUMN_CATEGORY);
                String Time = (String) mapColumn(cursor, RecipeEntry.COLUMN_TIME);
                Integer portions = (Integer) mapColumn(cursor, RecipeEntry.COLUMN_PORTIONS);
                Integer rating = (Integer) mapColumn(cursor, RecipeEntry.COLUMN_RATING);

                Recipe recipe = new Recipe(id);
                recipe.setName(name);
                recipe.setDescription(description);
                recipe.setCategory(category);
              //  recipe.setIngredients();
                recipe.setPortions(portions);
                recipe.setRating(rating);
                recipe.setTime(Time);
                list.add(recipe);
            } while (cursor.moveToNext());
        }

        //TODO add Ingredients

        cursor.close();

        return list;
    }

    @Override
    public void addRecipe(Recipe recipe) {

        SQLiteDatabase writableDatabase = SqlLiteHelper.getInstance(_context).getWritableDatabase();

        try {
            writableDatabase.beginTransaction();

            ContentValues values = new ContentValues();
            values.put(RecipeEntry.COLUMN_ID.Name, UUIDHelper.toByteArray(recipe.getId()));
            values.put(RecipeEntry.COLUMN_NAME.Name, recipe.getName());
            values.put(RecipeEntry.COLUMN_CATEGORY.Name, recipe.getCategory());
            values.put(RecipeEntry.COLUMN_DESCRIPTION.Name, recipe.getDescription());
            values.put(RecipeEntry.COLUMN_PORTIONS.Name, recipe.getPortions());
            values.put(RecipeEntry.COLUMN_RATING.Name, recipe.getRating());
            values.put(RecipeEntry.COLUMN_TIME.Name, recipe.getTime());
            //values.put("", recipe.getImage().GetImag) //TODO image
            writableDatabase.insertOrThrow(RecipeEntry.TABLE_NAME , null, values);


            for (Ingredient ingredient: recipe.getIngredients()) {
                values.clear();
                values.put("RecipeId", UUIDHelper.toByteArray(recipe.getId()));
                values.put("IngredientId", ingredient.getId().toString());
                values.put("Amount", ingredient.getAmount());

                writableDatabase.insertOrThrow(RecipeIngredientEntry.TABLE_NAME, null, values);
            }
            //new UUID(recipe.getId().getMostSignificantBits(),recipe.getId().getMostSignificantBits());
            writableDatabase.setTransactionSuccessful();
        }
        finally {
            writableDatabase.endTransaction();
        }
    }

    @Override
    public void updateRecipe(Recipe recipe) {

        SQLiteDatabase writableDatabase = SqlLiteHelper.getInstance(_context).getWritableDatabase();

        try {
            writableDatabase.beginTransaction();

            ContentValues values = new ContentValues();
            values.put(RecipeEntry.COLUMN_NAME.Name, recipe.getName());
            values.put(RecipeEntry.COLUMN_CATEGORY.Name, recipe.getCategory());
            values.put(RecipeEntry.COLUMN_DESCRIPTION.Name, recipe.getDescription());
            values.put(RecipeEntry.COLUMN_PORTIONS.Name, recipe.getPortions());
            values.put(RecipeEntry.COLUMN_RATING.Name, recipe.getRating());
            values.put(RecipeEntry.COLUMN_TIME.Name, recipe.getTime());


            //writableDatabase.execSQL("");
            //writableDatabase.update(RecipeEntry.TABLE_NAME)
// Which row to update, based on the ID
            //String selection = FeedEntry.COLUMN_NAME_ENTRY_ID + " LIKE ?";
           // String[] selectionArgs = { UUIDHelper.toByteArray(recipe.getId()) };

          //  int count = writableDatabase.update(
          //          FeedReaderDbHelper.FeedEntry.TABLE_NAME,
          //          values,
          //          selection,
          //          selectionArgs);

            writableDatabase.setTransactionSuccessful();
        }
        finally {
            writableDatabase.endTransaction();
        }

    }

    @Override
    public List<Ingredient> getIngredientList() {
        List<Ingredient> list = new LinkedList<>();


        SQLiteDatabase readableDatabase = SqlLiteHelper.getInstance(_context).getReadableDatabase();

        Cursor cursor = readableDatabase.query(
                RecipeEntry.TABLE_NAME,                    // The table to query
                new String[]{RecipeEntry.COLUMN_ID.Name, RecipeEntry.COLUMN_NAME.Name},// The columns to return
                null,                                                    // The columns for the WHERE clause
                null,                                                    // The values for the WHERE clause
                null,                                                    // don't group the rows
                null,                                                    // don't filter by row groups
                RecipeEntry.COLUMN_NAME.Name + " ASC"      // The sort order
        );



        Ingredient ingredient = new Ingredient();
        ingredient.setName("test2");

        list.add(ingredient);

        return list;
    }

    @Override
    public void addIngredient(Ingredient ingredient) {

    }

    private Object mapColumn(Cursor cursor, SqlLiteColumn column)
    {
        int columnIndex = cursor.getColumnIndex(column.Name);
        switch (column.Type)
        {
            case UUID:
                byte[] bytes = cursor.getBlob(columnIndex);
                return UUIDHelper.getFromByteArray(bytes);
            case BLOB:
                return cursor.getBlob(columnIndex);
            case INTEGER:
                return cursor.getInt(columnIndex);
            case REAL:
                return cursor.getDouble(columnIndex);
            case TEXT:
                return cursor.getString(columnIndex);
        }

        return null;
    }
}
