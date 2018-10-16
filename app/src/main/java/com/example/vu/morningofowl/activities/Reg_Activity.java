package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Reg_Activity extends AppCompatActivity {
    private EditText edtHoTen, edtEmail, edtMatKhau, edtSDT;
    private ProgressBar progressBar;
    public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_);
        edtHoTen = (EditText)findViewById(R.id.edtHoTen);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtMatKhau = (EditText)findViewById(R.id.edtMatKhau);
        edtSDT = (EditText)findViewById(R.id.edtSDT);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){

        }
    }
    private void DangKy(){
        final String hoTen = edtHoTen.getText().toString().trim();
        final String eMail = edtEmail.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();
        final String SDT = edtSDT.getText().toString().trim();

        if(hoTen.isEmpty()){
            edtHoTen.setError("Bạn Chưa Nhập Họ Tên");
            edtHoTen.requestFocus();
            return;
        }
        if(eMail.isEmpty()){
            edtEmail.setError("Bạn Chưa Nhập Email");
            edtEmail.requestFocus();
            return;
        }
        if(matKhau.isEmpty()){
            edtMatKhau.setError("Bạn Chưa Nhập Mật Khẩu");
            edtMatKhau.requestFocus();
            return;
        }
        if(SDT.length() != 10){
            edtSDT.setError("Số Điện Thoại Phải Có 10 Số");
            edtSDT.requestFocus();
            return;
        }

      mAuth.createUserWithEmailAndPassword(eMail,matKhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {

            if(task.isSuccessful()){
                Users users = new Users(hoTen,eMail,SDT);
                FirebaseDatabase.getInstance()
                        .getReference("Users")
                        .child(FirebaseAuth.getInstance()
                                .getCurrentUser()
                                .getUid())
                        .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Reg_Activity.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Reg_Activity.this,Home_Activity.class);
                            startActivity(intent);
                        }else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Reg_Activity.this, "Có Lỗi Xảy Ra, Vui Lòng Thử Lại", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                Toast.makeText(Reg_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
      });

    }

    public void clickBackToStart(View view) {
        Intent intent = new Intent(Reg_Activity.this,Start_Activity.class);
        startActivity(intent);
    }

    public void clickReg(View view) {
        progressBar.setVisibility(View.VISIBLE);
        DangKy();
    }
}
