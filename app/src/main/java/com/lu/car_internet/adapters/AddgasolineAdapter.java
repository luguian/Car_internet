package com.lu.car_internet.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.activity.AgasolinedetailActivity;
import com.lu.car_internet.beans.GasolineBean;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.tencent.open.utils.Global.getSharedPreferences;

/**
 * Created by lu on 2017/4/13.
 */

public class AddgasolineAdapter extends BaseAdapter {
    private List addgasolinedata;
    private Context mcontext;

    public AddgasolineAdapter(Context mcontext,List addgasolinedata ) {
        this.addgasolinedata = addgasolinedata;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {

        return addgasolinedata.size();
    }

    @Override
    public Object getItem(int position) {
        return addgasolinedata.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        // TODO 自动生成的方法存根
        View view=null;
        if(convertView==null){
            //通过资源id,把指定的布局文件填充成View对象
            //1.inflate方法填充View对象
            view = View.inflate(mcontext, R.layout.activity_itemordergasoline, null);
        }else{
            view=convertView;
        }
        TextView gasoline_name = (TextView)view.findViewById(R.id.gasoline_name);
        TextView gasoline_address = (TextView)view.findViewById(R.id.gasoline_address);
        Button commit_order = (Button)view.findViewById(R.id.commit_order);
        final GasolineBean gasoline = (GasolineBean)addgasolinedata.get(position);
        gasoline_name.setText(gasoline.getGasoline_station());
        gasoline_address.setText(gasoline.getGasoline_station_address());
        commit_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
                SharedPreferences.Editor editor=sPreferences.edit();
                editor.putString("E90", gasoline.getE90price());
                editor.putString("E93", gasoline.getE93price());
                editor.putString("E97", gasoline.getE97price());
                editor.putString("E0", gasoline.getE0price());
                editor.putString("name",gasoline.getGasoline_station_address());
                editor.putString("gasolineaddress",gasoline.getGasoline_station());
                editor.commit();
                Intent intentgasolinetails = new Intent(mcontext,AgasolinedetailActivity.class);
                mcontext.startActivity(intentgasolinetails);
            }
        });



        return view;
    }

}
