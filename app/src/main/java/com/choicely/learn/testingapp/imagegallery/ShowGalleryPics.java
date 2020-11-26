package com.choicely.learn.testingapp.imagegallery;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.choicely.learn.testingapp.R;

public class ShowGalleryPics extends AppCompatActivity {

    private ViewPager2 viewPager;
    ImageGalleryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_gallery_pics);

        viewPager = findViewById(R.id.view_pager_activity_pager);
        adapter = new ImageGalleryAdapter(this);
        viewPager.setAdapter(adapter);
    }
}
