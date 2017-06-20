package com.example.android.technologynews;


public class Headline {
    private final String headline;
    private final String url;
    private final String time;
    private final String sectionName;

    public Headline(String headline, String url, String date, String sectionName) {
        this.headline = headline;
        this.url = url;
        this.time = date;
        this.sectionName = sectionName;
    }

    public String getHeadline() {
        return headline;
    }

    public String getUrl() {
        return url;
    }

    public String getTime() {
        return time;
    }

    public String getSectionName() {
        return sectionName;
    }

}
