package com.example.wapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wapp.adapter.SummaryActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Calendar;

public class OrdersActivity extends AppCompatActivity implements PaymentResultListener {
    Button cartbutton;
Button cart_btn;
TextView ordernotv;
    Button paybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Checkout.preload(getApplicationContext());



        setContentView(R.layout.activity_orders);
        cart_btn = findViewById(R.id.cart_btn);
       // ordernotv = findViewById(R.id.ordernotv);

       // paybtn = findViewById(R.id.paybutton);
        cart_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),summaryActivity.class);
                startActivity(intent);
            }
        });
//        cartbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(getApplicationContext(), SummaryActivity.class);
//                startActivity(i);
//                setContentView(R.layout.activity_summary);
//            }
//        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.orders);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottom_home:
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    finish();
                    return true;
                case R.id.cart:
                    startActivity(new Intent(getApplicationContext(), CartActivity.class));
                    finish();
                    return true;
                case R.id.orders:
                    return true;
                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                    finish();
                    return true;

            }
            return false;
        });

        Calendar calender = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calender.getTime());

        TextView textView = findViewById(R.id.tv_date);
        textView.setText(currentDate);
//        paybtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                makepayment();
//            }
//        });



    }

    private void makepayment() {

        Checkout checkout = new Checkout();
        checkout.setKeyID("ZGSlvleWk2TCJ9JWMfYWmVju");

        checkout.setImage(R.drawable.pizzo1);


        final Activity activity = this;


        try {
            JSONObject options = new JSONObject();

            options.put("name", "Pizzo");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","6263965894");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Successfull Payment"+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {

        Toast.makeText(this, "Failed Payment"+s, Toast.LENGTH_SHORT).show();
    }
}