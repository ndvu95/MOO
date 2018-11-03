package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.example.vu.morningofowl.BottomNavigationViewHelper;
import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.fragments.Admin_Fragment;
import com.example.vu.morningofowl.fragments.Home_Fragment;
import com.example.vu.morningofowl.fragments.Kid_Fragment;
import com.example.vu.morningofowl.fragments.More_Fragment;
import com.google.firebase.auth.FirebaseAuth;

public class Home_Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Home_Fragment home_fragment;
    private ImageButton searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        searchView = (ImageButton)findViewById(R.id.searchPhim);
        BottomNavigationViewHelper.removeShiftMode(bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(this);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray_dark));

        loadFragment(new Home_Fragment());

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this, Search_Activity.class);
                startActivity(intent);
            }
        });
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        String uid = mAuth.getCurrentUser().getUid();
        if(!uid.equals("rAgQIoO5ouhal07Edgd53p7HNly1")){
            bottomNav.getMenu().removeItem(R.id.nav_admin);
        }
    }



    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.flkhung, fragment)
                    .addToBackStack(null)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        switch (item.getItemId()) {
            case R.id.nav_home:
                selectedFragment = new Home_Fragment();
                break;
            case R.id.nav_admin:
                selectedFragment = new Admin_Fragment();
                break;
            case R.id.nav_kid:
                selectedFragment = new Kid_Fragment();
                break;
            case R.id.nav_more:
                selectedFragment = new More_Fragment();
                break;
        }
        return loadFragment(selectedFragment);
    }



}