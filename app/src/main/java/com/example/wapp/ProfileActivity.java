package com.example.wapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    TextView nameEditTxt, contactEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        nameEditTxt = findViewById(R.id.nameEditBtn);
        contactEditTxt = findViewById(R.id.contactEdit);

        nameEditTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfileUpdate.class);
                startActivity(intent);
            }
        });

        contactEditTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, ProfileUpdate.class);
                startActivity(intent);
            }
        });



    }

    public static class Alt_auth {
    }
}