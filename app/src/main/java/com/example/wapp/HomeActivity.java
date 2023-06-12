package com.example.wapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class HomeActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ItemAdapter itemAdapter;
    List<Item> itemList = new ArrayList<>();
EditText Uname;
Button button;
FirebaseFirestore firestore;
RecyclerView foodRecyclerView;

    private void addSampleDataToFirestore() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        CollectionReference itemsCollection = firestore.collection("items");

        Item item1 = new Item("Item 1", 10.99, 5, "https://example.com/item1.jpg");
        Item item2 = new Item("Item 2", 15.99, 8, "https://example.com/item2.jpg");
        Item item3 = new Item("Item 3", 7.99, 3, "https://example.com/item3.jpg");

        itemsCollection.document("item1").set(item1);
        itemsCollection.document("item2").set(item2);
        itemsCollection.document("item3").set(item3);
        itemsCollection.document("item4").set(item1);
        itemsCollection.document("item5").set(item2);
        itemsCollection.document("item6").set(item3);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menupage);


        foodRecyclerView = findViewById(R.id.foodRecyclerView);
        foodRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(
                Color.parseColor("#0F9D58"));
       // actionBar.setBackgroundDrawable(colorDrawable);

        // initialise views



        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        AtomicReference<ItemAdapter> itemAdapterRef = new AtomicReference<>(new ItemAdapter(new ArrayList<>()));

// When you want to set a new list of items:
        itemAdapterRef.get().setItems(itemList);
        itemAdapterRef.get().notifyDataSetChanged();

// When you want to get the current ItemAdapter instance:
        ItemAdapter itemAdapter = itemAdapterRef.get();

        foodRecyclerView.setAdapter(itemAdapter);





        db.collection("items").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

                for (QueryDocumentSnapshot document : task.getResult()) {
                    // Extract data from the document and create an Item object
                    String name = document.getString("name");
                    double price = document.getDouble("price");
                    int quantity = document.getLong("quantity").intValue();
                    String imageUrl = document.getString("imageUrl");

                    Item item = new Item(name, price, quantity, imageUrl);
                    itemList.add(item);
                    Log.d(TAG, "Fetched Item: " + item.toString());
                }
                itemAdapter.notifyDataSetChanged();

            } else {
                // Handle the error
                Log.e(TAG, "Error getting documents: ", task.getException());
            }
        });
        addSampleDataToFirestore();






//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Map<String,Object> user = new HashMap<>();
//
//                user.put("name", Uname.getText().toString());
//                user.put("phone", mAuth.getCurrentUser().getPhoneNumber());
//
//
//                db.collection("ids").document(mAuth.getCurrentUser().getUid())
//                        .set(user)
//                        .addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Log.d("tag", "DocumentSnapshot successfully written!");
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Log.w("tag", "Error writing document", e);
//                            }
//                        });
//
//
//                Task<QuerySnapshot> docRef = db.collection("ids")
//                        .get()
//                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                            @Override
//                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                if (task.isSuccessful()) {
//                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        Log.d("TAG", document.getId() + " => " + document.getData());
//                                    }
//                                } else {
//                                    Log.d("TAG", "Error getting documents: ", task.getException());
//                                }
//                            }
//                        });
//
//            }
//        });



    }

}