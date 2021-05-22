package com.snapchatclone.helpers.models;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Class for users handling in the database
 */
public class User {
    private String email;
    private String uid;
    private boolean isStory;

    public User(String email, String uid, boolean isStory) {
        this.email = email;
        this.uid = uid;
        this.isStory = isStory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean getIsStory(){
        return this.isStory;
    }

    public void setIsStory(boolean charOrStory){
        this.isStory = charOrStory;
    }

    @Override
    public boolean equals(Object obj) {
        return this.uid.equals(((User) obj).uid);
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", uid='" + uid + '\'' +
                '}';
    }
}
