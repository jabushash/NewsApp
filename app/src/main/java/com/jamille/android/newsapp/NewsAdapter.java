package com.jamille.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jamille on 25/09/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (convertView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview, parent, false);  //false cuz we dont want to attach list item view to parent listView yet
        }
        News currentNews = getItem(position);
        TextView title = (TextView) listItemView.findViewById(R.id.title);
        title.setText(currentNews.getTitle());

        TextView section = (TextView) listItemView.findViewById(R.id.section);
        section.setText("Section: " + currentNews.getSection());

        TextView date = (TextView) listItemView.findViewById(R.id.date);
        date.setText("Date: " + currentNews.getDate());

        TextView name = (TextView) listItemView.findViewById(R.id.name);
        name.setText("Author: " + currentNews.getFirstName() + " " + currentNews.getLastName());

        return listItemView;
    }

}
