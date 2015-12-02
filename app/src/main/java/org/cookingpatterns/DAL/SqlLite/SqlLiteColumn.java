package org.cookingpatterns.DAL.SqlLite;

/**
 * Created by Andreas on 20.11.2015.
 */
public class SqlLiteColumn {
    public String Name;
    public SqlLiteDataTypes Type;

    public SqlLiteColumn(String name, SqlLiteDataTypes type) {
        Name = name;
        Type = type;
    }
}
