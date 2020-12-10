package com.choicely.learn.testingapp.blackjack;

class DealersGame{
    BlackJackActivity blackJackActivity;

    void stand() {
        isButtonsActive = false;
        buttonActivity();

        isPlayerActive = false;
        isDealerActive = true;
        setActivity();

        dealerCardsAtStart();
        handler.postDelayed(this::dealersGameAccordingToRules, 2000);
    }

    void dealerCardsAtStart() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < dealerCardList.size(); i++) {
            String everyCard = dealerCardList.get(i).toString();
            builder.append(everyCard + "\t");
        }
        dealerCards.setText(builder.toString());
        updateListSum(dealerCardList);
    }

    void dealersGameAccordingToRules() {
        if (dealerSum < 17) {
            addCardTo(dealerCardList);
            updateListSum(dealerCardList);
            handler.postDelayed(this::dealersGameAccordingToRules, 2000);
        } else if (dealerSum > FINAL_NUMBER_21) {
            handler.postDelayed(this::playerWin, 1000);
        } else {
            //dealer must stand
            handler.postDelayed(this::compareHands, 1000);
        }
    }
}
