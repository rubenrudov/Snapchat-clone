package com.snapchatclone.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.snapchatclone.R;
import com.snapchatclone.helpers.UsersAdapter;
import com.snapchatclone.helpers.models.User;

import java.util.ArrayList;
import java.util.Objects;

public class UsersListActivity extends AppCompatActivity {

    private Button searchButton;
    private EditText usersEditText;

    private RecyclerView recyclerView;
    private UsersAdapter adapter;
    private RecyclerView.LayoutManager lm;
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // UI components assignment
        recyclerView = findViewById(R.id.recyclerView);
        searchButton = findViewById(R.id.searchButton);
        usersEditText = findViewById(R.id.usersEditText);

        users = getUsersFromDb();
        searchButton .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenForData();
            }
        });


        // TODO: Find users dynamically
        adapter = new UsersAdapter(users, this);

        // Settings for the recycler
        recyclerView.setNestedScrollingEnabled(true);
        lm = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }

    private ArrayList<User> getUsersFromDb() {
        // Defining the empty users list
        ArrayList<User> data = new ArrayList<>();

        // Getting all the users from the database
        listenForData();

        // Returning the data
        return data;
    }

    private void listenForData() {
        DatabaseReference usersDb = FirebaseDatabase.getInstance().getReference().child("users");
        Query query = usersDb.orderByChild("email").startAt(usersEditText.getText().toString()).endAt(usersEditText.getText().toString() + "\uf8ff");
        query.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String email = "";
                String uid = dataSnapshot.getRef().getKey();
                if (dataSnapshot.child("email").getValue() != null){
                    email = Objects.requireNonNull(dataSnapshot.child("email").getValue()).toString();
                }

                if (!email.equals(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail())){
                    User f = new User(email, uid);
                    users.add(f);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void clear() {
        int size = this.users.size();
        this.users.clear();
        adapter.notifyItemRangeChanged(0, size);
    }

}
