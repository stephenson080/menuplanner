package com.example.menuplanner.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.menuplanner.R;
import com.example.menuplanner.activities.MainActivity;
import com.example.menuplanner.components.adapters.AllUsersAdapter;
import com.example.menuplanner.entities.User;
import com.example.menuplanner.utils.Db;

import java.util.ArrayList;

public class ManageUsersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);
        initUserCards();
    }

    private void initUserCards(){
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(MainActivity.DEFAULT_USER_LOGIN, 0);
        String storeUsername = sharedPreferences.getString(MainActivity.DEFAULT_USERNAME, MainActivity.SP_ANON_USER);

        Db db = new Db(getApplicationContext());
        User currentUser = db.getUser(storeUsername);
        currentUser.setRole(db.getUserRole(storeUsername));
        ArrayList<User> allUsers = db.getUsers();


        RecyclerView usersRecyclerView = findViewById(R.id.users_list);

        AllUsersAdapter usersAdapter = new AllUsersAdapter(getApplicationContext(), currentUser, allUsers);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        usersRecyclerView.setAdapter(usersAdapter);

        Button backBtn = findViewById(R.id.manage_users_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ManageUsersActivity.this.finish();
            }
        });
    }
}