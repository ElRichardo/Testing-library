package com.choicely.learn.testingapp;

import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SoundTestActivity extends AppCompatActivity {

    private static final String TAG = "SoundTestActivity";

    private static SoundPool testSound;
    private Button soundButton;
    private int testID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound_test_activity);

        soundButton = findViewById(R.id.sound_test_activity_sound_button);

        soundButton.setOnClickListener(v -> {
            loadTestSound();
            testSound.play(testID, .25f, .25f, 1, 0, 1);
        });

    }

    private void loadTestSound() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            testSound = new SoundPool.Builder()
                    .setMaxStreams(2)
                    .build();
        } else {
            testSound = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        }
        testID = testSound.load(this, R.raw.test, 1);
    }
}
