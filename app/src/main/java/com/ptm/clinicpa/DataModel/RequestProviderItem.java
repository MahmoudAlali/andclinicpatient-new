package com.ptm.clinicpa.DataModel;

public class RequestProviderItem {
    String sup_id,sup_name,logo_id,rating,bdb_booking_period,deposit_prcntg,bdb_loc_long,bdb_loc_lat,bdb_has_experience_cer,bdb_has_health_cer;

    public RequestProviderItem(String sup_id,String sup_name,String logo_id,String rating,String bdb_booking_period,String deposit_prcntg,String bdb_loc_lat,String bdb_loc_long,String bdb_has_experience_cer,String bdb_has_health_cer )
    {
        this.sup_id=sup_id;
        this.sup_name=sup_name;
        this.logo_id=logo_id;
        this.rating=rating;
        this.bdb_booking_period=bdb_booking_period;
        this.deposit_prcntg=deposit_prcntg;
        this.bdb_loc_lat=bdb_loc_lat;
        this.bdb_loc_long=bdb_loc_long;
        this.bdb_has_health_cer=bdb_has_health_cer;
        this.bdb_has_experience_cer=bdb_has_experience_cer;
    }

    public String getBdb_has_experience_cer() {
        return bdb_has_experience_cer;
    }

    public void setBdb_has_experience_cer(String bdb_has_experience_cer) {
        this.bdb_has_experience_cer = bdb_has_experience_cer;
    }

    public String getBdb_has_health_cer() {
        return bdb_has_health_cer;
    }

    public void setBdb_has_health_cer(String bdb_has_health_cer) {
        this.bdb_has_health_cer = bdb_has_health_cer;
    }

    public void setSup_id(String sup_id) {
        this.sup_id = sup_id;
    }

    public void setSup_name(String sup_name) {
        this.sup_name = sup_name;
    }

    public void setLogo_id(String logo_id) {
        this.logo_id = logo_id;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setBdb_booking_period(String bdb_booking_period) {
        this.bdb_booking_period = bdb_booking_period;
    }

    public void setDeposit_prcntg(String deposit_prcntg) {
        this.deposit_prcntg = deposit_prcntg;
    }

    public String getBdb_loc_long() {
        return bdb_loc_long;
    }

    public void setBdb_loc_long(String bdb_loc_long) {
        this.bdb_loc_long = bdb_loc_long;
    }

    public String getBdb_loc_lat() {
        return bdb_loc_lat;
    }

    public void setBdb_loc_lat(String bdb_loc_lat) {
        this.bdb_loc_lat = bdb_loc_lat;
    }

    public String getBdb_booking_period() {
        return bdb_booking_period;
    }

    public String getLogo_id() {
        return logo_id;
    }

    public String getRating() {
        return rating;
    }

    public String getSup_id() {
        return sup_id;
    }

    public String getSup_name() {
        return sup_name;
    }

    public String getDeposit_prcntg() {
        return deposit_prcntg;
    }
}
