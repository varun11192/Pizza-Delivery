package com.example.wapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProfileUpdate extends AppCompatActivity {

    private EditText nameEt, mobileEt, emailEt, addressEt;
    private Button updateBtn;
    private ImageView backBtn;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_info);

        nameEt = findViewById(R.id.full_name_et);
        mobileEt = findViewById(R.id.mobile_et);
        emailEt = findViewById(R.id.email_et);
        addressEt = findViewById(R.id.cnf_new_pass_et);
        updateBtn = findViewById(R.id.updateBtn);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, Object> data = new HashMap<>();
                data.put("name", nameEt.getText().toString());
                data.put("mobile", mobileEt.getText().toString());
                data.put("email", emailEt.getText().toString());
                data.put("address", addressEt.getText().toString());

                firebaseFirestore.collection("users").document(FirebaseAuth.getInstance().getUid())
                        .set(data)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@androidx.annotation.NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ProfileUpdate.this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(ProfileUpdate.this, task.getException().getMessage() , Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        } else{
            return false;
        }

    }

}