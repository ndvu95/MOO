package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    TextView tvSearch;
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
        tvSearch  = (TextView)findViewById(R.id.tvSearch);
        Intent intent = getIntent();
        String content = intent.getStringExtra("SearchQuery");
        tvSearch.setText("Kết Quả Tìm Kiếm Cho: "+ content);
        listPhim = new ArrayList<>();
        adapterSearch = new AdapterSearch(this, R.layout.custom_gridview_item_search, listPhim);
        gvSearch.setAdapter(adapterSearch);

        fillData(content);

        gvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent manhinhDetail = new Intent(Search_Activity.this, DetailActivity.class);
                String key = listPhim.get(position).getIdPhim();
                for (int i = 0; i < listPhim.size(); i++) {
                    manhinhDetail.putExtra("phim_UID", key);
                }
                startActivity(manhinhDetail);
            }
        });

    }
    private void fillData(String q){
        mData = FirebaseDatabase.getInstance().getReference("Phim");

        Query query = mData.orderByChild("tenPhim")
                .startAt(q)
                .endAt(q+"\uf8ff");
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
                    Long luotxem = (Long) snapshot.child("soluotXem").getValue();


                    listPhim.add(new Phim(idPhim, tenPhim, linkPhim, linkSub, posterPhim, theloaiPhim, motaPhim, dienvienPhim, luotxem));
                    adapterSearch.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void clickcloseSearch(View view) {
        super.onBackPressed();
    }
}
