package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.lu.car_internet.R;


/**
 * Created by lu on 2017/5/8.
 */

public class ShareActivity extends Activity {
    private EditText edit_share;
    private Button save_sharetextbtn;
    private TextView surplusnumber;
    private ImageButton share_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
//        ActivityCompat.requestPermissions(ShareActivity.this, new String[]{android
//                .Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
//        ActivityCompat.requestPermissions(ShareActivity.this, new String[]{android
//                .Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 2);
        edit_share = (EditText)findViewById(R.id.edit_share);
        surplusnumber = (TextView)findViewById(R.id.surplusnumber);
        share_back = (ImageButton)findViewById(R.id.share_back);
        edit_share.setSingleLine(false);
        save_sharetextbtn = (Button)findViewById(R.id.save_sharetextbtn);
        edit_share.setFilters(new InputFilter[]{new InputFilter.LengthFilter(80)});
        save_sharetextbtn.setOnClickListener(new saveshare());
        edit_share.addTextChangedListener(new change());
        share_back.setOnClickListener(new shareback());

    }
   private class change implements TextWatcher{

       @Override
       public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

       }

       @Override
       public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
         surplusnumber.setText("还能输入:"+(charSequence.length())+"/80");
         if(charSequence.length()>0){
             save_sharetextbtn.setVisibility(View.VISIBLE);
         }else{
             save_sharetextbtn.setVisibility(View.GONE);
         }
       }

       @Override
       public void afterTextChanged(Editable editable) {

       }
   }

    private class saveshare implements View.OnClickListener{
    @Override
    public void onClick(View view) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, edit_share.getText().toString());
        shareIntent.setType("text/plain");
        //设置分享列表的标题，并且每次都显示分享列表
        startActivity(Intent.createChooser(shareIntent, "分享到"));
        SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPreferences.edit();
        editor.putString("share", "useshare");
        editor.commit();
       // startActivityForResult(shareIntent, 2);
    }
}
private class shareback implements View.OnClickListener{

    @Override
    public void onClick(View view) {
        finish();
    }
}

//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            System.out.println("requestCode" + requestCode);
//            if (requestCode == 2) {
//                finish();
//            }
//        }
//    }
    @Override
    protected void onStart(){
        super.onStart();
        SharedPreferences sPreferences=this.getSharedPreferences("config", MODE_PRIVATE);
        String share=sPreferences.getString("share", "");
        if(share.equals("useshare")){
            SharedPreferences.Editor editor = sPreferences.edit();
            editor.putString("share", "");
            editor.commit();
            finish();
        }
    }

}
