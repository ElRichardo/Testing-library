package com.choicely.learn.testingapp.blackjack;

public class Balance {

    private static final int BALANCE_AT_START = 500;
    private int currentBalance;

    int balanceAtStart(){
        currentBalance = BALANCE_AT_START;
        return currentBalance;
    }

    void setBalance(int balance){
        this.currentBalance = balance;
    }

    int getBalance(){
        return currentBalance;
    }
}