package com.example.vu.morningofowl.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vu.morningofowl.R;
import com.example.vu.morningofowl.model.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter_All_User  extends RecyclerView.Adapter<Adapter_All_User.ViewHolder>{
    private ArrayList<Users> usersList ;
    private Context mContext;

    public Adapter_All_User(ArrayList<Users> usersList, Context mContext) {
        this.usersList = usersList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_user_item,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(usersList.get(position).Image)
                .placeholder(R.drawable.person)
                .into(holder.imgUserAvatar);

        String ten = usersList.get(position).HoTen;
        String email = usersList.get(position).Email;
        String state = usersList.get(position).Status;
        String last_active = usersList.get(position).Last_Active;
        String sdt = usersList.get(position).SDT;
        String img= usersList.get(position).Image;

        holder.tvUserName.setText(ten);
        holder.tvUserEmail.setText("<"+email+">");
        if(state.equals("Offline")){
            holder.tvUserState.setText(state);
            holder.tvUserState.setTextColor(mContext.getResources().getColor(R.color.offline));
            holder.green_dot.setVisibility(View.INVISIBLE);
        }else{
            holder.tvUserState.setText(state);
            holder.tvUserState.setTextColor(mContext.getResources().getColor(R.color.online));
            holder.green_dot.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvUserName, tvUserEmail, tvUserState;
        CircleImageView imgUserAvatar, green_dot;
        public ViewHolder(View itemView) {
            super(itemView);
            tvUserName = (TextView)itemView.findViewById(R.id.tvuserName);
            tvUserEmail = (TextView)itemView.findViewById(R.id.tvuserEmail);
            tvUserState = (TextView)itemView.findViewById(R.id.tvState);
            imgUserAvatar = (CircleImageView)itemView.findViewById(R.id.userImage);
            green_dot = (CircleImageView)itemView.findViewById(R.id.green_dot);
        }
    }
}
