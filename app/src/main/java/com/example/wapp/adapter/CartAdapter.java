package com.example.wapp.adapter;

import static android.content.Intent.getIntent;
import static android.content.Intent.makeRestartActivityTask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wapp.CartActivity;
import com.example.wapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<CartItem> cartItemList;
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();
    String userId = mAuth.getCurrentUser().getUid();
    private FirebaseFirestore firestore;

    private CartActivity cartActivity;

    public CartAdapter(List<CartItem> cartItemList, CartActivity cartActivity) {
        this.cartItemList = cartItemList;
        this.cartActivity = cartActivity;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item_row_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem cartItem = cartItemList.get(position);

        holder.itemNameTextView.setText(cartItem.getName());
        holder.itemPriceTextView.setText(String.valueOf(cartItem.getPrice()));

        // Set the item image
        if (cartItem.getImageBytes() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(cartItem.getImageBytes(), 0, cartItem.getImageBytes().length);
            holder.itemImageView.setImageBitmap(bitmap);
        }

        // Handle remove button click
        holder.removeButton.setOnClickListener(v -> {
            // Call the removeItem method in the CartActivity
            String itemId = cartItem.getItemId();
            firestore = FirebaseFirestore.getInstance();
            // Remove the item from the Firestore database
            firestore.collection("users").document(userId)
                    .collection("cart").document(itemId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        // Item removed successfully, update the UI if needed
                        Toast.makeText(cartActivity.getApplicationContext(), "Item removed", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle the error if item removal fails
                        Toast.makeText(cartActivity.getApplicationContext(), "Failed to remove item", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView itemNameTextView;
        TextView itemPriceTextView;
        Button removeButton;

        public CartViewHolder(View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}


