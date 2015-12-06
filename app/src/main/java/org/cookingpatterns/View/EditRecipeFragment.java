package org.cookingpatterns.View;

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
import android.widget.ListView;
import android.widget.RatingBar;

import com.google.inject.Inject;

import org.cookingpatterns.EventMessages.OnSaveRecipeClick;
import org.cookingpatterns.Loader.DataLoader;
import org.cookingpatterns.Loader.DataLoaderManager;
import org.cookingpatterns.Loader.IDataCallback;
import org.cookingpatterns.Loader.IngredientLoader;
import org.cookingpatterns.Loader.RecipeLoader;
import org.cookingpatterns.Model.ImageAsDrawable;
import org.cookingpatterns.Model.ImageInfo;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
        if (savedInstanceState != null) {
            RecipeToBeDisplayed  = (Recipe)savedInstanceState.getSerializable("Recipe");
            CreateNewRecipe  = (boolean)savedInstanceState.getBoolean("CreateNewRecipe");
        }
        else if (getArguments() != null) {
            RecipeToBeDisplayed  = (Recipe)getArguments().getSerializable("Recipe");
            CreateNewRecipe  = (boolean)getArguments().getBoolean("CreateNewRecipe");
        }

        IngdientsAdapter = new EditIngredientsListAdapter(getActivity().getApplication(), new ArrayList<Ingredient>());

        DataLoader loader = new IngredientLoader(getActivity(), null); //TODO add search parameters
        DataLoaderManager.init(getLoaderManager(), DataLoaderManager.INGREDIENT_LOADER_ID, loader, new IDataCallback() {
            @Override
            public void onFailure(Exception ex) { Log.i("EditRecipeFragment", "IngredientLoaderFailure"); }

            @Override
            public void onSuccess(Object result) { Log.i("EditRecipeFragment", "IngredientLoaderSuccess"); }
        });
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
                Picture.setImageDrawable((Drawable)image.GetImage());
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
        RecipeToBeDisplayed.setPortions(Integer.getInteger(Portion.getText().toString()));

        RecipeToBeDisplayed.setImage(new ImageAsDrawable((String) Picture.getTag()));
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
                String path = getFullPath(data.getData());
                try {

                    InputStream inputStream = getActivity().getContentResolver().openInputStream(data.getData());
                    Drawable yourDrawable = Drawable.createFromStream(inputStream, data.getData().toString());
                    String some = data.getData().toString();
                    Uri more = Uri.parse(some);
                    int comp = more.compareTo(data.getData());
                } catch (FileNotFoundException e) {

                }

                if(path != null) {
                    RecipeToBeDisplayed.setImage(new ImageAsDrawable(path));
                    Picture.setImageURI(data.getData());
                    //Picture.setImageDrawable((Drawable) RecipeToBeDisplayed.getImage().GetImage());
                    Picture.setTag(RecipeToBeDisplayed.getImage().GetImagePath());
                }
            }
        }
    }

    public String getFullPath(Uri uri)
    {
        if( uri == null ) {
            return null;
        }

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String result = cursor.getString(column_index);
            return result == null ? uri.getPath() : result;
        }
        return uri.getPath();
    }
}
