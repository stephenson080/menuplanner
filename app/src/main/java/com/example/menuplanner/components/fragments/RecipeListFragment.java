package com.example.menuplanner.components.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.menuplanner.R;
import com.example.menuplanner.components.adapters.RecipesListAdapter;
import com.example.menuplanner.entities.Recipe;
import com.example.menuplanner.entities.User;
import com.example.menuplanner.utils.Db;
import com.example.menuplanner.views.RecipeViewModel;

import java.util.ArrayList;

public class RecipeListFragment extends Fragment {

    private final Db dbManager;
    private final User currentUser;

    private final ArrayList<Recipe> recipes;

    private RecipesListAdapter recipesAdapter;




    public RecipeListFragment(Db databaseManager, User curentUser) {
        // Required empty public constructor
        this.dbManager = databaseManager;
        this.currentUser = curentUser;

        ArrayList<Recipe> recipes = dbManager.getAllRecipes();

        this.recipes = recipes;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);

         RecyclerView recipeRecyclerView = view.findViewById(R.id.recipes_list_view);
        RecipesListAdapter recipeListAdapter = new RecipesListAdapter(this.getContext(), this.currentUser, this.recipes);
        this.recipesAdapter = recipeListAdapter;
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recipeRecyclerView.setAdapter(recipeListAdapter);

        RecipeViewModel recipeViewModel = new ViewModelProvider(requireActivity()).get(RecipeViewModel.class);

        // Observe ViewModel changes
        recipeViewModel.getRecipeAdded().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean added) {
                if (added) {
                    // Update the list of recipes
                    updateRecipeList();
                    // Reset the ViewModel state
                    recipeViewModel.setRecipeAdded(false);
                }
            }
        });
        return  view;


    }

    public void updateRecipeList() {
        // Update the list of recipes in your adapter
        recipesAdapter.updateRecipes(dbManager.getAllRecipes());
    }


}