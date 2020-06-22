package com.ptm.clinicpa.DataModel;

import org.json.JSONArray;

public class BestOfferItem {

//    pack_code": 2,
//            "service count": 2,
//            "provider name": "صالون5",
//            "sersup_ids"

        String packages_count,pack_code,service_count,provider_name,old_price,new_price,total_discount,provider_logo_id,offer_type,deposit_prcntg,health_center_ar,health_center_en;
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

    public BestOfferItem( String pack_code, String service_count, String provider_name, String old_price, String new_price, String total_discount,JSONArray sersup_ids,String provider_logo_id,String offer_type) {
        this.pack_code = pack_code;
        this.service_count = service_count;
        this.provider_name = provider_name;
        this.old_price = old_price;
        this.new_price = new_price;
        this.total_discount = total_discount;
        this.sersup_ids=sersup_ids;
        this.provider_logo_id=provider_logo_id;
        this.offer_type=offer_type;
    }
    String provider_id;
    public BestOfferItem( String pack_code,String provider_id, String service_count, String provider_name, String old_price, String new_price, String total_discount,JSONArray sersup_ids,String provider_logo_id,String offer_type) {
        this.pack_code = pack_code;
        this.service_count = service_count;
        this.provider_name = provider_name;
        this.old_price = old_price;
        this.new_price = new_price;
        this.total_discount = total_discount;
        this.sersup_ids=sersup_ids;
        this.provider_logo_id=provider_logo_id;
        this.offer_type=offer_type;
        this.provider_id=provider_id;
    }
    String bdb_booking_period,start_date,end_date;
    String bdb_has_experience_cer,bdb_has_health_cer,speciality_ar,speciality_en;
    public BestOfferItem( String pack_code,String provider_id, String service_count, String provider_name, String old_price, String new_price, String total_discount,JSONArray sersup_ids,String provider_logo_id,String offer_type,String bdb_booking_period,String start_date,String end_date,String deposit_prcntg,String bdb_has_experience_cer,String bdb_has_health_cer ,String health_center_ar,String health_center_en,String speciality_ar,String speciality_en) {
        this.pack_code = pack_code;
        this.service_count = service_count;
        this.provider_name = provider_name;
        this.old_price = old_price;
        this.new_price = new_price;
        this.total_discount = total_discount;
        this.sersup_ids=sersup_ids;
        this.provider_logo_id=provider_logo_id;
        this.offer_type=offer_type;
        this.provider_id=provider_id;
        this.bdb_booking_period=bdb_booking_period;
        this.start_date=start_date;
        this.end_date=end_date;
        this.deposit_prcntg=deposit_prcntg;
        this.bdb_has_health_cer=bdb_has_health_cer;
        this.bdb_has_experience_cer=bdb_has_experience_cer;
        this.health_center_ar=health_center_ar;
        this.health_center_en=health_center_en;
        this.speciality_ar=speciality_ar;
        this.speciality_en=speciality_en;
    }

    public String getBdb_has_experience_cer() {
        return bdb_has_experience_cer;
    }

    public String getBdb_has_health_cer() {
        return bdb_has_health_cer;
    }

    public void setProvider_logo_id(String provider_logo_id) {
        this.provider_logo_id = provider_logo_id;
    }

    public void setOffer_type(String offer_type) {
        this.offer_type = offer_type;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }

    public void setBdb_booking_period(String bdb_booking_period) {
        this.bdb_booking_period = bdb_booking_period;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getBdb_booking_period() {
        return bdb_booking_period;
    }

    public String getProvider_id() {
        return provider_id;
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

    public String getProvider_logo_id() {
        return provider_logo_id;
    }

    public String getOffer_type() {
        return offer_type;
    }

    public String getDeposit_prcntg() {
        return deposit_prcntg;
    }

    public String getHealth_center_ar() {
        return health_center_ar;
    }

    public String getHealth_center_en() {
        return health_center_en;
    }

    public String getSpeciality_ar() {
        return speciality_ar;
    }

    public String getSpeciality_en() {
        return speciality_en;
    }
}
