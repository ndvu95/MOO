package com.example.vu.morningofowl.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.adapter.Adapter_Phim_Admin;
import com.example.vu.morningofowl.model.Phim;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

public class QL_PhimActivity extends AppCompatActivity {
    ArrayList<Phim> arrayList;
    Adapter_Phim_Admin adapter;
    DatabaseReference mData;
    GridView gvPhim;
    SearchView searchView;
    FloatingActionButton btnAdd;
    TextView tvDS;

    private Dialog dialogHoi = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ql__phim);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Window window = this.getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.gray_dark));
        InitUI();

        arrayList = new ArrayList<>();
        adapter = new Adapter_Phim_Admin(this, R.layout.grid_single_item, arrayList);
        gvPhim.setAdapter(adapter);
        fillDataAll();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QL_PhimActivity.this, ManagerAddPhim_Activity.class);
                startActivity(intent);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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

    private void fillDataAll() {
        mData = FirebaseDatabase.getInstance().getReference("Phim");

        Query query = mData;

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Phim phim = snapshot.getValue(Phim.class);
                        arrayList.add(phim);
                        Collections.sort(arrayList, new Comparator<Phim>() {
                            @Override
                            public int compare(Phim phim, Phim t1) {
                                return phim.getTenPhim().compareTo(t1.getTenPhim());
                            }
                        });
                    }
                    adapter.notifyDataSetChanged();


                    gvPhim.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String key = arrayList.get(position).getIdPhim();
                            dialogEdit(key);
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


    private void fillData(final String q) {

        mData = FirebaseDatabase.getInstance().getReference("Phim");
        Query query = mData.orderByChild("tenPhim");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                arrayList.clear();
                if (dataSnapshot.exists() && dataSnapshot != null) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Phim phim = snapshot.getValue(Phim.class);
                        String tenthuong = phim.getTenPhim().toLowerCase();
                        if(tenthuong.contains(q)){
                            arrayList.add(phim);
                        }
                    }
                    adapter.notifyDataSetChanged();


                    gvPhim.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                        @Override
                        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                            String key = arrayList.get(position).getIdPhim();
                            dialogEdit(key);
                            return false;
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


    private void dialogEdit(final String key) {
        dialogHoi = new Dialog(QL_PhimActivity.this);
        dialogHoi.setCanceledOnTouchOutside(false);
        dialogHoi.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogHoi.setContentView(R.layout.dialog_remove_phim);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogHoi.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        dialogHoi.getWindow().setAttributes(lp);


        Button btnHuy = (Button) dialogHoi.findViewById(R.id.btnHuy);
        Button btnSubmit = (Button) dialogHoi.findViewById(R.id.btnSubmit);
        Button btnEdit = (Button) dialogHoi.findViewById(R.id.btnSua);

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogHoi.dismiss();
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manhinhDetail = new Intent(QL_PhimActivity.this, Phim_InfoActivity.class);
                manhinhDetail.putExtra("Phim_UID", key);
                startActivity(manhinhDetail);
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(QL_PhimActivity.this);

                builder.setTitle("Xóa Phim");
                builder.setMessage("Bạn Muốn Xóa Phim Này?");

                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mData.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(QL_PhimActivity.this, "Xóa Phim Thành Công", Toast.LENGTH_SHORT).show();
                                    //reloadData();
                                    dialogHoi.dismiss();
                                }
                            }
                        });
                    }
                });
                final AlertDialog alertDialog =  builder.create();
                alertDialog.show();

                dialogHoi.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        alertDialog.dismiss();
                    }
                });
            }
        });
        dialogHoi.show();
    }

    private void InitUI() {
        tvDS = (TextView) findViewById(R.id.tvDS);
        btnAdd = (FloatingActionButton) findViewById(R.id.btnAddPhim);
        gvPhim = (GridView) findViewById(R.id.gridListPhim);
        searchView = (SearchView) findViewById(R.id.searchPhimAdmin);
    }

    public void clickBackToAdminn(View view) {
        finish();
    }
}
