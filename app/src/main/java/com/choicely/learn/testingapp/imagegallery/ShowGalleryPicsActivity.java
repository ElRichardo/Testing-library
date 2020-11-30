package com.choicely.learn.testingapp.imagegallery;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.choicely.learn.testingapp.R;
import com.choicely.learn.testingapp.db.RealmHelper;

import io.realm.Realm;
import io.realm.RealmResults;

public class ShowGalleryPicsActivity extends AppCompatActivity {

    private static final String TAG = "ShowGalleryPicsActivity";
    private static final String JPG_ITEM = ".jpg";
    private static final String PNG_ITEM = ".png";

    private ViewPager2 viewPager;
    ImageGalleryAdapter adapter;
    private Spinner imageTypeDropDown;
    private String suffix;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_gallery_pics_activity);

        imageTypeDropDown = findViewById(R.id.show_gallery_pics_type_dropdown);

        viewPager = findViewById(R.id.show_gallery_pics_pager);
        adapter = new ImageGalleryAdapter(this);
        viewPager.setAdapter(adapter);

        //updateContent();
        setDropDownAdapter();

        imageTypeDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = imageTypeDropDown.getSelectedItem().toString();

                if(selectedItem.equals(JPG_ITEM)){
                    suffix = JPG_ITEM;
                } else if(selectedItem.equals(PNG_ITEM)){
                    suffix = PNG_ITEM;
                }
                addToAdapterByType();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void addToAdapterByType() {
        adapter.clear();
        RealmHelper helper = RealmHelper.getInstance();
        Realm realm = helper.getRealm();

        if(suffix != null) {
            RealmResults<ImageGalleryData> images = realm.where(ImageGalleryData.class).contains("pictureUrl", suffix).findAll();

            for (ImageGalleryData image : images) {
                adapter.add(image);
            }

            adapter.notifyDataSetChanged();
        }
    }

    private void setDropDownAdapter() {
        ArrayAdapter<CharSequence> dropDownAdapter = ArrayAdapter
                .createFromResource(this,
                        R.array.image_type_array,
                        R.layout.support_simple_spinner_dropdown_item);
        dropDownAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        imageTypeDropDown.setAdapter(dropDownAdapter);
    }

    private void updateContent() {
        adapter.clear();

        RealmHelper helper = RealmHelper.getInstance();
        Realm realm = helper.getRealm();

        RealmResults<ImageGalleryData> images = realm.where(ImageGalleryData.class).findAll();

        for (ImageGalleryData image : images) {
            adapter.add(image);
        }

        adapter.notifyDataSetChanged();
    }
}
