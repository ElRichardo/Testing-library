package com.choicely.learn.testingapp.countdowntimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.learn.testingapp.R;

public class CountDownTimerActivity extends AppCompatActivity {

    private TextView textView;
    private Button startButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_down_timer_activity);

        textView = findViewById(R.id.count_down_timer_activity_numbers);
        startButton = findViewById(R.id.count_down_timer_activity_start);

        startButton.setOnClickListener(v -> {
            startTimer();
        });
    }

    private void startTimer() {
        new CountDownTimer(30000, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("seconds: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                textView.setText("done");
            }
        }.start();
    }
}
