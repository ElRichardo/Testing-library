package com.choicely.learn.testingapp.blackjack;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.learn.testingapp.R;

import java.util.Locale;
import java.util.Random;

public class BlackJackActivity extends AppCompatActivity {

    private static final String TAG = "BlackJackActivity";
    static final int FINAL_NUMBER_21 = 21;
    private static final int BALANCE_AT_START = 500;

    private final DealerHand.OnHandFinishedListener onHandFinishedListener = this::compareHands;
    private final DealerHand.OnHandChanged onHandChanged = this::updateHandUI;

    private final DealerHand dealerHand = new DealerHand(onHandFinishedListener, onHandChanged);
    private final PlayerHand playerHand = new PlayerHand();

    private TextView surrenderText;
    private TextView losingText;
    private TextView winningText;
    private TextView drawText;
    private TextView dealerCards;
    private TextView playerCards;
    private TextView playerSumText;
    private TextView dealerSumText;
    private TextView playerTitleText;
    private TextView dealerTitleText;
    private TextView moneyBetText;
    private TextView balance;
    private EditText setMoney;
    private Button newGameBtn;
    private Button hitBtn;
    private Button standBtn;
    private Button surrenderBtn;
    private Button betBtn;
    private boolean isGameRunning = false;
    boolean isPlayerActive;
    boolean isDealerActive;
    boolean isButtonsActive;
    private int currentBalance;
    private int amountOfMoneyBet;
    private int balanceAndBetDiff;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_jack_activity);

        dealerCards = findViewById(R.id.black_jack_activity_dealer_cards);
        playerCards = findViewById(R.id.black_jack_activity_player_cards);
        newGameBtn = findViewById(R.id.black_jack_activity_new_game);
        hitBtn = findViewById(R.id.black_jack_activity_hit);
        standBtn = findViewById(R.id.black_jack_activity_stand);
        surrenderBtn = findViewById(R.id.black_jack_activity_surrender);
        betBtn = findViewById(R.id.black_jack_activity_bet);
        moneyBetText = findViewById(R.id.black_jack_activity_money_bet_text);
        balance = findViewById(R.id.black_jack_activity_balance);
        setMoney = findViewById(R.id.black_jack_activity_set_money);
        surrenderText = findViewById(R.id.black_jack_activity_surrender_text);
        losingText = findViewById(R.id.black_jack_activity_lost);
        winningText = findViewById(R.id.black_jack_activity_won);
        drawText = findViewById(R.id.black_jack_activity_draw);
        playerSumText = findViewById(R.id.black_jack_activity_player_sum);
        dealerSumText = findViewById(R.id.black_jack_activity_dealer_sum);
        playerTitleText = findViewById(R.id.black_jack_activity_player_text);
        dealerTitleText = findViewById(R.id.black_jack_activity_dealer_text);

        setViewVisibility();

        currentBalance = BALANCE_AT_START;
    }

    //switch case is not used because it'll be deprecated in the upcoming android update
    public void onClick(View v) {
        if (v == newGameBtn) {
            newGame();
            balance.setText(currentBalance + "€");
        } else if (v == hitBtn) {
            hit();
        } else if (v == standBtn) {
            playerStand();
        } else if (v == betBtn) {
            startMoneyBet();
        } else if (v == surrenderBtn) {
            surrender();
        }
    }

    private void newGame() {
        isButtonsActive = false;
        buttonActivity();

        isPlayerActive = true;
        isDealerActive = false;
        setActivity();

        losingText.setVisibility(View.INVISIBLE);
        winningText.setVisibility(View.INVISIBLE);
        drawText.setVisibility(View.INVISIBLE);
        surrenderText.setVisibility(View.INVISIBLE);

        gameStart();
    }

    private void gameStart() {
        dealerHand.clear();
        playerHand.clear();
        newGameBtn.setVisibility(View.GONE);

        dealerHand.addCard();
        dealerHand.addCard();

        playerHand.addCard();
        playerHand.addCard();

        isGameRunning = true;
        setViewVisibility();

        updateHandUI();
    }

    private void updateHandUI() {
        {
            playerCards.setText(String.format(Locale.getDefault(), "%s", playerHand.getHandString()));
            playerSumText.setText(String.format(Locale.getDefault(), "Sum: %d", playerHand.getSum()));
        }
        {
            dealerCards.setText(String.format(Locale.getDefault(), "%s", dealerHand.getHandString()));
            dealerSumText.setText(String.format(Locale.getDefault(), "Sum: %d", dealerHand.getSum()));
        }
    }

    private void hit() {
        playerHand.addCard();
        updateHandUI();
        playerRules();
    }

    private void playerRules() {
        if (playerHand.getSum() > FINAL_NUMBER_21) {
            playerLose();
        } else if (playerHand.getSum() == FINAL_NUMBER_21) {
            playerStand();
        }
    }

    private void playerStand() {
        isButtonsActive = false;
        buttonActivity();

        isPlayerActive = false;
        isDealerActive = true;
        setActivity();

        dealerCards.setText(dealerHand.getHandString());

        dealerHand.startDealersGame();
    }

    private void compareHands() {
        if (playerHand.getSum() <= FINAL_NUMBER_21 && (dealerHand.getSum() > FINAL_NUMBER_21 || playerHand.getSum() > dealerHand.getSum())) {
            playerWin();
        } else if (playerHand.getSum() == dealerHand.getSum()) {
            gameDraw();
        } else {
            playerLose();
        }
    }

    private void startMoneyBet() {
        if (setMoney.getText().toString().length() > 0) {
            moneyBetting();

            isButtonsActive = true;
            buttonActivity();
        } else {
            Toast.makeText(this, "Set a bet", Toast.LENGTH_SHORT).show();
        }
    }

    private void moneyBetting() {
        if (setMoney.getText().toString().length() > 0) {
            amountOfMoneyBet = Integer.parseInt(setMoney.getText().toString());

            if (currentBalance > amountOfMoneyBet) {
                balanceAndBetDiff = (currentBalance - amountOfMoneyBet);
                balance.setText(balanceAndBetDiff + "€");
            } else {
                Toast toast = Toast.makeText(this, "You don't have enough money!", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toastView.setBackgroundResource(R.color.light_blue_A200);
                toast.show();
            }
        }
    }

    private void surrender() {
        setMoney.setText(null);
        int halfOfTheMoney = amountOfMoneyBet / 2;
        currentBalance -= halfOfTheMoney;
        gameEnd(surrenderText);
    }

    private void gameDraw() {
        setMoney.setText(null);
        gameEnd(drawText);
    }

    private void playerWin() {
        currentBalance += amountOfMoneyBet;
        setMoney.setText(null);
        gameEnd(winningText);
    }

    private void playerLose() {
        currentBalance = balanceAndBetDiff;
        setMoney.setText(null);
        gameEnd(losingText);
    }

    private void gameEnd(View view) {
        isPlayerActive = false;
        isDealerActive = false;
        setActivity();

        view.setVisibility(View.VISIBLE);
        isGameRunning = false;
        setViewVisibility();

        newGameBtn.setVisibility(View.VISIBLE);
    }

    private void buttonActivity() {
        if (!isButtonsActive) {
            hitBtn.setClickable(false);
            standBtn.setClickable(false);
            surrenderBtn.setClickable(false);

            hitBtn.setBackgroundColor(Color.GRAY);
            standBtn.setBackgroundColor(Color.GRAY);
            surrenderBtn.setBackgroundColor(Color.GRAY);
        } else {
            hitBtn.setClickable(true);
            standBtn.setClickable(true);
            surrenderBtn.setClickable(true);

            hitBtn.setBackgroundColor(Color.BLACK);
            standBtn.setBackgroundColor(Color.BLACK);
            surrenderBtn.setBackgroundColor(Color.BLACK);
        }
    }

    private void setActivity() {
        if (isPlayerActive) {
            playerTitleText.setTextColor(Color.RED);
            playerSumText.setTextColor(Color.RED);
        } else if (isDealerActive) {
            dealerTitleText.setTextColor(Color.RED);
            dealerSumText.setTextColor(Color.RED);
            playerTitleText.setTextColor(Color.BLACK);
            playerSumText.setTextColor(Color.BLACK);
        } else {
            playerTitleText.setTextColor(Color.BLACK);
            dealerTitleText.setTextColor(Color.BLACK);
            playerSumText.setTextColor(Color.BLACK);
            dealerSumText.setTextColor(Color.BLACK);
        }
    }

    private void setViewVisibility() {
        toggleGameRunningVisibility(hitBtn);
        toggleGameRunningVisibility(standBtn);
        toggleGameRunningVisibility(surrenderBtn);
        toggleGameRunningVisibility(betBtn);
        toggleGameRunningVisibility(moneyBetText);
        toggleGameRunningVisibility(balance);
        toggleGameRunningVisibility(setMoney);
        toggleGameRunningVisibility(playerCards);
        toggleGameRunningVisibility(dealerCards);
    }

    private void toggleGameRunningVisibility(View view) {
        if (!isGameRunning) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}