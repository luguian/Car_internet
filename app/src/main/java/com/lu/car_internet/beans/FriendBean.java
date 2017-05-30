package com.lu.car_internet.beans;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by lu on 2017/5/18.
 */

public class FriendBean implements Serializable {

    private String name;
    private Bitmap bitmap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
