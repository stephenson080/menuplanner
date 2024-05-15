package com.example.menuplanner.components.cards;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuplanner.R;

public class RecipeCard extends RecyclerView.ViewHolder {
    public RecipeCard(@NonNull View itemView) {
        super(itemView);

        name = itemView.findViewById(R.id.ediit_recipe_name);
        isPrivate = itemView.findViewById(R.id.edit_recipe_is_private);
        des = itemView.findViewById(R.id.edit_recipe_des);
        ingredients = itemView.findViewById(R.id.edit_recipe_in);
        editRecipe = itemView.findViewById(R.id.edit_recipe_btn);
        deleteRecipe= itemView.findViewById(R.id.delete_recipe);
    }

    public EditText name;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    public Switch isPrivate;
    public EditText des;
    public EditText ingredients ;
    public Button editRecipe;

    public Button deleteRecipe;

}