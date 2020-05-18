package com.ptmsa1.clinicclient.API;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PushNotifications {



    static String token_provider="fmLbr783vIE:APA91bH-uP20AjLfw0R4aso0fi5Ty4fRpF3gvavUxsv9qxJltsiEkbUTHuk_FPq2bP1PjyEfk8dkE2eNliQ3HjNdRklPv8k9shD2UZO0fUn7qtvCLWuHnEBoNUjB2zfhhj5hFQ2Sn8Ne";
    static String api_key_header_value_client = "Key=AAAA6gZ1CO8:APA91bHEg19SqKpRdvifPk3-o-nWwDm350IZaNjqX0yy0eHkRUnv1hSBHN6zaQZR0ZvoINJUNX1zbRMDto0W4ePuFwckOOBabMECCscYuwyisY4YEGHhCr10kjEVPoifc9IOz_x7dP0q";
    static String token_client="fSlA-jzIfq4:APA91bG7Gpr5DHrzQwQuHMKpeFYv9e6Ot5Ps8sYt39QjJJJi-aqtf62qhJgsBZRe52Un8HLQS_iWi9ZVEruc1amFVVZKErlNjH_Fmd2SpaTQTUxUrcAHHyyYJwd-wFODqHauoS0fyz-x";
    static String api_key_header_value_provider = "Key=AAAAAAXCVwM:APA91bFiJYACTd-gZPdHrymnwcypg2IQ6JfSdTqUWqt95VANEyTe7H8NAn2nUnwfoau63QdJTXrxpLR5ZyDQ2-PL6TfPCCH7JJrocD1-SkfE7qrfMIqZvu09ICnD72OqAzuB-o85WawO";


   public static void sendnotification_provider(Context context,String token_provider,String title,String body ,String action1,String action2) {
        try{
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://fcm.googleapis.com/fcm/send";
            JSONObject data1 = new JSONObject();
            data1.put("title", title);
            data1.put("body", body);
            if(action1.isEmpty() && action2.isEmpty()){

            }else {
                data1.put("action1", action1);
                data1.put("action2", action2);
            }
            JSONObject notification_data = new JSONObject();
            notification_data.put("to",token_provider);
            notification_data.put("data", data1);
            System.out.println(notification_data);

            JsonObjectRequest request = new JsonObjectRequest(url, notification_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", api_key_header_value_provider);
                    System.out.println("Send to provider");
                    return headers;
                }
            };

            queue.add(request);
            System.out.println(request);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void sendnotification_client(Context context,String token_client,String title,String body, String action1,String action2) {
       SharedPreferences prefs = context.getSharedPreferences("REG_ID",context.MODE_PRIVATE);
       token_client=prefs.getString("token_client",null);
       Log.e("token_client",token_client);
        try{
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://fcm.googleapis.com/fcm/send";
            JSONObject data1 = new JSONObject();
            data1.put("title", title);
            data1.put("body", body);
            if(action1.isEmpty() && action2.isEmpty()){

            }else {
                data1.put("action1", action1);
                data1.put("action2", action2);
            }
            JSONObject notification_data = new JSONObject();
            notification_data.put("to",token_client);
            notification_data.put("data", data1);
            System.out.println(notification_data);

            JsonObjectRequest request = new JsonObjectRequest(url, notification_data, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }){
                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authorization", api_key_header_value_client);
                    return headers;
                }
            };

            queue.add(request);
            System.out.println(request);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
