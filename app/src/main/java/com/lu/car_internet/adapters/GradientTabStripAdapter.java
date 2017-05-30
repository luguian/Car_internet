package com.lu.car_internet.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;


import com.lu.car_internet.R;
import com.lu.car_internet.fragments.CarFragment;
import com.lu.car_internet.fragments.HomeFragment;
import com.lu.car_internet.fragments.LiveFragment;
import com.lu.car_internet.fragments.PersonalFragment;

import am.widget.gradienttabstrip.GradientTabStrip;

import static android.support.v7.content.res.AppCompatResources.getDrawable;

/**
 * Created by lu on 2017/2/7.
 */

public class GradientTabStripAdapter extends FragmentPagerAdapter implements
        GradientTabStrip.GradientTabAdapter{

    public GradientTabStripAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public int getCount() {
        return 4;
    }


    @Override
    public Fragment getItem(int position) {
        String title = getPageTitle(position).toString();
        switch (position) {
            default:
            case 0:
                HomeFragment f1 = new  HomeFragment();
                return f1;
            case 1:
                CarFragment f2 = new CarFragment();
                return f2;
            case 2:
                LiveFragment f3 = new LiveFragment();
                return f3;
            case 3:
                PersonalFragment f4 = new PersonalFragment();
                return f4;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            default:
            case 0:
                return "首页";
            case 1:
                return "车辆";
            case 2:
                return "朋友";
            case 3:
                return "个人信息";
        }
    }

    @Override
    public Drawable getNormalDrawable(int position, Context context) {
        switch (position) {
            default:
            case 0:
                return ContextCompat.getDrawable(context, R.drawable.ic_gradienttabstrip_chat_normal);
            case 1:
                return ContextCompat.getDrawable(context, R.drawable.ic_gradienttabstrip_contacts_normal);
            case 2:
                return ContextCompat.getDrawable(context, R.drawable.ic_gradienttabstrip_discovery_normal);
            case 3:
                return ContextCompat.getDrawable(context, R.drawable.ic_gradienttabstrip_account_normal);
        }
    }

    @Override
    public Drawable getSelectedDrawable(int position, Context context) {
        switch (position) {
            default:
            case 0:
                return ContextCompat.getDrawable(context, R.drawable.ic_gradienttabstrip_chat_selected);
            case 1:
                return ContextCompat.getDrawable(context, R.drawable.ic_gradienttabstrip_contacts_selected);
            case 2:
                return ContextCompat.getDrawable(context, R.drawable.ic_gradienttabstrip_discovery_selected);
            case 3:
                return ContextCompat.getDrawable(context, R.drawable.ic_gradienttabstrip_account_selected);
        }
    }

    @Override
    public boolean isTagEnable(int position) {
        return position != 3;
    }

    @Override
    public String getTag(int position) {
        switch (position) {
            default:
            case 0:
                return "888";
            case 1:
                return "";
            case 2:
                return "new";
        }
    }
}
