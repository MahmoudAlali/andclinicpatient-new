package com.dcoret.beautyclient.DataModel;

import org.json.JSONArray;

public class BestOfferItem {

//    pack_code": 2,
//            "service count": 2,
//            "provider name": "صالون5",
//            "sersup_ids"

        String packages_count,pack_code,service_count,provider_name,old_price,new_price,total_discount;
        JSONArray sersup_ids;

    public BestOfferItem(String pack_code, String service_count, String provider_name, JSONArray sersup_ids) {
        this.pack_code = pack_code;
        this.service_count = service_count;
        this.provider_name = provider_name;
        this.sersup_ids = sersup_ids;
    }


    public BestOfferItem( String pack_code, String service_count, String provider_name, String old_price, String new_price, String total_discount) {
        this.pack_code = pack_code;
        this.service_count = service_count;
        this.provider_name = provider_name;
        this.old_price = old_price;
        this.new_price = new_price;
        this.total_discount = total_discount;
    }

    public BestOfferItem( String pack_code, String service_count, String provider_name, String old_price, String new_price, String total_discount,JSONArray sersup_ids) {
        this.pack_code = pack_code;
        this.service_count = service_count;
        this.provider_name = provider_name;
        this.old_price = old_price;
        this.new_price = new_price;
        this.total_discount = total_discount;
        this.sersup_ids=sersup_ids;
    }

    public String getOld_price() {
        return old_price;
    }

    public void setOld_price(String old_price) {
        this.old_price = old_price;
    }

    public String getNew_price() {
        return new_price;
    }

    public void setNew_price(String new_price) {
        this.new_price = new_price;
    }

    public String getTotal_discount() {
        return total_discount;
    }

    public void setTotal_discount(String total_discount) {
        this.total_discount = total_discount;
    }

    public String getPackages_count() {
        return packages_count;
    }

    public void setPackages_count(String packages_count) {
        this.packages_count = packages_count;
    }

    public String getPack_code() {
        return pack_code;
    }

    public void setPack_code(String pack_code) {
        this.pack_code = pack_code;
    }

    public String getService_count() {
        return service_count;
    }

    public void setService_count(String service_count) {
        this.service_count = service_count;
    }

    public String getProvider_name() {
        return provider_name;
    }

    public void setProvider_name(String provider_name) {
        this.provider_name = provider_name;
    }

    public JSONArray getSersup_ids() {
        return sersup_ids;
    }

    public void setSersup_ids(JSONArray sersup_ids) {
        this.sersup_ids = sersup_ids;
    }
}
