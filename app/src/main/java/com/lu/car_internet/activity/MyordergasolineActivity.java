package com.lu.car_internet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.adapters.GasolineorderAdapter;
import com.lu.car_internet.beans.CarMessage;
import com.lu.car_internet.beans.GasolineOrder;
import com.lu.car_internet.utils.HttpUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lu on 2017/4/23.
 */

public class MyordergasolineActivity extends Activity {
    private ListView result_myordergasoline;
    private String Carid;
    private String result;
    private GasolineorderAdapter orderAdapter;
    private ImageButton myorderback;
    private int FLAG_DISMISS = 1;
    private boolean flag = true;
    private Dialog mDialog;
    private TextView not_order;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myorderaddgasoline);
        init();
        showresult();
    }
    private void init(){
        result_myordergasoline = (ListView)findViewById(R.id.result_myordergasoline);
        result_myordergasoline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("gaolineid", ((GasolineOrder)orderAdapter.getItem(position)).getGasolineId());
                bundle.putString("gaolineOrderid", ((GasolineOrder)orderAdapter.getItem(position)).getGasoline_orderid());
                bundle.putString("carbrand", ((GasolineOrder)orderAdapter.getItem(position)).getCarbrand());
                bundle.putString("gasolinetype", ((GasolineOrder)orderAdapter.getItem(position)).getGasolineType());
                bundle.putString("gasolinenumber", ((GasolineOrder)orderAdapter.getItem(position)).getGasolineNumber());
                bundle.putString("gasolinemoney", ((GasolineOrder)orderAdapter.getItem(position)).getGasolineMoney());
                bundle.putString("gasolinedate", ((GasolineOrder)orderAdapter.getItem(position)).getGasolineDate());
                bundle.putString("gasolinestate", ((GasolineOrder)orderAdapter.getItem(position)).getGasoline_state());
                bundle.putString("gasolinestation", ((GasolineOrder)orderAdapter.getItem(position)).getGasolineStation());
                Intent intentordertails = new Intent(MyordergasolineActivity.this,OrderdetailsActivity.class);
                intentordertails.putExtras(bundle);
                startActivity(intentordertails);
            }
        });
        myorderback = (ImageButton)findViewById(R.id.myorderback);
        not_order = (TextView)findViewById(R.id.not_order);
        myorderback.setOnClickListener(new finishmyorderback());
        SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
        Carid=sPreferences.getString("carId","");
    }
    private class finishmyorderback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }
    private void showresult(){
        mDialog = new LoadingDialog(MyordergasolineActivity.this,"正在查询....");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        final Map<String,String> params=new HashMap<String,String>();
        params.put("carid", Carid);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    result= HttpUtils.submitPostData(params,"utf-8","querymyorder");
                    if(result.equals("没有预约订单")){

                        Message msg =  orderhandler.obtainMessage();
                        msg.what = 0;
                        orderhandler.sendMessage(msg);
                    }else{
                        if(result.equals("-1")){
                            Message msg =  orderhandler.obtainMessage();
                            msg.what = 1;
                            orderhandler.sendMessage(msg);
//                            result_myordergasoline.setVisibility(View.GONE);
//                            not_order.setVisibility(View.VISIBLE);
//                            not_order.setText("网络异常");
                        }else{
                            if(result.equals("查询失败,请重新查询")){
//                                result_myordergasoline.setVisibility(View.GONE);
//                                not_order.setVisibility(View.VISIBLE);
//                                not_order.setText("查询失败,请重新查询");
                                Message msg =  orderhandler.obtainMessage();
                                msg.what = 2;
                                orderhandler.sendMessage(msg);
                            } else{
                                Message msg =  orderhandler.obtainMessage();
                                msg.what = 3;
                                orderhandler.sendMessage(msg);
//                                orderAdapter = new  GasolineorderAdapter(MyordergasolineActivity.this, getData(result));
//                                result_myordergasoline .setAdapter(orderAdapter);
                            }
                        }

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    private List getData(String jsonString) {

        List< GasolineOrder> list = new ArrayList();
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            //返回json的数组
           JSONArray jsonArray = jsonObject.getJSONArray("gasoline");
            for(int i= 0;i<jsonArray.length();i++){
                JSONObject jsonorder = jsonArray.getJSONObject(i);
                GasolineOrder gasolineorder = new GasolineOrder();
                gasolineorder.setCarbrand(jsonorder.getString("carbrand"));
                gasolineorder.setCarid(jsonorder.getString("carId"));
                gasolineorder.setGasolineMoney(jsonorder.getString("gasolineMoney"));
                gasolineorder.setGasolineNumber(jsonorder.getString("gasolineNumber"));
                gasolineorder.setGasoline_state(jsonorder.getString("gasolineState"));
                gasolineorder.setGasolineStation(jsonorder.getString("gasolineStation"));
                gasolineorder.setGasolineType(jsonorder.getString("gasolineType"));
                gasolineorder.setGasolineId(jsonorder.getString("gasolineId"));
                gasolineorder.setGasoline_orderid(jsonorder.getString("gasolineOrderid"));
                gasolineorder.setGasoline_status(jsonorder.getString("gasolineStatus"));
                JSONObject jsonobjecttime=jsonorder.getJSONObject("gasolineDate");
                String longtime= jsonobjecttime.getString("time");
                Date date= new Date(Long.parseLong(longtime.trim()));
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                String dateString = formatter1.format(date);
                gasolineorder.setGasolineDate(dateString);
                if(!jsonorder.getString("gasolineStatus").equals("已经删除")){
                    list.add(gasolineorder);
                }

            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "异常",
                    Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        return list;
    }






    @Override
    protected void onPause() {

        super.onPause();
    }

    // 此方法在主线程中调用，可以更新UI
    Handler orderhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 处理消息时需要知道是成功的消息还是失败的消息
            switch (msg.what) {
                case 0:
                    mDialog.dismiss();
                    result_myordergasoline.setVisibility(View.GONE);
                    not_order.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mDialog.dismiss();
                    result_myordergasoline.setVisibility(View.GONE);
                    not_order.setVisibility(View.VISIBLE);
                    not_order.setText("网络异常");
                    break;
                case 2:
                    mDialog.dismiss();
                    result_myordergasoline.setVisibility(View.GONE);
                    not_order.setVisibility(View.VISIBLE);
                    not_order.setText("查询失败,请重新查询");
                    break;
                case 3:
                    mDialog.dismiss();
                    if(getData(result).size()<1){
                        result_myordergasoline.setVisibility(View.GONE);
                        not_order.setVisibility(View.VISIBLE);
                    }else{
                        orderAdapter = new  GasolineorderAdapter(MyordergasolineActivity.this, getData(result));
                        result_myordergasoline .setAdapter(orderAdapter);
                    }

                    break;
                default:
                    break;
            }

        }
    };
}
