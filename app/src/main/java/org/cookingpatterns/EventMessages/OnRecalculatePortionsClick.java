package org.cookingpatterns.EventMessages;

import org.cookingpatterns.Model.Ingredient;

public class OnRecalculatePortionsClick
{
    private Integer From;
    private Integer To;

    public OnRecalculatePortionsClick(Integer from, Integer to)
    {
        From = from;
        To = to;
    }

    public Integer GetFromPortion()
    {
        return From;
    }
    public Integer GetToPortion()
    {
        return To;
    }

}
