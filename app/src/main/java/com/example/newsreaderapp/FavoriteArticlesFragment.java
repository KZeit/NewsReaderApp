package com.example.newsreaderapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoriteArticlesFragment extends Fragment {

    private RecyclerView recyclerViewFavorites;
    private FavoriteArticlesAdapter favoriteArticlesAdapter;
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_articles, container, false);

        recyclerViewFavorites = view.findViewById(R.id.recyclerViewFavorites);
        recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(getContext()));

        db = AppDatabase.getInstance(getContext());

        // Fetch favorite articles from the database
        List<FavoriteArticle> favoriteArticles = db.favoriteArticleDao().getAllFavorites();
        favoriteArticlesAdapter = new FavoriteArticlesAdapter(favoriteArticles, getContext());
        recyclerViewFavorites.setAdapter(favoriteArticlesAdapter);

        return view;
    }
}
