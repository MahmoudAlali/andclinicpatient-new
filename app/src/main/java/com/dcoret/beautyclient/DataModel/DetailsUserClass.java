package com.dcoret.beautyclient.DataModel;

public class DetailsUserClass {
    String 		bdb_id ,
            bdb_owner_name ,
            bdb_name ,
            bdb_mobile1 ,
            bdb_mobile2 ,
//            bdb_days ,
            bdb_phone ,
            bdb_email ,
//            bdb_email_verified_at ,
//            bdb_points ,
//            bdb_level ,
//            bdb_com_reg_num ,
//            bdb_chairs_num ,
            bdb_gender ;
//            bdb_supplier_type ,
//            bdb_start_time ,
//            bdb_end_time ,
//            bdb_created_at,
//            bdb_updated_at,
//            bdb_nation ,
//            bdb_tasks ,
//            bdb_role ,
//            bdb_sup_id ,
//            bdb_status ;
//            bdb_logo_id ,
//            bdb_book_plcy ,
//            bdb_is_deleted ,
//            bdb_deleted_at ,
//            bdb_free_ads ,
//            bdb_del_reason ,
//            bdb_del_message ,
//            bdb_fb_token ,
//            bdb_book_confirm ,
//            bdb_nation_num ,
//            TotalRating;


    public DetailsUserClass(String bdb_id, String bdb_owner_name, String bdb_name, String bdb_mobile1, String bdb_mobile2, String bdb_phone, String bdb_email, String bdb_gender) {
        this.bdb_id = bdb_id;
        this.bdb_owner_name = bdb_owner_name;
        this.bdb_name = bdb_name;
        this.bdb_mobile1 = bdb_mobile1;
        this.bdb_mobile2 = bdb_mobile2;
        this.bdb_phone = bdb_phone;
        this.bdb_email = bdb_email;
        this.bdb_gender = bdb_gender;
    }

    public String getBdb_id() {
        return bdb_id;
    }

    public void setBdb_id(String bdb_id) {
        this.bdb_id = bdb_id;
    }

    public String getBdb_owner_name() {
        return bdb_owner_name;
    }

    public void setBdb_owner_name(String bdb_owner_name) {
        this.bdb_owner_name = bdb_owner_name;
    }

    public String getBdb_name() {
        return bdb_name;
    }

    public void setBdb_name(String bdb_name) {
        this.bdb_name = bdb_name;
    }

    public String getBdb_mobile1() {
        return bdb_mobile1;
    }

    public void setBdb_mobile1(String bdb_mobile1) {
        this.bdb_mobile1 = bdb_mobile1;
    }

    public String getBdb_mobile2() {
        return bdb_mobile2;
    }

    public void setBdb_mobile2(String bdb_mobile2) {
        this.bdb_mobile2 = bdb_mobile2;
    }

    public String getBdb_phone() {
        return bdb_phone;
    }

    public void setBdb_phone(String bdb_phone) {
        this.bdb_phone = bdb_phone;
    }

    public String getBdb_email() {
        return bdb_email;
    }

    public void setBdb_email(String bdb_email) {
        this.bdb_email = bdb_email;
    }

    public String getBdb_gender() {
        return bdb_gender;
    }

    public void setBdb_gender(String bdb_gender) {
        this.bdb_gender = bdb_gender;
    }
}
