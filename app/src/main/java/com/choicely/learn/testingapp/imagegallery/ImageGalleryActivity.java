package com.choicely.learn.testingapp.imagegallery;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.choicely.learn.testingapp.R;
import com.choicely.learn.testingapp.db.RealmHelper;

import io.realm.Realm;
import io.realm.Sort;

public class ImageGalleryActivity extends AppCompatActivity {

    private static final String GALLERY_PIC_ID = "galleryPicID";
    private static final String TAG = "ImageGalleryActivity";

    private EditText addUrl;
    private EditText nameUrl;
    private Button addImage;
    private ImageButton galleryBtn;
    private int galleryPicID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery_activity);

        addUrl = findViewById(R.id.image_gallery_activity_add_url);
        nameUrl = findViewById(R.id.image_gallery_activity_name_url);
        addImage = findViewById(R.id.image_gallery_activity_adding_button);
        galleryBtn = findViewById(R.id.image_gallery_activity_gallery_button);

        galleryBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ShowGalleryPicsActivity.class);
            intent.putExtra(GALLERY_PIC_ID, galleryPicID);
            startActivity(intent);
        });

        addImage.setOnClickListener(v -> {
            updateID();
            if (addUrl.getText().length() > 0) {
                saveImage();
            } else {
                Toast.makeText(this, "Give an url please!", Toast.LENGTH_SHORT).show();
            }
            addUrl.setText(null);
            nameUrl.setText(null);
        });
    }

    private void updateID() {
        Realm realm = RealmHelper.getInstance().getRealm();
        ImageGalleryData latestID = realm.where(ImageGalleryData.class).sort(GALLERY_PIC_ID, Sort.DESCENDING).findFirst();

        if (latestID != null) {
            galleryPicID = latestID.getGalleryPicID() + 1;
        } else {
            galleryPicID = 0;
        }
    }

    private void saveImage() {
        ImageGalleryData image = new ImageGalleryData();

        String pictureName = nameUrl.getText().toString();
        String pictureUrl = addUrl.getText().toString();

        image.setGalleryPicID(galleryPicID);
        image.setPictureName(pictureName);
        image.setPictureUrl(pictureUrl);

        Log.d(TAG, "galleryPicID: " + galleryPicID);
        Log.d(TAG, "pictureName: " + pictureName);
        Log.d(TAG, "pictureUrl: " + pictureUrl);

        RealmHelper helper = RealmHelper.getInstance();
        Realm realm = helper.getRealm();

        realm.executeTransaction(realm1 -> {
            realm.copyToRealmOrUpdate(image);
        });

        Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show();
    }
}
