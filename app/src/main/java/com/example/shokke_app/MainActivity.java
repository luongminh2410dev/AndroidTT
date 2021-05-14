package com.example.shokke_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;


import com.example.shokke_app.adapter.ProductAdapter;
import com.example.shokke_app.api.ApiService;
import com.example.shokke_app.model.Product;
import com.example.shokke_app.model.Value;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ProductAdapter productAdapter;
    private ArrayList<Product> products;
    private GridView grid_product;
    private EditText edt_search;
    private ImageView img_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getInit();
        CallApi();
        setEvent();
    }
    public void getInit(){
        getSupportActionBar().hide();
        edt_search = (EditText) this.findViewById(R.id.edt_search);
        img_search = (ImageView) this.findViewById(R.id.img_search);
        grid_product=findViewById(R.id.grid_product);
    }



    public void setEvent(){
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_search.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    callApiSearch(name);
                }
            }
        });
    }


    public void CallApi(){
        //https://servernodettandroid.herokuapp.com/product?id=5&name=Iphone&fbclid=IwAR0oCY_KuQKQ3gdYhNCItqQxnAISsJa5KS_pniNsCYUc8PHNruDuTz8yCLE
       ApiService.apiService.convertValue(5,"Iphone","IwAR0oCY_KuQKQ3gdYhNCItqQxnAISsJa5KS_pniNsCYUc8PHNruDuTz8yCLE").enqueue(new Callback<Value>() {
           @Override
           public void onResponse(Call<Value> call, Response<Value> response) {
               Value value = response.body();
               if(value != null){
                   products =  value.getProducts();
                   productAdapter = new ProductAdapter(MainActivity.this,products);
                   grid_product.setAdapter(productAdapter);
                   grid_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                       @Override
                       public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Product product = products.get(position);
                            Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                            intent.putExtra("PRODUCT", product);
                            startActivity(intent);
                       }
                   });
               }
           }
           @Override
           public void onFailure(Call<Value> call, Throwable t) {
               Toast.makeText(MainActivity.this, "Internet Error", Toast.LENGTH_SHORT).show();
           }
       });
    }

    public void callApiSearch(String name){
        ApiService.apiService.convertValueSearch(name).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                ArrayList<Product> productSearches = new ArrayList<>();
                Value value = response.body();
                if(value != null){
                    Intent intent = new Intent(MainActivity.this,SearchActivity.class);
                    intent.putExtra("VALUE",value);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}