package com.ptm.clinicpa.API;

public class Filters {

    public static String SERVICE_ID ="33";
    public static String SPECIALITY_ID ="45";
    public static String DOCTOR_GENDER ="46";
    public static String CLINIC_ID ="47";
    public static String PATIENT_GENDER ="48";
    public static String DOCTOR_ID ="49";
    public static String CENTER_RATING ="50";
    public static String PATIENT_OLD ="51";
    public static String DOCTOR_NAME ="52";
    public static String DATE ="54";

    public static String getString(String filterNum,String val1,String val2){
        String filter;
        filter="{\"num\":"+filterNum+",\"value1\":"+val1+",\"value2\":"+val2+"}";

        return filter;
    }
    public static String getString(String filterNum,String val1){
        String filter;
        filter="{\"num\":"+filterNum+",\"value1\":"+val1+",\"value2\":"+"0"+"}";
//            }

        return filter;
    }
    public static String getStringdate(String filterNum,String val1){
        String filter;
        filter="{\"num\":"+filterNum+",\"value1\":\""+val1+"\",\"value2\":"+"0"+"}";
//            }

        return filter;
    }
}
