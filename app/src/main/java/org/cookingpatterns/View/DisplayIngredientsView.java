package org.cookingpatterns.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.R;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;

/**
 * TODO: document your custom view class.
 */
public class DisplayIngredientsView extends LinearLayout {

    private Ingredient ingredient;

    @InjectView(R.id.amount) private TextView AmountView;
    @InjectView(R.id.unit) private TextView UnitView;
    @InjectView(R.id.name) private TextView NameView;

    public DisplayIngredientsView(Context context) {
        super(context);
        inflate(context, R.layout.displayingredientsview, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
    }

    public DisplayIngredientsView(Context context, Ingredient ingr) {
        super(context);
        inflate(context, R.layout.displayingredientsview, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);

        ingredient = ingr;
    }

    public DisplayIngredientsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.displayingredientsview, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
    }

    public DisplayIngredientsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.displayingredientsview, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
    }

    @Override
    public void onFinishInflate() {
        super.onFinishInflate();
        //All injections are available from here
        //in both cases of XML and programmatic creations (see below)
        if(ingredient != null) {
            AmountView.setText(String.format("%.2f", ingredient.getAmount()));
            UnitView.setText(String.format("%s", ingredient.getUnit()));
            NameView.setText(ingredient.getName());
        }
    }
}
