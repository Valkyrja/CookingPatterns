package org.cookingpatterns.View;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;

import java.util.ArrayList;
import java.util.List;

public class EditIngdientsListAdapter extends ArrayAdapter<Ingredient>
{
    private ArrayList<EditIngredientsView> views;

    public EditIngdientsListAdapter(Context context, List<Ingredient> values) {
        super(context, -1, values);
        views = new ArrayList<EditIngredientsView>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Ingredient item = getItem(position);
        EditIngredientsView ingrView = new EditIngredientsView(getContext(), item);
        ingrView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        parent.addView(ingrView);
        views.add(ingrView);

        return ingrView;
    }

    public List<Ingredient> ExtractIngredients()
    {
        List<Ingredient> list = new  ArrayList<Ingredient>();
        for (EditIngredientsView ingrView : views )
        {
            list.add(ingrView.ExtractDataFromView());
        }
        return list;
    }
}
