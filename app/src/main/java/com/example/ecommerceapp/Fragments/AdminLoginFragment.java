package com.example.ecommerceapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ecommerceapp.R;
import com.google.android.material.textfield.TextInputLayout;


public class AdminLoginFragment extends Fragment {

    Button loginbtn;
    TextInputLayout username_var,password_var;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root =(ViewGroup) inflater.inflate(R.layout.admin_login_fragment,container,false);

        //hooking the buttons
        loginbtn = root.findViewById(R.id.admin_login_btn);
        //text views
        username_var = root.findViewById(R.id.admin_username);
        password_var = root.findViewById(R.id.admin_password);

        //creating intent for login
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String password_ = password_var.getEditText().getText().toString();
                String username_ = username_var.getEditText().getText().toString();

                //validation, verification and login

                if (!username_.isEmpty()) {
                    username_var.setError(null);
                    username_var.setErrorEnabled(false);
                        if (!password_.isEmpty()) {
                            password_var.setError(null);
                            password_var.setErrorEnabled(false);
                        } else {
                            password_var.setError("Cannot be Empty");
                        }

                } else {
                    username_var.setError("Cannot be empty");
                }

               // Intent intent = new Intent(getActivity(),Mainactivity.class);
               // startActivity(intent);
            }
        });

        return root;
    }



}
