package com.example.vu.morningofowl.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginEmail_Activity extends AppCompatActivity {
    EditText edtEmailLogin, edtPasswordLogin;
    CheckBox cbRememberMe;
    TextView tvForgotPassword;
    ProgressDialog pd;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email_);
        edtEmailLogin = (EditText) findViewById(R.id.edtEmailLogin);
        edtPasswordLogin = (EditText) findViewById(R.id.edtMatKhauLogin);
        cbRememberMe = (CheckBox) findViewById(R.id.cbRememberMe);
        tvForgotPassword = (TextView) findViewById(R.id.tvQuenMatKhau);
        doimau();

    }

    private void doimau() {
        SpannableString ss = new SpannableString("Quên Mật Khẩu? Nhấn Vào Đây");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(LoginEmail_Activity.this, ForgotPassword_Activity.class);
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpan, 15, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.owl)), 15, 27, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvForgotPassword.setText(ss);
        tvForgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void clickLogin(View view) {
        showProgressDialog();
        Login();
    }

    private void Login() {
        mAuth = FirebaseAuth.getInstance();
        String Email = edtEmailLogin.getText().toString().trim();
        String Password = edtPasswordLogin.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginEmail_Activity.this, Home_Activity.class);
                            closeProgressDialog();
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginEmail_Activity.this, "Tài Khoản Hoặc Mật Khẩu Không Đúng.", Toast.LENGTH_SHORT).show();
                            closeProgressDialog();
                        }

                    }
                });


    }

    public void clickBackToStart1(View view) {
        Intent intent = new Intent(LoginEmail_Activity.this, Start_Activity.class);
        startActivity(intent);
    }

    public void showProgressDialog() {
        try {
            if (pd == null) {
                pd = ProgressDialog.show(this, "Loading", "Đang Xử Lý Dữ Liệu...", true, true);
            }
        } catch (Exception e) {
            Log.e("Error", "" + e.getMessage());
        }
    }

    public void closeProgressDialog() {
        try {
            if (pd != null) {
                pd.dismiss();
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
    }
}
