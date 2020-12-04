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
    private static final String CARD_FACE_DOWN = "?";
    private static final int FINAL_NUMBER_21 = 21;

    private Random random;
    private TextView losingText;
    private TextView dealerCards;
    private TextView playerCards;
    private EditText setMoney;
    private Button startBtn;
    private Button hitBtn;
    private Button standBtn;
    private Button surrenderBtn;
    private Button betBtn;
    private boolean isVisibility = false;
    private int sum;

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

        setButtonVisibility();

        startBtn.setOnClickListener(v -> {
            startBtn.setVisibility(View.GONE);
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                startingPosition();
                hitBtn.setEnabled(true); //I set it disabled first so the user couldn't hit before the starting position is printed
            }, 1500);

            isVisibility = true;
            setButtonVisibility();
        });

        hitBtn.setOnClickListener(v -> {
            addCard();
            sumOfArrayList(playerCardList);
            if(sum > FINAL_NUMBER_21){
                losingText.setVisibility(View.VISIBLE);
                isVisibility = false;
                setButtonVisibility();
            }
        });
    }

    private int sumOfArrayList(List<Integer> list) {
        sum = 0;
        for(int i : list){
            sum += i;
        }
        Log.d(TAG, "sum: " + sum);
        return sum;
    }

    private void addCard() {
        playerCardList.add(random.nextInt(10-1));
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < playerCardList.size(); i++){
            String everyCard = playerCardList.get(i).toString();
            builder.append(everyCard + "\t");
        }

        playerCards.setText(builder.toString());
        Log.d(TAG, "pelaajan lista: " + playerCardList);
        Log.d(TAG, "builder: " + builder.toString());
    }

    private void startingPosition() {
        random = new Random();
        int dealerCard = random.nextInt(10-1);
        int playerCard1 = random.nextInt(10-1);
        int playerCard2 = random.nextInt(10-1);

        dealerCards.setText(String.format(Locale.getDefault(), "%s\n%d", CARD_FACE_DOWN, dealerCard));
        playerCards.setText(String.format(Locale.getDefault(), "%d\n%d", playerCard1, playerCard2));

//        dealerCardList.add(dealerCard);
        playerCardList.add(playerCard1);
        playerCardList.add(playerCard2);
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
        if(!isVisibility) {
            view.setVisibility(View.INVISIBLE);
        } else{
            view.setVisibility(View.VISIBLE);
        }
    }
}
