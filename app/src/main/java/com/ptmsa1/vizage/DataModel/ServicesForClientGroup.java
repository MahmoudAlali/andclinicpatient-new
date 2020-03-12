package com.ptmsa1.vizage.DataModel;

public class ServicesForClientGroup {
String index,nameService;

    public ServicesForClientGroup(String index, String nameService) {
        this.index = index;
        this.nameService = nameService;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }
}
