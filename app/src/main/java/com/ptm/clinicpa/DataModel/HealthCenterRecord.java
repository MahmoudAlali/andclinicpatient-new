package com.ptm.clinicpa.DataModel;

public class HealthCenterRecord {
    String id,health_record,health_center_id,health_center_en,health_center_ar;

    public HealthCenterRecord(String id,String health_center_id,String health_record,String health_center_ar,String health_center_en)
    {
        this.health_center_id=health_center_id;
        this.health_record=health_record;
        this.health_center_ar=health_center_ar;
        this.health_center_en=health_center_en;
        this.id=id;
    }

    public String getHealth_center_id() {
        return health_center_id;
    }

    public String getHealth_record() {
        return health_record;
    }

    public String getId() {
        return id;
    }

    public String getHealth_center_en() {
        return health_center_en;
    }

    public String getHealth_center_ar() {
        return health_center_ar;
    }
}
