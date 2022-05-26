package com.example.ecommerceapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ecommerceapp.activities.Login;
import com.example.ecommerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SellerProfileFragment extends Fragment {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    FirebaseAuth firebaseAuth;
    View view;
    TextView logout_var,shopname_var,email_var,phone_number_var,address_var;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_seller_profile, container, false);

        ////////hooking log out text /////////////
        logout_var = view.findViewById(R.id.log_out_seller);

        ///////hooking all other profile detail texts/////////
        shopname_var = view.findViewById(R.id.shopname);
        email_var = view.findViewById(R.id.seller_email);
        phone_number_var = view.findViewById(R.id.seller_phone_number);
        address_var = view.findViewById(R.id.seller_address);

        /////////firebase auth to sign out //////////
        logout_var.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity().getApplicationContext(), "signed out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference("seller");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userID = user.getUid();
                if(snapshot.child(userID).exists()){
                    //////getting all the data from database///////
                    String fullname=snapshot.child(userID).child("shopname").getValue().toString();
                    String email=snapshot.child(userID).child("email").getValue().toString();
                    String phonenumber=snapshot.child(userID).child("phonenumber").getValue().toString();
                    String address=snapshot.child(userID).child("address").getValue().toString();
                    ///////////displaying data in their respective text views///////////
                    shopname_var.setText(fullname);
                    email_var.setText(email);
                    phone_number_var.setText(phonenumber);
                    address_var.setText(address);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("CH3","Failed to read value",error.toException());

            }
        });
        return view;
    }
}