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

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initFormControls();
    }

    private void initFormControls(){
        Button loginButton = findViewById(R.id.register_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText usernameEditText = findViewById(R.id.sign_up_username);
                EditText passwordEditText = findViewById(R.id.sign_up_password);
                EditText confirmPasswordField = findViewById(R.id.sign_up_confirm_password);

                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordField.getText().toString();
                signup(username, password, confirmPassword);

            }
        });

        Button signupBtn = findViewById(R.id.sign_up_back_btn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupActivity.this.finish();

            }
        });
    }

    public void signup(String username, String password, String confirmPassword) {
        if ( username == null || password == null || confirmPassword == null )
            Toast.makeText(this, "Username, password and confirm password are required!", Toast.LENGTH_SHORT).show();

        else if ( !password.equals(confirmPassword) )
            Toast.makeText(this, "Password is not same as confirm password", Toast.LENGTH_SHORT).show();

       else {
           try (Db db = new Db(getApplicationContext())) {
            boolean success = db.signup(username, password);
            if (success){
                Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPreferences = getApplication().getSharedPreferences(MainActivity.DEFAULT_USER_LOGIN, 0);
                SharedPreferences.Editor spEditor = sharedPreferences.edit();
                spEditor.putString(MainActivity.DEFAULT_USERNAME, username);
                spEditor.apply();
                Intent mainActivity = new Intent(SignupActivity.this, MainActivity.class);
                SignupActivity.this.startActivity(mainActivity);
            }
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
       }
    }
}