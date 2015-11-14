package org.cookingpatterns;

import android.os.Bundle;
import android.util.Log;

import org.cookingpatterns.EventMessages.OnDisplayRecipeClick;
import org.cookingpatterns.EventMessages.OnEditRecipeClick;
import org.cookingpatterns.EventMessages.OnNewRecipeClick;
import org.cookingpatterns.EventMessages.OnRecalculatePortionsClick;
import org.cookingpatterns.EventMessages.OnSearchRequestClick;

import roboguice.activity.RoboActivity;
import roboguice.event.Observes;
import roboguice.inject.ContentView;

@ContentView(R.layout.activity_controler)
public class Controler extends RoboActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void OnNewRecipeClicked(@Observes OnNewRecipeClick event) {
        Log.i("Controler", "OnNewRecipeClicked");
        // start Intent for the corresponding gauge
    }
    private void OnEditRecipeClicked(@Observes OnEditRecipeClick event) {
        Log.i("Controler","OnEditRecipeClicked");
        // start Intent for the corresponding gauge
    }
    private void OnDisplayRecipeClicked(@Observes OnDisplayRecipeClick event) {
        Log.i("Controler","OnDisplayRecipeClicked");
        // start Intent for the corresponding gauge
    }
    private void OnSearchRequestClicked(@Observes OnSearchRequestClick event) {
        Log.i("Controler","OnSearchRequestClicked");
        // start Intent for the corresponding gauge
    }
    private void OnRecalculatePortionsClicked(@Observes OnRecalculatePortionsClick event) {
        Log.i("Controler","OnUpdateRecipeDataEvent");
        // start Intent for the corresponding gauge
    }
}
