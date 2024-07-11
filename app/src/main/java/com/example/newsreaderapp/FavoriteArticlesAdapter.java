package com.example.newsreaderapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FavoriteArticlesAdapter extends RecyclerView.Adapter<FavoriteArticlesAdapter.ArticleViewHolder> {

    private List<FavoriteArticle> favoriteArticles;
    private Context context;
    private AppDatabase db;

    public FavoriteArticlesAdapter(List<FavoriteArticle> favoriteArticles, Context context) {
        this.favoriteArticles = favoriteArticles;
        this.context = context;
        this.db = AppDatabase.getInstance(context);
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favorite_article_item, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final FavoriteArticle article = favoriteArticles.get(position);
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

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.favoriteArticleDao().delete(article);
                favoriteArticles.remove(position);
                notifyItemRemoved(position);
                Toast.makeText(context, "Article removed from favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteArticles != null ? favoriteArticles.size() : 0; // Handle null case
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView;
        TextView sectionTextView;
        ImageButton deleteButton;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            sectionTextView = itemView.findViewById(R.id.sectionTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
