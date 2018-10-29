package com.example.vu.morningofowl.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.model.Phim;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ManagerAddPhim_Activity extends AppCompatActivity {
    DatabaseReference mData;
    EditText edtTenPhim, edtLinkPhim, edtLinkSub, edtLinkPoster, edtTheLoai, edtMoTa, edtDienVien, edtLuotXem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_add_phim);
        initUI();
    }

    private void initUI() {
        edtTenPhim = (EditText) findViewById(R.id.edtTen);
        edtLinkPhim = (EditText) findViewById(R.id.edtLink);
        edtLinkSub = (EditText) findViewById(R.id.edtLinkSub);
        edtLinkPoster = (EditText) findViewById(R.id.edtPoster);
        edtTheLoai = (EditText) findViewById(R.id.edtTheLoai);
        edtMoTa = (EditText) findViewById(R.id.edtMota);
        edtDienVien = (EditText) findViewById(R.id.edtDienVien);

    }

    public void clickUp(View view) {
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Đang xử lý dữ liệu...");
        pd.show();
        themPhim();
        pd.dismiss();
    }

    private void themPhim() {

        String tenPhim = edtTenPhim.getText().toString().trim();
        String linkPhim = edtLinkPhim.getText().toString().trim();
        String linkSub = edtLinkSub.getText().toString().trim();
        String linkPoster = edtLinkPoster.getText().toString().trim();
        String theLoai = edtTheLoai.getText().toString().trim();
        String moTa = edtMoTa.getText().toString().trim();
        String dienVien = edtDienVien.getText().toString().trim();
        Long luotXem = Long.parseLong("1");


        mData = FirebaseDatabase.getInstance().getReference("Phim");
        mData.push();
        String idPhim = mData.push().getKey();
        Phim phim = new Phim(idPhim, tenPhim, linkPhim, linkSub, linkPoster, theLoai, moTa, dienVien, luotXem);


        mData.child(idPhim).setValue(phim).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    clearText();
                    Toast.makeText(ManagerAddPhim_Activity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearText() {
        edtTenPhim.setText("");
        edtLinkPhim.setText("");
        edtLinkSub.setText("");
        edtLinkPoster.setText("");
        edtTheLoai.setText("");
        edtMoTa.setText("");
        edtDienVien.setText("");

    }

    public void clickClear(View view) {
        clearText();
    }

    public void clickBackToMain(View view) {
        Intent intent = new Intent(ManagerAddPhim_Activity.this, Home_Activity.class);
        startActivity(intent);
    }
}
