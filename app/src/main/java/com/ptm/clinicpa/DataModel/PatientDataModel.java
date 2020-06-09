package com.ptm.clinicpa.DataModel;

public class PatientDataModel {
    String bdb_id,bdb_health_record,bdb_gender,bdb_relation,bdb_user_name;

    public PatientDataModel(String bdb_id,String bdb_health_record,String bdb_gender,String bdb_relation,String bdb_user_name )
    {
        this.bdb_gender=bdb_gender;
        this.bdb_health_record=bdb_health_record;
        this.bdb_id=bdb_id;
        this.bdb_relation=bdb_relation;
        this.bdb_user_name=bdb_user_name;
    }

    public String getBdb_id() {
        return bdb_id;
    }

    public String getBdb_gender() {
        return bdb_gender;
    }

    public String getBdb_health_record() {
        return bdb_health_record;
    }

    public String getBdb_relation() {
        return bdb_relation;
    }

    public String getBdb_user_name() {
        return bdb_user_name;
    }
}
