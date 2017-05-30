package com.lu.car_internet.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.adapters.AddgasolineAdapter;
import com.lu.car_internet.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lu on 2017/5/18.
 */

public class FeedbackActivity extends Activity {
    private Button btnBack;
    private EditText feedback_value;
    private Button commit_feedback;
    private EditText contact_et;
    private String result="";
    private String userid;
    private Dialog feedbackdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        btnBack = (Button)findViewById(R.id.btnBack);
        feedback_value =(EditText)findViewById(R.id.feedback_value);
        commit_feedback = (Button)findViewById(R.id.commit_feedback);
        contact_et = (EditText)findViewById(R.id.contact_et);
        contact_et.addTextChangedListener(new oncanaddfeedback());
        btnBack.setOnClickListener(new backfeedback());
        commit_feedback.setOnClickListener(new addfeedback());
        SharedPreferences sPreferences = FeedbackActivity.this.getSharedPreferences("config", MODE_PRIVATE);
        userid=sPreferences.getString("Uid", "");

    }

    private class addfeedback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            final Map<String,String> params=new HashMap<String,String>();
            params.put("uid", userid);
            params.put("feedbackcont", feedback_value.getText().toString());
            params.put("feedbackcontact", contact_et.getText().toString());
            feedbackdialog = new LoadingDialog(FeedbackActivity.this,"正在提交....");
            feedbackdialog.setCanceledOnTouchOutside(false);
            feedbackdialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1500);
                        result= HttpUtils.submitPostData(params,"utf-8","editfeedback");
                        if(result.equals("反馈成功")){
                            Message msg =  feedbackhandle.obtainMessage();
                            msg.what = 0;
                            feedbackhandle.sendMessage(msg);
                        }else{
                            Message msg =  feedbackhandle.obtainMessage();
                            msg.what = 1;
                            feedbackhandle.sendMessage(msg);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private class oncanaddfeedback implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0 && feedback_value.getText().toString().length()>1){
                    commit_feedback.setEnabled(true);
                }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    Handler feedbackhandle = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 处理消息时需要知道是成功的消息还是失败的消息
            switch (msg.what) {
                case 0:
                    feedbackdialog.dismiss();
                    Toast.makeText(FeedbackActivity.this,"反馈成功", Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case 1:
                    feedbackdialog.dismiss();
                    Toast.makeText(FeedbackActivity.this, "反馈失败", Toast.LENGTH_LONG).show();
                default:
                    break;
            }

        }
    };

    private class backfeedback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }

}
