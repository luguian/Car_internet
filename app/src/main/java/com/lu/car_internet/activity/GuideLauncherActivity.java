package com.lu.car_internet.activity;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lu.car_internet.R;
import com.lu.car_internet.adapters.BaseFragmentAdapter;
import com.lu.car_internet.fragments.CarcontrolFragment;
import com.lu.car_internet.fragments.LauncherBaseFragment;
import com.lu.car_internet.fragments.LiveMessageLauncherFragment;
import com.lu.car_internet.fragments.StereoscopicLauncherFragment;
import com.lu.car_internet.view.GuideViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lu on 2017/2/8.
 */

public class GuideLauncherActivity extends FragmentActivity {
    private GuideViewPager vPager;
    private List<LauncherBaseFragment> list = new ArrayList<LauncherBaseFragment>();
    private BaseFragmentAdapter adapter;

    private ImageView[] tips;
    private int currentSelect;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luancher_main);

        //初始化点点点控件
        ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup);
        tips = new ImageView[3];
        for (int i = 0; i < tips.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(10, 10));
            if (i == 0) {
                imageView.setBackgroundResource(R.drawable.page_indicator_focused);
            } else {
                imageView.setBackgroundResource(R.drawable.page_indicator_unfocused);
            }
            tips[i] = imageView;

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            layoutParams.leftMargin = 20;//设置点点点view的左边距
            layoutParams.rightMargin = 20;//设置点点点view的右边距
            layoutParams.bottomMargin =20;
            group.addView(imageView, layoutParams);
        }

        //获取自定义viewpager 然后设置背景图片
        vPager = (GuideViewPager) findViewById(R.id.viewpager_launcher);
        vPager.setBackGroud(BitmapFactory.decodeResource(getResources(), R.drawable.white_bg));

        /**
         * 初始化三个fragment  并且添加到list中
         */
        CarcontrolFragment rewardFragment = new CarcontrolFragment();
        LiveMessageLauncherFragment privateFragment = new LiveMessageLauncherFragment();
        StereoscopicLauncherFragment stereoscopicFragment = new StereoscopicLauncherFragment();
        list.add(rewardFragment);
        list.add(privateFragment);
        list.add(stereoscopicFragment);

        adapter = new BaseFragmentAdapter(getSupportFragmentManager(), list);
        vPager.setAdapter(adapter);
        vPager.setOffscreenPageLimit(2);
        vPager.setCurrentItem(0);
        vPager.setOnPageChangeListener(changeListener);
    }
        /**
         * 监听viewpager的移动
         */
        ViewPager.OnPageChangeListener changeListener=new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int index) {
                setImageBackground(index);//改变点点点的切换效果
                LauncherBaseFragment fragment=list.get(index);
                list.get(currentSelect).stopAnimation();//停止前一个页面的动画
                fragment.startAnimation();//开启当前页面的动画
                currentSelect=index;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageScrollStateChanged(int arg0) {}
        };

        /**
         * 改变点点点的切换效果
         * @param selectItems
         */
        private void setImageBackground(int selectItems) {
            for (int i = 0; i < tips.length; i++) {
                if (i == selectItems) {
                    tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
                } else {
                    tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
                }
            }
        }

}
