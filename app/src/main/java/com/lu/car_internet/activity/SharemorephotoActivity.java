package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.lu.car_internet.R;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by lu on 2017/5/9.
 */

public class SharemorephotoActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> listfile= new ArrayList<String>();
        Bundle bundle= getIntent().getExtras();
        if (bundle!=null) {
            if (bundle.getStringArrayList("files")!=null) {
                listfile= bundle.getStringArrayList("files");
                ArrayList<Uri> uriList = new ArrayList<>();

                String path = Environment.getExternalStorageDirectory() + File.separator;
                for(int i = 0;i < listfile.size(); i ++){
                    uriList.add(Uri.fromFile(new File(listfile.get(i))));
                }

              //  uriList.add(Uri.fromFile(new File(path+"tencent/MicroMsg/WeiXin/wx_camera_1491918026521.jpg")));
                //  uriList.add(Uri.fromFile(new File(path+"australia_3.jpg")));
//                Toast.makeText(getApplicationContext(), path,
//                        Toast.LENGTH_SHORT).show();
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
                shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
                shareIntent.setType("image/*");
                startActivity(Intent.createChooser(shareIntent, "分享到"));
                finish();
//                Intent intent2 = new Intent();
//                intent2.setClass(this,GradientTabStripActivity.class);
//                // intent.setClass(getActivity(),LoginActivity.class);
//                startActivity(intent2);
//                startActivityForResult(shareIntent, 0);

            }
        }
    }



}
