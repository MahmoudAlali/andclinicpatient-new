package com.dcoret.beautyclient.DataModel;

public class ServiceFilter {
//    int id;
    Boolean ischecked;
    String FilterName;

    public ServiceFilter(Boolean ischecked, String filterName) {
        this.ischecked = ischecked;
//        this.id = id;
        FilterName = filterName;

    }

    public Boolean getIschecked() {
        return ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }

    public String getFilterName() {
        return FilterName;
    }

    public void setFilterName(String filterName) {
        FilterName = filterName;
    }
}
