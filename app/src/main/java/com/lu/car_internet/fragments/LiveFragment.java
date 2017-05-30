package com.lu.car_internet.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.lu.car_internet.R;
import com.lu.car_internet.adapters.FriendAdapter;
import com.lu.car_internet.beans.FriendBean;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.width;

/**
 * Created by lu on 2017/2/7.
 */

    public class LiveFragment extends Fragment {
    private ListView friend_list;
    private FriendAdapter friendAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_live,null);
        //LayoutInflater inflater = LayoutInflater.from(AddgasolineActivity.this);
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_frienditem_top, null);
       // View view = inflater.inflate(R.layout.item_listviewtop,null);
        friend_list = (ListView)rootView.findViewById(R.id.friend_list);
        friend_list.addHeaderView(view);
        friendAdapter = new FriendAdapter(getActivity(),getdata());
        friend_list.setAdapter(friendAdapter);
        return rootView;
    }


    private List getdata(){
        List<FriendBean> list = new ArrayList();
        for(int i=0;i<10;i++){
            FriendBean friendBean = new FriendBean();
            friendBean.setName("梦随心所愿");
           // friendBean.setBitmap();
            list.add(friendBean);

        }
        return list;
    }

}
