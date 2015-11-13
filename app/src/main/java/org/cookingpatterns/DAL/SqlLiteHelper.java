package org.cookingpatterns.DAL;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
