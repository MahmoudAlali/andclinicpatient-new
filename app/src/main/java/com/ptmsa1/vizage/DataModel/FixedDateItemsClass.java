package com.ptmsa1.vizage.DataModel;

public class FixedDateItemsClass {
    String serviceName,empName,time;

    public FixedDateItemsClass(String serviceName, String empName, String time) {
        this.serviceName = serviceName;
        this.empName = empName;
        this.time = time;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
