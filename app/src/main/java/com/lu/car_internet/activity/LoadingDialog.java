package com.lu.car_internet.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lu.car_internet.R;

/**
 * Created by lu on 2017/5/12.
 */

public class LoadingDialog extends Dialog {
    private TextView tv;
    private String texttoString;
    public LoadingDialog(Context context,String texttoString) {
        super(context, R.style.loadingDialogStyle);
        this.texttoString = texttoString;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_loading);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText(texttoString);
        LinearLayout linearLayout = (LinearLayout) this.findViewById(R.id.LinearLayout);
        linearLayout.getBackground().setAlpha(210);
    }
}
