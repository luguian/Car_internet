package com.lu.car_internet.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.adapters.SortAdapter;
import com.lu.car_internet.config.CharacterParser;
import com.lu.car_internet.config.PinyinComparator;
import com.lu.car_internet.model.SortModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lu on 2017/3/24.
 */

public class TypeActivity extends Activity {
    private ListView typeListview;
    private SortAdapter adapter;
    private List<SortModel> typeList;
    private RelativeLayout car_typeshow;
    private TextView selected_brandtv;
    private EditText edit_type;
    private Button commit_cartype;
    private String brand;
    private String brandpingying;
    private ImageButton type_back;

    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type);
        initViews();
    }
    private void initViews(){
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        typeListview = (ListView) findViewById(R.id.type_list);
        selected_brandtv = (TextView) findViewById(R.id.selected_brandtv);
        car_typeshow = (RelativeLayout) findViewById(R.id.car_typeshow);
        edit_type = (EditText) findViewById(R.id.edit_type);
        commit_cartype = (Button) findViewById(R.id.commit_cartype);
        type_back = (ImageButton) findViewById(R.id.type_back);
        type_back.setOnClickListener(new typeback());
        commit_cartype.setOnClickListener(new committype());
        edit_type.addTextChangedListener(new OnEditCartype());
        Intent intent =getIntent();
        brand = intent.getStringExtra("brand");
        brandpingying = intent.getStringExtra("brandpingying");
        selected_brandtv.setText(brand);
        typeListview.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //这里要利用adapter.getItem(position)来获取当前position所对应的对象
                //Toast.makeText(getApplication(), characterParser.getSelling(((SortModel)adapter.getItem(position)).getName()), Toast.LENGTH_SHORT).show();
                Intent intentcarbodylevel = new Intent(TypeActivity.this,BodylevelActivity.class);
                intentcarbodylevel.putExtra("type",((SortModel)adapter.getItem(position)).getName());
                intentcarbodylevel.putExtra("brand",brand);
                intentcarbodylevel.putExtra("brandpingying",brandpingying);
               // intentcarmessage.putExtra("brandimg", characterParser.getSelling(brand));
                startActivity(intentcarbodylevel);
            }
        });

        binddate(brandpingying);
        // 根据a-z进行排序源数据
        if(typeList != null){
            Collections.sort(typeList, pinyinComparator);
            adapter = new SortAdapter(this, typeList);
            typeListview.setAdapter(adapter);
        }



    }
    private class typeback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }
    private class committype implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            String type = edit_type.getText().toString();
            Intent intentcarbodylevel = new Intent(TypeActivity.this,BodylevelActivity.class);
            intentcarbodylevel.putExtra("type",type);
            intentcarbodylevel.putExtra("brand",brand);
            intentcarbodylevel.putExtra("brandpingying",brandpingying);
            // intentcarmessage.putExtra("brandimg", characterParser.getSelling(brand));
            startActivity(intentcarbodylevel);

        }
    }

    private class OnEditCartype implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (charSequence.length() >= 1)
                commit_cartype.setEnabled(true);
            else
                commit_cartype.setEnabled(false);

        }

        @Override
        public void afterTextChanged(Editable editable) {

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
    private void binddate(String carbrand){
        String flag = "true";
        if(carbrand.equals("aodi")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.aodi));
        }
        if(carbrand.equals("babosi")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.babosi));
        }
        if(carbrand.equals("baoma")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.baoma));
        }
        if(carbrand.equals("baoshijie")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.baoshijie));
        }
        if(carbrand.equals("benchi")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.benchi));
        }
        if(carbrand.equals("dazhong")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.dazhong));
        }
        if(carbrand.equals("maibahe")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.maibahe));
        }
        if(carbrand.equals("MINI")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.maibahe));
        }
        if(carbrand.equals("sikeda")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.maibahe));
        }
        if(carbrand.equals("weiziman")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.weiziman));
        }
        if(carbrand.equals("bieke")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.bieke));
        }
        if(carbrand.equals("daoqi")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.daoqi));
        }
        if(carbrand.equals("fute")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.fute));
        }
        if(carbrand.equals("hanma")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.hanma));
        }
        if(carbrand.equals("Jeep")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.Jeep));
        }
        if(carbrand.equals("kaidilake")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.kaidilake));
        }
        if(carbrand.equals("kelaisile")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.kelaisile));
        }
        if(carbrand.equals("linkeng")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.linkeng));
        }
        if(carbrand.equals("oubao")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.oubao));
        }
        if(carbrand.equals("xuefolan")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.xuefolan));
        }
        if(carbrand.equals("biaozhi")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.biaozhi));
        }
        if(carbrand.equals("leinuo")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.leinuo));
        }
        if(carbrand.equals("xuetielong")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.xuetielong));
        }
        if(carbrand.equals("bujiadi")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.bujiadi));
        }
        if(carbrand.equals("falali")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.falali));
        }
        if(carbrand.equals("feiyate")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.feiyate));
        }
        if(carbrand.equals("lanbojini")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.lanbojini));
        }
        if(carbrand.equals("mashaladi")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.mashaladi));
        }
        if(carbrand.equals("bentian")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.bentian));
        }
        if(carbrand.equals("fengtian")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.fengtian));
        }
        if(carbrand.equals("leikesasi")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.leikesasi));
        }
        if(carbrand.equals("lingmu")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.lingmu));
        }
        if(carbrand.equals("mazida")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.mazida));
        }
        if(carbrand.equals("ouge")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.ouge));
        }
        if(carbrand.equals("richan")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.richan));
        }
        if(carbrand.equals("sanling")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.sanling));
        }
        if(carbrand.equals("qiya")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.qiya));
        }
        if(carbrand.equals("xiandai")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.xiandai));
        }
        if(carbrand.equals("jiebao")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.jiebao));
        }
        if(carbrand.equals("laosilaisi")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.laosilaisi));
        }
        if(carbrand.equals("luhu")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.luhu));
        }
        if(carbrand.equals("benteng")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.benteng));
        }
        if(carbrand.equals("biyadi")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.biyadi));
        }
        if(carbrand.equals("changan")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.changan));
        }
        if(carbrand.equals("qichen")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.qichen));
        }
        if(carbrand.equals("qirui")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.qirui));
        }
        if(carbrand.equals("rongwei")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.rongwei));
        }
        if(carbrand.equals("wuling")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.wuling));
        }
        if(carbrand.equals("zhonghua")){
            flag="false";
            typeList = filledData(getResources().getStringArray(R.array.zhonghua));
        }
        if(!flag.equals("false")){
             typeListview.setVisibility(View.GONE);
             car_typeshow.setVisibility(View.VISIBLE);

        }
    }


}
