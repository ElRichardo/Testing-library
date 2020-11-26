package com.choicely.learn.testingapp.receiptsave;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.choicely.learn.testingapp.R;
import com.choicely.learn.testingapp.db.RealmHelper;

import io.realm.Realm;
import io.realm.RealmResults;

public class ReceiptSavingActivity extends AppCompatActivity {

    private static final String TAG = "ReceiptSavingActivity";

    private ImageButton cameraButton;
    private ImageButton searchButton;
    private EditText searchBar;
    private int picID;

    private RecyclerView recyclerView;
    private ReceiptAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receipt_saving_activity);

        cameraButton = findViewById(R.id.receipt_saving_activity_camera);
        searchButton = findViewById(R.id.receipt_saving_activity_search);
        searchBar = findViewById(R.id.receipt_saving_activity_textField);

        recyclerView = findViewById(R.id.receipt_saving_activity_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReceiptAdapter(this);
        recyclerView.setAdapter(adapter);


        cameraButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, CameraActivity.class);
            intent.putExtra("picID", picID);
            startActivity(intent);
        });

        //I could also add a textchangelistener which makes the query as soon as the user writes to the field
        searchButton.setOnClickListener(v -> {
            updateContent();
        });
    }



    private void updateContent() {
        adapter.clear();

        RealmHelper helper = RealmHelper.getInstance();
        Realm realm = helper.getRealm();

        RealmResults<ReceiptData> receipts = realm.where(ReceiptData.class)
                .contains("title", searchBar.getText().toString())
                .or()
                .contains("date", searchBar.getText().toString())
                .findAll();

        for (ReceiptData receipt : receipts) {
            adapter.add(receipt);
        }

        adapter.notifyDataSetChanged();
    }
}
