package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.ecommerceapp.Fragments.SellerAddProductFragment;
import com.example.ecommerceapp.Fragments.SellerHomePageFragment;
import com.example.ecommerceapp.Fragments.SellerOrdersFragment;
import com.example.ecommerceapp.Fragments.SellerProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;


public class SellerDashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        bottomNavigationView =(BottomNavigationView) findViewById(R.id.seller_bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigation);

    }
    private BottomNavigationView.OnNavigationItemSelectedListener navigation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectedFragment = null;
            switch(menuItem.getItemId()){
                case R.id.seller_homepage:
                    selectedFragment = new SellerHomePageFragment();
                    break;
                case R.id.seller_add_products:
                    selectedFragment = new SellerAddProductFragment();
                    break;
                case R.id.seller_orders:
                    selectedFragment = new SellerOrdersFragment();
                    break;
                case R.id.seller_profile:
                    selectedFragment = new SellerProfileFragment();
                    break;

            }
            ///////////replacing by default fragment on customer dashboard
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout1, selectedFragment).commit();
            return true;
        }
    };
}