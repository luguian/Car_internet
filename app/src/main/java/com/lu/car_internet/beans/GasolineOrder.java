package com.lu.car_internet.beans;

import java.io.Serializable;

/**
 * Created by lu on 2017/4/23.
 */

public class GasolineOrder implements Serializable {
    private String gasolineId;
    private String gasolineDate;
    private String gasolineMoney;
    private String gasolineNumber;
    private String carbrand;
    private String carid;
    private String gasoline_state;
    private String gasoline_orderid;
    private String gasoline_status;

    public String getGasoline_status() {
        return gasoline_status;
    }

    public void setGasoline_status(String gasoline_status) {
        this.gasoline_status = gasoline_status;
    }

    public String getGasoline_orderid() {
        return gasoline_orderid;
    }

    public void setGasoline_orderid(String gasoline_orderid) {
        this.gasoline_orderid = gasoline_orderid;
    }

    public String getGasoline_state() {
        return gasoline_state;
    }

    public void setGasoline_state(String gasoline_state) {
        this.gasoline_state = gasoline_state;
    }

    public String getCarid() {
        return carid;
    }

    public void setCarid(String carid) {
        this.carid = carid;
    }

    public String getCarbrand() {
        return carbrand;
    }

    public void setCarbrand(String carbrand) {
        this.carbrand = carbrand;
    }

    public String getGasolineId() {
        return gasolineId;
    }

    public void setGasolineId(String gasolineId) {
        this.gasolineId = gasolineId;
    }

    public String getGasolineDate() {
        return gasolineDate;
    }

    public void setGasolineDate(String gasolineDate) {
        this.gasolineDate = gasolineDate;
    }

    public String getGasolineMoney() {
        return gasolineMoney;
    }

    public void setGasolineMoney(String gasolineMoney) {
        this.gasolineMoney = gasolineMoney;
    }

    public String getGasolineNumber() {
        return gasolineNumber;
    }

    public void setGasolineNumber(String gasolineNumber) {
        this.gasolineNumber = gasolineNumber;
    }

    public String getGasolineStation() {
        return gasolineStation;
    }

    public void setGasolineStation(String gasolineStation) {
        this.gasolineStation = gasolineStation;
    }

    public String getGasolineType() {
        return gasolineType;
    }

    public void setGasolineType(String gasolineType) {
        this.gasolineType = gasolineType;
    }

    private String gasolineStation;
    private String gasolineType;

    //无参构造函数
    public GasolineOrder() {
        // TODO 自动生成的构造函数存根
    }

}
