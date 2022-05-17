package com.example.warittorn.barbershopapp;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.vishnusivadas.advanced_httpurlconnection.PutData;

import java.util.List;

public class ShopAdapter extends BaseAdapter {
    private List<ShopDataModel> mDatas;
    private LayoutInflater mLayoutInflater;
    Context mContext;

//    private String iconBaseUrl = "http://itpart.com/android/json/img/";

    public ShopAdapter(Context context, List<ShopDataModel> aList) {
        mDatas = aList;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;
    }

    static class ViewHolder {
        String url = "http://192.168.1.7/mobileapp/bookrequest.php";
        TextView tvName,tvTime,tvPhone,tvServise,ordertv,test;
        TextView Abt,Ebt;
        ListView lv;
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
            view = mLayoutInflater.inflate(R.layout.shopitem,viewGroup,false);
            holder = new ViewHolder();
            holder.tvName = view.findViewById(R.id.user_name);
            holder.tvPhone = view.findViewById(R.id.user_phone);
            holder.tvTime = view.findViewById(R.id.time_2);
            holder.tvServise = view.findViewById(R.id.servicebook);
            holder.ordertv = view.findViewById(R.id.order_id);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }
        holder.Abt = view.findViewById(R.id.Accepted);
        holder.Ebt = view.findViewById(R.id.Rejected);
        holder.lv = view.findViewById(R.id.shoplist);
        System.out.println("Size : "+mDatas.size());
        holder.Abt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDatas.size() > 0){
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {

                            String[] field = new String[2];
                            field[0] = "b_id";
                            field[1] = "status";
                            String[] data = new String[2];
                            data[0] = mDatas.get(position).getOrder_id();
                            data[1] = "Accepted";
                            PutData putData = new PutData(holder.url, "POST", field, data);
                            putData.startPut();
                            putData.onComplete();
                            mDatas.remove(position);
                            ShopAdapter.this.notifyDataSetChanged();
                            System.out.println("posittion : "+(position));
                        }
                    });

                }

            }
        });
        holder.Ebt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDatas.size() > 0){
                    Handler handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String[] field = new String[2];
                            field[0] = "b_id";
                            field[1] = "status";
                            String[] data = new String[2];
                            data[0] = mDatas.get(position).getOrder_id();
                            data[1] = "Rejected";
                            PutData putData = new PutData(holder.url, "POST", field, data);
                            putData.startPut();
                            putData.onComplete();
                            mDatas.remove(position);
                            ShopAdapter.this.notifyDataSetChanged();
                        }
                    });

                }
            }
        });
        holder.tvTime.setText("Time: "+mDatas.get(position).getTime());
        holder.tvName.setText("Customer: "+mDatas.get(position).getUsername());
        holder.tvServise.setText("Service: "+mDatas.get(position).getService());
        holder.tvPhone.setText("Contact: "+mDatas.get(position).getPhone());
        holder.ordertv.setText(mDatas.get(position).getOrder_id());
//        String imgUrl = iconBaseUrl +mDatas.get(position).getImg();

//        Picasso.get()
//                .load(imgUrl)
//                .placeholder(R.mipmap.ic_launcher).fit()
//                .error(R.mipmap.ic_launcher)
//                .into(holder.img);

        return view;
    }
}
