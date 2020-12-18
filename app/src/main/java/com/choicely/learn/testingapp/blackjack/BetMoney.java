package com.choicely.learn.testingapp.blackjack;

public class BetMoney {

    private int moneyBet;

    void setMoneyBet(int bet) { this.moneyBet = bet; }

    void win() { this.moneyBet *= 2; }

    void blackJackWin() { this.moneyBet *= 2.5; }

    void lose() { this.moneyBet = 0; }

    void surrender() { this.moneyBet *= 0.5; }

    int getMoneyBet() { return moneyBet; }
}