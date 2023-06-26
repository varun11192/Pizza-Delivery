package com.example.wapp;

import android.graphics.Bitmap;
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
public class SummaryItemAdapter extends RecyclerView.Adapter<SummaryItemAdapter.ViewHolder> {

    private List<SummaryItem> summaryItemList;

    public SummaryItemAdapter(List<SummaryItem> summaryItemList) {
        this.summaryItemList = summaryItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.summary_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SummaryItem summaryItem = summaryItemList.get(position);

        holder.item_name.setText(summaryItem.getName());
        holder.item_price.setText(String.valueOf(summaryItem.getPrice()));
        holder.item_status.setText(summaryItem.getStatus());

        // Set the image byte array to the ImageView
        byte[] imageBytes = summaryItem.getImageBytes();

        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        holder.item_image.setImageBitmap(bitmap);
    }

    @Override
    public int getItemCount() {
        return summaryItemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_name;
        public TextView item_price;
        public TextView item_status;
        public ImageView item_image;

        public ViewHolder(View view) {
            super(view);
            item_name = view.findViewById(R.id.item_name);
            item_price = view.findViewById(R.id.item_price);
            item_status = view.findViewById(R.id.item_status);
            item_image = view.findViewById(R.id.item_image);
        }
    }
}
