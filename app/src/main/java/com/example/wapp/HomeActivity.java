package com.example.wapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.wapp.adapter.Item;
import com.example.wapp.adapter.ItemAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button cartButton;
    FloatingActionButton add_alarm_fab;
    ItemAdapter itemAdapter;
    List<Item> itemList = new ArrayList<>();

    FirebaseFirestore firestore;
    RecyclerView foodRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menupage);

        final dialog loadingdialog = new dialog(HomeActivity.this);

        Boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("isFirstRun", true);

        if (isFirstRun) {
            //show loading dialog
            loadingdialog.startLoadingdialog();

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadingdialog.dismissdialog();
                }
            }, 4000);
        }


        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                .putBoolean("isFirstRun", false).commit();



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_home:
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
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    finish();
                    return true;

            }
            return false;
        });

        add_alarm_fab=findViewById(R.id.add_alarm_fab);
        add_alarm_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(HomeActivity.this, chatbot.class);
                startActivity(in);
            }
        });
//        cartButton = findViewById(R.id.cartButton);
//        cartButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(HomeActivity.this,CartActivity.class);
//                startActivity(i);
//            }
//        });
        foodRecyclerView = findViewById(R.id.foodRecyclerView);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0F9D58"));


        // Initialise views

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        itemAdapter = new ItemAdapter(itemList, firestore, mAuth, this);
        foodRecyclerView.setAdapter(itemAdapter);

        CollectionReference itemsRef = firestore.collection("items");
        itemsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                itemList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {

                    String name = document.getString("name");
                    double price = document.getDouble("price");
                    int quantity =0;
                    String imageString = document.getString("image");

                    // Convert the Base64 string to a byte array
                    byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);

                    Log.d(TAG, "Fetched Image: " + Arrays.toString(imageBytes));
                    Item item = new Item(name, price, quantity, imageBytes);
                    itemList.add(item);
                    Log.d(TAG, "Fetched Item: " + item.toString());
                    Log.d(TAG, "Fetched Image: " + Arrays.toString(item.getImageBytes()));
                }
                itemAdapter.notifyDataSetChanged();
            } else {
                Log.e(TAG, "Error getting documents: ", task.getException());
            }
        });
    }
}

