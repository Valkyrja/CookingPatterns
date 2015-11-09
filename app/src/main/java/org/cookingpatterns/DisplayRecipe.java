package org.cookingpatterns;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DisplayRecipe extends AppCompatActivity
{
    private TextView Name;
    private TextView Categoty;
    private TextView Time;
    private TextView Rating;
    private TextView Portion;

    private ImageView Picture;
    private LinearLayout Ingredients;
    private TextView Preparation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_recipe);

        Name = (TextView) this.findViewById(R.id.recipename);
        Categoty = (TextView) this.findViewById(R.id.category);
        Time = (TextView) this.findViewById(R.id.time);
        Rating = (TextView) this.findViewById(R.id.rating);
        Portion = (TextView) this.findViewById(R.id.portion);

        Picture = (ImageView) this.findViewById(R.id.picture);
        Ingredients = (LinearLayout) this.findViewById(R.id.listofingredients);
        Preparation = (TextView) this.findViewById(R.id.preparation);
    }
}

