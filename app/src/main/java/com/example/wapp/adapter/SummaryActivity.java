package com.example.wapp.adapter;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.wapp.OrdersActivity;
import com.example.wapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class SummaryActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private RecyclerView summaryRecyclerView;

    private SummaryAdapter summaryAdapter;
    private ArrayList<Summary> list ;
    private ListenerRegistration cartListenerRegistration;
    private TextView totalPriceTextView;
    private Button SummaryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance() ;
            list = new ArrayList<>();

        summaryRecyclerView = findViewById(R.id.Rvsummary);
        SummaryButton = findViewById(R.id.summaryBtn);
        summaryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        summaryAdapter = new SummaryAdapter(this,list);

        SummaryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), OrdersActivity.class);
                startActivity(i);
                setContentView(R.layout.activity_orders);
                finish();
            }
        });
    }
}