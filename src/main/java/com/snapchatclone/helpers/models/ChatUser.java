package com.snapchatclone.helpers.models;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Class for users handling in the database
 */
public class ChatUser extends User {
    private String imageUrl;

    public ChatUser(String email, String uid, String imageUrl) {
        super(email, uid, false);
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public String getUid() {
        return super.getUid();
    }

    public void setUid(String uid) {
        super.setEmail(uid);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return "ChatUser{" +
                "email='" + getEmail() + '\'' +
                ", uid='" + getUid() + '\'' +
                '}';
    }
}
