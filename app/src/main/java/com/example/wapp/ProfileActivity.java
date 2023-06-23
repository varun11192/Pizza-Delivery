package com.example.wapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class ProfileActivity extends AppCompatActivity {

    TextView nameEditTxt, contactEditTxt, nameTv, mobileTv, emailTv, addressTv;
    FirebaseAuth auth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.cart:
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                    return true;
                case R.id.orders:
                    startActivity(new Intent(getApplicationContext(), OrdersActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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

        firebaseFirestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        String uid = firebaseUser.getUid();

        if (firebaseUser != null) {
            // User is signed in
            firebaseFirestore.collection(uid)
                    .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()){
                                for (DocumentSnapshot snapshot: task.getResult()) {
                                    nameTv.setText(snapshot.getString("name"));
                                    mobileTv.setText(snapshot.getString("mobile"));
                                    emailTv.setText(snapshot.getString("email"));
                                    addressTv.setText(snapshot.getString("address"));
                                }
                            } else {
                                Toast.makeText(ProfileActivity.this, task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // No user is signed in
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