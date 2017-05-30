package com.lu.car_internet.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lu.car_internet.R;
import com.lu.car_internet.beans.FriendBean;

import java.util.List;

/**
 * Created by lu on 2017/5/18.
 */

public class FriendAdapter extends BaseAdapter {
    private List mdata;
    private Context mcontext;

    public FriendAdapter(Context mcontext,List mdata ) {
        this.mdata = mdata;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mdata.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View view=null;
        if(convertView==null){
            //通过资源id,把指定的布局文件填充成View对象
            //1.inflate方法填充View对象
            view = View.inflate(mcontext, R.layout.activity_frienditem, null);
        }else{
            view=convertView;
        }
        TextView friend_username = (TextView) view.findViewById(R.id.friend_username);
        FriendBean friendbean = (FriendBean)mdata.get(position);
        friend_username.setText(friendbean.getName());
        return view;
    }
}
