package com.example.android.technologynews;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;


public class HeadlineLoader extends AsyncTaskLoader<List<Headline>> {

    private static final String LOG_TAG = HeadlineLoader.class.getName();
    // Query URL
    private String url;

    public HeadlineLoader(Context context, String url){
        //call the super class constructor
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST: onStartLoading");
        // good practice to put forceLoad() whithin the loader subclass
        forceLoad();
    }

    @Override
    public List<Headline> loadInBackground() {
        Log.i(LOG_TAG, "TEST: loadInBackground");
        // Don't perform the request if there are no URLs, or the first URL is null.
        if (url == null) {
            return null;
        }

        // Perform the HTTP request for news data and process the response.
        return QueryUtils.fetchHeadlineData(url);
    }
}
