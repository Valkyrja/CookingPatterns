package org.cookingpatterns;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;

/**
 * TODO: document your custom view class.
 */
public class RecipeListItem extends LinearLayout
{
    //private String Time;
    //private String Name;
    //private int Rating;
    //private Drawable Picture;
    private Recipe RecipeToBeDisplayed;

    @InjectView(R.id.rating)   private RatingBar RatingView;
    @InjectView(R.id.duration) private TextView TimeView;
    @InjectView(R.id.name)     private TextView NameView;
    @InjectView(R.id.picture)  private ImageView PictureView;

    public RecipeListItem(Context context) {
        super(context);
        inflate(context, R.layout.recipelistitem, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
    }

    public RecipeListItem(Context context, Recipe recipe) {
        super(context);
        inflate(context, R.layout.recipelistitem, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);

        RecipeToBeDisplayed = recipe;
    }

    public RecipeListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.recipelistitem, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
    }

    public RecipeListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.recipelistitem, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RecipeListItem, defStyle, 0);

        //Time = a.getString(R.styleable.RecipeListItem_RLITime);
        //Name = a.getString(R.styleable.RecipeListItem_RLIName);
        //Rating = a.getInteger(R.styleable.RecipeListItem_RLIRating, -1);
        //Picture = a.getDrawable(R.styleable.RecipeListItem_RLIPicture);

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(RecipeToBeDisplayed != null)
        {
            NameView.setText(RecipeToBeDisplayed.getName());
            TimeView.setText(RecipeToBeDisplayed.getTime());
            RatingView.setRating(RecipeToBeDisplayed.getRating());

            PictureView.setImageDrawable((Drawable)RecipeToBeDisplayed.getImage().GetImage());
        }
    }
}
