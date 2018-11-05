package com.example.vu.morningofowl.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.Adapter_All_User;
import com.example.vu.morningofowl.model.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class All_UserActivity extends AppCompatActivity {
    private RecyclerView userRecycler;
    private Adapter_All_User adapter;
    ArrayList<Users> usersList;
    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__user);
        userRecycler = (RecyclerView) findViewById(R.id.recyclerUser);


        usersList = new ArrayList<>();
        adapter = new Adapter_All_User(usersList, All_UserActivity.this);
        userRecycler.setAdapter(adapter);
        userRecycler.setLayoutManager(new LinearLayoutManager(this));

        mData = FirebaseDatabase.getInstance().getReference("Users");
        fillData();
    }

    private void fillData() {
        DatabaseReference mData1 = FirebaseDatabase.getInstance().getReference("Users");
        mData1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usersList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Users users = ds.getValue(Users.class);
                    usersList.add(users);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
