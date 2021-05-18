package com.example.shokke_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shokke_app.model.Cart;
import com.example.shokke_app.model.Product;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private ImageView img_detail;
    private ImageButton btn_add_to_cart;
    private TextView name_detail,price_detail,description_detail;
    private String idProduct;
    private String userName;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Init();
        getData();
        setEvent();
    }

    public void Init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        img_detail = findViewById(R.id.img_detail);
        name_detail = findViewById(R.id.name_detail);
        price_detail=findViewById(R.id.price_detail);
        description_detail=findViewById(R.id.description_detail);
        btn_add_to_cart=(ImageButton)findViewById(R.id.btn_add_to_cart);
    }

    public void getData(){
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("PRODUCT");

        Picasso.get().load(product.getImg()).into(img_detail);
        name_detail.setText(product.getName());
        price = (int) product.getPrice();
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(product.getPrice());
        price_detail.setText( "₫ " + formattedNumber);
        description_detail.setText(String.valueOf(product.getDescription()));
        idProduct = product.get_id();
        userName = intent.getStringExtra("USERNAME");
    }
    public void setEvent(){
        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Tìm bản ghi trong cơ sở dữ liệu khi biết database
                Cursor dataCartCheck = MainActivity.cartDatabase.GetData("SELECT * FROM Cart WHERE username == '"+userName+"' AND idProduct == '"+idProduct+"'");
                if(dataCartCheck.getCount() > 0 ){
                    Toast.makeText(DetailActivity.this, "Sản phẩm này đã có trong giỏ hàng", Toast.LENGTH_SHORT).show();
                }else{
                    MainActivity.cartDatabase.Insert(userName,idProduct,1,price,null);
                    Cursor dataCart = MainActivity.cartDatabase.GetData("SELECT * FROM Cart WHERE username == '"+userName+"' AND idProduct == '"+idProduct+"'");
                    while (dataCart.moveToNext()){
                        int id = dataCart.getInt(0);
                        String userName = dataCart.getString(1);
                        String idProduct = dataCart.getString(2);
                        int count = dataCart.getInt(3);
                        int price = dataCart.getInt(4);
                        String timeCreate = dataCart.getString(5);
                        MainActivity.carts.add(new Cart(id,userName,idProduct,count,price,timeCreate));
                    }
                }
                startActivity(new Intent(DetailActivity.this,CartActivity.class));
            }
        });
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

}