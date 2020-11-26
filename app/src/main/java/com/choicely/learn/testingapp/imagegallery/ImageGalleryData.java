package com.choicely.learn.testingapp.imagegallery;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ImageGalleryData extends RealmObject {

    @PrimaryKey
    private int galleryPicID;
    private String pictureName;


    public int getGalleryPicID() {
        return galleryPicID;
    }

    public void setGalleryPicID(int galleryPicID) {
        this.galleryPicID = galleryPicID;
    }

    public String getPictureName() {
        return pictureName;
    }

    public void setPictureName(String pictureName) {
        this.pictureName = pictureName;
    }
}
