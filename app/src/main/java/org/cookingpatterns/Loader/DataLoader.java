package org.cookingpatterns.Loader;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;

import com.google.inject.Inject;

import org.cookingpatterns.DAL.IDataProvider;

import roboguice.RoboGuice;
import roboguice.event.EventManager;

/**
 * Created by Andreas on 16.11.2015.
 */
public abstract class DataLoader<D>  extends AsyncTaskLoader<DataResponse<D>> {

    @Inject
    protected EventManager eventManager;

    protected DataResponse<D> response;
    private Bundle mArgs;
    protected Context mContext;

    public DataLoader(Context context, Bundle args) {
        super(context);
        mArgs = args;
        mContext = context;
        RoboGuice.getInjector(context).injectMembers(this);
    }

    @Override
    public DataResponse<D> loadInBackground() {

        try {

            final D data = call(mArgs);
            response = DataResponse.ok(data);

        } catch (Exception ex) {

            response = DataResponse.error(ex);
        }

        sendEvent(response);
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

    public abstract void sendEvent(DataResponse<D> data);

}
