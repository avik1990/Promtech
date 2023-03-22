package com.store.promtech;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.jsibbold.zoomage.ZoomageView;

public class ImageFullActivity extends AppCompatActivity {

    ZoomageView zoomImage;
    String url = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_full);

        zoomImage = findViewById(R.id.zoomImage);
        url = getIntent().getExtras().getString("url");
        Glide.with(this)
                .load(url)
                .apply(new RequestOptions().placeholder(R.drawable.place_holder).error(R.drawable.place_holder).dontAnimate())
                .into(zoomImage);
    }
}