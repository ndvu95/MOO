package com.example.vu.morningofowl.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.activities.DetailActivity;
import com.example.vu.morningofowl.activities.ExoPlayerActivity;
import com.example.vu.morningofowl.model.Phim;
import com.example.vu.morningofowl.model.QuangCao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Banner_Adapter extends PagerAdapter {
    ArrayList<QuangCao> arrayListBanner;
    Context context;
    DatabaseReference mData;
    FirebaseUser user;
    ImageView imgAddPlaylist;
    List<String> listKey;
    String phim_UID;
    String tenphim;
    Long luotxem;
    private boolean mProcessWatchLater = false;

    public Banner_Adapter(Context context, ArrayList<QuangCao> arrayListBanner) {
        this.context = context;
        this.arrayListBanner = arrayListBanner;
    }

    @Override
    public int getCount() {
        return arrayListBanner.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        mData = FirebaseDatabase.getInstance().getReference();
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.banner_item, null);
        imgAddPlaylist = (ImageView) view.findViewById(R.id.imgAddToPlaylist);
        ImageView img = (ImageView) view.findViewById(R.id.imgBackGroundBanner);
        ImageView imgPlay = (ImageView) view.findViewById(R.id.imgPlay);


        Picasso.with(context)
                .load(Uri.parse(arrayListBanner.get(position).getLinkAnh()))
                .into(img);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = arrayListBanner.get(position).getIdPhim();
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("phim_UID", key);
                Log.d("KEYYYYY", "" + key);
                context.startActivity(intent);
            }
        });


        imgAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String idPhim = arrayListBanner.get(position).getIdPhim();
                mProcessWatchLater = true;

                Intent intent = new Intent(context.getApplicationContext(), DetailActivity.class);
                intent.putExtra("phim_UID", idPhim);
                context.startActivity(intent);
            }
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String key = arrayListBanner.get(position).getIdPhim();
                mData = FirebaseDatabase.getInstance().getReference("Phim");
                mData.child(key).child("soluotXem").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String view = dataSnapshot.getValue().toString();
                        luotxem = Long.parseLong(view);
                        update_luotXem(key);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });





                mData.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String,String> map = (Map<String,String>)dataSnapshot.getValue();
                        String link = map.get("linkPhim");
                        String sub = map.get("linksub");


                        Intent intent = new Intent(context, ExoPlayerActivity.class);
                        intent.putExtra("Link", link);
                        intent.putExtra("Link_Sub", sub);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        return view;
    }

    public void update_luotXem(String id) {
        Long clicked = (luotxem +1);

        FirebaseDatabase.getInstance().getReference("Phim").
                child(id).
                child("soluotXem").
                setValue(clicked);

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void getData() {
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

                        phim_UID = id_Phim;
                        listKey.add(id_Phim);
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
