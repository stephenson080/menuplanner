package com.example.menuplanner.components.cards;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuplanner.R;

public class ShoppingListCard extends RecyclerView.ViewHolder {
    public ShoppingListCard(@NonNull View itemView) {
        super(itemView);
        ingredient = itemView.findViewById(R.id.shopping_ingredient);
    }
    public TextView ingredient;

}