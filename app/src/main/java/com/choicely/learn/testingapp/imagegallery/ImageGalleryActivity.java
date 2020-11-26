package com.choicely.learn.testingapp.imagegallery;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.learn.testingapp.R;

public class ImageGalleryActivity extends AppCompatActivity {

    private ImageButton galleryBtn;
    private Button addImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery_activity);

        galleryBtn = findViewById(R.id.image_gallery_activity_gallery_button);
        addImage = findViewById(R.id.image_gallery_activity_adding_button);

        galleryBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShowGalleryPics.class);
            startActivity(intent);
        });

        addImage.setOnClickListener(v -> {
            Intent intent = new Intent(this, UrlAddingActivity.class);
            startActivity(intent);
        });
    }
}
