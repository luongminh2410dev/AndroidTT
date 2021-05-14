package com.example.shokke_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shokke_app.model.Product;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView img_detail;
    private TextView name_detail,price_detail,description_detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Init();
        getData();
    }

    public void Init(){
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        img_detail = findViewById(R.id.img_detail);
        name_detail = findViewById(R.id.name_detail);
        price_detail=findViewById(R.id.price_detail);
        description_detail=findViewById(R.id.description_detail);
    }

    public void getData(){
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("PRODUCT");

        Picasso.get().load(product.getImg()).into(img_detail);
        name_detail.setText(product.getName());

        int price = (int) product.getPrice();
        price_detail.setText(String.valueOf(price) + " đồng");
        description_detail.setText(String.valueOf(product.getDescription()));
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