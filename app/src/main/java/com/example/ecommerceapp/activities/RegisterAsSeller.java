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
import com.example.ecommerceapp.SellerDashboard;
import com.example.ecommerceapp.SellerMapActivity;
import com.example.ecommerceapp.StoringSellerData;
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

public class RegisterAsSeller extends AppCompatActivity {
    Button backtologinbtn, registerbtn;

    TextInputLayout shopname_var,  phonenmbr_var, paswrd_var, confirmpswrd_var, email_var;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_as_seller);

        firebaseAuth = FirebaseAuth.getInstance();

        backtologinbtn = findViewById(R.id.back_to_login);
        registerbtn = findViewById(R.id.register);

        shopname_var = findViewById(R.id.shop_name);

        phonenmbr_var = findViewById(R.id.phone_number);
        email_var = findViewById(R.id.seller_email);
        paswrd_var = findViewById(R.id.password_input_field);
        confirmpswrd_var = findViewById(R.id.confirm_password_input_field);

        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(getApplicationContext(), SellerDashboard.class);
            startActivity(intent);
            finish();
        }

        backtologinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Pattern PASSWORD_PATTERN =
                        Pattern.compile("^" +
                                "(?=.*[@#$%^&+=])" +     // at least 1 special character
                                "(?=\\S+$)" +            // no white spaces
                                ".{6,}" +                // at least 4 characters
                                "$");

                String shopname_ = shopname_var.getEditText().getText().toString();

                String phnnmbr_ = phonenmbr_var.getEditText().getText().toString();
                String pswrd_ = paswrd_var.getEditText().getText().toString();
                String cnfrmpswrd_ = confirmpswrd_var.getEditText().getText().toString();
                String email_ = email_var.getEditText().getText().toString();


                if (!shopname_.isEmpty()) {
                    shopname_var.setError(null);
                    shopname_var.setErrorEnabled(false);
                    if(!email_.isEmpty()) {
                        email_var.setErrorEnabled(false);
                        email_var.setError(null);
                        if (!phnnmbr_.isEmpty() & phnnmbr_.length() == 10) {
                            phonenmbr_var.setError(null);
                            phonenmbr_var.setErrorEnabled(false);
                            if (!pswrd_.isEmpty()) {
                                paswrd_var.setError(null);
                                paswrd_var.setErrorEnabled(false);
                                if (PASSWORD_PATTERN.matcher(pswrd_).matches()) {
                                    paswrd_var.setError(null);
                                    paswrd_var.setErrorEnabled(false);
                                    if (!cnfrmpswrd_.isEmpty()) {
                                        confirmpswrd_var.setError(null);
                                        confirmpswrd_var.setErrorEnabled(false);
                                        if (pswrd_.equals(cnfrmpswrd_)) {
                                            confirmpswrd_var.setError(null);
                                            confirmpswrd_var.setErrorEnabled(false);

                                            firebaseDatabase = FirebaseDatabase.getInstance();
                                            reference = firebaseDatabase.getReference("seller");

                                            String shopname_s = shopname_var.getEditText().getText().toString();
                                            String phnnmbr_s = phonenmbr_var.getEditText().getText().toString();
                                            String pswrd_s = paswrd_var.getEditText().getText().toString();
                                            String email_s = email_var.getEditText().getText().toString();
                                            String address = "";


                                            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                    if (snapshot.child(phnnmbr_s).exists()) {

                                                        Toast.makeText(getApplicationContext(), "Phone number already registered", Toast.LENGTH_SHORT).show();

                                                    } else {

                                                        firebaseAuth.createUserWithEmailAndPassword(email_s,pswrd_s).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                if(task.isSuccessful()){

                                                                    //getting unique id with which user is authenticated
                                                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                                                    String userID = user.getUid();

                                                                    Toast.makeText(getApplicationContext(),"Registered Successfully", Toast.LENGTH_SHORT).show();
                                                                    //sending user data to getter and setter class
                                                                    StoringSellerData ssd = new StoringSellerData(shopname_s, phnnmbr_s, pswrd_s, email_s,address);
                                                                    //storing data in seller node
                                                                    reference.child(userID).setValue(ssd);

                                                                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();

                                                                    @SuppressLint("HandlerLeak") Handler h = new Handler(){
                                                                        @Override
                                                                        public void handleMessage(Message msg) {

                                                                            Intent intent = new Intent(RegisterAsSeller.this, SellerMapActivity.class);
                                                                            startActivity(intent);
                                                                            finish();
                                                                        }
                                                                    };

                                                                    h.sendEmptyMessageDelayed(0, 4000); //


                                                                }else{
                                                                    Toast.makeText(RegisterAsSeller.this,"Error !"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                                                                }

                                                            }
                                                        });


                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {
                                                    Toast.makeText(getApplicationContext(), error.getMessage(),Toast.LENGTH_SHORT).show();

                                                }
                                            });
                                        } else {
                                            confirmpswrd_var.setError("Password doesn't matches");
                                        }
                                    } else {
                                        confirmpswrd_var.setError("Cannot be Empty");
                                    }
                                } else {
                                    paswrd_var.setError("Password have at least 1 special character\n At least 6 characters \n No white spaces");

                                }
                            } else {
                                paswrd_var.setError("Enter password ");
                            }
                        } else {
                            phonenmbr_var.setError("Enter phone number \n NUmber should be 10 digits");
                        }
                    }else{
                        email_var.setError("Cannot be empty");
                    }
                } else {
                    shopname_var.setError("Enter shop name");
                }
            }


        });


    }


}
