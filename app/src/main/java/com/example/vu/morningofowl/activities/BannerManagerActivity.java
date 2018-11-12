package com.example.vu.morningofowl.activities;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.Adapter_QC;
import com.example.vu.morningofowl.model.QuangCao;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
    private String ten;
    private String key;
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


       reloadQC();


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


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogAddBanner();
            }
        });
    }
    private void reloadQC(){
        arrayList.clear();
        mData = FirebaseDatabase.getInstance().getReference();
        mData.child("QuangCao").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String linkPoster = ds.child("linkAnh").getValue().toString();
                    String idphim = ds.child("idPhim").getValue().toString();
                    arrayList.add(new QuangCao(linkPoster,idphim));
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }
    private void dialogAddBanner(){
        final Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_banner);WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialog.getWindow().setAttributes(lp);

        Button btnHuy = (Button)dialog.findViewById(R.id.btnHuy);
        Button btnThem= (Button)dialog.findViewById(R.id.btnThem);
        Spinner sptenPhim = (Spinner)dialog.findViewById(R.id.sptenPhim);
        final EditText edtlinkPoster = (EditText)dialog.findViewById(R.id.edtlinkAnh);
        final ArrayList<String> arrayList;
        final ArrayAdapter<String> adapter;
        final DatabaseReference mDataQC;




        arrayList = new ArrayList<>();
        adapter = new ArrayAdapter(dialog.getContext(),android.R.layout.simple_spinner_item,arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sptenPhim.setAdapter(adapter);

        arrayList.add(0,"- Tên Phim -");

        mDataQC = FirebaseDatabase.getInstance().getReference();
        mDataQC.child("Phim").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    String tenphim = ds.child("tenPhim").getValue().toString();
                    arrayList.add(tenphim);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        sptenPhim.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).equals("- Tên Phim -")){

                }else{
                    ten = adapterView.getItemAtPosition(i).toString();
                    mDataQC.child("Phim").orderByChild("tenPhim").equalTo(ten).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds:dataSnapshot.getChildren()){
                                key = ds.getKey();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String linkPoster = edtlinkPoster.getText().toString().trim();
                QuangCao qc = new QuangCao(linkPoster, key);
                mDataQC.child("QuangCao").push().setValue(qc).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(BannerManagerActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                            reloadQC();
                            dialog.dismiss();
                        }else{
                            Toast.makeText(BannerManagerActivity.this, "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });

            }   
        });


        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });








        dialog.show();
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
