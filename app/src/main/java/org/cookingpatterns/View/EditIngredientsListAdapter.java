package org.cookingpatterns.View;


import android.app.Fragment;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import org.cookingpatterns.EventMessages.OnIngredientListResponseEvent;
import org.cookingpatterns.EventMessages.OnNewRecipeClick;
import org.cookingpatterns.Loader.DataResponse;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import roboguice.event.Observes;

public class EditIngredientsListAdapter extends ArrayAdapter<Ingredient>
{
    private ArrayList<EditIngredientsView> views;
    private List<Ingredient> existingSelection;

    public EditIngredientsListAdapter(Context context, List<Ingredient> values) {
        super(context, -1, values);
        views = new ArrayList<EditIngredientsView>();
        existingSelection = new ArrayList<Ingredient>();
    }

    private void OnIngredientListResponse(@Observes OnIngredientListResponseEvent event) {
        Log.i("EditIngrListAdapter", "OnIngredientListResponse");
        DataResponse<List<Ingredient>> result = event.getIngredientList();
        if(!result.hasError())
            existingSelection = result.getResult();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Ingredient item = getItem(position);
        EditIngredientsView ingrView = new EditIngredientsView(getContext(), item, existingSelection);
        ingrView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        //parent.addView(ingrView);
        views.add(ingrView);

        return ingrView;
    }

    public void setAll(Collection<? extends Ingredient> collection) {
        super.clear();
        super.addAll(collection);
    }

    /**
     * Adds the specified items at the end of the array.
     *
     * @param items The items to add at the end of the array.
     */
    public void setAll(Ingredient ... items) {
        super.clear();
        super.addAll(items);
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
