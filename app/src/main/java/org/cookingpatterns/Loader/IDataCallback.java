package org.cookingpatterns.Loader;

/**
 * Created by Andreas on 16.11.2015.
 */
public interface IDataCallback<D> {

    void onFailure(Exception ex);

    void onSuccess(D result);
}