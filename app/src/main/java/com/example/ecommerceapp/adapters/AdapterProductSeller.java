package com.example.ecommerceapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.FilterProductseller;
import com.example.ecommerceapp.models.ModelProduct;
import com.example.ecommerceapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterProductSeller extends RecyclerView.Adapter<AdapterProductSeller.HolderProductSeller> implements Filterable {
    private Context context;
    public ArrayList<ModelProduct> productList,filterList;
    private FilterProductseller filter;

    public AdapterProductSeller(Context context, ArrayList<ModelProduct> productList) {
        this.context = context;
        this.productList = productList;
        //this.filterList = filterList;
    }


    @NonNull
    @Override
    public HolderProductSeller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.rwo_product_seller,parent,false);
        return new HolderProductSeller(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductSeller holder, int position) {
        //get data
        ModelProduct modelProduct = productList.get(position);
        String id=modelProduct.getPId();
        String title=modelProduct.getTitle();
        String quantity = modelProduct.getQuantity();
        String price = modelProduct.getPrice();
        String description = modelProduct.getDescription();
        String category = modelProduct.getCategory();
        String uid= modelProduct.getUid();
        String img= modelProduct.getImg();
        String timestamp = modelProduct.getTimeStamp();

        //set data
        holder.titleVar.setText(title);
        holder.quantityVar.setText(quantity);
        holder.priceVar.setText("₹"+price);
        holder.titleVar.setText(title);
        holder.titleVar.setText(title);
        holder.titleVar.setText(title);

        try{
            Picasso.get().load(img).placeholder(R.drawable.ic_default_img).into(holder.productImgVar);

        }catch (Exception e){
            holder.productImgVar.setImageResource(R.drawable.ic_default_img);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handle item clicks,show item details

            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterProductseller(filterList, this);
        }
        return filter;
    }

    class HolderProductSeller extends RecyclerView.ViewHolder{
        private ImageView productImgVar;
        private TextView titleVar,quantityVar,priceVar;

        public HolderProductSeller(@NonNull View itemView) {
            super(itemView);

            productImgVar = itemView.findViewById(R.id.product_img);
            priceVar = itemView.findViewById(R.id.price_pr);
            quantityVar = itemView.findViewById(R.id.quantity_pr);
            titleVar = itemView.findViewById(R.id.title_pr);
        }
    }
}
