package com.example.ecommerceapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.ShopDetailsActivity;
import com.example.ecommerceapp.models.ModelShop;

import java.util.ArrayList;

public class AdapterShop extends RecyclerView.Adapter<AdapterShop.HolderShop> {
    private Context context;
    public ArrayList<ModelShop> shoplist;

    public AdapterShop(Context context, ArrayList<ModelShop> shoplist) {
        this.context = context;
        this.shoplist = shoplist;
    }

    @NonNull
    @Override
    public HolderShop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate row_shop
        View view = LayoutInflater.from(context).inflate(R.layout.row_shop, parent, false);
        return new HolderShop(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderShop holder, int position) {
        //get data
        ModelShop modelShop = shoplist.get(position);
        String shopName = modelShop.getShopname();
        String city = modelShop.getCity();
        String pswrd = modelShop.getPaswrd();
        String phoneNumber = modelShop.getPhonenumber();
        String address = modelShop.getAddress();
        String email = modelShop.getEmail();
        String latitude = modelShop.getLatitude();
        String longitude = modelShop.getLongitude();
        String uID = modelShop.getUid();

        //set data
        holder.shopname.setText(shopName);
        holder.phonenumber.setText(phoneNumber);
        holder.address.setText(address);

        //handle click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ShopDetailsActivity.class);
                intent.putExtra("shopUid",uID);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoplist.size();//return no of shops
    }

    class HolderShop extends RecyclerView.ViewHolder {
        private TextView shopname, phonenumber, address;

        public HolderShop(@NonNull View itemView) {
            super(itemView);
            //init ui views
            shopname = itemView.findViewById(R.id.shopname_shopui);
            phonenumber = itemView.findViewById(R.id.phonenmbr_shopui);
            address = itemView.findViewById(R.id.selleraddress_shopui);

        }
    }
}
