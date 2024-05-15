package com.example.menuplanner.components.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.menuplanner.R;
import com.example.menuplanner.components.cards.RecipeCard;
import com.example.menuplanner.entities.Recipe;
import com.example.menuplanner.entities.User;
import com.example.menuplanner.utils.Db;

import java.util.ArrayList;

public class RecipesListAdapter  extends RecyclerView.Adapter<RecipeCard> {
    private ArrayList<Recipe> recipes;
    private final Context context;
    private final User currentUser;

    private static final String TAG = "RecipeListAdapter";
    public RecipesListAdapter(Context context, User currentUser, ArrayList<Recipe> recipes) {
        this.context = context;
        this.currentUser = currentUser;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeCard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listRecipe = layoutInflater.inflate(R.layout.recipe_card, parent, false);
        RecipeCard recipeCard = new RecipeCard(listRecipe);
        return recipeCard;
    }

    @Override
    public int getItemCount() {
        return ( this.recipes == null ? 0 : this.recipes.size() );
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeCard card, int position) {
        Recipe recipe = this.recipes.get(position);

        Log.i(TAG, recipe.getUsername());
        card.name.setText(recipe.getTitle());

        card.isPrivate.setChecked(recipe.getIsPrivate());
        card.isPrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                recipe.setIsPrivate(isChecked);
            }


        });

        card.name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }});


        card.des.setText(recipe.getDescription());
        card.des.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        card.ingredients.setText(recipe.getIngredients());
        card.ingredients.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        card.editRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser.getUsername().equalsIgnoreCase(recipe.getUsername())){
                    Db db = new Db(context.getApplicationContext());
                    Recipe editRecipe = new Recipe();
                    editRecipe.set_id(recipe.get_id());
                    editRecipe.setDescription(card.des.getText().toString());
                    editRecipe.setIngredients(card.ingredients.getText().toString());
                    editRecipe.setTitle(card.name.getText().toString());
                    editRecipe.setType(recipe.getType());
                    editRecipe.setIsPrivate(recipe.getIsPrivate());
                    editRecipe.setUsername(recipe.getUsername());
                    boolean res = db.editRecipe(editRecipe);
                    if (res){
                        Toast.makeText(context.getApplicationContext(), "Edited!", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(context.getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

        card.deleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentUser.getUsername().equalsIgnoreCase(recipe.getUsername())){
                    Db db = new Db(context.getApplicationContext());
                    boolean res = db.deleteRecipe(recipe.get_id());
                    if (res){
                        recipes.remove(recipe);
                        Toast.makeText(context.getApplicationContext(), "Deleted!", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }else {
                        Toast.makeText(context.getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

    public void updateRecipes(ArrayList<Recipe> list) {
        this.recipes = list;
        notifyDataSetChanged();
    }




}

