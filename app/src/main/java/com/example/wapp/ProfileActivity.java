package com.example.wapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends AppCompatActivity {

    TextView  nameTv, mobileTv, emailTv, addressTv, nameBigTv;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;

    Button nameEditTxt, contactEditTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //ask user to edit details
            Toast.makeText(this, "Please update your personal details.", Toast.LENGTH_SHORT).show();

        }
        
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                    return true;
                case R.id.cart:
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    finish();
                    return true;
                case R.id.orders:
                    startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                    finish();
                    return true;
                case R.id.profile:
                    return true;

            }
            return false;
        });


        nameTv = findViewById(R.id.nameTv);
        mobileTv = findViewById(R.id.mobileTv);
        emailTv = findViewById(R.id.emailTv);
        addressTv = findViewById(R.id.addressTv);
        nameEditTxt = findViewById(R.id.nameEditBtn);
        contactEditTxt = findViewById(R.id.contactEdit);
        nameBigTv = findViewById(R.id.nameBigTv);

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        String userId = firebaseUser.getUid();

        if (firebaseUser != null) {
            // User is signed in
            DocumentReference documentReference = firebaseFirestore.collection("users")
                    .document(userId);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    nameBigTv.setText(value.getString("name"));
                    nameTv.setText(value.getString("name"));
                    mobileTv.setText(value.getString("mobile"));
                    emailTv.setText(value.getString("email"));
                    addressTv.setText(value.getString("address"));
                }
            });
        } else {
            // No user is signed in
            Toast.makeText(this, "Please SignIn and Update your personal details.", Toast.LENGTH_SHORT).show();
        }
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

}