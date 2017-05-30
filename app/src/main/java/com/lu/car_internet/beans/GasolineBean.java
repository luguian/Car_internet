package com.lu.car_internet.beans;

import java.io.Serializable;

/**
 * Created by lu on 2017/4/13.
 */

public class GasolineBean implements Serializable {
    private String gasoline_station;
    private String gasoline_station_address;
    private String e90price;
    public String getE90price() {
        return e90price;
    }

    public void setE90price(String e90price) {
        this.e90price = e90price;
    }

    public String getE93price() {
        return e93price;
    }

    public void setE93price(String e93price) {
        this.e93price = e93price;
    }

    public String getE97price() {
        return e97price;
    }

    public void setE97price(String e97price) {
        this.e97price = e97price;
    }

    public String getE0price() {
        return e0price;
    }

    public void setE0price(String e0price) {
        this.e0price = e0price;
    }

    private String e93price;
    private String e97price;
    private String e0price;


    public GasolineBean(String gasoline_station, String gasoline_station_address) {
        super();
        this.gasoline_station = gasoline_station;
        this.gasoline_station_address = gasoline_station_address;
    }

    public String getGasoline_station() {
        return gasoline_station;
    }

    public void setGasoline_station(String gasoline_station) {
        this.gasoline_station = gasoline_station;
    }

    public String getGasoline_station_address() {
        return gasoline_station_address;
    }

    public void setGasoline_station_address(String gasoline_station_address) {
        this.gasoline_station_address = gasoline_station_address;
    }

    //无参构造函数
    public GasolineBean() {
        // TODO 自动生成的构造函数存根
    }
}
