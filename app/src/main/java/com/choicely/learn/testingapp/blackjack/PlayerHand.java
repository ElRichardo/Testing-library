package com.choicely.learn.testingapp.blackjack;

import android.util.Log;

public class PlayerHand extends Hand {

    private static final String TAG = "PlayerHand";

    private final onPlayerHandFinishedListener onPlayerHandFinishedListener;

    public PlayerHand(PlayerHand.onPlayerHandFinishedListener onPlayerHandFinishedListener) {
        this.onPlayerHandFinishedListener = onPlayerHandFinishedListener;
    }

    void checkIfBlackJack() {
        if (list.size() == 2) {
            if (list.get(0) == 1 && list.get(1) == 10 || list.get(0) == 10 && list.get(1) == 1) {
                Log.d(TAG, "Blackjack!!!");
                onPlayerHandFinishedListener.onPlayerHandFinished(21);
            }
        }
    }

    public interface onPlayerHandFinishedListener {

        void onPlayerHandFinished(int sum);
    }
}
