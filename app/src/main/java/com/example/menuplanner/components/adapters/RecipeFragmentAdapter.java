package com.example.menuplanner.components.adapters;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.menuplanner.components.fragments.AddRecipeFragment;
import com.example.menuplanner.components.fragments.RecipeListFragment;
import com.example.menuplanner.entities.User;
import com.example.menuplanner.utils.Db;


public class RecipeFragmentAdapter extends FragmentStateAdapter   {
    private final User currentUser;
    private final Db databaseManager;

    public RecipeFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle, User currentUser, Db databaseManager) {
        super(fragmentManager, lifecycle);
        this.currentUser = currentUser;
        this.databaseManager = databaseManager;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if ( position == 0 )
            return new RecipeListFragment(this.databaseManager, this.currentUser);
        else if ( position == 1 )
            return new AddRecipeFragment(this.currentUser, databaseManager);

        return null;
    }



    @Override
    public int getItemCount() {
        return 2;
    }
}

