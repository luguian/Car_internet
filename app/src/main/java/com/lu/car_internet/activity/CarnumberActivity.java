package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.utils.Utils;

/**
 * Created by lu on 2017/3/27.
 */

public class CarnumberActivity extends Activity {
    private String defaultChepai = "粤";//粤=广东
    private View province;
    private TextView carprovince_sz;
    private EditText chepai_number;
    private EditText editmotor_number;
    private Button carnumber_btn;
    private String ShortName = "粤";
    private String brand;
    private String brandpingying;
    private String Cartype;
    private String bodylevel;
    private ImageButton carnumber_back;

    // 行驶证图示
    private View xsz_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editnumber);
        init();
    }
    private void init(){
        Intent intent =getIntent();
        brand = intent.getStringExtra("brand");
        brandpingying = intent.getStringExtra("brandpingying");
        Cartype = intent.getStringExtra("type");
        bodylevel = intent.getStringExtra("bodylevel");
        carprovince_sz=(TextView) findViewById(R.id.carprovince_sz);
        carprovince_sz.setText(defaultChepai);
        xsz_view = (View)findViewById(R.id.xsz_view);
        chepai_number = (EditText)findViewById(R.id.chepai_number);
        editmotor_number= (EditText)findViewById(R.id.editmotor_number);
        carnumber_btn = (Button)findViewById(R.id.carnumber_btn);
        carnumber_back = (ImageButton)findViewById(R.id.carnumber_back);
        editmotor_number.addTextChangedListener(new iscancommit());
        xsz_view.setOnTouchListener(new popOnTouchListener());
        carnumber_btn.setOnClickListener(new commitmessage());
        carnumber_back.setOnClickListener(new carnumberback());
        hideShowXSZ();
        province =(View) findViewById(R.id.carnumber_province);
        province.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(CarnumberActivity.this, ShortNameList.class);
                intent.putExtra("select_short_name", carprovince_sz.getText());
                startActivityForResult(intent, 0);
            }
        });
    }
    private class carnumberback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }
    private class commitmessage implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            String Cnumber = ShortName + chepai_number.getText();
            if(checkShopSign(Cnumber)){
                String Carnumber = chepai_number.getText().toString();
                String Carmotornumber = editmotor_number.getText().toString();
                Intent intentCarMessage = new Intent(CarnumberActivity.this,CarMessageActivity.class);
                intentCarMessage.putExtra("type",Cartype);
                intentCarMessage.putExtra("bodylevel",bodylevel);
                intentCarMessage.putExtra("brand",brand);
                intentCarMessage.putExtra("brandpingying",brandpingying);
                intentCarMessage.putExtra("carnumber",Carnumber);
                intentCarMessage.putExtra("carmotornumber",Carmotornumber);
                startActivity(intentCarMessage);
            }else{
                Toast.makeText(CarnumberActivity.this, "车牌号码格式不对",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class iscancommit implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            btncancommit();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null)
            return;

        switch (requestCode) {
            case 0:
                Bundle bundle = data.getExtras();
                ShortName = bundle.getString("short_name");
                carprovince_sz.setText(ShortName);
                break;
            case 1:

                break;
        }
    }
    // 避免穿透导致表单元素取得焦点
    private class popOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View arg0, MotionEvent arg1) {
            btncancommit();
            xsz_view.setVisibility(View.GONE);
            return true;
        }
    }
    // 显示隐藏行驶证图示
    private void hideShowXSZ() {
        View btn_help1 = (View) findViewById(R.id.ico_numberhelp);
        Button btn_closeXSZ = (Button) findViewById(R.id.xsz_closebtn);

        btn_help1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                carnumber_btn.setEnabled(false);
                xsz_view.setVisibility(View.VISIBLE);
            }
        });
        btn_closeXSZ.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btncancommit();
                xsz_view.setVisibility(View.GONE);
            }
        });
    }
    private boolean cancommit(){
        Editable carnumber = chepai_number.getText();
        Editable carmotornumber = editmotor_number.getText();
        return !Utils.isStrEmpty(carnumber)
                && !Utils.isStrEmpty(carmotornumber);
    }
    private void btncancommit(){
        if(cancommit()){
            carnumber_btn.setEnabled(true);
        }else{
            carnumber_btn.setEnabled(false);
        }
    }
    public  boolean checkShopSign(String shopSign){
        return shopSign.matches("^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$");
    }

}
