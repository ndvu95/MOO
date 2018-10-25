package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.vu.morningofowl.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashScreen_Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray));


    mAuth = FirebaseAuth.getInstance();

    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null && currentUser.isEmailVerified()){
                Intent intent1 = new Intent(SplashScreen_Activity.this, Home_Activity.class);
                startActivity(intent1);
            }else{
                Intent intent =new Intent(SplashScreen_Activity.this, Start_Activity.class);
                startActivity(intent);
            }
            finish();
        }
    }, 3500);


}}
