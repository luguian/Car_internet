package com.lu.car_internet.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import com.lu.car_internet.R;
import com.lu.car_internet.beans.CarMessage;
import com.lu.car_internet.utils.HttpUtils;
import com.lu.car_internet.widget.BottomPopupOption;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.tencent.open.utils.Global.getContext;
import static com.tencent.open.utils.Global.getSharedPreferences;

/**
 * Created by lu on 2017/5/3.
 */

public class PersonalmessageActivity extends Activity {
    private RelativeLayout personal_professionrl;
    private RelativeLayout personal_sexrl;
    private RelativeLayout personal_agerl;
    private RelativeLayout message_emailrl;
    private RelativeLayout address_valuerl;
    private RelativeLayout pensonnumberrl;
    private PopupWindow popWindow;
    private TextView personal_sexvalue;
    private TextView personal_age;
    private RadioButton men_option;
    private RadioButton women_option;
    private TextView email_number;
    private TextView profession_value;
    private TextView address_value;
    private TextView pensonnumber_value;
    private String result;
    private Button addpersonalmessage_btn;
    private Button savepersonalmessage_btn;
    private String userid;
    private String usermessageId;
    private ImageView personal_photo;
    private final int IMAGE_RESULT_CODE = 2;// 表示打开照相机
    private final int PICK = 1;// 选择图片库
    private ImageButton personalmessageback;
    private Dialog personalDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalmessage);
        ActivityCompat.requestPermissions(PersonalmessageActivity.this, new String[]{
                Manifest.permission.CAMERA}, 1);
        personal_professionrl = (RelativeLayout)findViewById(R.id.personal_professionrl);
        personal_sexrl = (RelativeLayout)findViewById(R.id.personal_sexrl);
        personal_agerl = (RelativeLayout)findViewById(R.id.personal_agerl);
        personal_sexvalue = (TextView)findViewById(R.id.personal_sexvalue);
        message_emailrl = (RelativeLayout)findViewById(R.id.message_emailrl);
        address_valuerl = (RelativeLayout)findViewById(R.id.address_valuerl);
        pensonnumberrl = (RelativeLayout)findViewById(R.id.pensonnumberrl);
        email_number = (TextView)findViewById(R.id.email_number);
        profession_value = (TextView)findViewById(R.id.profession_value);
        pensonnumber_value = (TextView)findViewById(R.id.pensonnumber_value);
        addpersonalmessage_btn = (Button)findViewById(R.id.addpersonalmessage_btn);
        savepersonalmessage_btn = (Button)findViewById(R.id.savepersonalmessage_btn);
        personal_photo = (ImageView)findViewById(R.id.personal_photo);
        personalmessageback = (ImageButton)findViewById(R.id.personalmessageback);
        personalmessageback.setOnClickListener(new personalmessageback());
        personal_photo.setOnClickListener(new choosepersonalimage());
        message_emailrl.setOnClickListener(new email());
        personal_age = (TextView)findViewById(R.id.personal_age);
        address_value = (TextView)findViewById(R.id.address_value);
        personal_sexrl.setOnClickListener(new choosesex());
        personal_professionrl.setOnClickListener(new querymypersonal());
        personal_agerl.setOnClickListener(new chooseage());
        address_valuerl.setOnClickListener(new editaddress());
        pensonnumberrl.setOnClickListener(new editidnumber());
        addpersonalmessage_btn.setOnClickListener(new addpersonalmessage());
        savepersonalmessage_btn.setOnClickListener(new updatemymessage());
        SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
        userid=sPreferences.getString("Uid", "");
        final Map<String,String> params=new HashMap<String,String>();
        params.put("userid", userid);
        personalDialog = new LoadingDialog(PersonalmessageActivity.this,"正在查询....");
        personalDialog.setCanceledOnTouchOutside(false);
        personalDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    result = HttpUtils.submitPostData(params,"utf-8","Showpersonalmessage");
                    if(result.equals("-1")){
                        Message msg =  personalhandler.obtainMessage();
                        msg.what = 0;
                        personalhandler.sendMessage(msg);
                    }else{
                        if(result.equals("系统出错")){
                            Message msg =  personalhandler.obtainMessage();
                            msg.what = 1;
                            personalhandler.sendMessage(msg);
                        }
                        if(result.equals("没有个人信息")){
                            Message msg =  personalhandler.obtainMessage();
                            msg.what = 2;
                            personalhandler.sendMessage(msg);
                        }
                        else{
                            Message msg =  personalhandler.obtainMessage();
                            msg.what = 3;
                            personalhandler.sendMessage(msg);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();



    }
  private class personalmessageback implements View.OnClickListener{

      @Override
      public void onClick(View view) {
          finish();
      }
  }
    private class querymypersonal implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(PersonalmessageActivity.this,SelectprofessionActivity.class);
            startActivity(intent);
        }
    }
    private class choosesex implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            popWindow();
        }
    }

    @SuppressLint("NewApi")
    private void popWindow(){
        LayoutInflater inflater = LayoutInflater.from(this);//获取一个填充器
        View view = inflater.inflate(R.layout.activity_sexdialog, null);//填充我们自定义的布局
        RelativeLayout man_selected = (RelativeLayout)view.findViewById(R.id.man_selected);
        RelativeLayout women_selected = (RelativeLayout)view.findViewById(R.id.women_selected);
        man_selected.setOnClickListener(new chooseman());
        women_selected.setOnClickListener(new choosewomen());
        men_option =( RadioButton)view.findViewById(R.id.men_option);
        men_option.setOnClickListener(new choosemenbutton());
        women_option =( RadioButton)view.findViewById(R.id.women_option);
        women_option .setOnClickListener(new choosewomenbutton());
        String sex = personal_sexvalue.getText().toString();
        if(sex.equals("男")){
            men_option.setChecked(true);
            women_option.setChecked(false);
        }else{
            if(sex.equals("女")){
                men_option.setChecked(false);
                women_option.setChecked(true);
            }else{
                men_option.setChecked(false);
                women_option.setChecked(false);
            }
        }
        Display display = getWindowManager().getDefaultDisplay();//得到当前屏幕的显示器对象
        Point size = new Point();//创建一个Point点对象用来接收屏幕尺寸信息
        display.getSize(size);//Point点对象接收当前设备屏幕尺寸信息
        int width = size.x;//从Point点对象中获取屏幕的宽度(单位像素)
        int height = size.y;//从Point点对象中获取屏幕的高度(单位像素)
        //  Log.v("zxy", "width="+width+",height="+height);//width=480,height=854可知手机的像素是480x854的
        //创建一个PopupWindow对象，第二个参数是设置宽度的，用刚刚获取到的屏幕宽度乘以2/3，取该屏幕的2/3宽度，从而在任何设备中都可以适配，高度则包裹内容即可，最后一个参数是设置得到焦点
        popWindow = new PopupWindow(view, 4*width/5, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());//设置PopupWindow的背景为一个空的Drawable对象，如果不设置这个，那么PopupWindow弹出后就无法退出了
        popWindow.setOutsideTouchable(true);//设置是否点击PopupWindow外退出PopupWindow
        WindowManager.LayoutParams params = getWindow().getAttributes();//创建当前界面的一个参数对象
        params.alpha = 0.8f;//设置参数的透明度为0.8，透明度取值为0~1，1为完全不透明，0为完全透明，因为android中默认的屏幕颜色都是纯黑色的，所以如果设置为1，那么背景将都是黑色，设置为0，背景显示我们的当前界面

        getWindow().setAttributes(params);//把该参数对象设置进当前界面中
        popWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {//设置PopupWindow退出监听器
            @Override
            public void onDismiss() {//如果PopupWindow消失了，即退出了，那么触发该事件，然后把当前界面的透明度设置为不透明
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1.0f;//设置为不透明，即恢复原来的界面
                getWindow().setAttributes(params);
            }
        });
        //第一个参数为父View对象，即PopupWindow所在的父控件对象，第二个参数为它的重心，后面两个分别为x轴和y轴的偏移量
        popWindow.showAtLocation(inflater.inflate(R.layout.activity_personalmessage, null), Gravity.CENTER,  0,  0);

    }

    private class chooseman implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            men_option.setChecked(true);
            women_option.setChecked(false);
            popWindow.dismiss();
            personal_sexvalue.setText("男");
        }
    }

    private class choosemenbutton implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            women_option.setChecked(false);
            popWindow.dismiss();
            personal_sexvalue.setText("男");
        }
    }
    private class choosewomen implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            women_option.setChecked(true);
            men_option.setChecked(false);
            popWindow.dismiss();
            personal_sexvalue.setText("女");
        }
    }

    private class choosewomenbutton implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            men_option.setChecked(false);
            popWindow.dismiss();
            personal_sexvalue.setText("女");
        }
    }

    private class chooseage implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            showDialog();
        }
    }
    private class email implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(PersonalmessageActivity.this,EditemailActivity.class);
            startActivity(intent);
        }
    }
    private class editaddress implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(PersonalmessageActivity.this,EditaddressActivity.class);
            startActivity(intent);
        }
    }
    private class editidnumber implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(PersonalmessageActivity.this,EditidnumberActivity.class);
            startActivity(intent);
        }
    }
    private void showDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.activity_agedialog, null);
        dialogBuilder.setView(dialogView);
        LoopView loopView = (LoopView) dialogView.findViewById(R.id.loopView);
        ArrayList<String> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(+i+"岁");
        }
        loopView.setItems(list);
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        // 滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                personal_age.setText(index+1+"岁");
                alertDialog.dismiss();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
        String uri = sPreferences.getString("uri", "");
        if(!uri.equals("")){
            Uri url = Uri.parse((String)uri);
            personal_photo.setImageURI(url);
        }
        String email=sPreferences.getString("email", "");
        String profession=sPreferences.getString("profession", "");
        String address = sPreferences.getString("address", "");
        String idnumber = sPreferences.getString("idnumber", "");
        if(email_number.getText().toString()!=null&&email.equals("")){

        }else{
            if(email!=null&&!email.equals("")){
                email_number.setText(email);
            }else{
                email_number.setText("未设置");
            }
        }
       if(profession_value.getText().toString()!=null&&profession.equals("")){

       }else{
           if(profession!=null&&!profession.equals("")){
               profession_value.setText(profession);
           }else{
               profession_value.setText("未设置");
           }
       }
       if(address_value.getText().toString()!=null&&address.equals("")){

       }else{
           if(address!=null&&!address.equals("")){
               address_value.setText(address);
           }else{
               address_value.setText("未设置");
           }
       }
      if( pensonnumber_value.getText().toString()!=null&&idnumber.equals("")){

      }else{
          if(idnumber!=null&&!idnumber.equals("")){
              pensonnumber_value.setText(idnumber);
          }else{
              pensonnumber_value.setText("未设置");
          }
      }

    }

    protected void onPause(){
        super.onPause();
        personalDialog.dismiss();

    }
    private void querymymessage(String jsonString){
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            //返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray("user");
            for(int i= 0;i<jsonArray.length();i++){
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
              //  JSONObject jsonobjecttime=jsonObject2.getJSONObject("id");
                String usermessageAddress= jsonObject2.getString("usermessageAddress");
                String usermessageAge= jsonObject2.getString("usermessageAge");
                String usermessageEmail= jsonObject2.getString("usermessageEmail");
                String usermessagePensonnumber= jsonObject2.getString("usermessagePensonnumber");
                String usermessageProfession= jsonObject2.getString("usermessageProfession");
                String usermessageSex= jsonObject2.getString("usermessageSex");
                usermessageId = jsonObject2.getString("usermessageId");
                SharedPreferences sPreferences=getSharedPreferences("config", getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor=sPreferences.edit();
                editor.putString("usermessageId", usermessageId);
                editor.commit();
                address_value.setText(usermessageAddress);
                personal_sexvalue.setText(usermessageSex);
                email_number.setText(usermessageEmail);
                personal_age.setText(usermessageAge);
                profession_value.setText(usermessageProfession);
                pensonnumber_value.setText(usermessagePensonnumber);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private class addpersonalmessage implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            String address = address_value.getText().toString();
            String sex = personal_sexvalue.getText().toString();
            String email = email_number.getText().toString();
            String age = personal_age.getText().toString();
            String profession = profession_value.getText().toString();
            String idnumber = pensonnumber_value.getText().toString();
            if(!address.equals("未设置")&&!sex.equals("未设置")&&!
                    email.equals("未设置")&&!age.equals("未设置")
                    &&!profession.equals("未设置")&&!idnumber.equals("未设置")){
                Map<String,String> params=new HashMap<String,String>();
                params.put("userId", userid);
                params.put("sex", sex);
                params.put("age", age);
                params.put("profession",profession);
                params.put("address",address);
                params.put("email",email);
                params.put("idnumber",idnumber);
                result = HttpUtils.submitPostData(params,"utf-8","managerUserMessage");
                if(result.equals("完善成功")){
                    Toast.makeText(getApplicationContext(), "信息绑定成功",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass( PersonalmessageActivity.this,PersonalmessageActivity.class);
                    startActivity(intent);
                    addpersonalmessage_btn.setVisibility(View.GONE);
                    savepersonalmessage_btn.setVisibility(View.VISIBLE);
                }else{
                    if(result.equals("完善失败")){
                        Toast.makeText(getApplicationContext(), "绑定失败,请重新尝试",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "网络连接异常",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }else{
                Toast.makeText(getApplicationContext(), "没有填写完整的信息",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class updatemymessage implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            String address = address_value.getText().toString();
            String sex = personal_sexvalue.getText().toString();
            String email = email_number.getText().toString();
            String age = personal_age.getText().toString();
            String profession = profession_value.getText().toString();
            String idnumber = pensonnumber_value.getText().toString();
            SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
            userid=sPreferences.getString("Uid", "");
            usermessageId=sPreferences.getString("usermessageId", "");
            if(!address.equals("未设置")&&!sex.equals("未设置")&&!
                    email.equals("未设置")&&!age.equals("未设置")
                    &&!profession.equals("未设置")&&!idnumber.equals("未设置")){
                Map<String,String> params=new HashMap<String,String>();
                params.put("userId", userid);
                params.put("usermessageId",usermessageId);
                params.put("sex", sex);
                params.put("age", age);
                params.put("profession",profession);
                params.put("address",address);
                params.put("email",email);
                params.put("idnumber",idnumber);
                result = HttpUtils.submitPostData(params,"utf-8","updatepersonalmessage");
                if(result.equals("修改成功")){
                    Toast.makeText(getApplicationContext(), "信息修改成功",
                            Toast.LENGTH_SHORT).show();
                    addpersonalmessage_btn.setVisibility(View.GONE);
                    savepersonalmessage_btn.setVisibility(View.VISIBLE);
                }else {
                   if(result.equals("修改失败")) {
                       Toast.makeText(getApplicationContext(), "请重新尝试",
                               Toast.LENGTH_SHORT).show();
                   }else {
                       Toast.makeText(getApplicationContext(), "网络连接异常",
                               Toast.LENGTH_SHORT).show();
                   }
                }
            }else{
                Toast.makeText(getApplicationContext(), "没有填写完整的信息",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class choosepersonalimage implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            final BottomPopupOption bottomPopupOption = new BottomPopupOption(PersonalmessageActivity.this);
            bottomPopupOption.setItemText("拍照", "从本地图库选择");
            bottomPopupOption.showPopupWindow();

            bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if(position == 0){
                        PackageManager packageManager = getPackageManager();
                        int permission = packageManager.checkPermission("android.permission.CAMERA", "com.lu.car_internet");
                        if(PackageManager.PERMISSION_GRANTED == permission){
                            //有这个权限
                            Intent intent = new Intent(
                                    android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, IMAGE_RESULT_CODE);// 打开照相机
                            bottomPopupOption.dismiss();
                        }else {
                            //没有这个权限
                            Toast.makeText(getApplicationContext(), "没有授予权限",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                    if(position == 1){
                        Intent intent = new Intent(
                                Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, PICK);// 打开照相机
                        bottomPopupOption.dismiss();
                    }

                }
            });
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // 表示 调用照相机拍照
            case IMAGE_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    personal_photo.setImageBitmap(bitmap);
                }
                break;
            // 选择图片库的图片
            case PICK:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    personal_photo.setImageURI(uri);
                    SharedPreferences sPreferences=getSharedPreferences("config", getContext().MODE_PRIVATE);
                    SharedPreferences.Editor editor=sPreferences.edit();
                    editor.putString("uri", uri.toString());
                    editor.commit();
                }
                break;
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 初始化控件

                }else{

                }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // 此方法在主线程中调用，可以更新UI
    Handler personalhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 处理消息时需要知道是成功的消息还是失败的消息
            switch (msg.what) {
                case 0:
                    personalDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "网络异常,请重新再试",
                               Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    personalDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "请重新查询",
                    Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    personalDialog.dismiss();
                    personal_sexvalue.setText("未设置");
                    email_number.setText("未设置");
                    address_value.setText("未设置");
                    personal_age.setText("未设置");
                    profession_value.setText("未设置");
                    pensonnumber_value.setText("未设置");
                    addpersonalmessage_btn.setVisibility(View.VISIBLE);
                    savepersonalmessage_btn.setVisibility(View.GONE);
                    break;
                case 3:
                    personalDialog.dismiss();
                    querymymessage(result);
                    addpersonalmessage_btn.setVisibility(View.GONE);
                    savepersonalmessage_btn.setVisibility(View.VISIBLE);
                    break;
                case 4:

                default:
                    break;
            }

        }
    };

}
