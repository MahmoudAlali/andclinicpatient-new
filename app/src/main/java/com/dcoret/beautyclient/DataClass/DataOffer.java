package com.dcoret.beautyclient.DataClass;

import org.json.JSONArray;

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



    String
            bdb_pack_code
            ,bdb_sup_name
            ,totalRating_to_Sup
            ,service_count
            ,is_fav_sup
            ,bdb_offer_end
            ,Num_of_times
            ,distance
            ,longitude
            ,latitude,oldPrice,newPrice,discount;
    JSONArray pack_data;


    public DataOffer(String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_end, String num_of_times, String distance, String longitude, String latitude, String oldPrice, String newPrice, String discount) {
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
    }

    public DataOffer(String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_end, String num_of_times, String distance, String longitude, String latitude, String oldPrice, String newPrice, String discount, JSONArray pack_data) {
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
        this.pack_data = pack_data;
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
}
