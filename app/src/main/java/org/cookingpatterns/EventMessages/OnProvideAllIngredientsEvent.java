package org.cookingpatterns.EventMessages;

import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;

import java.util.List;

public class OnProvideAllIngredientsEvent
{
    private List<Ingredient> list;

    public OnProvideAllIngredientsEvent(List<Ingredient> list)
    {
        this.list = list;
    }

    public List<Ingredient> GetResult()
    {
        return list;
    }
}
