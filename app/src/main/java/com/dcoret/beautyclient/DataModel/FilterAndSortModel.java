package com.dcoret.beautyclient.DataModel;

public class FilterAndSortModel {

    String num,value1,value2;

    public FilterAndSortModel(String num, String value1, String value2) {
        this.num = num;
        this.value1 = value1;
        this.value2 = value2;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }
}
