package com.lu.car_internet.activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.utils.CodeUtils;
import com.lu.car_internet.utils.HttpUtils;
import com.lu.car_internet.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterTypeActivity extends Activity {
	private EditText register_edit_account;
	private EditText register_edit_password;
	private EditText register_edit_passwordcf;
	private Button carinternet_register_btn;
	private Button register_btn_clear_password;
	private Button register_btn_clear_passwordcf;
	private ImageView vc_image; // 图片
	private EditText register_edit_code;
    private String getCode = null;
	private String strPhoneNumber;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_register);
		setup();
	}
	
	
	private void setup(){
		Intent intent = getIntent();
		strPhoneNumber = intent.getStringExtra("userphone");
		register_edit_account = (EditText) findViewById(R.id.register_edit_account);
		register_edit_account.setText(strPhoneNumber);
		register_edit_account.setEnabled(false);
		register_edit_password = (EditText) findViewById(R.id.register_edit_password);
		register_edit_passwordcf = (EditText) findViewById(R.id.register_edit_passwordcf);
		carinternet_register_btn = (Button) findViewById(R.id.carinternet_register_btn);
		register_btn_clear_password = (Button) findViewById(R.id.register_btn_clear_password);
		register_btn_clear_passwordcf = (Button) findViewById(R.id.register_btn_clear_passwordcf);
		vc_image = (ImageView) findViewById(R.id.register_vc_image);
		vc_image.setImageBitmap(CodeUtils.getInstance().getBitmap());
		register_edit_code = (EditText) findViewById(R.id.register_edit_code);
		getCode = CodeUtils.getInstance().getCode(); // 获取显示的验证码
		vc_image.setOnClickListener(new updatecode());
		register_edit_password.addTextChangedListener(new OnEditpassword());
		register_edit_passwordcf.addTextChangedListener(new OnEditpasswordcf());
		register_btn_clear_password.setOnClickListener(new clear_password());
		register_btn_clear_passwordcf.setOnClickListener(new clear_password());
		carinternet_register_btn.setOnClickListener(new OnLogin());
	}
	 
	
	@Override
	protected void onStart(){
		super.onStart();
	//	SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
	//	String username=sPreferences.getString("Name", "");
	//	register_edit_account.setText(username);
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
				register_btn_clear_password.setVisibility(View.VISIBLE);
			else
				register_btn_clear_password.setVisibility(View.GONE);
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class OnEditpasswordcf implements TextWatcher{

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
				register_btn_clear_passwordcf.setVisibility(View.VISIBLE);
			else
				register_btn_clear_passwordcf.setVisibility(View.GONE);
			
			initWidgetForCanUpdate();
			
			
			
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			
		}
		
	}
	   private class clear_password implements OnClickListener{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				switch (v.getId()) {
				case R.id.register_btn_clear_password:
					// 如果清除帐号则密码一并清除
					register_edit_password.setText("");
					break;
				case R.id.register_btn_clear_passwordcf:
					// 清除已输密码
					register_edit_passwordcf.setText("");
					break;
				default:
					break;
				}

			}
	    	
	    }
	// 根据是否可以注册密码，初始化相关控件
	 	private void initWidgetForCanUpdate(){
	 		if (canRegisterpassword()){
	 			carinternet_register_btn.setEnabled(true);
	 		}
	 		else{	
	 			carinternet_register_btn.setEnabled(false);
	 		}
	 	}

	 	// 判断当前用户注册密码是否合法，是否可以注册
	 	private boolean canRegisterpassword() {
	 		Editable user_register_password = register_edit_password.getText();
	 		Editable user_register_passwordcf = register_edit_passwordcf.getText();
	 		return !Utils.isStrEmpty(user_register_password)
	 				&& !Utils.isStrEmpty(user_register_passwordcf);
	 	}   
	private class OnLogin implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//验证码
			String v_code = register_edit_code.getText().toString().trim();
			//手机号
			String phoneaccount = register_edit_account.getText().toString().trim();
			//密码
			String userpassword = register_edit_password.getText().toString().trim();
			//确认密码
			String confimuserpassword = register_edit_passwordcf.getText().toString().trim();

			if(validatapassword(userpassword)){
				if(userpassword.equals(confimuserpassword)){
					if (v_code == null || v_code.equals("")) {
						Toast.makeText(RegisterTypeActivity.this, "验证码为空", Toast.LENGTH_SHORT).show();
					} else if (!v_code.equalsIgnoreCase(getCode)) {
						Toast.makeText(RegisterTypeActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
					} else {
						//Toast.makeText(RegisterTypeActivity.this, "验证成功", Toast.LENGTH_SHORT).show();
						String result="-1";
						Map<String,String> params=new HashMap<String,String>();
						params.put("tuse.userphone", phoneaccount);
						params.put("tuse.password", userpassword);
						result= HttpUtils.submitPostData(params,"utf-8","register");
						if(result.equals("用户注册成功")){
							Intent intentLogin = new Intent(RegisterTypeActivity.this, LoginActivity.class);
							startActivity(intentLogin);
						}else{
							if(result.equals("用户已存在")){
								Toast.makeText(RegisterTypeActivity.this, "用户已注册,请直接登录", Toast.LENGTH_SHORT).show();
							}
							if(result.equals("注册失败")){
								Toast.makeText(RegisterTypeActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
							}
							if(result.equals("系统出错")){
								Toast.makeText(RegisterTypeActivity.this, "后台服务器没有运行", Toast.LENGTH_SHORT).show();
							}
							if(result.equals("-1")){
								Toast.makeText(RegisterTypeActivity.this, "网络没有连接", Toast.LENGTH_SHORT).show();
							}
						}
					}
				}else{
					Toast.makeText(RegisterTypeActivity.this, "确认密码和输入密码不一致", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(RegisterTypeActivity.this, "请输入6-16位数字或字母任意组合", Toast.LENGTH_SHORT).show();
			}


		}
	}
	//刷新验证码
	private class updatecode implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			vc_image.setImageBitmap(CodeUtils.getInstance().getBitmap());
			getCode = CodeUtils.getInstance().getCode();
		}
	}
   private boolean validatapassword(String str){
	   if(Pattern.matches("^[0-9a-zA-Z]{6,16}", str)){
		   return true;
	   }else{
		   return false;
	   }

   }

}
