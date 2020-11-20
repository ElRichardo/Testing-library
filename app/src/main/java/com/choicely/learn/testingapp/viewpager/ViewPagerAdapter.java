package com.choicely.learn.testingapp.viewpager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        FragmentTest fragment = new FragmentTest();

        Bundle data = new Bundle();
        data.putInt(FragmentTest.POSITION_NUMBER, position);
        fragment.setArguments(data);

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
