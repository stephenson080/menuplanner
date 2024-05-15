package com.example.menuplanner.entities;

public class Recipe {
    private int _id;
    private String title;
    private String description;

    private String ingredients;

    private String username;
    private Boolean isPrivate;

    private String type;

    public int get_id(){
        return this._id;
    }

    public String getTitle(){
        return this.title;
    }

    public String getType(){
        return this.type;
    }

    public String getDescription(){
        return this.description;
    }

    public String getIngredients(){
        return this.ingredients;
    }

    public String getUsername(){
        return this.username;
    }

    public Boolean getIsPrivate(){
        return this.isPrivate;
    }

    public void set_id(int id){
        this._id = id;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setTitle(String title){
        this.title = title;
    }
    public void setType(String title){
        this.type = type;
    }

    public void setIngredients(String ingredients){
        this.ingredients = ingredients;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setIsPrivate(Boolean isPrivate){
        this.isPrivate = isPrivate;
    }
}
