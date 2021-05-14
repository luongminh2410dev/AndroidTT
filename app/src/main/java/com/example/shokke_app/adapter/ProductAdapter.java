package com.example.shokke_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.shokke_app.R;
import com.example.shokke_app.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
//
//    private ArrayList<Product> products;
//
//    public ProductAdapter(ArrayList<Product> products) {
//        this.products = products;
//    }
//
//    public void setProducts(ArrayList<Product> products) {
//        this.products = products;
//        notifyDataSetChanged();
//    }
//
//    @NonNull
//    @Override
//    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product,parent,false);
//        return new ProductViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ProductAdapter.ProductViewHolder holder, int position) {
//        Product product = products.get(position);
//        if(product==null){
//            return;
//        }
//        Picasso.get().load(product.getImg()).into(holder.img_product);
//        holder.tv_name.setText(product.getName());
//        holder.tv_price.setText(String.valueOf(product.getPrice()));
//    }
//
//    @Override
//    public int getItemCount() {
//        if(products!=null){
//            return products.size();
//        }
//        return 0;
//    }
//
//    public class ProductViewHolder extends RecyclerView.ViewHolder{
//        private ImageView img_product;
//        private TextView tv_name,tv_price;
//        public ProductViewHolder(@NonNull View itemView) {
//            super(itemView);
//
//            img_product = itemView.findViewById(R.id.img_product);
//            tv_name = itemView.findViewById(R.id.tv_name);
//            tv_price=itemView.findViewById(R.id.tv_price);
//        }
//    }
//}

public class ProductAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Product> products;

    public ProductAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    @Override
    public int getCount() {
        if(products != null)
            return products.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if(products != null)
            return products.get(position);
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_product,parent,false);

        TextView tv_name = convertView.findViewById(R.id.tv_name);
        tv_name.setText(products.get(position).getName());
        TextView tv_price = convertView.findViewById(R.id.tv_price);

        int price = (int)products.get(position).getPrice();
        tv_price.setText(String.valueOf(price) + " đồng");
        ImageView img_product = convertView.findViewById(R.id.img_product);
        Picasso.get().load(products.get(position).getImg()).into(img_product);
        return convertView;
    }

}
