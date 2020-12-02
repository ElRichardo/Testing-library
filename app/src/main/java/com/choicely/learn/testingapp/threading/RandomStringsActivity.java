package com.choicely.learn.testingapp.threading;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.learn.testingapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RandomStringsActivity extends AppCompatActivity {

    private static final String TAG = "RandomStringsActivity";

    private final List list = new ArrayList();
    private Button createStrings;
    private TextView countText;
    private long start;
    private ExecutorService threadPool = Executors.newFixedThreadPool(1);//number says how many threads can be at the same time

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.random_strings_activity);

        createStrings = findViewById(R.id.random_strings_activity_button);
        countText = findViewById(R.id.random_strings_activity_count);

        createStrings.setOnClickListener(v -> {
            start = System.currentTimeMillis();
            for(int i = 0; i < 4; i++){
                getRandomStrings(250_000);
            }
        });
    }

        private void getRandomStrings(int length) {
            threadPool.submit(() -> {
                for (int i = 0; i < length; i++) {
                    Log.d(TAG, "iteration: " + i);
                    String randomString = UUID.randomUUID().toString();
                    list.add(randomString);
                }
                countText.post(() -> {
                    countText.setText(String.format("Finished! time: %d\nAmount of strings: %d", (System.currentTimeMillis()-start), list.size()));
                });
            });
        }
}