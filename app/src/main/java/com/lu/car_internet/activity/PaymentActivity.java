package com.lu.car_internet.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.utils.HttpUtils;
import com.lu.car_internet.widget.BottomPopupOption;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import c.b.BP;
import c.b.PListener;

/**
 * Created by lu on 2017/4/26.
 */

public class PaymentActivity extends Activity {
    private TextView pay_carbrand;
    private TextView pay_station;
    private TextView pay_gasolinetype;
    private TextView pay_gasolinenumber;
    private TextView pay_pricevalue;
    private Button commit_pay;
    private ImageView confimorder_back;
    private ImageView pay_modeimage;
    private RelativeLayout payment_mode;
    // 此为测试Appid,请将Appid改成你自己的Bmob AppId
    String APPID = "5740e0464100d0a4d326dc955dee55f2";
    // 此为微信支付插件的官方最新版本号,请在更新时留意更新说明
    int PLUGINVERSION = 7;
    private String pay="Wechat";
    ProgressDialog dialog;
    private String gasoline_money;
    private String Carid;
    private String Uid;
    private String gasoline_number;
    private String result="";
    private String addgasolineorderid;
    private String paymode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confimorder);
        init();
    }
    private void init(){
        // 初始化BmobPay对象,可以在支付时再初始化
        BP.init(APPID);
        pay_carbrand = (TextView)findViewById(R.id.pay_carbrand);
        pay_station = (TextView)findViewById(R.id.pay_station);
        pay_gasolinetype = (TextView)findViewById(R.id.pay_gasolinetype);
        pay_gasolinenumber = (TextView)findViewById(R.id.pay_gasolinenumber);
        pay_pricevalue = (TextView)findViewById(R.id.pay_pricevalue);
        commit_pay = (Button)findViewById(R.id.commit_pay);
        confimorder_back=(ImageView)findViewById(R.id.confimorder_back);
        payment_mode = (RelativeLayout)findViewById(R.id.payment_mode);
        pay_modeimage = (ImageView)findViewById(R.id.pay_modeimage);
        payment_mode.setOnClickListener(new pay_mode());
        confimorder_back.setOnClickListener(new back());
        commit_pay.setOnClickListener(new pay());
          /*获取Intent中的Bundle对象*/
        Bundle bundle = this.getIntent().getExtras();
        /*获取Bundle中的数据，注意类型和key*/
        Carid = bundle.getString("Carid");
        Uid = bundle.getString("Uid");
        String gasoline_station = bundle.getString("gasoline_station");
        gasoline_number = bundle.getString("gasoline_number");
        gasoline_money = bundle.getString("gasoline_money");
        String gasoline_type = bundle.getString("gasoline_type");
        SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
        String carbrand = sPreferences.getString("carbrand", "");
        pay_carbrand.setText(carbrand);
        pay_station.setText(gasoline_station);
        pay_gasolinetype.setText(gasoline_type);
        pay_gasolinenumber.setText(gasoline_number+"升");
        pay_pricevalue.setText("￥"+gasoline_money);

    }
    private class back implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private class pay_mode implements View.OnClickListener{


        @Override
        public void onClick(View view) {
          final  BottomPopupOption bottomPopupOption = new BottomPopupOption(PaymentActivity.this);
            bottomPopupOption.setItemText("微信", "支付宝","油到付款");
// bottomPopupOption.setColors();//设置颜色
            bottomPopupOption.showPopupWindow();

            bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                @Override
                public void onItemClick(int position) {
                     if(position == 0){
                         pay_modeimage.setImageResource(R.drawable.wechat_pay);
                         pay="Wechat";
                         bottomPopupOption.dismiss();
                     }
                     if(position == 1){
                         pay_modeimage.setImageResource(R.drawable.alipay_pay);
                         pay="Alipay";
                         bottomPopupOption.dismiss();
                     }
                     if(position == 2){
                         pay_modeimage.setImageResource(R.drawable.money_pay);
                         pay="Otherpay";
                         bottomPopupOption.dismiss();
                     }
                }
            });
        }
    }

    private class pay implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(pay.equals("Wechat")){
                paymode="微信支付";
                pay(false);
            }else{
                if(pay.equals("Alipay")){
                paymode="支付宝支付";
                pay(true);
                }
                if(pay.equals("Otherpay")){
                    savesql(createRandomCharData(32),"油到付款");
                    Intent intent = new Intent();
                    intent.setClass(PaymentActivity .this,FinishorderActivity.class);
                    startActivity(intent);
               }
            }
        }
    }

    /**
     * 调用支付
     *
     * @param alipayOrWechatPay 支付类型，true为支付宝支付,false为微信支付
     */
    void  pay(final boolean alipayOrWechatPay) {
        if (alipayOrWechatPay) {
            if (!checkPackageInstalled("com.eg.android.AlipayGphone",
                    "https://www.alipay.com")) { // 支付宝支付要求用户已经安装支付宝客户端
                Toast.makeText(PaymentActivity.this, "请安装支付宝客户端", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
        } else {
            if (checkPackageInstalled("com.tencent.mm", "http://weixin.qq.com")) {// 需要用微信支付时，要安装微信客户端，然后需要插件
                // 有微信客户端，看看有无微信支付插件
                int pluginVersion = BP.getPluginVersion(this);
                if (pluginVersion < PLUGINVERSION) {// 为0说明未安装支付插件,
                    // 否则就是支付插件的版本低于官方最新版
                    Toast.makeText(
                            PaymentActivity.this,
                            pluginVersion == 0 ? "监测到本机尚未安装支付插件,无法进行支付,请先安装插件(无流量消耗)"
                                    : "监测到本机的支付插件不是最新版,最好进行更新,请先更新插件(无流量消耗)",
                            Toast.LENGTH_SHORT).show();
//                    installBmobPayPlugin("bp.db");

                    installApk("bp.db");
                    return;
                }
            } else {// 没有安装微信
                Toast.makeText(PaymentActivity.this, "请安装微信客户端", Toast.LENGTH_SHORT).show();
                return;
            }
        }

       // showDialog("正在获取订单...\nSDK版本号:" + BP.getPaySdkVersion());
        final String name =pay_gasolinetype.getText().toString();

        try {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            ComponentName cn = new ComponentName("com.bmob.app.sport",
                    "com.bmob.app.sport.wxapi.BmobActivity");
            intent.setComponent(cn);
            this.startActivity(intent);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        BP.pay(name, pay_gasolinenumber.getText().toString(), getPrice(), alipayOrWechatPay, new PListener() {

            // 因为网络等原因,支付结果未知(小概率事件),出于保险起见稍后手动查询
            @Override
            public void unknow() {
                Toast.makeText(PaymentActivity.this, "支付结果未知,请稍后手动查询", Toast.LENGTH_SHORT)
                        .show();
              //  tv.append(name + "'s pay status is unknow\n\n");
                hideDialog();
            }

            // 支付成功,如果金额较大请手动查询确认
            @Override
            public void succeed() {
                Toast.makeText(PaymentActivity.this, "支付成功!", Toast.LENGTH_SHORT).show();
             //   tv.append(name + "'s pay status is success\n\n");
                if(addgasolineorderid!=null){
                    savesql(addgasolineorderid,paymode);
                    Intent intent = new Intent();
                    intent.setClass(PaymentActivity .this,FinishorderActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(PaymentActivity.this, "获取不了订单号,请重新尝试!", Toast.LENGTH_SHORT).show();
                }
               // hideDialog();
            }

            // 无论成功与否,返回订单号
            @Override
            public void orderId(String orderId) {
                // 此处应该保存订单号,比如保存进数据库等,以便以后查询
             //   order.setText(orderId);
              //  tv.append(name + "'s orderid is " + orderId + "\n\n");
                addgasolineorderid = orderId;
              //  showDialog("获取订单成功!请等待跳转到支付页面~");
            }

            // 支付失败,原因可能是用户中断支付操作,也可能是网络原因
            @Override
            public void fail(int code, String reason) {
                Toast.makeText(PaymentActivity.this,  "微信支付暂时停用,正在修复中", Toast.LENGTH_SHORT)
                        .show();

                // 当code为-2,意味着用户中断了操作
                // code为-3意味着没有安装BmobPlugin插件
                if (code == -3) {
                    Toast.makeText(
                            PaymentActivity.this,
                            "监测到你尚未安装支付插件,无法进行支付,请先安装插件(已打包在本地,无流量消耗),安装结束后重新支付",
                            Toast.LENGTH_SHORT).show();
//                    installBmobPayPlugin("bp.db");
                    installApk("bp.db");
                } else {
                    Toast.makeText(PaymentActivity.this, "支付中断!", Toast.LENGTH_SHORT)
                            .show();
                }
             //   tv.append(name + "'s pay status is fail, error code is \n"
             //           + code + " ,reason is " + reason + "\n\n");
                hideDialog();
            }
        });
    }
    /**
     * 安装assets里的apk文件
     *
     * @param fileName
     */
    void installBmobPayPlugin(String fileName) {
        try {
            InputStream is = getAssets().open(fileName);
            File file = new File(Environment.getExternalStorageDirectory()
                    + File.separator + fileName + ".apk");
            if (file.exists())
                file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.parse("file://" + file),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查某包名应用是否已经安装
     *
     * @param packageName 包名
     * @param browserUrl  如果没有应用市场，去官网下载
     * @return
     */
    private boolean checkPackageInstalled(String packageName, String browserUrl) {
        try {
            // 检查是否有支付宝客户端
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            // 没有安装支付宝，跳转到应用市场
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=" + packageName));
                startActivity(intent);
            } catch (Exception ee) {// 连应用市场都没有，用浏览器去支付宝官网下载
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(browserUrl));
                    startActivity(intent);
                } catch (Exception eee) {
                    Toast.makeText(PaymentActivity.this,
                            "您的手机上没有没有应用市场也没有浏览器，我也是醉了，你去想办法安装支付宝/微信吧",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
        return false;
    }
    private static final int REQUESTPERMISSION = 101;

    private void installApk(String s) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUESTPERMISSION);
        } else {
            installBmobPayPlugin(s);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUESTPERMISSION) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    installBmobPayPlugin("bp.db");
                } else {
                    //提示没有权限，安装不了
                    Toast.makeText(PaymentActivity.this,"您拒绝了权限，这样无法安装支付插件",Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    void showDialog(String message) {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
                dialog.setCancelable(true);
            }
            dialog.setMessage(message);
            dialog.show();
        } catch (Exception e) {
            // 在其他线程调用dialog会报错
        }
    }

    void hideDialog() {
        if (dialog != null && dialog.isShowing())
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
    }

    // 默认为0.02
    double getPrice() {
        double price = 0.00;
        try {
            price = Double.parseDouble(this.gasoline_money);
        } catch (NumberFormatException e) {
        }
        return price;
    }

    private void savesql(String orderid,String paymode){
        Map<String,String> params=new HashMap<String,String>();
        params.put("Uid", Uid);
        params.put("Carid", Carid);
        params.put("gasoline_station",pay_station.getText().toString());
        params.put("gasoline_type",pay_gasolinetype.getText().toString());
        params.put("gasoline_number",gasoline_number);
        params.put("gasoline_money",gasoline_money);
        params.put("gasoline_orderid",orderid);
        if(pay.equals("Otherpay")){
            params.put("gasoline_state",paymode);
        }else{
            params.put("gasoline_state",paymode);
        }
        result= HttpUtils.submitPostData(params,"utf-8","addgasoline");
        if(result.equals("预约成功")){
            Toast.makeText(PaymentActivity.this, "预约成功", Toast.LENGTH_SHORT)
                    .show();
        }else{
            if(result.equals("-1")){
                Toast.makeText(PaymentActivity.this, "网络连接失败", Toast.LENGTH_SHORT)
                        .show();
            }else{
                Toast.makeText(PaymentActivity.this, "下单失败,请重试", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }


    public String createRandomCharData(int length)
    {
        StringBuilder sb=new StringBuilder();
        Random rand=new Random();//随机用以下三个随机生成器
        Random randdata=new Random();
        int data=0;
        for(int i=0;i<length;i++)
        {
            int index=rand.nextInt(3);
            //目的是随机选择生成数字，大小写字母
            switch(index)
            {
                case 0:
                    data=randdata.nextInt(10);//仅仅会生成0~9
                    sb.append(data);
                    break;
                case 1:
                    data=randdata.nextInt(26)+65;//保证只会产生65~90之间的整数
                    sb.append((char)data);
                    break;
                case 2:
                    data=randdata.nextInt(26)+97;//保证只会产生97~122之间的整数
                    sb.append((char)data);
                    break;
            }
        }
        String result=sb.toString();
        return result;
    }

}
