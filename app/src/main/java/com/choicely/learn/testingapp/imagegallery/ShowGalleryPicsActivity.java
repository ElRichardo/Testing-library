package com.choicely.learn.testingapp.imagegallery;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.choicely.learn.testingapp.R;
import com.choicely.learn.testingapp.db.RealmHelper;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShowGalleryPicsActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    ImageGalleryAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_gallery_pics_activity);

        viewPager = findViewById(R.id.show_gallery_pics_pager);
        adapter = new ImageGalleryAdapter(this);
        viewPager.setAdapter(adapter);

        updateContent();
    }

    private void updateContent() {
        adapter.clear();

        RealmHelper helper = RealmHelper.getInstance();
        Realm realm = helper.getRealm();

        RealmResults<ImageGalleryData> images = realm.where(ImageGalleryData.class).findAll();

        for(ImageGalleryData image: images){
            adapter.add(image);
        }

        adapter.notifyDataSetChanged();
    }
}
