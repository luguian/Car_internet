package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.widget.EmailAutoCompleteTextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.tencent.open.utils.Global.getContext;


/**
 * Created by lu on 2017/5/5.
 */

public class EditemailActivity extends Activity {
    private Button save_emailbtn;
    private EmailAutoCompleteTextView act;
    private ImageButton email_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editemail);
        save_emailbtn = (Button)findViewById(R.id.save_emailbtn);
        email_back = (ImageButton )findViewById(R.id.email_back);
        act = (EmailAutoCompleteTextView) findViewById(R.id.act);
        act.addTextChangedListener(new cansaveemail());
        email_back.setOnClickListener(new emailback());
        save_emailbtn.setOnClickListener(new saveemail());
    }

    private class cansaveemail implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() >= 1)
                save_emailbtn.setVisibility(View.VISIBLE);
            else
                save_emailbtn.setVisibility(View.GONE);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class emailback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }

    public  boolean isEmail(String str) {
        String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern regex = Pattern.compile(check);
        Matcher matcher = regex.matcher(str);
        boolean isMatched = matcher.matches();
        return isMatched;
    }

    private class saveemail implements View.OnClickListener{

        @Override
        public void onClick(View view) {
         boolean isemail = isEmail(act.getText().toString());
         if(isemail){
             Toast.makeText(getApplicationContext(), "是邮箱",
                     Toast.LENGTH_SHORT).show();
             SharedPreferences sPreferences=getSharedPreferences("config", getContext().MODE_PRIVATE);
             SharedPreferences.Editor editor=sPreferences.edit();
             editor.putString("email", act.getText().toString());
             editor.commit();
             finish();
         }else{
             Toast.makeText(getApplicationContext(), "不是邮箱",
                     Toast.LENGTH_SHORT).show();
         }
        }
    }
}
