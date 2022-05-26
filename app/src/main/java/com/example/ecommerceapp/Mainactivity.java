package com.example.ecommerceapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.activities.Login;

public class Mainactivity extends AppCompatActivity {


    //splash variable
    private static int SPLASH_SCREEN = 3500;

    //variables for animations
    Animation top_anim, bottom_anim;

    //views variables
    ImageView img;
    TextView logo, slogan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainactivity);

        //hooking animation
        top_anim = AnimationUtils.loadAnimation(this, R.anim.main_activity_top_animation);
        bottom_anim = AnimationUtils.loadAnimation(this, R.anim.main_activity_bottom_animation);

        //image hook
        img = findViewById(R.id.imageView);

        //text hooks
        logo = findViewById(R.id.textView2);
        slogan = findViewById(R.id.textView3);

        //assigning animation to image and text
        img.setAnimation(top_anim);
        logo.setAnimation(bottom_anim);
        slogan.setAnimation(bottom_anim);

        // writing intent under handler to delay moving to login page
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);


    }
}
