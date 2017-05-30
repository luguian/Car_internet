package com.lu.car_internet.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.activity.AddgasolineActivity;
import com.lu.car_internet.activity.EvaluateActivity;
import com.lu.car_internet.activity.LoadingDialog;
import com.lu.car_internet.activity.OrderScanActivity;
import com.lu.car_internet.activity.PaymentActivity;
import com.lu.car_internet.activity.QrcodeActivity;
import com.lu.car_internet.beans.GasolineOrder;
import com.lu.car_internet.config.CharacterParser;
import com.lu.car_internet.utils.HttpUtils;
import com.lu.car_internet.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by lu on 2017/4/23.
 */

public class GasolineorderAdapter extends BaseAdapter {
    private List addgasolineorder;
    private Context mcontext;
    private Bitmap bitmap = null;
    int size;
    private String result;
    private Dialog deleteDialog;
    private AlertDialog.Builder tipDialog;



    public GasolineorderAdapter(Context mcontext,List addgasolineorder ) {
        this.addgasolineorder = addgasolineorder;
        this.mcontext = mcontext;
    }
    @Override
    public int getCount() {
       return addgasolineorder.size();
    }
    @Override
    public Object getItem(int position) {
        return addgasolineorder.get(position);
    }

    @Override
    public long getItemId(int i) {
        return  i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        CharacterParser characterParser =new CharacterParser();
        View view=null;
        if(convertView==null){
            //通过资源id,把指定的布局文件填充成View对象
            //1.inflate方法填充View对象
            view = View.inflate(mcontext, R.layout.activity_queryorder, null);
        }else{
            view=convertView;
        }
        RelativeLayout details_orderrl =(RelativeLayout)view.findViewById(R.id.details_orderrl);
        ImageView car_logo = (ImageView) view.findViewById(R.id.car_logo);
        TextView ordercar_brand = (TextView) view.findViewById(R.id.ordercar_brand);
        final TextView station_address = (TextView) view.findViewById(R.id.station_address);
        final TextView gasoline_name = (TextView) view.findViewById(R.id.gasoline_name);
        final TextView gasoline_number= (TextView) view.findViewById(R.id.gasoline_number);
        TextView time_stamp = (TextView) view.findViewById(R.id.time_stamp);
        final TextView pay_money = (TextView) view.findViewById(R.id.pay_money);
        final TextView gasoline_state = (TextView)view.findViewById(R.id.gasoline_state);
        Button otherorder_btn = (Button) view.findViewById(R.id.otherorder_btn);
        Button deleteorder_btn = (Button)view.findViewById(R.id.deleteorder_btn);
        Button evaluate_btn = (Button)view.findViewById(R.id.evaluate_btn);
        final GasolineOrder gasolineorder = (GasolineOrder) addgasolineorder.get(position);
        evaluate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String,String>params=new HashMap<String,String>();
                params.put("gasolineId", gasolineorder.getGasolineId());
                result= HttpUtils.submitPostData(params,"utf-8","queryevaluate");
                if(result.equals("没有评价")){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    SharedPreferences sPreferences=mcontext.getSharedPreferences("config", MODE_PRIVATE);
                    String Uid = sPreferences.getString("Uid","");
                    bundle.putString("userId",Uid);
                    bundle.putString("gasolineId",gasolineorder.getGasolineId());
                    bundle.putString("evaluatestation",station_address.getText().toString());
                    intent.setClass(mcontext, EvaluateActivity.class);
                    intent.putExtras(bundle);
                    mcontext.startActivity(intent);
                }else{
                    Toast.makeText(mcontext, "已经评价",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });

        if(gasolineorder.getGasoline_status().equals("交易进行中")){
            deleteorder_btn.setEnabled(false);
        }
        if(gasolineorder.getGasoline_status().equals("交易结束")){
            evaluate_btn.setVisibility(View.VISIBLE);
            deleteorder_btn.setEnabled(true);
        }

        deleteorder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 退出对话框

                tipDialog = new AlertDialog.Builder(mcontext);
                tipDialog.setTitle("提示");
                tipDialog.setMessage("是否确定退出?");
                tipDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        size = position;
                        deleteDialog = new LoadingDialog(mcontext,"正在删除....");
                        deleteDialog.setCanceledOnTouchOutside(false);
                        deleteDialog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                    final Map<String,String> params=new HashMap<String,String>();
                                    params.put("carid", gasolineorder.getCarid());
                                    params.put("gasolineId", gasolineorder.getGasolineId());
                                    result= HttpUtils.submitPostData(params,"utf-8","deleteorder");
                                    if(result.equals("删除成功")){
                                        Message msg = deleteorderhandler.obtainMessage();
                                        msg.what = 0;
                                        deleteorderhandler.sendMessage(msg);
                                    }

                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();

                    }
                });
                tipDialog.setNegativeButton("取消", null);
                tipDialog.show();

            }
        });

        details_orderrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("carid", gasolineorder.getCarid());
                bundle.putString("gasolinetype", gasolineorder.getGasolineType());
                bundle.putString("gasolinenumber", gasolineorder.getGasolineNumber());
                bundle.putString("gasolinedate", gasolineorder.getGasolineDate());
                Intent intent = new Intent();
                intent.setClass(mcontext,OrderScanActivity.class);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);
            }
        });
        otherorder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                SharedPreferences sPreferences=mcontext.getSharedPreferences("config", MODE_PRIVATE);
                String Carid = sPreferences.getString("carId", "");
                String Uid = sPreferences.getString("Uid","");
                Toast.makeText(mcontext, Carid+Uid, Toast.LENGTH_SHORT)
                        .show();
                bundle.putString("Carid", Carid);
                bundle.putString("Uid", Uid);
                bundle.putString("gasoline_station", station_address.getText().toString());
                bundle.putString("gasoline_number", gasoline_number.getText().toString());
                bundle.putString("gasoline_money", pay_money.getText().toString());
                bundle.putString("gasoline_type", gasoline_name.getText().toString());
                Intent intent = new Intent();
                intent.setClass(mcontext, PaymentActivity.class);
                intent.putExtras(bundle);
                mcontext.startActivity(intent);

            }
        });
        String brandimg=characterParser.getSelling(gasolineorder.getCarbrand());
        bitmap = Utils.getbitmap("http://139.199.73.19/project_car/imgs/"+brandimg+".jpg");
        car_logo.setImageBitmap(bitmap);
        ordercar_brand.setText(gasolineorder.getCarbrand());
        gasoline_name.setText(gasolineorder.getGasolineType());
        gasoline_number.setText(gasolineorder.getGasolineNumber());
        time_stamp.setText(gasolineorder.getGasolineDate());
        pay_money.setText(gasolineorder.getGasolineMoney());
        station_address.setText(gasolineorder.getGasolineStation());
        gasoline_state.setText(gasolineorder.getGasoline_state());
        return view;
    }
    Handler deleteorderhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 处理消息时需要知道是成功的消息还是失败的消息
            switch (msg.what) {
                case 0:
                    deleteDialog.dismiss();

                    if(addgasolineorder.size()<1){
                        Toast.makeText(mcontext, "已经全部删除订单", Toast.LENGTH_SHORT)
                                .show();
                    }else{
                        addgasolineorder.remove(size);
                        GasolineorderAdapter.this.notifyDataSetChanged();
                    }

                    break;
                default:
                    break;
            }

        }
    };


}
