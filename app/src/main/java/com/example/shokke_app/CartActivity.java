package com.example.shokke_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

    private TextView message_cart,tv_total;
    public static TextView price_cart;
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
        setPriceCart();
    }
    public void getInit(){

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Giỏ hàng");

        message_cart = (TextView)findViewById(R.id.message_cart);
        tv_total=(TextView)findViewById(R.id.tv_total);
        price_cart=(TextView)findViewById(R.id.price_cart);
        btn_payment = (Button) findViewById(R.id.btn_payment);
        lsv_cart = (ListView) findViewById(R.id.lsv_cart);
    }

    public void setEvent(){
        btn_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(Gravity.CENTER);
            }
        });
        lsv_cart.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.carts.size() > 1){
                            MainActivity.cartDatabase.Delete(MainActivity.carts.get(position).getId());
                            MainActivity.carts.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            setPriceCart();
                        }else{
                            MainActivity.cartDatabase.Delete(MainActivity.carts.get(position).getId());
                            MainActivity.carts.remove(position);
                            cartAdapter.notifyDataSetChanged();
                            getData();
                        }

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return false;
            }
        });
    }
    public void getData(){
        carts = MainActivity.carts;
        if(carts.size() != 0){
            message_cart.setVisibility(View.INVISIBLE);
            cartAdapter = new CartAdapter(CartActivity.this,carts);
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

    public static void setPriceCart(){
        float price = 0;
        for(int i=0;i<MainActivity.carts.size();i++){
            price += MainActivity.carts.get(i).getCount() * MainActivity.carts.get(i).getPrice();
        }
        NumberFormat formatter = new DecimalFormat("#,###");
        String formattedNumber = formatter.format(price);
        price_cart.setText( "₫ " + formattedNumber);
    }

    public void openDialog(int gravity){
        Dialog dialog = new Dialog(CartActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info);

        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);

        EditText edt_name = dialog.findViewById(R.id.edt_name);
        EditText edt_address = dialog.findViewById(R.id.edt_address);
        EditText edt_phone = dialog.findViewById(R.id.edt_phone);
        Button btn_payment_dialog = dialog.findViewById(R.id.btn_payment_dialog);

        btn_payment_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(edt_name.getText().toString().trim()) || TextUtils.isEmpty(edt_address.getText().toString().trim()) || TextUtils.isEmpty(edt_phone.getText().toString().trim())){
                    Toast.makeText(CartActivity.this, "Yêu cầu nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    String address = edt_address.getText().toString().trim();   // Địa chỉ
                    String phone = edt_phone.getText().toString().trim();       // Số điện thoại
                    Date currentTime = Calendar.getInstance().getTime();        //Thời gian hiện tại

                    Cursor dataCart = MainActivity.cartDatabase.GetData("SELECT * FROM Cart WHERE username = '" + MainActivity.userName + "'");
                    while (dataCart.moveToNext()) {
                        String userName = dataCart.getString(1);    //userName
                        String idProduct = dataCart.getString(2);   //id sản phẩm
                        int count = dataCart.getInt(3);             //Số lượng sản phẩm
                        ApiService.apiService.getPost(userName,address,phone,idProduct,count,currentTime.toString()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(CartActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
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
            }
        });
        dialog.show();
    }
}