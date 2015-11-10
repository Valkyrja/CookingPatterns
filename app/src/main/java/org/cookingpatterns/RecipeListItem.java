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

/**
 * TODO: document your custom view class.
 */
public class RecipeListItem extends LinearLayout
{
    private String Time;
    private String Name;
    private int Rating;
    private Drawable Picture;

    private RatingBar RatingView;
    private TextView TimeView;
    private TextView NameView;
    private ImageView PictureView;

    public RecipeListItem(Context context) {
        super(context);
        initView(context);
        init(null, 0);
    }

    public RecipeListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        init(attrs, 0);
    }

    public RecipeListItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
        init(attrs, defStyle);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.recipelistitem, this);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RecipeListItem, defStyle, 0);

        Time = a.getString(R.styleable.RecipeListItem_RLITime);
        Name = a.getString(R.styleable.RecipeListItem_RLIName);
        Rating = a.getInteger(R.styleable.RecipeListItem_RLIRating, -1);
        Picture = a.getDrawable(R.styleable.RecipeListItem_RLIPicture);

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        RatingView = (RatingBar) this.findViewById(R.id.rating);
        RatingView.setRating((float)Rating);
        TimeView = (TextView) this.findViewById(R.id.duration);
        TimeView.setText(Time);
        NameView = (TextView) this.findViewById(R.id.name);
        NameView.setText(Name);
        PictureView = (ImageView) this.findViewById(R.id.picture);
        PictureView.setImageDrawable(Picture);
    }
}
