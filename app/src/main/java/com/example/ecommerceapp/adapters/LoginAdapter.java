package com.example.ecommerceapp.adapters;


import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.ecommerceapp.Fragments.AdminLoginFragment;
import com.example.ecommerceapp.Fragments.CustomerLoginFragment;
import com.example.ecommerceapp.Fragments.SellerLoginfragment;

public class LoginAdapter extends FragmentPagerAdapter {

    private Context context;

    //total no of pages
    int totaltabs;

    //constructor
    public LoginAdapter(FragmentManager fm, Context context, int totaltabs){
        super(fm);
        this.context = context;
        this.totaltabs = totaltabs;

    }


    @Override
    public int getCount() {
        return totaltabs;
    }

    //method to get while swiping
    public Fragment getItem(int position){
        switch(position){

            case 0:
               CustomerLoginFragment customerLoginFragment= new CustomerLoginFragment();
               return customerLoginFragment;

            case 1:
                SellerLoginfragment sellerLoginFragment = new SellerLoginfragment();
                return  sellerLoginFragment;

            case 2:
                AdminLoginFragment adminLoginFragment = new AdminLoginFragment();
                return  adminLoginFragment;

            default :
                return null;

        }
    }



}
