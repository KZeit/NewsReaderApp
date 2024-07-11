package com.example.newsreaderapp;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteArticleDao {

    @Query("SELECT * FROM favorites")
    List<FavoriteArticle> getAllFavorites();

    @Insert
    void insert(FavoriteArticle favoriteArticle);

    @Delete
    void delete(FavoriteArticle favoriteArticle);
}
