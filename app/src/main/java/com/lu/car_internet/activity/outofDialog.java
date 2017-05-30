package com.lu.car_internet.activity;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lu.car_internet.R;

import static com.tencent.open.utils.Global.getPackageName;

/**
 * Created by lu on 2017/4/8.
 */

public class outofDialog extends Dialog {
    private RelativeLayout out_currentaccount;
    private RelativeLayout finish_carinternet;
    Context context;
    private Dialog outDialog;

    public outofDialog(Context context) {
        super(context);
        this.context = context;
    }
    public outofDialog(Context context, int theme)
    {
        super(context, theme);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {         // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_outdialog);
        initview();
        initListener();
    }

    //初始化组件
    private void initview(){
        out_currentaccount = (RelativeLayout) findViewById(R.id.out_currentaccount);
        finish_carinternet = (RelativeLayout)findViewById(R.id.finish_carinternet);
    }
    //设置监听事件
    private void initListener(){
        {
            out_currentaccount.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                    outDialog = new LoadingDialog(context,"正在退出....");
                    outDialog.setCanceledOnTouchOutside(false);
                    outDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(1000);
                                Message msg =  outhandler.obtainMessage();
                                msg.what = 0;
                                outhandler.sendMessage(msg);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                }

            });
            finish_carinternet.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
        }
    }

    // 此方法在主线程中调用，可以更新UI
    Handler outhandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 处理消息时需要知道是成功的消息还是失败的消息
            switch (msg.what) {
                case 0:
                    //Toast.makeText(LoginActivity.this, "更新成功",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.setClass(context,LoginActivity.class);
                    context.startActivity(intent);
                    outDialog.dismiss();
                    break;
                default:
                    break;
            }

        }
    };
}
