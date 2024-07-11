package com.example.newsreaderapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {

    private List<GuardianResponse.Article> articles;
    private Context context;
    private AppDatabase db;

    public ArticlesAdapter(Context context) {
        this.context = context;
        this.articles = new ArrayList<>(); // Initialize the articles list
        this.db = AppDatabase.getInstance(context);
    }

    public void setArticles(List<GuardianResponse.Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.article_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        final GuardianResponse.Article article = articles.get(position);
        holder.titleTextView.setText(article.getTitle());
        holder.sectionTextView.setText(article.getSection());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("title", article.getTitle());
                intent.putExtra("url", article.getUrl());
                intent.putExtra("section", article.getSection());
                context.startActivity(intent);
            }
        });

        holder.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriteArticle favoriteArticle = new FavoriteArticle(article.getTitle(), article.getUrl(), article.getSection());
                db.favoriteArticleDao().insert(favoriteArticle);
                Toast.makeText(context, "Article saved to favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles != null ? articles.size() : 0; // Handle null case
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView sectionTextView;
        Button saveButton;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            sectionTextView = itemView.findViewById(R.id.sectionTextView);
            saveButton = itemView.findViewById(R.id.saveButton);
        }
    }
}
