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
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.activities.AccountActivity;
import com.example.vu.morningofowl.activities.ChangePassword_Activity;
import com.example.vu.morningofowl.activities.Home_Activity;
import com.example.vu.morningofowl.activities.Reg_Activity;
import com.example.vu.morningofowl.activities.Start_Activity;
import com.example.vu.morningofowl.adapter.Expandablelist_Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class More_Fragment extends Fragment {
    private ExpandableListView listView;
    private Expandablelist_Adapter adapter;
    private List<String> listHeader;
    private HashMap<String,List<String>> listHash;
    private FirebaseAuth mAuth;;
    private FirebaseDatabase mData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more,container,false);
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();;
        listView = (ExpandableListView)view.findViewById(R.id.lvMore);
        initData();
        adapter = new Expandablelist_Adapter(getContext(),listHeader,listHash);
                listView.setAdapter(adapter);
                listView.expandGroup(0);
                listView.expandGroup(2);
                listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                switch(groupPosition){
                    case 0:
                        switch (childPosition){
                            case 0:
                                Intent intentAccount = new Intent(getContext(),AccountActivity.class);
                                startActivity(intentAccount);
                                break;
                            case 1:
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(getContext(), "Vui Lòng Đăng Nhập Để Tiếp Tục Sử Dụng", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(getContext(),Start_Activity.class);
                                startActivity(intent1);
                                break;
                            case 2:
                                break;
                        }
                }
                return true;
            }
        });
        return view;
    }

    private void initData() {
        listHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listHeader.add("Tài Khoản");
        listHeader.add("Phim");
        listHeader.add("Hỗ Trợ");


        List<String> taiKhoan = new ArrayList<>();
        taiKhoan.add("Thông Tin");
        taiKhoan.add("Đăng Xuất");

        List<String> theloaiPhim = new ArrayList<>();
        theloaiPhim.add("Hành Động");
        theloaiPhim.add("Phiêu Lưu");
        theloaiPhim.add("Kinh Dị");
        theloaiPhim.add("Thiếu Nhi");
        theloaiPhim.add("Hoạt Hình");
        theloaiPhim.add("Khoa Học Viễn Tưởng");
        theloaiPhim.add("Tài Liệu");
        theloaiPhim.add("Tội Phạm");

        List<String> hotro= new ArrayList<>();
        hotro.add("Liên Hệ");
        hotro.add("Câu Hỏi Thường Gặp");
        hotro.add("About Us");

        listHash.put(listHeader.get(0),taiKhoan);
        listHash.put(listHeader.get(1),theloaiPhim);
        listHash.put(listHeader.get(2),hotro);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String email = firebaseUser.getEmail();
    }
}
