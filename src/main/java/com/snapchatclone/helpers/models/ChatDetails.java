package com.snapchatclone.helpers.models;

import androidx.annotation.NonNull;

// For retrieving data about chats and put it in a list
public class ChatDetails {
    private String from, to, imageLink;

    public ChatDetails(String from, String to, String imageLink) {
        this.from = from;
        this.to = to;
        this.imageLink = imageLink;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @NonNull
    @Override
    public String toString() {
        return "Chat details: From " + this.from + " To " + this.to + " image link: " + this.imageLink;
    }
}
