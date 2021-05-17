package com.example.shokke_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shokke_app.adapter.CartAdapter;
import com.example.shokke_app.model.Cart;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    private TextView message_cart,tv_total,price_cart;
    private Button btn_payment,btn_continue;
    private ListView lsv_cart;
    private CartAdapter cartAdapter;
    private ArrayList<Cart> carts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getInit();
        getData();
        setEvent();
    }
    public void getInit(){
        getSupportActionBar().setTitle("Giỏ hàng");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        message_cart = (TextView)findViewById(R.id.message_cart);
        tv_total=(TextView)findViewById(R.id.tv_total);
        price_cart=(TextView)findViewById(R.id.price_cart);
        btn_payment = (Button) findViewById(R.id.btn_payment);
        btn_continue = (Button) findViewById(R.id.btn_continue);
        lsv_cart = findViewById(R.id.lsv_cart);
    }
    public void setEvent(){
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });
    }
    public void getData(){
        carts = MainActivity.carts;
        if(carts.size() != 0){
            message_cart.setVisibility(View.INVISIBLE);
            cartAdapter = new CartAdapter(this,carts);
            lsv_cart.setAdapter(cartAdapter);
        }else {
            message_cart.setVisibility(View.VISIBLE);
            tv_total.setVisibility(View.INVISIBLE);
            price_cart.setVisibility(View.INVISIBLE);
            btn_payment.setVisibility(View.INVISIBLE);
            btn_continue.setVisibility(View.INVISIBLE);
        }
    }
}