package com.example.vu.morningofowl.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.Related_Adapter;
import com.example.vu.morningofowl.model.Phim;
import com.example.vu.morningofowl.model.Related_Phim;
import com.example.vu.morningofowl.model.SectionDataPhim;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import at.favre.lib.dali.Dali;


public class DetailActivity extends AppCompatActivity {
    private ImageView imgPoster;
    private TextView tvTenPhim;
    private TextView tvTheLoai;
    private TextView tvDienVien;
    private TextView tvViews;
    private ArrayList<Related_Phim> arrayList;
    private Related_Adapter adapter;
    private ExpandableTextView expandableTextView;
    private LinearLayout layout;
    private RecyclerView rcRelated;
    Context context;
    DatabaseReference mData = FirebaseDatabase.getInstance().getReference("Phim");

    String phimID;
    String link_Phim;
    String link_Anh;
    String theLoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow(); // in Activity's onCreate() for instance
        w.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        setContentView(R.layout.activity_detail);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window ww = getWindow(); // in Activity's onCreate() for instance
            ww.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        context = this;

        khoiTao();
        arrayList = new ArrayList<>();
        adapter = new Related_Adapter(arrayList, context);
        rcRelated.setAdapter(adapter);
        rcRelated.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));


        readData();
        fillDetail();

    }

    private void fillDetail() {

        Intent intent = getIntent();
        String phim_UID = intent.getStringExtra("phim_UID");
        phimID = phim_UID;
        mData.child(phim_UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> map = (Map<String, String>) dataSnapshot.getValue();
                Map<Long, Long> view = (Map<Long, Long>) dataSnapshot.getValue();
                String ten = map.get("tenPhim");

                String link_poster = map.get("posterPhim");
                String mota = map.get("motaPhim");
                String dienvien = map.get("dienvienPhim");
                String theloai = map.get("theloaiPhim");
                Long views = view.get("soluotXem");

                tvTenPhim.setText(ten);
                tvTheLoai.setText("Thể Loại: " + theloai);
                tvDienVien.setText("Diễn Viên:" + dienvien);
                expandableTextView.setText(mota);
                tvViews.setText("Lượt Xem: "+ views);

                Picasso.with(getBaseContext()).load(link_poster)
                        .placeholder(R.mipmap.ic_launcher_round)
                        .into(imgPoster);


                Picasso.with(getBaseContext()).load(link_poster).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        layout.setBackground(new BitmapDrawable(bitmap));
                        Bitmap blurImage = BlurImage(bitmap);

                        layout.setBackground(new BitmapDrawable(blurImage));
                        PorterDuffColorFilter greyFilter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
                        layout.getBackground().setColorFilter(greyFilter);

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public Bitmap BlurImage(Bitmap input) {
        RenderScript rsScript = RenderScript.create(context);
        Allocation alloc = Allocation.createFromBitmap(rsScript, input);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rsScript, Element.U8_4(rsScript));
        blur.setRadius(25);
        blur.setInput(alloc);

        Bitmap result = Bitmap.createBitmap(input.getWidth(), input.getHeight(), Bitmap.Config.ARGB_8888);
        Allocation outAlloc = Allocation.createFromBitmap(rsScript, result);
        blur.forEach(outAlloc);
        outAlloc.copyTo(result);

        rsScript.destroy();
        return result;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //fillDetail();
    }

    public void khoiTao() {
        imgPoster = (ImageView) findViewById(R.id.imgPoster);
        tvTenPhim = (TextView) findViewById(R.id.tvName);
        tvTheLoai = (TextView) findViewById(R.id.tvTheLoai);
        tvDienVien = (TextView) findViewById(R.id.tvDienVien);
        tvViews = (TextView) findViewById(R.id.tvSoLuotXem);
        layout = (LinearLayout) findViewById(R.id.layoutDetail);
        expandableTextView = (ExpandableTextView) findViewById(R.id.expandable_textView);
        rcRelated = (RecyclerView)findViewById(R.id.relatedRecycler);
    }

    public void readData() {
        Query query = mData.orderByChild("theloaiPhim").equalTo("Hoạt Hình");
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


                    if(!snapshot.child("idPhim").getValue().toString().equals(phimID)){
                        arrayList.add(new Related_Phim(idPhim, tenPhim, linkPhim, linkSub, posterPhim, theloaiPhim, motaPhim, dienvienPhim, luotxem));
                        adapter.notifyDataSetChanged();
                    }



                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void clickplayPhim(View view) {

        Intent intent = getIntent();

        final String key = intent.getStringExtra("phim_UID");

        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("Phim").child(key).child("linkPhim").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String linkPhim = dataSnapshot.getValue().toString();
                Intent intent = new Intent(DetailActivity.this, ExoPlayerActivity.class);
                intent.putExtra("Link", linkPhim);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        update_luotXem();

        //finish();
    }

    public void update_luotXem() {
        Intent intent = getIntent();
        final String key = intent.getStringExtra("phim_UID");

        FirebaseDatabase.getInstance().getReference("Phim").child(key).child("soluotXem").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Long giatri = (Long) dataSnapshot.getValue();
                mData.child("Phim").child(key).child("soluotXem").setValue(giatri + 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void clickBackToSomeThing(View view) {
        super.onBackPressed();
    }
}

