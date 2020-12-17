package com.choicely.learn.testingapp.blackjack;

public class BetMoney {

    private int moneyBet;
    private boolean isEnded = false;

    void setMoneyBet(int bet){
        this.moneyBet = bet;
    }

    void win() {
        this.isEnded = true;
        this.moneyBet *= 2;
    }

    void blackJackWin(){
        this.isEnded = true;
        this.moneyBet *= 2.5;
    }

    void lose() {
        this.isEnded = true;
        this.moneyBet = 0;
    }

    void draw() {
        this.isEnded = true;
        // nothing needs to be done
//        this.moneyBet = moneyBet;
    }

    void surrender(){
        this.isEnded = true;
        this.moneyBet *= 0.5;
    }

    int getMoneyBet() {
        if(!isEnded) {
            throw new IllegalStateException("Bet is not yet finished");
        }

        return moneyBet;
    }
}