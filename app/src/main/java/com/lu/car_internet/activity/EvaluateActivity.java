package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.utils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lu on 2017/5/19.
 */

public class EvaluateActivity extends Activity {
    private RatingBar evaluatert;
    private EditText edit_text;
    private Button commit_evaluatebtn;
    private String evaluatevalue="";
    private String gasolineId;
    private String userid;
    private String evaluatestation;
    private String result = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        evaluatert = (RatingBar) findViewById(R.id.evaluatert);
        edit_text =(EditText)findViewById(R.id.edit_text);
        commit_evaluatebtn = (Button)findViewById(R.id.commit_evaluatebtn);
        commit_evaluatebtn.setOnClickListener(new evaluate());
        edit_text.addTextChangedListener(new cancommit());
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        gasolineId = bundle.getString("gasolineId");
        userid = bundle.getString("userId");
        evaluatestation = bundle.getString("evaluatestation");
        evaluatert.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                evaluatevalue = String.valueOf(rating);
                Toast.makeText(EvaluateActivity.this, "rating:" + evaluatevalue,
                        Toast.LENGTH_LONG).show();
            }
        });
    }
    private class cancommit implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           if(charSequence.length()>0 && !evaluatevalue.equals("")){
               commit_evaluatebtn.setEnabled(true);
           }else{
               commit_evaluatebtn.setEnabled(false);
           }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }


    private class evaluate implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Map<String,String> params=new HashMap<String,String>();
            params.put("userId",userid);
            params.put("gasolineId",gasolineId);
            params.put("evaluatestation",evaluatestation);
            params.put("evaluatevalue",evaluatevalue);
            params.put("evaluatetext",edit_text.getText().toString());
            result= HttpUtils.submitPostData(params,"utf-8","evaluate");
            if(result.equals("-1")){
                Toast.makeText(EvaluateActivity.this, "手机没有联网", Toast.LENGTH_LONG).show();
            }else{
                if(result.equals("评价成功")){
                    Toast.makeText(EvaluateActivity.this, "评价成功", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    Toast.makeText(EvaluateActivity.this, "评价失败,请重新尝试", Toast.LENGTH_LONG).show();
                }
            }


        }
    }
}
