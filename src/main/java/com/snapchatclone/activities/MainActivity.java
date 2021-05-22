package com.snapchatclone.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.snapchatclone.R;
import com.snapchatclone.helpers.UserInfo;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // UI components
    Button toLogin, toRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).hide();

        // UI components assignment by ID
        toLogin = findViewById(R.id.toLogin);
        toRegister = findViewById(R.id.toRegister);

        // onClickListeners
        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }
}
