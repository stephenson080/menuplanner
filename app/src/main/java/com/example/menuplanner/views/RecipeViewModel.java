package com.example.menuplanner.views;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


public class RecipeViewModel extends ViewModel {
    private final MutableLiveData<Boolean> recipeAdded = new MutableLiveData<>();

    public LiveData<Boolean> getRecipeAdded() {
        return recipeAdded;
    }

    public void setRecipeAdded(boolean added) {
        recipeAdded.setValue(added);
    }
}