package com.example.vu.morningofowl.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.view.Window;
import android.widget.ListView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.Adapter_QC;
import com.example.vu.morningofowl.model.QuangCao;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BannerManagerActivity extends AppCompatActivity {
    ListView listBanner;
    ArrayList<QuangCao> arrayList;
    Adapter_QC adapter;
    FloatingActionButton btnAdd;
    DatabaseReference mData;

    SearchView searchBanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_manager);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray_dark));
        InitUI();

        arrayList = new ArrayList<>();
        adapter = new Adapter_QC(BannerManagerActivity.this, R.layout.banner_list_item, arrayList);
        listBanner.setAdapter(adapter);


        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("QuangCao").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    QuangCao quangCao = ds.getValue(QuangCao.class);
                    arrayList.add(quangCao);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        searchBanner.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchBanner(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchBanner(newText);
                return false;
            }
        });
    }


    private void searchBanner(String q){
        mData = FirebaseDatabase.getInstance().getReference("Phim");
        Query query = mData.orderByChild("tenPhim").startAt(q).endAt(q+"\uf8ff");


        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    final String idPhim = ds.child("idPhim").getValue().toString();
                    final String linkanh = ds.child("posterPhim").getValue().toString();
                    DatabaseReference mDataCheck = FirebaseDatabase.getInstance().getReference("QuangCao");
                    mDataCheck.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                String idphim_QC = ds.child("idPhim").getValue().toString();
                                QuangCao qc = ds.getValue(QuangCao.class);
                                if(idPhim.equals(idphim_QC)){
                                    arrayList.add(qc);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueEventListener);
    }


    private void InitUI() {
        listBanner = (ListView)findViewById(R.id.lvBanner);
        btnAdd = (FloatingActionButton)findViewById(R.id.btnAddBanner);
        searchBanner = (SearchView)findViewById(R.id.searchBanner);
    }
    public void clickBackToAdmin1(View view) {
        super.onBackPressed();
    }
}
