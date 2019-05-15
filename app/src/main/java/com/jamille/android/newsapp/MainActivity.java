package com.jamille.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    private ArrayList<News> mNews = new ArrayList<>();
    private NewsAdapter adapter;
    ListView newsListView;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsListView = (ListView) findViewById(R.id.list);
        if (isNetworkAvailable()) {
            bundle = new Bundle();
            bundle.putString("mURL", "https://content.guardianapis.com/search?q=entertainment&api-key=3aacfc79-41e2-4c81-a70c-8579cf902e04&show-tags=contributor");
            getLoaderManager().initLoader(1, bundle, this).forceLoad();
            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    // Find the current news that was clicked on
                    News currentNews = adapter.getItem(position);
                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri newsUri = Uri.parse(currentNews.getUrl());
                    // Create a new intent to view the news URI
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);
                    // Send the intent to launch a new activity
                    startActivity(websiteIntent);
                }
            });
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            // Network is present and connected
            isAvailable = true;
        }
        return isAvailable;
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(MainActivity.this, args.getString("mURL"));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        mNews = data;
        adapter = new NewsAdapter(this, mNews);
        newsListView.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        mNews = new ArrayList<>();  // clear array list
        adapter.notifyDataSetChanged();  //
    }

}
