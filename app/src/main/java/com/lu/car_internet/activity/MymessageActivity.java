package com.lu.car_internet.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lu.car_internet.R;

/**
 * Created by lu on 2017/4/2.
 */

public class MymessageActivity extends Activity {
    private ImageView mymessageback;
    private ImageView personal_photo;
    private TextView nick_namevalue;
    private TextView personal_emailvalue;
    private TextView personal_addressvalue;
    private TextView personal_idvalue;
    private TextView sex_value;
    private TextView age_value;
    private TextView profession_value;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mymessage);

    }
    private void init(){
        mymessageback = (ImageView) findViewById(R.id.mymessageback);
        personal_photo = (ImageView) findViewById(R.id.personal_photo);
        nick_namevalue= (TextView) findViewById(R.id.nick_namevalue);
        personal_emailvalue = (TextView) findViewById(R.id.personal_emailvalue);
        personal_addressvalue = (TextView) findViewById(R.id.personal_addressvalue);
        personal_idvalue = (TextView) findViewById(R.id.personal_idvalue);
        sex_value = (TextView) findViewById(R.id.sex_value);
        age_value = (TextView) findViewById(R.id.age_value);
        profession_value = (TextView) findViewById(R.id.profession_value);
        mymessageback.setOnClickListener(new mymessageback());


    }

    private class  mymessageback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }



}
