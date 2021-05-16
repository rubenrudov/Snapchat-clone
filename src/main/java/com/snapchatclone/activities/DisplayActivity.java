package com.snapchatclone.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.snapchatclone.R;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Objects;

public class DisplayActivity extends AppCompatActivity {

    private Intent pictureIntent;
    private byte[] data;
    Bitmap rotated;

    // UI components
    private ImageView display;
    private ImageButton approveButton, dismissButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        Objects.requireNonNull(getSupportActionBar()).hide();
        pictureIntent = getIntent();
        data = Objects.requireNonNull(pictureIntent.getExtras()).getByteArray("image");

        // Components assignment
        display = findViewById(R.id.capturedImage);

        approveButton = findViewById(R.id.approveButton);
        dismissButton = findViewById(R.id.dismissButton);

        approveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference reference = FirebaseDatabase.
                        getInstance().
                        getReference().
                        child("users").
                        child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).
                        child("stories");

                final String storyKey = reference.push().getKey();
                assert storyKey != null;
                Log.d("KEY", storyKey);

                final StorageReference storageReference  = FirebaseStorage.getInstance().getReference().child("stories").child(storyKey);

                ByteArrayOutputStream b = new ByteArrayOutputStream();
                rotated.compress(Bitmap.CompressFormat.JPEG, 20, b);
                byte[] data = b.toByteArray();
                final UploadTask task = storageReference.putBytes(data);

                // If task went wrong
                task.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

                // If task went successful
                task.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                        while(!uri.isComplete());
                        String url = Objects.requireNonNull(uri.getResult()).toString();
                        Log.d("URL", url);

                        // Set time of remove for 24 hours later:
                        long current = System.currentTimeMillis();
                        long deleteTime = current + 24 * 3600 * 1000;

                        // Update the story reference
                        HashMap<String, Object> dataMap = new HashMap<>();
                        dataMap.put("image", url);
                        dataMap.put("currentTime", current);
                        dataMap.put("deleteTime", deleteTime);

                        reference.child(storyKey).setValue(dataMap);
                        finish();
                    }
                });
            }
        });

        dismissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Background setting
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);

        int height = bitmap.getHeight();
        int width = bitmap.getWidth();

        Matrix m = new Matrix();
        m.setRotate(90);

        rotated = Bitmap.createBitmap(
                bitmap,
                0,
                0,
                width,
                height,
                m,
                true
        );

        display.setBackgroundDrawable(new BitmapDrawable(getApplicationContext().getResources(), rotated));
    }
}
