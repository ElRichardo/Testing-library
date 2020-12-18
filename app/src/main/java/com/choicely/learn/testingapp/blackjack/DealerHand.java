package com.choicely.learn.testingapp.blackjack;

import android.os.Handler;
import android.util.Log;

public class DealerHand extends Hand {

    private static final String TAG = "DealerHand";
    private final int BLACK_JACK_PARAMETER = 21;
    private final int DEFAULT_VALUE = -1;
    private final Handler handler = new Handler();

    private final OnHandFinishedListener onHandFinishedListener;
    private final OnHandChanged onHandChanged;

    private boolean isBlackJack = false;

    public DealerHand(OnHandFinishedListener onHandFinishedListener, OnHandChanged onHandChanged) {
        this.onHandFinishedListener = onHandFinishedListener;
        this.onHandChanged = onHandChanged;
    }

    @Override
    String getHandString() {
        StringBuilder everyCard = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            String singleCard = list.get(i).toString();
            if (list.size() == 1) {
                everyCard.append(singleCard).append("\t\t").append("?");
            } else {
                everyCard.append(singleCard).append("\t\t");
            }
        }
        return everyCard.toString();
    }

    void checkIfBlackJack() {
        if (list.size() == 2) {
            if (list.get(0) == 1 && list.get(1) == 10 || list.get(0) == 10 && list.get(1) == 1) {
                Log.d(TAG, "Blackjack!!!");
                isBlackJack = true;
            }
        }
    }

    private void dealersGameAccordingToRules() {
        int dealerSum = getSum();
        if (dealerSum < 17) {
            addCard(10);
            checkIfBlackJack();
            onHandChanged.onHandChanged();

            handler.postDelayed(this::dealersGameAccordingToRules, 2000);
        } else {
            //dealer must stand, looks nicer with delay
            if (!isBlackJack) {
                handler.postDelayed(() -> onHandFinishedListener.onHandFinished(DEFAULT_VALUE), 1000);
            } else {
                handler.postDelayed(() -> onHandFinishedListener.onHandFinished(BLACK_JACK_PARAMETER), 1000);
            }
        }
    }

    public void startDealersGame() {
        handler.postDelayed(this::dealersGameAccordingToRules, 1000);
    }

    public interface OnHandFinishedListener {

        void onHandFinished(int sum);
    }

    public interface OnHandChanged {

        void onHandChanged();
    }
}
