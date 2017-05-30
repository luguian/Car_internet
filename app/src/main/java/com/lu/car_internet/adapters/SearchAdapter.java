package com.lu.car_internet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lu.car_internet.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by lu on 2017/5/13.
 */

public class SearchAdapter extends BaseAdapter {

    private List<HashMap<String,String>> addressData;
    private LayoutInflater layoutInflater;


    public SearchAdapter(Context context) {
        layoutInflater=LayoutInflater.from(context);
        addressData=new ArrayList<HashMap<String, String>>() ;


    }


    @Override
    public int getCount() {
        return addressData.size();
    }

    @Override
    public Object getItem(int position) {
        return addressData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
       ViewHolder vh = null;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.item_layout, null);
            vh.title = (TextView) convertView.findViewById(R.id.poi_field_id);
            vh.text = (TextView) convertView.findViewById(R.id.poi_value_id);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.title.setText(addressData.get(position).get("name"));
        vh.text.setText(addressData.get(position).get("address"));
        return convertView;
    }

    class ViewHolder{
        public TextView title;
        public TextView text;
    }
}
