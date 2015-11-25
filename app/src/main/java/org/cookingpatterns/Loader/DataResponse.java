package org.cookingpatterns.Loader;

/**
 * Created by Andreas on 16.11.2015.
 */
public class DataResponse<D> {

    private Exception mException;
    private D mResult;

    static <D> DataResponse<D> ok(D data){

        DataResponse<D> response = new DataResponse<D>();
        response.mResult = data;

        return  response;
    }

    static <D> DataResponse<D> error(Exception ex){

        DataResponse<D> response = new DataResponse<D>();
        response.mException = ex;

        return  response;
    }

    public boolean hasError() {

        return mException != null;
    }

    public Exception getException() {

        return mException;
    }

    public D getResult() {

        return mResult;
    }
}
