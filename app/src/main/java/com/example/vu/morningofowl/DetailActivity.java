package com.example.vu.morningofowl;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vu.morningofowl.adapter.Phim_Adapter;
import com.example.vu.morningofowl.model.Phim;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends Activity {
    private ImageView imgPoster;
    private TextView tvTenPhim;
    private TextView tvTheLoai;
    private TextView tvDienVien;
    private TextView tvViews;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        khoiTao();


        Intent intent = getIntent();
        Phim phim = (Phim) intent.getSerializableExtra("dulieu");

        new LoadImageFromInternet(imgPoster).execute(phim.getPosterPhim());

            tvTenPhim.setText(phim.getTenPhim().trim());
            tvTheLoai.setText(phim.getTheloaiPhim());
            tvDienVien.setText(phim.getDienvienPhim());
            tvViews.setText(phim.getSoluotXem());
            ratingBar.setRating(phim.getRatingStar());


    }
    public void khoiTao(){
        imgPoster = (ImageView)findViewById(R.id.imgPoster);
        tvTenPhim =(TextView)findViewById(R.id.tvName);
        tvTheLoai =(TextView)findViewById(R.id.tvTheLoai);
        tvDienVien =(TextView)findViewById(R.id.tvDienVien);
        tvViews=(TextView)findViewById(R.id.tvSoLuotXem);
        ratingBar = (RatingBar)findViewById(R.id.ratingBarDetail);
    }

    public void clickplayPhim(View view) {
        Intent intent = getIntent();
        Phim phim = (Phim) intent.getSerializableExtra("dulieu");
        Intent manhinhXemPhim = new Intent(this, ExoPlayerActivity.class);
        String linkPhim = phim.getLinkPhim();
        manhinhXemPhim.putExtra("Link", linkPhim );
        startActivity(manhinhXemPhim);
    }

    private class LoadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        Bitmap bitmapHinh = null;
        Phim_Adapter.ViewHolder holder;
        ImageView bmHinh;

        public LoadImageFromInternet(ImageView bmHinh) {
            this.bmHinh = bmHinh;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();

                bitmapHinh = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmapHinh;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            bmHinh.setImageBitmap(bitmap);
        }
    }
}
