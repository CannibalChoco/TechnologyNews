package com.example.android.technologynews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HeadlineActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Headline>>, HeadlineAdapter.ListItemClickListener {

    private static final String LOG_TAG = HeadlineActivity.class.getName();
    private static final int HEADLINE_LOADER_ID = 1;

    // search urls
    private static final String QUERY_URL = "http://content.guardianapis.com/search?" +
            "order-by=newest&page-size=20&q=technology&api-key=c1a0ea4b-cb5b-4f89-86d4-d0636600e676";

    // handle layout
    private RecyclerView recyclerView;
    private HeadlineAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    // handle empty screen
    private TextView emptyStateTextView;
    ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "TEST: onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.headline_list);

        // find views to later set visibility
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        emptyStateTextView = (TextView) findViewById(R.id.empty_state_text_view);
        loadingIndicator = (ProgressBar) findViewById(R.id.loading_spinner);

        // use a linear layout manager on the recyclerView
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        adapter = new HeadlineAdapter(new ArrayList<Headline>(), this);
        recyclerView.setAdapter(adapter);

        if (isConnected()){
            loadingIndicator.setVisibility(View.VISIBLE);
            LoaderManager loaderManager = getLoaderManager();
            Log.i(LOG_TAG, "TEST: calling initLoader() from onCreate");
            loaderManager.initLoader(HEADLINE_LOADER_ID, null, this);
        }else{
            recyclerView.setVisibility(View.GONE);
            loadingIndicator.setVisibility(View.GONE);
            emptyStateTextView.setVisibility(View.VISIBLE);
            emptyStateTextView.setText(R.string.no_network_connection);
        }

    }

    public void onListItemClick (int position) {
        Headline currentHeadline = adapter.getItem(position);
        openWebPage(currentHeadline.getUrl());
    }

    public void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
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
        loadingIndicator.setVisibility(View.GONE);
        // If there is a valid list of {@link Headline}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (headlines != null && !headlines.isEmpty()) {
            adapter.addAll(headlines);
        }else{
            emptyStateTextView.setText(R.string.no_new_articles);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Headline>> loader) {
        Log.i(LOG_TAG, "TEST: onLoaderReset");
        //queryUrl = null;
        adapter.clear();
    }
}
