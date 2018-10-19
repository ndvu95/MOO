package com.example.vu.morningofowl.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.vu.morningofowl.activities.DetailActivity;
import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.Phim_Adapter;
import com.example.vu.morningofowl.model.Phim;

import java.io.Serializable;
import java.util.ArrayList;

public class Kid_Fragment extends android.support.v4.app.Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    android.support.v4.content.CursorLoader cursorLoader;
    private ListView lvKid;
    private ArrayList<Phim> arrayList;
    private Phim_Adapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kid,container,false);
        getLoaderManager().initLoader(1,null,this);
        lvKid = (ListView)view.findViewById(R.id.lvKidz);
        arrayList = new ArrayList<>();
        adapter = new Phim_Adapter(getContext(),R.layout.dong_layout,arrayList);
        lvKid.setAdapter(adapter);

        lvKid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Phim phim = arrayList.get(position);
                Intent manhinhDetail = new Intent(getActivity(), DetailActivity.class);
                for(int i=0; i< arrayList.size(); i++){
                    manhinhDetail.putExtra("dulieu", (Serializable)phim);
                }
                startActivity(manhinhDetail);
            }
        });

        return view;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        cursorLoader = new android.support.v4.content.CursorLoader(getContext(), Uri.parse("content://vund.itplus.vn.appql.DataBase/cte"), null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        arrayList = new ArrayList<>();
        adapter = new Phim_Adapter(getContext(),R.layout.dong_layout,arrayList);
        lvKid.setAdapter(adapter);
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
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
