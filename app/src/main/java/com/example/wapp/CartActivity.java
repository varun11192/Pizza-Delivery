package com.example.wapp;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wapp.adapter.CartAdapter;
import com.example.wapp.adapter.CartItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class CartActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private RecyclerView cartRecyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItemList;
    private ListenerRegistration cartListenerRegistration;
    private TextView totalPriceTextView;
    private Button checkoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.cart);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                    return true;
                case R.id.cart:
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

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        checkoutButton = findViewById(R.id.checkoutButton);

        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        cartItemList = new ArrayList<>();
        cartAdapter = new CartAdapter(cartItemList, this);
        cartRecyclerView.setAdapter(cartAdapter);

        // Replace `userId` with your actual user ID value
        String userId = mAuth.getCurrentUser().getUid();
        CollectionReference cartItemsRef = firestore.collection("users").document(userId).collection("cart");
        cartItemsRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                cartItemList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    String itemId = document.getId();
                    String name = document.getString("name");
                    double price = document.getDouble("price");
                    String imageString = document.getString("image");

                    // Convert the Base64 string to a byte array
                    byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);

                    CartItem cartItem = new CartItem(itemId, name, price, imageBytes);
                    cartItemList.add(cartItem);
                }
                cartAdapter.notifyDataSetChanged();
                updateTotalPrice();
            } else {
                // Handle the error
                Log.e(TAG, "Error getting documents: ", task.getException());
            }
        });

//        CollectionReference cartRef = firestore.collection("users").document(userId).collection("cart");
//
//        cartListenerRegistration = cartRef.addSnapshotListener((snapshot, error) -> {
//            if (error != null) {
//                Log.e(TAG, "Error listening for cart changes: ", error);
//                return;
//            }
//
//            // Clear the existing list
//            cartItemList.clear();
//
//            // Iterate over the snapshot documents and populate the list
//            for (DocumentSnapshot document : snapshot.getDocuments()) {
//                String itemId = document.getId();
//                String name = document.getString("name");
//                double price = document.getDouble("price");
//                String imageString = document.getString("image");
//
//                // Convert the Base64 string to a byte array
//                byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
//
//                CartItem cartItem = new CartItem(itemId, name, price, imageBytes);
//                cartItemList.add(cartItem);
//            }
//
//            // Notify the adapter that the data set has changed
//            cartAdapter.notifyDataSetChanged();
//            updateTotalPrice();
//        });

        checkoutButton.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(), "Order Placed Successfully", Toast.LENGTH_SHORT).show();
            addOrderToOrders(); // Add the order to Firestore
            clearCart(); // Remove items from the cart
            Intent intent = new Intent(getApplicationContext(),OrdersActivity.class);

            startActivity(intent);
            finish();
        });
    }

    private void updateTotalPrice() {
        double total = 0.0;
        for (CartItem cartItem : cartItemList) {
            total += cartItem.getPrice();
        }

        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String totalPrice = decimalFormat.format(total);
        totalPriceTextView.setText("Rs " + totalPrice);
    }

    public void removeItem(int position) {
        cartItemList.remove(position);
        cartAdapter.notifyItemRemoved(position);
        updateTotalPrice();
    }

//    private void addOrderToOrders() {
//        // Retrieve the user ID
//        String uid = mAuth.getCurrentUser().getUid();
//
//        DocumentReference cartRef = firestore.collection("users").document(uid).collection("order").document();
//        Map<String, Object> order = new HashMap<>();
//        String userId = mAuth.getCurrentUser().getUid();
////        String itemId = document.getId();
////            String name = document.getString("name");
////            double price = document.getDouble("price");
////            String imageString = document.getString("image");
//
//        // Convert the Base64 string to a byte array
//
//        byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
//
//        imageString = Base64.encodeToString(order.getImageBytes(), Base64.DEFAULT);
//        order.put("image", imageString);
//
//        cartRef.set(cartItem)
//                .addOnSuccessListener(aVoid -> {
//                    Toast.makeText(CartActivity.this, "Item added to cart", Toast.LENGTH_SHORT).show();
//                })
//                .addOnFailureListener(e -> {
//                    Toast.makeText(CartActivity.this, "Failed to add item to cart", Toast.LENGTH_SHORT).show();
//                    Log.e(TAG, "Error adding item to cart: ", e);
//                });
//
////        for (DocumentSnapshot document : snapshot.getDocuments()) {
////            String itemId = document.getId();
////            String name = document.getString("name");
////            double price = document.getDouble("price");
////            String imageString = document.getString("image");
////
////            // Convert the Base64 string to a byte array
////            byte[] imageBytes = Base64.decode(imageString, Base64.DEFAULT);
////
////        }
//
//        // Get the order details from the cart or calculate them as needed
//        List<CartItem> items = cartItemList;
//        double totalAmount = getTotalAmount();
//
//        // Generate an order ID (you can use your own logic here)
//        String orderId = generateOrderId();
//
//        // Create a new order document in the "orders" collection
//        DocumentReference orderRef = firestore.collection("users").document(userId);
//
//        Order order = new Order(userId, items,
//                //  itemId, name, price, imageBytes,
//                totalAmount,
//                "preparing", orderId); // Set the initial order status and order ID
//
//
//    }
private void addOrderToOrders() {
    // Retrieve the user ID
    String userId = mAuth.getCurrentUser().getUid();

    // Generate an order ID
    String orderId = generateOrderId(userId);
    getIntent().putExtra(orderId,orderId);

    // Iterate over each cart item and create a separate order document for each item
    for (CartItem cartItem : cartItemList) {
        String itemId = cartItem.getItemId();
        String name = cartItem.getName();
        double price = cartItem.getPrice();
        byte[] imageBytes = cartItem.getImageBytes();

        // Create a separate Order object for each cart item
        Order order = new Order(userId, itemId, name, price, imageBytes, price, "preparing", orderId);

        // Create a unique document ID for each order item
        String orderItemId = generateOrderItemId(itemId); // Implement your own logic to generate a unique order item ID

        // Create a reference to the order item document using the order ID and order item ID
        DocumentReference orderItemRef = firestore.collection("orders").document();

        orderItemRef.set(order)
                .addOnSuccessListener(aVoid -> {
                    Log.d(TAG, "Order item added to Firestore successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Failed to add order item to Firestore: " + e.getMessage());
                });
    }

    // Update the order document with the total price
//    double totalPrice = calculateTotalPrice(); // Implement your own logic to calculate the total price
//    DocumentReference orderRef = firestore.collection("orders").document(orderId);
//    orderRef.update("totalPrice", totalPrice)
//            .addOnSuccessListener(aVoid -> {
//                Log.d(TAG, "Total price updated successfully");
//            })
//            .addOnFailureListener(e -> {
//                Log.e(TAG, "Failed to update total price: " + e.getMessage());
//            });
}

    private String generateOrderId(String userId) {
        // Generate a random number between 100 and 1000
        int randomNumber = new Random().nextInt(901) + 100;

        // Combine the user ID and random number to create the order ID
        String orderId = userId + "_" + randomNumber;

        return orderId;
    }


    private void clearCart() {
        // Replace `userId` with your actual user ID value
        String userId = mAuth.getCurrentUser().getUid();

        // Remove all items from the cart collection in Firestore
        firestore.collection("users").document(userId).collection("cart")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                    WriteBatch batch = firestore.batch();
                    for (DocumentSnapshot document : documents) {
                        batch.delete(document.getReference());
                    }
                    ((WriteBatch) batch).commit()
                            .addOnSuccessListener(aVoid -> {
                                Log.d(TAG, "Cart cleared successfully");
                            })
                            .addOnFailureListener(e -> {
                                Log.e(TAG, "Failed to clear cart: " + e.getMessage());
                            });
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error clearing cart: " + e.getMessage());
                });
    }

    private double getTotalAmount() {
        double total = 0.0;
        for (CartItem cartItem : cartItemList) {
            total += cartItem.getPrice();
        }
        return total;
    }

//    private String generateOrderId() {
//        // Generate your order ID logic here (e.g., based on timestamp, random number, etc.)
//        // For simplicity, let's use the current timestamp in this example
//        return String.valueOf(System.currentTimeMillis());
//    }

    private String generateOrderItemId(String itemId) {
        // Generate a unique order item ID using the itemId and a UUID
        return itemId + "_" + UUID.randomUUID().toString();
    }
    private double calculateTotalPrice() {
        double totalPrice = 0.0;

        // Iterate over each cart item and add up the prices
        for (CartItem cartItem : cartItemList) {
            totalPrice += cartItem.getPrice();
        }

        return totalPrice;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cartListenerRegistration != null) {
            cartListenerRegistration.remove();
        }
    }
}
