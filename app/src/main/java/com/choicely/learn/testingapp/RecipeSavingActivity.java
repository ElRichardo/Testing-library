package com.choicely.learn.testingapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeSavingActivity extends AppCompatActivity {

    private ImageButton cameraButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_saving_activity);

        cameraButton = findViewById(R.id.recipe_saving_activity_camera);

        cameraButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CameraActivity.class);
            startActivity(intent);
        });
    }
}
