package org.cookingpatterns.DAL.SqlLite;

import android.provider.BaseColumns;

/**
 * Created by Andreas on 20.11.2015.
 */
public abstract class RecipeIngredientEntry implements BaseColumns {
    public static final String TABLE_NAME = "RecipeIngredient";

    public static final SqlLiteColumn COLUMN_RECIPEID = new SqlLiteColumn("RecipeId", SqlLiteDataTypes.UUID);
    public static final SqlLiteColumn COLUMN_INGREDIENTID = new SqlLiteColumn("IngredientId", SqlLiteDataTypes.UUID);
    public static final SqlLiteColumn COLUMN_AMOUNT = new SqlLiteColumn("Amount", SqlLiteDataTypes.REAL);
}