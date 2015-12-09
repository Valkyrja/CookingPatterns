package org.cookingpatterns.View;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.inject.Inject;

import org.cookingpatterns.EventMessages.OnIngredientListResponseEvent;
import org.cookingpatterns.EventMessages.OnProvideAllIngredientsEvent;
import org.cookingpatterns.EventMessages.OnRequestAllIngredientsEvent;
import org.cookingpatterns.EventMessages.OnSaveRecipeClick;
import org.cookingpatterns.Loader.DataResponse;
import org.cookingpatterns.Model.ImageAsUri;
import org.cookingpatterns.Model.ImageInfo;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.R;
import org.cookingpatterns.UtilsAndExtentions.Utils;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;
import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.inject.InjectView;


public class EditRecipeFragment extends Fragment
{
    public static final int PICTURE_SELECTED    =  1;
    public static final int RESULT_OK           = -1;

    @InjectView(R.id.recipename) private EditText Name;
    @InjectView(R.id.category)   private EditText Categoty;
    @InjectView(R.id.time)       private EditText Time;
    @InjectView(R.id.rating)     private RatingBar Rating;
    @InjectView(R.id.portion)    private EditText Portion;

    @InjectView(R.id.picture)           private ImageView Picture;
    @InjectView(R.id.listofingredients) private ListView Ingredients;
    @InjectView(R.id.preparation)       private EditText Preparation;

    @InjectView(R.id.submit)            private FloatingActionButton SubmitButton;
    @InjectView(R.id.addingredient)     private ImageButton AddIngredient;

    @Inject
    private EventManager eventManager;

    private Recipe RecipeToBeDisplayed;
    private EditIngredientsListAdapter IngdientsAdapter;
    private boolean CreateNewRecipe;

    public static EditRecipeFragment CreateFragment(Recipe recipe, boolean createNewRecipe)
    {
        EditRecipeFragment fragment = new EditRecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable("Recipe", recipe);
        args.putBoolean("CreateNewRecipe", createNewRecipe);
        fragment.setArguments(args);
        return fragment;
    }

    public EditRecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectMembersWithoutViews(this);
        if (getArguments() != null) {
            RecipeToBeDisplayed  = (Recipe)getArguments().getSerializable("Recipe");
            CreateNewRecipe  = (boolean)getArguments().getBoolean("CreateNewRecipe");
        }

        eventManager.fire(new OnRequestAllIngredientsEvent());
        IngdientsAdapter = new EditIngredientsListAdapter(getActivity().getApplication(), new ArrayList<Ingredient>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_edit_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectViewMembers(this);

        UpdateDataInView();

        SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("EditRecipeFragment","Save");
                RecipeToBeDisplayed = ExtractDataFromView();
                eventManager.fire(new OnSaveRecipeClick(RecipeToBeDisplayed, CreateNewRecipe));
            }
        });

        //http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically/2636538#2636538
        Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.i("EditRecipeFragment", "Image");
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_SELECTED);
            }
        });

        AddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Log.i("EditRecipeFragment", "AddIngredient");
                IngdientsAdapter.add(new Ingredient());
                IngdientsAdapter.notifyDataSetChanged();
            }
        });

        Ingredients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("EditRecipeFragment", "OnDeleteIngredient");
                Utils.HapticFeedbackShort(getActivity());
                IngdientsAdapter.remove(IngdientsAdapter.getItem(position));
                return true;
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
            if(image != null) {
                Picture.setImageURI((Uri) image.GetImage());
                Picture.setTag(image.GetImagePath());
            }
            IngdientsAdapter.setAll(RecipeToBeDisplayed.getIngredients());
        }
        Ingredients.setAdapter(IngdientsAdapter);
    }

    private Recipe ExtractDataFromView()
    {
        if(RecipeToBeDisplayed == null)
        {
            RecipeToBeDisplayed = new Recipe();
        }

        RecipeToBeDisplayed.setName(Name.getText().toString());
        RecipeToBeDisplayed.setCategory(Categoty.getText().toString());
        RecipeToBeDisplayed.setTime(Time.getText().toString());
        RecipeToBeDisplayed.setDescription(Preparation.getText().toString());

        RecipeToBeDisplayed.setRating((int) Rating.getRating());
        RecipeToBeDisplayed.setPortions(Integer.parseInt(Portion.getText().toString()));

        RecipeToBeDisplayed.setImage(new ImageAsUri((String) Picture.getTag()));
        RecipeToBeDisplayed.setIngredients(IngdientsAdapter.ExtractIngredients());

        return RecipeToBeDisplayed;
    }

    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        Log.i("EditRecipeFragment", "onViewStateRestored");
        if(savedInstanceState != null)
        {
            RecipeToBeDisplayed  = (Recipe)savedInstanceState.getSerializable("Recipe");
            CreateNewRecipe  = (boolean)savedInstanceState.getBoolean("CreateNewRecipe");
        }
    }

    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        RecipeToBeDisplayed = ExtractDataFromView();
        outState.putSerializable("Recipe", RecipeToBeDisplayed);
        outState.putBoolean("CreateNewRecipe", CreateNewRecipe);

        Log.i("EditRecipeFragment", "onSaveInstanceState");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("EditRecipeFragment", "onActivityResult");
        if (resultCode == RESULT_OK) {
            if (requestCode == PICTURE_SELECTED) {
                try {
                    String path = data.getData().toString();
                    RecipeToBeDisplayed.setImage(new ImageAsUri(path));
                    Picture.setImageURI((Uri)RecipeToBeDisplayed.getImage().GetImage());
                    Picture.setTag(RecipeToBeDisplayed.getImage().GetImagePath());
                } catch (Exception e) {

                }
            }
        }
    }

    private void HandleProvideAllIngredientsEvent(@Observes OnProvideAllIngredientsEvent event) {
        Log.i("EditRecipeFragment", "OnProvideAllIngredientsEvent");
        IngdientsAdapter.propagateSelectFromList(event.GetResult());
    }
}
