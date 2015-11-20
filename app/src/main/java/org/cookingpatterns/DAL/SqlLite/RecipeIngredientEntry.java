package org.cookingpatterns.DAL.SqlLite;

import android.provider.BaseColumns;

/**
 * Created by Andreas on 20.11.2015.
 */
public abstract class RecipeIngredientEntry implements BaseColumns {
    public static final String TABLE_NAME = "RecipeIngredient";
    public static final String COLUMN_NAME_RECIPEID = "RecipeId";
    public static final String COLUMN_NAME_INGREDIENTID = "IngredientId";
    public static final String COLUMN_NAME_AMOUNT = "Amount";
}