package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ecommerceapp.Fragments.CustomerHomeFragment;
import com.example.ecommerceapp.adapters.AdapterProductUser;
import com.example.ecommerceapp.models.ModelProduct;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShopDetailsActivity extends AppCompatActivity {
private TextView shopName,phoneNumber,email,address,filteredproducts;
private ImageButton filterbtn,backbtn,cartbtn;
private EditText search;
private RecyclerView productsRv;
private String shopUid,shopname, shopphone,shopemail,shopaddress;

private FirebaseAuth firebaseAuth;

private ArrayList<ModelProduct> productArrayList;
private AdapterProductUser adapterProductUser;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        shopName = findViewById(R.id.shopname_shopdetails);
        phoneNumber = findViewById(R.id.phonenumber_shopdetails);
        email = findViewById(R.id.email_shopdetails);
        address = findViewById(R.id.address_shopdetails);
        backbtn = findViewById(R.id.backbtn);
        filterbtn = findViewById(R.id.filterbtnsd);
        filteredproducts = findViewById(R.id.filteredproducttvsd);
        search = findViewById(R.id.searchproductsd);
        cartbtn = findViewById(R.id.cartbtnsd);
        productsRv = findViewById(R.id.productssdRv);
        //get uid of shop from intent
        shopUid = getIntent().getStringExtra("shopUid");
        firebaseAuth = FirebaseAuth.getInstance();
        loadMyInfo();
        loadShopDetails();
        loadShopProducts();

       search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    adapterProductUser.getFilter().filter(s);

                }catch (Exception e){

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go to customer home

                Intent intent = new Intent( getApplicationContext(),CustomerDashboard.class);
                startActivity(intent);
            }
        });
        cartbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                builder.setTitle("Choose Category").setItems(Constants.productCategories1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //get selected items
                        String selected = Constants.productCategories1[which];
                        filteredproducts.setText(selected);
                        if(selected.equals("All")){
                            //loads all products
                            loadShopProducts();
                        }else{
                            adapterProductUser.getFilter().filter(selected);
                        }

                    }
                }).show();
            }
        });


    }

    private void loadMyInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("users");
        ref.child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    String name = ""+ds.child("fullname").getValue();
                    String city = ""+ds.child("city").getValue();
                    String phonenumber = ""+ds.child("phonenumber").getValue();
                    String email = ""+ds.child("email").getValue();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void loadShopDetails() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("seller");
        ref.child(shopUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get shop data
                shopname =""+snapshot.child("shopname").getValue();
                shopaddress= ""+snapshot.child("address").getValue();
                shopemail = ""+snapshot.child("email").getValue();
                shopphone = ""+snapshot.child("phonenumber").getValue();

                //set shop data
                shopName.setText(shopname);
                email.setText(shopemail);
                address.setText(shopaddress);
                phoneNumber.setText(shopphone);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void loadShopProducts() {

        productArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("seller");
        ref.child(shopUid).child("Products")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //clean list before
                        productArrayList.clear();
                        for(DataSnapshot ds: snapshot.getChildren()){
                            ModelProduct modelProduct = ds.getValue(ModelProduct.class);
                            productArrayList.add(modelProduct);

                        }
                        adapterProductUser = new AdapterProductUser(getApplicationContext(), productArrayList);
                        //set adapter
                        productsRv.setAdapter(adapterProductUser);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}