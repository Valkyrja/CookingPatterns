package org.cookingpatterns.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.UnitOfMeasure;
import org.cookingpatterns.R;
import org.cookingpatterns.UtilsAndExtentions.Utils;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;
import roboguice.inject.InjectView;

/**
 * TODO: document your custom view class.
 */
public class EditIngredientsView extends LinearLayout
{
    private Ingredient ingredient;
    private List<Ingredient> existingSelection;

    @InjectView(R.id.amount)        private EditText AmountView;
    @InjectView(R.id.unit)          private Spinner UnitView;
    @InjectView(R.id.name)          private AutoCompleteTextView NameView;
    //InjectView(R.id.nameSpinner)   private Spinner DropdownNameView;

    public EditIngredientsView(Context context) {
        super(context);
        inflate(context, R.layout.editingredientsview, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
        RoboGuice.getInjector(getContext()).injectViewMembers(this);

        existingSelection = new ArrayList<Ingredient>();
    }

    public EditIngredientsView(Context context, Ingredient ingr, List<Ingredient> selectionList) {
        super(context);
        inflate(context, R.layout.editingredientsview, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
        RoboGuice.getInjector(getContext()).injectViewMembers(this);

        ingredient = ingr;
        existingSelection = selectionList;
        onFinishInflate();
    }

    public EditIngredientsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflate(context, R.layout.editingredientsview, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
        RoboGuice.getInjector(getContext()).injectViewMembers(this);
    }

    public EditIngredientsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.editingredientsview, this);
        RoboGuice.getInjector(getContext()).injectMembers(this);
        RoboGuice.getInjector(getContext()).injectViewMembers(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line, Utils.getNames(UnitOfMeasure.class));
        UnitView.setAdapter(unitAdapter);

        if(existingSelection.isEmpty())
        {
            Ingredient ingr = new Ingredient();
            ingr.setUnit(UnitOfMeasure.pcs);
            ingr.setName("Eggs");
            existingSelection.add(ingr);
        }

        //set auto complete
        ArrayAdapter<Ingredient> adapter = new ArrayAdapter<Ingredient>(getContext(), android.R.layout.simple_dropdown_item_1line, existingSelection);
        NameView.setAdapter(adapter);
        NameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) {
                String name = s != null ? s.toString() : "";
                Ingredient ingr = getExistingIngredientByName(name);
                boolean isknownIngredient = (ingr != null);
                UnitView.setEnabled(isknownIngredient);
                UnitView.setClickable(isknownIngredient);
                if(isknownIngredient)
                    UnitView.setSelection(ingr.getUnit().ordinal());
            }
        });
        NameView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Ingredient selected = (Ingredient)parent.getSelectedItem();
                UnitView.setSelection(selected.getUnit().ordinal());
                //NameView.setText(selected.getName());
                //NameView.dismissDropDown();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Ingredient selected = (Ingredient)parent.getSelectedItem();
                UnitView.setSelection(selected.getUnit().ordinal());
                //NameView.setText(selected.getName());
                //NameView.dismissDropDown();
            }
        });
        //set spinner
        /*DropdownNameView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Ingredient selected = (Ingredient)DropdownNameView.getSelectedItem();
                UnitView.setSelection(selected.getUnit().ordinal());
                NameView.setText(selected.getName());
                NameView.dismissDropDown();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Ingredient selected = (Ingredient)DropdownNameView.getSelectedItem();
                UnitView.setSelection(selected.getUnit().ordinal());
                NameView.setText(selected.getName());
                NameView.dismissDropDown();
            }
        });*/

        if (ingredient != null) {
            AmountView.setText(String.format("%.2f", ingredient.getAmount()));
            UnitView.setSelection(ingredient.getUnit().ordinal());
            NameView.setText(ingredient.getName());
        }
    }

    private Ingredient getExistingIngredientByName( String name)
    {
        for(Ingredient ingr : existingSelection)
        {
            if(ingr.getName().equalsIgnoreCase(name))
            {
                return ingr;
            }
        }
        return null;
    }

    public Ingredient ExtractDataFromView()
    {
        String name = NameView.getText() != null ? NameView.getText().toString() : "";
        Ingredient ingr = getExistingIngredientByName(name);
        ingredient = (ingr != null ? new Ingredient(ingr.getId()) : (ingredient == null ? new Ingredient() : ingredient));
        ingredient.setName(name);
        ingredient.setAmount(Double.parseDouble(AmountView.getText() != null && AmountView.getText().toString() != "" ? AmountView.getText().toString() : "0"));
        ingredient.setUnit(UnitOfMeasure.valueOf((String) UnitView.getSelectedItem()));

        return ingredient;
    }
}
