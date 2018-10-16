package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vu.morningofowl.R;

public class Start_Activity extends AppCompatActivity {
    private TextView tvReg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_);
        tvReg = (TextView)findViewById(R.id.tvReg);

        SpannableString ss = new SpannableString("Bạn chưa có tài khoản? Đăng Ký");

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent intent = new Intent(Start_Activity.this,Reg_Activity.class);
                startActivity(intent);
            }
        };
        ss.setSpan(clickableSpan,23,30,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.owl)),23,30,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvReg.setText(ss);
        tvReg.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public void clickLoginWithEmail(View view) {
        Intent intent = new Intent(Start_Activity.this, LoginEmail_Activity.class);
        startActivity(intent);
    }
}
