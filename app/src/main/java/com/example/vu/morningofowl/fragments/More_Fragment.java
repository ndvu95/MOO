package com.example.vu.morningofowl.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.activities.Reg_Activity;
import com.example.vu.morningofowl.activities.Start_Activity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class More_Fragment extends Fragment {
    private TextView tvLogout, tvUser;
    private FirebaseAuth mAuth;;
    private FirebaseDatabase mData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more,container,false);
        tvLogout =(TextView)view.findViewById(R.id.tvLogout);
        tvUser = (TextView)view.findViewById(R.id.tvCurrentUser);
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();;
        SpannableString ss = new SpannableString("Đăng Xuất");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(),Start_Activity.class);
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpan,0,9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.owl)),0,9,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvLogout.setText(ss);
        tvLogout.setMovementMethod(LinkMovementMethod.getInstance());

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String email = firebaseUser.getEmail();

        tvUser.setText("Xin Chào" +email);
    }
}
