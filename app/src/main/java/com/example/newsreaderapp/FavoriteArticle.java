package com.example.newsreaderapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorites")
public class FavoriteArticle {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String url;
    private String section;

    // Constructor, getters, and setters

    public FavoriteArticle(String title, String url, String section) {
        this.title = title;
        this.url = url;
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
