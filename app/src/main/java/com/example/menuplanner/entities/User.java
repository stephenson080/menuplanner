package com.example.menuplanner.entities;

import androidx.annotation.NonNull;

public class User {
    public User() {
        this("default");
    }

    public User(String username) {
        setUsername(username);
        setFirstName(INFO_NOT_AVAILABLE);
        setMiddleName(INFO_NOT_AVAILABLE);
        setLastName(INFO_NOT_AVAILABLE);
        setAddress(INFO_NOT_AVAILABLE);
        setCity(INFO_NOT_AVAILABLE);
        setState(INFO_NOT_AVAILABLE);
        setEmail(INFO_NOT_AVAILABLE);
        setPhone(INFO_NOT_AVAILABLE);
        setRole(DEFAULT_ROLE);
    }

    private String username;
    private String firstName;
    private String middleName;
    private String lastName;
    private String address;
    private String city;
    private String state;
    private String email;
    private String phone;
    private String role;

    public static final String INFO_NOT_AVAILABLE = "---";
    public static final String DEFAULT_ROLE = "user";

    @NonNull
    public String toString() {
        String toReturn = getFirstName() + " " + getMiddleName() + " " + getLastName() + " - ";
        toReturn += getAddress() + " " + getCity() + " " + getState() + " " + " - ";
        toReturn += getEmail() + " " + getPhone();
        toReturn += getRole();
        return toReturn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if ( username == null || username.trim().length() == 0 )
            return;
        this.username = username.trim();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if ( firstName == null || firstName.trim().length() == 0 )
            return;
        this.firstName = firstName.trim();
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        if ( role == null || role.trim().length() == 0 )
            return;
        this.role = role;
    }
}
