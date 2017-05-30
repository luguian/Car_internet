package com.lu.car_internet.presenter;

import android.content.Context;

import com.lu.car_internet.beans.NewsDetailBean;
import com.lu.car_internet.model.NewsModel;
import com.lu.car_internet.model.NewsModelImpl;
import com.lu.car_internet.model.OnLoadNewsDetailListener;
import com.lu.car_internet.view.NewsDetailView;


/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 2015/12/21
 */
public class NewsDetailPresenterImpl implements NewsDetailPresenter, OnLoadNewsDetailListener {

    private Context mContent;
    private NewsDetailView mNewsDetailView;
    private NewsModel mNewsModel;

    public NewsDetailPresenterImpl(Context mContent, NewsDetailView mNewsDetailView) {
        this.mContent = mContent;
        this.mNewsDetailView = mNewsDetailView;
        mNewsModel = new NewsModelImpl();
    }

    @Override
    public void loadNewsDetail(final String docId) {
        mNewsDetailView.showProgress();
        mNewsModel.loadNewsDetail(docId, this);
    }


    @Override
    public void onSuccess(NewsDetailBean newsDetailBean) {
        if (newsDetailBean != null) {
            mNewsDetailView.showNewsDetialContent(newsDetailBean.getBody());
        }
        mNewsDetailView.hideProgress();
    }

    @Override
    public void onFailure(String msg, Exception e) {
        mNewsDetailView.hideProgress();
    }
}
