package com.jamille.android.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jamille on 25/09/2017.
 */

public class QueryUtils {

    private QueryUtils() {
    }

    public static ArrayList<News> extractBooks(String jsonResponse) {
        String title = "";
        News mNews;
        ArrayList<News> newsArrayList = new ArrayList<>();
        String firstName = "";
        String lastName = "";

        try {
            JSONObject baseJsonResponse = new JSONObject(jsonResponse);
            JSONObject response = baseJsonResponse.getJSONObject("response");
            JSONArray resultArray = response.getJSONArray("results");
            for (int i = 0; i < resultArray.length(); i++) {
                firstName = "";
                lastName = "";
                JSONObject element = resultArray.getJSONObject(i);
                title = element.getString("webTitle");
                Log.e("Title ", title);
                String section = element.getString("sectionName");
                Log.e("section ", section);
                String dateTotal = element.getString("webPublicationDate");
                Log.e("dateTotal ", dateTotal);
                String dateParts[] = dateTotal.split("T");
                String date = dateParts[0];
                Log.e("date ", date);
                String url = element.getString("webUrl");
                Log.e("url ", url);
                JSONArray nameArray = element.getJSONArray("tags");

                for (int y = 0; y < nameArray.length(); y++) {

                    JSONObject nameObject = nameArray.getJSONObject(y);
                    firstName = nameObject.getString("firstName");
                    lastName = nameObject.getString("lastName");
                }

                mNews = new News(title, section, date, firstName, lastName, url);
                newsArrayList.add(mNews);
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the News JSON results", e);
        }
        // Return the list of books
        return newsArrayList;
    }

}
