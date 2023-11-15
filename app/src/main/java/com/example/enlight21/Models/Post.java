
package com.example.enlight21.Models;

public class Post {
    private String postUrl;
    private String caption;
    private String username;
    private String Technology;

    public Post() {}

    public Post(String postUrl, String caption) {
        this.postUrl = postUrl;
        this.caption = caption;
    }

    public Post(String postUrl, String caption , String username , String Technology) {
        this.postUrl = postUrl;
        this.caption = caption;
        this.username = username;
        this.Technology = Technology;
    }

    // Getters and setters (optional)
    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setTechnology(String Technology) {
        this.Technology = Technology;
    }

    public String getTechnology() {
        return Technology;
    }


}
