package com.example.newsreaderapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText searchTerm;
    private Button searchButton;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private ArticlesAdapter articlesAdapter;
    private Button viewFavoritesButton;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private static final String API_KEY = "775b40d0-1fc4-4da2-9051-9ec6d1e3560e";
    private static final String BASE_URL = "https://content.guardianapis.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Force locale to German
        Locale locale = new Locale("de");
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);

        searchTerm = findViewById(R.id.searchTerm);
        searchButton = findViewById(R.id.searchButton);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        viewFavoritesButton = findViewById(R.id.viewFavoritesButton);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        articlesAdapter = new ArticlesAdapter(this);
        recyclerView.setAdapter(articlesAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchTerm.getText().toString().trim();
                if (!query.isEmpty()) {
                    new FetchArticlesTask().execute(query);
                } else {
                    Toast.makeText(MainActivity.this, "Bitte geben Sie einen Suchbegriff ein", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewFavoritesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
                startActivity(intent);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_main) {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                } else if (id == R.id.nav_favorite) {
                    startActivity(new Intent(MainActivity.this, FavoriteActivity.class));
                } else if (id == R.id.nav_settings) {
                    startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                } else {
                    return false;
                }
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private class FetchArticlesTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... strings) {
            String query = strings[0];
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder().addInterceptor(logging).build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            GuardianApi service = retrofit.create(GuardianApi.class);
            Call<GuardianResponse> call = service.searchArticles(API_KEY, query);

            call.enqueue(new Callback<GuardianResponse>() {
                @Override
                public void onResponse(Call<GuardianResponse> call, Response<GuardianResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful() && response.body() != null) {
                        List<GuardianResponse.Article> articles = response.body().getResponse().getArticles();
                        articlesAdapter.setArticles(articles);
                    } else {
                        Log.e("API_CALL", "Response was not successful: " + response.code() + " - " + response.message());
                        try {
                            Log.e("API_CALL", "Response body: " + response.errorBody().string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(MainActivity.this, "Artikel konnten nicht abgerufen werden", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<GuardianResponse> call, Throwable t) {
                    Log.e("API_CALL", "Failed to fetch articles", t);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Artikel konnten nicht abgerufen werden: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            return null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_help) {
            showHelpDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.action_help)
                .setMessage("Anweisungen zur Nutzung der Schnittstelle")
                .setPositiveButton("OK", null)
                .show();
    }
}
