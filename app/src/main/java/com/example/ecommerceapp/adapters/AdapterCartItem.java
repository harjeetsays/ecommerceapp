package com.example.ecommerceapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.ModelCartProduct;
import com.example.ecommerceapp.models.ModelProduct;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AdapterCartItem extends RecyclerView.Adapter<AdapterCartItem.HolderProductCart>{
    Context context;
    public ArrayList<ModelCartProduct> cartProductsList;
    FirebaseAuth firebaseAuth;

    public AdapterCartItem(Context context,ArrayList<ModelCartProduct> cartProductsList){
        this.context = context;
        this.cartProductsList = cartProductsList;
    }

    @NonNull
    @Override
    public HolderProductCart onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the card layout
        View view = LayoutInflater.from(context).inflate(R.layout.row_cart_product, parent, false);
        return new HolderProductCart(view);
    }

    private double cost = 0;
    private double finalcost = 0;
    private int quantity = 0;
    @Override
    public void onBindViewHolder(@NonNull HolderProductCart holder, int position) {
        //getting the data
        ModelCartProduct modelCartProduct =cartProductsList.get(position);
        String productID = modelCartProduct.getPId();
        String sellerID = modelCartProduct.getsID();
        String userID = modelCartProduct.getuID();
        String productTitle = modelCartProduct.getTitle();
        String price = modelCartProduct.getPrice();
        String intialquantity = modelCartProduct.getQuantity();

        //setting data
        holder.itemTitle.setText(productTitle);
        holder.quantityChangable.setText(intialquantity);
        holder.itemFinalPrice.setText("₹"+price);

        cost = Double.parseDouble(price.replaceAll("₹", ""));
        finalcost = Double.parseDouble(price.replaceAll("₹", ""));
        quantity =1;

        holder.incrementbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalcost= finalcost+cost;
                quantity++;

                holder.itemFinalPrice.setText("₹"+finalcost);
                holder.quantityChangable.setText(""+quantity);
            }
        });

        //decrease quantity of product
        holder.decrementbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity>1){
                    finalcost= finalcost - cost;
                    quantity--;

                    holder.itemFinalPrice.setText("₹"+finalcost);
                    holder.quantityChangable.setText(""+quantity);
                }
            }
        });

        holder.removeItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                ref.child(userID).child("Cart").child(productID).removeValue();

            }
        });


    }

    @Override
    public int getItemCount() {
        return cartProductsList.size();
    }

    class HolderProductCart extends RecyclerView.ViewHolder{
        private TextView itemTitle,quantityChangable,removeItem,itemFinalPrice;
        private ImageButton incrementbtn,decrementbtn;

        public HolderProductCart(@NonNull View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.itemtitle);
            quantityChangable = itemView.findViewById(R.id.quantity_change);
            removeItem = itemView.findViewById(R.id.removeitem);
            itemFinalPrice = itemView.findViewById(R.id.itemfinalprice);
            incrementbtn = itemView.findViewById(R.id.incrementalitembtn);
            decrementbtn = itemView.findViewById(R.id.decrementalitembtn);
        }
    }
}
