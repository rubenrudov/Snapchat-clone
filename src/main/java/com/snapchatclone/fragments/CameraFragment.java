package com.snapchatclone.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera; // NOT GRAPHICS !!!!!!!!!!!!!
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.Button;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.snapchatclone.R;
import com.snapchatclone.activities.DisplayActivity;
import com.snapchatclone.activities.MainActivity;
import com.snapchatclone.activities.UsersListActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class CameraFragment extends Fragment {

    private final static String[] cameraPermission = new String[] {
            Manifest.permission.CAMERA
    };

    // Properties
    private Camera camera;
    private Camera.PictureCallback callback;

    // UI components
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private ImageButton logoutButton, searchButton, takePictureButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_camera, container, false);

        // Logout button setting + onClickListener binding
        logoutButton = v.findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent toMain = new Intent(getActivity(), MainActivity.class);
                startActivity(toMain);
                Objects.requireNonNull(getActivity()).finish();
            }
        });

        searchButton = v.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent findUsers = new Intent(getActivity(), UsersListActivity.class);
                startActivity(findUsers);
            }
        });

        // Getting the capturing button id
        takePictureButton = v.findViewById(R.id.takePictureButton);

        if (ActivityCompat.checkSelfPermission(v.getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            surfaceView = v.findViewById(R.id.cameraView);
            surfaceHolder = surfaceView.getHolder();
            surfaceHolder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    camera = Camera.open();
                    Camera.Parameters cameraParams = camera.getParameters(); // Deprecated after api 16, pay attention
                    camera.setDisplayOrientation(90); // Regular vision of camera (vertical/landscape visions..)
                    cameraParams.setPreviewFrameRate(60); // 40 FPS
                    cameraParams.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);

                    ArrayList<Camera.Size> sizes = (ArrayList<Camera.Size>) camera.getParameters().getSupportedPreviewSizes();
                    Camera.Size size = sizes.get(0); // First value of size
                    sizes.remove(0);

                    Log.d("Sizes list", sizes.toString());
                    for (Camera.Size s: sizes) {
                        // Chooses the best size of the camera for our screen
                        if (s.width * s.height > size.height * s.width) {
                            size = s;
                        }
                    }

                    cameraParams.setPictureSize(size.width, size.height);

                    try {
                        camera.setPreviewDisplay(surfaceHolder);
                        camera.startPreview();

                        // TODO: Implement video capturing

                        takePictureButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                camera.takePicture(null, null, callback);
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }
            });
        } else {
            requestPermissions(
                    cameraPermission,
                     1337
            );
        }

        // Call back for images setting
        callback = new Camera.PictureCallback() {

            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                display(new Intent(getActivity(), DisplayActivity.class), data);
            }

            private void display(Intent intent, byte[] data) {
                // Putting extras and sends to a new activity
                intent.putExtra("image", data);
                startActivity(intent);
            }
        };

        return v;
    }
}
