package com.example.menuplanner.activities.meal_plan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.menuplanner.R;
import com.example.menuplanner.components.adapters.AllMealsAdapter;
import com.example.menuplanner.components.adapters.ShoppingListAdapter;
import com.example.menuplanner.entities.MealPlan;
import com.example.menuplanner.utils.Db;

import java.util.ArrayList;

public class ShoppingListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        Db db = new Db(getApplicationContext());

        ArrayList<String> shoppingList = db.getShopping();

        RecyclerView shoppingListView = findViewById(R.id.shopping_list);

        ShoppingListAdapter shoppingListAdapter = new ShoppingListAdapter(ShoppingListActivity.this, shoppingList);
        shoppingListView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        shoppingListView.setAdapter(shoppingListAdapter);

        Button backBtn = findViewById(R.id.shopping_back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}