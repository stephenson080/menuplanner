package com.example.menuplanner.components.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuplanner.R;
import com.example.menuplanner.components.cards.ShoppingListCard;

import java.util.ArrayList;

public class ShoppingListAdapter  extends RecyclerView.Adapter<ShoppingListCard> {
    private ArrayList<String> shoppingList;
    private final Context context;


    private static final String TAG = "ShoppingListAdapter";
    public ShoppingListAdapter(Context context,ArrayList<String> list) {
        this.context = context;
        this.shoppingList = list;
    }

    @NonNull
    @Override
    public ShoppingListCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listCard = layoutInflater.inflate(R.layout.shopping_list_card, parent, false);
        ShoppingListCard card = new ShoppingListCard(listCard);
        return card;
    }

    @Override
    public int getItemCount() {
        return ( this.shoppingList == null ? 0 : this.shoppingList.size() );
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListCard card, int position) {
        String ingredient = this.shoppingList.get(position);


        card.ingredient.setText(ingredient);

    }




}

