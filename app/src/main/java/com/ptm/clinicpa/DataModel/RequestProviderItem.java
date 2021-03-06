package com.ptm.clinicpa.DataModel;

import java.util.ArrayList;

public class RequestProviderItem {
    String sup_id,sup_name,logo_id,rating,bdb_booking_period,deposit_prcntg,bdb_loc_long,bdb_loc_lat,bdb_has_experience_cer,bdb_has_health_cer,healthCntr,speciality,healthCntr_ar,speciality_ar,healthCntr_id;

    ArrayList<String> featuresIds = new ArrayList<>();
    String is_fav_center,is_fav_doctor,gender;
    String max_age,min_age,supported_gender;
    public RequestProviderItem(String max_age,String min_age,String supported_gender,String sup_id,String sup_name,String logo_id,String rating,String bdb_booking_period,String deposit_prcntg,String bdb_loc_lat,String bdb_loc_long,String bdb_has_experience_cer,String bdb_has_health_cer,String healthCntr ,String healthCntr_ar,String speciality,String speciality_ar ,String healthCntr_id,String gender,String is_fav_doctor,String is_fav_center)
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
        this.healthCntr=healthCntr;
        this.healthCntr_ar=healthCntr_ar;
        this.speciality_ar=speciality_ar;
        this.speciality=speciality;
        this.healthCntr_id=healthCntr_id;
        this.gender=gender;
        this.is_fav_center=is_fav_center;
        this.is_fav_doctor=is_fav_doctor;
        this.max_age=max_age;
        this.min_age=min_age;
        this.supported_gender=supported_gender;
    }

    String is_av_outside;
    public RequestProviderItem(String is_av_outside,String max_age,String min_age,String supported_gender,String sup_id,String sup_name,String logo_id,String rating,String bdb_booking_period,String deposit_prcntg,String bdb_loc_lat,String bdb_loc_long,String bdb_has_experience_cer,String bdb_has_health_cer,String healthCntr ,String healthCntr_ar,String speciality,String speciality_ar ,String healthCntr_id,String gender,String is_fav_doctor,String is_fav_center)
    {
        this.sup_id=sup_id;
        this.sup_name=sup_name;
        this.is_av_outside=is_av_outside;
        this.logo_id=logo_id;
        this.rating=rating;
        this.bdb_booking_period=bdb_booking_period;
        this.deposit_prcntg=deposit_prcntg;
        this.bdb_loc_lat=bdb_loc_lat;
        this.bdb_loc_long=bdb_loc_long;
        this.bdb_has_health_cer=bdb_has_health_cer;
        this.bdb_has_experience_cer=bdb_has_experience_cer;
        this.healthCntr=healthCntr;
        this.healthCntr_ar=healthCntr_ar;
        this.speciality_ar=speciality_ar;
        this.speciality=speciality;
        this.healthCntr_id=healthCntr_id;
        this.gender=gender;
        this.is_fav_center=is_fav_center;
        this.is_fav_doctor=is_fav_doctor;
        this.max_age=max_age;
        this.min_age=min_age;
        this.supported_gender=supported_gender;
    }
    public RequestProviderItem(String sup_id,String logo_id,String rating,String bdb_booking_period,String bdb_loc_lat,String bdb_loc_long,String healthCntr ,String healthCntr_ar,String healthCntr_id,String is_fav_center,ArrayList featuresIds)
    {
        this.sup_id=sup_id;
        this.logo_id=logo_id;
        this.rating=rating;
        this.bdb_booking_period=bdb_booking_period;
        this.bdb_loc_lat=bdb_loc_lat;
        this.bdb_loc_long=bdb_loc_long;
        this.healthCntr=healthCntr;
        this.healthCntr_ar=healthCntr_ar;
        this.healthCntr_id=healthCntr_id;
        this.is_fav_center=is_fav_center;
        this.featuresIds=featuresIds;
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

    public String getHealthCntr_id() {
        return healthCntr_id;
    }

    public String getSup_name() {
        return sup_name;
    }

    public String getDeposit_prcntg() {
        return deposit_prcntg;
    }

    public String getHealthCntr() {
        return healthCntr;
    }

    public String getHealthCntr_ar() {
        return healthCntr_ar;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getSpeciality_ar() {
        return speciality_ar;
    }

    public String getIs_fav_doctor() {
        return is_fav_doctor;
    }

    public String getGender() {
        return gender;
    }

    public String getIs_fav_center() {
        return is_fav_center;
    }

    public ArrayList<String> getFeaturesIds() {
        return featuresIds;
    }

    public String getSupported_gender() {
        return supported_gender;
    }

    public String getMax_age() {
        return max_age;
    }

    public String getIs_av_outside() {
        return is_av_outside;
    }

    public String getMin_age() {
        return min_age;
    }
}
