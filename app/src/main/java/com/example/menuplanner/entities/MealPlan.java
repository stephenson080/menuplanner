package com.example.menuplanner.entities;

import java.time.LocalDate;

public class MealPlan {
    private int _id;
    private Recipe recipe;
    private LocalDate date;
    private String mealType;

    private boolean isPrivate;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    // Getter and Setter for date
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMealType() {
        return this.mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public boolean getIsPrivate(){
        return this.isPrivate;
    }

    public void setIsPrivate(boolean isPrivate){
        this.isPrivate = isPrivate;
    }
}

