package com.choicely.learn.testingapp;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CitySearchActivity extends AppCompatActivity {

    private static final String TAG = "CitySearchActivity";
    private static final String CITY_FULL_NAME_JSON = "full_name";
    private static final String DATA_ROOT_JSON = "data";
    private final List<String> list = new ArrayList<>();

    private AutoCompleteTextView searchBar;
    private String cityFullName;
    private ProgressBar loadingIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_search_activity);

        searchBar = findViewById(R.id.city_search_activity_search_bar);
        loadingIndicator = findViewById(R.id.city_search_activity_loading);

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadingIndicator.setVisibility(View.VISIBLE);
                createUrlAndAddCitiesToAdapter();
                Handler handler = new Handler();
                handler.postDelayed(() -> loadingIndicator.setVisibility(View.INVISIBLE), 3000);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void createUrlAndAddCitiesToAdapter() {
        String city = searchBar.getText().toString();
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
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {
                    try {
                        String jsonString = response.body().string();
                        JSONObject reader = new JSONObject(jsonString);
                        JSONArray data = reader.getJSONArray(DATA_ROOT_JSON);

                        for(int i = 0; i < data.length(); i++){
                            JSONObject jsonObject = data.getJSONObject(i);
                            cityFullName = jsonObject.getString(CITY_FULL_NAME_JSON); //full_name
                            list.add(cityFullName);
                        }

                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, list);
        searchBar.setAdapter(adapter);
    }
}

