package org.cookingpatterns;

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

/**
 * TODO: document your custom view class.
 */
public class EditIngredientsView extends LinearLayout {
    private String Unit;
    private float Amount;
    private String Name;

    private EditText AmountView;
    private TextView UnitView;
    private AutoCompleteTextView NameView;
    private Spinner DropdownNameView;

    public EditIngredientsView(Context context) {
        super(context);
        initView(context);
        init(null, 0);
    }

    public EditIngredientsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        init(attrs, 0);
    }

    public EditIngredientsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
        init(attrs, defStyle);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.editingredientsview, this);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.EditIngredientsView, defStyle, 0);

        Unit = a.getString(R.styleable.EditIngredientsView_EIUnit);
        Amount = a.getFloat(R.styleable.EditIngredientsView_EIAmount, 0);
        Name = a.getString(R.styleable.EditIngredientsView_EIName);

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        AmountView = (EditText) this.findViewById(R.id.amount);
        AmountView.setText(String.format("%.2f", Amount));
        UnitView = (TextView) this.findViewById(R.id.unit);
        UnitView.setText(Unit);
        NameView = (AutoCompleteTextView) this.findViewById(R.id.edit_ip);
        NameView.setText(Name);
        DropdownNameView = (Spinner) this.findViewById(R.id.spinner_ip);

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
}
