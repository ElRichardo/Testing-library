package com.choicely.learn.testingapp.receiptsave;

import java.io.File;

import io.realm.RealmObject;

public class ReceiptData extends RealmObject {

    private String title;
    private String date;
    private File directory;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public File getDirectory() {
        return directory;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }
}
