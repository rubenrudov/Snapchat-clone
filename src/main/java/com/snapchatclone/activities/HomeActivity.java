package com.snapchatclone.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.snapchatclone.R;
import com.snapchatclone.helpers.FragmentAdapter;
import com.snapchatclone.helpers.UserInfo;

import java.util.Objects;

public class HomeActivity extends AppCompatActivity {

    // Properties
    FragmentAdapter customFragments;

    // UI components
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Objects.requireNonNull(getSupportActionBar()).hide();

        UserInfo userInfo = new UserInfo();
        userInfo.startFetching();

        // Components assignment
        viewPager = findViewById(R.id.swipeableViewPager);


        // Adapter setting for viewPager
        customFragments = new FragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(customFragments);
        viewPager.setCurrentItem(1);
    }
}
