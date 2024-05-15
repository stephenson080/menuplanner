package com.example.menuplanner.components.cards;

import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuplanner.R;

public class MealPlanCard extends RecyclerView.ViewHolder{
    public MealPlanCard(@NonNull View itemView) {
        super(itemView);

        mealType = itemView.findViewById(R.id.meal_selected_meal_type);
        isPrivate = itemView.findViewById(R.id.meal_is_private);
        selectedRecipe = itemView.findViewById(R.id.meal_selected_recipe);
        selectedDate = itemView.findViewById(R.id.meal_selected_date);
        editMealBtn = itemView.findViewById(R.id.edit_meal_btn);
        deleteMealBtn = itemView.findViewById(R.id.delete_meal_btn);
        recipesSpinner = itemView.findViewById(R.id.meal_recipe_spinner);
        mealtypeSpinner = itemView.findViewById(R.id.meal_meal_type_spinner);
        selectDateBtn = itemView.findViewById(R.id.meal_select_date_btn);

    }

    public TextView mealType;
    public Switch isPrivate;
    public TextView selectedRecipe;

    public TextView selectedDate;
    public Button editMealBtn;

    public Button selectDateBtn;

    public Button deleteMealBtn;

    public Spinner recipesSpinner;

    public Spinner mealtypeSpinner;
}