package com.example.menuplanner.activities.recipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.menuplanner.R;
import com.example.menuplanner.activities.MainActivity;
import com.example.menuplanner.components.adapters.RecipeFragmentAdapter;
import com.example.menuplanner.entities.User;
import com.example.menuplanner.utils.Db;
import com.google.android.material.tabs.TabLayout;

public class RecipeActivity extends AppCompatActivity {

    private User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(MainActivity.DEFAULT_USER_LOGIN, 0);
        String activeUser = sharedPreferences.getString(MainActivity.DEFAULT_USERNAME, MainActivity.SP_ANON_USER);


        Db dbManager = new Db(getApplicationContext());
        User currentUser = dbManager.getUser(activeUser);
        this.currentUser = currentUser;
        currentUser.setRole(dbManager.getUserRole(activeUser));



        TabLayout tabLayout = findViewById(R.id.recipe_tab_layout);
        ViewPager2 viewPager = findViewById(R.id.recipe_view_page);

        RecipeFragmentAdapter recipeFragmentAdapter = new RecipeFragmentAdapter(getSupportFragmentManager(), getLifecycle(), this.currentUser, dbManager);
        viewPager.setAdapter(recipeFragmentAdapter);

        tabLayout.addTab(tabLayout.newTab().setText("Recipes"));
        tabLayout.addTab(tabLayout.newTab().setText("Add Recipe"));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

        Button closeActivityButton = findViewById(R.id.recipe_back_btn);
        closeActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecipeActivity.this.finish();
            }
        });
    }



}