package com.choicely.learn.testingapp.receiptsave;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static File getJPEGFile(Context context, String fileName) {
        try {
            File fileDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File imageFile = File.createTempFile(fileName, ".jpg", fileDir);

            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        //returns null if the try doesn't work
        return null;
    }
}
