package com.example.ecommerceapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapters.AdapterCartItem;
import com.example.ecommerceapp.adapters.AdapterProductUser;
import com.example.ecommerceapp.models.ModelCartProduct;
import com.example.ecommerceapp.models.ModelProduct;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CustomerCartFragment extends Fragment {
   
  View view;
  private RecyclerView carProductRv;
  private TextView totalCartPrice;
    FirebaseAuth firebaseAuth;
  private Button buybtn;
  private ArrayList<ModelCartProduct> productCartList=null;
  private AdapterCartItem adapterCartItem;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view =inflater.inflate(R.layout.fragment_customer_cart, container, false);

       totalCartPrice = view.findViewById(R.id.totalCartprice);
       carProductRv =view.findViewById(R.id.cartRv);
       buybtn = view.findViewById(R.id.buybtn);
        firebaseAuth = FirebaseAuth.getInstance();
        carProductRv.setLayoutManager(new LinearLayoutManager(getContext()));

        productCartList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("seller");
        FirebaseUser user = firebaseAuth.getCurrentUser();
        ref.child(user.getUid()).child("Cart")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clean list before

                        for(DataSnapshot ds: snapshot.getChildren()){
                            ModelCartProduct modelCartProduct = ds.getValue(ModelCartProduct.class);
                            productCartList.add(modelCartProduct);

                        }
                        adapterCartItem= new AdapterCartItem(getContext(), productCartList);
                        //set adapter

                        carProductRv.setHasFixedSize(true);
                        carProductRv.setAdapter(adapterCartItem);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
       




       return view;

    }


}