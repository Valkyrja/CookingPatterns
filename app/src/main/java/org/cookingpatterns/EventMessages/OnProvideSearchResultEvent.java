package org.cookingpatterns.EventMessages;

import org.cookingpatterns.Model.Recipe;

import java.util.List;

public class OnProvideSearchResultEvent
{
    private List<Recipe> list;

    public OnProvideSearchResultEvent(List<Recipe> list)
    {
        this.list = list;
    }

    public List<Recipe> GetResult()
    {
        return list;
    }
}
