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
import org.cookingpatterns.EventMessages.OnSaveRecipeClick;
import org.cookingpatterns.EventMessages.OnSearchRequestClick;
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.View.DisplayRecipeFragment;
import org.cookingpatterns.View.EditRecipeFragment;

import java.util.ArrayList;

import roboguice.activity.RoboActivity;
import roboguice.event.Observes;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_controler)
public class Controler extends RoboActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Log.i("Controler","onBackStackChanged");
            }
        });
    }

    private void OnNewRecipeClicked(@Observes OnNewRecipeClick event) {
        Log.i("Controler", "OnNewRecipeClicked");
        Fragment fragment = EditRecipeFragment.CreateFragment(new Recipe());
        switchFragment(fragment);
    }
    private void OnSaveRecipeClicked(@Observes OnSaveRecipeClick event) {
        Log.i("Controler", "OnDisplayRecipeClicked");
        getFragmentManager().popBackStack();
        //TODO save it!!!!
        ArrayList<Recipe> list = new ArrayList<Recipe>();
        list.add(event.getRecipe());
        eventManager.fire(new OnProvideSearchResultEvent(list));
    }
    private void OnEditRecipeClicked(@Observes OnEditRecipeClick event) {
        Log.i("Controler","OnEditRecipeClicked");
        Fragment fragment = EditRecipeFragment.CreateFragment(event.getRecipe());
        switchFragment(fragment);
    }
    private void OnDisplayRecipeClicked(@Observes OnDisplayRecipeClick event) {
        Log.i("Controler","OnDisplayRecipeClicked");
        Fragment fragment = DisplayRecipeFragment.CreateFragment(event.getRecipe());
        switchFragment(fragment);
    }
    private void OnSearchRequestClicked(@Observes OnSearchRequestClick event) {
        Log.i("Controler","OnSearchRequestClicked");
        //TODO something usefull!!!!
        eventManager.fire(new OnProvideSearchResultEvent(new ArrayList<Recipe>()));
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
