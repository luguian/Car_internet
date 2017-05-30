package com.lu.car_internet.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lu.car_internet.fragments.LauncherBaseFragment;

import java.util.List;

/**
 * Created by lu on 2017/2/8.
 */

public class BaseFragmentAdapter extends FragmentStatePagerAdapter {
    private List<LauncherBaseFragment> list;
    public BaseFragmentAdapter(FragmentManager fm, List<LauncherBaseFragment> list){
        super(fm);
        this.list = list;
    }
    public BaseFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        return list.get(arg0);
    }

    @Override
    public int getCount() {
        return  list.size();
    }
}
