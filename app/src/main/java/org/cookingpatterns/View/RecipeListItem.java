package org.cookingpatterns.View;

import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.cookingpatterns.Model.ImageInfo;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.R;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;

/**
 * TODO: document your custom view class.
 */
public class RecipeListItem extends LinearLayout
{
    private Recipe RecipeToBeDisplayed;

    @InjectView(R.id.rating)   private RatingBar RatingView;
    @InjectView(R.id.duration) private TextView TimeView;
    @InjectView(R.id.name)     private TextView NameView;
    @InjectView(R.id.picture)  private ImageView PictureView;

    public RecipeListItem(Context context) {
        super(context);
        inflate(context, R.layout.recipelistitem, this);
        RoboGuice.getInjector(getContext().getApplicationContext()).injectMembers(this);
        RoboGuice.getInjector(getContext().getApplicationContext()).injectViewMembers(this);
    }

    public RecipeListItem(Context context, Recipe recipe) {
        super(context);
        inflate(context, R.layout.recipelistitem, this);
        RoboGuice.getInjector(getContext().getApplicationContext()).injectMembers(this);
        RoboGuice.getInjector(getContext().getApplicationContext()).injectViewMembers(this);

        RecipeToBeDisplayed = recipe;
    }

    public RecipeListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.recipelistitem, this);
        RoboGuice.getInjector(getContext().getApplicationContext()).injectMembers(this);
        RoboGuice.getInjector(getContext().getApplicationContext()).injectViewMembers(this);
    }

    public RecipeListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.recipelistitem, this);
        RoboGuice.getInjector(getContext().getApplicationContext()).injectMembers(this);
        RoboGuice.getInjector(getContext().getApplicationContext()).injectViewMembers(this);
    }

    public static RecipeListItem CreateListItem(Context context, Recipe recipe)
    {
        RecipeListItem item = new RecipeListItem(context, recipe);
        item.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        item.onFinishInflate();
        return item;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Log.w("RecipeListItem", "onFinishInflate --- Called!!!!!!!!!!!!!!!!!!!!!!");

        UpdateDataInView();
    }

    private void UpdateDataInView()
    {
        if(RecipeToBeDisplayed != null)
        {
            NameView.setText(RecipeToBeDisplayed.getName());
            TimeView.setText(RecipeToBeDisplayed.getTime());
            RatingView.setRating(RecipeToBeDisplayed.getRating());

            ImageInfo image = RecipeToBeDisplayed.getImage();
            PictureView.setImageDrawable((Drawable)(image != null ? image.GetImage() : null));
        }
    }

    public void setRecipe(Recipe recipeToBeDisplayed) {
        RecipeToBeDisplayed = recipeToBeDisplayed;
        UpdateDataInView();
    }
}
