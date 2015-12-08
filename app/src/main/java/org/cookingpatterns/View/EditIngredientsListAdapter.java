package org.cookingpatterns.View;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import org.cookingpatterns.Model.Ingredient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EditIngredientsListAdapter extends ArrayAdapter<Ingredient>
{
    private Map<Integer,EditIngredientsView> views;
    private List<Ingredient> existingSelection;

    public EditIngredientsListAdapter(Context context, List<Ingredient> values) {
        super(context, -1, values);
        views = new LinkedHashMap<Integer,EditIngredientsView>();
        existingSelection = new ArrayList<Ingredient>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        EditIngredientsView ingrView = null;

        if(views.containsKey(position))
        {
            ingrView = views.get(position);
        }
        else
        {
            Ingredient item = getItem(position);
            ingrView = new EditIngredientsView(getContext(), item, existingSelection);
            ingrView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            views.put(position, ingrView);
        }

        return ingrView;
    }

    public void setAll(Collection<? extends Ingredient> collection) {
        super.clear();
        super.addAll(collection);
    }

    public void setAll(Ingredient ... items) {
        super.clear();
        super.addAll(items);
    }

    public void propagateSelectFromList(List<Ingredient> collection) {
        existingSelection.addAll(collection);
    }

    @Override
    public void remove(Ingredient object) {
        int position = super.getPosition(object);
        if(views.containsKey(position))
        {
            views.remove(position);
        }
        super.remove(object);
    }

    @Override
    public void clear() {
        super.clear();
        views.clear();
    }

    public List<Ingredient> ExtractIngredients()
    {
        List<Ingredient> list = new  ArrayList<Ingredient>();
        for (EditIngredientsView ingrView : views.values()) {
            list.add(ingrView.ExtractDataFromView());
        }
        return list;
    }
}
