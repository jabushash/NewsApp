package com.jamille.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Jamille on 25/09/2017.
 */

public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {

    private String mUrl;
    private String jsonResponse = "";
    private URL url = null;
    String result = "";

    public NewsLoader(Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        super.onStartLoading();
    }

    @Override
    public ArrayList<News> loadInBackground() {  //BACKGROUND

        if (mUrl.isEmpty()) {
            return new ArrayList<News>();
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            url = new URL(mUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
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
                jsonResponse = output.toString();

            } else {
                Log.e("Error ", "Error response code: " + urlConnection.getResponseCode());
            }

            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return QueryUtils.extractBooks(jsonResponse);
    }
}
