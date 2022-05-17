package com.example.warittorn.barbershopapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private List<SearchDataModel> mDatas;
    private LayoutInflater mLayoutInflater;
    Context mContext;
    public void setFilteredList(List<SearchDataModel> filteredList){
        this.mDatas = filteredList;
        notifyDataSetChanged();
    }
    public SearchAdapter(Context context, List<SearchDataModel> aList ,RecyclerViewInterface recyclerViewInterface) {
        mDatas = aList;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.searchitem,parent,false);
        return new SearchAdapter.ViewHolder(view,recyclerViewInterface);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        String title = mDatas.get(position).getS_name();
        holder.tvShop.setText(title);
        holder.tvOwmer.setText("Owner : "+mDatas.get(position).getS_owner());
        holder.tvTime.setText("Open: "+mDatas.get(position).getS_start()+" : "+mDatas.get(position).getS_end());
        holder.tvContact.setText("Contect : "+mDatas.get(position).getS_contact());
        holder.tvEmail.setText("Email : "+mDatas.get(position).getS_email());
        holder.tvAddress.setText("Address : "+mDatas.get(position).getS_address());
        String str1 = mDatas.get(position).getS_start();
        String str2 = mDatas.get(position).getS_end();
        LocalTime time = LocalTime.now(); // Gets the current time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime timestart = LocalTime.parse(str1);
        LocalTime timestop = LocalTime.parse(str2);
        if (time.isAfter(timestart) && time.isBefore(timestop)){
            mDatas.get(position).setS_status("Open");
            holder.tvStatus.setText(mDatas.get(position).getS_status());
            holder.tvStatus.setTextColor(Color.parseColor("#0eab32"));
        }else{
            mDatas.get(position).setS_status("Close");
            holder.tvStatus.setText(mDatas.get(position).getS_status());
            holder.tvStatus.setTextColor(Color.parseColor("#FF0000"));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvShop,tvOwmer,tvEmail,tvContact,tvTime,tvAddress,tvStatus;
        public ViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvShop = itemView.findViewById(R.id.user_name);
            tvOwmer = itemView.findViewById(R.id.user_phone);
            tvEmail = itemView.findViewById(R.id.time_2);
            tvContact = itemView.findViewById(R.id.servicebook);
            tvTime = itemView.findViewById(R.id.time);
            tvAddress = itemView.findViewById(R.id.address);
            tvStatus = itemView.findViewById(R.id.ser_open);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(recyclerViewInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            recyclerViewInterface.OnitemCilck(pos);
                        }
                    }
                }
            });
        }
    }
}

