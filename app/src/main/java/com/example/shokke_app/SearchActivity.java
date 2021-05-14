package com.example.shokke_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.shokke_app.adapter.ProductAdapter;
import com.example.shokke_app.model.Product;
import com.example.shokke_app.model.Value;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private TextView tv_message;
    private GridView grid_product_search;
    private ProductAdapter productAdapter;
    private ArrayList<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        getInit();
        getData();
    }

    public void getInit() {
        getSupportActionBar().setTitle("Kết quả tìm kiếm");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tv_message=(TextView) this.findViewById(R.id.tv_message);
        grid_product_search = findViewById(R.id.grid_product_search);
        products = new ArrayList<>();
    }

    public void getData() {
        Intent intent = getIntent();
        Value value = (Value) intent.getSerializableExtra("VALUE");

        if(value.isSuccess() == true){
            products = value.getProducts();
            productAdapter = new ProductAdapter(SearchActivity.this, products);
            grid_product_search.setAdapter(productAdapter);
            grid_product_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Product product = products.get(position);
                    Intent intent = new Intent(SearchActivity.this,DetailActivity.class);
                    intent.putExtra("PRODUCT", product);
                    startActivity(intent);
                }
            });
        }else{
            tv_message.setText("Không tìm thấy sản phẩm");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}