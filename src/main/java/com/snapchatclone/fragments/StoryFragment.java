package com.snapchatclone.fragments;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.snapchatclone.R;
import com.snapchatclone.helpers.StoryUserAdapter;
import com.snapchatclone.helpers.UserInfo;
import com.snapchatclone.helpers.models.User;

import java.util.ArrayList;
import java.util.Objects;

public class StoryFragment extends Fragment {

    ArrayList<User> users;

    // UI components
    private RecyclerView recyclerView;
    private Button refreshList;

    // Helpers
    private RecyclerView.LayoutManager lm;
    private RecyclerView.Adapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_story, container, false);

        // Assignments
        recyclerView = v.findViewById(R.id.recyclerView);

        lm = new LinearLayoutManager(getContext());


        users = new ArrayList<>();
        getData();
        adapter = new StoryUserAdapter(getContext(), users);

        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);

        return v;
    }

    private void getData() {
        for (String uid: UserInfo.listFollowing){
            DatabaseReference friendStory = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

            // Getting stories
            friendStory.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String email = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                    String uid = dataSnapshot.getRef().getKey();

                    // TODO: Pass the uploadedAt var to the display story act
                    long uploadedAt;
                    long finishAt;
                    long currentTime = System.currentTimeMillis();

                    // For each story check if there are no more than 24 hours since it was uploaded
                    for (DataSnapshot storySnapshot : dataSnapshot.child("stories").getChildren()) {
                        uploadedAt = Long.parseLong(Objects.requireNonNull(storySnapshot.child("currentTime").getValue()).toString());
                        finishAt = Long.parseLong(Objects.requireNonNull(storySnapshot.child("deleteTime").getValue()).toString());

                        if (currentTime <= finishAt){
                            User user = new User(email, uid, true);

                            if (users.contains(user)){
                                continue;
                            }
                            else {
                                users.add(user);
                                Log.d("ADDED", user.toString());
                                Log.d("Stories", Objects.requireNonNull(storySnapshot.child("image").getValue()).toString());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}
