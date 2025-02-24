package com.choicely.learn.testingapp.blackjack;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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
    private static final String RULES_LINK = "https://bicyclecards.com/how-to-play/blackjack/";
    private static final int FINAL_NUMBER_21 = 21;

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
    private TextView balanceText;
    private EditText setMoney;
    private Button newGameBtn;
    private Button hitBtn;
    private Button standBtn;
    private Button surrenderBtn;
    private Button blackjackRulesBtn;
    private Button betBtn;
    private boolean isGameRunning = false;
    private boolean isPlayerActive;
    private boolean isDealerActive;
    private boolean isButtonsActive;
    private boolean isPlayerBlackJack;
    private boolean isDealerBlackJack;

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
    };
    private final PlayerHand playerHand = new PlayerHand(onPlayerHandFinishedListener);

    private final Balance balance = new Balance();

    //bet instance differs from balance because balance is the same balance every game
    //unlike bet, which is always set again in the beginning of a game
    @Nullable
    private BetMoney bet;

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
        blackjackRulesBtn = findViewById(R.id.black_jack_activity_rules);
        betBtn = findViewById(R.id.black_jack_activity_bet);
        moneyBetText = findViewById(R.id.black_jack_activity_money_bet_text);
        balanceText = findViewById(R.id.black_jack_activity_balance);
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

        balance.balanceAtStart();
    }

    //switch case is not used because it'll be deprecated in the upcoming android update
    public void onClick(View v) {
        if (v == newGameBtn) {
            newGame();
        } else if (v == hitBtn) {
            hit();
        } else if (v == standBtn) {
            playerStand();
        } else if (v == betBtn) {
            bet();
        } else if (v == surrenderBtn) {
            surrender();
        } else if (v == blackjackRulesBtn) {
            blackjackRulesLink();
        }
    }

    private void newGame() {
        betBtn.setBackgroundColor(Color.BLACK);
        betBtn.setClickable(true);

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

        balanceText.setText(String.format(Locale.getDefault(), "%d€", balance.getBalance()));
    }

    private void updateHandUI() {
        {
            playerCards.setText(String.format(Locale.getDefault(), "%s", playerHand.getHandString()));
            int sum = playerHand.getSum();
            int altSum = playerHand.getAltSum();
            if (sum == altSum) {
                playerSumText.setText(String.format(Locale.getDefault(), "Sum: %d", sum));
            } else {
                playerSumText.setText(String.format(Locale.getDefault(), "Sum: %d / %d", sum, altSum));
            }
        }
        {
            dealerCards.setText(String.format(Locale.getDefault(), "%s", dealerHand.getHandString()));
            int sum = dealerHand.getSum();
            int altSum = dealerHand.getAltSum();
            if (sum == altSum) {
                dealerSumText.setText(String.format(Locale.getDefault(), "Sum: %d", sum, altSum));
            } else {
                dealerSumText.setText(String.format(Locale.getDefault(), "Sum: %d / %d", sum, altSum));
            }
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

    private void bet() {
        String setMoneyString = setMoney.getText().toString().replaceAll("[^\\d-]", ""); //[^\\d-] replace everything except numeric values and minus sign
        if (setMoneyString.length() > 0) {
            int moneyBet = Integer.parseInt(setMoney.getText().toString());
            bet = new BetMoney();
            bet.setMoneyBet(moneyBet);

            beginGameIfBetIsProper();
        } else {
            Toast.makeText(this, "Set a bet", Toast.LENGTH_SHORT).show();
        }
    }

    private void beginGameIfBetIsProper() {
        if (balance.getBalance() >= bet.getMoneyBet() && bet.getMoneyBet() > 0) {
            setBalanceAfterBet();
            //looks better with this
            closeKeyBoard();

            isButtonsActive = true;
            buttonActivity();

            betBtn.setBackgroundColor(Color.GRAY);
            betBtn.setClickable(false);

            gameStart();
        } else if (bet.getMoneyBet() < 0) {
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

    private void setBalanceAfterBet() {
        int balanceAfterBet = balance.getBalance() - bet.getMoneyBet();

        balance.setBalance(balanceAfterBet);
        balanceText.setText(String.format(Locale.getDefault(), "%d€", balanceAfterBet));
    }

    private void gameStart() {
        dealerHand.addCard();

        playerHand.addCard();
        playerHand.addCard();
        handler.postDelayed(playerHand::checkIfBlackJack, 2000);

        updateHandUI();
    }

    private void playerWin() {
        bet.win();
        balance.setBalance(balance.getBalance() + bet.getMoneyBet());
        gameEnd(winningText);
    }

    private void blackJackPlayerWin() {
        bet.blackJackWin();
        balance.setBalance(balance.getBalance() + bet.getMoneyBet());
        gameEnd(winningText);
    }

    private void gameDraw() {
        //returns the bet money to the balance
        balance.draw(bet.getMoneyBet());
        gameEnd(drawText);
    }

    private void playerLose() {
        bet.lose();
        gameEnd(losingText);
    }

    private void surrender() {
        bet.surrender();
        balance.setBalance(balance.getBalance() + bet.getMoneyBet());
        gameEnd(surrenderText);
    }

    private void gameEnd(@NotNull View view) {
        setMoney.setText(null);

        isPlayerActive = false;
        isDealerActive = false;
        setActivity();

        view.setVisibility(View.VISIBLE);
        isGameRunning = false;
        setViewVisibility();

        newGameBtn.setVisibility(View.VISIBLE);
    }

    private void blackjackRulesLink() {
        Uri uri = Uri.parse(RULES_LINK);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private void showBlackJackText(@NotNull TextView textView) {
        textView.setVisibility(View.VISIBLE);
        handler.postDelayed(() -> textView.setVisibility(View.INVISIBLE), 1000);
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
        toggleGameRunningVisibility(balanceText);
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