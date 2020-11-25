package com.choicely.learn.testingapp.countdowntimer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.learn.testingapp.R;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CountDownTimerActivity extends AppCompatActivity {

    private static final String TAG = "CountDownTimerActivity";

    private TextView countDownText;
    private EditText timeSetByUser;
    private Button startButton;
    private Button setTime;

    private long startTimeMillis;
    private long timeLeftInMillis;

    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_down_timer_activity);

        countDownText = findViewById(R.id.count_down_timer_activity_numbers);
        timeSetByUser = findViewById(R.id.count_down_timer_activity_set_time_text);
        startButton = findViewById(R.id.count_down_timer_activity_start);
        setTime = findViewById(R.id.count_down_timer_activity_time_set_button);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.count_down_timer_activity_start:
                startButton.setEnabled(false);
                startButton.setBackgroundResource(R.color.gray);
                startTimer();
                break;
            case R.id.count_down_timer_activity_time_set_button:
                setUserInputAsMillis();
                break;
        }
    }

    private void setUserInputAsMillis() {
        startTimeMillis = 0;
        String minutesInput = timeSetByUser.getText().toString();

        long millisTime = Long.parseLong(minutesInput) * 60000;

        setTime(millisTime);
    }

    private void setTime(long millisTime) {
        startTimeMillis = millisTime;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(startTimeMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountdownTimer();
            }

            @Override
            public void onFinish() {
                countDownText.setText("Finished");
                startButton.setEnabled(true);
                startButton.setBackgroundResource(R.color.design_default_color_background);
            }
        }.start();
    }

    private void updateCountdownTimer() {
        long seconds = (timeLeftInMillis / 1000) % 60;
        long minutes = ((timeLeftInMillis / (1000 * 60)) % 60);
        long hours = ((timeLeftInMillis / (1000 * 60 * 60)) % 24);

        if (hours == 0) {
            //countDownText.setText(minutes + ":" + seconds);
            countDownText.setText(String.format(Locale.getDefault(), "%d:%d", minutes, seconds));

        } else {
            //countDownText.setText(hours + ":" + minutes + ":" + seconds);
            countDownText.setText(String.format(Locale.getDefault(), "%d:%d:%d", hours, minutes, seconds));
        }
    }
}