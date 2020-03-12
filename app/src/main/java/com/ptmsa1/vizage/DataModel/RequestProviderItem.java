package com.ptmsa1.vizage.DataModel;

public class RequestProviderItem {
    String sup_id,sup_name,logo_id,rating;

    public RequestProviderItem(String sup_id,String sup_name,String logo_id,String rating)
    {
        this.sup_id=sup_id;
        this.sup_name=sup_name;
        this.logo_id=logo_id;
        this.rating=rating;
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
