package com.example.vu.morningofowl.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.Adapter_WatchLater;
import com.example.vu.morningofowl.model.Phim;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Watch_Later_Activity extends AppCompatActivity {
    private GridView gvWatchLater;
    ArrayList<Phim> arrayList;
    Adapter_WatchLater adapter;
    DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch__later_);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray_dark));
        gvWatchLater = (GridView) findViewById(R.id.gvWatchLater);
        arrayList = new ArrayList<>();
        adapter = new Adapter_WatchLater(this, R.layout.custom_grid_item_wl, arrayList);
        gvWatchLater.setAdapter(adapter);
        gvWatchLater.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = arrayList.get(position).getIdPhim();
                Intent intent = new Intent(Watch_Later_Activity.this, DetailActivity.class);
                intent.putExtra("phim_UID",key);
                startActivity(intent);
            }
        });
        fillData();
    }

    private void fillData() {
        String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Users").child(userID).child("Watch_Later").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    final String idP = ds.getKey();

                    mData.child("Phim").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                String idPhim = ds.child("idPhim").getValue().toString();
                                String tenPhim = ds.child("tenPhim").getValue().toString();
                                String linkPhim = ds.child("linkPhim").getValue().toString();
                                String linkSub = ds.child("linksub").getValue().toString();
                                String posterPhim = ds.child("posterPhim").getValue().toString();
                                String theloaiPhim = ds.child("theloaiPhim").getValue().toString();
                                String motaPhim = ds.child("motaPhim").getValue().toString();
                                String dienvienPhim = ds.child("dienvienPhim").getValue().toString();
                                String luotxem = ds.child("soluotXem").getValue().toString();

                                if (idPhim.equals(idP)) {
                                    arrayList.add(new Phim(idPhim, tenPhim, linkPhim, linkSub, posterPhim, theloaiPhim, motaPhim, dienvienPhim, Long.parseLong(luotxem)));
                                    adapter.notifyDataSetChanged();
                                }
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
        });
    }

    public void clickBacktoMore(View view) {
        super.onBackPressed();
    }
}
