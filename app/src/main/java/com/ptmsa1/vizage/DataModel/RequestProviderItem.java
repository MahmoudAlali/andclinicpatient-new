package com.ptmsa1.vizage.DataModel;

public class RequestProviderItem {
    String sup_id,sup_name,logo_id,rating,bdb_booking_period;

    public RequestProviderItem(String sup_id,String sup_name,String logo_id,String rating,String bdb_booking_period)
    {
        this.sup_id=sup_id;
        this.sup_name=sup_name;
        this.logo_id=logo_id;
        this.rating=rating;
        this.bdb_booking_period=bdb_booking_period;
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
}
