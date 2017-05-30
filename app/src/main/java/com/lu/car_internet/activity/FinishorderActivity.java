package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.lu.car_internet.R;

/**
 * Created by lu on 2017/4/29.
 */

public class FinishorderActivity extends Activity {

    private Button finishorder_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finishorder);
        finishorder_btn = (Button)findViewById(R.id.finishorder_btn);
        finishorder_btn.setOnClickListener(new finish());
    }


    private class finish implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(FinishorderActivity.this,GradientTabStripActivity.class);
            startActivity(intent);
        }
    }
}
