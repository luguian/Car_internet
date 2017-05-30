package com.lu.car_internet.adapters;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lu.car_internet.R;
import com.lu.car_internet.model.ListModel;
import com.lu.car_internet.model.SortModel;

import java.util.List;

import static com.tencent.open.utils.Global.getContext;
import static com.tencent.open.utils.Global.getSharedPreferences;


/**
 * Created by lu on 2017/5/4.
 */

public class ProfessionAdapter extends BaseAdapter {

    private List<SortModel> mDate;
    private Context mContext;
    final int VIEW_TYPE = 2;
    final int TYPE_1 = 0;
    final int TYPE_2 = 1;
    LayoutInflater inflater;

    private int selectedPosition = -1;
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

    public ProfessionAdapter( Context mContext,List mDate){
        this.mContext=mContext;
        this.mDate=mDate;
    }
    public List<SortModel> getmDate() {
        return mDate;
    }

    public void setmDate(List<SortModel> mDate) {
        this.mDate = mDate;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mDate.size();
    }
    @Override
    public Object getItem(int position) {
        return mDate.get(position);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolderhead head = null;
        ViewHolderprofession profession = null;
        int type = getItemViewType(position);
        if(convertView == null){
            inflater = LayoutInflater.from(mContext);
            //按照当前布局需求，确定布局
            switch(type){
                case TYPE_1:
                    convertView = inflater.inflate(R.layout.activity_pensonitemhead,
                            viewGroup, false);
                    head = new ViewHolderhead();
                    head.profession_item = (TextView)convertView.findViewById(R.id.listView_head);
                    convertView.setTag(head);
                    break;
                case TYPE_2:
                    convertView = inflater.inflate(R.layout.activity_ordinarypensonitem,
                            viewGroup, false);
                    RelativeLayout ordinary_rl =(RelativeLayout) convertView. findViewById(R.id.ordinary_rl);
                   // ImageView ischoose=(ImageView) convertView. findViewById(R.id.ischoose);
//                    ordinary_rl.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//
//
//                        }
//                    });


                    profession = new ViewHolderprofession();
                    profession.profession_item = (TextView)convertView.findViewById(R.id.profession_item);
                    profession.ischoose = (ImageView)convertView.findViewById(R.id.ischoose);
                    convertView.setTag(profession);

                    break;
            }
        }else{
            switch(type){
                case TYPE_1:
                    head = (ViewHolderhead)convertView.getTag();
                    break;
                case TYPE_2:
                    profession = (ViewHolderprofession)convertView.getTag();
                    break;
            }
        }

        switch(type){
            case TYPE_1:
            head.profession_item.setText(this.mDate.get(position).getName());
            break;
            case TYPE_2:
            profession.profession_item.setText(this.mDate.get(position).getName());
                if (selectedPosition == position) {
                    if (profession.ischoose.getVisibility() == View.GONE) {
                        profession.ischoose.setVisibility(View.VISIBLE);
                        SharedPreferences sPreferences=mContext.getSharedPreferences("config", getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor=sPreferences.edit();
                        editor.putString("profession", profession.profession_item.getText().toString());
                        editor.commit();
                    }else{
                        SharedPreferences sPreferences=mContext.getSharedPreferences("config", getContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor=sPreferences.edit();
                        editor.putString("profession", "");
                        editor.commit();
                        profession.ischoose.setVisibility(View.GONE);
                    }
                }else{
                    profession.ischoose.setVisibility(View.GONE);
                }
            break;
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        int p = position;
        if (p == 0||p==14||p==22||p==28||p==36||p==44||p==49||p==57||p==64||p==74||p==81)
            //头部
            return TYPE_1;
        else
            //普通布局
            return TYPE_2;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    //头部
    public class ViewHolderhead{
     TextView profession_item;
    }

    //普通列表
    public class ViewHolderprofession{
        TextView profession_item;
        ImageView ischoose;
    }


}
