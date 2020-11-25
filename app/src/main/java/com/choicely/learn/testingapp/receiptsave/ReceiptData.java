package com.choicely.learn.testingapp.receiptsave;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ReceiptData extends RealmObject {

    @PrimaryKey
    private int picID;
    private String title;
    private String date;
    private String photoUri;

    public int getPicID() { return picID; }

    public void setPicID(int picID) { this.picID = picID; }

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


    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}
