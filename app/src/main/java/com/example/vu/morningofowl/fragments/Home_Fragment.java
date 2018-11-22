package com.example.vu.morningofowl.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.model.SectionDataPhim;
import com.example.vu.morningofowl.adapter.Banner_Adapter;
import com.example.vu.morningofowl.adapter.Recyclerview_Data_Adapter;
import com.example.vu.morningofowl.model.Phim;
import com.example.vu.morningofowl.model.QuangCao;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

public class Home_Fragment extends Fragment {

    DatabaseReference mData;
    DatabaseReference mDataCate;
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    Banner_Adapter adapter;
    ArrayList<QuangCao> arrayList;
    List<String> listKey;
    Runnable runnable;
    Handler handler;

    ArrayList<SectionDataPhim> allSampleData;


    int currentItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);

        circleIndicator = (CircleIndicator) view.findViewById(R.id.indicatorDefault);
        arrayList = new ArrayList<>();
        listKey = new ArrayList<>();
        adapter = new Banner_Adapter(getActivity(), arrayList);
        viewPager.setAdapter(adapter);
        circleIndicator.setViewPager(viewPager);
        adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        mDataCate = FirebaseDatabase.getInstance().getReference("TheLoai");


        getDataQuangCao();
        initPhim("Hoạt Hình");
        initPhim("Hành Động");
        initPhim("Chiến Tranh");
        initPhim("Hài");
        initPhim("Tình Cảm");



        return view;
    }

    private void initPhim(String theloai) {

        allSampleData = new ArrayList<>();
        RecyclerView myRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        myRecyclerView.setHasFixedSize(true);
        final Recyclerview_Data_Adapter adapter1 = new Recyclerview_Data_Adapter(view.getContext(), allSampleData);
        myRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
        myRecyclerView.setNestedScrollingEnabled(false);
        myRecyclerView.setAdapter(adapter1);


        final SectionDataPhim dm = new SectionDataPhim();

        dm.setHeaderTitle(theloai);


        final DatabaseReference mDataPhim;
        mDataPhim = FirebaseDatabase.getInstance().getReference("Phim");
        Query query = mDataPhim.orderByChild("theloaiPhim").equalTo(theloai);

        query.addValueEventListener(new ValueEventListener() {
            ArrayList<Phim> singleItem = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()&&dataSnapshot!= null) {
                    singleItem.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Phim phim = snapshot.getValue(Phim.class);
                        singleItem.add(phim);
                        Collections.sort(singleItem, new Comparator<Phim>() {
                            @Override
                            public int compare(Phim phim, Phim t1) {
                                return phim.getTenPhim().compareTo(t1.getTenPhim());
                            }
                        });
                    }
                    dm.setAllPhimSections(singleItem);
                    allSampleData.add(dm);
                    adapter1.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void getDataQuangCao() {
        mData = FirebaseDatabase.getInstance().getReference("QuangCao");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    arrayList.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String key = ds.getKey();
                        mData.child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                QuangCao qc = dataSnapshot.getValue(QuangCao.class);
                                arrayList.add(qc);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                currentItem = viewPager.getCurrentItem();
                currentItem++;
                if (currentItem >= viewPager.getAdapter().getCount()) {
                    currentItem = 0;
                }
                viewPager.setCurrentItem(currentItem, true);
                handler.postDelayed(runnable, 4500);
            }
        };
        handler.postDelayed(runnable, 4500);
    }


}
