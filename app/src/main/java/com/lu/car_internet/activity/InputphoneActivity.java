package com.lu.car_internet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.utils.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.SMSSDK;

/**
 * Created by lu on 2017/3/9.
 */

public class InputphoneActivity extends Activity {
    private EditText edit_phone;
    private Button commit_phonenum;
    private String PhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editphone);
        setup();
    }
    private void setup(){
        edit_phone = (EditText)findViewById(R.id.edit_phone);
        commit_phonenum = (Button)findViewById(R.id.commit_phonenum);
        edit_phone.addTextChangedListener(new OnEditphone());
        commit_phonenum.setOnClickListener(new nextup());
    }
    private class nextup implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            PhoneNumber = edit_phone.getText().toString();
            if(isMobileNO(PhoneNumber)){
                showDialog();
            }else{
                Toast.makeText(InputphoneActivity.this, "电话号码输入有误,请重新输入", Toast.LENGTH_SHORT).show();
            }
//            if (null == strPhoneNumber || "".equals(strPhoneNumber) || strPhoneNumber.length() != 11) {
//                Toast.makeText(IuputphoneActivity.this, "电话号码输入有误", Toast.LENGTH_SHORT).show();
//                return;
//            }
            // SMSSDK.getVerificationCode("86", strPhoneNumber);
        }
    }
    private class OnEditphone implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            initWidgetForCannext();

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    // 判断当前手机输入是否合法
    private boolean cannext() {
        Editable phonenum =edit_phone.getText();
        return !Utils.isStrEmpty(phonenum);

    }
    // 根据是否可以下一步，初始化相关控件
    private void initWidgetForCannext() {
        if (cannext()){
            commit_phonenum.setEnabled(true);
            // moImgSlider.setImageResource(R.drawable.ic_arrow_circle_right);
            //mcarinternet_login_btn.setBackgroundColor((Color.parseColor("#CC1E90FF")));


        }
        else{
            // moImgSlider.setImageResource(R.drawable.ic_ask_circle);
            commit_phonenum.setEnabled(false);
            // mcarinternet_login_btn.setBackgroundColor((Color.parseColor("#661E90FF")));

        }
    }
    private boolean isMobileNO(String mobiles){
        boolean flag = false;
        try {

            // 13********* ,15********,18*********
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");

            Matcher m = p.matcher(mobiles);
            flag = m.matches();

        } catch (Exception e) {
            flag = false;
        }

        return flag;
    }
    private void showDialog(){
        AlertDialog sendphonedialog = new AlertDialog.Builder(InputphoneActivity.this).create();
        sendphonedialog.setIcon(R.drawable.ic_launcher);
        sendphonedialog.setTitle("发送验证码");
        sendphonedialog.setMessage("确定发送验证码到"+PhoneNumber+"号码上?");
        sendphonedialog.setButton(DialogInterface.BUTTON_NEGATIVE,"取消",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        sendphonedialog.setButton(DialogInterface.BUTTON_POSITIVE,"确定",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent inputcodeintent = new Intent();
                inputcodeintent.putExtra("userphone",PhoneNumber);
                inputcodeintent.setClass(InputphoneActivity.this,ValidateActivity.class);
                startActivity(inputcodeintent);
            }
        });
        sendphonedialog.show();
        Button btnPositive =
                sendphonedialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
        Button btnNegative =
                sendphonedialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE);
        btnNegative.setTextColor(getResources().getColor(R.color.black));
        // btnNegative.setTextSize(18);
        btnPositive.setTextColor(getResources().getColor(R.color.black));
        // btnPositive.setTextSize(18);
    }


}
