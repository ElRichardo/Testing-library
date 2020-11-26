package com.choicely.learn.testingapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RandomStringsActivity extends AppCompatActivity {

    private static final String TAG = "RandomStringsActivity";

    private final List list = new ArrayList();
    private Button createStrings;
    private TextView countText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_strings_activity);

        createStrings = findViewById(R.id.random_strings_activity_button);
        countText = findViewById(R.id.random_strings_activity_count);

        createStrings.setOnClickListener(v -> {
            MyThread thread = new MyThread();
            thread.getRandomStrings(1000000);
            thread.start();
        });
    }

    class MyThread extends Thread {
        private void getRandomStrings(int length) {
            new Thread(() -> {
                for (int i = 0; i < length; i++) {
                    String randomString = UUID.randomUUID().toString();
                    list.add(randomString);
                }
                countText.post(() -> {
                    countText.setText("Finished!" + "\n" + "Amount of strings: " + list.size());
                });
            }).start();
        }
    }
}
