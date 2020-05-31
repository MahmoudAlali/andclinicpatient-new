package com.ptm.clinicpa.DataModel;

public class HealthCenterImages {
    String bdb_id;
    String bdb_description;

    public HealthCenterImages(String bdb_id,String bdb_description)
    {
        this.bdb_description=bdb_description;
        this.bdb_id=bdb_id;
    }

    public String getBdb_id() {
        return bdb_id;
    }

    public String getBdb_description() {
        return bdb_description;
    }
}
