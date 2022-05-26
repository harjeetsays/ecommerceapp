package com.example.ecommerceapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.FilterProductsUser;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.models.ModelProduct;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import p32929.androideasysql_library.Column;
import p32929.androideasysql_library.EasyDB;

public class AdapterProductUser extends RecyclerView.Adapter<AdapterProductUser.HolderProductUser> implements Filterable {
     Context context;
    public ArrayList<ModelProduct> productArrayList, filterList;
    private FilterProductsUser filter;
    FirebaseAuth firebaseAuth;

    public AdapterProductUser(Context context, ArrayList<ModelProduct> productArrayList) {
        this.context = context;
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public HolderProductUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout
        View view = LayoutInflater.from(context).inflate(R.layout.rwo_product_user, parent, false);
        return new HolderProductUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProductUser holder, int position) {
        //get data
        ModelProduct modelProduct = productArrayList.get(position);
        String category = modelProduct.getCategory();
        String description = modelProduct.getDescription();
        String priceP = modelProduct.getPrice();
        String titleP = modelProduct.getTitle();
        String img = modelProduct.getImg();
        String timestamp = modelProduct.getTimeStamp();
        String pID = modelProduct.getPId();
        String quantity = modelProduct.getQuantity();
        String sID = modelProduct.getUid();

        //set data
        holder.title.setText(titleP);
        holder.desc.setText(description);
        holder.price.setText("₹" + priceP);

        try {
            Picasso.get().load(img).placeholder(R.drawable.ic_default_img).into(holder.productimg);

        } catch (Exception e) {
            holder.productimg.setImageResource(R.drawable.ic_default_img);
        }
        holder.addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add product to cart
                String timestamp = ""+System.currentTimeMillis();
                FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
                HashMap<String, Object> hashMap =new HashMap<>();
                hashMap.put("PId",""+pID);
                hashMap.put("Title",""+titleP);
                hashMap.put("Category",""+category);
                hashMap.put("Quantity","1");
                hashMap.put("Price",""+priceP);
                hashMap.put("Img",""+img);//no image
                hashMap.put("TimeStamp",""+timestamp);
                hashMap.put("sID",""+sID);
                hashMap.put("uID",""+user.getUid());
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
                ref.child(user.getUid()).child("Cart").child(pID).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context,"Product Added to cart",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show product details
            }
        });

    }


    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new FilterProductsUser(filterList, this);

        }
        return filter;
    }

    class HolderProductUser extends RecyclerView.ViewHolder {
        private ImageView productimg;
        private TextView title, desc, price, addtocart;

        public HolderProductUser(@NonNull View itemView) {
            super(itemView);

            productimg = itemView.findViewById(R.id.product_img_user);
            title = itemView.findViewById(R.id.title_pr_user);
            desc = itemView.findViewById(R.id.description_pr);
            price = itemView.findViewById(R.id.price_pr_user);
            addtocart = itemView.findViewById(R.id.addtocartbtn);


        }
    }
}
