package org.cookingpatterns.View;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.inject.Inject;

import org.cookingpatterns.EventMessages.OnNewRecipeClick;
import org.cookingpatterns.EventMessages.OnSaveRecipeClick;
import org.cookingpatterns.Model.ImageAsDrawable;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.R;

import java.util.ArrayList;

import roboguice.RoboGuice;
import roboguice.event.EventManager;
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
    private EditIngdientsListAdapter IngdientsAdapter;

    public static EditRecipeFragment CreateFragment(Recipe recipe)
    {
        EditRecipeFragment fragment = new EditRecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable("Recipe", recipe);
        fragment.setArguments(args);
        return fragment;
    }

    public EditRecipeFragment() {
        // Required empty public constructor
        IngdientsAdapter = new EditIngdientsListAdapter(getActivity().getApplication(), new ArrayList<Ingredient>());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectMembersWithoutViews(this);
        if (getArguments() != null) {
            RecipeToBeDisplayed  = (Recipe)savedInstanceState.getSerializable("Recipe");
        }
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
                RecipeToBeDisplayed = ExtractDataFromView();
                eventManager.fire(new OnSaveRecipeClick(RecipeToBeDisplayed));
            }
        });

        //http://stackoverflow.com/questions/2169649/get-pick-an-image-from-androids-built-in-gallery-app-programmatically/2636538#2636538
        Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICTURE_SELECTED);
            }
        });

        AddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                RecipeToBeDisplayed.addIngredient(new Ingredient());
                IngdientsAdapter.notifyDataSetChanged();
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
            Rating.setRating(RecipeToBeDisplayed.getRating());
            Portion.setText(RecipeToBeDisplayed.getPortions());
            Preparation.setText(RecipeToBeDisplayed.getDescription());

            Picture.setImageDrawable((Drawable) RecipeToBeDisplayed.getImage().GetImage());
            IngdientsAdapter.addAll(RecipeToBeDisplayed.getIngredients());
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
        RecipeToBeDisplayed.setPortions(Integer.getInteger(Portion.getText().toString()));

        RecipeToBeDisplayed.setImage(new ImageAsDrawable((String) Picture.getTag()));
        RecipeToBeDisplayed.setIngredients(IngdientsAdapter.ExtractIngredients());

        return RecipeToBeDisplayed;
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

        RecipeToBeDisplayed = ExtractDataFromView();
        outState.putSerializable("Recipe", RecipeToBeDisplayed);

        Log.i("SearchRecipeFragment", "onSaveInstanceState");
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == PICTURE_SELECTED) {
                Uri selectedImageUri = data.getData();
                getPath(selectedImageUri);
            }
        }
    }

    public String getPath(Uri uri)
    {
        // just some safety built in
        if( uri == null ) {
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }
}
