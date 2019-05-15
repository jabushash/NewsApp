package com.jamille.android.newsapp;

/**
 * Created by Jamille on 25/09/2017.
 */

public class News {
    private String title;
    private String section;
    private String date;
    private String firstName;
    private String lastName;
    private String url;

    public News(String title, String section, String date, String firstName, String lastName, String url) {
        this.title = title;
        this.section = section;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public String getSection() {
        return section;
    }

    public String getDate() {
        return date;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUrl() {
        return url;
    }

}