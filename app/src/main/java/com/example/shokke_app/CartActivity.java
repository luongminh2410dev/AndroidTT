package com.example.shokke_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shokke_app.adapter.CartAdapter;
import com.example.shokke_app.api.ApiService;
import com.example.shokke_app.model.Cart;
import com.example.shokke_app.model.Product;
import com.example.shokke_app.model.Value;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private TextView message_cart,tv_total,price_cart;
    private Button btn_payment;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Giỏ hàng");

        message_cart = (TextView)findViewById(R.id.message_cart);
        tv_total=(TextView)findViewById(R.id.tv_total);
        price_cart=(TextView)findViewById(R.id.price_cart);
        btn_payment = (Button) findViewById(R.id.btn_payment);
        lsv_cart = findViewById(R.id.lsv_cart);
    }
    public void setEvent(){
        if(carts.size() > 0){
            price_cart.setText(String.valueOf((int) getPriceCart(carts) + "đồng"));
        }
        lsv_cart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                price_cart.setText(String.valueOf((int) getPriceCart(carts) + "đồng"));
            }
        });
    }
    public void getData(){
        carts = MainActivity.carts;
        if(carts.size() != 0){
            message_cart.setVisibility(View.INVISIBLE);
            cartAdapter = new CartAdapter(this,carts);
            lsv_cart.setAdapter(cartAdapter);
            cartAdapter.notifyDataSetChanged();
        }else {
            message_cart.setVisibility(View.VISIBLE);
            tv_total.setVisibility(View.INVISIBLE);
            price_cart.setVisibility(View.INVISIBLE);
            btn_payment.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public float getPriceCart(ArrayList<Cart> carts){
        float price = 0;
        for(int i=0;i<carts.size();i++){
            price += carts.get(i).getPrice()*carts.get(i).getCount();
        }
        return price;
    }


}