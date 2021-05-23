package com.snapchatclone.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.snapchatclone.R;
import com.snapchatclone.helpers.models.ChatUser;
import com.snapchatclone.helpers.models.User;

import java.util.ArrayList;

public class ChatUserAdapter extends RecyclerView.Adapter<ChatUserAdapter.ChatHolder> {

    public Context context;
    ArrayList<ChatUser> users;

    public ChatUserAdapter(Context context, ArrayList<ChatUser> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public ChatUserAdapter.ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, null);
        return new ChatUserAdapter.ChatHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatUserAdapter.ChatHolder holder, int position) {
        holder.email.setText(users.get(position).getEmail());
        Glide.with(context).load(users.get(position).getImageUrl()).into(holder.profileImage);
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    static class ChatHolder extends RecyclerView.ViewHolder {

        TextView email;
        ImageView profileImage;

        ChatHolder(@NonNull View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.email);
            profileImage = itemView.findViewById(R.id.profileImage);
        }
    }
}
