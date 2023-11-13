
package com.example.enlight21.Models;

public class Post {
    private String postUrl;
    private String caption;

    public Post() {}

    public Post(String postUrl, String caption) {
        this.postUrl = postUrl;
        this.caption = caption;
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


}
