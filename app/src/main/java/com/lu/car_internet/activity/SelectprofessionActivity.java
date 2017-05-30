package com.lu.car_internet.activity;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.adapters.ProfessionAdapter;
import com.lu.car_internet.adapters.SortAdapter;
import com.lu.car_internet.model.SortModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu on 2017/5/4.
 */

public class SelectprofessionActivity extends Activity {

    private ListView professionList;
    private List<SortModel> typeList;
    private ProfessionAdapter adapter;
    private Button save_professionbtn;
    private ImageButton professback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectprofession);
        professionList = (ListView)findViewById(R.id.profession_list);
        save_professionbtn = (Button)findViewById(R.id.save_professionbtn);
        professback = (ImageButton)findViewById(R.id.professback);
        professback.setOnClickListener(new professionback());
        save_professionbtn.setOnClickListener(new saveprofession());
        professionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelectedPosition(i);
                adapter.notifyDataSetInvalidated();
                save_professionbtn.setVisibility(View.VISIBLE);


            }
        });
        typeList = filledData(getResources().getStringArray(R.array.profession));
        adapter = new ProfessionAdapter(this, typeList);
        professionList.setAdapter(adapter);
    }


    private List<SortModel> filledData(String [] date){
        List<SortModel> mSortList = new ArrayList<SortModel>();

        for(int i=0; i<date.length; i++) {
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);
            mSortList.add(sortModel);
        }
        return mSortList;
    }

    private class saveprofession implements View.OnClickListener{


        @Override
        public void onClick(View view) {
           finish();

        }
    }

    private class professionback implements View.OnClickListener{

        @Override
        public void onClick(View view) {
            finish();
        }
    }
}
