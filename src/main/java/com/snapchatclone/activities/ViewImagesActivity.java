package com.snapchatclone.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.snapchatclone.R;

import java.util.ArrayList;
import java.util.Objects;

public class ViewImagesActivity extends AppCompatActivity {

    private String uid, currentUid, isStory;
    private ImageView displayImageView;
    private boolean hasStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_images);
        Objects.requireNonNull(getSupportActionBar()).hide();

        currentUid = FirebaseAuth.getInstance().getUid();
        assert getIntent().getExtras() != null;
        uid = getIntent().getExtras().getString("uid");
        isStory = getIntent().getExtras().getString("type");

        displayImageView = findViewById(R.id.image);

        if (isStory.equals("story")) {
            // Display story image
            displayStory();
        } else {
            // Display direct image
            Log.d("Displays", "Chat");
        }
    }

    ArrayList<String> imagesLinks = new ArrayList<>();

    private void displayStory() {
        final DatabaseReference storiesRef = FirebaseDatabase.getInstance().getReference().child("users").child(uid);

        storiesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String image;
                long uploadedAt = 0, deleteTime = 0, currentTime = System.currentTimeMillis();

                for (DataSnapshot story: snapshot.child("stories").getChildren()) {
                    uploadedAt = Long.parseLong(Objects.requireNonNull(story.child("currentTime").getValue()).toString());
                    deleteTime = Long.parseLong(Objects.requireNonNull(story.child("deleteTime").getValue()).toString());
                    image = Objects.requireNonNull(story.child("image").getValue()).toString();

                    if (deleteTime >= currentTime) {
                        imagesLinks.add(image);

                        if (!hasStarted) {
                            hasStarted = true;
                            initializeDisplay();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void initializeDisplay() {
        final int[] index = {0};
        String link = imagesLinks.get(index[0]);

        Glide.with(getApplicationContext()).load(link).into(displayImageView);

        // Log.d("LINK", link);

        displayImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Handler handler = new Handler();
                final int delay = 5000;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(index[0] == imagesLinks.size() - 1){
                            finish();
                            return;
                        }

                        index[0]++;
                        String link = imagesLinks.get(index[0]);
                        // Log.d("LINK", link);

                        Glide.with(getApplicationContext()).load(link).into(displayImageView);
                        handler.postDelayed(this, delay);
                    }
                }, delay);
            }
        });
    }

}