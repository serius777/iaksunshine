package com.hudapc.iaksunshine.model;

import java.util.ArrayList;

/**
 * Created by lilmechine on 11/18/17.
 */

public class RequestDailyForeCast
{
    private String cod;
    private float message;
    private City city;
    private int cnt;
    private ArrayList<ForeCast> list;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public float getMessage() {
        return message;
    }

    public void setMessage(float message) {
        this.message = message;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ArrayList<ForeCast> getList() {
        return list;
    }

    public void setList(ArrayList<ForeCast> list) {
        this.list = list;
    }
}
