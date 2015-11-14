package org.cookingpatterns;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.View.RecipeListItem;

public class SearchActivity extends AppCompatActivity {

	//For ListView ???
	//http://www.vogella.com/tutorials/AndroidListView/article.html

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LinearLayout list = (LinearLayout) findViewById(R.id.resultList);

        Log.w("SearchActivity", "onCreate --- Called!!!!!!!!!!!!!!!!!!!!!!");

        Recipe res = new Recipe();
        res.setName("Eierspeiß");
        res.setRating(2);
        res.setTime("00:10");

        list.addView(RecipeListItem.CreateListItem(getApplicationContext(), res));
        list.addView(RecipeListItem.CreateListItem(getApplicationContext(), res));
    }

}
