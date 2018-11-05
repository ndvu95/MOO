package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Phim_InfoActivity extends AppCompatActivity {
    EditText edtID, edtTen, edtLink, edtSub, edtPoster, edtCate, edtDes, edtActor;
    DatabaseReference mData;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phim__info);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray_dark));
        InitUI();

        edtID.setFocusable(false);
        edtID.setClickable(false);

        Intent intent = getIntent();
        key = intent.getStringExtra("Phim_UID");
        mData = FirebaseDatabase.getInstance().getReference("Phim").child(key);
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String id = dataSnapshot.child("idPhim").getValue().toString();
                    String ten = dataSnapshot.child("tenPhim").getValue().toString();
                    String link = dataSnapshot.child("linkPhim").getValue().toString();
                    String sub = dataSnapshot.child("linksub").getValue().toString();
                    String poster = dataSnapshot.child("posterPhim").getValue().toString();
                    String mota = dataSnapshot.child("motaPhim").getValue().toString();
                    String theloai = dataSnapshot.child("theloaiPhim").getValue().toString();
                    String dienvien = dataSnapshot.child("dienvienPhim").getValue().toString();

                    edtID.setText(id);
                    edtTen.setText(ten);
                    edtLink.setText(link);
                    edtSub.setText(sub);
                    edtPoster.setText(poster);
                    edtDes.setText(mota);
                    edtCate.setText(theloai);
                    edtActor.setText(dienvien);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void InitUI() {
        edtID = (EditText) findViewById(R.id.edtID);
        edtTen = (EditText) findViewById(R.id.edtTen);
        edtLink = (EditText) findViewById(R.id.edtLink);
        edtSub = (EditText) findViewById(R.id.edtLinkSub);
        edtPoster = (EditText) findViewById(R.id.edtPoster);
        edtCate = (EditText) findViewById(R.id.edtTheLoai);
        edtDes = (EditText) findViewById(R.id.edtMota);
        edtActor = (EditText) findViewById(R.id.edtDienVien);
    }

    public void clickBackToAdmin(View view) {
        Intent intent = new Intent(Phim_InfoActivity.this, QL_PhimActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickClear(View view) {
        edtID.setText("");
        edtTen.setText("");
        edtLink.setText("");
        edtSub.setText("");
        edtPoster.setText("");
        edtDes.setText("");
        edtCate.setText("");
        edtActor.setText("");
    }

    public void clickSavePhim(View view) {


        String name = edtTen.getText().toString().trim();
        String link = edtLink.getText().toString().trim();
        String sub = edtSub.getText().toString().trim();
        String poster = edtPoster.getText().toString().trim();
        String cate = edtCate.getText().toString().trim();
        String des = edtDes.getText().toString().trim();
        String actor = edtActor.getText().toString().trim();


        mData.child("tenPhim").setValue(name);
        mData.child("linkPhim").setValue(link);
        mData.child("linksub").setValue(sub);
        mData.child("posterPhim").setValue(poster);
        mData.child("motaPhim").setValue(des);
        mData.child("theloaiPhim").setValue(cate);
        mData.child("dienvienPhim").setValue(actor);

        Toast.makeText(this, "Cập Nhật Thông Tin Phim Thành Công", Toast.LENGTH_SHORT).show();

    }
}
