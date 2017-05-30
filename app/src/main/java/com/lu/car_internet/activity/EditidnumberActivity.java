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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tencent.open.utils.Global.getContext;

/**
 * Created by lu on 2017/5/6.
 */

public class EditidnumberActivity extends Activity {

    private ImageButton idnumber_back;
    private EditText edit_idnumber;
    private Button save_idnumberbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editidnumber);
        idnumber_back = (ImageButton)findViewById(R.id.idnumber_back);
        edit_idnumber = (EditText)findViewById(R.id.edit_idnumber);
        save_idnumberbtn = (Button)findViewById(R.id.save_idnumberbtn);
        edit_idnumber.addTextChangedListener(new iscansaveidnumber());
        idnumber_back.setOnClickListener(new idnumberback());
        save_idnumberbtn.setOnClickListener(new saveidnumber());
    }

    private class idnumberback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }


    private class iscansaveidnumber implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              if(charSequence.length()==18||charSequence.length()==15){
                  save_idnumberbtn.setVisibility(View.VISIBLE);
              }else{
                  save_idnumberbtn.setVisibility(View.GONE);
              }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
    private class saveidnumber implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(isidnumber( edit_idnumber.getText().toString())){
                SharedPreferences sPreferences=getApplication().getSharedPreferences("config", getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor=sPreferences.edit();
                editor.putString("idnumber", edit_idnumber.getText().toString());
                editor.commit();
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "身份证输入有误",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    public  boolean isidnumber(String str) {
        String check ="\\d{15}|\\d{18}";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }
}
