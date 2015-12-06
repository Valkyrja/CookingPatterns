package org.cookingpatterns.View;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.inject.Inject;

import org.cookingpatterns.EventMessages.OnRecalculatePortionsClick;
import org.cookingpatterns.EventMessages.OnUpdateRecipeDataForViewEvent;
import org.cookingpatterns.Model.ImageInfo;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.R;

import roboguice.RoboGuice;
import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.inject.InjectView;


public class DisplayRecipeFragment extends Fragment
{
    @InjectView(R.id.recipename) private TextView Name;
    @InjectView(R.id.category)   private TextView Categoty;
    @InjectView(R.id.time)       private TextView Time;
    @InjectView(R.id.rating)     private RatingBar Rating;
    @InjectView(R.id.portion)    private EditText Portion;

    @InjectView(R.id.picture)           private ImageView Picture;
    @InjectView(R.id.listofingredients) private LinearLayout Ingredients;
    @InjectView(R.id.preparation)       private TextView Preparation;

    @Inject
    private EventManager eventManager;

    private Recipe RecipeToBeDisplayed;

    public static DisplayRecipeFragment CreateFragment(Recipe recipe) {
        DisplayRecipeFragment fragment = new DisplayRecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable("Recipe", recipe);
        fragment.setArguments(args);
        return fragment;
    }

    public DisplayRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectMembersWithoutViews(this);
        if (savedInstanceState != null) {
            RecipeToBeDisplayed  = (Recipe)savedInstanceState.getSerializable("Recipe");
        }
        else if (getArguments() != null) {
            RecipeToBeDisplayed  = (Recipe)getArguments().getSerializable("Recipe");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_display_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectViewMembers(this);

        UpdateDataInView();

        Portion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    Integer from = RecipeToBeDisplayed.getPortions();
                    Integer to = Integer.parseInt(Portion.getText().toString());
                    eventManager.fire(new OnRecalculatePortionsClick(from, to));
                }
            }
        });
    }

    private void UpdateDataInView()
    {
        if(RecipeToBeDisplayed != null)
        {
            Name.setText(RecipeToBeDisplayed.getName());
            Categoty.setText(RecipeToBeDisplayed.getCategory());
            Time.setText(RecipeToBeDisplayed.getTime());
            Rating.setRating(RecipeToBeDisplayed.getRatingNotNullable());
            Portion.setText(RecipeToBeDisplayed.getPortionsAsString());
            Preparation.setText(RecipeToBeDisplayed.getDescription());

            ImageInfo image = RecipeToBeDisplayed.getImage();
            Picture.setImageDrawable((Drawable) (image != null ? image.GetImage() : null));

            for (Ingredient ingr : RecipeToBeDisplayed.getIngredients())
            {
                DisplayIngredientsView ingrView = new DisplayIngredientsView(getActivity().getApplicationContext(), ingr);
                ingrView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                Ingredients.addView(ingrView);
            }
        }
    }

    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        Log.i("SearchRecipeFragment", "onViewStateRestored");
        if(savedInstanceState != null)
        {
            RecipeToBeDisplayed  = (Recipe)savedInstanceState.getSerializable("Recipe");
        }
    }

    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putSerializable("Recipe", RecipeToBeDisplayed);

        Log.i("SearchRecipeFragment", "onSaveInstanceState");
    }

    private void OnUpdateRecipeDataEvent(@Observes OnUpdateRecipeDataForViewEvent event) {
        Log.i("DisplayRecipeFragment","OnUpdateRecipeDataEvent");
        // start Intent for the corresponding gauge
    }
}
