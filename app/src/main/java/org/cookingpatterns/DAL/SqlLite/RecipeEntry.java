package org.cookingpatterns.DAL.SqlLite;

import android.provider.BaseColumns;

/**
 * Created by Andreas on 20.11.2015.
 */
public abstract class RecipeEntry implements BaseColumns {
    public static final String TABLE_NAME = "Recipe";

    public static final SqlLiteColumn COLUMN_ID = new SqlLiteColumn("Id", SqlLiteDataTypes.UUID);
    public static final SqlLiteColumn COLUMN_NAME = new SqlLiteColumn("Name", SqlLiteDataTypes.TEXT);
    public static final SqlLiteColumn COLUMN_CATEGORY = new SqlLiteColumn("Category", SqlLiteDataTypes.TEXT);
    public static final SqlLiteColumn COLUMN_DESCRIPTION = new SqlLiteColumn("Description", SqlLiteDataTypes.TEXT);
    public static final SqlLiteColumn COLUMN_PORTIONS = new SqlLiteColumn("Portions", SqlLiteDataTypes.INTEGER);
    public static final SqlLiteColumn COLUMN_RATING = new SqlLiteColumn("Rating", SqlLiteDataTypes.INTEGER);
    public static final SqlLiteColumn COLUMN_TIME = new SqlLiteColumn("Time", SqlLiteDataTypes.TEXT);
}