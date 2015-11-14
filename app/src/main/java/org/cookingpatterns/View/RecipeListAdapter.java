package org.cookingpatterns.View;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.R;

import java.util.ArrayList;
import java.util.List;

public class RecipeListAdapter extends ArrayAdapter<Recipe>
{
    public RecipeListAdapter(Context context, List<Recipe> values) {
        super(context, -1, values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        /*Recipe item = getItem(position);
        RecipeListItem listItem = RecipeListItem.CreateListItem(getContext().getApplicationContext(), item);
        //parent.addView(listItem);*/

        Recipe item = getItem(position);
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.recipe_list_item_view, parent, false);
        RecipeListItem listItem = (RecipeListItem) rowView.findViewById(R.id.listItem);
        listItem.setRecipe(item);

        return rowView;
    }
}
