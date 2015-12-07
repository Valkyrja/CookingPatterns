package org.cookingpatterns.DAL.SqlLite;

/**
 * Created by Andreas on 20.11.2015.
 */
public class SqlLiteColumn {
    public final String Name;
    public final SqlLiteDataTypes Type;

    public SqlLiteColumn(String name, SqlLiteDataTypes type) {
        Name = name;
        Type = type;
    }
}
