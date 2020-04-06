package com.ptmsa1.vizage.DataModel;

import org.json.JSONArray;

import java.util.ArrayList;

public class DataOffer {
//
//    "bdb_pack_code": 1,
//            "bdb_sup_name": "صالون",
//            "totalRating_to_Sup": 2,
//            "service count": 1,
//            "is_fav_sup": 0,
//            "bdb_offer_end": "2019-07-24T21:00:00.000000Z",
//            "Num_of_times": 0,
//            "oldPrice": 66,
//            "newPrice": 33,
//            "discount": 50,
//            "pack_data": [
//    {
//        "bdb_ser_sup_id": 23,
//            "bdb_ser_name": "soiree makeup",
//            "bdb_ser_id": 1
//    }
//                ],
//                        "distance": 45.95988189275612,
//                        "longitude": 39.1693746,
//                        "latitude": 21.5347819



//    String
//            bdb_pack_code
//            ,bdb_sup_name
//            ,totalRating_to_Sup
//            ,service_count
//            ,is_fav_sup
//            ,bdb_offer_start
//            ,bdb_offer_end
//            ,Num_of_times
//            ,distance
//            ,longitude
//            ,latitude,oldPrice,newPrice,discount,bdb_offer_type;
    JSONArray pack_data;

    String bdb_pack_code,bdb_sup_name,totalRating_to_Sup,service_count,is_fav_sup,bdb_offer_start,bdb_offer_end,Num_of_times,oldPrice,newPrice,discount,bdb_offer_status,bdb_offer_type
                        ,longitude
            ,latitude,distance

    ,bdb_is_effects_on,bdb_is_journey_on,bdb_is_old_on,bdb_offer_place;
            ;
    ArrayList<SupIdClass> sersup_ids;


//    public DataOffer(String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_end, String num_of_times, String distance, String longitude, String latitude, String oldPrice, String newPrice, String discount) {
//        this.bdb_pack_code = bdb_pack_code;
//        this.bdb_sup_name = bdb_sup_name;
//        this.totalRating_to_Sup = totalRating_to_Sup;
//        this.service_count = service_count;
//        this.is_fav_sup = is_fav_sup;
//        this.bdb_offer_end = bdb_offer_end;
//        Num_of_times = num_of_times;
//        this.distance = distance;
//        this.longitude = longitude;
//        this.latitude = latitude;
//        this.oldPrice = oldPrice;
//        this.newPrice = newPrice;
//        this.discount = discount;
//    }
//
//    public DataOffer(String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_end, String num_of_times, String distance, String longitude, String latitude, String oldPrice, String newPrice, String discount, JSONArray pack_data) {
//        this.bdb_pack_code = bdb_pack_code;
//        this.bdb_sup_name = bdb_sup_name;
//        this.totalRating_to_Sup = totalRating_to_Sup;
//        this.service_count = service_count;
//        this.is_fav_sup = is_fav_sup;
//        this.bdb_offer_end = bdb_offer_end;
//        Num_of_times = num_of_times;
//        this.distance = distance;
//        this.longitude = longitude;
//        this.latitude = latitude;
//        this.oldPrice = oldPrice;
//        this.newPrice = newPrice;
//        this.discount = discount;
//        this.pack_data = pack_data;
//    }

    public DataOffer(String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_end, String num_of_times, String distance, String longitude, String latitude, String oldPrice, String newPrice, String discount, String bdb_offer_type) {
        this.bdb_pack_code = bdb_pack_code;
        this.bdb_sup_name = bdb_sup_name;
        this.totalRating_to_Sup = totalRating_to_Sup;
        this.service_count = service_count;
        this.is_fav_sup = is_fav_sup;
        this.bdb_offer_end = bdb_offer_end;
        Num_of_times = num_of_times;
        this.distance = distance;
        this.longitude = longitude;
        this.latitude = latitude;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.discount = discount;
        this.bdb_offer_type = bdb_offer_type;
//        this.pack_data = pack_data;
    }

    public DataOffer( String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_start, String bdb_offer_end, String num_of_times, String oldPrice, String newPrice, String discount, String bdb_offer_status, String bdb_offer_type, String longitude, String latitude, String distance,
                      String bdb_is_journey_on,String bdb_is_old_on,String bdb_offer_place,String bdb_is_effects_on, ArrayList<SupIdClass> sersup_ids) {
        this.pack_data = pack_data;
        this.bdb_pack_code = bdb_pack_code;
        this.bdb_sup_name = bdb_sup_name;
        this.totalRating_to_Sup = totalRating_to_Sup;
        this.service_count = service_count;
        this.is_fav_sup = is_fav_sup;
        this.bdb_offer_start = bdb_offer_start;
        this.bdb_offer_end = bdb_offer_end;
        Num_of_times = num_of_times;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.discount = discount;
        this.bdb_offer_status = bdb_offer_status;
        this.bdb_offer_type = bdb_offer_type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
        this.sersup_ids = sersup_ids;
        this.bdb_is_journey_on = bdb_is_journey_on;
        this.bdb_is_old_on = bdb_is_old_on;
        this.bdb_offer_place = bdb_offer_place;
        this.bdb_is_effects_on= bdb_is_effects_on;
    }
    String bdb_booking_period;
    String deposit_ratio;
    public DataOffer( String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_start, String bdb_offer_end, String num_of_times, String oldPrice, String newPrice, String discount, String bdb_offer_status, String bdb_offer_type, String longitude, String latitude, String distance,
                      String bdb_is_journey_on,String bdb_is_old_on,String bdb_offer_place,String bdb_is_effects_on,String bdb_booking_period, ArrayList<SupIdClass> sersup_ids,String deposit_ratio) {
        this.pack_data = pack_data;
        this.bdb_pack_code = bdb_pack_code;
        this.bdb_sup_name = bdb_sup_name;
        this.totalRating_to_Sup = totalRating_to_Sup;
        this.service_count = service_count;
        this.is_fav_sup = is_fav_sup;
        this.bdb_offer_start = bdb_offer_start;
        this.bdb_offer_end = bdb_offer_end;
        Num_of_times = num_of_times;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.discount = discount;
        this.bdb_offer_status = bdb_offer_status;
        this.bdb_offer_type = bdb_offer_type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance = distance;
        this.sersup_ids = sersup_ids;
        this.bdb_is_journey_on = bdb_is_journey_on;
        this.bdb_is_old_on = bdb_is_old_on;
        this.bdb_offer_place = bdb_offer_place;
        this.bdb_is_effects_on= bdb_is_effects_on;
        this.bdb_booking_period= bdb_booking_period;
        this.deposit_ratio=deposit_ratio;
    }

    public String getBdb_booking_period() {
        return bdb_booking_period;
    }

    public void setBdb_booking_period(String bdb_booking_period) {
        this.bdb_booking_period = bdb_booking_period;
    }

    public String getBdb_is_effects_on() {
        return bdb_is_effects_on;
    }

    public void setBdb_is_effects_on(String bdb_is_effects_on) {
        this.bdb_is_effects_on = bdb_is_effects_on;
    }

    public String getBdb_is_journey_on() {
        return bdb_is_journey_on;
    }

    public void setBdb_is_journey_on(String bdb_is_journey_on) {
        this.bdb_is_journey_on = bdb_is_journey_on;
    }

    public String getBdb_is_old_on() {
        return bdb_is_old_on;
    }

    public void setBdb_is_old_on(String bdb_is_old_on) {
        this.bdb_is_old_on = bdb_is_old_on;
    }

    public String getBdb_offer_place() {
        return bdb_offer_place;
    }

    public void setBdb_offer_place(String bdb_offer_place) {
        this.bdb_offer_place = bdb_offer_place;
    }

    public String getBdb_offer_type() {
        return bdb_offer_type;
    }

    public void setBdb_offer_type(String bdb_offer_type) {
        this.bdb_offer_type = bdb_offer_type;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public JSONArray getPack_data() {
        return pack_data;
    }

    public void setPack_data(JSONArray pack_data) {
        this.pack_data = pack_data;
    }

    public DataOffer(String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_end, String num_of_times, String distance, String longitude, String latitude) {
        this.bdb_pack_code = bdb_pack_code;
        this.bdb_sup_name = bdb_sup_name;
        this.totalRating_to_Sup = totalRating_to_Sup;
        this.service_count = service_count;
        this.is_fav_sup = is_fav_sup;
        this.bdb_offer_end = bdb_offer_end;
        Num_of_times = num_of_times;
        this.distance = distance;
        this.longitude = longitude;
        this.latitude = latitude;
    }
    public DataOffer(String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_start, String bdb_offer_end, String num_of_times, String oldPrice, String newPrice, String discount, String bdb_offer_status, String bdb_offer_type, ArrayList<SupIdClass> sersup_ids) {
        this.bdb_pack_code = bdb_pack_code;
        this.bdb_sup_name = bdb_sup_name;
        this.totalRating_to_Sup = totalRating_to_Sup;
        this.service_count = service_count;
        this.is_fav_sup = is_fav_sup;
        this.bdb_offer_start = bdb_offer_start;
        this.bdb_offer_end = bdb_offer_end;
        this.Num_of_times = num_of_times;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.discount = discount;
        this.bdb_offer_status = bdb_offer_status;
        this.bdb_offer_type = bdb_offer_type;
        this.sersup_ids = sersup_ids;
    }


    public String getBdb_offer_start() {
        return bdb_offer_start;
    }

    public void setBdb_offer_start(String bdb_offer_start) {
        this.bdb_offer_start = bdb_offer_start;
    }

    public String getBdb_offer_status() {
        return bdb_offer_status;
    }

    public void setBdb_offer_status(String bdb_offer_status) {
        this.bdb_offer_status = bdb_offer_status;
    }

    public ArrayList<SupIdClass> getSersup_ids() {
        return sersup_ids;
    }

    public void setSersup_ids(ArrayList<SupIdClass> sersup_ids) {
        this.sersup_ids = sersup_ids;
    }

    public String getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(String oldPrice) {
        this.oldPrice = oldPrice;
    }

    public String getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(String newPrice) {
        this.newPrice = newPrice;
    }

    public String getBdb_pack_code() {
        return bdb_pack_code;
    }

    public void setBdb_pack_code(String bdb_pack_code) {
        this.bdb_pack_code = bdb_pack_code;
    }

    public String getBdb_sup_name() {
        return bdb_sup_name;
    }

    public void setBdb_sup_name(String bdb_sup_name) {
        this.bdb_sup_name = bdb_sup_name;
    }

    public String getTotalRating_to_Sup() {
        return totalRating_to_Sup;
    }

    public void setTotalRating_to_Sup(String totalRating_to_Sup) {
        this.totalRating_to_Sup = totalRating_to_Sup;
    }

    public String getService_count() {
        return service_count;
    }

    public void setService_count(String service_count) {
        this.service_count = service_count;
    }

    public String getIs_fav_sup() {
        return is_fav_sup;
    }

    public void setIs_fav_sup(String is_fav_sup) {
        this.is_fav_sup = is_fav_sup;
    }

    public String getBdb_offer_end() {
        return bdb_offer_end;
    }

    public void setBdb_offer_end(String bdb_offer_end) {
        this.bdb_offer_end = bdb_offer_end;
    }

    public String getNum_of_times() {
        return Num_of_times;
    }

    public void setNum_of_times(String num_of_times) {
        Num_of_times = num_of_times;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }



    public static class SupIdClass{
        String bdb_ser_sup_id,bdb_name,bdb_name_ar,bdb_ser_id;


        public SupIdClass(String bdb_ser_sup_id, String bdb_name, String bdb_ser_id) {
            this.bdb_ser_sup_id = bdb_ser_sup_id;
            this.bdb_name = bdb_name;
//            this.bdb_name_ar = bdb_name_ar;
            this.bdb_ser_id = bdb_ser_id;
        }

        public String getBdb_ser_sup_id() {
            return bdb_ser_sup_id;
        }

        public void setBdb_ser_sup_id(String bdb_ser_sup_id) {
            this.bdb_ser_sup_id = bdb_ser_sup_id;
        }

        public String getBdb_name() {
            return bdb_name;
        }

        public void setBdb_name(String bdb_name) {
            this.bdb_name = bdb_name;
        }

        public String getBdb_name_ar() {
            return bdb_name_ar;
        }

        public void setBdb_name_ar(String bdb_name_ar) {
            this.bdb_name_ar = bdb_name_ar;
        }

        public String getBdb_ser_id() {
            return bdb_ser_id;
        }

        public void setBdb_ser_id(String bdb_ser_id) {
            this.bdb_ser_id = bdb_ser_id;
        }
    }

    public String getDeposit_ratio() {
        return deposit_ratio;
    }
}
