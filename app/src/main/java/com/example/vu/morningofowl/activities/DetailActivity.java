package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.model.Phim;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private ImageView imgPoster;
    private TextView tvTenPhim;
    private TextView tvTheLoai;
    private TextView tvDienVien;
    private TextView tvViews;
    private ExpandableTextView expandableTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        khoiTao();


        Intent intent = getIntent();
        Phim phim = (Phim) intent.getSerializableExtra("dulieu");
        Picasso.with(getBaseContext()).load(Uri.parse(phim.getPosterPhim()))
                .placeholder(R.mipmap.ic_launcher_round)
                .into(imgPoster);
        tvTenPhim.setText(phim.getTenPhim().trim());
        tvTheLoai.setText("Thể Loại: " + phim.getTheloaiPhim());
        tvDienVien.setText("Diễn Viên: " + phim.getDienvienPhim());
        expandableTextView.setText(phim.getMotaPhim());
        tvViews.setText("Views: " + phim.getSoluotXem());


    }

    public void khoiTao() {
        imgPoster = (ImageView) findViewById(R.id.imgPoster);
        tvTenPhim = (TextView) findViewById(R.id.tvName);
        tvTheLoai = (TextView) findViewById(R.id.tvTheLoai);
        tvDienVien = (TextView) findViewById(R.id.tvDienVien);
        tvViews = (TextView) findViewById(R.id.tvSoLuotXem);
        expandableTextView = (ExpandableTextView)findViewById(R.id.expandable_textView);
    }

    public void clickplayPhim(View view) {
        Intent intent = getIntent();
        Phim phim = (Phim) intent.getSerializableExtra("dulieu");
        Intent manhinhXemPhim = new Intent(this, ExoPlayerActivity.class);
        String linkPhim = phim.getLinkPhim();
        manhinhXemPhim.putExtra("Link", linkPhim);
        startActivity(manhinhXemPhim);
    }
}

