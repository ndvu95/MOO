package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.database.Cursor;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;


import android.os.Bundle;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import android.widget.TextView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.Phim_Adapter;
import com.example.vu.morningofowl.model.Phim;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {
    android.support.v4.content.CursorLoader cursorLoader;
    private ListView lvPhim;
    private ArrayList<Phim> arrayList;
    private Phim_Adapter adapter;

    private ImageView imgHome;
    private ImageView imgFeed;
    private ImageView imgKidz;
    private ImageView imgLive;
    private ImageView imgMore;

    private TextView tvHome;
    private TextView tvFeed;
    private TextView tvKidz;
    private TextView tvLive;
    private TextView tvMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Init();

        getSupportLoaderManager().initLoader(1,null,this);

        lvPhim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               final Phim phim = arrayList.get(position);
                Intent manhinhDetail = new Intent(MainActivity.this, DetailActivity.class);
                for(int i=0; i< arrayList.size(); i++){
                    manhinhDetail.putExtra("dulieu", (Serializable)phim);
                }
                startActivity(manhinhDetail);
            }
        });
    }


    public void clearClick(){
        imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home));
        imgFeed.setImageDrawable(getResources().getDrawable(R.drawable.feed));
        imgKidz.setImageDrawable(getResources().getDrawable(R.drawable.kid));
        //imgLive.setImageDrawable(getResources().getDrawable(R.drawable.livestream));
        //imgMore.setImageDrawable(getResources().getDrawable(R.drawable.menu));

        tvHome.setTextColor(getResources().getColor(R.color.notselected));
        tvFeed.setTextColor(getResources().getColor(R.color.notselected));
        tvKidz.setTextColor(getResources().getColor(R.color.notselected));
        tvLive.setTextColor(getResources().getColor(R.color.notselected));
        tvMore.setTextColor(getResources().getColor(R.color.notselected));
    }
    public void Init(){
        lvPhim = (ListView)findViewById(R.id.lvPhim);
        imgHome = (ImageView)findViewById(R.id.imgHome);
        imgFeed = (ImageView)findViewById(R.id.imgFeed);
        imgKidz = (ImageView)findViewById(R.id.imgKid);
        imgLive = (ImageView)findViewById(R.id.imgLive);
        imgMore= (ImageView)findViewById(R.id.imgMore);

        tvHome = (TextView)findViewById(R.id.tvHome);
        tvFeed = (TextView)findViewById(R.id.tvFeed);
        tvKidz = (TextView)findViewById(R.id.tvKid);
        tvLive = (TextView)findViewById(R.id.tvLive);
        tvMore = (TextView)findViewById(R.id.tvMore);
    }
    public void clickMenu(View view){
            switch (view.getId()){
                case R.id.imgHome:
                    clearClick();
                    //imgHome.setImageDrawable(getResources().getDrawable(R.drawable.home_selected));
                    tvHome.setTextColor(getResources().getColor(R.color.selected));
                    break;
                case R.id.imgFeed:
                    clearClick();
                    //imgFeed.setImageDrawable(getResources().getDrawable(R.drawable.feed_selected));
                    tvFeed.setTextColor(getResources().getColor(R.color.selected));
                    break;
                case R.id.imgKid:
                    clearClick();
                   // imgKidz.setImageDrawable(getResources().getDrawable(R.drawable.kid_selected));
                    tvKidz.setTextColor(getResources().getColor(R.color.selected));
                    break;
                case R.id.imgLive:
                    clearClick();
                    //imgLive.setImageDrawable(getResources().getDrawable(R.drawable.livestream_selected));
                    tvLive.setTextColor(getResources().getColor(R.color.selected));
                    break;
                case R.id.imgMore:
                    clearClick();
                   // imgMore.setImageDrawable(getResources().getDrawable(R.drawable.menu_selected));
                    tvMore.setTextColor(getResources().getColor(R.color.selected));
                    break;
            }
    }

    @NonNull
    @Override
    public android.support.v4.content.Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
       cursorLoader = new android.support.v4.content.CursorLoader(this, Uri.parse("content://vund.itplus.vn.appql.DataBase/cte"), null, null, null, null);
        return cursorLoader;

    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<Cursor> loader, Cursor data) {
        arrayList = new ArrayList<>();
        adapter = new Phim_Adapter(MainActivity.this, R.layout.dong_layout,arrayList);
        lvPhim.setAdapter(adapter);
        data.moveToFirst();
        String ten1 = data.getString(1);
        String link1 = data.getString(2);
        String poster1= data.getString(3);
        String theloai1= data.getString(4);
        String mota1 = data.getString(5);
        String dienvien1= data.getString(6);
        int views1 = data.getInt(7);
        arrayList.add(new Phim(ten1,link1,"",poster1,theloai1,mota1,dienvien1,views1));
        while(data.moveToNext()){
            String ten = data.getString(1);
            String link = data.getString(2);
            String poster= data.getString(3);
            String theloai= data.getString(4);
            String mota = data.getString(5);
            String dienvien= data.getString(6);
            int views = data.getInt(7);
            arrayList.add(new Phim(ten,link,"",poster,theloai,mota,dienvien,views));
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<Cursor> loader) {

    }
}
