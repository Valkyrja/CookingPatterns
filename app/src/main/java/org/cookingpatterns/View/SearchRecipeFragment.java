package org.cookingpatterns.View;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import org.cookingpatterns.Model.Recipe;
import org.cookingpatterns.R;
import java.util.ArrayList;
import roboguice.RoboGuice;
import roboguice.event.EventManager;
import roboguice.event.Observes;
import roboguice.fragment.RoboFragment;
import roboguice.inject.InjectView;


public class SearchRecipeFragment extends Fragment
{

    @InjectView(R.id.searchView) private SearchView SearchField;
    @InjectView(R.id.resultList) private ListView ResultList;
    @InjectView(R.id.fab)        private FloatingActionButton AddButton;

    @Inject
    private EventManager eventManager;

    private RecipeListAdapter RecipeAdapter;

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
        if (getArguments() != null) {
            //TODO RestoreState
            //mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
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

        ArrayList<Recipe> list = new ArrayList<Recipe>();
        for (int i = 0; i < 10; i++)
        {
            Recipe res = new Recipe();
            res.setName("Eierspeiß");
            res.setRating(2);
            res.setTime("00:10");
            list.add(res);
        }

        RecipeAdapter = new RecipeListAdapter(getActivity().getApplication(), list);
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

        SearchField.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("SearchRecipeFragment", "OnSearchRequestClick");
                eventManager.fire(new OnSearchRequestClick(SearchField.getQuery()));
            }
        });
    }

    public void onViewStateRestored(@Nullable Bundle savedInstanceState)
    {
        super.onViewStateRestored(savedInstanceState);
        Log.i("SearchRecipeFragment", "onViewStateRestored");
        if(savedInstanceState != null)
        {
            SearchField.setQuery(savedInstanceState.getString("Query"), false);
        }
    }

    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString("Query", SearchField.getQuery().toString());

        Log.i("SearchRecipeFragment", "onSaveInstanceState");
    }

    private void OnProvideSearchResultEvent(@Observes OnProvideSearchResultEvent event) {
        Log.i("SearchRecipeFragment", "OnDisplayRecipeClicked");
        RecipeAdapter.clear();
        RecipeAdapter.addAll(event.GetResult());
    }

    private void onRecipeListLoadedEvent(@Observes OnRecipeListResponseEvent event)
    {
        Log.i("Loader", "OnRecipeListResponseEvent");
    }
}
