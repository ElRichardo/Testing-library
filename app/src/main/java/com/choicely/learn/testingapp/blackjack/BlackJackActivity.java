package com.choicely.learn.testingapp.blackjack;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.learn.testingapp.R;

import org.jetbrains.annotations.NotNull;

import java.util.Locale;

public class BlackJackActivity extends AppCompatActivity {

    private static final String TAG = "BlackJackActivity";
    private static final int FINAL_NUMBER_21 = 21;
    private static final int BALANCE_AT_START = 500;

    private TextView surrenderText;
    private TextView losingText;
    private TextView winningText;
    private TextView drawText;
    private TextView dealerBlackJackText;
    private TextView playerBlackJackText;
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
    private boolean isPlayerActive;
    private boolean isDealerActive;
    private boolean isButtonsActive;
    private boolean isPlayerBlackJack;
    private boolean isDealerBlackJack;
    private int currentBalance;
    private int amountOfMoneyBet;
    private int balanceAndBetDiff;
    private String setMoneyString;

    private final Handler handler = new Handler();

    private final DealerHand.OnHandFinishedListener onDealerHandFinishedListener = sum -> {
        if (sum == 21) {
            isDealerBlackJack = true;
            showBlackJackText(dealerBlackJackText);
            //delay so that blackjack text has time to be on screen
            handler.postDelayed(this::compareHands, 2000);
        } else {
            //without delay so that it doesn't take too long
            compareHands();
        }
    };
    private final DealerHand.OnHandChanged onHandChanged = this::updateHandUI;
    private final DealerHand dealerHand = new DealerHand(onDealerHandFinishedListener, onHandChanged);

    private final PlayerHand.onPlayerHandFinishedListener onPlayerHandFinishedListener = sum -> {
        if (sum == 21) {
            isPlayerBlackJack = true;
            showBlackJackText(playerBlackJackText);
        }
        playerStand();
        Log.d(TAG, "playerStand()");
    };
    private final PlayerHand playerHand = new PlayerHand(onPlayerHandFinishedListener);

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
        dealerBlackJackText = findViewById(R.id.black_jack_activity_dealer_bj_text);
        playerBlackJackText = findViewById(R.id.black_jack_activity_player_bj_text);

        setViewVisibility();

        currentBalance = BALANCE_AT_START;
    }

    //switch case is not used because it'll be deprecated in the upcoming android update
    public void onClick(View v) {
        if (v == newGameBtn) {
            newGame();
            balance.setText(String.format(Locale.getDefault(), "%d€", currentBalance));
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

        dealerHand.clear();
        dealerCards.setText(null);
        dealerSumText.setText(null);

        playerHand.clear();
        playerCards.setText(null);
        playerSumText.setText(null);

        isPlayerBlackJack = false;
        isDealerBlackJack = false;

        newGameBtn.setVisibility(View.INVISIBLE);
        losingText.setVisibility(View.INVISIBLE);
        winningText.setVisibility(View.INVISIBLE);
        drawText.setVisibility(View.INVISIBLE);
        surrenderText.setVisibility(View.INVISIBLE);

        isGameRunning = true;
        setViewVisibility();
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
        playerHand.addCard(1);
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

    private void gameStart() {
        dealerHand.addCard(1);

        playerHand.addCard(10);
        playerHand.addCard(1);
        handler.postDelayed(playerHand::checkIfBlackJack, 2000);

        updateHandUI();
    }

    private void startMoneyBet() {
        setMoneyString = setMoney.getText().toString().replaceAll("[^\\d-]", "");//[^\\d-] replace everything except numeric values and minus sign
        if (setMoneyString.length() > 0) {
            moneyBetting();
        } else {
            Toast.makeText(this, "Set a bet", Toast.LENGTH_SHORT).show();
        }
    }

    private void moneyBetting() {
        if (setMoneyString.length() > 0) {
            amountOfMoneyBet = Integer.parseInt(setMoneyString);
            if (currentBalance >= amountOfMoneyBet && amountOfMoneyBet > 0) {
                balanceAndBetDiff = (currentBalance - amountOfMoneyBet);
                balance.setText(String.format(Locale.getDefault(), "%d€", balanceAndBetDiff));

                gameStart();
                //looks better with this
                closeKeyBoard();

                isButtonsActive = true;
                buttonActivity();
            } else if (amountOfMoneyBet < 0) {
                Toast toast = Toast.makeText(this, "Money can't be negative", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toastView.setBackgroundResource(R.color.light_blue_A200);
                toast.show();
            } else {
                Toast toast = Toast.makeText(this, "You don't have enough money!", Toast.LENGTH_SHORT);
                View toastView = toast.getView();
                toastView.setBackgroundResource(R.color.light_blue_A200);
                toast.show();
            }
        }
    }

    /**
     * so that the keyboard wouldn't be in the way of the cards
     */
    private void closeKeyBoard() {
        View view = this.getCurrentFocus();

        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void compareHands() {
        if (playerHand.getSum() <= FINAL_NUMBER_21 && dealerHand.getSum() > FINAL_NUMBER_21) {
            playerWin();
        } else if (playerHand.getSum() > dealerHand.getSum() && !isPlayerBlackJack && !isDealerBlackJack) {
            playerWin();
        } else if (playerHand.getSum() >= dealerHand.getSum() && isPlayerBlackJack && !isDealerBlackJack) {
            blackJackPlayerWin();
        } else if (playerHand.getSum() == dealerHand.getSum() && !isPlayerBlackJack && !isDealerBlackJack) {
            gameDraw();
        } else if (isPlayerBlackJack && isDealerBlackJack) {
            gameDraw();
        } else {
            playerLose();
        }
    }

    private void playerWin() {
        setMoney.setText(null);
        currentBalance += amountOfMoneyBet;
        gameEnd(winningText);
    }

    private void blackJackPlayerWin() {
        setMoney.setText(null);
        currentBalance += amountOfMoneyBet * 1.5;
        gameEnd(winningText);
    }

    private void gameDraw() {
        setMoney.setText(null);
        gameEnd(drawText);
    }

    private void playerLose() {
        setMoney.setText(null);
        currentBalance = balanceAndBetDiff;
        gameEnd(losingText);
    }

    private void surrender() {
        setMoney.setText(null);
        int halfOfTheMoney = amountOfMoneyBet / 2;
        currentBalance -= halfOfTheMoney;
        gameEnd(surrenderText);
    }

    private void gameEnd(@NotNull View view) {
        isPlayerActive = false;
        isDealerActive = false;
        setActivity();

        view.setVisibility(View.VISIBLE);
        isGameRunning = false;
        setViewVisibility();

        newGameBtn.setVisibility(View.VISIBLE);
    }

    private void showBlackJackText(@NotNull TextView textView) {
        Log.d(TAG, "visible");
        textView.setVisibility(View.VISIBLE);
        handler.postDelayed(() -> textView.setVisibility(View.INVISIBLE), 1000);
        Log.d(TAG, "invisible");
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