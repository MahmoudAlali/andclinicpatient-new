package com.ptmsa1.clinicclient.DataModel;

public class ReservationClientsEmployee {

    String serviceName,employeeName,day,time;

    public ReservationClientsEmployee(String serviceName, String employeeName, String day, String time) {
        this.serviceName = serviceName;
        this.employeeName = employeeName;
        this.day = day;
        this.time = time;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
