package com.empire.sitpoly.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.empire.sitpoly.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        toolbar = findViewById(R.id.toolbar_image);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Image");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String image = getIntent().getStringExtra("image");
        PhotoView imageView = findViewById(R.id.full_image);

        Glide.with(this).load(image).into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}