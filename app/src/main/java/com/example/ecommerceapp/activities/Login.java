package com.example.ecommerceapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.example.ecommerceapp.adapters.LoginAdapter;
import com.example.ecommerceapp.R;
import com.google.android.material.tabs.TabLayout;

public class Login extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Customer"));
        tabLayout.addTab(tabLayout.newTab().setText("Seller"));
        tabLayout.addTab(tabLayout.newTab().setText("Admin"));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //making object of login adapter
        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this,tabLayout.getTabCount());

        //setting view pager to adaprter
        viewPager.setAdapter(adapter);

        //adding page changer listener to view pager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));


        //animation part for last , do it lastly


    }
}

