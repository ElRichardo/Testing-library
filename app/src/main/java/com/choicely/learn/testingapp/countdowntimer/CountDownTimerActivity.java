package com.choicely.learn.testingapp.countdowntimer;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.learn.testingapp.R;

public class CountDownTimerActivity extends AppCompatActivity {

    private TextView textView;
    private EditText timeSetByUser;
    private Button startButton;
    private Button setTime;
    private long startTimeMillis;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.count_down_timer_activity);

        textView = findViewById(R.id.count_down_timer_activity_numbers);
        timeSetByUser = findViewById(R.id.count_down_timer_activity_set_time_text);
        startButton = findViewById(R.id.count_down_timer_activity_start);
        setTime = findViewById(R.id.count_down_timer_activity_time_set_button);
    }

    public void onClick(View view) {
        switch (view.getId()){
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
        updateCountDownText();
    }

    private void updateCountDownText() {
        //millis divided by 1000 turns them into seconds.
        // By dividing by 3600, hours is given because an hour has 3600 seconds
        long hours = (startTimeMillis / 1000) / 3600;
    }

    private void setTime(long millisTime) {
        startTimeMillis = millisTime;
    }

    private void startTimer() {
        new CountDownTimer(startTimeMillis, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                textView.setText("time: " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                textView.setText("done");
                startButton.setEnabled(true);
                startButton.setBackgroundResource(R.color.design_default_color_background);
            }
        }.start();
    }
}
