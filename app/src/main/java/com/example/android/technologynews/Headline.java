package com.example.android.technologynews;


public class Headline {
    private String headline;
    private String url;

    public Headline(String headline, String url){
        this.headline = headline;
        this.url = url;
    }

    public String getHeadline() {
        return headline;
    }

    public String getUrl() {
        return url;
    }
}
