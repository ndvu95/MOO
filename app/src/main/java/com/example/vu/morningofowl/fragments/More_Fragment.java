package com.example.vu.morningofowl.fragments;

import android.app.Dialog;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.activities.AccountActivity;
import com.example.vu.morningofowl.activities.ChangePassword_Activity;
import com.example.vu.morningofowl.activities.Home_Activity;
import com.example.vu.morningofowl.activities.Reg_Activity;
import com.example.vu.morningofowl.activities.Start_Activity;
import com.example.vu.morningofowl.activities.Watch_Later_Activity;
import com.example.vu.morningofowl.adapter.Expandablelist_Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class More_Fragment extends Fragment {
    private ExpandableListView listView;
    private Expandablelist_Adapter adapter;
    private List<String> listHeader;
    private HashMap<String, List<String>> listHash;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mData;
    private FirebaseUser mUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_more, container, false);
        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance();
        listView = (ExpandableListView) view.findViewById(R.id.lvMore);
        initData();
        adapter = new Expandablelist_Adapter(getContext(), listHeader, listHash);
        listView.setAdapter(adapter);
        listView.expandGroup(0);
        listView.expandGroup(2);
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                switch (groupPosition) {
                    case 0:
                        switch (childPosition) {
                            case 0:
                                Intent intentWL = new Intent(getContext(), Watch_Later_Activity.class);
                                startActivity(intentWL);
                                break;
                            case 1:
                                Intent intentAccount = new Intent(getContext(), AccountActivity.class);
                                startActivity(intentAccount);
                                break;
                            case 2:
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(getContext(), "Vui Lòng Đăng Nhập Để Tiếp Tục Sử Dụng", Toast.LENGTH_SHORT).show();
                                Intent intent1 = new Intent(getContext(), Start_Activity.class);
                                startActivity(intent1);
                                break;
                        }
                        break;
                    case 2:
                        switch (childPosition) {
                            case 0:
                                dialogFeedback();
                                break;
                        }
                }
                return true;
            }
        });
        return view;
    }

    private void dialogFeedback() {
        final Dialog dialog = new Dialog(getContext());
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_feedback);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);


        Button btnHuy = (Button) dialog.findViewById(R.id.btnHuy);
        Button btnSubmit = (Button) dialog.findViewById(R.id.btnSubmit);


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUser = FirebaseAuth.getInstance().getCurrentUser();

                EditText edtContent = (EditText) dialog.findViewById(R.id.edtContent);
                String feedbackContent = edtContent.getText().toString();
                submitFeedback(feedbackContent);

                edtContent.setText("");
                Toast.makeText(getContext(), "Cảm ơn bạn đã đóng góp ý kiến", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }

    private void submitFeedback(final String content) {
        DatabaseReference mDataFeedBack;
        final DatabaseReference mData;
        FirebaseUser user;
        mData = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        String email = mUser.getEmail();
        final String userID = user.getUid();
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month_of_year = cal.get(Calendar.MONTH);
        int day_of_month = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        mDataFeedBack = mData.child("FeedBack").child(userID).child("" + day_of_month + "-" + (month_of_year + 1) + "-" + year + ":" + hour + ":" + minute + ":" + second);
        mDataFeedBack.child("content").setValue(content);
        mDataFeedBack.child("Email").setValue(email);
    }

    private void initData() {
        listHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listHeader.add("Tài Khoản");
        listHeader.add("Phim");
        listHeader.add("Hỗ Trợ");


        List<String> taiKhoan = new ArrayList<>();
        taiKhoan.add("Danh Sách Xem Sau");
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

        List<String> hotro = new ArrayList<>();
        hotro.add("Feedback");
        hotro.add("About Us");

        listHash.put(listHeader.get(0), taiKhoan);
        listHash.put(listHeader.get(1), theloaiPhim);
        listHash.put(listHeader.get(2), hotro);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        String email = firebaseUser.getEmail();
    }
}
