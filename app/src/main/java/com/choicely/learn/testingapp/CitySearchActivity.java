package com.choicely.learn.testingapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CitySearchActivity extends AppCompatActivity {

    private static final String TAG = "CitySearchActivity";

    private AutoCompleteTextView searchBar;
    private String city;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_search_activity);

        searchBar = findViewById(R.id.city_search_activity_search_bar);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                createLinkAndAddCitiesToAdapter();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        searchBar.setAdapter(adapter);
    }

    private void createLinkAndAddCitiesToAdapter() {
        city = searchBar.getText().toString();
        HttpUrl url = new HttpUrl.Builder()
                .scheme("https")
                .host("geo-test.choicely.com")
                .addPathSegment("search")
                .addPathSegment("cities")
                .addPathSegment(city)
                .build();
        Log.d(TAG, "url: " + url);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {

            }
        });

    }

    private static final String[] COUNTRIES = new String[]{
            "Belgium", "France", "Italy", "Germany", "Spain"
    };
}

