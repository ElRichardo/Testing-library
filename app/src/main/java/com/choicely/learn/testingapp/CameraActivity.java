package com.choicely.learn.testingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {

    private ImageView imageView;

    private static final String FILE_PROVIDER_AUTHORITY = "com.example.android.fileprovider";
    private static final String TAG = "CameraActivity";

    private File photoFile = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

        imageView = findViewById(R.id.camera_activity_photo);

        pictureIntent();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data != null) {
            Bundle extras = data.getExtras();
            Bitmap bm = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bm);
            Log.d(TAG, "image set");
        }
    }

    /**
     * Need to create a file to save the image into
     */
    private File createNewImageFile() {
        try {
            String time = new SimpleDateFormat("yyyyddMM_HHmmss", Locale.US).format(new Date());
            String fileName = "JPEG_" + time + "_";
            File fileDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = File.createTempFile(fileName, ".jpg", fileDir);
            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //returns null if the try doesn't work
        return null;
    }

    private void pictureIntent() {
        photoFile = createNewImageFile();

        if (photoFile != null) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            Uri photoURI = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            //might use startActivityForResult() later
            startActivity(takePictureIntent);
        }
    }
}
