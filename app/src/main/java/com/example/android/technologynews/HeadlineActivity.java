package com.example.android.technologynews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class HeadlineActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Headline>> {

    private static final String LOG_TAG = HeadlineActivity.class.getName();
    private static final int HEADLINE_LOADER_ID = 1;

    // search urls
    private static final String SAMPLE_URL = "https://content.guardianapis.com/search?api-key=c1a0ea4b-cb5b-4f89-86d4-d0636600e676";

    private HeadlineAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "TEST: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headline_list);

        ListView headlineListView = (ListView) findViewById(R.id.list);
        adapter = new HeadlineAdapter(this, new ArrayList<Headline>());
        headlineListView.setAdapter(adapter);

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
        return new HeadlineLoader(this, SAMPLE_URL);
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
