package com.example.ecommerceapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.adapters.AdapterShop;
import com.example.ecommerceapp.models.ModelShop;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CustomerHomeFragment extends Fragment {
    View view;
   private RecyclerView shopRv;
    FirebaseAuth firebaseAuth;
    private ArrayList<ModelShop> shopList=null;
    private AdapterShop adapterShop;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_customer_home, container, false);

            shopRv = view.findViewById(R.id.shopsRv);
            firebaseAuth = FirebaseAuth.getInstance();
            shopRv.setLayoutManager(new LinearLayoutManager(getContext()));
            userInfo();
        }else{

        }


        return view;
        
    }

    private void userInfo() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
        reference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String city =""+ds.child("city").getValue();
                    //loads only those shops that are in uer city
                    loadshops(city);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void loadshops(String city) {
        shopList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("seller");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear list before adding
                shopList.clear();
                for(DataSnapshot ds: snapshot.getChildren()){
                    ModelShop modelShop = ds.getValue(ModelShop.class);

                    //get all shops in the city
                    String shopcity = ""+ds.child("city").getValue();
                   // if (shopcity.equals(city)) {

                  //      shopList.add(modelShop);
                  //  }
                    shopList.add(modelShop);
                    adapterShop = new AdapterShop(getActivity(),shopList);
                    //set adapter to recycler view
                    shopRv.setAdapter(adapterShop);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        if (view.getParent() != null) {
            ((ViewGroup)view.getParent()).removeView(view);
        }
        super.onDestroyView();
    }
}