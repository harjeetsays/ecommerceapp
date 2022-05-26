package com.example.ecommerceapp.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ecommerceapp.adapters.AdapterProductSeller;
import com.example.ecommerceapp.Constants;
import com.example.ecommerceapp.models.ModelProduct;
import com.example.ecommerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SellerHomePageFragment extends Fragment {

    private EditText searchProduct;
    private ImageButton filterProductBtn;
    private TextView filterProductTv;
    private RecyclerView productRv;
    private ArrayList<ModelProduct> productList=null;
    private AdapterProductSeller adapterProductSeller;

    FirebaseAuth firebaseAuth;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View root= inflater.inflate(R.layout.fragment_seller_home_page, container, false);
        searchProduct = root.findViewById(R.id.searchProduct);
        filterProductBtn = root.findViewById(R.id.filterProductBtn);
        filterProductTv = root.findViewById(R.id.filterProductTv);
        productRv = root.findViewById(R.id.productRv);
        firebaseAuth = FirebaseAuth.getInstance();

       productRv.setLayoutManager(new LinearLayoutManager(getContext()));

        //search product
        searchProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterProductSeller.getFilter().filter(s);

                }catch (Exception e){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

         loadAllProducts();
         filterProductBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                 builder.setTitle("Choose Category").setItems(Constants.productCategories1, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         //get selected items
                         String selected = Constants.productCategories1[which];
                         filterProductTv.setText(selected);
                         if(selected.equals("All")){
                             //loads all products
                             loadAllProducts();
                         }else{
                             loadSelectedProducts(selected);
                         }

                     }
                 }).show();
             }
         });



        return root;
    }

    private void loadSelectedProducts(String selected) {
        productList = new ArrayList<>();

        //get all products
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("seller");
        reference.child(user.getUid()).child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset lis
                        productList.clear();
                        for(DataSnapshot ds:snapshot.getChildren()){
                            String productCategory = ""+ds.child("Category").getValue();

                            //if selected category matches product category
                            if(selected.equals(productCategory)){
                                ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                                productList.add(modelProduct);
                            }


                        }
                        //setup adapter
                        adapterProductSeller = new AdapterProductSeller(getActivity(),productList);
                        //set adapter
                        productRv.setAdapter(adapterProductSeller);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

    private void loadAllProducts() {


        productList = new ArrayList<>();

        //get all products
        FirebaseUser user = firebaseAuth.getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("seller");
        reference.child(user.getUid()).child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //before getting reset lis
                        productList.clear();
                        for(DataSnapshot ds:snapshot.getChildren()){
                            ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                            productList.add(modelProduct);

                        }
                        //setup adapter
                        adapterProductSeller = new AdapterProductSeller(getActivity(),productList);
                        //set adapter
                        productRv.setAdapter(adapterProductSeller);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}