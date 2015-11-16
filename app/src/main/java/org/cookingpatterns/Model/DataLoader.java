package org.cookingpatterns.Model;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

/**
 * Created by Andreas on 16.11.2015.
 */
public abstract class DataLoader<D, IDataProvider>  extends AsyncTaskLoader<DataResponse<D>> {


    protected DataResponse<D> response;
    protected final IDataProvider mDataProvider;
    private Bundle mArgs;
    
    public DataLoader(Context context, IDataProvider dataProvider, Bundle args) {
        super(context);
        mDataProvider = dataProvider;
        mArgs = args;
    }

    @Override
    public DataResponse<D> loadInBackground() {

        try {

            final D data = call(mArgs);
            response = DataResponse.ok(data);

        } catch (Exception ex) {

            response = DataResponse.error(ex);
        }

        return response;
    }

    @Override
    protected void onStartLoading() {

        super.onStartLoading();

        if (response != null) {

            deliverResult(response);
        }

        if (takeContentChanged() || response == null) {

            forceLoad();
        }
    }

    @Override
    protected void onReset() { //todo add parameters?

        super.onReset();

        response = null;
    }

    public abstract D call(Bundle args);

}
