package com.example.mydaysapp2.ui.mainpage;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.mydaysapp2.ui.mainpage.tabFirst.GoalsFragment;
import com.example.mydaysapp2.ui.mainpage.tabSecond.WorksFragment;

import org.jetbrains.annotations.NotNull;

public class TabAdapter extends FragmentPagerAdapter {
    private final int ITEM_COUNT = 2;

    @NonNull
    private Context context;


    public TabAdapter(@NonNull FragmentManager fm, @NotNull Context context) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        if (position == 0) {
            fragment = new GoalsFragment();
        }
        else{
                fragment = new WorksFragment();
            }
            return fragment;

    }

    @Override
    public int getCount() {
        return ITEM_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position) {
            case 0:
                title = "Our goals" ;
                break;
            case 1:
                title = "Works";
                break;
            default:
                title = null;
                break;
        }
        return title;
    }
}
