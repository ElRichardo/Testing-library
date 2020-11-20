package com.choicely.learn.testingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class CardView1Activity extends AppCompatActivity {

    private CardView cardView;
    private float deltaX, deltaY;
    private float endvalueX = 0;
    private float endvalueY = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view_1_activity);

        cardView = findViewById(R.id.card_view_1_activity_card);

        cardView.setOnTouchListener(onMoveTouchListener);
    }

    private float startX = -1;
    private float startY = -1;


    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener onMoveTouchListener = (v, event) -> {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getRawX();
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                deltaX = event.getRawX() - startX;
                deltaY = event.getRawY() - startY;

                cardView.setX(deltaX + endvalueX);
                cardView.setY(deltaY + endvalueY);
                break;
            case MotionEvent.ACTION_UP:
                endvalueX += deltaX;
                endvalueY += deltaY;
                break;
        }
        return true;
    };


}
