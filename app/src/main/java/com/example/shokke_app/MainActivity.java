package com.example.shokke_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;


import com.example.shokke_app.adapter.ProductAdapter;
import com.example.shokke_app.api.ApiService;
import com.example.shokke_app.database.CartDatabase;
import com.example.shokke_app.model.Cart;
import com.example.shokke_app.model.DetailCart;
import com.example.shokke_app.model.Product;
import com.example.shokke_app.model.Value;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private ProductAdapter productAdapter;
    private ArrayList<Product> products;

    private GridView grid_product;
    private EditText edt_search;
    private ImageView img_search, img_cart;
    private Spinner spinner;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;

    public static CartDatabase cartDatabase;    //database
    public static String userName;  //Tên tài khoản

    public static ArrayList<Cart> carts; //danh sách giỏ hàng


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getData();
        getInit();
        CallApi();
        setEvent();
    }


    public void getInit() {
        getSupportActionBar().hide();
        edt_search = (EditText) this.findViewById(R.id.edt_search);
        img_search = (ImageView) this.findViewById(R.id.img_search);
        img_cart = (ImageView) this.findViewById(R.id.img_cart);
        grid_product = findViewById(R.id.grid_product);
        spinner = findViewById(R.id.spn_product);
        list = new ArrayList<>();
        list.add("Tất cả sản phẩm");
        list.add("Laptop");
        list.add("Điện thoại");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        carts = new ArrayList<>();


        //Khởi tạo database
        cartDatabase = new CartDatabase(this, "Cart.sqlite", null, 1);
        //Tạo table
        cartDatabase.QueryData("CREATE TABLE IF NOT EXISTS Cart(id INTEGER PRIMARY KEY AUTOINCREMENT,username VARCHAR(200),idProduct VARCHAR(200),count INTEGER,price INTEGER ,timeCreate VARCHAR(200))");
//        cartDatabase.QueryData("DELETE FROM Cart");
    }

    public void setEvent() {
        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_search.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    callApiSearch(name);
                }
            }
        });
        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Lấy ra dữ liệu khi biết userName
                Cursor dataCart = cartDatabase.GetData("SELECT * FROM Cart WHERE username = '" + userName + "'");
                carts.clear();
                while (dataCart.moveToNext()) {
                    int id = dataCart.getInt(0);
                    String userName = dataCart.getString(1);
                    String idProduct = dataCart.getString(2);
                    int count = dataCart.getInt(3);
                    int price = dataCart.getInt(4);
                    String timeCreate = dataCart.getString(5);
                    carts.add(new Cart(id, userName, idProduct, count,price, timeCreate));
                }
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterProduct(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void filterProduct(int position){
        if(position == 0)
        {
            CallApi();
        }
        if(position <= 2){
            ApiService.apiService.convertValueBySortout(position).enqueue(new Callback<Value>() {
                @Override
                public void onResponse(Call<Value> call, Response<Value> response) {
                    Value value = response.body();
                    if(value.isSuccess()){
                        products = value.getProducts();
                        productAdapter = new ProductAdapter(MainActivity.this, products);
                        grid_product.setAdapter(productAdapter);
                        grid_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Product product = products.get(position);
                                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                                intent.putExtra("PRODUCT", product);
                                intent.putExtra("USERNAME",userName);
                                startActivity(intent);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<Value> call, Throwable t) {
                }
            });
        }

    }
    public void getData() {
        Intent intent = getIntent();
        userName = intent.getStringExtra("USERNAME");
    }

    public void CallApi() {
        //https://servernodettandroid.herokuapp.com/product?id=5&name=Iphone&fbclid=IwAR0oCY_KuQKQ3gdYhNCItqQxnAISsJa5KS_pniNsCYUc8PHNruDuTz8yCLE
        ApiService.apiService.convertValue(5, "Iphone", "IwAR0oCY_KuQKQ3gdYhNCItqQxnAISsJa5KS_pniNsCYUc8PHNruDuTz8yCLE").enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                Value value = response.body();
                if (value != null) {
                    products = value.getProducts();
                    productAdapter = new ProductAdapter(MainActivity.this, products);
                    grid_product.setAdapter(productAdapter);
                    grid_product.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Product product = products.get(position);
                            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                            intent.putExtra("PRODUCT", product);
                            intent.putExtra("USERNAME",userName);
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

    public void callApiSearch(String name) {
        ApiService.apiService.convertValueSearch(name).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                ArrayList<Product> productSearches = new ArrayList<>();
                Value value = response.body();
                if (value != null) {
                    Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                    intent.putExtra("VALUE", value);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Internet Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    public void getProductCartList(ArrayList<Cart> carts){
//        if(carts != null){
//            for(int i=0;i<carts.size();i++){
//                countCurrent = carts.get(i).getCount();
//                ApiService.apiService.convertValueById(carts.get(i).getIdProduct()).enqueue(new Callback<Value>() {
//                    @Override
//                    public void onResponse(Call<Value> call, Response<Value> response) {
//                        Value value = response.body();
//                        if(value != null){
//                           Product product = value.getProducts().get(0);
//                           productCarts.add(new DetailProduct(product,countCurrent));
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Value> call, Throwable t) {
//
//                    }
//                });
//            }
//        }
//    }


}