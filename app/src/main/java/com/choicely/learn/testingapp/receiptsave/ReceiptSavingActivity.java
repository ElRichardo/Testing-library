package com.choicely.learn.testingapp.receiptsave;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.learn.testingapp.R;

public class ReceiptSavingActivity extends AppCompatActivity {

    private ImageButton cameraButton;
    private int picID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_saving_activity);

        cameraButton = findViewById(R.id.receipt_saving_activity_camera);

        cameraButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CameraActivity.class);
            intent.putExtra("picID", picID);
            startActivity(intent);
        });
    }
}
