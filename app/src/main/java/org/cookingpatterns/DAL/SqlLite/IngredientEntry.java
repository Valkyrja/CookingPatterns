package org.cookingpatterns.DAL.SqlLite;

import android.util.Pair;

/**
 * Created by Andreas on 20.11.2015.
 */
public abstract class IngredientEntry {
    public static final String TABLE_NAME = "Ingredient";
    public static final String COLUMN_NAME_ID = "Id";
    public static final String COLUMN_NAME_NAME = "Name";
    public static final String COLUMN_NAME_UNIT = "UnitOfMeasure";

    public static final SqlLiteColumn Id = new SqlLiteColumn("Id", SqlLiteDataTypes.UUID);
}