package org.cookingpatterns.DAL.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import org.cookingpatterns.DAL.IDataProvider;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.Model.UnitOfMeasure;
import org.cookingpatterns.Parsing.RootNode;
import org.cookingpatterns.Parsing.Node;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Andreas on 10.11.2015.
 */
public class SqlLiteDataProvider implements IDataProvider {

    private Context _context;

    public SqlLiteDataProvider(Context context) {
        _context = context;
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
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                RecipeEntry.COLUMN_NAME.Name + " ASC"     // The sort order
        );

        List<Recipe> list = mapRecipes(cursor);
        loadIngredientsForRecipes(list, readableDatabase);

        return list;
    }

    @Override
    public List<Recipe> getRecipeList(RootNode searchTree) {

        SqlLiteSyntaxTreeVisitor visitor = new SqlLiteSyntaxTreeVisitor();
        for (Node node : searchTree) {
            node.acceptVisitor(visitor);
        }

        String sqlWhere = visitor.GetSql();
        LinkedList<String> parameterList = visitor.GetSqlParameter();
        String[] parameterArray = new String[parameterList.size()];
        parameterArray = parameterList.toArray(parameterArray);

        String sql = "SELECT r." +
                RecipeEntry.COLUMN_ID.Name + ", r." +
                RecipeEntry.COLUMN_NAME.Name + ", r." +
                RecipeEntry.COLUMN_PORTIONS.Name + ", r." +
                RecipeEntry.COLUMN_CATEGORY.Name + ", r." +
                RecipeEntry.COLUMN_DESCRIPTION.Name + ", r." +
                RecipeEntry.COLUMN_TIME.Name + ", r." +
                RecipeEntry.COLUMN_RATING.Name + " " +
                "FROM " + RecipeEntry.TABLE_NAME + " AS r " +
                "WHERE " + sqlWhere + " " +
                "ORDER BY r." + RecipeEntry.COLUMN_NAME.Name + " ASC";

        SQLiteDatabase readableDatabase = SqlLiteHelper.getInstance(_context).getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery(sql, parameterArray);

        return mapRecipes(cursor);
    }

    private void loadIngredientsForRecipes(List<Recipe> recipes, SQLiteDatabase readableDatabase) {
        //add ingredients to recipes
        for (Recipe recipe : recipes) {

            String sql = "SELECT ri." +
                    RecipeIngredientEntry.COLUMN_AMOUNT.Name + ", i." +
                    IngredientEntry.COLUMN_ID.Name + ", i." +
                    IngredientEntry.COLUMN_NAME.Name + ", i." +
                    IngredientEntry.COLUMN_UNIT.Name + " " +
                    "FROM " + RecipeIngredientEntry.TABLE_NAME + " AS ri INNER JOIN " +
                    IngredientEntry.TABLE_NAME + " AS i ON " +
                    "ri." + RecipeIngredientEntry.COLUMN_INGREDIENTID.Name +
                    " = i." + IngredientEntry.COLUMN_ID.Name + " " +
                    "WHERE ri." + RecipeIngredientEntry.COLUMN_RECIPEID.Name + " = ?";

            Cursor cursor = readableDatabase.rawQuery(sql, new String[]{recipe.getId().toString()});

            if (cursor.moveToFirst()) {
                LinkedList<Ingredient> ingredientList = new LinkedList<>();
                do {
                    UUID id = (UUID) mapColumn(cursor, IngredientEntry.COLUMN_ID);
                    String name = (String) mapColumn(cursor, IngredientEntry.COLUMN_NAME);
                    Integer unitInt = (Integer) mapColumn(cursor, IngredientEntry.COLUMN_UNIT);
                    Double amount = (Double) mapColumn(cursor, RecipeIngredientEntry.COLUMN_AMOUNT);

                    Ingredient ingredient = new Ingredient(id);
                    ingredient.setName(name);
                    ingredient.setUnit(UnitOfMeasure.values()[unitInt]);
                    ingredient.setAmount(amount);

                    ingredientList.add(ingredient);
                } while (cursor.moveToNext());

                recipe.setIngredients(ingredientList);
            }
            cursor.close();
        }
    }

    private List<Recipe> mapRecipes(Cursor cursor) {
        List<Recipe> list = new LinkedList<>();

        if (cursor.moveToFirst()) {
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

        cursor.close();

        return list;
    }

    @Override
    public void addRecipe(Recipe recipe) {

        SQLiteDatabase writableDatabase = SqlLiteHelper.getInstance(_context).getWritableDatabase();

        try {
            writableDatabase.beginTransaction();

            ContentValues values = new ContentValues();
            values.put(RecipeEntry.COLUMN_ID.Name, recipe.getId().toString());
            values.put(RecipeEntry.COLUMN_NAME.Name, recipe.getName());
            values.put(RecipeEntry.COLUMN_CATEGORY.Name, recipe.getCategory());
            values.put(RecipeEntry.COLUMN_DESCRIPTION.Name, recipe.getDescription());
            values.put(RecipeEntry.COLUMN_PORTIONS.Name, recipe.getPortions());
            values.put(RecipeEntry.COLUMN_RATING.Name, recipe.getRating());
            values.put(RecipeEntry.COLUMN_TIME.Name, recipe.getTime());
            //values.put("", recipe.getImage().GetImag) //TODO image
            writableDatabase.insertOrThrow(RecipeEntry.TABLE_NAME, null, values);

            addRecipeIngredients(recipe, writableDatabase);

            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    @Override
    public void updateRecipe(Recipe recipe) {

        SQLiteDatabase writableDatabase = SqlLiteHelper.getInstance(_context).getWritableDatabase();

        try {
            writableDatabase.beginTransaction();

            String sql = "UPDATE " + RecipeEntry.TABLE_NAME + " SET " +
                    RecipeEntry.COLUMN_NAME.Name + " = ? " +
                    RecipeEntry.COLUMN_CATEGORY.Name + " = ? " +
                    RecipeEntry.COLUMN_DESCRIPTION.Name + " = ? " +
                    RecipeEntry.COLUMN_PORTIONS.Name + " = ? " +
                    RecipeEntry.COLUMN_RATING.Name + " = ? " +
                    RecipeEntry.COLUMN_TIME.Name + " = ? " +
                    "WHERE " + RecipeEntry.COLUMN_ID.Name + " = ?";
            SQLiteStatement statement = writableDatabase.compileStatement(sql);

            statement.bindString(1, recipe.getName());
            statement.bindString(2, recipe.getCategory());
            statement.bindString(3, recipe.getDescription());
            statement.bindLong(4, recipe.getPortions());
            statement.bindLong(5, recipe.getRating());
            statement.bindString(6, recipe.getTime());
            statement.bindString(7, recipe.getId().toString());

            statement.executeUpdateDelete();

            sql = "DELETE FROM " + RecipeIngredientEntry.TABLE_NAME + " WHERE " +
                    RecipeIngredientEntry.COLUMN_RECIPEID.Name + " = ?";
            statement = writableDatabase.compileStatement(sql);
            statement.bindString(1, recipe.getId().toString());
            statement.executeUpdateDelete();

            addRecipeIngredients(recipe, writableDatabase);

            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }

    }

    private void addRecipeIngredients(Recipe recipe, SQLiteDatabase writableDatabase) {

        ContentValues values = new ContentValues();
        for (Ingredient ingredient : recipe.getIngredients()) {
            values.clear();
            values.put(IngredientEntry.COLUMN_ID.Name, ingredient.getId().toString());
            values.put(IngredientEntry.COLUMN_NAME.Name, ingredient.getName());
            values.put(IngredientEntry.COLUMN_UNIT.Name, ingredient.getUnit().ordinal());
            writableDatabase.insertWithOnConflict(IngredientEntry.TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_IGNORE);

            values.clear();
            values.put(RecipeIngredientEntry.COLUMN_RECIPEID.Name, recipe.getId().toString());
            values.put(RecipeIngredientEntry.COLUMN_INGREDIENTID.Name, ingredient.getId().toString());
            values.put(RecipeIngredientEntry.COLUMN_AMOUNT.Name, ingredient.getAmount());
            writableDatabase.insertOrThrow(RecipeIngredientEntry.TABLE_NAME, null, values);
        }

    }

    @Override
    public List<Ingredient> getIngredientList() {

        SQLiteDatabase readableDatabase = SqlLiteHelper.getInstance(_context).getReadableDatabase();

        Cursor cursor = readableDatabase.query(
                IngredientEntry.TABLE_NAME,                    // The table to query
                new String[]{
                        IngredientEntry.COLUMN_ID.Name,
                        IngredientEntry.COLUMN_NAME.Name,
                        IngredientEntry.COLUMN_UNIT.Name},// The columns to return
                null,                                     // The columns for the WHERE clause
                null,                                     // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                IngredientEntry.COLUMN_NAME.Name + " ASC" // The sort order
        );

        List<Ingredient> list = new LinkedList<>();

        if (cursor.moveToFirst()) {
            do {
                UUID id = (UUID) mapColumn(cursor, IngredientEntry.COLUMN_ID);
                String name = (String) mapColumn(cursor, IngredientEntry.COLUMN_NAME);
                Integer unitInt = (Integer) mapColumn(cursor, IngredientEntry.COLUMN_UNIT);

                Ingredient ingredient = new Ingredient(id);
                ingredient.setName(name);
                ingredient.setUnit(UnitOfMeasure.values()[unitInt]);
                list.add(ingredient);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    @Override
    public void addIngredient(Ingredient ingredient) {
        SQLiteDatabase writableDatabase = SqlLiteHelper.getInstance(_context).getWritableDatabase();

        try {
            writableDatabase.beginTransaction();

            ContentValues values = new ContentValues();
            values.put(IngredientEntry.COLUMN_ID.Name, ingredient.getId().toString());
            values.put(IngredientEntry.COLUMN_NAME.Name, ingredient.getName());
            values.put(IngredientEntry.COLUMN_UNIT.Name, ingredient.getUnit().ordinal());

            writableDatabase.insertOrThrow(IngredientEntry.TABLE_NAME, null, values);

            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }

    private Object mapColumn(Cursor cursor, SqlLiteColumn column) {
        int columnIndex = cursor.getColumnIndex(column.Name);
        switch (column.Type) {
            case UUID:
                return UUID.fromString(cursor.getString(columnIndex));
            //   byte[] bytes = cursor.getBlob(columnIndex);
            //  return UUIDHelper.getFromByteArray(bytes);
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
