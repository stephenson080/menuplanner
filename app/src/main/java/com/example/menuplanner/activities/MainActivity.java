package com.example.menuplanner.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import com.example.menuplanner.R;
import com.example.menuplanner.activities.auth.LoginActivity;
import com.example.menuplanner.activities.meal_plan.MealPlanIndexActivity;
import com.example.menuplanner.activities.meal_plan.ShoppingListActivity;
import com.example.menuplanner.activities.recipe.RecipeActivity;
import com.example.menuplanner.activities.user.ManageUsersActivity;
import com.example.menuplanner.activities.user.ProfileActivity;
import com.example.menuplanner.entities.MealPlan;
import com.example.menuplanner.utils.Db;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "HOME";
    public static final String DEFAULT_USER_LOGIN = "default_user_login";
    public static final String DEFAULT_USERNAME = "DEFAULT_USER";
    public static final String SP_ANON_USER = "another_user";

    private final String[] breakfasts = {"Waffles", "Pancakes", "Yogurt"};
    private final String[] lunches = {"Pasta", "PBJ Sandwich", "Cheese Sandwich"};
    private final String[] dinners = {"Stir Fry", "Lasagna", "Fish"};

    private ArrayList<MealPlan> mealPlans;

    private int counter = 0;

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences(DEFAULT_USER_LOGIN, 0);
        String activeUser = sharedPreferences.getString(DEFAULT_USERNAME, SP_ANON_USER);
        if ( activeUser.equalsIgnoreCase(SP_ANON_USER) ) {
            Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
            try{
                MainActivity.this.startActivity(loginActivity);
            }catch (Exception e){
                Log.e(TAG, e.toString());
            }
//
        } else {
            TextView activeUserText = findViewById(R.id.active_user);
            activeUserText.setText(activeUser);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initFormButtons();
        this.manageDailyMenu();
    }

    protected void initFormButtons(){
        Button logoutButton = findViewById(R.id.logout_btn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getApplication().getSharedPreferences(DEFAULT_USER_LOGIN, 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(MainActivity.DEFAULT_USERNAME, MainActivity.SP_ANON_USER);
                editor.apply();
                Intent loginActivity = new Intent(MainActivity.this, LoginActivity.class);
                MainActivity.this.startActivity(loginActivity);
            }
        });
        Spinner spinner = findViewById(R.id.pages);
        Button openPageBtn = findViewById(R.id.open_page_btn);
        openPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectedPage = spinner.getSelectedItem().toString();
                if ( selectedPage.equalsIgnoreCase("User Profile") ) {
                    Intent profileIntent = new Intent(MainActivity.this, ProfileActivity.class);
                    MainActivity.this.startActivity(profileIntent);
                } else if ( selectedPage.equalsIgnoreCase("Manage Users") ) {
                    Intent manageUsers = new Intent(MainActivity.this, ManageUsersActivity.class);
                    MainActivity.this.startActivity(manageUsers);
                }else if (selectedPage.equalsIgnoreCase("Recipe Editor")){
                    Intent recipeManageIntent = new Intent(MainActivity.this, RecipeActivity.class);
                    MainActivity.this.startActivity(recipeManageIntent);
                }
                else if (selectedPage.equalsIgnoreCase("Weekly Meal Plan Editor")){
                    Intent mealPlanIndex = new Intent(MainActivity.this, MealPlanIndexActivity.class);
                    MainActivity.this.startActivity(mealPlanIndex);
                }
                else if (selectedPage.equalsIgnoreCase("Shopping List Viewer")){
                    Intent shoppingListActivity = new Intent(MainActivity.this, ShoppingListActivity.class);
                    MainActivity.this.startActivity(shoppingListActivity);
                }
            }
        });
    }

    protected void manageDailyMenu(){
        Db db = new Db(getApplicationContext());

        ArrayList<LocalDate> days = this.getDatesOfTheWeek();
        ArrayList<MealPlan> mealPlans = db.getAllMealPlansForPeriod(days);
        this.mealPlans = mealPlans;
        DateTimeFormatter dtf = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
        LocalDate currentDate = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentDate = LocalDate.now();
        }
        String dateToDisplay = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateToDisplay = dtf.format(currentDate);
        }

        TextView menuDay = findViewById(R.id.menu_date);
        menuDay.setText(dateToDisplay);

        TextView lunchItem = findViewById(R.id.lunch_item);
        TextView dinnerItem = findViewById(R.id.dinner_item);

        TextView breakfastItem = findViewById(R.id.breakfast_item);
        for (MealPlan mealPlan : mealPlans) {
            if (mealPlan.getDate().equals(currentDate)) {
                if (mealPlan.getMealType().equalsIgnoreCase("Breakfast")){
                    breakfastItem.setText(mealPlan.getRecipe().getTitle());
                }else if (mealPlan.getMealType().equalsIgnoreCase("Lunch")){
                    lunchItem.setText(mealPlan.getRecipe().getTitle());
                }else {
                    dinnerItem.setText(mealPlan.getRecipe().getTitle());
                }

            }
            else {
                breakfastItem.setText("Not set");
                lunchItem.setText("Not set");
                dinnerItem.setText("Not set");
            }
        }
//        MealPlan breakFastMeal = mealPlans.
//        breakfastItem.setText(breakfasts[counter]);
//        TextView lunchItem = findViewById(R.id.lunch_item);
//        lunchItem.setText(lunches[counter]);
//        TextView dinnerItem = findViewById(R.id.dinner_item);
//        dinnerItem.setText(dinners[counter]);

        Button previousDay = findViewById(R.id.menu_previous_day);
        DateTimeFormatter finalDtf = dtf;
        previousDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalDate currentDate = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    currentDate = LocalDate.parse(menuDay.getText().toString(), finalDtf);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    currentDate = currentDate.minusDays(1);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    menuDay.setText(finalDtf.format(currentDate));
                }

                counter--;
                if ( counter < 0 )
                    counter = breakfasts.length-1;

                TextView breakfastItem = findViewById(R.id.breakfast_item);

                TextView lunchItem = findViewById(R.id.lunch_item);

                TextView dinnerItem = findViewById(R.id.dinner_item);

                for (MealPlan mealPlan : mealPlans) {
                    if (mealPlan.getDate().equals(currentDate)) {
                        if (mealPlan.getMealType().equalsIgnoreCase("Breakfast")){
                            breakfastItem.setText(mealPlan.getRecipe().getTitle());
                        }else if (mealPlan.getMealType().equalsIgnoreCase("Lunch")){
                            lunchItem.setText(mealPlan.getRecipe().getTitle());
                        }else {
                            dinnerItem.setText(mealPlan.getRecipe().getTitle());
                        }

                    }
                    else {
                        breakfastItem.setText("Not set");
                        lunchItem.setText("Not set");
                        dinnerItem.setText("Not set");
                    }
                }
            }
        });

        Button nextDay = findViewById(R.id.menu_next_day);
        DateTimeFormatter finalDtf1 = dtf;
        nextDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick: Going to the next day");
                LocalDate currentDate = null;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    currentDate = LocalDate.parse(menuDay.getText().toString(), finalDtf1);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    currentDate = currentDate.plusDays(1);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    menuDay.setText(finalDtf1.format(currentDate));
                }

                counter++;
                if ( counter >= breakfasts.length )
                    counter = 0;

                TextView breakfastItem = findViewById(R.id.breakfast_item);

                TextView lunchItem = findViewById(R.id.lunch_item);

                TextView dinnerItem = findViewById(R.id.dinner_item);

                for (MealPlan mealPlan : mealPlans) {
                    if (mealPlan.getDate().equals(currentDate)) {
                        if (mealPlan.getMealType().equalsIgnoreCase("Breakfast")){
                            breakfastItem.setText(mealPlan.getRecipe().getTitle());
                            break;
                        }else if (mealPlan.getMealType().equalsIgnoreCase("Lunch")){
                            lunchItem.setText(mealPlan.getRecipe().getTitle());
                            break;
                        }else {
                            dinnerItem.setText(mealPlan.getRecipe().getTitle());
                            break;
                        }

                    }
                    else {
                        breakfastItem.setText("Not set");
                        lunchItem.setText("Not set");
                        dinnerItem.setText("Not set");
                    }
                }
            }
        });
    }

    public ArrayList<LocalDate> getDatesOfTheWeek() {
        LocalDate today = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            today = LocalDate.now();
        }
        LocalDate startOfWeek = null; // Get the start of the week (Monday)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startOfWeek = today.with(DayOfWeek.MONDAY);
        }
        LocalDate endOfWeek = null; // Get the end of the week (Sunday)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            endOfWeek = startOfWeek.plusDays(6);
        }

        ArrayList<LocalDate> datesOfWeek = new ArrayList<>();
        LocalDate dateIterator = startOfWeek;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            while (!dateIterator.isAfter(endOfWeek)) {
                datesOfWeek.add(dateIterator);
                dateIterator = dateIterator.plusDays(1);
            }
        }
        return datesOfWeek;
    }

}