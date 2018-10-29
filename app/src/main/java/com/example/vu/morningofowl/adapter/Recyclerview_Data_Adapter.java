package com.example.vu.morningofowl.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.model.SectionDataPhim;

import java.util.ArrayList;

public class Recyclerview_Data_Adapter extends RecyclerView.Adapter<Recyclerview_Data_Adapter.ItemRowHolder> {
    private ArrayList<SectionDataPhim> dataList;
    private Context mContext;

    public Recyclerview_Data_Adapter(ArrayList<SectionDataPhim> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_recycler_item,parent,false);
        return new ItemRowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, int position) {
        final String sectionName = dataList.get(position).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(position).getAllPhimSections();

        holder.itemTitle.setText(sectionName);

        Section_List_Adapter itemListDataAdapter = new Section_List_Adapter(singleSectionItems, mContext);

        holder.recycler_view_list.setHasFixedSize(true);
        holder.recycler_view_list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        holder.recycler_view_list.setAdapter(itemListDataAdapter);
        holder.recycler_view_list.setNestedScrollingEnabled(true);
        if(position >0 ){
            holder.itemTitle.setTextColor(holder.itemTitle.getResources().getColor(R.color.sectionHeader));
        }

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        private TextView itemTitle;
        private RecyclerView recycler_view_list;


        private ItemRowHolder(View view) {
            super(view);
            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
        }
    }
}