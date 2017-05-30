package com.lu.car_internet.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.activity.FeedbackActivity;
import com.lu.car_internet.activity.ImgFileListActivity;
import com.lu.car_internet.activity.MycarActivity;
import com.lu.car_internet.activity.PersonalmessageActivity;
import com.lu.car_internet.activity.ShareActivity;
import com.lu.car_internet.activity.outofDialog;
import com.lu.car_internet.utils.Utils;
import com.lu.car_internet.widget.BottomPopupOption;


import static android.content.Context.MODE_PRIVATE;


/**
 * Created by lu on 2017/2/7.
 */

public class PersonalFragment extends Fragment {
    private RelativeLayout personal_car_rl;
    private RelativeLayout personal_out_rl;
    private RelativeLayout  persoanl_message_rl;
    private RelativeLayout persoanl_share_rl;
    private RelativeLayout persoanl_circle_friends_rl;
    private RelativeLayout persoanl_update_version_rl;
    private RelativeLayout persoanl_delete_rl;
    private PopupWindow popWindow;
    private EditText edit_textvalue;
    private ImageView personal_img_photo;
    private TextView personal_account;
    private String userid;
    private Bitmap bitmap = null;
    private String  userlogouri="http://q.qlogo.cn/qqapp/1105848992/DB831F94CA72A550DD7BA9D5307E8852/100";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ActivityCompat.requestPermissions(getActivity(), new String[]{android
                .Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_personalmessage, container, false);
        // return inflater.inflate(R.layout.fragment_personalmessage, container, false);
        //头像
        SharedPreferences sPreferences=getActivity().getSharedPreferences("config", MODE_PRIVATE);
        userid=sPreferences.getString("Uid", "");
        personal_img_photo =(ImageView)rootView.findViewById(R.id.personal_img_photo);
        personal_account = (TextView)rootView.findViewById(R.id.personal_account);
        //我的车辆
        personal_car_rl = (RelativeLayout) rootView.findViewById(R.id.persoanl_car_rl);
        //我的资料
        persoanl_message_rl =(RelativeLayout)rootView.findViewById(R.id.persoanl_message_rl);
        //分享
        persoanl_share_rl =(RelativeLayout)rootView.findViewById(R.id.persoanl_share_rl);
        //朋友圈
        persoanl_circle_friends_rl =(RelativeLayout)rootView.findViewById(R.id.persoanl_circle_friends_rl);
        //版本更新
        persoanl_update_version_rl =(RelativeLayout)rootView.findViewById(R.id.persoanl_update_version_rl);
        //删除缓存
        persoanl_delete_rl = (RelativeLayout)rootView.findViewById(R.id.persoanl_delete_rl);
        //退出
        personal_out_rl = (RelativeLayout) rootView.findViewById(R.id.persoanl_out_rl);
        personal_car_rl.setOnClickListener(new querymycar());
        personal_out_rl.setOnClickListener(new outaccount());
        persoanl_message_rl.setOnClickListener(new mypersonalmessage());
        persoanl_share_rl.setOnClickListener(new sharemessage());
        persoanl_circle_friends_rl.setOnClickListener(new feedback());
        String mode = sPreferences.getString("qqmode", "");
        if(mode.equals("qqlogin")){
          //  final String userlogouri=sPreferences.getString("imageuri", "");
            new Thread(new Runnable(){
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    bitmap = Utils.getbitmap(userlogouri);
                    Message msg = Imagehandler.obtainMessage();
                    msg.what = 0;
                    Imagehandler.sendMessage(msg);
                }
            }).start();

        }


        return rootView;
    }

    private class querymycar implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(!userid.equals("")){
                Intent intentmycar = new Intent(getActivity(),MycarActivity.class);
                intentmycar.putExtra("Uid", userid);
                startActivity(intentmycar);
            }else{
                Toast.makeText(getActivity(), "你没有注册账号,请注册",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
    private class outaccount implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Dialog dialog = new outofDialog(getActivity(), R.style.dialog);
           // dialog.setContentView(R.layout.register_moredialog);
            //WindowManager m = getactivity().getWindowManager();
           // Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            WindowManager.LayoutParams p = dialog.getWindow().getAttributes();//获取对话框当前的参数值
            p.height = (int) (dm.widthPixels * 0.2);// 高度设置为屏幕的0.6
            p.width = (int) (dm.heightPixels * 0.58);// 宽度设置为屏幕的0.65
            dialog.getWindow().setAttributes(p);
            dialog.show();
        }
    }

    private class mypersonalmessage implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            if(!userid.equals("")){
                Intent intentpersonal = new Intent();
                intentpersonal.setClass(getActivity(),PersonalmessageActivity.class);
                startActivity(intentpersonal);
            }else{
                Toast.makeText(getActivity(), "你没有注册账号,请注册",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class feedback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setClass(getActivity(), FeedbackActivity.class);
            startActivity(intent);
        }
    }

    private class sharemessage implements View.OnClickListener{

        @Override
        public void onClick(View view) {
/*            Intent intentshare = new Intent();
            intentshare.setClass(getActivity(),ShareActivity.class);
            startActivity(intentshare);*/
            final BottomPopupOption bottomPopupOption = new BottomPopupOption(getActivity());
            bottomPopupOption.setItemText("文字分享", "图片分享");
            bottomPopupOption.showPopupWindow();
            bottomPopupOption.setItemClickListener(new BottomPopupOption.onPopupWindowItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if(position == 0){
                        bottomPopupOption.dismiss();
                        PackageManager packageManager = getActivity().getPackageManager();
                        int permission = packageManager.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", "com.lu.car_internet");
                        if(PackageManager.PERMISSION_GRANTED == permission){
                            //有这个权限
                            Intent intent = new Intent();
                            intent.setClass(getActivity(),ShareActivity.class);
                            startActivity(intent);
//                            Intent shareIntent = new Intent();
//                            shareIntent.setAction(Intent.ACTION_SEND);
//                            shareIntent.putExtra(Intent.EXTRA_TEXT, "This is my Share text.");
//                            shareIntent.setType("text/plain");
//                            //设置分享列表的标题，并且每次都显示分享列表
//                            startActivity(Intent.createChooser(shareIntent, "分享到"));
                        }else {
                            //没有这个权限
                            Toast.makeText(getActivity(), "没有授予权限",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    if(position == 1){
                        bottomPopupOption.dismiss();
                        Intent intent = new Intent();
                        intent.setClass(getActivity(),ImgFileListActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }



    private class sharetext implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            String text= edit_textvalue.getText().toString();
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_TEXT, text);
            shareIntent.setType("text/plain");
            //设置分享列表的标题，并且每次都显示分享列表
            startActivity(Intent.createChooser(shareIntent, "分享到"));
        }
    }
    @Override
    public void onStart(){
        super.onStart();
        SharedPreferences sPreferences=getActivity().getSharedPreferences("config", MODE_PRIVATE);
        String uri = sPreferences.getString("uri", "");
        String phone = sPreferences.getString("userphone", "");
        if(!phone.equals("")){
            String phoneNumber = phone.substring(0,3)+"****"+phone.substring(7,phone.length());
            personal_account.setText(phoneNumber);
        }
        if(!uri.equals("")){
            Uri url = Uri.parse((String)uri);
            personal_img_photo.setImageURI(url);
        }
    }

    // 此方法在主线程中调用，可以更新UI
    Handler Imagehandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            // 处理消息时需要知道是成功的消息还是失败的消息
            switch (msg.what) {
                case 0:
                    personal_img_photo.setImageBitmap(bitmap);
                    break;
                default:
                    break;
            }

        }
    };
}
