package com.choicely.learn.testingapp.blackjack;

import android.graphics.Color;
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

    private final Handler handler = new Handler();
    private Random random;
    private TextView losingText;
    private TextView winningText;
    private TextView drawText;
    private TextView dealerCards;
    private TextView playerCards;
    private TextView playerSumText;
    private TextView dealerSumText;
    private TextView playerTitleText;
    private TextView dealerTitleText;
    private EditText setMoney;
    private Button newGameBtn;
    private Button hitBtn;
    private Button standBtn;
    private Button surrenderBtn;
    private Button betBtn;
    private boolean isVisibility = false;
    private boolean isPlayerActive;
    private boolean isDealerActive;
    private boolean isStandAndHitActive;
    private int playerSum;
    private int dealerSum;

    private final List<Integer> dealerCardList = new ArrayList<>();
    private final List<Integer> playerCardList = new ArrayList<>();

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
        setMoney = findViewById(R.id.black_jack_activity_set_money);
        losingText = findViewById(R.id.black_jack_activity_lost);
        winningText = findViewById(R.id.black_jack_activity_won);
        drawText = findViewById(R.id.black_jack_activity_draw);
        playerSumText = findViewById(R.id.black_jack_activity_player_sum);
        dealerSumText = findViewById(R.id.black_jack_activity_dealer_sum);
        playerTitleText = findViewById(R.id.black_jack_activity_player_text);
        dealerTitleText = findViewById(R.id.black_jack_activity_dealer_text);

        setViewVisibility();
    }

    //switch case is not used because it'll be deprecated in the upcoming android update
    public void onClick(View v) {
        if (v == newGameBtn) {
            newGame();
        } else if (v == hitBtn) {
            hit();
        } else if (v == standBtn) {
            stand();
        }
    }

    private void newGame() {
        isStandAndHitActive = true;
        StandAndHitActivity();

        isPlayerActive = true;
        isDealerActive = false;
        setActivity();

        losingText.setVisibility(View.INVISIBLE);
        winningText.setVisibility(View.INVISIBLE);
        drawText.setVisibility(View.INVISIBLE);

        gameStart();
    }

    private void gameStart() {
        dealerCardList.clear();
        playerCardList.clear();
        newGameBtn.setVisibility(View.GONE);

        random = new Random();
        int dealerCard = random.nextInt(10 - 1) + 1;
        int cardFaceDown = random.nextInt(10 - 1) + 1;
        int playerCard1 = random.nextInt(10 - 1) + 1;
        int playerCard2 = random.nextInt(10 - 1) + 1;

        dealerCards.setText(String.format(Locale.getDefault(), "%d\t%s", dealerCard, "?"));
        playerCards.setText(String.format(Locale.getDefault(), "%d\t%d", playerCard1, playerCard2));

        //putting updateListSum here might be an ugly solution
        dealerCardList.add(dealerCard);
        updateListSum(dealerCardList);
        dealerCardList.add(cardFaceDown);

        playerCardList.add(playerCard1);
        playerCardList.add(playerCard2);

        isVisibility = true;
        setViewVisibility();

        updateListSum(playerCardList);
    }

    private void updateListSum(List<Integer> list) {
        int sum = 0;
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
    }

    private void hit() {
        addCardTo(playerCardList);
        updateListSum(playerCardList);
        playerRules();
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

    private void playerRules() {
        if (playerSum > FINAL_NUMBER_21) {
            playerLose();
        } else if (playerSum == FINAL_NUMBER_21) {
            stand();
            updateListSum(playerCardList);
        }
    }

    private void stand() {
        isStandAndHitActive = false;
        StandAndHitActivity();

        isPlayerActive = false;
        isDealerActive = true;
        setActivity();

        dealerCardsAtStart();
        handler.postDelayed(this::dealersGameAccordingToRules, 2000);
    }

    private void dealerCardsAtStart() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < dealerCardList.size(); i++) {
            String everyCard = dealerCardList.get(i).toString();
            builder.append(everyCard + "\t");
        }
        dealerCards.setText(builder.toString());
        updateListSum(dealerCardList);
    }

    private void dealersGameAccordingToRules() {
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

    private void compareHands() {
        if (playerSum > dealerSum) {
            playerWin();
        } else if (playerSum == dealerSum) {
            GameDraw();
        } else {
            playerLose();
        }
    }

    private void GameDraw() {
        gameEnd(drawText);
    }

    private void playerWin() {
        gameEnd(winningText);
    }

    private void playerLose() {
        gameEnd(losingText);
    }

    private void gameEnd(View view) {
        isPlayerActive = false;
        isDealerActive = false;
        setActivity();

        view.setVisibility(View.VISIBLE);
        isVisibility = false;
        setViewVisibility();

        newGameBtn.setVisibility(View.VISIBLE);
    }

    private void StandAndHitActivity() {
        if(!isStandAndHitActive) {
            hitBtn.setClickable(false);
            standBtn.setClickable(false);

            hitBtn.setBackgroundColor(Color.GRAY);
            standBtn.setBackgroundColor(Color.GRAY);
        } else{
            hitBtn.setClickable(true);
            standBtn.setClickable(true);

            hitBtn.setBackgroundColor(Color.BLACK);
            standBtn.setBackgroundColor(Color.BLACK);
        }
    }

    private void setActivity() {
        if (isPlayerActive) {
            playerTitleText.setTextColor(Color.RED);
            playerSumText.setTextColor(Color.RED);
        } else if (isDealerActive) {
            playerTitleText.setTextColor(Color.BLACK);
            dealerTitleText.setTextColor(Color.RED);
            dealerSumText.setTextColor(Color.RED);
        } else {
            playerTitleText.setTextColor(Color.BLACK);
            dealerTitleText.setTextColor(Color.BLACK);
            playerSumText.setTextColor(Color.BLACK);
            dealerSumText.setTextColor(Color.BLACK);
        }
    }

    private void setViewVisibility() {
        viewVisibilitySet(hitBtn);
        viewVisibilitySet(standBtn);
        viewVisibilitySet(surrenderBtn);
        viewVisibilitySet(betBtn);
        viewVisibilitySet(setMoney);
        viewVisibilitySet(playerCards);
        viewVisibilitySet(dealerCards);
    }

    private void viewVisibilitySet(View view) {
        if (!isVisibility) {
            view.setVisibility(View.INVISIBLE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}