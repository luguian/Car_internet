package com.lu.car_internet.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lu.car_internet.R;
import com.lu.car_internet.adapters.CarMessageAdapter;
import com.lu.car_internet.adapters.GasolineorderAdapter;
import com.lu.car_internet.beans.CarMessage;
import com.lu.car_internet.config.CharacterParser;
import com.lu.car_internet.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lu on 2017/3/28.
 */

public class MycarActivity extends Activity {


    private ListView result_mycar;
    private ImageView addcar_img;
    private RelativeLayout mycar;
    private RelativeLayout addcar_rl;
    private RelativeLayout loading_rl;
    private String result = "-1";
    private String brandpingyin;
    private CarMessageAdapter mAdapter;
    private CharacterParser characterParser;
    //private List<CarMessage> carmessages;
    private int x;//横坐标位置
    private int y;//纵坐标位置
    private PopupWindow popWindow;
    private ImageButton mycarback;
    private ImageView addcar_photo;
    private Dialog mycarDialog;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 循环添加列表项显示自定义adapter
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        closeStrictMode();
        setContentView(R.layout.activity_mycar);
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        result_mycar = (ListView)findViewById(R.id.result_mycar);
        addcar_img = (ImageView)findViewById(R.id.addcar_img);
        addcar_rl = (RelativeLayout)findViewById(R.id.addcar_rl);
        loading_rl = (RelativeLayout)findViewById(R.id.loading_rl);
        mycar =(RelativeLayout)findViewById(R.id.mycar);
        mycarback = (ImageButton)findViewById(R.id.mycarback);
        addcar_photo = (ImageView)findViewById(R.id.addcar_photo);
        addcar_photo.setOnClickListener(new Onaddcar());
        mycarback.setOnClickListener(new finishcarback());
        addcar_img.setOnClickListener(new Onaddcar());
        result_mycar.setOnTouchListener(new TouchListenerImp());
        result_mycar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
                SharedPreferences.Editor editor = sPreferences.edit();
                editor.putString("state", "updatecar");
                editor.putString("carId",((CarMessage)mAdapter.getItem(position)).getCar_id());
                editor.commit();
                Intent intentcardetails = new Intent(MycarActivity.this,CarMessageActivity.class);
                intentcardetails.putExtra("brandpingying",characterParser.getSelling(((CarMessage)mAdapter.getItem(position)).getCar_brand()));
                intentcardetails.putExtra("carid",((CarMessage)mAdapter.getItem(position)).getCar_id());
                intentcardetails.putExtra("brand",((CarMessage)mAdapter.getItem(position)).getCar_brand());
                intentcardetails.putExtra("type",((CarMessage)mAdapter.getItem(position)).getCar_type());
                intentcardetails.putExtra("bodylevel",((CarMessage)mAdapter.getItem(position)).getCar_bodylevel());
                intentcardetails.putExtra("carnumber",((CarMessage)mAdapter.getItem(position)).getCar_number());
                intentcardetails.putExtra("carmotornumber",((CarMessage)mAdapter.getItem(position)).getCar_motor_number());
                startActivity(intentcardetails);
            }
        });
        result_mycar.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
                SharedPreferences.Editor editor = sPreferences.edit();
                editor.putString("carId",((CarMessage)mAdapter.getItem(position)).getCar_id());
                editor.putString("carbrand",((CarMessage)mAdapter.getItem(position)).getCar_brand());
                editor.commit();
                popWindow();
                return true;
            }
        });
        Intent intent = getIntent();
        String Uid=intent.getStringExtra("Uid");
        final Map<String,String> params=new HashMap<String,String>();
        params.put("userid", Uid);
        mycarDialog = new LoadingDialog(MycarActivity.this,"正在查询....");
        mycarDialog.setCanceledOnTouchOutside(false);
        mycarDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    result = HttpUtils.submitPostData(params,"utf-8","showCars");
                    if(result.equals("-1")||result.equals("系统出错")){
//                        Toast.makeText(MycarActivity.this, "无法连接服务器，请连接网络", Toast.LENGTH_LONG).show();
//                        loading_rl.setVisibility(View.VISIBLE);
                        Message msg =  mycarhandler.obtainMessage();
                        msg.what = 0;
                        mycarhandler.sendMessage(msg);
                    }else{
                        if(result.equals("没有车辆")){
                            Message msg =  mycarhandler.obtainMessage();
                            msg.what = 1;
                            mycarhandler.sendMessage(msg);
                        }
                        else{
                            Message msg =  mycarhandler.obtainMessage();
                            msg.what = 2;
                            mycarhandler.sendMessage(msg);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }
    @Override
    protected void onPause(){
      super.onPause();
    }
    private class Onaddcar implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.putString("state", "addcar");
            editor.commit();
            Intent intentBrnd = new Intent(MycarActivity.this, BrandActivity.class);
            startActivity(intentBrnd);
        }
    }
    private List getData(String jsonString) {
        List<CarMessage> list = new ArrayList();
        try{
           JSONObject jsonObject = new JSONObject(jsonString);
           //返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray("cars");
            for(int i= 0;i<jsonArray.length();i++){
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                CarMessage carmessage = new CarMessage();
                carmessage.setCar_id(jsonObject2.getString("carId"));
                carmessage.setCar_brand(jsonObject2.getString("carBrand"));
                carmessage.setCar_flag(jsonObject2.getString("carFlag"));
                carmessage.setCar_bodylevel(jsonObject2.getString("carBodylevel"));
                carmessage.setCar_number(jsonObject2.getString("carNumber"));
                carmessage.setCar_motor_number(jsonObject2.getString("carMotorNumber"));
                carmessage.setCar_type(jsonObject2.getString("carType"));
                list.add(carmessage);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }


        return list;
    }
    @SuppressLint("NewApi")
    private void popWindow(){
        LayoutInflater inflater = LayoutInflater.from(this);//获取一个填充器
        View view = inflater.inflate(R.layout.pop_list_window, null);//填充我们自定义的布局
        TextView order_gasoline =(TextView)view.findViewById(R.id.order_gasoline);
        TextView query_ordergasoline = (TextView)view.findViewById(R.id.query_ordergasoline);
        query_ordergasoline.setOnClickListener(new query_gasoline());
        order_gasoline.setOnClickListener(new order_gasoline());
        Display display = getWindowManager().getDefaultDisplay();//得到当前屏幕的显示器对象
        Point size = new Point();//创建一个Point点对象用来接收屏幕尺寸信息
        display.getSize(size);//Point点对象接收当前设备屏幕尺寸信息
        int width = size.x;//从Point点对象中获取屏幕的宽度(单位像素)
        int height = size.y;//从Point点对象中获取屏幕的高度(单位像素)
      //  Log.v("zxy", "width="+width+",height="+height);//width=480,height=854可知手机的像素是480x854的
        //创建一个PopupWindow对象，第二个参数是设置宽度的，用刚刚获取到的屏幕宽度乘以2/3，取该屏幕的2/3宽度，从而在任何设备中都可以适配，高度则包裹内容即可，最后一个参数是设置得到焦点
        popWindow = new PopupWindow(view, 2*width/3, RelativeLayout.LayoutParams.WRAP_CONTENT, true);
        popWindow.setBackgroundDrawable(new BitmapDrawable());//设置PopupWindow的背景为一个空的Drawable对象，如果不设置这个，那么PopupWindow弹出后就无法退出了
        popWindow.setOutsideTouchable(true);//设置是否点击PopupWindow外退出PopupWindow
        WindowManager.LayoutParams params = getWindow().getAttributes();//创建当前界面的一个参数对象
        params.alpha = 0.8f;//设置参数的透明度为0.8，透明度取值为0~1，1为完全不透明，0为完全透明，因为android中默认的屏幕颜色都是纯黑色的，所以如果设置为1，那么背景将都是黑色，设置为0，背景显示我们的当前界面
        if(y>0&&y<300){
            params.y=-320;
        }
        if(y>300&&y<580){
            params.y=-30;
        }
        if(y>580&&y<860){
            params.y=290;
        }
        params.x=80;
        params.gravity=Gravity.LEFT;
        params.gravity=Gravity.BOTTOM;
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
        popWindow.showAtLocation(inflater.inflate(R.layout.activity_mycar, null), Gravity.CENTER,  params.x,  params.y);

    }
   private class order_gasoline implements View.OnClickListener{
       @Override
       public void onClick(View view) {
           Intent intentgasoline = new Intent();
           intentgasoline.setClass(MycarActivity.this,AddgasolineActivity.class);
           startActivity(intentgasoline);
           popWindow.dismiss();
       }
   }
   private class query_gasoline implements  View.OnClickListener{

       @Override
       public void onClick(View view) {
           Intent intentmyorder = new Intent();
           intentmyorder.setClass(MycarActivity.this,MyordergasolineActivity.class);
           startActivity(intentmyorder);
           popWindow.dismiss();
       }
   }
    private class TouchListenerImp implements View.OnTouchListener {

        public boolean onTouch(View v, MotionEvent event) {
            x=(int)event.getX();
            y=(int)event.getY();
            return false;
        }
    }
   private class finishcarback implements View.OnClickListener{

       @Override
       public void onClick(View view) {
           finish();
       }
   }


    // 此方法在主线程中调用，可以更新UI
    Handler mycarhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 处理消息时需要知道是成功的消息还是失败的消息
            switch (msg.what) {
                case 0:
                   mycarDialog.dismiss();
                   Toast.makeText(MycarActivity.this, "无法连接服务器，请连接网络", Toast.LENGTH_LONG).show();
                   loading_rl.setVisibility(View.VISIBLE);
                    break;
                case 1:
                    mycarDialog.dismiss();
                    result_mycar.setVisibility(View.GONE);
                    mycar.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    mycarDialog.dismiss();
                    mAdapter = new  CarMessageAdapter(MycarActivity.this, getData(result));
                    result_mycar.setAdapter(mAdapter);
                    addcar_rl.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
            }

        }
    };

    @SuppressLint("NewApi")
    public static void closeStrictMode() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectAll().penaltyLog().build());
    }
}
