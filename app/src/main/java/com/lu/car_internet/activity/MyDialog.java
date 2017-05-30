package com.lu.car_internet.activity;

import java.util.HashMap;

import com.lu.car_internet.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import android.widget.Toast;


import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.RegisterPage;

import static android.content.Context.MODE_PRIVATE;
public class MyDialog extends Dialog {
	private RelativeLayout mcar_register_rl;
	private RelativeLayout mcar_help_login_rl;
	Context context; 
	public MyDialog(Context context) {
		super(context);
		this.context = context;    
	}
	public MyDialog(Context context, int theme)
	{         
	super(context, theme);         
	this.context = context;     
	}
	@Override    
	protected void onCreate(Bundle savedInstanceState) 
	{         // TODO Auto-generated method stub         
	super.onCreate(savedInstanceState);         
	this.setContentView(R.layout.register_moredialog);
	initview();
	initListener();
 }   
	
	//初始化组件
	private void initview(){
		mcar_register_rl = (RelativeLayout) findViewById(R.id.car_register_rl);
		mcar_help_login_rl = (RelativeLayout)findViewById(R.id.car_help_rl);
	}
	//设置监听事件
	private void initListener(){
    {

    	mcar_register_rl.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
		    mcar_register_rl.setBackgroundColor((Color.parseColor("#66BEBEBE")));
		    dismiss();
				SharedPreferences sPreferences = context.getSharedPreferences("config", MODE_PRIVATE);
				SharedPreferences.Editor editor=sPreferences.edit();
				editor.putString("forgetorregister", "register");
				editor.commit();
			Intent inputphoneintent = new Intent(context,InputphoneActivity.class);
			context.startActivity(inputphoneintent);
			}
		
		});
    	mcar_help_login_rl.setOnClickListener(new android.view.View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}
		});
   }
 }
	 
	
}
