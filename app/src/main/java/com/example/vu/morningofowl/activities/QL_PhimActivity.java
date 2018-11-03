package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.Adapter_Phim_Admin;
import com.example.vu.morningofowl.model.Phim;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QL_PhimActivity extends AppCompatActivity {
    ArrayList<Phim> arrayList;
    Adapter_Phim_Admin adapter;
    DatabaseReference mData;
    GridView gvPhim;
    SearchView searchView;
    FloatingActionButton btnAdd;
    TextView tvDS;
    boolean searchState = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql__phim);

        InitUI();

        arrayList = new ArrayList<>();
        adapter = new Adapter_Phim_Admin(this, R.layout.grid_single_item, arrayList);
        gvPhim.setAdapter(adapter);
        readData();
        

        searchData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QL_PhimActivity.this, ManagerAddPhim_Activity.class);
                startActivity(intent);
            }
        });
    }

    private void searchData() {

    }

    private void readData() {
        mData = FirebaseDatabase.getInstance().getReference("Phim");
        mData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Phim phim = ds.getValue(Phim.class);
                    arrayList.add(phim);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void InitUI() {
        tvDS = (TextView)findViewById(R.id.tvDS);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAddPhim);
        gvPhim = (GridView) findViewById(R.id.gridListPhim);
        searchView = (SearchView) findViewById(R.id.searchPhimAdmin);
    }
}
