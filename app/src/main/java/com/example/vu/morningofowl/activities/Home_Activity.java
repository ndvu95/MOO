package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Map;

public class Home_Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    private Home_Fragment home_fragment;
    private ImageButton searchView;
    FirebaseAuth mAuth;
    DatabaseReference mData;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        searchView = (ImageButton) findViewById(R.id.searchPhim);
        BottomNavigationViewHelper.removeShiftMode(bottomNav);
        bottomNav.setOnNavigationItemSelectedListener(this);
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray_dark));

        mData = FirebaseDatabase.getInstance().getReference();



        loadFragment(new Home_Fragment());

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Activity.this, Search_Activity.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();

        String uid = mAuth.getCurrentUser().getUid();
        userID = uid;
        if (!uid.equals("rAgQIoO5ouhal07Edgd53p7HNly1")) {
            bottomNav.getMenu().removeItem(R.id.nav_admin);
        }

        DatabaseReference connectRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://morning-of-owl.firebaseio.com");
        final DatabaseReference mDataOnlineState = mData.child("Users").child(userID).child("Status");
        final DatabaseReference lastOnline = mData.child("Users").child(userID).child("Last_Active");


        Calendar cal = Calendar.getInstance();
        final int year = cal.get(Calendar.YEAR);
        final int month = (cal.get(Calendar.MONTH) + 1);
        final int day = cal.get(Calendar.DAY_OF_MONTH);

        final int hour = cal.get(Calendar.HOUR_OF_DAY);
        final int minute = cal.get(Calendar.MINUTE);

        connectRef.child(".info").child("connected").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Boolean con = dataSnapshot.getValue(Boolean.class);
                if(con){
                    mDataOnlineState.setValue("Đang Hoạt Động");
                    lastOnline.setValue("Đang Hoạt Động");


                    mDataOnlineState.onDisconnect().setValue("Offline");
                    lastOnline.onDisconnect().setValue("Ngày "
                            + day
                            + " tháng "
                            + month + " năm "
                            + year
                            + " lúc " + hour
                            + "h:" + minute);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


//    private void push_Category() {
//        mData = FirebaseDatabase.getInstance().getReference();
//        mData.child("TheLoai").push().child("TenTheLoai").setValue("Hành Động");
//        mData.child("TheLoai").push().child("TenTheLoai").setValue("Phiêu Lưu");
//        mData.child("TheLoai").push().child("TenTheLoai").setValue("Kinh Dị");
//        mData.child("TheLoai").push().child("TenTheLoai").setValue("Thiếu Nhi");
//        mData.child("TheLoai").push().child("TenTheLoai").setValue("Hoạt Hình");
//        mData.child("TheLoai").push().child("TenTheLoai").setValue("Khoa Học Viễn Tưởng");
//        mData.child("TheLoai").push().child("TenTheLoai").setValue("Tài Liệu");
//        mData.child("TheLoai").push().child("TenTheLoai").setValue("Tội Phạm");
//        mData.child("TheLoai").push().child("TenTheLoai").setValue("Tâm Lý");
//        mData.child("TheLoai").push().child("TenTheLoai").setValue("Tình Cảm");
//
//    }

//    private void checkOffline() {
//
//        Calendar cal = Calendar.getInstance();
//        int year = cal.get(Calendar.YEAR);
//        int month = (cal.get(Calendar.MONTH) + 1);
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
//        int minute = cal.get(Calendar.MINUTE);
//
//
//        if(mAuth.getCurrentUser() != null){
//        mData.child("Users").child(userID).child("Status").setValue("Offline");
//        mData.child("Users").child(userID).child("Last_Active").setValue("Ngày "
//                + day
//                + " tháng "
//                + month + " năm "
//                + year
//                + " lúc " + hour
//                + "h:" + minute);
//    }
//}


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