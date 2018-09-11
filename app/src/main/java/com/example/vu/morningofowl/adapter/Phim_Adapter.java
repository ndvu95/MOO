package com.example.vu.morningofowl.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Rating;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.model.Phim;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Phim_Adapter extends ArrayAdapter<Phim> {
    private List<Phim> phimList;
    private Context context;
    private LayoutInflater layoutInflater;

    public Phim_Adapter(@NonNull Context context, int resource, @NonNull List<Phim> objects) {
        super(context, resource, objects);
        this.phimList = objects;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.dong_layout, parent, false);
            holder.imgHinh = (ImageView) convertView.findViewById(R.id.hinhPhim);
            holder.tvName = (TextView) convertView.findViewById(R.id.tvtenPhim);
            holder.tvCategory = (TextView) convertView.findViewById(R.id.tvtheLoai);
            holder.tvViews = (TextView) convertView.findViewById(R.id.tvLuotXem);
            holder.ratingBar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final Phim phim = phimList.get(position);

        new LoadImageFromInternet(holder.imgHinh).execute(phim.getPosterPhim());
        holder.tvName.setText(phim.getTenPhim());
        holder.tvCategory.setText(phim.getTheloaiPhim());
        holder.tvViews.setText(phim.getSoluotXem());
        holder.ratingBar.setRating(phim.getRatingStar());
        return convertView;
    }

    public static class ViewHolder {
        public ImageView imgHinh;
        public TextView tvName;
        public TextView tvCategory;
        public TextView tvViews;
        public RatingBar ratingBar;
    }

    private class LoadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        Bitmap bitmapHinh = null;
        ViewHolder holder;
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

//
}
