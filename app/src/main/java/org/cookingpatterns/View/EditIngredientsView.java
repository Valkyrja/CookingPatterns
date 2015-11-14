package org.cookingpatterns.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.cookingpatterns.Model.ImageAsDrawable;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.Model.UnitOfMeasure;
import org.cookingpatterns.R;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;

/**
 * TODO: document your custom view class.
 */
public class EditIngredientsView extends LinearLayout
{
    private Ingredient ingredient;

    @InjectView(R.id.amount)        private EditText AmountView;
    @InjectView(R.id.unit)          private TextView UnitView;
    @InjectView(R.id.name)          private AutoCompleteTextView NameView;
    @InjectView(R.id.nameSpinner)   private Spinner DropdownNameView;

    public EditIngredientsView(Context context) {
        super(context);
        inflate(context, R.layout.editingredientsview, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
    }

    public EditIngredientsView(Context context, Ingredient ingr) {
        super(context);
        inflate(context, R.layout.editingredientsview, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);

        ingredient = ingr;
    }

    public EditIngredientsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.editingredientsview, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
    }

    public EditIngredientsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.editingredientsview, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if(ingredient != null) {
            AmountView.setText(String.format("%.2f", ingredient.getAmount()));
            UnitView.setText(String.format("%s", ingredient.getUnit()));
            NameView.setText(ingredient.getName());
        }

        String[] list = new String[] { "some 1", "some 2", "some 3" };
        //set auto complete
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, list);
        NameView.setAdapter(adapter);
        //set spinner
        DropdownNameView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                NameView.setText(DropdownNameView.getSelectedItem().toString());
                NameView.dismissDropDown();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                NameView.setText(DropdownNameView.getSelectedItem().toString());
                NameView.dismissDropDown();
            }
        });
    }

    public Ingredient ExtractDataFromView()
    {
        if(ingredient == null)
        {
            ingredient = new Ingredient();
        }
        ingredient.setName(NameView.getText().toString());
        ingredient.setAmount(Double.parseDouble(AmountView.getText().toString()));
        ingredient.setUnit(UnitOfMeasure.valueOf(UnitView.getText().toString()));

        return ingredient;
    }
}
