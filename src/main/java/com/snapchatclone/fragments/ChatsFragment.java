package com.snapchatclone.fragments;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.snapchatclone.helpers.ChatUserAdapter;
import com.snapchatclone.helpers.models.ChatUser;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {

    // Structs and more
    ArrayList<ChatUser> chatUsers;
    ChatUserAdapter userAdapter;

    // UI components
    RecyclerView chatsView;
    // SearchView searchView; // For later -> Advanced searching of users

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chats, container, false);

        // UI components assignment
        chatsView = v.findViewById(R.id.recyclerView);

        chatUsers = retrieveChatUsers();
        userAdapter = new ChatUserAdapter(getContext(), chatUsers);

        chatsView.setLayoutManager(new LinearLayoutManager(getContext()));
        chatsView.setAdapter(userAdapter);

        return v;
    }

    // Retrieves users data from the data base
    private ArrayList<ChatUser> retrieveChatUsers() {
        final ArrayList<ChatUser> users = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot chats) {
                for(DataSnapshot chat: chats.getChildren()) {
                    // String email = (String) chat.child("email").getValue();
                    // String uid =  (String) chat.child("uid").getValue();
                    // ChatUser chatUser = new ChatUser(email, uid , findImg(email));
                    users.add(new ChatUser("r", "r", "g"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return users;
    }
}
