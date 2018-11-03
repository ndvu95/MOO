package com.example.vu.morningofowl.activities;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.AdapterSearch;
import com.example.vu.morningofowl.model.Phim;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search_Activity extends AppCompatActivity {
    GridView gvSearch;
    SearchView tvSearch;
    AdapterSearch adapterSearch;
    ArrayList<Phim> listPhim;
    String searchtext;
    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray_dark));
        gvSearch = (GridView) findViewById(R.id.gvSearch);
        tvSearch = (SearchView) findViewById(R.id.tvSearch);
        Intent intent = getIntent();
        String content = intent.getStringExtra("SearchQuery");
        listPhim = new ArrayList<>();
        adapterSearch = new AdapterSearch(this, R.layout.custom_gridview_item_search, listPhim);
        gvSearch.setAdapter(adapterSearch);
        fillDataAll();





        tvSearch.setIconified(false);
        tvSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fillData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                fillData(newText);
                return false;
            }
        });

    }
    private void fillDataAll(){
        mData = FirebaseDatabase.getInstance().getReference("Phim");

        Query query = mData.orderByChild("tenPhim");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPhim.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Phim phim = snapshot.getValue(Phim.class);
                        listPhim.add(phim);
                    }
                    adapterSearch.notifyDataSetChanged();
                }
                gvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent manhinhDetail = new Intent(Search_Activity.this, DetailActivity.class);
                        String key = listPhim.get(position).getIdPhim();
                        manhinhDetail.putExtra("phim_UID", key);
                        startActivity(manhinhDetail);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueEventListener);
    }



    private void fillData(String q) {

        mData = FirebaseDatabase.getInstance().getReference("Phim");

        Query query = mData.orderByChild("tenPhim")
                .startAt(q)
                .endAt(q + "\uf8ff");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPhim.clear();
                if(dataSnapshot.exists()){
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        Phim phim = snapshot.getValue(Phim.class);
                        listPhim.add(phim);
                    }
                    adapterSearch.notifyDataSetChanged();
                }
                gvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent manhinhDetail = new Intent(Search_Activity.this, DetailActivity.class);
                        String key = listPhim.get(position).getIdPhim();
                        manhinhDetail.putExtra("phim_UID", key);
                        startActivity(manhinhDetail);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueEventListener);
    }

    public void clickcloseSearch(View view) {
        super.onBackPressed();
    }
}
