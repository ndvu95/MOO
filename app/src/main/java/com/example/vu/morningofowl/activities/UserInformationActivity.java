package com.example.vu.morningofowl.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.Adapter_User_Log;
import com.example.vu.morningofowl.model.User_Log;
import com.example.vu.morningofowl.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.blurry.Blurry;

public class UserInformationActivity extends AppCompatActivity {
    TextView tvHoTen, tvLastSeen, tvMail, tvPhone;
    ImageView imageViewBG;
    private LinearLayout layout;
    CircleImageView imgAvatar, green_dot_user;
    ListView lichsuUser;
    Context context;
    String email;
    DatabaseReference mData;

    ArrayList<User_Log> arrayList;
    Adapter_User_Log adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_information);
        Window w = getWindow(); // in Activity's onCreate() for instance
        w.setStatusBarColor(ContextCompat.getColor(this, R.color.gray_dark));

        context = this;

        initUI();
        mData = FirebaseDatabase.getInstance().getReference();
        arrayList = new ArrayList<>();
        adapter = new Adapter_User_Log(context, R.layout.user_log_list_item, arrayList);
        lichsuUser.setAdapter(adapter);


        Intent intent = getIntent();
        Users users = (Users) intent.getSerializableExtra("dulieu");


        String hoten = users.HoTen;
        email = users.Email;
        String phone = users.SDT;
        String last_seen = users.Last_Active;
        String status = users.Status;


        tvHoTen.setText(hoten);
        tvMail.setText("<" + email + ">");
        tvPhone.setText(phone);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;

        Uri userAvatar = Uri.parse(users.Image);

        Picasso.with(UserInformationActivity.this)
                .load(userAvatar)
                .into(imgAvatar);

        Picasso.with(UserInformationActivity.this).load(userAvatar).resize(width, 300).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Blurry.with(UserInformationActivity.this).radius(25).from(bitmap).into(imageViewBG);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });

        if (status.equals("Offline")) {
            tvLastSeen.setText("Online lần cuối: " + last_seen);
            green_dot_user.setVisibility(View.INVISIBLE);
        } else {
            tvLastSeen.setTextColor(getResources().getColor(R.color.online));
            tvLastSeen.setText(status);
            green_dot_user.setVisibility(View.VISIBLE);
        }

        getUID();
    }

    private void populateListView(String uid) {
        mData.child("UserLog").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    if(ds.exists()){
                        String dateTime = ds.child("dateTime").getValue().toString();
                        String watchedMovie = ds.child("movieWatched").getValue().toString();
                        arrayList.add(new User_Log(dateTime, watchedMovie));
                    }else{
                        Toast.makeText(context, "Nhật ký xem phim của người dùng chưa có", Toast.LENGTH_SHORT).show();
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getUID() {
        mData.child("Users").orderByChild("Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String key = ds.getKey();
                    populateListView(key);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void initUI() {
        tvHoTen = (TextView) findViewById(R.id.tvHoTen);
        tvLastSeen = (TextView) findViewById(R.id.tvLastSeen);
        tvMail = (TextView) findViewById(R.id.tvMail);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        layout = (LinearLayout) findViewById(R.id.layoutUserInfo);

        green_dot_user = (CircleImageView) findViewById(R.id.green_dot_user);
        imageViewBG = (ImageView) findViewById(R.id.imageViewBG);
        imgAvatar = (CircleImageView) findViewById(R.id.imgAvatar);
        lichsuUser = (ListView) findViewById(R.id.lichsuUser);
    }

    public void clickBackToAllUser(View view) {
        finish();
    }

}
