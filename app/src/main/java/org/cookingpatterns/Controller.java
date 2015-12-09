package org.cookingpatterns;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.cookingpatterns.EventMessages.OnDisplayRecipeClick;
import org.cookingpatterns.EventMessages.OnEditRecipeClick;
import org.cookingpatterns.EventMessages.OnIngredientListResponseEvent;
import org.cookingpatterns.EventMessages.OnNewRecipeClick;
import org.cookingpatterns.EventMessages.OnProvideAllIngredientsEvent;
import org.cookingpatterns.EventMessages.OnProvideSearchResultEvent;
import org.cookingpatterns.EventMessages.OnRecalculatePortionsClick;
import org.cookingpatterns.EventMessages.OnRecipeListResponseEvent;
import org.cookingpatterns.EventMessages.OnRequestAllIngredientsEvent;
import org.cookingpatterns.EventMessages.OnSaveRecipeClick;
import org.cookingpatterns.EventMessages.OnSearchRequestClick;
import org.cookingpatterns.Interfaces.ISearchQueryParser;
import org.cookingpatterns.Loader.AddRecipeLoader;
import org.cookingpatterns.Loader.DataLoader;
import org.cookingpatterns.Loader.DataLoaderManager;
import org.cookingpatterns.Loader.DataResponse;
import org.cookingpatterns.Loader.IngredientLoader;
import org.cookingpatterns.Loader.RecipeLoader;
import org.cookingpatterns.Loader.UpdateRecipeLoader;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.Parsing.EnglishSearchPaser;
import org.cookingpatterns.Parsing.Node;
import org.cookingpatterns.UtilsAndExtentions.OmittedDataCallback;
import org.cookingpatterns.View.DisplayRecipeFragment;
import org.cookingpatterns.View.EditRecipeFragment;
import org.cookingpatterns.View.SearchRecipeFragment;

import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.event.Observes;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_controler)
public class Controller extends RoboActivity
{
    private ISearchQueryParser parser = new EnglishSearchPaser();

//    @Inject
//    private EventManager eventManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Controller", "onCreate");

        if(savedInstanceState == null || !savedInstanceState.getBoolean("HasFragment"))
        {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragmentContainer, SearchRecipeFragment.CreateFragment());
            transaction.commit();
        }

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.i("Controller", "onBackStackChanged");
            }
        });
    }

    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("HasFragment", true);

        Log.i("Controller", "onSaveInstanceState");
    }

    private void HandleNewRecipeClicked(@Observes OnNewRecipeClick event) {
        Log.i("Controller", "OnNewRecipeClicked");
        Fragment fragment = EditRecipeFragment.CreateFragment(new Recipe(), true);
        switchFragment(fragment);
    }

    private void HandleSaveRecipeClicked(@Observes OnSaveRecipeClick event) {
        Log.i("Controller", "OnSaveRecipeClicked");
        getFragmentManager().popBackStack();

        Bundle args = new Bundle();
        args.putSerializable("recipe", event.getRecipe());

        if(event.getIsNew()) {
            DataLoader loader = new AddRecipeLoader(this, args);
            DataLoaderManager.start(getLoaderManager(), DataLoaderManager.ADDRECIPE_LOADER_ID, loader, new OmittedDataCallback("Controller SaveRecipe New"));
        }
        else
        {
            DataLoader loader = new UpdateRecipeLoader(this, args);
            DataLoaderManager.start(getLoaderManager(), DataLoaderManager.UPDATERECIPE_LOADER_ID, loader, new OmittedDataCallback("Controller SaveRecipe Edit"));
        }
    }

    private void HandleEditRecipeClicked(@Observes OnEditRecipeClick event) {
        Log.i("Controller", "OnEditRecipeClicked");
        Fragment fragment = EditRecipeFragment.CreateFragment(event.getRecipe(), false);
        switchFragment(fragment);
    }

    private void HandleDisplayRecipeClicked(@Observes OnDisplayRecipeClick event) {
        Log.i("Controller", "OnDisplayRecipeClicked");
        Fragment fragment = DisplayRecipeFragment.CreateFragment(event.getRecipe());
        switchFragment(fragment);
    }

    private void HandleSearchRequestClicked(@Observes OnSearchRequestClick event) {
        Log.i("Controller", "OnSearchRequestClicked");

        Bundle args = new Bundle();
        if(event.GetQuery() != null && !event.GetQuery().isEmpty()) {
            Node root = parser.ParseString(event.GetQuery());
            args.putSerializable("searchTree", root);
        }

        DataLoader loader = new RecipeLoader(this, args);
        DataLoaderManager.start(getLoaderManager(), DataLoaderManager.RECIPE_LOADER_ID, loader, new OmittedDataCallback("Controller SearchRequest"));
    }

    private void HandleRequestAllIngredientsEvent(@Observes OnRequestAllIngredientsEvent event) {
        Log.i("Controller", "OnRequestAllIngredientsEvent");

        DataLoader loader = new IngredientLoader(this, null);
        DataLoaderManager.start(getLoaderManager(), DataLoaderManager.INGREDIENT_LOADER_ID, loader, new OmittedDataCallback("Controller Request All Ingredients"));
    }

    private void HandleIngredientListResponseEvent(@Observes OnIngredientListResponseEvent event)
    {
        Log.i("Loader", "OnIngredientListResponseEvent");
        DataResponse<List<Ingredient>> result = event.getIngredientList();
        if(!result.hasError()) {
            eventManager.fire(new OnProvideAllIngredientsEvent(result.getResult()));
        }
        else
        {
            Log.i("Loader", "OnIngredientListResponseEvent Failed");
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "Could not load list of ingredients.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void HandleRecipeListLoadedEvent(@Observes OnRecipeListResponseEvent event)
    {
        Log.i("Loader", "OnRecipeListResponseEvent");
        DataResponse<List<Recipe>> result = event.getRecipeList();
        if(!result.hasError()) {
            eventManager.fire(new OnProvideSearchResultEvent(result.getResult()));
        }
        else
        {
            Log.i("Loader", "OnRecipeListResponseEvent Failed");
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "Could not load recipe.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void HandleRecalculatePortionsClicked(@Observes OnRecalculatePortionsClick event) {
        Log.i("Controller","OnUpdateRecipeDataEvent");
        // start Intent for the corresponding gauge
    }

    private void switchFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.addToBackStack(null);

        transaction.commit();
    }
}
