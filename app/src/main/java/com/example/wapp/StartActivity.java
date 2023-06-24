package com.example.wapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

    Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
//             User is already authenticated, navigate to the next activity
            Intent intent = new Intent(StartActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

        startBtn = findViewById(R.id.startButton);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class) );
                finish();
            }
        });


    }
}