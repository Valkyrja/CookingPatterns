package org.cookingpatterns.DAL;

/**
 * Created by Andreas on 11.11.2015.
 */
public class DataProviderFactory {
    public static DataProvider createDataProvider(String type)
    {
        switch (type) {
            case "SQLLite":
                return new SqlLiteDataProvider();
            default:
                return new SqlLiteDataProvider();
        }
    }
}
