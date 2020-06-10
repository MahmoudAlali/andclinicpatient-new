package com.ptm.clinicpa.DataModel;

import com.ptm.clinicpa.Activities.Services;

public class ServicesInsideAppointment {
    String bdb_id ;
    String bdb_price ;
    String service_en_name ;
    String service_ar_name ;
    String service_category ;
    String is_checked_in2 ;
    String visit_type2 ;
    String basic_price2 ;

    public ServicesInsideAppointment( String bdb_id ,String bdb_price ,String service_en_name ,String service_ar_name ,
            String service_category , String is_checked_in2 , String visit_type2 , String basic_price2 )
    {
        this. bdb_id=bdb_id ;
        this. bdb_price=bdb_price ;
        this. service_en_name=service_en_name ;
        this. service_ar_name=service_ar_name ;
        this. service_category=service_category ;
        this. is_checked_in2=is_checked_in2 ;
        this. visit_type2=visit_type2 ;
        this. basic_price2=basic_price2 ;
    }

    public String getBdb_id() {
        return bdb_id;
    }

    public String getBdb_price() {
        return bdb_price;
    }

    public String getBasic_price2() {
        return basic_price2;
    }

    public String getIs_checked_in2() {
        return is_checked_in2;
    }

    public String getService_ar_name() {
        return service_ar_name;
    }

    public String getService_category() {
        return service_category;
    }

    public String getService_en_name() {
        return service_en_name;
    }

    public String getVisit_type2() {
        return visit_type2;
    }
}
