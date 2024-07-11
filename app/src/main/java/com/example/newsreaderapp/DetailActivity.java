package com.example.newsreaderapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTextView;
    private TextView urlTextView;
    private TextView sectionTextView;
    private Button openUrlButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleTextView = findViewById(R.id.titleTextView);
        urlTextView = findViewById(R.id.urlTextView);
        sectionTextView = findViewById(R.id.sectionTextView);
        openUrlButton = findViewById(R.id.openUrlButton);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        final String url = intent.getStringExtra("url");
        String section = intent.getStringExtra("section");

        titleTextView.setText(title);
        urlTextView.setText(url);
        sectionTextView.setText(section);

        openUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(browserIntent);
            }
        });
    }
}
