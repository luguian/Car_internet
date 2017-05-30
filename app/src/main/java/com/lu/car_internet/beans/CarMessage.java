package com.lu.car_internet.beans;

import java.io.Serializable;

/**
 * Created by lu on 2017/3/28.
 */

public class CarMessage implements Serializable {
    /**
     * 汽车编号
     */
    private String car_id;

    /**
     * 汽车品牌
     */
    private String car_brand;
    /**
     * 汽车标志
     */
    private String car_flag;
    /**
     * 汽车型号
     */
    private String car_type;
    /**
     * 车牌号码
     */
    private String car_number;
    /**
     * 发动机号
     */
    private String car_motor_number;



    /**
     * 车身级别
     */
    private String car_bodylevel;



    public String getCar_id() {
        return car_id;
    }
    public void setCar_id(String car_id) {
        this.car_id = car_id;
    }
    public String getCar_brand() {
        return car_brand;
    }
    public void setCar_brand(String car_brand) {
        this.car_brand = car_brand;
    }
    public String getCar_flag() {
        return car_flag;
    }
    public void setCar_flag(String car_flag) {
        this.car_flag = car_flag;
    }
    public String getCar_type() {
        return car_type;
    }
    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }
    public String getCar_number() {
        return car_number;
    }
    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }
    public String getCar_motor_number() {
        return car_motor_number;
    }
    public void setCar_motor_number(String car_motor_number) {
        this.car_motor_number = car_motor_number;
    }
    public String getCar_bodylevel() {
        return car_bodylevel;
    }
    public void setCar_bodylevel(String car_bodylevel) {
        this.car_bodylevel = car_bodylevel;
    }
    public CarMessage(String car_id, String car_brand, String car_flag,
                      String car_type, String car_number, String car_motor_number,
                      String car_bodylevel) {
        super();
        this.car_id = car_id;
        this.car_brand = car_brand;
        this.car_flag = car_flag;
        this.car_type = car_type;
        this.car_number = car_number;
        this.car_motor_number = car_motor_number;
        this.car_bodylevel = car_bodylevel;
    }

    //无参构造函数
    public CarMessage() {
        // TODO 自动生成的构造函数存根
    }


}
