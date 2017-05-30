package com.lu.car_internet.activity;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.lu.car_internet.R;
import com.lu.car_internet.utils.HttpUtils;
import com.netease.scan.IScanModuleCallBack;
import com.netease.scan.QrScan;
import com.netease.scan.ui.CaptureActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;



/**
 * Created by lu on 2017/5/10.
 */

public class QuerycarmessageActivity extends Activity {

    private ImageButton carmessagdetailsheadback;
    private TextView message_mileage;
    private TextView message_gasoline;
    private TextView message_motor_performance;
    private TextView message_motor_transmission;
    private TextView message_lamp;
    private RelativeLayout car_updatedetailmessagerl;
    private RelativeLayout save_detailmessagerl;
    String carid;
    /**扫描二维码*/
    private CaptureActivity mCaptureContext;
    //里程数
    private ImageView message_mileagestate;
    //剩余油量
    private ImageView message_gasolinestate;
    //变速器
    private ImageView message_motor_performancestate;
    //发动机
    private ImageView message_motor_transmissionstate;
    //车灯
    private ImageView message_lampstate;
    //主键
    private String tmessageId;
    //线程停止

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_querycarmessage);
        super.onCreate(savedInstanceState);
//        SharedPreferences sPreferences=this.getSharedPreferences("config", MODE_PRIVATE);
//        carid=sPreferences.getString("carid", "");
        Intent intent =getIntent();
        carid =intent.getStringExtra("carid");
        init();
        getdate();
    }


    private void init(){
        carmessagdetailsheadback =(ImageButton)findViewById(R.id.carmessagdetailsheadback);
        message_mileage = (TextView)findViewById(R.id.message_mileage);
        message_gasoline = (TextView)findViewById(R.id.message_gasoline);
        message_motor_performance = (TextView)findViewById(R.id.message_motor_performance);
        message_motor_transmission = (TextView)findViewById(R.id.message_motor_transmission);
        message_lamp = (TextView)findViewById(R.id.message_lamp);
        car_updatedetailmessagerl = (RelativeLayout)findViewById(R.id.car_updatedetailmessagerl);
        save_detailmessagerl = (RelativeLayout)findViewById(R.id.save_detailmessagerl);
        save_detailmessagerl.setOnClickListener(new savedetailsmessage());
        car_updatedetailmessagerl.setOnClickListener(new car_update());
        carmessagdetailsheadback.setOnClickListener(new carmessageback());
        message_mileagestate = (ImageView)findViewById(R.id.message_mileagestate);
        message_gasolinestate = (ImageView)findViewById(R.id.message_gasolinestate);
        message_motor_performancestate = (ImageView)findViewById(R.id.message_motor_performancestate);
        message_motor_transmissionstate = (ImageView)findViewById(R.id.message_motor_transmissionstate);
        message_lampstate = (ImageView)findViewById(R.id.message_lampstate);
    }
    private void getdate(){
        Map<String,String> params=new HashMap<String,String>();
        params.put("carid", carid);
        String result= HttpUtils.submitPostData(params,"utf-8","querycarmessage");
        if(result.equals("-1")){
            Toast.makeText(QuerycarmessageActivity.this, "用户名或密码错误", Toast.LENGTH_LONG).show();
        }else{
            if(result.equals("没有车辆详细信息")){
                Toast.makeText(QuerycarmessageActivity.this, "没有车辆信息，请录入", Toast.LENGTH_LONG).show();
                message_mileagestate.setVisibility(View.GONE);
                message_gasolinestate.setVisibility(View.GONE);
                message_motor_performancestate.setVisibility(View.GONE);
                message_motor_transmissionstate.setVisibility(View.GONE);
                message_lampstate.setVisibility(View.GONE);

            }else{
                if(result.equals("查询失败,请重新查询")){
                    message_mileagestate.setVisibility(View.INVISIBLE);
                    message_gasolinestate.setVisibility(View.INVISIBLE);
                    message_motor_performancestate.setVisibility(View.INVISIBLE);
                    message_motor_transmissionstate.setVisibility(View.INVISIBLE);
                    message_lampstate.setVisibility(View.INVISIBLE);
                    Toast.makeText(QuerycarmessageActivity.this, "系统正在维护,请稍后查询", Toast.LENGTH_LONG).show();
                }else{
                    message_mileagestate.setVisibility(View.VISIBLE);
                    message_gasolinestate.setVisibility(View.VISIBLE);
                    message_motor_performancestate.setVisibility(View.VISIBLE);
                    message_motor_transmissionstate.setVisibility(View.VISIBLE);
                    message_lampstate.setVisibility(View.VISIBLE);
                    querymymessage(result);
                }
            }
        }
    }

    private void querymymessage(String jsonString){
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            //返回json的数组
            JSONArray jsonArray = jsonObject.getJSONArray("carmessage");
            for(int i= 0;i<jsonArray.length();i++){
                JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                //  JSONObject jsonobjecttime=jsonObject2.getJSONObject("id");
                String messageGasoline = jsonObject2.getString("messageGasoline");
                String messageLamp = jsonObject2.getString("messageLamp");
                String messageMileage = jsonObject2.getString("messageMileage");
                String messageMotorPerformance = jsonObject2.getString("messageMotorPerformance");
                String messageMotorTransmission = jsonObject2.getString("messageMotorTransmission");
                tmessageId = jsonObject2.getString("tmessageId");
                message_mileage.setText(messageMileage);
                message_gasoline.setText(messageGasoline);
                message_motor_performance.setText(messageMotorPerformance);
                message_motor_transmission.setText(messageMotorTransmission);
                message_lamp .setText(messageLamp);
                int milage = Integer.parseInt(messageMileage);
                int gasoline = Integer.parseInt(messageGasoline);
                message_mileagestate.setVisibility(View.VISIBLE);
                message_gasolinestate.setVisibility(View.VISIBLE);
                message_motor_performancestate.setVisibility(View.VISIBLE);
                message_motor_transmissionstate.setVisibility(View.VISIBLE);
                message_lampstate.setVisibility(View.VISIBLE);
                if(milage>123456){
                    message_mileagestate.setImageResource(R.drawable.unusual);
                }else{
                    message_mileagestate.setImageResource(R.drawable.normal);
                }
                if(gasoline<40){
                    message_gasolinestate.setImageResource(R.drawable.unusual);
                }else{
                    message_gasolinestate.setImageResource(R.drawable.normal);
                }
                if(messageMotorPerformance.equals("坏")){
                    message_motor_performancestate.setImageResource(R.drawable.unusual);
                }else{
                    message_motor_performancestate.setImageResource(R.drawable.normal);
                }
                if(messageMotorTransmission.equals("坏")){
                    message_motor_transmissionstate.setImageResource(R.drawable.unusual);
                }else{
                    message_motor_transmissionstate.setImageResource(R.drawable.normal);
                }
                if(messageLamp.equals("坏")){
                    message_lampstate.setImageResource(R.drawable.unusual);
                }else{
                    message_lampstate.setImageResource(R.drawable.normal);
                }
            }
        }catch (JSONException e){
            Toast.makeText(this,"解析异常", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private class  car_update implements View.OnClickListener{


        @Override
        public void onClick(View view) {
            QrScan.getInstance().launchScan(QuerycarmessageActivity.this, new IScanModuleCallBack() {
                @Override
                public void OnReceiveDecodeResult(final Context context, String result) {
                    mCaptureContext = (CaptureActivity)context;

                    AlertDialog dialog = new AlertDialog.Builder(mCaptureContext)
                            .setMessage(result)
                            .setCancelable(false)
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    QrScan.getInstance().restartScan(mCaptureContext);
                                }
                            })
                            .setPositiveButton("关闭", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    QrScan.getInstance().finishScan(mCaptureContext);
                                }
                            })
                            .create();
                    dialog.show();
                }
            });
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        SharedPreferences sPreferences = this.getSharedPreferences("config", MODE_PRIVATE);
        String coderesult=sPreferences.getString("coderesult", "");
        if(!coderesult.equals("")){
            SharedPreferences.Editor editor=sPreferences.edit();
            editor.putString("coderesult", "");
            editor.commit();
            car_updatedetailmessagerl.setVisibility(View.GONE);
            save_detailmessagerl.setVisibility(View.VISIBLE);
            querymymessage(coderesult);
//            Intent intent = new Intent();
//            intent.setClass(this,LicenseActivity.class);
//            startActivity(intent);

        }else{
            car_updatedetailmessagerl.setVisibility(View.VISIBLE);
            save_detailmessagerl.setVisibility(View.GONE);
        }

    }

    private class carmessageback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class savedetailsmessage implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            //Toast.makeText(QuerycarmessageActivity.this, "更新成功", Toast.LENGTH_LONG).show();
            final Map<String,String> params=new HashMap<String,String>();
            params.put("carid", carid);
            params.put("tmessageId", tmessageId);
            params.put("messageGasoline", message_gasoline.getText().toString());
            params.put("messageLamp", message_lamp .getText().toString());
            params.put("messageMileage", message_mileage.getText().toString());
            params.put("messageMotorPerformance", message_motor_performance.getText().toString());
            params.put("messageMotorTransmission", message_motor_transmission.getText().toString());

          //  Toast.makeText(QuerycarmessageActivity.this, result, Toast.LENGTH_LONG).show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = HttpUtils.submitPostData(params,"utf-8","insertorupdatecarmessage");
 //                       Toast.makeText(QuerycarmessageActivity.this, "sdfsf", Toast.LENGTH_LONG).show();
//                        String result = HttpUtils.submitPostData(params,"utf-8","insertorupdatecarmessage");
                        if(result.equals("更新成功")||result.equals("插入成功")){
                            Message msg = handler.obtainMessage();
                            msg.what = 0;
                            handler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        }
    }



    // 此方法在主线程中调用，可以更新UI
    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 处理消息时需要知道是成功的消息还是失败的消息
            switch (msg.what) {
                case 0:
                    Toast.makeText(QuerycarmessageActivity.this, "更新成功",Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }

        }
    };

}
