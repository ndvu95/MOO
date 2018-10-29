package com.example.vu.morningofowl.fragments;

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
import java.util.List;
import java.util.Map;

import me.relex.circleindicator.CircleIndicator;

public class Home_Fragment extends Fragment {

    DatabaseReference mData;
    View view;
    ViewPager viewPager;
    CircleIndicator circleIndicator;
    Banner_Adapter adapter;
    ArrayList<QuangCao> arrayList;
    List<String> listKey;
    Runnable runnable;
    Handler handler;

    int currentItem;
    ArrayList<SectionDataPhim> allSampleData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


            view = inflater.inflate(R.layout.fragment_home, container, false);
            viewPager = (ViewPager) view.findViewById(R.id.viewPager);

            circleIndicator = (CircleIndicator) view.findViewById(R.id.indicatorDefault);
            arrayList = new ArrayList<>();
            listKey = new ArrayList<>();
            adapter = new Banner_Adapter(getActivity(), arrayList);
            viewPager.setAdapter(adapter);
            circleIndicator.setViewPager(viewPager);
            adapter.registerDataSetObserver(circleIndicator.getDataSetObserver());



            allSampleData = new ArrayList<SectionDataPhim>();
            RecyclerView myRecyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
            myRecyclerView.setHasFixedSize(true);
            Recyclerview_Data_Adapter adapter1 = new Recyclerview_Data_Adapter(allSampleData, view.getContext());
            myRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false));
            myRecyclerView.setAdapter(adapter1);


            getDataQuangCao();
            initPhim("Hoạt Hình");
            initPhim("Hành Động");
            //testData();



        return view;
    }

    private void initPhim(String theloai) {
        final SectionDataPhim dm = new SectionDataPhim();

        dm.setHeaderTitle(theloai);


        final DatabaseReference mDataPhim;
        mDataPhim = FirebaseDatabase.getInstance().getReference("Phim");
        Query query = mDataPhim.orderByChild("theloaiPhim").equalTo(theloai);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            ArrayList<Phim> singleItem = new ArrayList<Phim>();

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
                    Long luotxem = (Long) snapshot.child("soluotXem").getValue();


                    singleItem.add(new Phim(idPhim, tenPhim, linkPhim, linkSub, posterPhim, theloaiPhim, motaPhim, dienvienPhim, luotxem));
                    dm.setAllPhimSections(singleItem);
                }
                allSampleData.add(dm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void testData() {
        SectionDataPhim dm = new SectionDataPhim();

        dm.setHeaderTitle("Hot ");

        ArrayList<Phim> singleItem = new ArrayList<Phim>();

        for (int i = 0; i < 5; i++) {
            singleItem.add(new Phim("-LPApVA6QjeVUWPrKdMu", "Minions", "https://moowlcom.000webhostapp.com/Minions%20Official%20Trailer%201%20(2015)%20-%20Despicable%20Me%20Prequel%20HD.mp4",
                    "https://moowlcom.000webhostapp.com/Minions%20Official%20Trailer%201%20(2015)%20-%20Despicable%20Me%20Prequel%20HD.mp4",
                    "https://c2.staticflickr.com/2/1888/42471882670_c03e961a2c_m.jpg",
                    "Hoạt Hình",
                    "Câu chuyện kể về các MINION ",
                    "Steve Carell, Kristen Wiig,Trey Parker", 1));

            dm.setAllPhimSections(singleItem);
        }

        allSampleData.add(dm);
    }


    private void getDataQuangCao() {
        mData = FirebaseDatabase.getInstance().getReference("QuangCao");
        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final String key = dataSnapshot.getKey().toString();
                mData.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                        String id_Phim = map.get("idPhim");
                        String link_Anh = map.get("linkAnh");

                        listKey.add(key);
                        arrayList.add(new QuangCao(link_Anh, id_Phim));
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
