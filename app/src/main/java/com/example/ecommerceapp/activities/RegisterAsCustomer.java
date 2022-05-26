package com.example.ecommerceapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ecommerceapp.CustomerDashboard;
import com.example.ecommerceapp.R;
import com.example.ecommerceapp.StoringCustomerData;
import com.example.ecommerceapp.activities.Login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class RegisterAsCustomer extends AppCompatActivity {
    Button registerbtn;

    TextInputLayout fullname_var, email_var, phonenumber_var, password_var, confirmpassword_var;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        registerbtn = findViewById(R.id.register_user);

        //getting instance of firebase authentication to use in later to create new  user
        firebaseAuth = FirebaseAuth.getInstance();

        //hooking all textfields
        fullname_var = findViewById(R.id.full_name);
        email_var = findViewById(R.id.email_address_text_field_design);
        phonenumber_var = findViewById(R.id.phone_number);
        password_var = findViewById(R.id.password_input_field);
        confirmpassword_var = findViewById(R.id.confirm_password_input_field);

        //checking if user is already logged in through an account
        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(), CustomerDashboard.class);
            startActivity(intent);
            finish();
        }

        //register button coding
        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Pattern PASSWORD_PATTERN =
                        Pattern.compile("^" +
                                "(?=.*[@#$%^&+=])" +     // at least 1 special character
                                "(?=\\S+$)" +            // no white spaces
                                ".{6,}" +                // at least 4 characters
                                "$");

                String fullname_ = fullname_var.getEditText().getText().toString();
                String email_ = email_var.getEditText().getText().toString();
                String phonenumber_ = phonenumber_var.getEditText().getText().toString();
                String password_ = password_var.getEditText().getText().toString();
                String confirmpassword_ = confirmpassword_var.getEditText().getText().toString();

                if (!fullname_.isEmpty()) {
                    fullname_var.setError(null);
                    fullname_var.setErrorEnabled(false);
                    if (!email_.isEmpty()) {
                        email_var.setError(null);
                        email_var.setErrorEnabled(false);
                        if (!phonenumber_.isEmpty() & phonenumber_.length() == 10) {
                            phonenumber_var.setErrorEnabled(false);
                            phonenumber_var.setError(null);
                            if (!password_.isEmpty()) {
                                password_var.setError(null);
                                password_var.setErrorEnabled(false);
                                if (PASSWORD_PATTERN.matcher(password_).matches()) {
                                    password_var.setError(null);
                                    password_var.setErrorEnabled(false);
                                    if (!confirmpassword_.isEmpty()) {
                                        confirmpassword_var.setError(null);
                                        confirmpassword_var.setErrorEnabled(false);
                                        if (password_.equals(confirmpassword_)) {
                                            confirmpassword_var.setError(null);
                                            confirmpassword_var.setErrorEnabled(false);

                                            firebaseDatabase = FirebaseDatabase.getInstance();
                                            reference = firebaseDatabase.getReference("users");

                                            String fullname_s = fullname_var.getEditText().getText().toString();
                                            String email_s = email_var.getEditText().getText().toString();
                                            String phonenumber_s = phonenumber_var.getEditText().getText().toString();
                                            String password_s = password_var.getEditText().getText().toString();
                                            String address = "";

                                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.child(phonenumber_s).exists()) {

                                                        Toast.makeText(getApplicationContext(), "Phone number already registered", Toast.LENGTH_SHORT).show();

                                                    } else {

                                                        firebaseAuth.createUserWithEmailAndPassword(email_s,password_s).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                if(task.isSuccessful()){

                                                                    //getting unique id with which user is authenticated
                                                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                                                    String userID = user.getUid();

                                                                    //storing data in realtime db of user if user created with email
                                                                    StoringCustomerData sud = new StoringCustomerData(fullname_s, email_s, phonenumber_s, password_s,address);

                                                                    //storing data in in user under child which is UID of user
                                                                    reference.child(userID).setValue(sud);

                                                                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();

                                                                    @SuppressLint("HandlerLeak") Handler h = new Handler(){
                                                                    @Override
                                                                         public void handleMessage(Message msg) {

                                                                    Intent intent = new Intent(RegisterAsCustomer.this, MapActivityCustomer.class);
                                                                    startActivity(intent);
                                                                    finish();
                                                                }
                                                                };

                                                                h.sendEmptyMessageDelayed(0, 4000); //

                                                                    //once data is stored customer to be divereted to dashboard


                                                                }else{

                                                                }
                                                            }
                                                        });



                                                    }

                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    Toast.makeText(getApplicationContext(), "error occured"+error.getMessage(), Toast.LENGTH_SHORT).show();

                                                }
                                            });


                                        } else {
                                            confirmpassword_var.setError("password not matches");
                                        }
                                    } else {
                                        confirmpassword_var.setError("Cannot be null");
                                    }
                                } else {
                                    password_var.setError("Password have at least 1 special character\n At least 6 characters \n No white spaces");
                                }
                            } else {
                                password_var.setError("Cannot be empty");
                            }

                        } else {
                            phonenumber_var.setError("Enter phone number\n Number must be 10 digits");
                        }

                    } else {
                        email_var.setError("Cannot be null");
                    }
                } else {
                    fullname_var.setError("Cannot be null");
                }
            }
        });

    }

    public void loginbuttonclick(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }
}
