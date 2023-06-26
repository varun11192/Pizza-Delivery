package com.example.wapp;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wapp.adapter.Item;

import java.util.List;
import java.util.Locale;

public class SummaryItemAdapter extends RecyclerView.Adapter<SummaryItemAdapter.SummaryItemViewHolder> {
    private List<Item> itemList;

    public SummaryItemAdapter(List<Item> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public SummaryItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_item_row, parent, false);
        return new SummaryItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryItemViewHolder holder, int position) {
        Item item = itemList.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class SummaryItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImageView;
        private TextView nameTextView;
        private TextView priceTextView;
        private TextView statusTextView;

        public SummaryItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.item_image);
            nameTextView = itemView.findViewById(R.id.item_name);
            priceTextView = itemView.findViewById(R.id.item_price);
            statusTextView = itemView.findViewById(R.id.item_status);
        }

        public void bind(Item item) {
            // Set the item data to the views
            itemImageView.setImageBitmap(BitmapFactory.decodeByteArray(item.getImageBytes(), 0, item.getImageBytes().length));
            nameTextView.setText(item.getName());
            priceTextView.setText(String.format(Locale.getDefault(), "Price: $%.2f", item.getPrice()));
            statusTextView.setText("Status: " + item.getStatus());
        }
    }
}

