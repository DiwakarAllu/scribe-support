package com.example.scribesupport.scribe;
//
//import android.content.res.ColorStateList;
//import android.graphics.Color;
//import android.os.Bundle;
//
//import androidx.activity.EdgeToEdge;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.Toolbar;
//import androidx.core.graphics.Insets;
//import androidx.core.view.ViewCompat;
//import androidx.core.view.WindowInsetsCompat;
//
//import com.example.scribesupport.R;
//
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentManager;
//import androidx.lifecycle.Lifecycle;
//import androidx.viewpager.widget.ViewPager;
//import androidx.viewpager2.adapter.FragmentStateAdapter;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.TextView;
//
//import com.google.android.material.tabs.TabLayoutMediator;
//
//import java.util.ArrayList;
//import java.util.List;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.fragment.app.FragmentPagerAdapter;
//import androidx.viewpager2.widget.ViewPager2;
//import com.google.android.material.tabs.TabLayout;
//import java.util.ArrayList;
//import java.util.List;
//import android.os.Bundle;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.viewpager2.adapter.FragmentStateAdapter;
//import androidx.viewpager2.widget.ViewPager2;
//import com.google.android.material.tabs.TabLayout;
//import java.util.ArrayList;
//import java.util.List;
////
////public class VolunteerProfileViewActivity extends AppCompatActivity implements View.OnClickListener{
////    ColorStateList def;
////    TextView item1;
////    TextView item2;
////    TextView select;
////    @Override
////    protected void onCreate(Bundle savedInstanceState) {
////        super.onCreate(savedInstanceState);
////        setContentView(R.layout.activity_volunteer_profile_view);
////        Toolbar toolbar = findViewById(R.id.toolbar);
////        setSupportActionBar(toolbar);
////        item1 = findViewById(R.id.item1);
////        item2 = findViewById(R.id.item2);
////
////        item1.setOnClickListener(this);
////        item2.setOnClickListener(this);
////
////        select = findViewById(R.id.select);
////        def = item2.getTextColors();
////    }
////    @Override
////    public void onClick(View view) {
////        if (view.getId() == R.id.item1){
////            select.animate().x(0).setDuration(100);
////            item1.setTextColor(Color.WHITE);
////            item2.setTextColor(def);
////
////        } else if (view.getId() == R.id.item2) {
////            item1.setTextColor(def);
////            item2.setTextColor(Color.WHITE);
////
////            int size = item2.getWidth();
////            select.animate().x(size).setDuration(100);
////        }
////    }
////}
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.viewpager.widget.ViewPager;
//import android.os.Bundle;
//import com.google.android.material.tabs.TabLayout;
//import java.util.ArrayList;
//import java.util.List;
//
//public class VolunteerProfileViewActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_volunteer_profile_view);
//
//        ViewPager viewPager = findViewById(R.id.view_pager);
//        TabLayout tabLayout = findViewById(R.id.tab_layout);
//
////        // Add icons to tabs
////        tabLayout.getTabAt(1).setIcon(R.drawable.user_icon);
//
//        // Set custom tab indicator
//        tabLayout.setSelectedTabIndicatorColor(Color.RED); // Set tab indicator color
//        tabLayout.setSelectedTabIndicatorHeight(7); // Set tab indicator height
//
//
//        // Initialize ViewPager adapter
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
//        adapter.addFragment(new ContactFragment(), "Contact");
//        adapter.addFragment(new AddressFragment(), "Address");
//
//        // Set adapter to ViewPager
//        viewPager.setAdapter(adapter);
//
//        // Connect TabLayout with ViewPager
//        tabLayout.setupWithViewPager(viewPager);
//    }
//
//    // Adapter for ViewPager
//    static class ViewPagerAdapter extends FragmentPagerAdapter {
//        private final List<Fragment> fragmentList = new ArrayList<>();
//        private final List<String> fragmentTitleList = new ArrayList<>();
//
//        ViewPagerAdapter(FragmentManager fragmentManager) {
//            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        }
//
//        void addFragment(Fragment fragment, String title) {
//            fragmentList.add(fragment);
//            fragmentTitleList.add(title);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            return fragmentList.get(position);
//        }
//
//        @Override
//        public int getCount() {
//            return fragmentList.size();
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return fragmentTitleList.get(position);
//        }
//    }
//}

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.scribesupport.R;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VolunteerProfileViewActivity extends AppCompatActivity {

    Toolbar toolbar;
    HashMap<String, Object> userData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_profile_view);

        toolbar=findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

        // Retrieve the HashMap from Intent extras
        Intent intent = getIntent();
        if (intent != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                userData = (HashMap<String, Object>) extras.getSerializable("userData");
                if (userData != null) {
                    String name = (String) userData.get("userName");
                    String pin = (String) userData.get("pin");
                    String gender = (String) userData.get("gender");
                    String phoneNumber = (String) userData.get("phone number");
                    String email = (String) userData.get("email");
                    String address = (String) userData.get("address");
                    String dob = (String) userData.get("dob");
                }
            }
        }

        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        // Initialize ViewPager adapter
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ContactFragment(), "Contact", R.drawable.volunteer,userData);
        adapter.addFragment(new AddressFragment(), "Address", R.drawable.user_icon,userData);

        // Set adapter to ViewPager
        viewPager.setAdapter(adapter);

        // Connect TabLayout with ViewPager
        tabLayout.setupWithViewPager(viewPager);

//        // Set icons for each tab
//        for (int i = 0; i < adapter.getCount(); i++) {
//            //tabLayout.getTabAt(i).setIcon(adapter.getIcon(i));
//           // tabLayout.getTabAt(0).setIcon(adapter.getIcon(0));
//        }
    }

    @Override
    public void onBackPressed() {
        // Check if the current activity is VolunteerProfileViewActivity
        if (this instanceof VolunteerProfileViewActivity) {
            // Navigate back to the previous activity
            super.onBackPressed();
        } else {
            // If the current activity is not VolunteerProfileViewActivity,
            // perform default back button behavior
            super.onBackPressed();
        }
    }


    // Adapter for ViewPager
    static class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();
        private final List<Integer> iconList = new ArrayList<>();

        private final List userDataList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }

        void addFragment(Fragment fragment, String title, int iconRes, HashMap<String, Object> userData) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
            iconList.add(iconRes);
            userDataList.add(userData);
        }


        @Override
        public Fragment getItem(int position) {
            Fragment fragment = fragmentList.get(position);
            Bundle args = new Bundle();
            args.putSerializable("userData", (Serializable) userDataList.get(position)); // Pass user data to the fragment
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }

        int getIcon(int position) {
            return iconList.get(position);
        }
    }
}
