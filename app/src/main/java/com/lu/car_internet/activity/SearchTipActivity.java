package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.lu.car_internet.R;
import com.lu.car_internet.adapters.SearchAdapter;
import com.netease.scan.util.ToastUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * Created by lu on 2017/5/13.
 */

public class SearchTipActivity extends Activity implements TextWatcher,Inputtips.InputtipsListener {
   private AutoCompleteTextView search_edit;
   private ListView search_list;
   private String city = "";
  // private TextView search_for_place;
   private SearchAdapter searchadapter;
   private Button search_for_place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchtip);
        search_edit = (AutoCompleteTextView)findViewById(R.id.search_edit);
        search_list = (ListView)findViewById(R.id.search_list);
        search_for_place = (Button)findViewById(R.id.search_for_place);
        search_for_place.setOnClickListener(new commitaddress());
        search_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                HashMap<String,String> map=(HashMap<String,String>) search_list.getItemAtPosition(position);
                String title=map.get("name");
                String content=map.get("address");
                //search_edit.setText(title);
                Intent intent = getIntent();
                String point = intent.getStringExtra("pointordestion");
                if(point.equals("point")){
                    SharedPreferences sPreferences=getSharedPreferences("config",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sPreferences.edit();
                    editor.putString("point",content+title);
                    editor.commit();
                }else{
                    SharedPreferences sPreferences=getSharedPreferences("config",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sPreferences.edit();
                    editor.putString("destion",content+title);
                    editor.commit();
                }

                finish();
               // startActivity(intent);

            }
        });
        search_edit.addTextChangedListener(this);

    }


    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String content=charSequence.toString().trim();//获取自动提示输入框的内容
        InputtipsQuery inputtipsQuery=new InputtipsQuery(content,city);//初始化一个输入提示搜索对象，并传入参数
        inputtipsQuery.setCityLimit(true);//将获取到的结果进行城市限制筛选
        Inputtips inputtips=new Inputtips(this,inputtipsQuery);//定义一个输入提示对象，传入当前上下文和搜索对象
        inputtips.setInputtipsListener(this);//设置输入提示查询的监听，实现输入提示的监听方法onGetInputtips()
        inputtips.requestInputtipsAsyn();//输入查询提示的异步接口实现

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onGetInputtips(List<Tip> list, int returnCode) {
        if(returnCode== AMapException.CODE_AMAP_SUCCESS){//如果输入提示搜索成功
            List<HashMap<String,String>> searchList=new ArrayList<HashMap<String, String>>() ;
            for (int i=0;i<list.size();i++){
                HashMap<String,String> hashMap=new HashMap<String, String>();
                hashMap.put("name",list.get(i).getName());
                hashMap.put("address",list.get(i).getDistrict());//将地址信息取出放入HashMap中
                searchList.add(hashMap);//将HashMap放入表中

            }
            searchadapter=new SearchAdapter(this);//新建一个适配器
            search_list.setAdapter(searchadapter);//为listview适配
            SimpleAdapter aAdapter = new SimpleAdapter(getApplicationContext(), searchList, R.layout.item_layout,
                    new String[] {"name","address"}, new int[] {R.id.poi_field_id, R.id.poi_value_id});

            search_list.setAdapter(aAdapter);
            aAdapter.notifyDataSetChanged();//动态更新listview


        }else{
            //ToastUtil.show(this,returnCode);


        }
    }
    private class commitaddress implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            String point = intent.getStringExtra("pointordestion");
            if(!search_edit.getText().toString().equals("")){
                if(point.equals("point")){
                    SharedPreferences sPreferences=getSharedPreferences("config",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sPreferences.edit();
                    editor.putString("point",search_edit.getText().toString());
                    editor.commit();
                }else{
                    SharedPreferences sPreferences=getSharedPreferences("config",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sPreferences.edit();
                    editor.putString("destion",search_edit.getText().toString());
                    editor.commit();
                }
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "地址为空,请输入",
                        Toast.LENGTH_SHORT).show();
            }

        }
    }
}
