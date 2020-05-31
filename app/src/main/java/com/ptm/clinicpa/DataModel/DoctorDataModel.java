package com.ptm.clinicpa.DataModel;

public class DoctorDataModel {
    String bdb_id,
            bdb_name,
            bdb_gender,
            bdb_extra_info,
            bdb_supported_gender,
            bdb_specialization_name_ar,
            bdb_specialization_name_en,
            is_fav_doctor;

    public DoctorDataModel(String bdb_id,String bdb_name,String bdb_gender,String bdb_extra_info,String bdb_supported_gender,
                           String bdb_specialization_name_ar,String bdb_specialization_name_en,String is_fav_doctor)
    {
        this.bdb_extra_info=bdb_extra_info;
        this.bdb_gender=bdb_gender;
        this.bdb_id=bdb_id;
        this.bdb_name=bdb_name;
        this.bdb_specialization_name_ar=bdb_specialization_name_ar;
        this.bdb_specialization_name_en=bdb_specialization_name_en;
        this.bdb_supported_gender=bdb_supported_gender;
        this.is_fav_doctor=is_fav_doctor;
    }

    public String getBdb_id() {
        return bdb_id;
    }

    public String getBdb_name() {
        return bdb_name;
    }

    public String getBdb_extra_info() {
        return bdb_extra_info;
    }

    public String getBdb_gender() {
        return bdb_gender;
    }

    public String getBdb_specialization_name_ar() {
        return bdb_specialization_name_ar;
    }

    public String getBdb_specialization_name_en() {
        return bdb_specialization_name_en;
    }

    public String getBdb_supported_gender() {
        return bdb_supported_gender;
    }

    public String getIs_fav_doctor() {
        return is_fav_doctor;
    }

}
