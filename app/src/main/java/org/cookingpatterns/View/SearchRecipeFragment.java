package org.cookingpatterns.View;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.google.inject.Inject;
import org.cookingpatterns.EventMessages.OnDisplayRecipeClick;
import org.cookingpatterns.EventMessages.OnEditRecipeClick;
import org.cookingpatterns.EventMessages.OnNewRecipeClick;
import org.cookingpatterns.EventMessages.OnProvideSearchResultEvent;
import org.cookingpatterns.EventMessages.OnRecipeListResponseEvent;
import org.cookingpatterns.EventMessages.OnSaveRecipeClick;
import org.cookingpatterns.EventMessages.OnSearchRequestClick;
import org.cookingpatterns.Loader.DataResponse;
import org.cookingpatterns.Model.Ingredient;
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.Model.UnitOfMeasure;
import org.cookingpatterns.R;
import org.cookingpatterns.UtilsAndExtentions.Utils;

import java.util.ArrayList;
import java.util.List;

import roboguice.RoboGuice;
import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;


public class SearchRecipeFragment extends Fragment
{

    @InjectView(R.id.searchQuery)  private EditText SearchQuery;
    @InjectView(R.id.searchButton) private ImageButton SearchButton;
    @InjectView(R.id.resultList)   private ListView ResultList;
    @InjectView(R.id.fab)          private FloatingActionButton AddButton;

    @Inject
    private EventManager eventManager;

    private RecipeListAdapter RecipeAdapter;
    private CharSequence lastCommittedQuery;

    public static SearchRecipeFragment CreateFragment()
    {
        SearchRecipeFragment fragment = new SearchRecipeFragment();
        return fragment;
    }

    public SearchRecipeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectMembersWithoutViews(this);
        if (getArguments() != null)
        {
            lastCommittedQuery = getArguments().getString("LastCommittedQuery");
            SearchQuery.setText(getArguments().getString("Query"));
            if (lastCommittedQuery != null)
                eventManager.fire(new OnSearchRequestClick(lastCommittedQuery));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_search_recipe, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RoboGuice.getInjector(getActivity()).injectViewMembers(this);

        RecipeAdapter = new RecipeListAdapter(getActivity().getApplication(), new ArrayList<Recipe>());
        ResultList.setAdapter(RecipeAdapter);

        ResultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("SearchRecipeFragment", "OnDisplayRecipeClick");
                eventManager.fire(new OnDisplayRecipeClick((Recipe) parent.getItemAtPosition(position)));
            }
        });
        ResultList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("SearchRecipeFragment", "OnEditRecipeClick");
                Utils.HapticFeedbackShort(getActivity());
                eventManager.fire(new OnEditRecipeClick((Recipe) parent.getItemAtPosition(position)));
                return true;
            }
        });

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SearchRecipeFragment", "OnNewRecipeClick");
                eventManager.fire(new OnNewRecipeClick());
            }
        });

        SearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SearchRecipeFragment", "OnSearchRequestClick");
                lastCommittedQuery = SearchQuery.getText();
                eventManager.fire(new OnSearchRequestClick(lastCommittedQuery));
            }
        });


    }

    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        Log.i("SearchRecipeFragment", "onViewStateRestored");
        if(savedInstanceState != null)
        {
            lastCommittedQuery = savedInstanceState.getString("LastCommittedQuery");
            SearchQuery.setText(savedInstanceState.getString("Query"));
            if (lastCommittedQuery != null)
                eventManager.fire(new OnSearchRequestClick(lastCommittedQuery));        }
    }

    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        CharSequence query = SearchQuery != null ? SearchQuery.getText() : null;
        if(outState != null) {
            outState.putString("Query", query != null ? query.toString() : null);
            outState.putString("LastCommittedQuery", lastCommittedQuery != null ? lastCommittedQuery.toString() : null);
        }

        Log.i("SearchRecipeFragment", "onSaveInstanceState");
    }

    private void HandleProvideSearchResultEvent(@Observes final OnProvideSearchResultEvent event) {
        Log.i("SearchRecipeFragment", "OnDisplayRecipeClicked");
        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                RecipeAdapter.clear();
                RecipeAdapter.addAll(event.GetResult());
            }
        });
    }
}
