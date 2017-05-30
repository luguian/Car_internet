package com.lu.car_internet.activity;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.lu.car_internet.R;
import org.json.JSONObject;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import static android.content.ContentValues.TAG;

/**
 * Created by lu on 2017/3/9.
 */

public class ReceiveMessage extends Activity implements View.OnClickListener {
    private EditText edit_phone;
    private EditText edit_code;
    private Button get_code;
    private Button confim_commit;
    private EventHandler eventHandler;
    private String strPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        setup();
        SMSSDK.initSDK( ReceiveMessage.this, "116b07e90b603", "f0ee05e55fc82831475fdd1261b3f53f");
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
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.confim_commit) {
            String strCode = edit_code.getText().toString();
            if (null != strCode && strCode.length() == 4) {
              //  Log.d(TAG, mEditTextCode.getText().toString());
                SMSSDK.submitVerificationCode("86", strPhoneNumber, edit_code.getText().toString());
            } else {
                Toast.makeText(this, "密码长度不正确", Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.get_code) {
            strPhoneNumber = edit_phone.getText().toString();
            if (null == strPhoneNumber || "".equals(strPhoneNumber) || strPhoneNumber.length() != 11) {
                Toast.makeText(this, "电话号码输入有误", Toast.LENGTH_SHORT).show();
                return;
            }
            SMSSDK.getVerificationCode("86", strPhoneNumber);
            get_code.setClickable(false);
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
    }
    private void setup(){
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_code = (EditText) findViewById(R.id.edit_code);
        get_code = (Button) findViewById(R.id.get_code);
        confim_commit = (Button) findViewById(R.id.confim_commit);
        get_code.setOnClickListener(this);
        confim_commit.setOnClickListener(this);

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
                            Toast.makeText(ReceiveMessage.this, "发送验证码成功", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "get verification code successful.");
                        } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) { //提交验证码
                            Log.d(TAG, "submit code successful");
                            Toast.makeText(ReceiveMessage.this, "提交验证码成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ReceiveMessage.this, RegisterTypeActivity.class);
                            startActivity(intent);
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
                                Toast.makeText(ReceiveMessage.this, des, Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                case 0x01:
                    get_code.setText("重新发送(" + msg.arg1 + ")");
                    break;
                case 0x02:
                    get_code.setText("获取验证码");
                    get_code.setClickable(true);
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