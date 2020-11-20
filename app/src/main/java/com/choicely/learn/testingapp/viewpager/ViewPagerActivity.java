package com.choicely.learn.testingapp.viewpager;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.choicely.learn.testingapp.R;
import com.choicely.learn.testingapp.viewpager.ViewPagerAdapter;

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager2 viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_activity);

        viewPager = findViewById(R.id.view_pager_activity_pager);
        viewPager.setAdapter(new ViewPagerAdapter(this));
    }
}
