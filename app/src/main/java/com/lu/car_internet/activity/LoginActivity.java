package com.lu.car_internet.activity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.lu.car_internet.R;
import com.lu.car_internet.utils.HttpUtils;
import com.lu.car_internet.utils.Utils;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;




/****
 * 
 * @author Administrator 注释：七岁小猫 时间：2015年5月12日 16:14:45 内容：登录中头像动画
 */
public class LoginActivity extends Activity {

	// Widgets
	private ImageView moImgPhoto;
	private ImageView moImgProgress;
//	private LinearLayout moLayoutWelcome;
	private View moViewSlideLine;
	private EditText moEditUsername;
	private EditText moEditPassword;
	private ImageView moImgSlider;
	private Button moBtnClearUsername;
	private Button moBtnClearPassword;
	private RelativeLayout more_carnet_rl;
    private Button mcarinternet_login_btn;
    private ImageView mcarinternet_qq_login;
   

	// Members
	private Handler moHandler;
	private boolean mbIsSlidingBack;
	private int miSliderMinX, miSliderMaxX, miLastX;
	private String msRedirectPage;
	String result ="-1";

	// Constant
	public static final int PASSWORD_MIN_LENGTH = 6;
	public static final int LOGIN_SUCCESS = 0; // 登录成功
	public static final int LOGIN_FAILED = 1; // 登录失败
	public static final int LOGIN_SLIDER_TIP = 2; // 登录页面滑块向左自动滑动
	public static final int LOGIN_PHOTO_ROTATE_TIP = 3; // 登录页面加载图片转动
    //qq登录
	private Tencent mTencent;
	private IUiListener loginListener;
	private IUiListener userInfoListener;
	private static final String APPID = "1105848992";
	private int FLAG_DISMISS = 1;
	private boolean flag = true;
	private Dialog mDialog;
	private UserInfo userInfo;
	private String scope;
	LoadingDialog dialog;
	private String imageuri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


	//	SMSSDK.initSDK(LoginActivity.this, "116b07e90b603", "f0ee05e55fc82831475fdd1261b3f53f");
		SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
		boolean isFirstRun = sPreferences.getBoolean("isFirstRun", true);
		SharedPreferences.Editor editor = sPreferences.edit();

		if(isFirstRun){
			editor.putBoolean("isFirstRun", false);
			editor.commit();
			Intent intent = new Intent();
			intent.setClass(this,GuideLauncherActivity.class);
			// intent.setClass(getActivity(),LoginActivity.class);
			startActivity(intent);
			finish();
		}else {

			setContentView(R.layout.activity_main);
			PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,"dp2HuYpHUreq902qINcupGx3");
			editor.putBoolean("isFirstRun", false);
			editor.commit();
			setHandler();
			initMembers();
			setEventListeners();
			closeStrictMode();
			initData();
		}


	}

	// 触摸登录界面收回键盘
	public boolean onTouchEvent(android.view.MotionEvent poEvent) {
		try {
			InputMethodManager loInputMgr = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			return loInputMgr.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), 0);
		} catch (Exception e) {
			return false;
		}
	}

	private void setHandler() {
		moHandler = new Handler() {
			@Override
			public void handleMessage(Message poMsg) {
				switch (poMsg.what) {
				case LOGIN_SUCCESS:
					// 登录成功
					Map<String, String> lmExtra = null;
					if (!Utils.isStrEmpty(msRedirectPage)) {
						lmExtra = new HashMap<String, String>();
						lmExtra.put("redirect", msRedirectPage);
					}
					// Utils.gotoActivity(LoginActivity.this,
					// LoginSuccessActivity.class, true, lmExtra);
					break;
				case LOGIN_FAILED:
					// 登录失败
					stopLogin();
					Toast.makeText(LoginActivity.this, (String) poMsg.obj,
							Toast.LENGTH_LONG).show();
					break;
				case LOGIN_SLIDER_TIP:
					moImgSlider.layout(miLastX, moImgSlider.getTop(), miLastX
							+ moImgSlider.getWidth(), moImgSlider.getTop()
							+ moImgSlider.getHeight());
					break;
				case LOGIN_PHOTO_ROTATE_TIP:
					moImgPhoto.setImageBitmap((Bitmap) poMsg.obj);
					break;
				}
			}
		};
	}

	@Override
	protected void onStart(){
		super.onStart();
	
	}
	@Override
	public void onPause(){
		super.onPause();
//		dialog.dismiss();
	}

	@Override
	public void onResume(){
		super.onResume();
		userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
		userInfo.getUserInfo(userInfoListener);
	}
	public void showRoundProcessDialog(Context mContext, int layout){
	        mDialog = new AlertDialog.Builder(mContext).create();
		mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
	        mDialog.show();
	        if(!mThread.isAlive()){
	            flag=true;	
	            if(mThread.getState()==Thread.State.TERMINATED){
	            	//mThread = new Thread();
	            	mThread.start();
	            }else{
	            	mThread.start();
	            }
	           
	        }else{
	        	
	        	flag=true;	
	        }
	        // 注意此处要放在show之后 否则会报异常
	        mDialog.setContentView(layout);
	       // mDialog.setCancelable(false);  //false设置点击其他地方不能取消进度条
	    }
	    
	    private Thread mThread = new Thread(){
	        @Override
	        public void run() {
	     super.run();
	     while(flag){
	         try {
	      Thread.sleep(1200);
	      Message msg = mHandler.obtainMessage();
	      msg.what = FLAG_DISMISS;
	      mHandler.sendMessage(msg);
	         } catch (InterruptedException e) {
	      e.printStackTrace();
	         }
	     }
	        }   
	    };
	    
	    private Handler mHandler = new Handler(){
	        @Override
	        public void handleMessage(Message msg) {
	     super.handleMessage(msg);
	     if(msg.what == FLAG_DISMISS)
	    	
	    	 mDialog.dismiss();
	        }
	        
	    };	   
	    public void dismiss() {	        
	        flag = false;
	    }
	@Override
	protected void onDestroy(){
		if (mTencent != null) {
			mTencent.logout(LoginActivity.this);
		}
		super.onDestroy();
		
	}
	// 实例化控件
	private void initMembers() {
		moImgPhoto = (ImageView) findViewById(R.id.login_img_photo);
		SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
		String uri = sPreferences.getString("uri", "");
		if(!uri.equals("")){
			Uri url = Uri.parse((String)uri);
			moImgPhoto.setImageURI(url);
		}
		moImgProgress = (ImageView) findViewById(R.id.login_img_progress);
//		moLayoutWelcome = (LinearLayout) findViewById(R.id.login_layout_welcome);
		moViewSlideLine = findViewById(R.id.login_view_line);
		moEditUsername = (EditText) findViewById(R.id.login_edit_username);
		String phone = sPreferences.getString("userphone", "");
		if(!phone.equals("")){
			moEditUsername.setText(phone);
		}
		moEditPassword = (EditText) findViewById(R.id.login_edit_password);
		moImgSlider = (ImageView) findViewById(R.id.login_img_slide);
		moBtnClearUsername = (Button) findViewById(R.id.login_btn_clear_username);
		moBtnClearPassword = (Button) findViewById(R.id.login_btn_clear_password);
		more_carnet_rl = (RelativeLayout) findViewById(R.id.more_carnet_rl);
		mcarinternet_login_btn = (Button) findViewById(R.id.carinternet_login_btn);
		mcarinternet_qq_login = (ImageView) findViewById(R.id.qq_carnet_login);
		mbIsSlidingBack = false;
		miLastX = 0;
		miSliderMinX = 0;
		miSliderMaxX = 0;
	}

	// 设置监听事件
	private void setEventListeners() {
		moEditUsername.addTextChangedListener(new OnEditUsername());
		moEditPassword.addTextChangedListener(new OnEditPassword());
		moBtnClearUsername.setOnClickListener(new OnClearEditText());
		moBtnClearPassword.setOnClickListener(new OnClearEditText());
		moImgSlider.setOnClickListener(new OnSliderClicked());
		moImgSlider.setOnTouchListener(new OnSliderDragged());
		mcarinternet_login_btn.setOnClickListener(new OnLoginClicked());
		more_carnet_rl.setOnClickListener(new OnMore());
		mcarinternet_qq_login.setOnClickListener(new On_qq_Login());

	}

	/************** 事件处理类 *******************************/
	// 处理用户名编辑事件
	private class OnEditUsername implements TextWatcher {

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// 1. 处理右侧清除按钮隐藏/显示
			if (s.length() >= 1)
				moBtnClearUsername.setVisibility(View.VISIBLE);
			else
				moBtnClearUsername.setVisibility(View.GONE);
			// 2. 处理滑块是否可滑动
			initWidgetForCanLogin();
		}

		@Override
		public void afterTextChanged(Editable s) {
		}
	}
	// 处理密码编辑事件
	private class OnEditPassword implements TextWatcher {
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
		}
		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			// 1. 处理右侧清空按钮显示/隐藏
			if (s.length() >= 1)
				moBtnClearPassword.setVisibility(View.VISIBLE);
			else if (s.length() == 0
					&& moBtnClearPassword.getVisibility() == View.VISIBLE)
				moBtnClearPassword.setVisibility(View.GONE);
			// 2. 处理滑块是否可滑动
			initWidgetForCanLogin();
		}

		@Override
		public void afterTextChanged(Editable s) {
		}

	}
    //qq登录
	private class On_qq_Login implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(isQQClientAvailable(LoginActivity .this)){
				showRoundProcessDialog(LoginActivity.this, R.layout.loading_process_dialog_anim);
				login();
			}else{
				Toast.makeText(getApplicationContext(), "你手机没有安装QQ,请安装!",
					     Toast.LENGTH_SHORT).show();
			}
			
		}
		
	}
	// 清除输入控件中的文字的事件处理
	private class OnClearEditText implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.login_btn_clear_username:
				// 如果清除帐号则密码一并清除
				moEditUsername.setText("");
				moEditPassword.setText("");
				break;
			case R.id.login_btn_clear_password:
				// 清除已输密码
				moEditPassword.setText("");
				break;
			default:
				break;
			}
		}
	}
    //  登录点击事件
	private class OnLoginClicked implements OnClickListener {

		@Override
		public void onClick(View v) {
			if(canLogin()){
				dialog = new LoadingDialog(LoginActivity.this,"正在登录....");
				dialog.setCanceledOnTouchOutside(false);
				dialog.show();
				String phone = moEditUsername.getText().toString();
				String password = moEditPassword.getText().toString();
				final Map<String,String>params=new HashMap<String,String>();
				params.put("userphone", phone);
				params.put("password", password);
				//result= HttpUtils.submitPostData(params,"utf-8","login");
				//Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1000);
							result= HttpUtils.submitPostData(params,"utf-8","login");
							if(result.equals("登录失败")){
							//	Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
								Message msg =  myhandler.obtainMessage();
								msg.what = 0;
								myhandler.sendMessage(msg);
							}else {
								if (result.equals("-1") || result.equals("系统出错") || result.equals("无法连接网络")) {
								//	Toast.makeText(LoginActivity.this, "无法连接服务器，请连接网络", Toast.LENGTH_LONG).show();
									Message msg =  myhandler.obtainMessage();
									msg.what = 1;
									myhandler.sendMessage(msg);
								} else {
									try {
										String[] message = result.split(",");
										if (message[1].equals("登录成功")) {
											SharedPreferences sPreferences = getSharedPreferences("config", MODE_PRIVATE);
											SharedPreferences.Editor editor = sPreferences.edit();
											editor.putString("Uid", message[0]);
											editor.putString("userphone",moEditUsername.getText().toString());
											editor.putString("qqmode","");
											editor.commit();
											Message msg =  myhandler.obtainMessage();
											msg.what = 2;
											myhandler.sendMessage(msg);
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}).start();
				
			}
			
		}
		
	}
	
	// 滑动图标点击事件
	private class OnSliderClicked implements OnClickListener {
		@Override
		public void onClick(View v) {
			// 如果不符合登录条件 则跳转到忘记密码界面
			Utils.gotoActivity(LoginActivity.this, InputphoneActivity.class,
						false, null);
			SharedPreferences sPreferences = LoginActivity.this.getSharedPreferences("config", MODE_PRIVATE);
			SharedPreferences.Editor editor=sPreferences.edit();
			editor.putString("forgetorregister", "forget");
			editor.commit();
		}
	}

	// 滑动图标滑动事件
	private class OnSliderDragged implements OnTouchListener {
		@SuppressWarnings("unused")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
//			Utils.closeKeybord(moEditPassword, LoginActivity.this);
//			Utils.closeKeybord(moEditUsername, LoginActivity.this);
//			if (canLogin() && !mbIsSlidingBack) {
//				if (miSliderMaxX == 0) {
//					miSliderMinX = moViewSlideLine.getLeft()
//							- moImgSlider.getWidth() / 2;
//					miSliderMaxX = moViewSlideLine.getRight()
//							- moImgSlider.getWidth() / 2;
//				}
//				switch (event.getAction()) {
//				case MotionEvent.ACTION_DOWN:
//					miLastX = (int) event.getRawX();
//				case MotionEvent.ACTION_MOVE:
//					int liX = (int) event.getRawX();
//					if (liX > miSliderMaxX)
//						liX = miSliderMaxX;
//					else if (liX < miSliderMinX)
//						liX = miSliderMinX;
//					if (liX != miLastX) {
//						moImgSlider.layout(liX, moImgSlider.getTop(), liX
//								+ moImgSlider.getWidth(), moImgSlider.getTop()
//								+ moImgSlider.getHeight());
//						miLastX = liX;
//						if (miLastX == miSliderMaxX) {
//							// startRotateImg();
//							String lsUsername = moEditUsername.getText()
//									.toString();
//							String lsPassword = moEditPassword.getText()
//									.toString();
//							startLogin();
//							// TODO 调用借口
//						}
//					}
//					break;
//				case MotionEvent.ACTION_UP:
//				    if ((int) event.getRawX() < miSliderMaxX)
//						//showToast("wo");
//						//slideBack();
//					break;
//				}
//
//			}
			return false;
		}
	}

	// 注册事件
	private class OnMore implements OnClickListener {
		@Override
		public void onClick(View v) {			
			// Utils.gotoActivity(LoginActivity.this,
			// RegisterTypeActivity.class,
			// false, null);
			Dialog dialog = new MyDialog(LoginActivity.this, R.style.dialog);  
	        // dialog.setContentView(R.layout.register_moredialog); 
	        WindowManager m = getWindowManager();
	        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
	        WindowManager.LayoutParams p = dialog.getWindow().getAttributes(); // 获取对话框当前的参数值
	        p.height = (int) (d.getHeight() * 0.2); // 高度设置为屏幕的0.6
	        p.width = (int) (d.getWidth() * 0.78); // 宽度设置为屏幕的0.65
	        dialog.getWindow().setAttributes(p);
	        dialog.show();
	       
	        
		}
	}

	// 根据是否可以登录，初始化相关控件
	private void initWidgetForCanLogin() {
		if (canLogin()){
			mcarinternet_login_btn.setEnabled(true);
			//mcarinternet_login_btn.setBackgroundColor((Color.parseColor("#CC1E90FF")));
		}
		else{
			moImgSlider.setImageResource(R.drawable.ic_ask_circle);
		    mcarinternet_login_btn.setEnabled(false);
		   // mcarinternet_login_btn.setBackgroundColor((Color.parseColor("#661E90FF")));

		}
	}

	// 判断当前用户输入是否合法，是否可以登录
	private boolean canLogin() {
		Editable loUsername = moEditUsername.getText();
		Editable loPassword = moEditPassword.getText();
		return !Utils.isStrEmpty(loUsername)
				&& loPassword.length() >= PASSWORD_MIN_LENGTH;
	}

	// 滑块向会自动滑动
	private void slideBack() {
		new Thread() {
			@Override
			public void run() {
				mbIsSlidingBack = true;
				while (miLastX > miSliderMinX) {
					miLastX -= 5;
					if (miLastX > miSliderMinX)
						miLastX = miSliderMinX;
					Message loMsg = new Message();
					loMsg.what = LOGIN_SLIDER_TIP;
					moHandler.sendMessage(loMsg);
					try {
						Thread.sleep(3);
					} catch (InterruptedException e) {
					}
				}
				mbIsSlidingBack = false;
			}
		}.start();
	}

	// 动画开启
	private void startLogin() {
		Animation loAnimRotate = AnimationUtils.loadAnimation(this,
				R.anim.rotate);
		Animation loAnimScale = AnimationUtils.loadAnimation(this,
				R.anim.login_photo_scale_small);
		// 匀速动画
		LinearInterpolator linearInterpolator = new LinearInterpolator();
		// 加速动画
		// AccelerateInterpolator accelerateInterpolator = new
		// AccelerateInterpolator();
		// 弹跳动画
		// BounceInterpolator bounceInterpolator = new BounceInterpolator();

		loAnimRotate.setInterpolator(linearInterpolator);
		loAnimScale.setInterpolator(linearInterpolator);
		moImgProgress.setVisibility(View.VISIBLE);
		moImgProgress.startAnimation(loAnimRotate);
		moImgPhoto.startAnimation(loAnimScale);

		moImgSlider.setVisibility(View.GONE);
		moViewSlideLine.setVisibility(View.GONE);
		moEditUsername.setVisibility(View.GONE);
		moEditPassword.setVisibility(View.GONE);
		moBtnClearUsername.setVisibility(View.GONE);
		moBtnClearPassword.setVisibility(View.GONE);
		more_carnet_rl.setVisibility(View.GONE);


	//	moLayoutWelcome.setVisibility(View.VISIBLE);
	}

	// 动画结束
	private void stopLogin() {
		Animation loAnimScale = AnimationUtils.loadAnimation(this,
				R.anim.login_photo_scale_big);
		LinearInterpolator loLin = new LinearInterpolator();
		loAnimScale.setInterpolator(loLin);
		moImgProgress.clearAnimation();
		moImgProgress.setVisibility(View.GONE);
		moImgPhoto.clearAnimation();
		moImgPhoto.startAnimation(loAnimScale);

		moImgSlider.setVisibility(View.VISIBLE);
		moViewSlideLine.setVisibility(View.VISIBLE);
		moEditUsername.setVisibility(View.VISIBLE);
		moEditPassword.setVisibility(View.VISIBLE);
		moBtnClearUsername.setVisibility(View.VISIBLE);
		moBtnClearPassword.setVisibility(View.VISIBLE);
		more_carnet_rl.setVisibility(View.VISIBLE);

	//	moLayoutWelcome.setVisibility(View.GONE);
	}

	private void showToast(String strToast) {
		Toast.makeText(this, strToast, Toast.LENGTH_SHORT).show();
	}


	//判断qq是否安装
	public static boolean isQQClientAvailable(Context context) {  
	    final PackageManager packageManager = context.getPackageManager();  
	    List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);  
	    if (pinfo != null) {  
	        for (int i = 0; i < pinfo.size(); i++) {  
	            String pn = pinfo.get(i).packageName;  
	             
	            if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {  
	                return true;  
	            }  
	        }  
	    }  
	    return false;  
	}  
	private void initData() {
		mTencent = Tencent.createInstance(APPID, LoginActivity.this);
		//要所有权限，不用再次申请增量权限，这里不要设置成get_user_info,add_t
		scope = "all";
		loginListener = new IUiListener() {
			@Override
			public void onError(UiError arg0) {
				// TODO Auto-generated method stub

			}
			@Override
			public void onComplete(Object value) {
				// TODO Auto-generated method stub
				
				System.out.println("有数据返回..");
			   
				if (value == null) {
					return;
				}
				try {
					JSONObject jo = (JSONObject) value;
					String msg = jo.getString("msg");
					System.out.println("json=" + String.valueOf(jo));
					System.out.println("msg="+msg);
					if ("sucess".equals(msg)) {
						Toast.makeText(LoginActivity.this, "登录成功",
								Toast.LENGTH_LONG).show();
						String openID = jo.getString("openid");
						String accessToken = jo.getString("access_token");
						String expires = jo.getString("expires_in");
						mTencent.setOpenId(openID);
						mTencent.setAccessToken(accessToken, expires);
					}

				} catch (Exception e) {
					// TODO: handle exception
				}

			}

			@Override
		public void onCancel() {
				// TODO Auto-generated method stub

			}
		};
		
		userInfoListener = new IUiListener() {
			
			@Override
			public void onError(UiError arg0) {
				// TODO Auto-generated method stub				
			}
			
			/**
			 * {"is_yellow_year_vip":"0","ret":0,
			 * "figureurl_qq_1":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/40",
			 * "figureurl_qq_2":"http:\/\/q.qlogo.cn\/qqapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
			 * "nickname":"攀爬←蜗牛","yellow_vip_level":"0","is_lost":0,"msg":"",
			 * "city":"黄冈","
			 * figureurl_1":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/50",
			 * "vip":"0","level":"0",
			 * "figureurl_2":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/100",
			 * "province":"湖北",
			 * "is_yellow_vip":"0","gender":"男",
			 * "figureurl":"http:\/\/qzapp.qlogo.cn\/qzapp\/1104732758\/015A22DED93BD15E0E6B0DDB3E59DE2D\/30"}
			 */
			@Override
			public void onComplete( final Object arg0) {
				// TODO Auto-generated method stub
				if(arg0 == null){
					return;
				}
				try {
					JSONObject jo = (JSONObject) arg0;
					int ret = jo.getInt("ret");
					System.out.println("json=" + String.valueOf(jo));
					if(ret == 100030){
						Toast.makeText(getApplicationContext(), "456",
								Toast.LENGTH_SHORT).show();
						//权限不够，需要增量授权
						Runnable r = new Runnable() {
							public void run() {
								 JSONObject json = (JSONObject)arg0;
								  
								  mTencent.reAuth(LoginActivity.this, "all", new IUiListener() {
								
									@Override
									public void onError(UiError arg0) {
										// TODO Auto-generated method stub
										
									}
									
									@Override
									public void onComplete(Object arg0) {								    
										
									}
									
									@Override
									public void onCancel() {
										// TODO Auto-generated method stub
										
									}
								});
							}
						};
						
						LoginActivity.this.runOnUiThread(r);

					}else{
//						String nickName = jo.getString("nickname");
//						String gender = jo.getString("gender");
						imageuri = jo.getString("figureurl_qq_2");

					//	et1.setText(nickName);
						
					//	try {
                   //        bitmap = Util.getbitmap(jo.getString("figureurl_qq_2"));
                    //    } catch (JSONException e) {
                            // TODO Auto-generated catch block
                    //        e.printStackTrace();
                    //    }
					//	userlogo.setImageBitmap(bitmap);
					//	Toast.makeText(MainActivity.this, "你好，" + nickName, Toast.LENGTH_LONG).show();
					}
					
				} catch (Exception e) {
					// TODO: handle exception
				}				
			}			
			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}
		};
	}
	private void login() {
			mTencent.login(LoginActivity.this, scope, loginListener);
	}
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (requestCode == Constants.REQUEST_API) {
			if (resultCode == Constants.RESULT_LOGIN) {
				Tencent.handleResultData(data, loginListener);
			}
			super.onActivityResult(requestCode, resultCode, data);
		}
		finish();
		SharedPreferences sPreferences = LoginActivity.this.getSharedPreferences("config", MODE_PRIVATE);
		SharedPreferences.Editor editor=sPreferences.edit();
		editor.putString("imageuri", imageuri);
		editor.putString("qqmode","qqlogin");
		editor.putString("Uid", "");
		editor.commit();
		Intent intent = new Intent();
		intent.setClass(LoginActivity.this,GradientTabStripActivity.class);
		startActivity(intent);

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}	
	@SuppressLint("NewApi")
	public static void closeStrictMode() {
	StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
	.detectAll().penaltyLog().build());
	}


	// 此方法在主线程中调用，可以更新UI
	Handler myhandler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			// 处理消息时需要知道是成功的消息还是失败的消息
			switch (msg.what) {
				case 0:
					//Toast.makeText(LoginActivity.this, "更新成功",Toast.LENGTH_LONG).show();
					dialog.dismiss();
					Toast.makeText(LoginActivity.this, "用户名密码错误", Toast.LENGTH_LONG).show();
					break;
				case 1:
					dialog.dismiss();
					Toast.makeText(LoginActivity.this, "无法连接服务器，请连接网络", Toast.LENGTH_LONG).show();
					break;
				case 2:
					dialog.dismiss();
					Intent intent = new Intent();
					intent.setClass( LoginActivity.this,GradientTabStripActivity.class);
					startActivity(intent);
					break;
				default:
					break;
			}

		}
	};

}
