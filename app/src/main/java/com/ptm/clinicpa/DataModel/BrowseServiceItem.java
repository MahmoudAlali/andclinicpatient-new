package com.ptm.clinicpa.DataModel;

import com.ptm.clinicpa.Fragments.PlaceServiceFragment;

public class BrowseServiceItem {

         private     String bdb_ser_sup_id,
                    bdb_sup_name,
                    bdb_sup_rating,
                    bdb_emp_rating,
                    totalRating,
                    bdb_ser_home,
                    bdb_ser_hall,
                    bdb_ser_salon,
                    bdb_hotel,
                    bdb_emp_id,
                    bdb_ser_home_price,
                    bdb_ser_hall_price,
                    bdb_ser_salon_price,
                    bdb_hotel_price,
                    distance,
                    longitude,
                    latitude,
                 is_fav_sup,newFav,
                    bdb_booking_period,
                    ser_id,
                    sup_id,
                 logoId,image_1,image_2,catId,bdb_client_deposit_ratio,
    bdb_time,
                bdb_isbride_ser;


    public BrowseServiceItem(String bdb_ser_sup_id, String bdb_sup_name, String bdb_sup_rating, String bdb_emp_rating, String totalRating, String bdb_ser_home, String bdb_ser_hall, String bdb_ser_salon, String bdb_hotel, String bdb_ser_home_price, String bdb_ser_hall_price, String bdb_ser_salon_price, String bdb_hotel_price, String distance, String longitude, String latitude, String is_fav_sup) {
        this.bdb_ser_sup_id = bdb_ser_sup_id;
        this.bdb_sup_name = bdb_sup_name;
        this.bdb_sup_rating = bdb_sup_rating;
        this.bdb_emp_rating = bdb_emp_rating;
        this.totalRating = totalRating;
        this.bdb_ser_home = bdb_ser_home;
        this.bdb_ser_hall = bdb_ser_hall;
        this.bdb_ser_salon = bdb_ser_salon;
        this.bdb_hotel = bdb_hotel;
        this.bdb_ser_home_price = bdb_ser_home_price;
        this.bdb_ser_hall_price = bdb_ser_hall_price;
        this.bdb_ser_salon_price = bdb_ser_salon_price;
        this.bdb_hotel_price = bdb_hotel_price;
        this.distance = distance;
        this.longitude = longitude;
        this.latitude = latitude;
        this.is_fav_sup = is_fav_sup;
    }

    public BrowseServiceItem(String bdb_ser_sup_id, String bdb_sup_name, String bdb_sup_rating, String bdb_emp_rating, String totalRating, String bdb_ser_home, String bdb_ser_hall, String bdb_ser_salon, String bdb_hotel, String bdb_emp_id, String bdb_ser_home_price, String bdb_ser_hall_price, String bdb_ser_salon_price, String bdb_hotel_price, String distance, String longitude, String latitude, String is_fav_sup, String bdb_booking_period) {
        this.bdb_ser_sup_id = bdb_ser_sup_id;
        this.bdb_sup_name = bdb_sup_name;
        this.bdb_sup_rating = bdb_sup_rating;
        this.bdb_emp_rating = bdb_emp_rating;
        this.totalRating = totalRating;
        this.bdb_ser_home = bdb_ser_home;
        this.bdb_ser_hall = bdb_ser_hall;
        this.bdb_ser_salon = bdb_ser_salon;
        this.bdb_hotel = bdb_hotel;
        this.bdb_emp_id = bdb_emp_id;
        this.bdb_ser_home_price = bdb_ser_home_price;
        this.bdb_ser_hall_price = bdb_ser_hall_price;
        this.bdb_ser_salon_price = bdb_ser_salon_price;
        this.bdb_hotel_price = bdb_hotel_price;
        this.distance = distance;
        this.longitude = longitude;
        this.latitude = latitude;
        this.is_fav_sup = is_fav_sup;
        this.bdb_booking_period = bdb_booking_period;
    }


    public String getBdb_emp_id() {
        return bdb_emp_id;
    }

    public void setBdb_emp_id(String bdb_emp_id) {
        this.bdb_emp_id = bdb_emp_id;
    }

    public BrowseServiceItem(String bdb_ser_sup_id, String bdb_sup_name, String bdb_sup_rating, String bdb_emp_rating, String totalRating, String bdb_ser_home, String bdb_ser_hall, String bdb_ser_salon, String bdb_hotel, String bdb_ser_home_price, String bdb_ser_hall_price, String bdb_ser_salon_price, String bdb_hotel_price, String distance, String longitude, String latitude,
                             String is_fav_sup, String bdb_booking_period,String ser_id,String sup_id,String bdb_time,String bdb_isbride_ser,String bdb_logo_id,String bdb_client_deposit_ratio) {
        this.bdb_ser_sup_id = bdb_ser_sup_id;
        this.bdb_sup_name = bdb_sup_name;
        this.bdb_sup_rating = bdb_sup_rating;
        this.bdb_emp_rating = bdb_emp_rating;
        this.totalRating = totalRating;
        this.bdb_ser_home = bdb_ser_home;
        this.bdb_ser_hall = bdb_ser_hall;
        this.bdb_ser_salon = bdb_ser_salon;
        this.bdb_hotel = bdb_hotel;
        this.bdb_ser_home_price = bdb_ser_home_price;
        this.bdb_ser_hall_price = bdb_ser_hall_price;
        this.bdb_ser_salon_price = bdb_ser_salon_price;
        this.bdb_hotel_price = bdb_hotel_price;
        this.distance = distance;
        this.longitude = longitude;
        this.latitude = latitude;
        this.is_fav_sup = is_fav_sup;
        this.bdb_booking_period = bdb_booking_period;
        this.ser_id= ser_id;
        this.sup_id= sup_id;
        this.bdb_time= bdb_time;
        this.bdb_isbride_ser= bdb_isbride_ser;
        this.logoId= bdb_logo_id;
        this.newFav=is_fav_sup;
        this.bdb_client_deposit_ratio=bdb_client_deposit_ratio;
    }
    String bdb_name_ar,bdb_name;
    public BrowseServiceItem(String bdb_ser_sup_id, String bdb_sup_name, String bdb_sup_rating, String bdb_emp_rating, String totalRating, String bdb_ser_home, String bdb_ser_hall, String bdb_ser_salon, String bdb_hotel, String bdb_ser_home_price, String bdb_ser_hall_price, String bdb_ser_salon_price, String bdb_hotel_price, String distance, String longitude, String latitude,
                             String is_fav_sup, String bdb_booking_period,String ser_id,String sup_id,String bdb_time,String bdb_isbride_ser,String bdb_logo_id,String bdb_name_ar,String bdb_name,String image_1,String image_2,String catId) {
        this.bdb_ser_sup_id = bdb_ser_sup_id;
        this.bdb_sup_name = bdb_sup_name;
        this.bdb_sup_rating = bdb_sup_rating;
        this.bdb_emp_rating = bdb_emp_rating;
        this.totalRating = totalRating;
        this.bdb_ser_home = bdb_ser_home;
        this.bdb_ser_hall = bdb_ser_hall;
        this.bdb_ser_salon = bdb_ser_salon;
        this.bdb_hotel = bdb_hotel;
        this.bdb_ser_home_price = bdb_ser_home_price;
        this.bdb_ser_hall_price = bdb_ser_hall_price;
        this.bdb_ser_salon_price = bdb_ser_salon_price;
        this.bdb_hotel_price = bdb_hotel_price;
        this.distance = distance;
        this.longitude = longitude;
        this.latitude = latitude;
        this.is_fav_sup = is_fav_sup;
        this.bdb_booking_period = bdb_booking_period;
        this.ser_id= ser_id;
        this.sup_id= sup_id;
        this.bdb_time= bdb_time;
        this.bdb_isbride_ser= bdb_isbride_ser;
        this.logoId= bdb_logo_id;
        this.newFav=is_fav_sup;
        this.bdb_name_ar=bdb_name_ar;
        this.bdb_name=bdb_name;
        this.image_1=image_1;
        this.image_2=image_2;
        this.catId=catId;
    }

    public String getBdb_name() {
        return bdb_name;
    }

    public String getBdb_name_ar() {
        return bdb_name_ar;
    }

    public String getBdb_isbride_ser() {
        return bdb_isbride_ser;
    }

    public void setBdb_isbride_ser(String bdb_isbride_ser) {
        this.bdb_isbride_ser = bdb_isbride_ser;
    }

    public String getBdb_time() {
        return bdb_time;
    }

    public void setBdb_time(String bdb_time) {
        this.bdb_time = bdb_time;
    }

    public String getSup_id() {
        return sup_id;
    }

    public void setSup_id(String sup_id) {
        this.sup_id = sup_id;
    }

    public String getSer_id() {
        return ser_id;
    }

    public void setSer_id(String ser_id) {
        this.ser_id = ser_id;
    }

    public String getBdb_booking_period() {
        return bdb_booking_period;
    }

    public void setBdb_booking_period(String bdb_booking_period) {
        this.bdb_booking_period = bdb_booking_period;
    }

    public String getBdb_ser_sup_id() {
        return bdb_ser_sup_id;
    }

    public void setBdb_ser_sup_id(String bdb_ser_sup_id) {
        this.bdb_ser_sup_id = bdb_ser_sup_id;
    }

    public String getBdb_sup_name() {
        return bdb_sup_name;
    }

    public void setBdb_sup_name(String bdb_sup_name) {
        this.bdb_sup_name = bdb_sup_name;
    }

    public String getBdb_sup_rating() {
        return bdb_sup_rating;
    }

    public void setBdb_sup_rating(String bdb_sup_rating) {
        this.bdb_sup_rating = bdb_sup_rating;
    }

    public String getBdb_emp_rating() {
        return bdb_emp_rating;
    }

    public void setBdb_emp_rating(String bdb_emp_rating) {
        this.bdb_emp_rating = bdb_emp_rating;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public String getBdb_ser_home() {
        return bdb_ser_home;
    }

    public void setBdb_ser_home(String bdb_ser_home) {
        this.bdb_ser_home = bdb_ser_home;
    }

    public String getBdb_ser_hall() {
        return bdb_ser_hall;
    }

    public void setBdb_ser_hall(String bdb_ser_hall) {
        this.bdb_ser_hall = bdb_ser_hall;
    }

    public String getBdb_ser_salon() {
        return bdb_ser_salon;
    }

    public void setBdb_ser_salon(String bdb_ser_salon) {
        this.bdb_ser_salon = bdb_ser_salon;
    }

    public String getBdb_hotel() {
        return bdb_hotel;
    }

    public void setBdb_hotel(String bdb_hotel) {
        this.bdb_hotel = bdb_hotel;
    }

    public String getBdb_ser_home_price() {
        return bdb_ser_home_price;
    }

    public void setBdb_ser_home_price(String bdb_ser_home_price) {
        this.bdb_ser_home_price = bdb_ser_home_price;
    }

    public String getBdb_ser_hall_price() {
        return bdb_ser_hall_price;
    }

    public void setBdb_ser_hall_price(String bdb_ser_hall_price) {
        this.bdb_ser_hall_price = bdb_ser_hall_price;
    }

    public String getBdb_ser_salon_price() {
        return bdb_ser_salon_price;
    }

    public void setBdb_ser_salon_price(String bdb_ser_salon_price) {
        this.bdb_ser_salon_price = bdb_ser_salon_price;
    }

    public String getBdb_hotel_price() {
        return bdb_hotel_price;
    }

    public void setBdb_hotel_price(String bdb_hotel_price) {
        this.bdb_hotel_price = bdb_hotel_price;
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

    public String getIs_fav_sup() {
        return is_fav_sup;
    }

    public void setIs_fav_sup(String is_fav_sup) {
        this.is_fav_sup = is_fav_sup;
    }

    public String getPriceByFilter(){
        String price="";
        if (PlaceServiceFragment.placeId==1){
            price=getBdb_ser_home_price();
        }else if (PlaceServiceFragment.placeId==30){
            price=getBdb_ser_hall_price();
        }else if (PlaceServiceFragment.placeId==31){
            getBdb_hotel_price();
        }else if (PlaceServiceFragment.placeId==32){
            price=getBdb_ser_salon_price();
        }
        return price;
    }

    public String getLogoId() {
        return logoId;
    }

    public String getNewFav() {
        return newFav;
    }

    public void setNewFav(String newFav) {
        this.newFav = newFav;
    }

    public String getImage_1() {
        return image_1;
    }

    public String getImage_2() {
        return image_2;
    }

    public String getCatId() {
        return catId;
    }

    public String getBdb_client_deposit_ratio() {
        return bdb_client_deposit_ratio;
    }
}
