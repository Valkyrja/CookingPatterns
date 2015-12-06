package org.cookingpatterns;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import org.cookingpatterns.EventMessages.OnDisplayRecipeClick;
import org.cookingpatterns.EventMessages.OnEditRecipeClick;
import org.cookingpatterns.EventMessages.OnNewRecipeClick;
import org.cookingpatterns.EventMessages.OnProvideSearchResultEvent;
import org.cookingpatterns.EventMessages.OnRecalculatePortionsClick;
import org.cookingpatterns.EventMessages.OnRecipeListResponseEvent;
import org.cookingpatterns.EventMessages.OnSaveRecipeClick;
import org.cookingpatterns.EventMessages.OnSearchRequestClick;
import org.cookingpatterns.Interfaces.ILiteralNode;
import org.cookingpatterns.Interfaces.ISearchQueryParser;
import org.cookingpatterns.Loader.AddIngredientLoader;
import org.cookingpatterns.Loader.AddRecipeLoader;
import org.cookingpatterns.Loader.DataLoader;
import org.cookingpatterns.Loader.DataLoaderManager;
import org.cookingpatterns.Loader.DataResponse;
import org.cookingpatterns.Loader.IDataCallback;
import org.cookingpatterns.Loader.RecipeLoader;
import org.cookingpatterns.Loader.UpdateRecipeLoader;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.Model.UnitOfMeasure;
import org.cookingpatterns.Parsing.EnglishSearchPaser;
import org.cookingpatterns.UtilsAndExtentions.OmittedDataCallback;
import org.cookingpatterns.View.DisplayRecipeFragment;
import org.cookingpatterns.View.EditRecipeFragment;
import org.cookingpatterns.View.SearchRecipeFragment;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboActivity;
import roboguice.event.Observes;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_controler)
public class Controler extends RoboActivity
{
    private ISearchQueryParser parser = new EnglishSearchPaser();

//    @Inject
//    private EventManager eventManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Controler", "onCreate");

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
                Log.i("Controler", "onBackStackChanged");
            }
        });

        /*ArrayList<Recipe> list = new ArrayList<Recipe>();
        Recipe res = new Recipe();
        res.setName("Eierspeiß");
        res.setRating(2);
        res.setTime("00:10");
        res.setCategory("Fast&Furious");
        List<Ingredient> ingr = new ArrayList<Ingredient>();
        Ingredient in = new Ingredient();
        in.setAmount(2);
        in.setName("Eier");
        in.setUnit(UnitOfMeasure.pcs);
        ingr.add(in);
        res.setIngredients(ingr);
        res.setDescription("Für die Eierspeis die Eier in eine Schüssel schlagen und mit Salz sowie Pfeffer würzen. Mit einer Gabel verschlagen.In einer Pfanne geben.");
        list.add(res);

        Bundle args = new Bundle();
        args.putSerializable("ingredient", in);
        DataLoader loader = new AddIngredientLoader(this, args);
        DataLoaderManager.init(getLoaderManager(), DataLoaderManager.ADDINGREDIENT_LOADER_ID, loader, new OmittedDataCallback("Controler"));

        args = new Bundle();
        args.putSerializable("recipe", res);
        loader = new AddRecipeLoader(this, args);
        DataLoaderManager.init(getLoaderManager(), DataLoaderManager.ADDRECIPE_LOADER_ID, loader, new OmittedDataCallback("Controler"));*/
    }

    protected void onSaveInstanceState (Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("HasFragment", true);

        Log.i("Controler", "onSaveInstanceState");
    }

    private void OnNewRecipeClicked(@Observes OnNewRecipeClick event) {
        Log.i("Controler", "OnNewRecipeClicked");
        Fragment fragment = EditRecipeFragment.CreateFragment(new Recipe(), true);
        switchFragment(fragment);
    }

    private void OnSaveRecipeClicked(@Observes OnSaveRecipeClick event) {
        Log.i("Controler", "OnSaveRecipeClicked");
        getFragmentManager().popBackStack();

        Bundle args = new Bundle();
        args.putSerializable("recipe", event.getRecipe());

        if(event.getIsNew()) {
            DataLoader loader = new AddRecipeLoader(this, args);
            DataLoaderManager.init(getLoaderManager(), DataLoaderManager.ADDRECIPE_LOADER_ID, loader, new OmittedDataCallback("Controler SaveRecipe New"));
        }
        else
        {
            DataLoader loader = new UpdateRecipeLoader(this, args);
            DataLoaderManager.init(getLoaderManager(), DataLoaderManager.UPDATERECIPE_LOADER_ID, loader, new OmittedDataCallback("Controler SaveRecipe Edit"));
        }
    }

    private void OnEditRecipeClicked(@Observes OnEditRecipeClick event) {
        Log.i("Controler","OnEditRecipeClicked");
        Fragment fragment = EditRecipeFragment.CreateFragment(event.getRecipe(), false);
        switchFragment(fragment);
    }

    private void OnDisplayRecipeClicked(@Observes OnDisplayRecipeClick event) {
        Log.i("Controler","OnDisplayRecipeClicked");
        Fragment fragment = DisplayRecipeFragment.CreateFragment(event.getRecipe());
        switchFragment(fragment);
    }

    private void OnSearchRequestClicked(@Observes OnSearchRequestClick event) {
        Log.i("Controler", "OnSearchRequestClicked");

        ILiteralNode root = parser.ParseString(event.GetQuery());
        Bundle args = new Bundle();
        args.putSerializable("searchTree", root);

        DataLoader loader = new RecipeLoader(this, args);
        DataLoaderManager.init(getLoaderManager(), DataLoaderManager.RECIPE_LOADER_ID, loader, new OmittedDataCallback("Controler SearchRequest"));
    }

    private void OnRecalculatePortionsClicked(@Observes OnRecalculatePortionsClick event) {
        Log.i("Controler","OnUpdateRecipeDataEvent");
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
