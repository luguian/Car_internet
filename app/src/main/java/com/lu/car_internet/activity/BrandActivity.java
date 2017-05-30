package com.lu.car_internet.activity;

/**
 * Created by lu on 2017/3/23.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import com.lu.car_internet.R;
import com.lu.car_internet.adapters.SortAdapter;
import com.lu.car_internet.config.CharacterParser;
import com.lu.car_internet.config.PinyinComparator;
import com.lu.car_internet.model.SortModel;
import com.lu.car_internet.view.ClearEditText;
import com.lu.car_internet.view.SideBar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class BrandActivity extends Activity {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;
    private RelativeLayout germany;
    private RelativeLayout france;
    private RelativeLayout america;
    private RelativeLayout japan;
    private RelativeLayout italy;
    private RelativeLayout britain;
    private RelativeLayout chinese;
    private RelativeLayout other;
    private ImageButton imcar;



    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<SortModel> SourceDateList;
    private List<SortModel> SourcegermanyList;
    private List<SortModel> SourceamericaList;
    private List<SortModel> SourcefranceList;
    private List<SortModel> SourceitalyList;
    private List<SortModel> SourcebritainList;
    private List<SortModel> SourcejapanList;
    private List<SortModel> SourcechineseList;
    private List<SortModel> SourceotherList;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_brand);
        initViews();
    }

    private void initViews() {

        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();

        pinyinComparator = new PinyinComparator();

        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        germany = (RelativeLayout) findViewById(R.id.germany);
        france = (RelativeLayout) findViewById(R.id.france);
        america = (RelativeLayout) findViewById(R.id.america);
        japan = (RelativeLayout) findViewById(R.id.japan);
        italy = (RelativeLayout) findViewById(R.id.italy);
        britain = (RelativeLayout) findViewById(R.id. britain);
        chinese = (RelativeLayout) findViewById(R.id.chinese);
        other = (RelativeLayout) findViewById(R.id.other);
        imcar = (ImageButton)findViewById(R.id.imback);
        imcar.setOnClickListener(new backbrand());
        germany.setOnClickListener(new choosegermany());
        france.setOnClickListener(new choosefrance());
        america.setOnClickListener(new chooseamerica());
        japan.setOnClickListener(new choosejapan());
        italy.setOnClickListener(new chooseitaly());
        britain.setOnClickListener(new choosebritain());
        chinese.setOnClickListener(new choosechinese());
        other.setOnClickListener(new chooseother());

        sideBar.setTextView(dialog);

        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    sortListView.setSelection(position);
                }

            }
        });

        sortListView = (ListView) findViewById(R.id.country_lvcountry);
        sortListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
               // characterParser.getSelling(((SortModel)adapter.getItem(position)).getName()).startsWith(filterStr.toString())
              //  Toast.makeText(getApplication(),  characterParser.getSelling(((SortModel)adapter.getItem(position)).getName()), Toast.LENGTH_SHORT).show();
               // Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
                Intent intenttype = new Intent(BrandActivity.this,TypeActivity.class);
                intenttype.putExtra("brand",((SortModel)adapter.getItem(position)).getName());
                intenttype.putExtra("brandpingying",characterParser.getSelling(((SortModel)adapter.getItem(position)).getName()));
                startActivity(intenttype);
            }
        });

        SourceDateList = filledData(getResources().getStringArray(R.array.date));

        // 根据a-z进行排序源数据
        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);


        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

        //根据输入框输入值的改变来过滤搜索
        mClearEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });


    }
   private class backbrand implements View.OnClickListener{

       @Override
       public void onClick(View view) {
           SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
           SharedPreferences.Editor editor=sPreferences.edit();
           editor.putString("state", "back");
           editor.commit();
           finish();
       }
   }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            SharedPreferences sPreferences=getSharedPreferences("config", MODE_PRIVATE);
            SharedPreferences.Editor editor=sPreferences.edit();
            editor.putString("state", "back");
            editor.commit();
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }

    }
    //选择德国监听事件
    private class choosegermany implements View.OnClickListener {


        @Override
        public void onClick(View view) {
            changebg("germany");
            SourcegermanyList = filledData(getResources().getStringArray(R.array.germany));
            // 根据a-z进行排序源数据
            Collections.sort(SourcegermanyList, pinyinComparator);
            adapter = new SortAdapter(BrandActivity.this, SourcegermanyList);
            sortListView.setAdapter(adapter);
        }
    }
    //选择美国监听事件
    private class chooseamerica implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            changebg("america");
            SourceamericaList = filledData(getResources().getStringArray(R.array.america));
            // 根据a-z进行排序源数据
            Collections.sort(SourceamericaList, pinyinComparator);
            adapter = new SortAdapter(BrandActivity.this, SourceamericaList);
            sortListView.setAdapter(adapter);
        }
    }
    //选择法国监听事件
    private class choosefrance implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            changebg("france");
            SourcefranceList = filledData(getResources().getStringArray(R.array.france));
            // 根据a-z进行排序源数据
            Collections.sort(SourcefranceList, pinyinComparator);
            adapter = new SortAdapter(BrandActivity.this, SourcefranceList);
            sortListView.setAdapter(adapter);
        }
    }
    //选择意大利监听事件
    private class chooseitaly implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            changebg("italy");
            SourceitalyList = filledData(getResources().getStringArray(R.array.italy));
            // 根据a-z进行排序源数据
            Collections.sort(SourceitalyList, pinyinComparator);
            adapter = new SortAdapter(BrandActivity.this, SourceitalyList);
            sortListView.setAdapter(adapter);
        }
    }
    //选择英国监听事件
    private class choosebritain implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            changebg("britain");
            SourcebritainList = filledData(getResources().getStringArray(R.array.britain));
            // 根据a-z进行排序源数据
            Collections.sort(SourcebritainList, pinyinComparator);
            adapter = new SortAdapter(BrandActivity.this, SourcebritainList);
            sortListView.setAdapter(adapter);
        }
    }
    //选择日本监听事件
    private class choosejapan implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            changebg("japan");
            SourcejapanList = filledData(getResources().getStringArray(R.array.japan));
            // 根据a-z进行排序源数据
            Collections.sort(SourcejapanList, pinyinComparator);
            adapter = new SortAdapter(BrandActivity.this, SourcejapanList);
            sortListView.setAdapter(adapter);
        }
    }
    //选择中国监听事件
    private class choosechinese implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            changebg("chinese");
            SourcechineseList = filledData(getResources().getStringArray(R.array.chinese));
            // 根据a-z进行排序源数据
            Collections.sort(SourcechineseList, pinyinComparator);
            adapter = new SortAdapter(BrandActivity.this, SourcechineseList);
            sortListView.setAdapter(adapter);
        }
    }
    //选择其他监听事件
    private class chooseother implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            changebg("other");
            SourceotherList = filledData(getResources().getStringArray(R.array.other));
            // 根据a-z进行排序源数据
            Collections.sort(SourceotherList, pinyinComparator);
            adapter = new SortAdapter(BrandActivity.this, SourceotherList);
            sortListView.setAdapter(adapter);
        }
    }
    /**
     * 为ListView填充数据
     * @param date
     * @return
     */
    private List<SortModel> filledData(String [] date){
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for(int i=0; i<date.length; i++){
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            //汉字转换成拼音
            String pinyin = characterParser.getSelling(date[i]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if(sortString.matches("[A-Z]")){
                sortModel.setSortLetters(sortString.toUpperCase());
            }else{
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     * @param filterStr
     */
    private void filterData(String filterStr){
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if(TextUtils.isEmpty(filterStr)){
            filterDateList = SourceDateList;
        }else{
            filterDateList.clear();
            for(SortModel sortModel : SourceDateList){
                String name = sortModel.getName();
                if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }


    private void changebg(String flag){
        if(flag.equals("germany")){
            germany.setBackgroundColor(Color.parseColor("#FFFFFF"));
            america.setBackgroundResource(R.drawable.notselected_bg);
            france.setBackgroundResource(R.drawable.notselected_bg);
            italy.setBackgroundResource(R.drawable.notselected_bg);
            britain.setBackgroundResource(R.drawable.notselected_bg);
            japan.setBackgroundResource(R.drawable.notselected_bg);
            chinese.setBackgroundResource(R.drawable.notselected_bg);
            other.setBackgroundResource(R.drawable.notselected_bg);
        }
        if(flag.equals("america")){
            germany.setBackgroundResource(R.drawable.notselected_bg);
            america.setBackgroundColor(Color.parseColor("#FFFFFF"));
            france.setBackgroundResource(R.drawable.notselected_bg);
            italy.setBackgroundResource(R.drawable.notselected_bg);
            britain.setBackgroundResource(R.drawable.notselected_bg);
            japan.setBackgroundResource(R.drawable.notselected_bg);
            chinese.setBackgroundResource(R.drawable.notselected_bg);
            other.setBackgroundResource(R.drawable.notselected_bg);
        }
        if(flag.equals("france")){
            france.setBackgroundColor(Color.parseColor("#FFFFFF"));
            germany.setBackgroundResource(R.drawable.notselected_bg);
            america.setBackgroundResource(R.drawable.notselected_bg);
            italy.setBackgroundResource(R.drawable.notselected_bg);
            britain.setBackgroundResource(R.drawable.notselected_bg);
            japan.setBackgroundResource(R.drawable.notselected_bg);
            chinese.setBackgroundResource(R.drawable.notselected_bg);
            other.setBackgroundResource(R.drawable.notselected_bg);

        }
        if(flag.equals("italy")){
            italy.setBackgroundColor(Color.parseColor("#FFFFFF"));
            france.setBackgroundResource(R.drawable.notselected_bg);
            germany.setBackgroundResource(R.drawable.notselected_bg);
            america.setBackgroundResource(R.drawable.notselected_bg);
            britain.setBackgroundResource(R.drawable.notselected_bg);
            japan.setBackgroundResource(R.drawable.notselected_bg);
            chinese.setBackgroundResource(R.drawable.notselected_bg);
            other.setBackgroundResource(R.drawable.notselected_bg);

        }
        if(flag.equals("britain")){
            britain.setBackgroundColor(Color.parseColor("#FFFFFF"));
            italy.setBackgroundResource(R.drawable.notselected_bg);
            france.setBackgroundResource(R.drawable.notselected_bg);
            germany.setBackgroundResource(R.drawable.notselected_bg);
            america.setBackgroundResource(R.drawable.notselected_bg);
            japan.setBackgroundResource(R.drawable.notselected_bg);
            chinese.setBackgroundResource(R.drawable.notselected_bg);
            other.setBackgroundResource(R.drawable.notselected_bg);
        }
        if(flag.equals("japan")){
            japan.setBackgroundColor(Color.parseColor("#FFFFFF"));
            britain.setBackgroundResource(R.drawable.notselected_bg);
            italy.setBackgroundResource(R.drawable.notselected_bg);
            france.setBackgroundResource(R.drawable.notselected_bg);
            germany.setBackgroundResource(R.drawable.notselected_bg);
            america.setBackgroundResource(R.drawable.notselected_bg);
            chinese.setBackgroundResource(R.drawable.notselected_bg);
            other.setBackgroundResource(R.drawable.notselected_bg);
        }
        if(flag.equals("chinese")){
            chinese.setBackgroundColor(Color.parseColor("#FFFFFF"));
            japan.setBackgroundResource(R.drawable.notselected_bg);
            britain.setBackgroundResource(R.drawable.notselected_bg);
            italy.setBackgroundResource(R.drawable.notselected_bg);
            france.setBackgroundResource(R.drawable.notselected_bg);
            germany.setBackgroundResource(R.drawable.notselected_bg);
            america.setBackgroundResource(R.drawable.notselected_bg);
            other.setBackgroundResource(R.drawable.notselected_bg);
        }
        if(flag.equals("other")){
            other.setBackgroundColor(Color.parseColor("#FFFFFF"));
            chinese.setBackgroundResource(R.drawable.notselected_bg);
            japan.setBackgroundResource(R.drawable.notselected_bg);
            britain.setBackgroundResource(R.drawable.notselected_bg);
            italy.setBackgroundResource(R.drawable.notselected_bg);
            france.setBackgroundResource(R.drawable.notselected_bg);
            germany.setBackgroundResource(R.drawable.notselected_bg);
            america.setBackgroundResource(R.drawable.notselected_bg);
        }


    }

}

