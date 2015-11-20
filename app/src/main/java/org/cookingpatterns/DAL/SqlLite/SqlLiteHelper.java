package org.cookingpatterns.DAL.SqlLite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Andreas on 10.11.2015.
 */

public class SqlLiteHelper extends SQLiteOpenHelper {

    private static SqlLiteHelper _instance;

    private static final String DATABASE_NAME = "CookingPatterns.db";


    private SqlLiteHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    public static synchronized SqlLiteHelper getInstance(Context context)
    {
        if(_instance == null)
        {
          //  context.deleteDatabase(DATABASE_NAME);
            _instance = new SqlLiteHelper(context.getApplicationContext());
        }
        return _instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("SqlLite", "create Database");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + RecipeEntry.TABLE_NAME + "( " +
                RecipeEntry.COLUMN_ID.Name +" BLOB NOT NULL PRIMARY KEY, " +
                RecipeEntry.COLUMN_NAME.Name + " TEXT NOT NULL, " +
                RecipeEntry.COLUMN_CATEGORY.Name + " TEXT NOT NULL, " +
                RecipeEntry.COLUMN_DESCRIPTION.Name + " TEXT NOT NULL, " +
                RecipeEntry.COLUMN_PORTIONS.Name + " INTEGER NOT NULL, " +
                RecipeEntry.COLUMN_RATING.Name + " INTEGER NOT NULL, " +
                RecipeEntry.COLUMN_TIME.Name +" TEXT NOT NULL);");
        //TODO image
        db.execSQL("CREATE TABLE IF NOT EXISTS " + IngredientEntry.TABLE_NAME + "( " +
                IngredientEntry.COLUMN_NAME_ID + " BLOB NOT NULL PRIMARY KEY, "+
                IngredientEntry.COLUMN_NAME_NAME + " TEXT NOT NULL, "+
                IngredientEntry.COLUMN_NAME_UNIT + " INTEGER NOT NULL);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + RecipeIngredientEntry.TABLE_NAME + "( " +
                RecipeIngredientEntry.COLUMN_NAME_RECIPEID + " BLOB NOT NULL, " +
                RecipeIngredientEntry.COLUMN_NAME_INGREDIENTID + " TEXT NOT NULL, " +
                RecipeIngredientEntry.COLUMN_NAME_AMOUNT + " REAL NOT NULL, " +
                "PRIMARY KEY ("+RecipeIngredientEntry.COLUMN_NAME_RECIPEID + ", " +
                RecipeIngredientEntry.COLUMN_NAME_INGREDIENTID+"));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


