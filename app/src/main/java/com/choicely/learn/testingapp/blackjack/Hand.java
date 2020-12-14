package com.choicely.learn.testingapp.blackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hand {
    private final Random random = new Random();

    final List<Integer> list = new ArrayList<>();

    public void clear() {
        list.clear();
    }

    public void addCard() {
        int card = random.nextInt(10 - 1) + 1;
        list.add(card);
    }

    String getHandString() {
        StringBuilder everyCard = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            String singleCard = list.get(i).toString();
            everyCard.append(singleCard + "\t\t");
        }

        return everyCard.toString();
    }

    int getSum() {
        int sum = 0;
        for (int i : list) {
            sum += i;
        }
        return sum;
    }
}
