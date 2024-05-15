package com.example.menuplanner.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menuplanner.R;
import com.example.menuplanner.activities.MainActivity;
import com.example.menuplanner.components.adapters.ProfileTabStateAdapter;
import com.example.menuplanner.entities.User;
import com.example.menuplanner.utils.Db;
import com.example.menuplanner.views.UserViewModel;
import com.google.android.material.tabs.TabLayout;

public class ProfileActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getCurrentUser();
        this.setTabs();
        initButtons();


    }

    private void getCurrentUser(){

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(MainActivity.DEFAULT_USER_LOGIN, 0);
        String activeUser = sharedPreferences.getString(MainActivity.DEFAULT_USERNAME, MainActivity.SP_ANON_USER);

        TextView userProfileUsername = findViewById(R.id.profile_username);
        userProfileUsername.setText(activeUser);

        Db db = new Db(getApplicationContext());
        User user = db.getUser(activeUser);
        if (user != null){
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            userViewModel.setUser(user);
        }

    }

    private void setTabs(){
        TabLayout tabs = findViewById(R.id.profile_tabs);
        ViewPager2 currentFragment = findViewById(R.id.fragment_page);

        ProfileTabStateAdapter adapter = new ProfileTabStateAdapter(getSupportFragmentManager(), getLifecycle());
        currentFragment.setAdapter(adapter);

        tabs.addTab(tabs.newTab().setText("Contacts"));
        tabs.addTab(tabs.newTab().setText("Name"));
        tabs.addTab(tabs.newTab().setText("Address"));


        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                currentFragment.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        currentFragment.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabs.selectTab(tabs.getTabAt(position));
            }
        });

    }

    private void initButtons(){
        Button saveBtn = findViewById(R.id.profile_save_btn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User updatedUser = userViewModel.getUser();
                Db dbManager = new Db(getApplicationContext());
                if (dbManager.updateUser(updatedUser)) {
                    Toast.makeText(getApplicationContext(), "User Profile Updated!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "User Profile Updated!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button backBtn = findViewById(R.id.profile_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProfileActivity.this.finish();
            }
        });
    }
}