package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.lu.car_internet.R;

import org.json.JSONObject;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static android.content.ContentValues.TAG;

/**
 * Created by lu on 2017/3/9.
 */

public class ValidateActivity  extends Activity {
    private EditText edit_code;
    private TextView phone_numbervalue;
    private TextView show_codetv;
    private Button commit_code;
    private EventHandler eventHandler;
    private String strPhoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validatecode);
        SMSSDK.initSDK( ValidateActivity.this, "116b07e90b603", "f0ee05e55fc82831475fdd1261b3f53f");

        setup();
        handlemessage();

    }

   private void handlemessage(){
       commitcode();
       eventHandler = new EventHandler() {

           /**
            * 在操作之后被触发
            *
            * @param event  参数1
            * @param result 参数2 SMSSDK.RESULT_COMPLETE表示操作成功，为SMSSDK.RESULT_ERROR表示操作失败
            * @param data   事件操作的结果
            */

           @Override
           public void afterEvent(int event, int result, Object data) {
               Message message = myHandler.obtainMessage(0x00);
               message.arg1 = event;
               message.arg2 = result;
               message.obj = data;
               myHandler.sendMessage(message);
           }
       };

       SMSSDK.registerEventHandler(eventHandler);

   }


    private void setup(){
        Intent intent = getIntent();
        strPhoneNumber = intent.getStringExtra("userphone");
        SMSSDK.getVerificationCode("86", strPhoneNumber);
        edit_code = (EditText)findViewById(R.id.edit_code);
        commit_code = (Button)findViewById(R.id.commit_code);
        show_codetv = (TextView)findViewById(R.id.show_codetv);
        phone_numbervalue = (TextView)findViewById(R.id.phone_numbervalue);
        phone_numbervalue.setText(strPhoneNumber);
        edit_code.addTextChangedListener(new OnEditCode());
        show_codetv.setOnClickListener(new resend());
        commit_code.setOnClickListener(new commitnext());
        show_codetv.setClickable(false);


    }
    private class resend implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            SMSSDK.getVerificationCode("86", strPhoneNumber);
            handlemessage();
        }
    }
    private class OnEditCode implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() >= 1){
                commit_code.setEnabled(true);
            }else{
                commit_code.setEnabled(false);
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    private class commitnext implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            String strCode = edit_code.getText().toString();
            if (null != strCode && strCode.length() == 4) {
                //  Log.d(TAG, mEditTextCode.getText().toString());
                SMSSDK.submitVerificationCode("86", strPhoneNumber, edit_code.getText().toString());
            } else {
                Toast.makeText(ValidateActivity.this, "密码长度不正确", Toast.LENGTH_SHORT).show();
            }
          //  commitcode();
        }
    }

    private void commitcode(){
        //开启线程去更新button的text
        new Thread() {
            @Override
            public void run() {
                int totalTime = 60;
                for (int i = 0; i < totalTime; i++) {
                    Message message = myHandler.obtainMessage(0x01);
                    message.arg1 = totalTime - i;
                    myHandler.sendMessage(message);
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                myHandler.sendEmptyMessage(0x02);
            }
        }.start();
    }


    Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x00:
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    Log.e(TAG, "result : " + result + ", event: " + event + ", data : " + data);
                    if (result == SMSSDK.RESULT_COMPLETE) { //回调  当返回的结果是complete
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) { //获取验证码
                            Toast.makeText(ValidateActivity.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "get verification code successful.");
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码
                            Log.d(TAG, "submit code successful");
                            finish();
                            Toast.makeText(ValidateActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
                            SharedPreferences sPreferences = ValidateActivity.this.getSharedPreferences("config", MODE_PRIVATE);
                            String loginorregist = sPreferences.getString("forgetorregister", "");
                            if(loginorregist.equals("register")){
                                Intent intentregist = new Intent(ValidateActivity.this,RegisterTypeActivity.class);
                                intentregist.putExtra("userphone",strPhoneNumber);
                                startActivity(intentregist);
                            }else{
                                Intent intentforget = new Intent(ValidateActivity.this,ForgetPwdActivity.class);
                                intentforget.putExtra("userphone",strPhoneNumber);
                                startActivity(intentforget);
                            }

                        } else {
                            Log.d(TAG, data.toString());
                        }
                    } else { //进行操作出错，通过下面的信息区分析错误原因
                        try {
                            Throwable throwable = (Throwable) data;
                            throwable.printStackTrace();
                            JSONObject object = new JSONObject(throwable.getMessage());
                            String des = object.optString("detail");//错误描述
                            int status = object.optInt("status");//错误代码
                            //错误代码：  http://wiki.mob.com/android-api-%E9%94%99%E8%AF%AF%E7%A0%81%E5%8F%82%E8%80%83/
                            Log.e(TAG, "status: " + status + ", detail: " + des);
                            if (status > 0 && !TextUtils.isEmpty(des)) {
                                Toast.makeText(ValidateActivity.this, des, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 0x01:
                    show_codetv.setText("重新发送(" + msg.arg1 + ")");
                    break;
                case 0x02:
                    show_codetv.setText("收不到验证码");
                    show_codetv.setClickable(true);
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

}
