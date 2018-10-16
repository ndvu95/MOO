package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.example.vu.morningofowl.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen_Activity extends AppCompatActivity {
private ProgressBar progressBar;
    private FirebaseAuth mAuth;;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_);
    progressBar = (ProgressBar)findViewById(R.id.pgBar);
    mAuth = FirebaseAuth.getInstance();
    progressBar.setVisibility(View.VISIBLE);
    new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                Intent intent1 = new Intent(SplashScreen_Activity.this, Home_Activity.class);
                progressBar.setVisibility(View.GONE);
                startActivity(intent1);
            }else{
                Intent intent =new Intent(SplashScreen_Activity.this, Start_Activity.class);
                progressBar.setVisibility(View.GONE);
                startActivity(intent);
            }
            finish();
        }
    }, 3500);
    }
}
