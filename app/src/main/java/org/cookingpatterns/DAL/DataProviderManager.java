package org.cookingpatterns.DAL;

import android.content.Context;

import org.cookingpatterns.DAL.SqlLite.SqlLiteDataProvider;

/**
 * Created by Andreas on 25.11.2015.
 */
public class DataProviderManager {

    private static DataProviderManager _instance;

    private IDataProvider _activeDataProvider;

    private DataProviderManager(Context context)
    {
        _activeDataProvider = new SqlLiteDataProvider(context); //defaultdataprovider
    }

    public static synchronized DataProviderManager getInstance(Context context)
    {
        if(_instance == null)
        {
            _instance = new DataProviderManager(context);
        }
        return _instance;
    }

    public IDataProvider getActiveDataProvider()
    {
        return _activeDataProvider;
    }

    public void setActiveDataProvider(IDataProvider dataProvider)
    {
        _activeDataProvider = dataProvider;
    }
}
