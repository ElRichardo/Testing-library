package com.choicely.learn.testingapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.choicely.learn.testingapp.countdowntimer.CountDownTimerActivity;
import com.choicely.learn.testingapp.imagegallery.ImageGalleryActivity;
import com.choicely.learn.testingapp.receiptsave.ReceiptSavingActivity;
import com.choicely.learn.testingapp.threading.RandomStringsActivity;
import com.choicely.learn.testingapp.viewpager.ViewPagerActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public String CHANNEL_ID = "1";
    public int notificationID = 1;

    private Button imageTest;
    private Button soundTest;
    private Button cardTest;
    private Button viewPagerTest;
    private Button notificationTest;
    private Button scrollTest;
    private Button receiptSaving;
    private Button countDownBtn;
    private Button randomStrings;
    private Button imageGallery;
    private Button citySearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        imageTest = findViewById(R.id.main_activity_image_test);
        soundTest = findViewById(R.id.main_activity_sound_test);
        cardTest = findViewById(R.id.main_activity_card_view_1);
        viewPagerTest = findViewById(R.id.main_activity_view_pager);
        notificationTest = findViewById(R.id.main_activity_notification);
        scrollTest = findViewById(R.id.main_activity_adjust_screen);
        receiptSaving = findViewById(R.id.main_activity_receipt_button);
        countDownBtn = findViewById(R.id.main_activity_count_down);
        randomStrings = findViewById(R.id.main_activity_random_strings);
        citySearch = findViewById(R.id.main_activity_city_search);

        createNotificationChannel();
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            default:
            case R.id.main_activity_image_test:
                intent = new Intent(this, ImageTestActivity.class);
                startActivity(intent);
                break;
            case R.id.main_activity_sound_test:
                intent = new Intent(this, SoundTestActivity.class);
                startActivity(intent);
                break;
            case R.id.main_activity_card_view_1:
                intent = new Intent(this, CardView1Activity.class);
                startActivity(intent);
                break;
            case R.id.main_activity_view_pager:
                intent = new Intent(this, ViewPagerActivity.class);
                startActivity(intent);
                break;
            case R.id.main_activity_notification:
                addNotification();
                break;
            case R.id.main_activity_adjust_screen:
                intent = new Intent(this, AdjustTestActivity.class);
                startActivity(intent);
                break;
            case R.id.main_activity_receipt_button:
                intent = new Intent(this, ReceiptSavingActivity.class);
                startActivity(intent);
                break;
            case R.id.main_activity_count_down:
                intent = new Intent(this, CountDownTimerActivity.class);
                startActivity(intent);
                break;
            case R.id.main_activity_random_strings:
                intent = new Intent(this, RandomStringsActivity.class);
                startActivity(intent);
                break;
            case R.id.main_activity_image_gallery:
                intent = new Intent(this, ImageGalleryActivity.class);
                startActivity(intent);
                break;
            case R.id.main_activity_city_search:
                intent = new Intent(this, CitySearchActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * Channel needs to be created immediately after the app starts because the notification can't show without a channel
     */
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "Channel 1", importance);
            channel.setDescription("This is a channel for my notifications");

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void addNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        //Builds the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notibell)
                .setContentTitle("My first notification")
                .setContentText("Once upon a time there was a little notification. He was always a happy child.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(Color.BLUE)
                .setContentIntent(pendingIntent);

        //shows the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(notificationID, builder.build());
    }
}