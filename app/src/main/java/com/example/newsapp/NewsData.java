package com.example.newsapp;

public class NewsData {
    private String title;
    private String author;
    private String ImageUrl;
    private String newsUrl;
    private  String source;
    private boolean starred;

    public NewsData(String title,String author,String ImageUrl, String newsUrl,String source)
    {
        this.title=title;
        this.author=author;
        this.ImageUrl=ImageUrl;
        this.newsUrl=newsUrl;
        this.source=source;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getSource() {
        return source;
    }

    public boolean isStarred() {
        return starred;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStarred(boolean starred) {
        this.starred = starred;
    }
}
