package com.snapchatclone.helpers;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snapchatclone.helpers.models.ChatUser;
import com.snapchatclone.helpers.models.User;

import java.util.ArrayList;

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ChatHolder> {

    Context context;
    ArrayList<ChatUser> users;

    public ChatUserAdapter(Context context, ArrayList<ChatUser> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ChatUserAdapter.ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUserAdapter.ChatHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    static class ChatHolder extends RecyclerView.ViewHolder {

        TextView email;
        ImageView profileImage;

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
