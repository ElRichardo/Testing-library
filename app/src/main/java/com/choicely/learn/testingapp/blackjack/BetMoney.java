package com.choicely.learn.testingapp.blackjack;

public class BetMoney {

    private int moneyBet;

    //kommentoin ulos koska en vois kutsuu muuten getmoneybettii ennen ku peli on loppunu
//    private boolean isEnded = false;

    void setMoneyBet(int bet){
        this.moneyBet = bet;
    }

    void win() {
//        this.isEnded = true;
        this.moneyBet *= 2;
    }

    void blackJackWin(){
//        this.isEnded = true;
        this.moneyBet *= 2.5;
    }

    void lose() {
//        this.isEnded = true;
        this.moneyBet = 0;
    }

    void draw() {
    }

    void surrender(){
//        this.isEnded = true;
        this.moneyBet *= 0.5;
    }

    int getMoneyBet() {
//        if(!isEnded) {
//            throw new IllegalStateException("Bet is not yet finished");
//        }

        return moneyBet;
    }
}