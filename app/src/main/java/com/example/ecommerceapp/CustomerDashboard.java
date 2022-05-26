package com.example.ecommerceapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.ecommerceapp.Fragments.CustomerCartFragment;
import com.example.ecommerceapp.Fragments.CustomerHomeFragment;
import com.example.ecommerceapp.Fragments.CustomerOrdersFragment;
import com.example.ecommerceapp.Fragments.CustomerProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerDashboard extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_dashboard);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.user_bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigation);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigation = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.user_homepage:
                    selectedFragment = new CustomerHomeFragment();
                    break;

                case R.id.user_cart:
                    selectedFragment = new CustomerCartFragment();
                    break;

                case R.id.user_orders:
                    selectedFragment = new CustomerOrdersFragment();
                    break;

                case R.id.user_profile:
                    selectedFragment = new CustomerProfileFragment();
                    break;
            }
            ///////////replacing by default fragment on customer dashboard
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, selectedFragment).commit();
            return true;
        }
    };
}

