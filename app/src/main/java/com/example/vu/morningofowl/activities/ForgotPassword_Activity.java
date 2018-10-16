package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword_Activity extends AppCompatActivity {
    EditText edtForgot;
    ProgressBar pgBar;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_);
        edtForgot = (EditText) findViewById(R.id.edtEmailForgot);
        pgBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void BackToStart(View view) {
        Intent intent = new Intent(ForgotPassword_Activity.this, Start_Activity.class);
        startActivity(intent);
    }

    public void clickReset(View view) {
        pgBar.setVisibility(View.VISIBLE);
        reset();
    }

    private void reset() {
        mAuth = FirebaseAuth.getInstance();
        String Email = edtForgot.getText().toString().trim();
        mAuth.sendPasswordResetEmail(Email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ForgotPassword_Activity.this, "Email đã được gửi, vui lòng xác nhận.", Toast.LENGTH_SHORT).show();
                        pgBar.setVisibility(View.GONE);
                    }
                });
    }
}
