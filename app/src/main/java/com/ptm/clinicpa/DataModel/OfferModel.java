package com.ptm.clinicpa.DataModel;



import java.util.ArrayList;

public class OfferModel {

    String bdb_pack_code,
            bdb_doctor_name,
            health_center_name_ar,
            specialization_ar,
            specialization_en,
            supported_gender,
            health_center_name_en,
            health_center_id,
            is_fav_center,
            is_fav_doctor,
            bdb_offer_start,
            bdb_offer_end,
            bdb_sup_name,
            totalRating_to_Sup,
            oldPrice,
            newPrice,
            discount,
            service_count,
            distance,
            longitude,
            minAge,
            maxAge,
            latitude,

            is_fav_sup,

            Num_of_times,
                    bdb_logo_id,

            bdb_offer_status,
            bdb_offer_type,
            bdb_is_effects_on,
            bdb_is_journey_on,
            bdb_is_old_on,
            bdb_is_season_on,
            bdb_offer_place;
    ArrayList<SupIdClass> sersup_ids;

//    public OfferModel(String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_end, String num_of_times, String oldPrice, String newPrice, String discount, ArrayList<SupIdClass> sersup_ids) {
//        this.bdb_pack_code = bdb_pack_code;
//        this.bdb_sup_name = bdb_sup_name;
//        this.totalRating_to_Sup = totalRating_to_Sup;
//        this.service_count = service_count;
//        this.is_fav_sup = is_fav_sup;
//        this.bdb_offer_end = bdb_offer_end;
//        Num_of_times = num_of_times;
//        this.oldPrice = oldPrice;
//        this.newPrice = newPrice;
//        this.discount = discount;
//        this.sersup_ids = sersup_ids;
//    }

//    public OfferModel(String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_end, String num_of_times, String oldPrice, String newPrice, String discount, String bdb_offer_status, ArrayList<SupIdClass> sersup_ids) {
//        this.bdb_pack_code = bdb_pack_code;
//        this.bdb_sup_name = bdb_sup_name;
//        this.totalRating_to_Sup = totalRating_to_Sup;
//        this.service_count = service_count;
//        this.is_fav_sup = is_fav_sup;
//        this.bdb_offer_end = bdb_offer_end;
//        Num_of_times = num_of_times;
//        this.oldPrice = oldPrice;
//        this.newPrice = newPrice;
//        this.discount = discount;
//        this.bdb_offer_status = bdb_offer_status;
//        this.sersup_ids = sersup_ids;
//    }

//    public OfferModel(String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_start, String bdb_offer_end, String num_of_times, String oldPrice, String newPrice, String discount, String bdb_offer_status, ArrayList<SupIdClass> sersup_ids) {
//        this.bdb_pack_code = bdb_pack_code;
//        this.bdb_sup_name = bdb_sup_name;
//        this.totalRating_to_Sup = totalRating_to_Sup;
//        this.service_count = service_count;
//        this.is_fav_sup = is_fav_sup;
//        this.bdb_offer_start = bdb_offer_start;
//        this.bdb_offer_end = bdb_offer_end;
//        Num_of_times = num_of_times;
//        this.oldPrice = oldPrice;
//        this.newPrice = newPrice;
//        this.discount = discount;
//        this.bdb_offer_status = bdb_offer_status;
//        this.sersup_ids = sersup_ids;
//    }

//    public OfferModel(String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_start, String bdb_offer_end, String num_of_times, String oldPrice, String newPrice, String discount, String bdb_offer_status, String bdb_is_old_on,String bdb_offer_place, ArrayList<SupIdClass> sersup_ids) {
//        this.bdb_pack_code = bdb_pack_code;
//        this.bdb_sup_name = bdb_sup_name;
//        this.totalRating_to_Sup = totalRating_to_Sup;
//        this.service_count = service_count;
//        this.is_fav_sup = is_fav_sup;
//        this.bdb_offer_start = bdb_offer_start;
//        this.bdb_offer_end = bdb_offer_end;
//        this.Num_of_times = num_of_times;
//        this.oldPrice = oldPrice;
//        this.newPrice = newPrice;
//        this.discount = discount;
//        this.bdb_offer_status = bdb_offer_status;
//        this.bdb_is_old_on = bdb_is_old_on;
//        this.bdb_offer_place = bdb_offer_place;
//        this.sersup_ids = sersup_ids;
//    }


    public OfferModel(String bdb_pack_code, String bdb_sup_name, String totalRating_to_Sup, String service_count, String is_fav_sup, String bdb_offer_start, String bdb_offer_end, String num_of_times, String oldPrice, String newPrice, String discount, String bdb_offer_status, String bdb_offer_type, String bdb_is_effects_on, String bdb_is_journey_on, String bdb_is_old_on, String bdb_is_season_on,String bdb_offer_place, ArrayList<SupIdClass> sersup_ids) {
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
        this.bdb_is_effects_on = bdb_is_effects_on;
        this.bdb_is_journey_on = bdb_is_journey_on;
        this.bdb_is_old_on = bdb_is_old_on;
        this.bdb_is_season_on = bdb_is_season_on;
        this.sersup_ids = sersup_ids;
        this.bdb_offer_place= bdb_offer_place;
    }
    public OfferModel(String bdb_pack_code, String health_center_id,String health_center_name_ar,String health_center_name_en,String bdb_doctor_name,
                      String totalRating_to_Sup, String service_count,
                      String is_fav_doctor,String is_fav_center, String bdb_offer_start, String bdb_offer_end, String num_of_times, String oldPrice, String newPrice, String discount,String distance,String latitude,String longitude, String bdb_offer_status, String bdb_offer_type, String bdb_is_journey_on, String bdb_is_old_on,String bdb_offer_place,String bdb_logo_id, ArrayList<SupIdClass> sersup_ids,String minAge,String maxAge,String supported_gender,String specialization_ar,String specialization_en) {
        this.bdb_pack_code = bdb_pack_code;
        this.health_center_id = health_center_id;
        this.health_center_name_ar = health_center_name_ar;
        this.health_center_name_en = health_center_name_en;
        this.bdb_doctor_name = bdb_doctor_name;
        this.is_fav_center = is_fav_center;
        this.is_fav_doctor = is_fav_doctor;
        this.bdb_logo_id = bdb_logo_id;
        this.totalRating_to_Sup = totalRating_to_Sup;
        this.service_count = service_count;
        this.distance = distance;
        this.bdb_offer_start = bdb_offer_start;
        this.bdb_offer_end = bdb_offer_end;
        Num_of_times = num_of_times;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.discount = discount;
        this.bdb_offer_status = bdb_offer_status;
        this.bdb_offer_type = bdb_offer_type;
        this.latitude = latitude;
        this.bdb_is_journey_on = bdb_is_journey_on;
        this.bdb_is_old_on = bdb_is_old_on;
        this.longitude = longitude;
        this.sersup_ids = sersup_ids;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.bdb_offer_place= bdb_offer_place;
        this.specialization_ar= specialization_ar;
        this.specialization_en= specialization_en;
        this.supported_gender= supported_gender;
    }
    public OfferModel(String bdb_pack_code,String bdb_doctor_name,String bdb_offer_start, String bdb_offer_end,  String bdb_offer_type, String bdb_is_journey_on, String bdb_is_old_on,String bdb_offer_place, ArrayList<SupIdClass> sersup_ids,String minAge,String maxAge,String supported_gender,String specialization_ar,String specialization_en) {
        this.bdb_pack_code = bdb_pack_code;
        this.health_center_id = health_center_id;
        this.health_center_name_ar = health_center_name_ar;
        this.health_center_name_en = health_center_name_en;
        this.bdb_doctor_name = bdb_doctor_name;
        this.is_fav_center = is_fav_center;
        this.is_fav_doctor = is_fav_doctor;
        this.bdb_logo_id = bdb_logo_id;
        this.totalRating_to_Sup = totalRating_to_Sup;
        this.service_count = service_count;
        this.distance = distance;
        this.bdb_offer_start = bdb_offer_start;
        this.bdb_offer_end = bdb_offer_end;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        this.discount = discount;
        this.bdb_offer_status = bdb_offer_status;
        this.bdb_offer_type = bdb_offer_type;
        this.latitude = latitude;
        this.bdb_is_journey_on = bdb_is_journey_on;
        this.bdb_is_old_on = bdb_is_old_on;
        this.longitude = longitude;
        this.sersup_ids = sersup_ids;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.bdb_offer_place= bdb_offer_place;
        this.specialization_ar= specialization_ar;
        this.specialization_en= specialization_en;
        this.supported_gender= supported_gender;
    }

    public String getBdb_offer_place() {
        return bdb_offer_place;
    }

    public void setBdb_offer_place(String bdb_offer_place) {
        this.bdb_offer_place = bdb_offer_place;
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

    public String getBdb_is_season_on() {
        return bdb_is_season_on;
    }

    public void setBdb_is_season_on(String bdb_is_season_on) {
        this.bdb_is_season_on = bdb_is_season_on;
    }

    public String getBdb_offer_type() {
        return bdb_offer_type;
    }

    public void setBdb_offer_type(String bdb_offer_type) {
        this.bdb_offer_type = bdb_offer_type;
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

    public String getNum_of_times() {
        return Num_of_times;
    }

    public void setNum_of_times(String num_of_times) {
        Num_of_times = num_of_times;
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

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public ArrayList<SupIdClass> getSersup_ids() {
        return sersup_ids;
    }

    public void setSersup_ids(ArrayList<SupIdClass> sersup_ids) {
        this.sersup_ids = sersup_ids;
    }

    public String getHealth_center_id() {
        return health_center_id;
    }

    public String getIs_fav_center() {
        return is_fav_center;
    }

    public String getIs_fav_doctor() {
        return is_fav_doctor;
    }

    public String getBdb_logo_id() {
        return bdb_logo_id;
    }

    public String getBdb_doctor_name() {
        return bdb_doctor_name;
    }

    public String getDistance() {
        return distance;
    }

    public String getHealth_center_name_ar() {
        return health_center_name_ar;
    }

    public String getHealth_center_name_en() {
        return health_center_name_en;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getSpecialization_ar() {
        return specialization_ar;
    }

    public String getSpecialization_en() {
        return specialization_en;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public String getMinAge() {
        return minAge;
    }

    public String getSupported_gender() {
        return supported_gender;
    }

    public static class SupIdClass{
        String bdb_ser_sup_id,bdb_name,bdb_name_ar,bdb_ser_id;


        public SupIdClass(String bdb_ser_sup_id, String bdb_name, String bdb_ser_id) {
            this.bdb_ser_sup_id = bdb_ser_sup_id;
            this.bdb_name = bdb_name;
//            this.bdb_name_ar = bdb_name_ar;
            this.bdb_ser_id = bdb_ser_id;
        }
        String bdb_ext_pack_code,bdb_time;
        public SupIdClass(String bdb_ser_sup_id, String bdb_name,String bdb_name_ar, String bdb_ser_id,String bdb_ext_pack_code,String bdb_time) {
            this.bdb_ser_sup_id = bdb_ser_sup_id;
            this.bdb_name = bdb_name;
            this.bdb_name_ar = bdb_name_ar;
            this.bdb_ser_id = bdb_ser_id;
            this.bdb_time = bdb_time;
            this.bdb_ext_pack_code = bdb_ext_pack_code;
        }

        public String getBdb_ser_sup_id() {
            return bdb_ser_sup_id;
        }

        public void setBdb_ser_sup_id(String bdb_ser_sup_id) {
            this.bdb_ser_sup_id = bdb_ser_sup_id;
        }

        public String getBdb_ext_pack_code() {
            return bdb_ext_pack_code;
        }

        public String getBdb_time() {
            return bdb_time;
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
}
