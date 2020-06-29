package com.ptm.clinicpa.DataModel;

import java.util.ArrayList;

public class AppointmentsDataModel {
    String bdb_appointment_id ;
    String bdb_inner_booking;
    String bookedByMe ;
    String booking_place ;
    String services_price ;
    String journey_cost ;
    String journey_count ;
    String client_name ;
    String bdb_start_date ;
    String bdb_start_time ;
    String clients_count ;
    String services_count;
    String client_phone;
    String bdb_user_level2_id ;
    String bdb_internally_number ;
    String bdb_loc_long ;
    String bdb_loc_lat ;
    String bdb_health_center_logo_id ;
    String can_cancel ;
    String can_rating ;
    String can_check_in ;
    String can_order_change;
    String can_health_center_rating ;
    String doctor_rating ;
    String health_center_rating ;
    String health_center_ar ;
    String health_record ;
    String health_center_en ;
    String specialization_ar ;
    String specialization_en ;
    String doctor_name ;
    String doctor_id ;
    String health_center_id ;
    String bdb_max_delay ;
    String bdb_health_center_phone ;
    String is_shifted ;
    String shifted_period ;
    String is_has_change_order ;
    String bdb_is_group_booking  ;
    String is_checked_in ;
    String visit_type ;
    String basic_price ;
    String status ;
    ArrayList<ServicesInsideAppointment> services=new ArrayList<>();

    public AppointmentsDataModel(String bdb_appointment_id , String bdb_inner_booking,
            String bookedByMe , String booking_place , String services_price ,
            String journey_cost , String journey_count , String client_name ,
            String bdb_start_date , String bdb_start_time , String clients_count ,
            String services_count, String client_phone, String bdb_user_level2_id ,
            String bdb_internally_number , String bdb_loc_long , String bdb_loc_lat ,
            String bdb_health_center_logo_id , String can_cancel , String can_rating ,
            String can_check_in , String can_order_change, String can_health_center_rating ,
            String doctor_rating , String health_center_rating , String health_center_ar ,
            String health_record , String health_center_en , String specialization_ar ,
            String specialization_en , String doctor_name , String doctor_id ,
            String health_center_id , String bdb_max_delay , String bdb_health_center_phone ,
            String is_shifted , String shifted_period , String is_has_change_order ,
            String bdb_is_group_booking  , String is_checked_in , String visit_type ,
                                 String basic_price,ArrayList <ServicesInsideAppointment> services,String status)
    {
        this. bdb_appointment_id=bdb_appointment_id ;
        this. bdb_inner_booking=bdb_inner_booking;
        this. bookedByMe =bookedByMe;
        this. booking_place=booking_place ;
        this. services_price =services_price;
        this. journey_cost =journey_cost;
        this. journey_count=journey_count ;
        this. client_name =client_name;
        this. bdb_start_date=bdb_start_date ;
        this. bdb_start_time =bdb_start_time;
        this. clients_count=clients_count ;
        this. services_count=services_count;
        this. client_phone=client_phone;
        this. bdb_user_level2_id =bdb_user_level2_id;
        this. bdb_internally_number =bdb_internally_number;
        this. bdb_loc_long=bdb_loc_long ;
        this. bdb_loc_lat =bdb_loc_lat;
        this. bdb_health_center_logo_id=bdb_health_center_logo_id ;
        this. can_cancel =can_cancel;
        this. can_rating =can_rating;
        this. can_check_in=can_check_in ;
        this. can_order_change=can_order_change;
        this. can_health_center_rating=can_health_center_rating ;
        this. doctor_rating =doctor_rating;
        this. health_center_rating=health_center_rating ;
        this. health_center_ar=health_center_ar ;
        this. health_record=health_record ;
        this. health_center_en=health_center_en ;
        this. specialization_ar=specialization_ar ;
        this. specialization_en=specialization_en ;
        this. doctor_name=doctor_name ;
        this. doctor_id =doctor_id;
        this. health_center_id=health_center_id ;
        this. bdb_max_delay=bdb_max_delay ;
        this. bdb_health_center_phone=bdb_health_center_phone ;
        this. is_shifted=is_shifted ;
        this. shifted_period=shifted_period ;
        this. is_has_change_order=is_has_change_order ;
        this. bdb_is_group_booking=bdb_is_group_booking  ;
        this. is_checked_in=is_checked_in ;
        this. visit_type=visit_type ;
        this. basic_price=basic_price ;
        this. services=services ;
        this. status=status ;
    }

    public String getClient_name() {
        return client_name;
    }

    public String getBdb_start_date() {
        return bdb_start_date;
    }

    public String getBdb_appointment_id() {
        return bdb_appointment_id;
    }

    public String getBdb_health_center_logo_id() {
        return bdb_health_center_logo_id;
    }

    public String getBdb_inner_booking() {
        return bdb_inner_booking;
    }

    public String getBdb_internally_number() {
        return bdb_internally_number;
    }

    public String getBookedByMe() {
        return bookedByMe;
    }

    public String getBdb_loc_lat() {
        return bdb_loc_lat;
    }

    public String getBdb_loc_long() {
        return bdb_loc_long;
    }

    public String getBdb_start_time() {
        return bdb_start_time;
    }

    public String getBooking_place() {
        return booking_place;
    }

    public String getBdb_user_level2_id() {
        return bdb_user_level2_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public String getClients_count() {
        return clients_count;
    }

    public String getJourney_cost() {
        return journey_cost;
    }

    public String getCan_cancel() {
        return can_cancel;
    }

    public String getJourney_count() {
        return journey_count;
    }

    public String getCan_check_in() {
        return can_check_in;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public String getServices_price() {
        return services_price;
    }

    public String getClient_phone() {
        return client_phone;
    }

    public String getCan_health_center_rating() {
        return can_health_center_rating;
    }

    public String getCan_order_change() {
        return can_order_change;
    }

    public String getBdb_is_group_booking() {
        return bdb_is_group_booking;
    }

    public String getBasic_price() {
        return basic_price;
    }

    public String getCan_rating() {
        return can_rating;
    }

    public String getDoctor_rating() {
        return doctor_rating;
    }

    public String getServices_count() {
        return services_count;
    }

    public String getBdb_health_center_phone() {
        return bdb_health_center_phone;
    }

    public String getHealth_center_ar() {
        return health_center_ar;
    }

    public String getBdb_max_delay() {
        return bdb_max_delay;
    }

    public String getHealth_center_en() {
        return health_center_en;
    }

    public String getHealth_center_rating() {
        return health_center_rating;
    }

    public String getHealth_center_id() {
        return health_center_id;
    }

    public String getHealth_record() {
        return health_record;
    }

    public String getIs_checked_in() {
        return is_checked_in;
    }

    public String getIs_shifted() {
        return is_shifted;
    }

    public String getSpecialization_ar() {
        return specialization_ar;
    }

    public String getIs_has_change_order() {
        return is_has_change_order;
    }

    public String getShifted_period() {
        return shifted_period;
    }

    public String getSpecialization_en() {
        return specialization_en;
    }

    public String getVisit_type() {
        return visit_type;
    }

    public ArrayList<ServicesInsideAppointment> getServices() {
        return services;
    }

    public String getStatus() {
        return status;
    }
}
