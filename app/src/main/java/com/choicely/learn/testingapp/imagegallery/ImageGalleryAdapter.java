package com.choicely.learn.testingapp.imagegallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.choicely.learn.testingapp.viewpager.FragmentTest;

import java.util.ArrayList;
import java.util.List;

public class ImageGalleryAdapter extends FragmentStateAdapter {

    private final List<ImageGalleryData> list = new ArrayList<>();

    public ImageGalleryAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        ImageGalleryData image = list.get(position);
        FragmentForPics fragment = new FragmentForPics();

        Bundle data = new Bundle();
        data.putString("pictureUrl", image.getPictureUrl());
        data.putString("UrlName", image.getPictureName());
        fragment.setArguments(data);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void add(ImageGalleryData image) { list.add(image); }

    public void clear() { list.clear(); }

    //public void remove(ImageGalleryData image){list.remove(image);}
}
