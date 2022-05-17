package com.example.warittorn.barbershopapp;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class HIstoryAdapter extends BaseAdapter {
    private List<HistoryDataModel> mDatas;
    private LayoutInflater mLayoutInflater;
    Context mContext;

//    private String iconBaseUrl = "http://itpart.com/android/json/img/";

    public HIstoryAdapter(Context context, List<HistoryDataModel> aList) {
        mDatas = aList;

        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    static class ViewHolder {
        TextView tvShop,tvTime,tvDate,tvServise,tvStatus;
//        ImageView img;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.historyitem,viewGroup,false);
            holder = new ViewHolder();
            holder.tvShop = view.findViewById(R.id.user_name);
            holder.tvTime = view.findViewById(R.id.user_phone);
            holder.tvDate = view.findViewById(R.id.time_2);
            holder.tvServise = view.findViewById(R.id.servicebook);
            holder.tvStatus = view.findViewById(R.id.ser_open);
            view.setTag(holder);
            if(mDatas.get(position).getStatus().equals("Accepted")) {
                holder.tvStatus.setTextColor(Color.parseColor("#0eab32"));
            }else if(mDatas.get(position).getStatus().equals("Rejected")){
                holder.tvStatus.setTextColor(Color.parseColor("#FF0000"));
            }
        } else{
            holder = (ViewHolder)view.getTag();
        }
        String title = mDatas.get(position).getName();
        holder.tvShop.setText(title);
        holder.tvTime.setText("Time: "+mDatas.get(position).getTime());
        holder.tvDate.setText("Date: "+mDatas.get(position).getDate());
        holder.tvServise.setText("Service: "+mDatas.get(position).getService());
        holder.tvStatus.setText(mDatas.get(position).getStatus());

//        String imgUrl = iconBaseUrl +mDatas.get(position).getImg();

//        Picasso.get()
//                .load(imgUrl)
//                .placeholder(R.mipmap.ic_launcher).fit()
//                .error(R.mipmap.ic_launcher)
//                .into(holder.img);

        return view;
    }
}
