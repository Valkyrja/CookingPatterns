package org.cookingpatterns.DAL;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import roboguice.inject.ContextSingleton;

import javax.inject.Inject;

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
            _instance = new SqlLiteHelper(context.getApplicationContext());
        }
        return _instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS " + RecipeEntry.TABLE_NAME + "( "
                + RecipeEntry.COLUMN_NAME_ID +" BLOB NOT NULL PRIMARY KEY, "+
                RecipeEntry.COLUMN_NAME_NAME + " TEXT NOT NULL);");
        //TODO add more
        db.execSQL("CREATE TABLE IF NOT EXISTS " + IngredientEntry.TABLE_NAME + "( "
                + IngredientEntry.COLUMN_NAME_ID +" BLOB NOT NULL PRIMARY KEY, "+
                IngredientEntry.COLUMN_NAME_NAME + " TEXT NOT NULL);");
        //TODO add more
        db.execSQL("CREATE TABLE IF NOT EXISTS " + RecipeIngredientEntry.TABLE_NAME + "( "
                + RecipeIngredientEntry.COLUMN_NAME_RECIPEID +" BLOB NOT NULL, "+
                RecipeIngredientEntry.COLUMN_NAME_INGREDIENTID + " TEXT NOT NULL,"+
                "PRIMARY KEY ("+RecipeIngredientEntry.COLUMN_NAME_RECIPEID + ", " +
                RecipeIngredientEntry.COLUMN_NAME_INGREDIENTID+"));");
        //TODO add more

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public abstract class RecipeEntry implements BaseColumns {
        public static final String TABLE_NAME = "Recipe";
        public static final String COLUMN_NAME_ID = "Id";
        public static final String COLUMN_NAME_NAME = "Name";
     //   public static final String COLUMN_NAME_SUBTITLE = "subtitle";

    }

    public abstract class IngredientEntry implements  BaseColumns{
        public static final String TABLE_NAME = "Ingredient";
        public static final String COLUMN_NAME_ID = "Id";
        public static final String COLUMN_NAME_NAME = "Name";
    }

    public abstract class RecipeIngredientEntry implements  BaseColumns{
        public static final String TABLE_NAME = "RecipeIngredient";
        public static final String COLUMN_NAME_RECIPEID = "RecipeId";
        public static final String COLUMN_NAME_INGREDIENTID = "IngredientId";
    }
}


