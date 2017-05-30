package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lu.car_internet.R;

import static com.tencent.open.utils.Global.getContext;

/**
 * Created by lu on 2017/5/5.
 */

public class EditaddressActivity extends Activity {
    private ImageButton address_back;
    private Button save_addressbtn;
    private EditText edit_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editaddress);
        address_back = (ImageButton)findViewById(R.id.address_back);
        save_addressbtn = (Button)findViewById(R.id.save_addressbtn);
        edit_address = (EditText)findViewById(R.id.edit_address);
        address_back.setOnClickListener(new addressback());
        save_addressbtn.setOnClickListener(new save_address());
        edit_address.addTextChangedListener(new iscansave());
    }

    private class addressback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }
    private class save_address implements View.OnClickListener{

        @Override
        public void onClick(View view) {
          String address = edit_address.getText().toString();
          if(address.length()<40){
              SharedPreferences sPreferences=getSharedPreferences("config", getContext().MODE_PRIVATE);
              SharedPreferences.Editor editor=sPreferences.edit();
              editor.putString("address", edit_address.getText().toString());
              editor.commit();
              finish();
          }else{
              Toast.makeText(EditaddressActivity.this, "地址长度太长", Toast.LENGTH_LONG).show();
          }
        }
    }

    private class iscansave implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
             if(charSequence.length()>0){
                 save_addressbtn.setVisibility(View.VISIBLE);
             }else{
                 save_addressbtn.setVisibility(View.GONE);
             }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
