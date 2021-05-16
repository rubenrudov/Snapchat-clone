package com.snapchatclone.helpers;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.snapchatclone.fragments.CameraFragment;
import com.snapchatclone.fragments.ChatsFragment;
import com.snapchatclone.fragments.StoryFragment;

public class FragmentAdapter extends FragmentPagerAdapter {


    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        // Selection between 3 screens: chats, camera & story
        switch (position) {
            case 0:
                return new ChatsFragment();
            case 1:
                return new CameraFragment();
            case 2:
                return new StoryFragment();
        }

        return new Fragment(); // Create 404 page ?
    }

    @Override
    public int getCount() {
        return 3;
    }
}
