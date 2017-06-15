package com.example.android.technologynews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HeadlineActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Headline>> {

    private static final String LOG_TAG = HeadlineActivity.class.getName();
    private static final int HEADLINE_LOADER_ID = 1;

    // search urls
    private static final String QUERY_URL = "http://content.guardianapis.com/search?section=technology&order-by=newest&page-size=15&q=technology&api-key=c1a0ea4b-cb5b-4f89-86d4-d0636600e676";

    private RecyclerView recyclerView;
    private HeadlineAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "TEST: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headline_list);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter
        adapter = new HeadlineAdapter(new ArrayList<Headline>());
        recyclerView.setAdapter(adapter);

        if (isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            Log.i(LOG_TAG, "TEST: calling initLoader() from onCreate");
            loaderManager.initLoader(HEADLINE_LOADER_ID, null, this);
        }
    }

    public boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    @Override
    public Loader<List<Headline>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "TEST: onCreateLoader");
        return new HeadlineLoader(this, QUERY_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Headline>> loader, List<Headline> headlines) {
        Log.i(LOG_TAG, "TEST: onLoadFinished");
        adapter.clear();
        // If there is a valid list of {@link Headline}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (headlines != null && !headlines.isEmpty()) {
            adapter.addAll(headlines);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Headline>> loader) {
        Log.i(LOG_TAG, "TEST: onLoaderReset");
        //queryUrl = null;
        adapter.clear();
    }
}
