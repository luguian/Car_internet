package com.lu.car_internet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.lu.car_internet.R;
import com.lu.car_internet.fragments.PersonalFragment;
import com.lu.car_internet.utils.HttpUtils;
import com.lu.car_internet.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import static com.tencent.open.utils.Global.getSharedPreferences;

/**
 * Created by lu on 2017/3/25.
 */

public class CarMessageActivity extends Activity {
    private Bitmap bitmap = null;
    private ImageView car_brandmessage_photo;
    private TextView car_flagmessage;//标志
    private TextView car_typemessage;//型号
    private TextView car_bodylevel;//车身级别
    private TextView car_number;//车牌号码
    private TextView car_motornumber;//发动机号码
    private RelativeLayout car_updatemessagerl;
    private RelativeLayout querycardetailmessagerl;
    private Button confimaddcar_btn;
    private String type;
    private String bodylevel;
    private String brand;
    private String carnumber;
    private String carmotornumber;
    private String carid;
    private ImageButton imcarback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        closeStrictMode();
        setContentView(R.layout.activity_carmessage);
    }
    @Override
    public void onResume(){
        super.onResume();
        if(!Utils.isNetworkAvailable(this)){
            Utils.showDialogTip(4,this);
        }else{
            initViews();
        }

    }
    private void initViews(){
        car_flagmessage = (TextView)findViewById(R.id.car_flagmessage);
        car_typemessage = (TextView)findViewById(R.id.car_typemessage);
        car_bodylevel = (TextView)findViewById(R.id.car_bodylevel);
        car_number = (TextView)findViewById(R.id.car_number);
        car_motornumber = (TextView)findViewById(R.id.car_motornumber);
        car_updatemessagerl = (RelativeLayout)findViewById(R.id.car_updatemessagerl);
        querycardetailmessagerl = (RelativeLayout)findViewById(R.id.querycardetailmessagerl);
        confimaddcar_btn =(Button)findViewById(R.id.confimaddcar_btn);
        imcarback = (ImageButton)findViewById(R.id.imcarback);
        imcarback.setOnClickListener(new imcarback());
        confimaddcar_btn.setOnClickListener(new addmycar());
        car_updatemessagerl.setOnClickListener(new updateCarmessage());
        querycardetailmessagerl.setOnClickListener(new queryCardetailmessage());
        SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
        String state=sPreferences.getString("state", "");
        if(state.equals("addcar")||state.equals("updatecarfinish")){
            confimaddcar_btn.setVisibility(View.VISIBLE);
            car_updatemessagerl.setVisibility(View.GONE);
            querycardetailmessagerl.setVisibility(View.GONE);
        }else{
            confimaddcar_btn.setVisibility(View.GONE);
            car_updatemessagerl.setVisibility(View.VISIBLE);
            querycardetailmessagerl.setVisibility(View.VISIBLE);
        }
        Intent intent =getIntent();
        final String brandimg = intent.getStringExtra("brandpingying");
        type =intent.getStringExtra("type");
        bodylevel =intent.getStringExtra("bodylevel");
        brand =intent.getStringExtra("brand");
        carnumber =intent.getStringExtra("carnumber");
        carmotornumber =intent.getStringExtra("carmotornumber");
        carid = intent.getStringExtra("carid");
//        SharedPreferences.Editor editor = sPreferences.edit();
//        editor.putString("carid", carid);
//        editor.commit();
        car_brandmessage_photo = (ImageView) findViewById(R.id. car_brandmessage_photo);
        //创建一个新线程，用于从网络上获取图片
        new Thread(new Runnable() {

            @Override
            public void run() {
                //从网络上获取图片
                bitmap = Utils.getbitmap("http://139.199.73.19/project_car/imgs/"+brandimg+".jpg");

//                try {
//                    Thread.sleep(200);//线程休眠两秒钟
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
              //  alpha.setRepeatCount(0);//循环显示
                //发送一个Runnable对象
                car_brandmessage_photo.post(new Runnable(){


                    @Override
                    public void run() {
                        car_brandmessage_photo.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
                    }

                });

            }
        }).start();//开启线程
        car_flagmessage.setText(brand);
        car_typemessage.setText(type);
        car_bodylevel.setText(bodylevel);
        car_number.setText(carnumber);
        car_motornumber.setText(carmotornumber);

    }
private class imcarback implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        finish();
    }
}
 private class updateCarmessage implements View.OnClickListener{

     @Override
     public void onClick(View view) {
       //  car_updatemessagerl.setBackgroundColor((Color.parseColor("#66CCCCCC")));
       //  Intent intentCarMessage = new Intent(CarMessageActivity.this,BrandActivity.class);
       //  startActivity(intentCarMessage);
      SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
      SharedPreferences.Editor editor=sPreferences.edit();
      editor.putString("state", "updatecarfinish");
      editor.commit();
      Intent intentupdatecar = new Intent(CarMessageActivity.this,BrandActivity.class);
      startActivity(intentupdatecar);

     }

 }

    private class addmycar implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            SharedPreferences sPreferences = getSharedPreferences("config", MODE_PRIVATE);
            String state=sPreferences.getString("state", "");
            if(state.equals("updatecarfinish")){
                String result = "-1";
                sPreferences = getSharedPreferences("config", MODE_PRIVATE);
                String userid = sPreferences.getString("Uid", "");
                String carid = sPreferences.getString("carId", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("carId",carid);
                params.put("userId", userid);
                params.put("car_brand", brand);
                params.put("car_flag", brand);
                params.put("car_type", type);
                params.put("car_number", carnumber);
                params.put("car_motor_number", carmotornumber);
                params.put("car_bodylevel", bodylevel);
                result = HttpUtils.submitPostData(params, "utf-8", "updatecarmessage");
                if (result.equals("修改成功")) {
                    Toast.makeText(CarMessageActivity.this, result, Toast.LENGTH_LONG).show();
                    Intent intentmycar = new Intent(CarMessageActivity.this,GradientTabStripActivity.class);
                    startActivity(intentmycar);
                } else {
                    if (result.equals("修改车辆失败,请重试")) {
                        Toast.makeText(CarMessageActivity.this, result, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(CarMessageActivity.this, result, Toast.LENGTH_LONG).show();
                    }
                }
            }else {
                String result = "-1";
                sPreferences = getSharedPreferences("config", MODE_PRIVATE);
                String userid = sPreferences.getString("Uid", "");
                Map<String, String> params = new HashMap<String, String>();
                params.put("userId", userid);
                params.put("car_brand", brand);
                params.put("car_flag", brand);
                params.put("car_type", type);
                params.put("car_number", carnumber);
                params.put("car_motor_number", carmotornumber);
                params.put("car_bodylevel", bodylevel);
                result = HttpUtils.submitPostData(params, "utf-8", "addcar");
                if (result.equals("添加成功")) {
                    Toast.makeText(CarMessageActivity.this, result, Toast.LENGTH_LONG).show();
                    Intent intenthome = new Intent(CarMessageActivity.this,GradientTabStripActivity.class);
                    startActivity(intenthome);
                } else {
                    if (result.equals("添加车辆失败,请重试")) {
                        Toast.makeText(CarMessageActivity.this, result, Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(CarMessageActivity.this, result, Toast.LENGTH_LONG).show();
                    }
                }
            }
         }

    }

    private class queryCardetailmessage implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intenttainmain = new Intent(CarMessageActivity.this,QuerycarmessageActivity.class);
            intenttainmain.putExtra("carid",carid);
            startActivity(intenttainmain);
        }
    }

    @SuppressLint("NewApi")
    public static void closeStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll().penaltyLog().build());
    }


}
