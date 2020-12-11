package com.choicely.learn.testingapp.blackjack;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DealerHand {

    private final Random random = new Random();
    private final Handler handler = new Handler();

    private final OnHandFinishedListener onHandFinishedListener;

    final List<Integer> list = new ArrayList<>();

    public DealerHand(OnHandFinishedListener onHandFinishedListener) {
        this.onHandFinishedListener = onHandFinishedListener;
    }

    public void clear() {
        list.clear();
    }

    int getSum() {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }

    String getHandString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            String singleCard = list.get(i).toString();
            builder.append(singleCard + "\t");
        }

        return builder.toString();
    }

    private void dealersGameAccordingToRules() {
        int dealerSum = getSum();
        if (dealerSum < 17) {
            addCard();
            handler.postDelayed(this::dealersGameAccordingToRules, 2000);
        } else {
            //dealer must stand, looks nicer with delay
            handler.postDelayed(onHandFinishedListener::onHandFinished, 1000);
            // TODO: call back to activity, dealers game has ended
//             this is where activity method to compare player and dealer hands is needed
        }
    }


    public void addCard() {
        int card  = random.nextInt(10 - 1) + 1;
        list.add(card);
    }

    public void startDealersGame() {
        handler.postDelayed(this::dealersGameAccordingToRules, 2000);
    }

    public interface OnHandFinishedListener {

        void onHandFinished();

    }

}
