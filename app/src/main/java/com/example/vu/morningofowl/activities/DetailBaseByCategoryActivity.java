package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.Adapter_Detail_Base_By_Cate;
import com.example.vu.morningofowl.model.Phim;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DetailBaseByCategoryActivity extends AppCompatActivity {
    private SearchView searchView;
    private GridView gvDetailPhim;
    private TextView tvtenDS;

    private ArrayList<Phim> arrayList;
    private Adapter_Detail_Base_By_Cate adapter;
    private String tenTL;
    DatabaseReference mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_base_by_category);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Window w = this.getWindow();
        w.setStatusBarColor(ContextCompat.getColor(this, R.color.gray_dark));
        Intent intent = getIntent();
        tenTL = intent.getStringExtra("tenTL");

        initUI();
        tvtenDS.setText(tenTL);
        mData = FirebaseDatabase.getInstance().getReference("Phim");


        arrayList = new ArrayList<>();
        adapter = new Adapter_Detail_Base_By_Cate(DetailBaseByCategoryActivity.this,R.layout.grid_single_item,arrayList);
        gvDetailPhim.setAdapter(adapter);
        filldataPhim();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPhim(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("")){
                    filldataPhim();
                }else{
                    searchPhim(newText);
                }

                return false;
            }
        });


        gvDetailPhim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1= new Intent(DetailBaseByCategoryActivity.this, DetailActivity.class);
                String uid = arrayList.get(i).getIdPhim();
                intent1.putExtra("phim_UID",uid);
                startActivity(intent1);
            }
        });



    }

    private void initUI() {
        searchView = (SearchView)findViewById(R.id.searchPhimAdmin);
        gvDetailPhim = (GridView)findViewById(R.id.gridListPhim);
        tvtenDS = (TextView)findViewById(R.id.tvDS);
    }
    private void filldataPhim(){
        mData.orderByChild("theloaiPhim").equalTo(tenTL).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                if(dataSnapshot != null && dataSnapshot.exists()){
                    for(DataSnapshot ds:dataSnapshot.getChildren()){
                        Phim phim = ds.getValue(Phim.class);
                        arrayList.add(phim);
                    }
                    Collections.sort(arrayList, new Comparator<Phim>() {
                        @Override
                        public int compare(Phim phim, Phim t1) {
                            return phim.getTenPhim().compareTo(t1.getTenPhim());
                        }
                    });
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void searchPhim(final String tenPhim){
       Query query =  mData.orderByChild("tenPhim");

       ValueEventListener valueEventListener = new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               arrayList.clear();
               if(dataSnapshot != null && dataSnapshot.exists()){
                   for(DataSnapshot ds:dataSnapshot.getChildren()){
                       Phim phim = ds.getValue(Phim.class);
                       String tlPhim = phim.getTheloaiPhim();
                       String tenthuong = phim.getTenPhim().toLowerCase();
                       if(tlPhim.equals(tenTL) && tenthuong.contains(tenPhim)){
                           arrayList.add(phim);
                       }
                   }
                   adapter.notifyDataSetChanged();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       };


       query.addValueEventListener(valueEventListener);
    }

    public void clickBackToAdminn(View view) {
        finish();
    }
}
