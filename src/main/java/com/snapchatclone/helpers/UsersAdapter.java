package com.snapchatclone.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.snapchatclone.R;
import com.snapchatclone.helpers.models.User;

import java.util.ArrayList;
import java.util.Objects;

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
    public void onBindViewHolder(@NonNull final UserHolder holder, final int position) {
        holder.emailTextView.setText(this.users.get(0).getEmail());
        holder.followButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                String id = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

                User u = users.get(position);
                String uid = u.getUid();

                if (holder.followButton.getText().toString().equals("follow")) {
                    holder.followButton.setBackgroundResource(R.drawable.ic_remove);
                    holder.followButton.setText("unfollow");
                    FirebaseDatabase.getInstance().getReference().child("users").child(id).child("following").child(uid).setValue(true);
                }

                else {
                    Log.d("Unfollow", "64");
                    holder.followButton.setBackgroundResource(R.drawable.ic_follow);
                    holder.followButton.setText("follow");
                    FirebaseDatabase.getInstance().getReference().child("users").child(id).child("following").child(uid).removeValue();
                }
            }
        });
    }

    static class UserHolder extends RecyclerView.ViewHolder {
        TextView emailTextView;
        Button followButton;

        UserHolder(@NonNull View itemView) {
            super(itemView);
            emailTextView = itemView.findViewById(R.id.emailTextView);
            followButton = itemView.findViewById(R.id.followButton);
        }
    }
}
