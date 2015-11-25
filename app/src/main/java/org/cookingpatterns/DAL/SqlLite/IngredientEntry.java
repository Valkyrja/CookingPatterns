package org.cookingpatterns.DAL.SqlLite;

/**
 * Created by Andreas on 20.11.2015.
 */
public abstract class IngredientEntry {
    public static final String TABLE_NAME = "Ingredient";

    public static final SqlLiteColumn COLUMN_ID = new SqlLiteColumn("Id", SqlLiteDataTypes.UUID);
    public static final SqlLiteColumn COLUMN_NAME = new SqlLiteColumn("Name", SqlLiteDataTypes.TEXT);
    public static final SqlLiteColumn COLUMN_UNIT = new SqlLiteColumn("UnitOfMeasure", SqlLiteDataTypes.INTEGER);
}