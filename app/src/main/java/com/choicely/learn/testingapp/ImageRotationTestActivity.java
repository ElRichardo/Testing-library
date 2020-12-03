package com.choicely.learn.testingapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ImageRotationTestActivity extends AppCompatActivity {

    private static final String TAG = "ImageTestActivity";

    private ImageView imageView;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_rotation_test_activity);

        imageView = findViewById(R.id.image_rotation_test_activity_button);
        frameLayout = findViewById(R.id.image_rotation_test_activity_layout);
        frameLayout.setOnTouchListener(onRotateTouchListener);
    }

    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener onRotateTouchListener = (v, event) -> {
        float x = event.getX();
        float y = event.getY();

        float xc = imageView.getPivotX() +  imageView.getWidth()/2;
        float yc = imageView.getPivotY() + imageView.getHeight()/2;

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "Action down");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d(TAG, "Action move");

                float deltaX = x - xc;
                float deltaY = y - yc;

                //Tää kommentoitu juttu ei toiminu, koska elämä on vaikeeta.
                //double rotation = Math.toDegrees(Math.atan2(deltaX,deltaY));

                //imageView.setRotation((float) -rotation);
                //Log.d(TAG, "Vastaus kaikkeen: " +  rotation);
                imageView.setRotation(deltaX);
                imageView.setRotation(deltaY);
                break;
            case MotionEvent.ACTION_UP:
                Log.d(TAG, "Action up");
                break;
        }

        return true;
    };

}
