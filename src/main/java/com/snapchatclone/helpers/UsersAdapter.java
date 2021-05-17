package com.snapchatclone.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import com.snapchatclone.R;
import com.snapchatclone.helpers.models.User;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {

    private ArrayList<User> users;
    private Context context;

    public UsersAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.recycler_item, null);
        return new UserHolder(view);
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        holder.emailTextView.setText(this.users.get(0).getEmail());
        holder.followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Add to following function
            }
        });
    }

    static class UserHolder extends RecyclerView.ViewHolder {
        TextView emailTextView;
        Button followButton;


        public UserHolder(@NonNull View itemView) {
            super(itemView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            followButton = itemView.findViewById(R.id.followButton);
        }
    }
}
