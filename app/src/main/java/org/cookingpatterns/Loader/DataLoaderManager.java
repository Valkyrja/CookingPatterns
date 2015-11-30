package org.cookingpatterns.Loader;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;

/**
 * Created by Andreas on 16.11.2015.
 */

public class DataLoaderManager {

    public static final int RECIPE_LOADER_ID = 1;
    public static final int INGREDIENT_LOADER_ID = 2;
    public static final int ADDRECIPE_LOADER_ID = 3;
    public static final int UPDATERECIPE_LOADER_ID = 4;
    public static final int ADDINGREDIENT_LOADER_ID = 5;

    //TODO automatic get id ???
   /* public static int GetLoaderId(DataLoader loader)
    {

    }*/

    public static <D> void init(final LoaderManager manager, final int loaderId,
                                final DataLoader<D> loader, final IDataCallback<D> callback) {

        //TODO or restart?
        manager.initLoader(loaderId, Bundle.EMPTY, new LoaderCallbacks<DataResponse<D>>() {

            @Override
            public Loader<DataResponse<D>> onCreateLoader(int id, Bundle args) {

                return loader;
            }

            @Override
            public void onLoadFinished(Loader<DataResponse<D>> loader, DataResponse<D> data) {

                if (data.hasError()) {

                    callback.onFailure(data.getException());

                } else {

                    callback.onSuccess(data.getResult());
                }
            }

            @Override
            public void onLoaderReset(Loader<DataResponse<D>> loader) {

                //Nothing to do here
            }
        });
    }
}