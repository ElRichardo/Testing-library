package com.choicely.learn.testingapp.blackjack;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DealerHand extends Hand{

    private static final String TAG = "DealerHand";
    private final Handler handler = new Handler();

    private final OnHandFinishedListener onHandFinishedListener;
    private final OnHandChanged onHandChanged;

    public DealerHand(OnHandFinishedListener onHandFinishedListener, OnHandChanged onHandChanged) {
        this.onHandFinishedListener = onHandFinishedListener;
        this.onHandChanged = onHandChanged;
    }

    private void dealersGameAccordingToRules() {
        int dealerSum = getSum();
        if (dealerSum < 17) {
            addCard();
            onHandChanged.onHandChanged();
            Log.d(TAG, "add card");
            handler.postDelayed(this::dealersGameAccordingToRules, 2000);
        } else {
            //dealer must stand, looks nicer with delay
            handler.postDelayed(onHandFinishedListener::onHandFinished, 1000);
        }
    }

    public void startDealersGame() {
        handler.postDelayed(this::dealersGameAccordingToRules, 2000);
    }

    public interface OnHandFinishedListener {

        void onHandFinished();
    }

    public interface OnHandChanged{

        void onHandChanged();
    }
}
