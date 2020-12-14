package com.choicely.learn.testingapp.blackjack;

import android.os.Handler;
import android.util.Log;

public class DealerHand extends Hand{

    private static final String TAG = "DealerHand";
    private final Handler handler = new Handler();

    private final OnHandFinishedListener onHandFinishedListener;
    private final OnHandChanged onHandChanged;

    public DealerHand(OnHandFinishedListener onHandFinishedListener, OnHandChanged onHandChanged) {
        this.onHandFinishedListener = onHandFinishedListener;
        this.onHandChanged = onHandChanged;
    }

    /**
     * I'm overriding this method because Dealer has to have the question mark (card face down) in beginning of the game
     */
    @Override
    String getHandString() {
        StringBuilder everyCard = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            String singleCard = list.get(i).toString();
            if(list.size() == 1){
                everyCard.append(singleCard + "\t\t" + "?");
            } else {
                everyCard.append(singleCard + "\t\t");
            }
        }

        return everyCard.toString();
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
