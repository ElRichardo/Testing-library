package com.choicely.learn.testingapp.sulkeiset;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.learn.testingapp.R;

import java.util.ArrayList;
import java.util.List;

public class SulkeisetActivity extends AppCompatActivity {

    private LiikenneValot liikenneValot = new LiikenneValot();

    private LinearLayout linearLayout;

    private List<View> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sulkeiset_activity);

        linearLayout = findViewById(R.id.sulkeiset_activity_layout);

        liikenneValot.createLights();

        for (Lamppu lamppu : liikenneValot.list) {
            View view = new View(this);
            if (lamppu.isActive()) {
                view.setBackgroundColor(lamppu.getColor());
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
            params.gravity = Gravity.CENTER;

            view.setLayoutParams(params);
            linearLayout.addView(view);
            list.add(view);
        }

        linearLayout.setOnClickListener(v -> {
            liikenneValot.updateStatus();
        });

        LiikenneValot.StatusChange statusChange = new LiikenneValot.StatusChange() {
            @Override
            public void onStatusChanged() {
                Toast.makeText(SulkeisetActivity.this, "ontouch", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < list.size(); i++) {
                    if (liikenneValot.list.get(i).isActive()) {
                        list.get(i).setBackgroundColor(liikenneValot.list.get(i).getColor());
                    } else {
                        list.get(i).setBackgroundColor(Color.WHITE);
                    }
                }
            }
        };
        liikenneValot.setStatusChange(statusChange);
    }
}
