package com.example.vu.morningofowl.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.RecyclerView;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Kid_Fragment extends android.support.v4.app.Fragment {
    DatabaseReference mData;
    View view;
    private GridView gridviewKid;
    private ArrayList<Phim> arrayList;
    private Phim_Adapter adapter;
    private RecyclerView rcRelated;
    private List<String> listKey;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

            view = inflater.inflate(R.layout.fragment_kid, container, false);
            gridviewKid = (GridView) view.findViewById(R.id.gvKidz);

            arrayList = new ArrayList<>();
            adapter = new Phim_Adapter(getContext(), R.layout.custom_grid_item, arrayList);
            gridviewKid.setAdapter(adapter);
            readData();


            gridviewKid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Intent manhinhDetail = new Intent(getActivity(), DetailActivity.class);
                    String key = arrayList.get(position).getIdPhim();
                    for (int i = 0; i < arrayList.size(); i++) {
                        manhinhDetail.putExtra("phim_UID", key);
                    }
                    startActivity(manhinhDetail);
                }
            });


        return view;
    }


    public void readData() {
        mData = FirebaseDatabase.getInstance().getReference("Phim");

        Query query = mData.orderByChild("theloaiPhim").equalTo("Hoạt Hình");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String idPhim = snapshot.child("idPhim").getValue().toString();
                    String tenPhim = snapshot.child("tenPhim").getValue().toString();
                    String linkPhim = snapshot.child("linkPhim").getValue().toString();
                    String linkSub = snapshot.child("linksub").getValue().toString();
                    String posterPhim = snapshot.child("posterPhim").getValue().toString();
                    String theloaiPhim = snapshot.child("theloaiPhim").getValue().toString();
                    String motaPhim = snapshot.child("motaPhim").getValue().toString();
                    String dienvienPhim = snapshot.child("dienvienPhim").getValue().toString();
                    String luotxem = snapshot.child("soluotXem").getValue().toString();


                    arrayList.add(new Phim(idPhim, tenPhim, linkPhim, linkSub, posterPhim, theloaiPhim, motaPhim, dienvienPhim, Long.parseLong(luotxem)));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
