package com.choicely.learn.testingapp.receiptsave;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.choicely.learn.testingapp.R;
import com.choicely.learn.testingapp.db.RealmHelper;
import com.choicely.learn.testingapp.receiptsave.ReceiptSavingActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;

public class CameraActivity extends AppCompatActivity {

    private static final int IMAGE_REQUEST_CODE = 1;
    private static final String FILE_PROVIDER_AUTHORITY = "com.example.android.fileprovider";
    private static final String TAG = "CameraActivity";

    private ImageView imageView;
    private EditText title;
    private EditText date;

    private Uri photoURI;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_activity);

        imageView = findViewById(R.id.camera_activity_photo);
        title = findViewById(R.id.camera_activity_title);
        date = findViewById(R.id.camera_activity_date);

        pictureIntent();
        saveData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "resultcode: " + resultCode);

        if (requestCode == IMAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(photoURI);
            } else { //RESULT_CANCELLED
                Intent intent = new Intent(this, ReceiptSavingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    }

    /**
     * Need to create a file to save the image into
     */
    private File createNewImageFile() {
        try {
            String timeStamp = new SimpleDateFormat("ddMMyyyy").format(new Date());
            String fileName = "JPEG_" + timeStamp + "_";
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
        File photoFile = createNewImageFile();

        if (photoFile != null) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            photoURI = FileProvider.getUriForFile(this, FILE_PROVIDER_AUTHORITY, photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, IMAGE_REQUEST_CODE);
        }
    }

    private void saveData() {
        ReceiptData receipt = new ReceiptData();

        String fileDate = date.getText().toString();
        String fileTitle = title.getText().toString();

        if (fileTitle != null || fileDate != null) {
            receipt.setTitle(fileTitle);
            receipt.setDate(fileDate);

            RealmHelper helper = RealmHelper.getInstance();
            Realm realm = helper.getRealm();

            realm.executeTransaction(realm1 -> {
                realm.copyToRealmOrUpdate(receipt);
                Log.d(TAG, "Data added");
            });
        }
    }
}
