package org.cookingpatterns;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * TODO: document your custom view class.
 */
public class DisplayIngredientsView extends LinearLayout {
    private String Unit;
    private float Amount;
    private String Name;

    private TextView AmountView;
    private TextView UnitView;
    private TextView NameView;

    public DisplayIngredientsView(Context context) {
        super(context);
        initView(context);
        init(null, 0);
    }

    public DisplayIngredientsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        init(attrs, 0);
    }

    public DisplayIngredientsView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
        init(attrs, defStyle);
    }

    private void initView(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.displayingredientsview, this);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DisplayIngredientsView, defStyle, 0);

        Unit = a.getString(R.styleable.DisplayIngredientsView_DIUnit);
        Amount = a.getFloat(R.styleable.DisplayIngredientsView_DIAmount, 0);
        Name = a.getString(R.styleable.DisplayIngredientsView_DIName);

        a.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        AmountView = (TextView) this.findViewById(R.id.amount);
        AmountView.setText(String.format("%.2f", Amount));
        UnitView = (TextView) this.findViewById(R.id.unit);
        UnitView.setText(Unit);
        NameView = (TextView) this.findViewById(R.id.name);
        NameView.setText(Name);
    }
}
