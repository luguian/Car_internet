package com.lu.car_internet.adapters;


import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lu.car_internet.R;
import com.lu.car_internet.beans.CarMessage;
import com.lu.car_internet.config.CharacterParser;
import com.lu.car_internet.model.SortModel;
import com.lu.car_internet.utils.Utils;

import java.util.List;

/**
 * Created by lu on 2017/3/28.
 */

public class CarMessageAdapter extends BaseAdapter {
    private List mdata;
    private Context mcontext;
    private Bitmap bitmap = null;

    //构造函数
    public CarMessageAdapter(Context context,List data){
        this.mcontext=context;
        this.mdata=data;
    }
    @Override
    public int getCount() {
        // TODO 自动生成的方法存根
        return mdata.size();
    }

    @Override
    public Object getItem(int position) {
        return mdata.get(position);

    }

    @Override
    public long getItemId(int position) {
        // TODO 自动生成的方法存根
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO 自动生成的方法存根
        View view=null;
        if(convertView==null){
            //通过资源id,把指定的布局文件填充成View对象
            //1.inflate方法填充View对象
            view = View.inflate(mcontext, R.layout.activity_mycaritem, null);
        }else{
            view=convertView;
        }
        /**
         * 汉字转换成拼音的类
         */
        CharacterParser characterParser =new CharacterParser();
        ImageView car_photo =(ImageView) view.findViewById(R.id.car_photo);
        TextView flag = (TextView) view.findViewById(R.id.car_flagvalue);
        TextView type = (TextView) view.findViewById(R.id.car_typevalue);
        TextView number = (TextView) view.findViewById(R.id.car_numbervalue);
      //TextView motor_number = (TextView) view.findViewById(R.id.car_motornumbervalue);
        TextView bodylevel = (TextView) view.findViewById(R.id.car_bodylevelvalue);
        CarMessage carMessage=(CarMessage) mdata.get(position);

       // int bianhao=position+1;
       // String idString=String.valueOf(bianhao);
      //  id.setText(idString);
      //  brand.setText(carMessage.getCar_brand());
        /**
         * 需要优化
         *
         */
        String brandimg=characterParser.getSelling(carMessage.getCar_brand());
        bitmap = Utils.getbitmap("http://139.199.73.19/project_car/imgs/"+brandimg+".jpg");
        car_photo.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
        flag.setText(carMessage.getCar_flag());
        type.setText(carMessage.getCar_type());
        number.setText(carMessage.getCar_number());
    //    motor_number.setText(carMessage.getCar_motor_number());
        bodylevel.setText(carMessage.getCar_bodylevel());
        return view;
    }

}
