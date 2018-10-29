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
import com.example.vu.morningofowl.model.QuangCao;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Banner_Adapter extends PagerAdapter {
    ArrayList<QuangCao> arrayListBanner;
    Context context;
    DatabaseReference mData;
    ImageView imgAddPlaylist;
    List<String> listKey;
    String phim_UID;

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
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.banner_item,null);
        imgAddPlaylist = (ImageView)view.findViewById(R.id.imgAddToPlaylist);
        ImageView img =(ImageView)view.findViewById(R.id.imgBackGroundBanner);
        ImageView imgPlay = (ImageView)view.findViewById(R.id.imgPlay);


        Picasso.with(context)
                .load(Uri.parse(arrayListBanner.get(position).getLinkAnh()))
                .into(img);
        container.addView(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = arrayListBanner.get(position).getIdPhim();
                Intent intent = new Intent(context,DetailActivity.class);
                intent.putExtra("phim_UID",key);
                context.startActivity(intent);
            }
        });


        imgAddPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context.getApplicationContext(), "testtttt", Toast.LENGTH_SHORT).show();
            }
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = arrayListBanner.get(position).getIdPhim();
                mData= FirebaseDatabase.getInstance().getReference();
                mData.child("Phim").child(key).child("linkPhim").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String linkPhim = dataSnapshot.getValue().toString();
                        Intent intent = new Intent(context,ExoPlayerActivity.class);
                        intent.putExtra("Link",linkPhim);
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


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private void getData() {
        mData =FirebaseDatabase.getInstance().getReference("QuangCao");
        mData.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final String key = dataSnapshot.getKey().toString();
                mData.child(key).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Map<String,String> map =(Map<String,String>)dataSnapshot.getValue();
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
}}
