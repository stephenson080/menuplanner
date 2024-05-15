package com.example.menuplanner.activities.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menuplanner.R;
import com.example.menuplanner.activities.MainActivity;
import com.example.menuplanner.utils.Db;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Login Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initFormControls();
    }

    public void loginUser(String username, String password) {
        if ( username == null || password == null )
            Toast.makeText(this, "Username and password are required!", Toast.LENGTH_SHORT).show();

        username = username.trim();
        password = password.trim();


        try (Db db = new Db(getApplicationContext())) {
            Boolean isAuth = db.login(username, password);
            if ( isAuth ) {
                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getApplication().getSharedPreferences(MainActivity.DEFAULT_USER_LOGIN, 0);
                SharedPreferences.Editor spEditor = sharedPreferences.edit();
                spEditor.putString(MainActivity.DEFAULT_USERNAME, username);
                spEditor.apply();
                LoginActivity.this.finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "Something went wrong", e);
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private void initFormControls(){
        Button loginButton = findViewById(R.id.register_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEditText = findViewById(R.id.sign_up_username);
                EditText passwordEditText = findViewById(R.id.sign_up_password);

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                loginUser(username, password);

            }
        });

        Button signupBtn = findViewById(R.id.sign_up_back_btn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signupActivity = new Intent(LoginActivity.this, SignupActivity.class);
                LoginActivity.this.startActivity(signupActivity);

            }
        });
    }
}