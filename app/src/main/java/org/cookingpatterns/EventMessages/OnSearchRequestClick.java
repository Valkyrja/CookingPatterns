package org.cookingpatterns.EventMessages;

public class OnSearchRequestClick
{
    private String query;

    public OnSearchRequestClick(CharSequence query)
    {
        this.query = query != null ? query.toString() : "";
    }

    public String GetQuery()
    {
        return query;
    }
}
