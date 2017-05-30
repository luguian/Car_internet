package com.lu.car_internet.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.utils.HttpUtils;
import com.lu.car_internet.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class ForgetPwdActivity extends Activity{
	private EditText Modify_password_edit_account;
	private EditText Modify_new_password;
	private EditText Modify_new_passwordconfim;
	private Button Modify_new_passwordcf_btn;
	private Button Modify_btn_clear_password;
	private Button Modifycf_btn_clear_passwordcf;
	private String strPhoneNumber;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forget_pwd);
		setup();
	}
    private void setup(){
		Intent intent = getIntent();
		strPhoneNumber = intent.getStringExtra("userphone");
    	Modify_password_edit_account =(EditText)findViewById(R.id.Modify_password_edit_account);
		Modify_password_edit_account.setText(strPhoneNumber);
		Modify_password_edit_account.setEnabled(false);
    	Modify_new_password = (EditText) findViewById(R.id.Modify_new_password);
    	Modify_new_passwordconfim = (EditText) findViewById(R.id.Modify_new_passwordconfim);
    	Modify_new_passwordcf_btn = (Button) findViewById(R.id.Modify_new_passwordcf_btn);
    	Modify_btn_clear_password = (Button) findViewById(R.id.Modify_btn_clear_password);
    	Modifycf_btn_clear_passwordcf = (Button) findViewById(R.id.Modifycf_btn_clear_passwordcf);
    	Modify_new_password.addTextChangedListener(new OnEditpassword());
    	Modify_new_passwordconfim.addTextChangedListener(new OnEditcfpassword());
    	Modify_new_passwordcf_btn.setOnClickListener(new updatepassword());
    	Modify_btn_clear_password.setOnClickListener(new clear_password());
    	Modifycf_btn_clear_passwordcf.setOnClickListener(new clear_password());
    }
    
    private class OnEditpassword implements TextWatcher{

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (s.length() >= 1)
				Modify_btn_clear_password.setVisibility(View.VISIBLE);
			else
				Modify_btn_clear_password.setVisibility(View.GONE);
		}
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
    }
    private class OnEditcfpassword implements TextWatcher{
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// TODO Auto-generated method stub
			if (s.length() >= 1)
				Modifycf_btn_clear_passwordcf.setVisibility(View.VISIBLE);	
			else
				Modifycf_btn_clear_passwordcf.setVisibility(View.GONE);
			initWidgetForCanUpdate();
		}
		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
    }
    private class updatepassword implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String useraccount = Modify_password_edit_account.getText().toString();
			String usernewpassword = Modify_new_password.getText().toString();
			String usernewcfpassword = Modify_new_passwordconfim.getText().toString();
			if(usernewpassword.equals(usernewcfpassword)){
				String result ="-1";
				Map<String,String> params=new HashMap<String,String>();
				params.put("userphone", useraccount);
				params.put("password", usernewcfpassword);
				result= HttpUtils.submitPostData(params,"utf-8","updateNewPassword");
				if(result.equals("修改成功")){
					Toast.makeText(getApplicationContext(), "修改密码成功", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(ForgetPwdActivity.this,LoginActivity.class);
					startActivity(intent);
				}else{
					if(result.equals("用户不存在")){
						Toast.makeText(getApplicationContext(), "所修改的用户账号不存在", Toast.LENGTH_SHORT).show();
					}
					if(result.equals("修改失败,请重新再试")){
						Toast.makeText(getApplicationContext(), "修改失败,请重新再试", Toast.LENGTH_SHORT).show();
					}
					if(result.equals("系统出错")){
						Toast.makeText(getApplicationContext(), "系统出错", Toast.LENGTH_SHORT).show();

					}
				}

			}
			
		}
    	
    }
    
    private class clear_password implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.Modify_btn_clear_password:
				// 如果清除帐号则密码一并清除
				Modify_new_password.setText("");
				break;
			case R.id.Modifycf_btn_clear_passwordcf:
				// 清除已输密码
				Modify_new_passwordconfim.setText("");
				break;
			default:
				break;
			}

		}
    	
    }
 // 根据是否可以更改密码，初始化相关控件
 	private void initWidgetForCanUpdate(){
 		if (canUpdatepassword()){
 			Modify_new_passwordcf_btn.setEnabled(true);
 		}
 		else{	
 			Modify_new_passwordcf_btn.setEnabled(false);
 		}
 	}

 	// 判断当前用户输入是否合法，是否可以登录
 	private boolean canUpdatepassword() {
 		Editable user_new_password = Modify_new_password.getText();
 		Editable user_new_passwordcf = Modify_new_passwordconfim.getText();
 		return !Utils.isStrEmpty(user_new_password)
 				&& !Utils.isStrEmpty(user_new_passwordcf);
 	}   
}
