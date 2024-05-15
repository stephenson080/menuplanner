package com.example.menuplanner.activities.meal_plan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.menuplanner.R;
import com.example.menuplanner.activities.MainActivity;
import com.example.menuplanner.activities.recipe.RecipeActivity;
import com.example.menuplanner.components.adapters.AllMealsAdapter;
import com.example.menuplanner.components.adapters.AllUsersAdapter;
import com.example.menuplanner.entities.MealPlan;
import com.example.menuplanner.entities.User;
import com.example.menuplanner.utils.Db;

import java.util.ArrayList;

public class MealPlanIndexActivity extends AppCompatActivity implements AddMealPlanActivity.MealPlanAddedListener {
    private AllMealsAdapter mealPlanAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan_index);

        Db db = new Db(getApplicationContext());

        ArrayList<MealPlan> mealPlans = db.getAllMealPlans();

        RecyclerView mealPlansListView = findViewById(R.id.meal_list_view);

        mealPlanAdapter = new AllMealsAdapter(MealPlanIndexActivity.this, mealPlans);
        mealPlansListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mealPlansListView.setAdapter(mealPlanAdapter);

        Button backBtn = findViewById(R.id.all_meals_back_btn);

         Button showaddMealPlanBtn = findViewById(R.id.show_add_meal_btn);

         showaddMealPlanBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 try {
                     Intent addMealPlan = new Intent(MealPlanIndexActivity.this, AddMealPlanActivity.class);
                     AddMealPlanActivity.setMealPlanAddedListener(MealPlanIndexActivity.this);
                     MealPlanIndexActivity.this.startActivity(addMealPlan);
                 }catch (Exception e){
                     Log.i("Meal Plans", e.getMessage());
                 }

             }
         });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MealPlanIndexActivity.this.finish();
            }
        });
    }

    @Override
    public void onMealPlanAdded() {
        // Callback triggered when a new meal plan is added
        // Update the adapter's dataset and notify data change
        Db db = new Db(getApplicationContext());
        ArrayList<MealPlan> updatedMealPlans = db.getAllMealPlans();
        this.mealPlanAdapter.updateData(updatedMealPlans);
        this.mealPlanAdapter.notifyDataSetChanged();
    }
}