package com.example.wapp.adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<Item> itemList;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    private Context context;
    public ItemAdapter(List<Item> itemList, FirebaseFirestore firestore, FirebaseAuth mAuth, Context context) {
        this.itemList = itemList;
        this.firestore = firestore;
        this.mAuth = mAuth;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.itemNameTextView.setText(item.getName());
        holder.itemPriceTextView.setText(String.valueOf(item.getPrice()));

        // Set the item image from the byte array
        if (item.getImageBytes() != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(item.getImageBytes(), 0, item.getImageBytes().length);
            holder.itemImageView.setImageBitmap(bitmap);
        }
        holder.addButton.setOnClickListener(v -> {
            // Handle the click event for the "Add" button
            addItemToCart(item);
        });
    }

    private void addItemToCart(Item item) {
        String uid = mAuth.getCurrentUser().getUid();

        DocumentReference cartRef = firestore.collection("users").document(uid).collection("cart").document();
        Map<String, Object> cartItem = new HashMap<>();
        cartItem.put("name", item.getName());
        cartItem.put("price", item.getPrice());

        String imageString = Base64.encodeToString(item.getImageBytes(), Base64.DEFAULT);
        cartItem.put("image", imageString);

        cartRef.set(cartItem)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(context, "Failed to add item to cart", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error adding item to cart: ", e);
                });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImageView;
        TextView itemNameTextView;
        TextView itemPriceTextView;

        Button addButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
            itemNameTextView = itemView.findViewById(R.id.itemNameTextView);
            itemPriceTextView = itemView.findViewById(R.id.itemPriceTextView);
            addButton = itemView.findViewById(R.id.addButton);
        }
    }
}
