package com.example.warittorn.barbershopapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private List<ServiceDataModel> mDatas;
    private LayoutInflater mLayoutInflater;
    Context mContext;
    public void setFilteredList(List<ServiceDataModel> filteredList){
        this.mDatas = filteredList;
        notifyDataSetChanged();
    }
    public ServiceAdapter(Context context, List<ServiceDataModel> aList , RecyclerViewInterface recyclerViewInterface) {
        mDatas = aList;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.serviceitem,parent,false);
        return new ServiceAdapter.ViewHolder(view,recyclerViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
        holder.tvser_name.setText(mDatas.get(position).getSer_name());
        holder.tvser_detail.setText(mDatas.get(position).getSer_detail());
        holder.tvser_price.setText("Price : "+mDatas.get(position).getSer_price()+" Baht");
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvser_name,tvser_detail,tvser_price;
        public ViewHolder(@NonNull View itemView,RecyclerViewInterface recyclerViewInterface) {
            super(itemView);
            tvser_name = itemView.findViewById(R.id.user_name);
            tvser_detail = itemView.findViewById(R.id.user_phone);
            tvser_price = itemView.findViewById(R.id.ser_open);
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

