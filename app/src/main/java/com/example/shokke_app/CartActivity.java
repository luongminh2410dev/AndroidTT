package com.example.shokke_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
            NumberFormat formatter = new DecimalFormat("#,###");
            String formattedNumber = formatter.format(getPriceCart(carts));
            price_cart.setText( "₫ " + formattedNumber);
//            price_cart.setText(String.valueOf((int) getPriceCart(carts) + "đồng"));
        }

        lsv_cart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NumberFormat formatter = new DecimalFormat("#,###");
                String formattedNumber = formatter.format(getPriceCart(carts));
                price_cart.setText( "₫ " + formattedNumber);
//                price_cart.setText(String.valueOf((int) getPriceCart(carts) + "đồng"));
            }
        });
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date currentTime = Calendar.getInstance().getTime();
                Cursor dataCart = MainActivity.cartDatabase.GetData("SELECT * FROM Cart WHERE username = '" + MainActivity.userName + "'");
                while (dataCart.moveToNext()) {
                    String userName = dataCart.getString(1);
                    String idProduct = dataCart.getString(2);
                    int count = dataCart.getInt(3);
                    ApiService.apiService.getPost(userName,idProduct,count,currentTime.toString()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            Toast.makeText(CartActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                            MainActivity.cartDatabase.QueryData("DELETE FROM Cart");
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(CartActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
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