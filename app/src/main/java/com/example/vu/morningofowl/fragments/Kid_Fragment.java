package com.example.vu.morningofowl.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vu.morningofowl.activities.DetailActivity;
import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.Phim_Adapter;
import com.example.vu.morningofowl.model.Phim;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Kid_Fragment extends android.support.v4.app.Fragment {
    DatabaseReference mData;
    private GridView gridviewKid;
    private ArrayList<Phim> arrayList;
    private Phim_Adapter adapter;
    private List<String> listKey;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kid, container, false);
        gridviewKid = (GridView) view.findViewById(R.id.gvKidz);
        arrayList = new ArrayList<>();
        listKey = new ArrayList<>();
        adapter = new Phim_Adapter(getContext(), R.layout.custom_grid_item, arrayList);

        gridviewKid.setAdapter(adapter);
        readData();


        gridviewKid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Phim phim = arrayList.get(position);
                Intent manhinhDetail = new Intent(getActivity(), DetailActivity.class);
                for (int i = 0; i < arrayList.size(); i++) {
                    manhinhDetail.putExtra("dulieu", (Serializable) phim);
                }
                String key = listKey.get(position);
                for (int i = 0; i < listKey.size(); i++) {
                    manhinhDetail.putExtra("phim_UID",key);
                }
                startActivity(manhinhDetail);
            }
        });

        return view;
    }



    public void readData() {
        mData = FirebaseDatabase.getInstance().getReference("Phim");

        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final String key = dataSnapshot.getKey().toString();

                mData.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                        Map<Long, Long> mapViews = (Map<Long, Long>) dataSnapshot.getValue();
                        String tenphim = map.get("tenPhim");
                        String linkphim = map.get("linkPhim");
                        String linksub = map.get("linksub");
                        String posterphim = map.get("posterPhim");
                        String motaphim = map.get("motaPhim");
                        String theloai = map.get("theloaiPhim");
                        String dienvien = map.get("dienvienPhim");
                        Long luotxem = mapViews.get("soluotXem");

                        listKey.add(key);
                        arrayList.add(new Phim(tenphim, linkphim, linksub, posterphim, theloai, motaphim, dienvien, luotxem));
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
