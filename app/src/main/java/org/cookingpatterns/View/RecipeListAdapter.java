package org.cookingpatterns.View;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import org.cookingpatterns.Model.Recipe;

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
        Recipe item = getItem(position);
        RecipeListItem listItem = RecipeListItem.CreateListItem(getContext().getApplicationContext(), item);
        parent.addView(listItem);

        /*LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position]);
        // change the icon for Windows and iPhone
        String s = values[position];
        if (s.startsWith("iPhone")) {
            imageView.setImageResource(R.drawable.no);
        } else {
            imageView.setImageResource(R.drawable.ok);
        }*/

        return listItem;
    }
}
