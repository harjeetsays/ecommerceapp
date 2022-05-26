package com.example.ecommerceapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ecommerceapp.CustomerDashboard;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.activities.RegisterAsCustomer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class CustomerLoginFragment extends Fragment {

    Button loginbtn;
    Button signupbtn;

    TextInputLayout email_var, password_var;

    FirebaseAuth firebaseAuth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.customer_login_fragment, container, false);

        //hooking the buttons
        loginbtn = root.findViewById(R.id.customer_login_btn);
        signupbtn = root.findViewById(R.id.customer_signup_btn);

        //getting instance of firebase auth to authenticate user
        firebaseAuth = FirebaseAuth.getInstance();

        //hooking text fields
        email_var = root.findViewById(R.id.customer_emailid);
        password_var = root.findViewById(R.id.customer_password);

        //creating intent for new user
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RegisterAsCustomer.class);
                startActivity(intent);
            }
        });

        //creating intent for login
        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //getting text form text fields
                String email_ = email_var.getEditText().getText().toString();
                String password_ = password_var.getEditText().getText().toString();

                //validation, verification and login
                if (!email_.isEmpty()) {
                    email_var.setError(null);
                    email_var.setErrorEnabled(false);

                    if (!password_.isEmpty()) {
                        password_var.setError(null);
                        password_var.setErrorEnabled(false);




                                    firebaseAuth.signInWithEmailAndPassword(email_, password_).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {

                                            //if looged in successfully
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getActivity().getApplicationContext(), "logged in", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getActivity().getApplicationContext(), CustomerDashboard.class);
                                                startActivity(intent);
                                            } else {
                                                //shows error why login was failed
                                                Toast.makeText(getActivity().getApplicationContext(), "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    });







                    } else {
                        password_var.setError("Cannot be Empty");
                    }
                } else {
                    email_var.setError("Cannot be empty");
                }


            }
        });

        return root;
    }
}
