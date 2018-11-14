package com.example.vu.morningofowl.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.activities.All_UserActivity;
import com.example.vu.morningofowl.activities.BannerManagerActivity;
import com.example.vu.morningofowl.activities.FeedbackManagerActivity;
import com.example.vu.morningofowl.activities.ManagerAddPhim_Activity;
import com.example.vu.morningofowl.activities.QL_PhimActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Admin_Fragment extends Fragment {
    LinearLayout linearPhim, linearUser, linearAd, linearFeedback;
    TextView tvFeedCount;
    DatabaseReference mData;
    View view;
    private int i =0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin, container, false);

        InitUI();



            mData = FirebaseDatabase.getInstance().getReference("FeedBack");
            mData.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    i=0;
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        if(ds.exists()){
                                String status = ds.child("Status").getValue(String.class);
                                if(status != null && status.equals("Sent")){
                                    i++;
                                }
                                tvFeedCount.setText(""+i);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



        linearPhim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), QL_PhimActivity.class);
                startActivity(intent);
            }
        });

        linearUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), All_UserActivity.class);
                startActivity(intent);
            }
        });


        linearAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), BannerManagerActivity.class);
                startActivity(intent);
            }
        });

        linearFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), FeedbackManagerActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private void InitUI() {
        linearPhim = (LinearLayout) view.findViewById(R.id.linearPhim);
        linearUser = (LinearLayout) view.findViewById(R.id.linearUser);
        linearAd = (LinearLayout) view.findViewById(R.id.linearAd);
        linearFeedback = (LinearLayout) view.findViewById(R.id.linearFeedback);
        tvFeedCount = (TextView) view.findViewById(R.id.tvnotiFeedbackCount);
    }

}
