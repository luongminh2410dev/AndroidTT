package com.example.shokke_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shokke_app.CartActivity;
import com.example.shokke_app.MainActivity;
import com.example.shokke_app.R;
import com.example.shokke_app.api.ApiService;
import com.example.shokke_app.model.Cart;
import com.example.shokke_app.model.Product;
import com.example.shokke_app.model.Value;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Cart> carts;
    private float priceCart = 0;

    public CartAdapter(Context context, ArrayList<Cart> carts, float priceCart) {
        this.context = context;
        this.carts = carts;
        this.priceCart = priceCart;
    }

//    public CartAdapter(Context context, ArrayList<Cart> carts) {
//        this.context = context;
//        this.carts = carts;
//    }

    public float getPriceCart() {
        return priceCart;
    }

    public void setPriceCart(float priceCart) {
        this.priceCart = priceCart;
    }

    @Override
    public int getCount() {
        if(carts!=null)
            return carts.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(carts != null)
            return carts.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView =inflater.inflate(R.layout.item_cart,parent,false);


        ImageView img_product_cart = convertView.findViewById(R.id.img_product_cart);
        TextView name_cart = convertView.findViewById(R.id.name_product_cart);
        TextView price_cart = convertView.findViewById(R.id.price_product_cart);
        TextView tv_value = convertView.findViewById(R.id.tv_value);
        Button btn_minus = convertView.findViewById(R.id.btn_minus);
        Button btn_plus = convertView.findViewById(R.id.btn_plus);

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(tv_value.getText().toString()) ;
                if(value > 1){
                    value --;
                }
                tv_value.setText(String.valueOf(value));
                MainActivity.cartDatabase.Update(value,carts.get(position).getId());
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = Integer.parseInt(tv_value.getText().toString()) ;
                value ++;
                tv_value.setText(String.valueOf(value));
                MainActivity.cartDatabase.Update(value,carts.get(position).getId());
            }
        });
        tv_value.setText(String.valueOf(carts.get(position).getCount()));
        ApiService.apiService.convertValueById(carts.get(position).getIdProduct()).enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                Value value = response.body();
                Product product = value.getProducts().get(0);

                Picasso.get().load(product.getImg()).into(img_product_cart);
                name_cart.setText(product.getName());
                price_cart.setText(String.valueOf(product.getPrice())+" đồng");

                priceCart += carts.get(position).getCount() * product.getPrice();
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {

            }
        });

        return convertView;
    }
}
