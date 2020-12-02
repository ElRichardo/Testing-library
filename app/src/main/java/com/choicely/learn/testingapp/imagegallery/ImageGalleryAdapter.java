package com.choicely.learn.testingapp.imagegallery;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.choicely.learn.testingapp.viewpager.FragmentTest;

import java.util.ArrayList;
import java.util.List;

import static androidx.viewpager.widget.PagerAdapter.POSITION_NONE;

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
    public int getItemCount() { return list.size(); }

    /**
     * the whole filtering by type doesn't work without this
     * the fragments need ids if they are updated on top of each others positions
     **/
    @Override
    public long getItemId(int position) { return list.get(position).getGalleryPicID(); }

    public void add(ImageGalleryData image) { list.add(image); }

    public void clear() { list.clear(); }
}
