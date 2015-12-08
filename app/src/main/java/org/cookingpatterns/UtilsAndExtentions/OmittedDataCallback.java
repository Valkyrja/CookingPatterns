package org.cookingpatterns.UtilsAndExtentions;

import android.util.Log;

import org.cookingpatterns.Loader.IDataCallback;

public class OmittedDataCallback implements IDataCallback
{
    private String name;
    public OmittedDataCallback(String name)
    {
        this.name = name;
    }

    @Override
    public void onFailure(Exception ex) { Log.e(name, "Failure", ex); }

    @Override
    public void onSuccess(Object result) {
        Log.i(name, "Success");
    }
}
