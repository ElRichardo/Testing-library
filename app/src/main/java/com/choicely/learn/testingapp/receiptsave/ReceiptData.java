package com.choicely.learn.testingapp.receiptsave;

import java.io.File;

import io.realm.RealmObject;

public class ReceiptData extends RealmObject {

    private String title;
    private String date;

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
}
