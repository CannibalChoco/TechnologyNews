package com.example.android.technologynews;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();
    public static final int URL_READ_TIMEOUT = 10000; /* milliseconds */
    public static final int URL_CONNECT_TIMEOUT = 15000; /* milliseconds */
    public static final int URL_RESPONSE_OK = 200;


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the api dataset and return a list of {@link Headline} objects
     */
    public static List<Headline> fetchHeadlineData(String requestUrl) {
        Log.i(LOG_TAG, "TEST: fetchHeadlineData");
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and create list of {@link Headline} objects
        List<Headline> headlines = extractHeadlinesFromJson(jsonResponse);

        // Return the list of {@link Headline}s
        return headlines;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        Log.i(LOG_TAG, "TEST: createUrl");
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        Log.i(LOG_TAG, "TEST: makeHttpRequest");
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(URL_READ_TIMEOUT);
            urlConnection.setConnectTimeout(URL_CONNECT_TIMEOUT);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == URL_RESPONSE_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the headline JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        Log.i(LOG_TAG, "TEST: readFromStream");
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Headline} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Headline> extractHeadlinesFromJson(String headlineJsonResponse) {
        Log.i(LOG_TAG, "TEST: extractHeadlinesFromJson");
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(headlineJsonResponse)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding headlines to
        List<Headline> headlines = new ArrayList<>();

        // Try to parse the JsonResponse. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            JSONObject root = new JSONObject(headlineJsonResponse);
            JSONObject response = root.getJSONObject("response");
            JSONArray resultsArray = response.getJSONArray("results");

            for (int i = 0, n = resultsArray.length(); i < n; i++) {
                JSONObject currentHeadline = resultsArray.getJSONObject(i);
                String title = "";
                String webUrl = "";

                if(currentHeadline.has("webTitle")){
                    title = currentHeadline.getString("webTitle");
                }

                if(currentHeadline.has("webUrl")){
                    webUrl = currentHeadline.getString("webTitle");
                }

                headlines.add(new Headline(title, webUrl));
                String data = headlines.toString();
                Log.v("extractHeadlines", data);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the headlines JSON results", e);
        }

        // Return the list of headlines
        return headlines;
    }

}