package com.example.wapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wapp.adapter.Item;
import com.example.wapp.adapter.ItemAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ItemAdapter itemAdapter;
    List<Item> itemList = new ArrayList<>();

    FirebaseFirestore firestore;
    RecyclerView foodRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menupage);

        foodRecyclerView = findViewById(R.id.foodRecyclerView);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#0F9D58"));


        // Initialise views

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        itemAdapter = new ItemAdapter(itemList);
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
