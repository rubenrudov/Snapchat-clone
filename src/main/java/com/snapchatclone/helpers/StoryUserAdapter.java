package com.snapchatclone.helpers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.snapchatclone.R;
import com.snapchatclone.activities.DisplayActivity;
import com.snapchatclone.activities.ViewImagesActivity;
import com.snapchatclone.helpers.models.User;

import java.util.ArrayList;

public class StoryUserAdapter extends RecyclerView.Adapter<StoryUserAdapter.StoryHolder> {

    ArrayList<User> users;
    private Context context;

    public StoryUserAdapter(Context context, ArrayList<User> users) {
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public StoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.stories_list_item, null);
        return new StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final StoryHolder holder, int position) {
        String tag = users.get(position).getIsStory() ? "story" : "chatPic";
        holder.itemView.setTag(tag);
        holder.email.setText(users.get(position).getEmail());
        holder.email.setTag(users.get(position).getUid());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewImagesActivity.class);
                Bundle b = new Bundle();
                b.putString("uid", holder.email.getTag().toString());
                b.putString("type", v.getTag().toString());
                intent.putExtras(b);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    static class StoryHolder extends RecyclerView.ViewHolder {

        TextView email;

        StoryHolder(final View itemView){
            super(itemView);
            email = itemView.findViewById(R.id.email);
        }
    }
}
