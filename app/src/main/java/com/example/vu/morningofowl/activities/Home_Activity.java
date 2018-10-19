package com.example.vu.morningofowl.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.vu.morningofowl.BottomNavigationViewHelper;
import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.fragments.Feed_Fragment;
import com.example.vu.morningofowl.fragments.Home_Fragment;
import com.example.vu.morningofowl.fragments.Kid_Fragment;
import com.example.vu.morningofowl.fragments.Live_Fragment;
import com.example.vu.morningofowl.fragments.More_Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.lang.reflect.Field;

public class Home_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        BottomNavigationViewHelper.removeShiftMode(bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray_dark));


        Fragment defaultFragment = new Home_Fragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.flkhung,defaultFragment).commit();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()){
                        case R.id.nav_home:
                            selectedFragment = new Home_Fragment();
                            break;
                        case R.id.nav_feed:
                            selectedFragment  = new Feed_Fragment();
                            break;
                        case R.id.nav_kid:
                            selectedFragment  = new Kid_Fragment();
                            break;
                        case R.id.nav_live:
                            selectedFragment  = new Live_Fragment();
                            break;
                        case R.id.nav_more:
                            selectedFragment  = new More_Fragment();
                            break;
                    }
            getSupportFragmentManager().beginTransaction().replace(R.id.flkhung,selectedFragment).commit();
                    return true;
                }
            };

}
