package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import com.lu.car_internet.R;
import com.lu.car_internet.adapters.SortAdapter;
import com.lu.car_internet.config.CharacterParser;
import com.lu.car_internet.config.PinyinComparator;
import com.lu.car_internet.model.SortModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lu on 2017/3/27.
 */

public class BodylevelActivity extends Activity {
    private List<SortModel> bodylevelDateList;
    private ListView bodtlevel_list;
    private PinyinComparator pinyinComparator;
    private CharacterParser characterParser;
    private SortAdapter adapter;
    private String brand;
    private String brandpingying;
    private String Cartype;
    private ImageButton bodylevel_back;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bodylevel);
        initViews();
    }

    private void initViews(){
        //实例化汉字转拼音类
        Intent intent =getIntent();
        brand = intent.getStringExtra("brand");
        brandpingying = intent.getStringExtra("brandpingying");
        Cartype = intent.getStringExtra("type");
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        bodtlevel_list = (ListView)findViewById(R.id.bodtlevel_list);
        bodylevel_back = (ImageButton)findViewById(R.id.bodylevel_back);
        bodylevel_back.setOnClickListener(new bodylevelback());
        bodtlevel_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                // characterParser.getSelling(((SortModel)adapter.getItem(position)).getName()).startsWith(filterStr.toString())
                //  Toast.makeText(getApplication(),  characterParser.getSelling(((SortModel)adapter.getItem(position)).getName()), Toast.LENGTH_SHORT).show();
                // Toast.makeText(getApplication(), ((SortModel)adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
                Intent intentcarnumber = new Intent(BodylevelActivity.this,CarnumberActivity.class);
              //  intenttype.putExtra("brand",((SortModel)adapter.getItem(position)).getName());
              //  intenttype.putExtra("brandpingying",characterParser.getSelling(((SortModel)adapter.getItem(position)).getName()));
                intentcarnumber.putExtra("type",Cartype);
                intentcarnumber.putExtra("bodylevel",((SortModel)adapter.getItem(position)).getName());
                intentcarnumber.putExtra("brand",brand);
                intentcarnumber.putExtra("brandpingying",brandpingying);
                startActivity(intentcarnumber);
            }
        });
        bodylevelDateList = filledData(getResources().getStringArray(R.array.bodylevel));

        // 根据a-z进行排序源数据
        Collections.sort(bodylevelDateList, pinyinComparator);
        adapter = new SortAdapter(this, bodylevelDateList);
        bodtlevel_list.setAdapter(adapter);
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
   private class bodylevelback implements View.OnClickListener{

       @Override
       public void onClick(View view) {
           finish();
       }
   }
}
