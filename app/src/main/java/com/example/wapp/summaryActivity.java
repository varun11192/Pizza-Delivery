package com.example.wapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.example.wapp.adapter.Item;
import com.example.wapp.adapter.ItemAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private List<Item> itemList;
    private SummaryItemAdapter summaryItemAdapter;
    private RecyclerView summaryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        itemList = new ArrayList<>();
        summaryItemAdapter = new SummaryItemAdapter(itemList);
        summaryRecyclerView = findViewById(R.id.summary_recycler_view);
        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        summaryRecyclerView.setAdapter(summaryItemAdapter);

        // Fetch user orders
        String userId = mAuth.getCurrentUser().getUid();
        fetchUserOrders(userId);
    }

    private void fetchUserOrders(String userId) {
        CollectionReference ordersRef = firestore.collection("orders");
        Query query = ordersRef.whereEqualTo("userId", userId);

        query.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                itemList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String orderId = document.getId();
                    String itemId = document.getString("itemId");
                    String name = document.getString("name");
                    double price = document.getDouble("price");
                    String status = document.getString("status");
                    String imageString = document.getString("image");

                    // Convert the Base64 string to a byte array
                    byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);

                    Log.d(TAG, "Fetched Image: " + Arrays.toString(imageBytes));
                    Item item = new Item(orderId, itemId, name, price, status, imageBytes);
                    itemList.add(item);
                    Log.d(TAG, "Fetched Item: " + item.toString());
                    Log.d(TAG, "Fetched Image: " + Arrays.toString(item.getImageBytes()));
                }
                summaryItemAdapter.notifyDataSetChanged();
            } else {
                Log.e(TAG, "Error fetching user orders: ", task.getException());
            }
        });
    }
}
