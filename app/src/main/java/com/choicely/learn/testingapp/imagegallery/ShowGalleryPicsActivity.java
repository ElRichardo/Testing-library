package com.choicely.learn.testingapp.imagegallery;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private static final String JPEG_ITEM = ".jpeg";
    private static final String JPG_ITEM = ".jpg";
    private static final String PNG_ITEM = ".png";
    private static final String WEBP_ITEM = ".webP";
    private static final String NO_FILTER_ITEM = "no filter";

    private ViewPager2 viewPager;
    ImageGalleryAdapter adapter;
    private Spinner imageTypeDropDown;
    private String suffix;
    private Button removeButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_gallery_pics_activity);

        imageTypeDropDown = findViewById(R.id.show_gallery_pics_type_dropdown);
        removeButton = findViewById(R.id.show_gallery_pics_remove);

        viewPager = findViewById(R.id.show_gallery_pics_pager);
        adapter = new ImageGalleryAdapter(this);
        viewPager.setAdapter(adapter);

        setDropDownAdapter();

        imageTypeDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getSelectedItem().toString();
                Log.d(TAG, "selected: " + selectedItem);

                switch (selectedItem) {
                    default:
                    case NO_FILTER_ITEM:
                        showEverythingInAdapter();
                        break;
                    case JPEG_ITEM:
                        suffix = JPEG_ITEM;
                        addToAdapterByType();
                        break;
                    case JPG_ITEM:
                        suffix = JPG_ITEM;
                        addToAdapterByType();
                        break;
                    case PNG_ITEM:
                        suffix = PNG_ITEM;
                        addToAdapterByType();
                        break;
                    case WEBP_ITEM:
                        suffix = WEBP_ITEM;
                        addToAdapterByType();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "nothing selected");
            }
        });

        removeButton.setOnClickListener(v -> {
            //removeImage();
        });

    }

//    private void removeImage() {
//        RealmHelper helper = RealmHelper.getInstance();
//        Realm realm = helper.getRealm();
    //
//        realm.executeTransaction(realm1 -> {
//            realm.where(ImageGalleryData.class).equalTo("pictureUrl", url).findAll().deleteAllFromRealm();
//        });
//    }

    private void addToAdapterByType() {
        adapter.clear();

        RealmHelper helper = RealmHelper.getInstance();
        Realm realm = helper.getRealm();

        if (suffix != null) {
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

    private void showEverythingInAdapter() {
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
