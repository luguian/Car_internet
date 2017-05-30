package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lu on 2017/5/7.
 */

public class OrderdetailsActivity extends Activity implements View.OnClickListener{
    private String gasolineid;
    private String gasolineorderid;
    private String carbrand;
    private String gasolinetype;
    private String gasolinenumber;
    private String gasolinemoney;
    private String gasolinedate;
    private String gasolinestate;
    private String gasolinestation;
    private Button drawback;
    private Button confimaddgasoline;
    private TextView addgasoline_car;
    private TextView addgasoline_type;
    private TextView gasoline_number;
    private TextView sum_numbervalue;
    private TextView pay_value;
    private TextView order_numbervalue;
    private TextView order_timevalue;
    private TextView pay_modevalue;
    private TextView station_namevalue;
    private ImageButton ordertails_back;
    private TextView gasolinestatetv;
    private String result="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderdetails);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        gasolineid = bundle.getString("gaolineid");
        gasolineorderid = bundle.getString("gaolineOrderid");
        carbrand = bundle.getString("carbrand");
        gasolinetype = bundle.getString("gasolinetype");
        gasolinenumber = bundle.getString("gasolinenumber");
        gasolinemoney = bundle.getString("gasolinemoney");
        gasolinedate = bundle.getString("gasolinedate");
        gasolinestate = bundle.getString("gasolinestate");
        gasolinestation = bundle.getString("gasolinestation");
        init();
    }

    private void init(){
        drawback = (Button)findViewById(R.id.drawback);
        confimaddgasoline = (Button)findViewById(R.id.confimaddgasoline);
        gasolinestatetv = (TextView)findViewById(R.id.gasolinestatetv);
        drawback.setOnClickListener(this);
        confimaddgasoline.setOnClickListener(this);
        if(gasolinestate.equals("申请退款")){
            drawback.setEnabled(false);
            confimaddgasoline.setEnabled(false);
            gasolinestatetv.setText("退款中");
        }else{
            if(gasolinestate.equals("已确认加油")){
                confimaddgasoline.setEnabled(false);
                confimaddgasoline.setText(gasolinestate);
                gasolinestatetv.setText("已确认加油");
            }
        }

        addgasoline_car = (TextView)findViewById(R.id.addgasoline_car);
        addgasoline_type = (TextView)findViewById(R.id.addgasoline_type);
        gasoline_number = (TextView)findViewById(R.id.gasoline_number);
        sum_numbervalue = (TextView)findViewById(R.id.sum_numbervalue);
        pay_value = (TextView)findViewById(R.id.pay_value);
        order_numbervalue = (TextView)findViewById(R.id.order_numbervalue);
        order_timevalue = (TextView)findViewById(R.id.order_timevalue);
        pay_modevalue = (TextView)findViewById(R.id.pay_modevalue);
        station_namevalue = (TextView)findViewById(R.id.station_namevalue);
        ordertails_back = (ImageButton)findViewById(R.id.ordertails_back);
        ordertails_back.setOnClickListener(this);
        addgasoline_car.setText(carbrand);
        addgasoline_type.setText(gasolinetype);
        gasoline_number.setText(gasolinenumber);
        sum_numbervalue.setText(gasolinemoney);
        pay_value.setText(gasolinemoney);
        order_numbervalue.setText(gasolineorderid);
        order_timevalue.setText(gasolinedate);
        pay_modevalue.setText(gasolinestate);
        station_namevalue.setText(gasolinestation);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.ordertails_back:
                finish();
                break;
            case R.id.drawback:
                updatestate("申请退款","交易终止");
                if(result.equals("更新成功")){
                    drawback.setEnabled(false);
                    gasolinestatetv.setText("退款中");
                }
                break;
            case R.id.confimaddgasoline:
                updatestate("已确认加油","交易结束");
                if(result.equals("更新成功")){
                    confimaddgasoline.setEnabled(false);
                    gasolinestatetv.setText("已确认加油");
                }
                break;

        }
    }

    private void updatestate(String text,String status){
        SharedPreferences sPreferences=OrderdetailsActivity.this.getSharedPreferences("config", MODE_PRIVATE);
        String carid=sPreferences.getString("carId", "");
        Map<String,String> params=new HashMap<String,String>();
        params.put("carid",carid);
        params.put("gasolineId",gasolineid);
        params.put("state",text);
        params.put("gasolinestatus",status);
        result= HttpUtils.submitPostData(params,"utf-8","updateorderstate");
        if(result.equals("更新成功")){
            Toast.makeText(OrderdetailsActivity.this, "更新成功", Toast.LENGTH_LONG).show();
            confimaddgasoline.setEnabled(false);
        }

    }
}
