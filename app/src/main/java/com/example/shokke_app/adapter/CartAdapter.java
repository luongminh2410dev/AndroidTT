package com.example.shokke_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shokke_app.R;
import com.example.shokke_app.model.Cart;

import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Cart> carts;
//    private ArrayList<DetailProduct> products;

    public CartAdapter(Context context, ArrayList<Cart> carts) {
        this.context = context;
        this.carts = carts;
    }

//    public CartAdapter(Context context, ArrayList<DetailProduct> products) {
//        this.context = context;
//        this.products = products;
//    }

    @Override
    public int getCount() {
        if(carts!=null)
            return carts.size();
//        if(products != null)
//            return products.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(carts != null)
            return carts.get(position);
//        if(products != null)
//            return products.get(position);
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
        Button btn_value = convertView.findViewById(R.id.btn_value);

        btn_value.setText("1");
//        btn_value.setText(String.valueOf(products.get(position).getCount()));
//        Picasso.get().load(products.get(position).getProduct().getImg()).into(img_product_cart);
//        name_cart.setText(products.get(position).getProduct().getName());
//        price_cart.setText(String.valueOf(products.get(position).getProduct().getPrice())+" đồng");
        return convertView;
    }
}
