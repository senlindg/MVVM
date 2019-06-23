package com.yanshun.mfluitmarket.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.yanshun.mfluitmarket.R;

import java.util.List;


/**
 * Created by hasee on 2017/10/16.
 */

public class AddrAdapter extends BaseAdapter {

    private List<String> selectedContents;

    private ViewHolder viewHolder;

    private Context mContext;

    public AddrAdapter(List<String> selectedContents,Context mContext){
        this.selectedContents = selectedContents;
        this.mContext = mContext;
    }
    @Override
    public int getCount() {
        return selectedContents.size();
    }

    @Override
    public Object getItem(int i) {
        return selectedContents.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (null == view){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.addr_item_adapter,null);
            viewHolder.address = view.findViewById(R.id.addr_content);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.address.setText(selectedContents.get(i));
        return view;
    }

    static class ViewHolder{
        TextView address;
    }
}
