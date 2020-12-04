package com.choicely.learn.testingapp.blackjack;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.learn.testingapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class BlackJackActivity extends AppCompatActivity {

    private static final String TAG = "BlackJackActivity";
    private static final int FINAL_NUMBER_21 = 21;

    private Random random;
    private Handler handler = new Handler();
    private TextView losingText;
    private TextView winningText;
    private TextView dealerCards;
    private TextView playerCards;
    private TextView playerSumText;
    private TextView dealerSumText;
    private EditText setMoney;
    private Button startBtn;
    private Button hitBtn;
    private Button standBtn;
    private Button surrenderBtn;
    private Button betBtn;
    private boolean isVisibility = false;
    private int sum;
    private int playerSum;
    private int dealerSum;
    private int cardFaceDown;

    private List<Integer> dealerCardList = new ArrayList<>();
    private List<Integer> playerCardList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.black_jack_activity);

        dealerCards = findViewById(R.id.black_jack_activity_dealer_cards);
        playerCards = findViewById(R.id.black_jack_activity_player_cards);
        startBtn = findViewById(R.id.black_jack_activity_start);
        hitBtn = findViewById(R.id.black_jack_activity_hit);
        standBtn = findViewById(R.id.black_jack_activity_stand);
        surrenderBtn = findViewById(R.id.black_jack_activity_surrender);
        betBtn = findViewById(R.id.black_jack_activity_bet);
        setMoney = findViewById(R.id.black_jack_activity_set_money);
        losingText = findViewById(R.id.black_jack_activity_lost);
        winningText = findViewById(R.id.black_jack_activity_won);
        playerSumText = findViewById(R.id.black_jack_activity_player_sum);
        dealerSumText = findViewById(R.id.black_jack_activity_dealer_sum);

        setButtonVisibility();
    }

    //switch case is not used because it'll be deprecated in the upcoming android update
    public void onClick(View v) {
        if (v == startBtn) {
            gameStart();
        } else if (v == hitBtn) {
            hitAccordingToPlayerRules();
        } else if (v == standBtn) {
            dealerCardsAtStart();
            dealersGame();
        }
    }

    private void gameStart() {
        startBtn.setVisibility(View.GONE);

        random = new Random();
        int dealerCard = random.nextInt(10 - 1) + 1;
        cardFaceDown = random.nextInt(10 - 1) + 1;
        int playerCard1 = random.nextInt(10 - 1) + 1;
        int playerCard2 = random.nextInt(10 - 1) + 1;

        dealerCards.setText(String.format(Locale.getDefault(), "%s\t%d", "?", dealerCard));
        playerCards.setText(String.format(Locale.getDefault(), "%d\t%d", playerCard1, playerCard2));

        dealerCardList.add(cardFaceDown);
        dealerCardList.add(dealerCard);
        playerCardList.add(playerCard1);
        playerCardList.add(playerCard2);

        hitBtn.setEnabled(true); //I set it disabled first so the user couldn't hit before the starting position is printed

        isVisibility = true;
        setButtonVisibility();

        updateListSum(playerCardList);
    }

    private int updateListSum(List<Integer> list) {
        sum = 0;
        for (int i : list) {
            sum += i;
        }
        if (list == playerCardList) {
            playerSumText.setText(String.format(Locale.getDefault(), "Sum: %d", sum));
            playerSum = sum;
            Log.d(TAG, "sumOfArrayList: " + playerSum);

        } else {
            dealerSumText.setText(String.format(Locale.getDefault(), "Sum: %d", sum));
            dealerSum = sum;
        }
        return sum;
    }

    private void addCardTo(List<Integer> list) {
        list.add(random.nextInt(10 - 1) + 1);
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < list.size(); i++) {
            String everyCard = list.get(i).toString();
            builder.append(everyCard + "\t");
        }

        if (list == playerCardList) {
            playerCards.setText(builder.toString());
        } else {
            dealerCards.setText(builder.toString());
        }
        Log.d(TAG, "list: " + list);
    }

    private void hitAccordingToPlayerRules() {
        updateListSum(playerCardList);
        if (playerSum > FINAL_NUMBER_21) {
            playerLose();
        } else if (playerSum == FINAL_NUMBER_21) {
            dealersGame();
        } else if (playerSum < FINAL_NUMBER_21) {
            addCardTo(playerCardList);
            updateListSum(playerCardList);
        }
    }

    private void dealerCardsAtStart() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < dealerCardList.size(); i++) {
            String everyCard = dealerCardList.get(i).toString();
            builder.append(everyCard + "\t");
        }
        dealerCards.setText(builder.toString());
    }

    private void dealersGame() {
        Log.d(TAG, "dealersGame: ");
        updateListSum(dealerCardList);
        if (dealerSum < 17) {
            addCardTo(dealerCardList);
            updateListSum(dealerCardList);
            handler.postDelayed(this::dealersGame, 2000);
        } else {
            //dealer must stand
            compareHands();
        }
    }

    private void compareHands() {
        if (playerSum > dealerSum) {
            playerWin();
        } else {
            playerLose();
        }
    }

    private void playerWin() {
        winningText.setVisibility(View.VISIBLE);
        isVisibility = false;
        setButtonVisibility();
    }

    private void playerLose() {
        losingText.setVisibility(View.VISIBLE);
        isVisibility = false;
        setButtonVisibility();
    }

    private void setButtonVisibility() {
        btnVisibilitySet(hitBtn);
        btnVisibilitySet(standBtn);
        btnVisibilitySet(surrenderBtn);
        btnVisibilitySet(betBtn);
        btnVisibilitySet(setMoney);
        btnVisibilitySet(playerCards);
        btnVisibilitySet(dealerCards);
    }

    private void btnVisibilitySet(View view) {
        if (!isVisibility) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}