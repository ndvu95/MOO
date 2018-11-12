package com.example.vu.morningofowl.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.activities.All_UserActivity;
import com.example.vu.morningofowl.activities.BannerManagerActivity;
import com.example.vu.morningofowl.activities.ManagerAddPhim_Activity;
import com.example.vu.morningofowl.activities.QL_PhimActivity;

public class Admin_Fragment extends Fragment {
    LinearLayout linearPhim, linearUser, linearAd, linearFeedback;

    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_admin,container,false);

        InitUI();

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

        return view;
    }

    private void InitUI() {
        linearPhim = (LinearLayout)view.findViewById(R.id.linearPhim);
        linearUser = (LinearLayout)view.findViewById(R.id.linearUser);
        linearAd = (LinearLayout)view.findViewById(R.id.linearAd);
        linearFeedback = (LinearLayout)view.findViewById(R.id.linearFeedback);

    }

}
