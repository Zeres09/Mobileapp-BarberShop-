package com.example.warittorn.barbershopapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class ProfileAdapter extends BaseAdapter {
    private List<UserDataModel> mDatas;
    private LayoutInflater mLayoutInflater;
    Context mContext;
    public ProfileAdapter(Context context,List<UserDataModel> aList) {
        mDatas = aList;
        mLayoutInflater = LayoutInflater.from(context);
        mContext = context;

    }

    static class ViewHolder {
        TextInputEditText itvname,itvphone;
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
        ProfileAdapter.ViewHolder holder;
        if (view == null) {
            view = mLayoutInflater.inflate(R.layout.activity_profile,viewGroup,false);
            holder = new ProfileAdapter.ViewHolder();
            holder.itvname = view.findViewById(R.id.username);
            holder.itvphone = view.findViewById(R.id.number);
            view.setTag(holder);
        } else {
            holder = (ProfileAdapter.ViewHolder)view.getTag();
        }
        holder.itvname.setText(mDatas.get(position).getUsername());
        holder.itvphone.setText(mDatas.get(position).getPhone());
        return view;
    }
}

