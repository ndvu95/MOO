package com.example.vu.morningofowl.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.model.Phim;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Adapter_Detail_Base_By_Cate extends ArrayAdapter<Phim> {
    public List<Phim> arrayList;
    public LayoutInflater inflater;
    public Context context;

    public Adapter_Detail_Base_By_Cate(@NonNull Context context, int resource, @NonNull List<Phim> objects) {
        super(context, resource, objects);
        this.arrayList = objects;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.grid_single_item,parent, false);
            holder.imgHinh = (ImageView)convertView.findViewById(R.id.imgAnh);
            holder.tvTenPhim = (TextView)convertView.findViewById(R.id.tvTenPhimAdmin);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        Uri uri = Uri.parse(arrayList.get(position).getPosterPhim());


        Picasso.with(getContext())
                .load(uri)
                .into(holder.imgHinh);

        holder.tvTenPhim.setText(arrayList.get(position).getTenPhim());



        return convertView;
    }

    public static class ViewHolder{
        public ImageView imgHinh;
        public TextView tvTenPhim;
    }
}
