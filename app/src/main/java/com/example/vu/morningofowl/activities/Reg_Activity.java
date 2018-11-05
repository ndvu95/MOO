package com.example.vu.morningofowl.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Reg_Activity extends AppCompatActivity {
    private EditText edtHoTen, edtEmail, edtMatKhau, edtSDT;
    private ProgressBar progressBar;
    public FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray_dark));
        edtHoTen = (EditText) findViewById(R.id.edtHoTen);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtMatKhau = (EditText) findViewById(R.id.edtMatKhau);
        edtSDT = (EditText) findViewById(R.id.edtSDT);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) {

        }
    }

    private void DangKy() {
        final ProgressDialog pd = new ProgressDialog(Reg_Activity.this);
        pd.setTitle("Đang Xử Lý Dữ Liệu");
        pd.setMessage("Vui Lòng Chờ Trong Giây Lát...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        String eMail = edtEmail.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();


        mAuth.createUserWithEmailAndPassword(eMail, matKhau).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    String hoTen = edtHoTen.getText().toString().trim();
                    String eMail = edtEmail.getText().toString().trim();
                    String SDT = edtSDT.getText().toString().trim();
                    Users users = new Users(hoTen, eMail, SDT, "Default_Image","Default","Default");
                    FirebaseDatabase.getInstance()
                            .getReference("Users")
                            .child(FirebaseAuth.getInstance()
                                    .getCurrentUser()
                                    .getUid())
                            .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                pd.dismiss();
                                Toast.makeText(Reg_Activity.this, "Đăng Ký Thành Công", Toast.LENGTH_SHORT).show();
                                FirebaseUser user = mAuth.getCurrentUser();
                                user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Reg_Activity.this, "Vui Lòng Kích Hoạt Email", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(Reg_Activity.this, Start_Activity.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                                mAuth.signOut();
                            } else {
                                pd.dismiss();
                                Toast.makeText(Reg_Activity.this, "Có Lỗi Xảy Ra, Vui Lòng Thử Lại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    pd.dismiss();
                    Toast.makeText(Reg_Activity.this, "Email đã có người sử dụng, vui lòng chọn email khác", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void clickBackToStart(View view) {
        Intent intent = new Intent(Reg_Activity.this, Start_Activity.class);
        startActivity(intent);
    }

    public void clickReg(View view) {
        DangKy();
        kickHoat();
    }

    public void kickHoat() {


    }

}
