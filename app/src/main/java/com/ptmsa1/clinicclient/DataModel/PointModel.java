package com.ptmsa1.clinicclient.DataModel;

public class PointModel {
    String pointsCount,pointsDate,pointsDesc ,pointOPtype;
    public PointModel(String Count, String Date, String Desc, String type)
    {
        pointsCount = Count;
        pointsDate = Date;
        pointsDesc = Desc;
        pointOPtype = type;
    }
    public String getPointsCount() {
        return pointsCount;
    }

    public void setPointsCount(String pointsCount) {
        this.pointsCount = pointsCount;
    }
    public String getPointsDate() {
        return pointsDate;
    }

    public void setPointsDate(String pointsDate) {
        this.pointsDate = pointsDate;
    }
    public String getPointsDesc() {
        return pointsDesc;
    }

    public void setPointsDesc(String pointsDesc) {
        this.pointsDesc = pointsDesc;
    }

    public String getPointOPtype()
    {
        return pointOPtype;
    }

}
