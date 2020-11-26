package com.choicely.learn.testingapp.imagegallery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.choicely.learn.testingapp.receiptsave.ReceiptData;

import java.util.ArrayList;
import java.util.List;

public class ImageGalleryAdapter extends FragmentStateAdapter {

    private final List list = new ArrayList<>();

    public ImageGalleryAdapter(@NonNull FragmentActivity fragmentActivity) { super(fragmentActivity); }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
//        list.get(position)
        FragmentForPics fragment = new FragmentForPics();

//        Bundle data = new Bundle();
//        data.putInt(FragmentForPics.POSITION_NUMBER, position);
//        fragment.setArguments(data);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
