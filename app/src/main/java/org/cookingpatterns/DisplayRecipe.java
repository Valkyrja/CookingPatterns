package org.cookingpatterns;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.activity_display_recipe)
public class DisplayRecipe extends RoboActivity
{
    @InjectView(R.id.recipename) private TextView Name;
    @InjectView(R.id.category)   private TextView Categoty;
    @InjectView(R.id.time)       private TextView Time;
    @InjectView(R.id.rating)     private TextView Rating;
    @InjectView(R.id.portion)    private TextView Portion;

    @InjectView(R.id.picture)           private ImageView Picture;
    @InjectView(R.id.listofingredients) private LinearLayout Ingredients;
    @InjectView(R.id.preparation)       private TextView Preparation;

    private Recipe RecipeToBeDisplayed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(RecipeToBeDisplayed != null)
        {
            Name.setText(RecipeToBeDisplayed.getName());
            Categoty.setText(RecipeToBeDisplayed.getCategory());
            Time.setText(RecipeToBeDisplayed.getTime());
            Rating.setText(RecipeToBeDisplayed.getRating());
            Portion.setText(RecipeToBeDisplayed.getPortions());
            Preparation.setText(RecipeToBeDisplayed.getDescription());

            Picture.setImageDrawable((Drawable)RecipeToBeDisplayed.getImage().GetImage());
            for (Ingredient ingr : RecipeToBeDisplayed.getIngredients())
            {
                DisplayIngredientsView ingrView = new DisplayIngredientsView(getApplicationContext(), ingr);
                ingrView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                Ingredients.addView(ingrView);
            }
            Name.setText(RecipeToBeDisplayed.getName());
        }
    }

    public Recipe getRecipe() { return RecipeToBeDisplayed; }
    public void setRecipe(Recipe recipeToBeDisplayed) { RecipeToBeDisplayed = recipeToBeDisplayed; }
}

