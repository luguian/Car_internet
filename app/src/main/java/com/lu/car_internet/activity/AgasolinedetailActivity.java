package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.utils.HttpUtils;
import com.lu.car_internet.view.VirtualKeyboardView;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lu on 2017/4/21.
 */

public class AgasolinedetailActivity extends Activity {
    private VirtualKeyboardView virtualKeyboardView;
    private GridView gridView;
    private ArrayList<Map<String, String>> valueList;
    private EditText edit_gasolinenumber;
    private Animation enterAnim;
    private Animation exitAnim;
    private ImageView first_Image;
    private ImageView second_Image;
    private ImageView third_Image;
    private ImageView four_Image;
    private RelativeLayout first_typerl;
    private RelativeLayout second_typerl;
    private RelativeLayout third_typerl;
    private RelativeLayout four_typerl;
    private TextView first_gasolineprice;
    private TextView second_gasolineprice;
    private TextView third_gasolineprice;
    private TextView four_gasolineprice;
    private TextView gasolinestation_name;
    private TextView money_pay;
    private String flag ="0";
    private Button addgasoline_btn;
    private String userid;
    private String Carid;
    double gasolinenumber;
    double gasolineprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addgasolinedetails);
        initAnim();
        initView();
        valueList = virtualKeyboardView.getValueList();
    }

    /**
     * 数字键盘显示动画
     */
    private void initAnim() {

        enterAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_in);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.push_bottom_out);
    }

    private void initView() {

        edit_gasolinenumber = (EditText) findViewById(R.id.edit_gasolinenumber);
        edit_gasolinenumber.addTextChangedListener(new changevalue());
        virtualKeyboardView = (VirtualKeyboardView) findViewById(R.id.virtualKeyboardView);
        addgasoline_btn = (Button)findViewById(R.id.addgasoline_btn);
        first_Image = (ImageView)findViewById(R.id.first_Image);
        second_Image = (ImageView)findViewById(R.id.second_Image);
        third_Image = (ImageView)findViewById(R.id.third_Image);
        four_Image = (ImageView)findViewById(R.id.four_Image);
        first_typerl = (RelativeLayout)findViewById(R.id.first_typerl);
        second_typerl = (RelativeLayout)findViewById(R.id.second_typerl);
        third_typerl = (RelativeLayout)findViewById(R.id.third_typerl);
        four_typerl = (RelativeLayout)findViewById(R.id.four_typerl);
        first_gasolineprice = (TextView)findViewById(R.id.first_gasolineprice);
        second_gasolineprice = (TextView)findViewById(R.id.second_gasolineprice);
        third_gasolineprice = (TextView)findViewById(R.id.third_gasolineprice);
        four_gasolineprice = (TextView)findViewById(R.id.four_gasolineprice);
        gasolinestation_name = (TextView)findViewById(R.id.gasolinestation_name);
        money_pay = (TextView)findViewById(R.id.money_pay);
        addgasoline_btn.setOnClickListener(new addgasoline());
        first_typerl.setOnClickListener(new choosefirsrtype());
        second_typerl.setOnClickListener(new choosesecondtype());
        third_typerl.setOnClickListener(new choosethridtype());
        four_typerl.setOnClickListener(new choosefourtype());
        SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
        userid=sPreferences.getString("Uid", "");
        Carid=sPreferences.getString("carId","");
        String E90price = sPreferences.getString("E90", "");
        String E93price = sPreferences.getString("E93", "");
        String E97price = sPreferences.getString("E97", "");
        String E0price = sPreferences.getString("E0", "");
        String name = sPreferences.getString("name","");
        String address = sPreferences.getString("gasolineaddress","");
        if(!E90price.equals("")&&!E93price.equals("")&&!E97price.equals("")&&!E0price.equals("")){
            gasolinestation_name.setText(address+name);
            first_gasolineprice.setText(E90price);
            second_gasolineprice.setText(E93price);
            third_gasolineprice.setText(E97price);
            four_gasolineprice.setText(E0price);
        }else{
            gasolinestation_name.setText("无法获取加油站信息");
            first_gasolineprice.setText("0.0/升");
            second_gasolineprice.setText("0.0/升");
            third_gasolineprice.setText("0.0/升");
            four_gasolineprice.setText("0.0/升");
        }


        // 设置不调用系统键盘
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            edit_gasolinenumber.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(
                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(edit_gasolinenumber, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                virtualKeyboardView.startAnimation(exitAnim);
                virtualKeyboardView.setVisibility(View.GONE);
                addgasoline_btn.setVisibility(View.VISIBLE);
            }
        });

        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(onItemClickListener);

        edit_gasolinenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addgasoline_btn.setVisibility(View.GONE);
                virtualKeyboardView.setFocusable(true);
                virtualKeyboardView.setFocusableInTouchMode(true);
                virtualKeyboardView.startAnimation(enterAnim);
                virtualKeyboardView.setVisibility(View.VISIBLE);
            }
        });

    }
    private class changevalue implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(charSequence.length()>0){


            }
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             if(flag.equals("E90")){
                 try {
                     calculate (first_gasolineprice);
                 } catch (NumberFormatException e) {
                     e.printStackTrace();
                 }

             }
            if(flag.equals("E93")){
                try {
                    calculate (second_gasolineprice);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
            if(flag.equals("E97")){
                try {
                    calculate (third_gasolineprice);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
            if(flag.equals("E0")){
                try {
                    calculate (four_gasolineprice);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            if (position < 11 && position != 9) {    //点击0~9按钮

                String amount = edit_gasolinenumber.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");

                edit_gasolinenumber.setText(amount);

                Editable ea = edit_gasolinenumber.getText();
                edit_gasolinenumber.setSelection(ea.length());
            } else {

                if (position == 9) {      //点击退格键
                    String amount =edit_gasolinenumber.getText().toString().trim();
                    if (!amount.contains(".")) {
                        amount = amount + valueList.get(position).get("name");
                        edit_gasolinenumber.setText(amount);

                        Editable ea = edit_gasolinenumber.getText();
                        edit_gasolinenumber.setSelection(ea.length());
                    }
                }

                if (position == 11) {      //点击退格键
                    String amount = edit_gasolinenumber.getText().toString().trim();
                    if (amount.length() > 0) {
                        amount = amount.substring(0, amount.length() - 1);
                        edit_gasolinenumber.setText(amount);

                        Editable ea = edit_gasolinenumber.getText();
                        edit_gasolinenumber.setSelection(ea.length());
                    }
                }
            }
        }
    };

    private class choosefirsrtype implements View.OnClickListener{

        @Override
        public void onClick(View view) {
           if(first_Image.getVisibility() == View.VISIBLE){
               first_Image.setVisibility(View.GONE);
               flag="0";
               money_pay.setText("0");
           }else{
               flag = "E90";
               calculate (first_gasolineprice);
               first_Image.setVisibility(View.VISIBLE);
               second_Image.setVisibility(View.GONE);
               third_Image.setVisibility(View.GONE);
               four_Image.setVisibility(View.GONE);
           }
        }
    }
    private class choosesecondtype implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(second_Image.getVisibility() == View.VISIBLE){
                second_Image.setVisibility(View.GONE);
                flag="0";
                money_pay.setText("0");
            }else{
                flag = "E93";
                calculate (second_gasolineprice);
                second_Image.setVisibility(View.VISIBLE);
                first_Image.setVisibility(View.GONE);
                third_Image.setVisibility(View.GONE);
                four_Image.setVisibility(View.GONE);
            }
        }
    }
    private class choosethridtype implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(third_Image.getVisibility() == View.VISIBLE){
                third_Image.setVisibility(View.GONE);
                flag="0";
                money_pay.setText("0");
            }else{
                flag = "E97";
                calculate (third_gasolineprice);
                third_Image.setVisibility(View.VISIBLE);
                second_Image.setVisibility(View.GONE);
                first_Image.setVisibility(View.GONE);
                four_Image.setVisibility(View.GONE);
            }
        }
    }
    private class choosefourtype implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            if(four_Image.getVisibility() == View.VISIBLE){
                four_Image.setVisibility(View.GONE);
                flag="0";
                money_pay.setText("0");
            }else{
                flag = "E0";
                calculate (four_gasolineprice);
                four_Image.setVisibility(View.VISIBLE);
                second_Image.setVisibility(View.GONE);
                first_Image.setVisibility(View.GONE);
                third_Image.setVisibility(View.GONE);
            }
        }
    }

    private class addgasoline implements View.OnClickListener{

        @Override
        public void onClick(View view) {
//            Map<String,String>params=new HashMap<String,String>();
//            params.put("Uid", userid);
//            params.put("Carid", Carid);
//            params.put("gasoline_station",gasolinestation_name.getText().toString());
//            params.put("gasoline_type",flag);
//            params.put("gasoline_number",edit_gasolinenumber.getText().toString());
//            params.put("gasoline_money",money_pay.getText().toString());
//            if(flag!=null&&money_pay.getText().toString()!=null){
//                String result="";
//                result= HttpUtils.submitPostData(params,"utf-8","addgasoline");
//                if(result.equals("预约成功")){
//                    Toast.makeText(AgasolinedetailActivity.this, "预约成功", Toast.LENGTH_LONG).show();
//
//                }else{
//                    Toast.makeText(AgasolinedetailActivity.this, "预约不成功,请重新再预约", Toast.LENGTH_LONG).show();
//                }
//            }else{
//                Toast.makeText(AgasolinedetailActivity.this, "没有选择加油类型", Toast.LENGTH_LONG).show();
//            }
            if(flag.equals("0")){
                Toast.makeText(AgasolinedetailActivity.this, "没有选择加油类型", Toast.LENGTH_LONG).show();

            }else{
                if(edit_gasolinenumber.getText()==null){
                    Toast.makeText(AgasolinedetailActivity.this, "加油数量必须大于1升", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent();
                    intent.setClass(AgasolinedetailActivity.this,PaymentActivity.class);
                    Bundle bundle = new Bundle();
            /*字符、字符串、布尔、字节数组、浮点数等等，都可以传*/
                    bundle.putString("Uid", userid);
                    bundle.putString("Carid", Carid);
                    bundle.putString("gasoline_station",gasolinestation_name.getText().toString());
                    bundle.putString("gasoline_number",edit_gasolinenumber.getText().toString());
                    bundle.putString("gasoline_money",money_pay.getText().toString());
                    bundle.putString("gasoline_type",flag);
            /*把bundle对象assign给Intent*/
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }


        }
    }

    private void calculate (TextView tv){
        String price= tv.getText().toString();
        double money=0;
        String number;
        if(edit_gasolinenumber.getText().toString().equals("")){
            number="0";
        }else{
            number=edit_gasolinenumber.getText().toString();
        }
        gasolinenumber = Double.parseDouble(number);
        gasolineprice = Double.parseDouble(price);
        money =gasolinenumber * gasolineprice;
        DecimalFormat df = new  DecimalFormat("#####0.00");
        String m=df.format(money);//返回的是String类型的数据
        money_pay.setText(m);
    }
}
