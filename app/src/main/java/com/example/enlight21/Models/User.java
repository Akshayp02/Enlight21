package com.example.enlight21.Models;

public class User {
    public String username;
    public String password;
    public String email;
    public String image;
    public String description;
    private boolean following = true;

    public User() {
        // Default constructor required for Firebase
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username, String password, String email, String image, String description) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
        this.description = description;
    }

    public User(String username, String description) {
        this.username = username;
        this.description = description;
    }

    public User(String username, String password, String email, String image) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return username;
    }

    public String getDescription() {
        return description;
    }

    public void setName(String name) {
        this.username = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }



    // Getter and setter for following
    public boolean isFollowing() {
        return following;
    }

    public void setFollowing(boolean following) {
        this.following = following;
    }

    public void setEmail(String email) {
        this.email =email;
    }
}
