package com.example.menuplanner.views;

import androidx.lifecycle.ViewModel;

import com.example.menuplanner.entities.User;

public class UserViewModel extends ViewModel {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
