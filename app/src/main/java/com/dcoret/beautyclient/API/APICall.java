package com.dcoret.beautyclient.API;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.IndividualBooking;
import com.dcoret.beautyclient.Activities.Login;
import com.dcoret.beautyclient.Activities.Offers;
import com.dcoret.beautyclient.Activities.Services;
import com.dcoret.beautyclient.Activities.SplashScreen;
import com.dcoret.beautyclient.Activities.TabOne;
import com.dcoret.beautyclient.Activities.TabTwo;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.DataClass.AddToCart;
import com.dcoret.beautyclient.DataClass.BestOfferItem;
import com.dcoret.beautyclient.DataClass.BookingAutomatedBrowseData;
import com.dcoret.beautyclient.DataClass.BrowseServiceItem;
import com.dcoret.beautyclient.DataClass.Cities;
import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.FilterAndSortModel;
import com.dcoret.beautyclient.DataClass.GetCart;
import com.dcoret.beautyclient.DataClass.LocationTitles;
import com.dcoret.beautyclient.DataClass.SearchBookingDataSTR;
import com.dcoret.beautyclient.DataClass.SerchGroupBookingData;
import com.dcoret.beautyclient.DataClass.ServiceItems;
import com.dcoret.beautyclient.Fragments.AccountFragment;
import com.dcoret.beautyclient.Fragments.BagReservationFragment;
import com.dcoret.beautyclient.Fragments.GroupReservationFragment;
import com.dcoret.beautyclient.Fragments.GroupReservationResultFragment;
import com.dcoret.beautyclient.Fragments.MapFragment;
import com.dcoret.beautyclient.Fragments.PlaceServiceFragment;
import com.dcoret.beautyclient.Fragments.PlaceServiceGroupFragment;
import com.dcoret.beautyclient.Fragments.ReservationFragment;
import com.dcoret.beautyclient.Fragments.ServiceFragment;
import com.dcoret.beautyclient.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class APICall {




    public APICall() {
    }
    private static final OkHttpClient client = new OkHttpClient();
    static ProgressDialog pd;
    static String error = "";
    static   String ln= SplashScreen.ln;


    //-------------------------------------------
    public static String gettoken(Context context){
        String shared_token=((AppCompatActivity)context).getSharedPreferences("LOGIN",Context.MODE_PRIVATE).getString("token",null);
    return shared_token;
    }
        //---استعراض خدمات المزودين لخدمة معينة-----------------------------done
        static  String json;
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)

        public static void getData(Context context, String url, final ServicesAdapter adapter, final String id) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("REsponse",response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
    //                adapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Volley", error.toString());
                    progressDialog.dismiss();
                }
            }){

                @Override
                public String getBodyContentType() {

                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    final Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json");
                    headers.put("Authrization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjQyZmZlN2Q5NmYzNzM4OWRhZDNjMDllMDk5NGQwZDY4ODdmNGI0YzMxMzlmYjZhOTE1MTc5NWIwMzVmYTE5MDBhODI0NzI3NThmYTM2MDFhIn0.eyJhdWQiOiIxIiwianRpIjoiNDJmZmU3ZDk2ZjM3Mzg5ZGFkM2MwOWUwOTk0ZDBkNjg4N2Y0YjRjMzEzOWZiNmE5MTUxNzk1YjAzNWZhMTkwMGE4MjQ3Mjc1OGZhMzYwMWEiLCJpYXQiOjE1NTgyOTY3ODEsIm5iZiI6MTU1ODI5Njc4MSwiZXhwIjoxNTg5OTE5MTgxLCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.MG04uKIgEjPHwU_bo3ai_f1oGhOJdk0DQr5_KFQYKAZOj5EUM7ZVi08JnefgSB9R9rPAE3VcZaIBCGJ2OrjI0zO3K7PQZEQ2m-gmdsMye4sMGL2LeRrm6aHOKpJDY_jdpJMgFgbOL3oFh9XbVxbha0f0ofhOhiSl4oIfZ8-G7VzWZPuwA1dIt1QfgqjfWBSpWd9JPe7s3PrwrcUcXIF8qObjl6MqWQ3I33I8g2-Vc9O7b356_21Un_XP6lYbZMN4VWKUifqYiO2t509M6RjUovDI_cd9a30EHB7hTdIkmxHP2JKudwHbHil7cEkel7UQAvfJrbcapm60Jb8fucWoAtedtuPpxYEgxAZ0HqZNi5ynPoO1VIygHOiYvI8iNzNwkRMJ5quV4PIK4SaGArZs6Nd5Pz9vXKc-apWo2WzDZ9R1KQg0y3LNNRyMPmGjVN_8u3QixbomiXuPoOAsKuZzzCsRZMdQ2sug0nlm69BiCSbq3Zn40gmIqTXAhG1AIcm2WqgCqi9SKWyWOBc8Tv2NnnccH_FCkUCPCa54ZRMsMGrkycG6oV1wYQkpBKF1lS0yx2NCX0RJGFLEATkMKRX1wdKOgjmyALtk5IBsN9KOr6rBl4sWEQb0zsVgzaTdHqex4j0a03jtsbq8RplKJeY5SbJOUv-o8EC6gjMbJzCa5ik");
    //                    headers.put("Connection", "keep-alive");
                    return headers;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("bdb_ser_id", id);
    //                    params.put("owner_name", owner_name.getText().toString());
                    return params;
                }

            };
            Log.d("JsonArray",jsonArrayRequest+"");
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonArrayRequest);
        }
        //    ------------------- register new user in beauty client app----------------
        static String mMessage="";

    public  static  String  new_user( final String phone,final String gender ,final String password, final String confirm_password, final String loc_long
            , final String loc_lat, final String description,final String my_description,  final  String url, final Context context){
        if (validationPassword(password)){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd=new ProgressDialog(context);
            pd.show();
            //        String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try {

                postdata.put("bdb_mobile", phone);
                postdata.put("bdb_gender", gender);
                postdata.put("password", password);
                postdata.put("c_password", confirm_password);
                postdata.put("bdb_loc_long", loc_long);
                postdata.put("bdb_loc_lat", loc_lat);
                postdata.put("bdb_descr",description );
                postdata.put("bdb_my_descr", my_description);
//                postdata.put("bdb_city", "1");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type","multipart/form-data")
                    .addHeader("Accept","application/json")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
                    pd.dismiss();


                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(context);
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                TextView confirm = dialog.findViewById(R.id.confirm);
                                TextView message = dialog.findViewById(R.id.message);
                                TextView title = dialog.findViewById(R.id.title);
                                title.setText(R.string.Null);
                                message.setText(R.string.check_internet_con);
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();

                                    }
                                });
                                dialog.show();

                            }
                        });


                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.e("TAG", mMessage);
                    pd.dismiss();
                    SharedPreferences.Editor editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE).edit();
                    try {
                        final JSONObject userresponse=new JSONObject(mMessage);
                        String success=userresponse.getString("success");
                        if(success.equals("true")){
                            JSONObject data=userresponse.getJSONObject("data");
                            String ac_num=userresponse.getString("activation number :");
                            Log.d("number",ac_num);
                            APICall.name=name;
                            APICall.token_temp=data.getString("token");
                            showSweetDialog(context,R.string.ExuseMeAlert,R.string.EnterVerificationCode,true);
                        }else if(success.equals("false")) {
                            JSONObject err= userresponse.getJSONObject("message");
                            JSONArray bdb_mobile=err.getJSONArray("bdb_mobile");
                            String error=bdb_mobile.getString(0);
                            if(error.equals("bdb_mobile is already exists and not activated")){
                                activateAgain("http://clientapp.dcoret.com/api/auth/user/register/ActivateAgain",
                                        phone,
                                        context);
                                showSweetDialog(context,R.string.ExuseMeAlert,R.string.numberNotActivatedAlert,true);
                            }else if (error.equals("The bdb mobile format is invalid.")){
                                showSweetDialog(context,R.string.ExuseMeAlert,R.string.InvalidFormatNum,false);
//                            for email address
                            }else if (error.equals("The bdb mobile format is invalid.")){
                                showSweetDialog(context,R.string.ExuseMeAlert,R.string.InvalidFormatNum,false);
                            }else if(error.equals("bdb_mobile is already exists and activated")) {
                                showSweetDialog(context,R.string.ExuseMeAlert,R.string.MobTakenAlert,false);
                            }
                        }
                    }catch (final JSONException je){
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,je.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });

                    }

                }

            });
        }else {
            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showSweetDialog(context,R.string.ExuseMeAlert,R.string.InvalidPassword);

                }
            });
        }
        return mMessage;

    }


    //   ------------------ rating the app =-------------------------
    public  static  void  rateApp(final String bdb_rate,final  String url,final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        pd=new ProgressDialog(context);
        pd.show();
//        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {

            postdata.put("bdb_rate", bdb_rate);
//            postdata.put("password", "12345");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                pd.dismiss();


                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();

                        }
                    });

                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("TAG", mMessage);
                pd.dismiss();
                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true"))
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,R.string.rate_toast,Toast.LENGTH_LONG).show();

                            }
                        });

                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });
//        Log.d("MessageResponse",mMessage);
    }


    //   ------------------ Best Offer-------------------------
    public  static  void  bestOffer(final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd=new ProgressDialog(context);
                Offers.pullToRefresh.setRefreshing(true);
                pd.show();
            }
        });

//        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();


        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/service/offer/bestOffer")
                .post(body)
                .addHeader("Content-Type","application/json")
//                .addHeader("Accept","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Offers.pullToRefresh.setRefreshing(false);
                        pd.dismiss();
                    }
                });


                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();
                        }
                    });
                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Offers.pullToRefresh.setRefreshing(false);
                        pd.dismiss();
                    }
                });

                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true")) {
                       int pkg_count=Integer.parseInt(j.getString( "packages count"));
                       JSONArray packages=j.getJSONArray("packages");
                       for (int i=0;i<packages.length();i++){
                           JSONObject pkg=packages.getJSONObject(i);
                           String pack_code=pkg.getString("pack_code");
                           String service_count=pkg.getString("service count");
                           String provider_name=pkg.getString("provider name");
                           String old_price=pkg.getString("old_price");
                           String new_price=pkg.getString("new_price");
                           String total_discount=pkg.getString("total_discount");
                           JSONArray sersup_ids=pkg.getJSONArray("sersup_ids");
//                            Log.e("pkg",pack_code+":"+service_count+":"+provider_name);
                        Offers.bestOfferItems.add(new BestOfferItem(pack_code,service_count,provider_name,old_price,new_price,total_discount,sersup_ids));

                       }
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Offers.bestOffer.notifyDataSetChanged();
                            }
                        });

                    }
                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        Log.d("MessageResponse",mMessage);
    }


        //    ------------------------------ detailsuser-----------------
        public  static  void  detailsUser(final  String url, final EditText e_bdb_name, final EditText e_bdb_email, final EditText e_bdb_mobile,  final Context context){
            final SharedPreferences.Editor editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE).edit();
            SharedPreferences sh=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
            try{
                sh.getString("bdb_name",null).equals("");
            }catch (NullPointerException npe){
                editor.putString("bdb_name","");
                editor.commit();
            }
//            Log.e("isfirstopen",BeautyMainPage.isFirstOpen+"");
            if(!BeautyMainPage.isFirstOpen){
                e_bdb_name.setText(sh.getString("bdb_name",null));
                e_bdb_email.setText(sh.getString("bdb_email",null));
                e_bdb_mobile.setText(sh.getString("bdb_mobile",null));

            }else {
                BeautyMainPage.isFirstOpen=false;
                String token = ((AppCompatActivity) context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("token", null);
                MediaType MEDIA_TYPE = MediaType.parse("application/json");

                        pd = new ProgressDialog(context);
                        pd.show();


                //-----------String url = "http://clientapp.dcoret.com/api/service/Service";
                OkHttpClient client = new OkHttpClient();
                JSONObject postdata = new JSONObject();

                RequestBody body = RequestBody.create(MEDIA_TYPE, "");

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(url)
                        .post(body)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("X-Requested-With", "XMLHttpRequest")
                        .header("Authorization", "Bearer " + token)
    //                .header("Content-Type", "application/json")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mMessage = e.getMessage().toString();
                        Log.w("failure Response", mMessage);
                        pd.dismiss();


                        if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                            ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final Dialog dialog = new Dialog(BeautyMainPage.context);
                                    dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                    TextView confirm = dialog.findViewById(R.id.confirm);
                                    TextView message = dialog.findViewById(R.id.message);
                                    TextView title = dialog.findViewById(R.id.title);
                                    title.setText(R.string.Null);
                                    message.setText(R.string.check_internet_con);
                                    confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.cancel();
                                            final Dialog refreshDialog = new Dialog(BeautyMainPage.context);
                                            refreshDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                            refreshDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                            refreshDialog.setContentView(R.layout.refresh_btn_dialog);
                                            Button refresh=refreshDialog.findViewById(R.id.refresh);
                                            refresh.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    refreshDialog.cancel();
//                                                    new_user(email,name,phone,password,confirm_password,loc_long,loc_lat,city,gender,url,context);
                                            detailsUser(url,e_bdb_name,e_bdb_email,e_bdb_mobile,context);
                                                }
                                            });
                                            refreshDialog.show();
                                        }
                                    });
                                    dialog.show();
                                }
                            });
                        }else {
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        mMessage = response.body().string();
                        Log.e("TAG", mMessage);
                        try {
                            JSONObject jsonObject = new JSONObject(mMessage);
                            String success = jsonObject.getString("success");
                            if (success.equals("true")) {

                                JSONObject data = jsonObject.getJSONObject("data");
                                final String bdb_name = data.getString("bdb_name");
                                final String bdb_email = data.getString("bdb_email");
                                final String bdb_mobile = data.getString("bdb_mobile");


                                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        e_bdb_mobile.setText(bdb_mobile);
                                        e_bdb_email.setText(bdb_email);
                                        e_bdb_name.setText(bdb_name);

                                    }
                                });



//                                editor.putString("bdb_name", bdb_name);
                                editor.putString("bdb_email", bdb_email);
                                editor.putString("bdb_mobile", bdb_mobile);
                                editor.putString("addressUser", mMessage+"");
                                editor.apply();
                                editor.commit();

                                JSONArray addressUser=jsonObject.getJSONArray("address");
                                for (int i=0;i<addressUser.length();i++){
                                JSONObject address=addressUser.getJSONObject(i);
                                String bdb_id=address.getString("bdb_id");
                                String bdb_user_id=address.getString("bdb_user_id");
                                String bdb_city_id=address.getString("bdb_city_id");
                                String bdb_loc_lat=address.getString("bdb_loc_lat");
                                String bdb_sup_id=address.getString("bdb_sup_id");
                                String bdb_descr=address.getString("bdb_descr");
                                String bdb_loc_long=address.getString("bdb_loc_long");
                                String bdb_is_deleted=address.getString("bdb_is_deleted");
                                String bdb_my_descr=address.getString("bdb_my_descr");
                                LatLng ltlng=new LatLng(Double.parseDouble(bdb_loc_lat),Double.parseDouble(bdb_loc_long));
                                AccountFragment.locationTitles.add(new LocationTitles(bdb_id,ltlng,bdb_my_descr,bdb_user_id,bdb_city_id,bdb_sup_id,bdb_descr));

                                Log.e("LTAccFrag", AccountFragment.locationTitles.get(i).getId()+" : "+AccountFragment.locationTitles.get(i).getBdb_my_descr());
                            }
                            }
                        } catch (JSONException e) {

                        }
                        pd.dismiss();
                    }

                });

                Log.d("MessageResponse", mMessage);
            }

    }
        public  static  void  detailsUser1(final  String url,  final Context context){
        final SharedPreferences.Editor editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE).edit();
//        SharedPreferences sh=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
//        try{
//            sh.getString("bdb_name",null).equals("");
//        }catch (NullPointerException npe){
//            editor.putString("bdb_name","");
//            editor.commit();
//        }
//            Log.e("isfirstopen",BeautyMainPage.isFirstOpen+"");
//            e_bdb_name.setText(sh.getString("bdb_name",null));
//            e_bdb_email.setText(sh.getString("bdb_email",null));
//            e_bdb_mobile.setText(sh.getString("bdb_mobile",null));

//            BeautyMainPage.isFirstOpen=false;
            String token = ((AppCompatActivity) context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("token", null);
            MediaType MEDIA_TYPE = MediaType.parse("application/json");

//            pd = new ProgressDialog(context);
//            pd.show();


            //-----------String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();

            RequestBody body = RequestBody.create(MEDIA_TYPE, "");

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .header("Authorization", "Bearer " + token)
                    //                .header("Content-Type", "application/json")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
                    pd.dismiss();


                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(BeautyMainPage.context);
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                TextView confirm = dialog.findViewById(R.id.confirm);
                                TextView message = dialog.findViewById(R.id.message);
                                TextView title = dialog.findViewById(R.id.title);
                                title.setText(R.string.Null);
                                message.setText(R.string.check_internet_con);
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                        final Dialog refreshDialog = new Dialog(BeautyMainPage.context);
                                        refreshDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                        refreshDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                        refreshDialog.setContentView(R.layout.refresh_btn_dialog);
                                        Button refresh=refreshDialog.findViewById(R.id.refresh);
                                        refresh.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                refreshDialog.cancel();
//                                                    new_user(email,name,phone,password,confirm_password,loc_long,loc_lat,city,gender,url,context);
                                                detailsUser1(url,context);
                                            }
                                        });
                                        refreshDialog.show();
                                    }
                                });
                                dialog.show();
                            }
                        });
                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.e("TAG", mMessage);
                    try {
                        JSONObject jsonObject = new JSONObject(mMessage);
                        String success = jsonObject.getString("success");
                        if (success.equals("true")) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            String bdb_name = data.getString("bdb_name");
//                            e_bdb_name.setText(bdb_name);
                            String bdb_email = data.getString("bdb_email");
//                            e_bdb_email.setText(bdb_email);
                            String bdb_mobile = data.getString("bdb_mobile");

//                            e_bdb_mobile.setText(bdb_mobile);
//                                editor.putString("bdb_name", bdb_name);
                            editor.putString("bdb_email", bdb_email);
                            editor.putString("bdb_name", bdb_name);
                            editor.putString("bdb_mobile", bdb_mobile);
                            editor.putString("addressUser", mMessage+"");
                            editor.apply();
                            editor.commit();

//                            JSONArray addressUser=jsonObject.getJSONArray("address");
//                            for (int i=0;i<addressUser.length();i++){
//                                JSONObject address=addressUser.getJSONObject(i);
//                                String bdb_id=address.getString("bdb_id");
//                                String bdb_user_id=address.getString("bdb_user_id");
//                                String bdb_city_id=address.getString("bdb_city_id");
//                                String bdb_loc_lat=address.getString("bdb_loc_lat");
//                                String bdb_sup_id=address.getString("bdb_sup_id");
//                                String bdb_descr=address.getString("bdb_descr");
//                                String bdb_loc_long=address.getString("bdb_loc_long");
//                                String bdb_is_deleted=address.getString("bdb_is_deleted");
//                                String bdb_my_descr=address.getString("bdb_my_descr");
//                                LatLng ltlng=new LatLng(Double.parseDouble(bdb_loc_lat),Double.parseDouble(bdb_loc_long));
//                                AccountFragment.locationTitles.add(new LocationTitles(bdb_id,ltlng,bdb_my_descr,bdb_user_id,bdb_city_id,bdb_sup_id,bdb_descr));

//                                Log.e("LTAccFrag", AccountFragment.locationTitles.get(i).getId()+" : "+AccountFragment.locationTitles.get(i).getBdb_my_descr());
                            }
//                        }
                    } catch (JSONException e) {

                    }
//                    pd.dismiss();
                }

            });

            Log.d("MessageResponse", mMessage);
        }




    //---------------------- active account-------------------
        static   String name,token_temp;
        public  static  void   activeAccount(final  String url, final String token, final Context context){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        pd=new ProgressDialog(context);
        pd.show();
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("token", token);
//            postdata.put("password", "12345");
        } catch (JSONException e) {
//         TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE,postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                pd.dismiss();

                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();
                        }
                    });


                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                try {
                    final JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true")){
                        pd.dismiss();
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, R.string.familiyBeauty, Toast.LENGTH_LONG).show();
                                SharedPreferences.Editor editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE).edit();
//                               if (!name.isEmpty()) {
                                editor.putString("name", "ok");
                                editor.putString("token", token_temp);
                                Log.e("Tokensaved",token_temp);
                                editor.apply();
                                editor.commit();

                                Intent i=new Intent(context,Offers.class);
                                ((AppCompatActivity) context).finish();
                                context.startActivity(i);
                            }
                        });
                    }else {
                        showSweetDialog(context,R.string.nice,R.string.InputCodeWrongAlert,token,true,"");
                    }

                }catch (JSONException je){
                }
                Log.e("TAG", mMessage);

                pd.dismiss();
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,mMessage,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        Log.d("MessageResponse",mMessage);
    }
        public  static  void   activateAgain(final  String url, final String number, final Context context){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_mobile", number);

        } catch (JSONException e) {
//         TODO Auto-generated catch block
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(MEDIA_TYPE,postdata.toString());
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                pd.dismiss();


                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();
                        }
                    });
                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("Activation_Code",mMessage);
                try{
                   JSONObject jsonObject=new JSONObject(mMessage);
                    APICall.token_temp=jsonObject.getString("bdb_token");
                }catch (JSONException je){
                    je.printStackTrace();
                }
            }
        });

    }
        //------------------------------ login----------------------
        static JSONObject data;
        public  static  void  login(final String name,String  pass,final  String url,final Context context){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        pd=new ProgressDialog(context);
        pd.show();

        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_mobile", name);
            postdata.put("password", pass);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type","application/json")
                .addHeader("X-Requested-With","XMLHttpRequest")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage();
                pd.dismiss();

                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();

                        }
                    });
                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("TAG", mMessage);
                pd.dismiss();
                try {
                    JSONObject res=new JSONObject(mMessage);
                    String success=res.getString("success");
                     final String message=res.getString("message");
                     Log.e("message",message);
                    if (success.equals("true")){
                        data=res.getJSONObject("data");
                        Intent i=new Intent(context,BeautyMainPage.class);
                        context.startActivity(i);
                        ((AppCompatActivity)context).finish();
                        String token=data.getString("bdb_token");
                        SharedPreferences.Editor editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE).edit();
                        editor.putString("name","ok");
                        editor.putString("token",token);
                        editor.commit();
                        editor.apply();
                    }else {
//                        Log.e("messaged",message);
                        if (success.equals("false")) {
                            if (message.equals("This user is not active")) {
                                activateAgain("http://clientapp.dcoret.com/api/auth/user/register/ActivateAgain",
                                        name,
                                        context);
                                showSweetDialog(context, R.string.ExuseMeAlert, R.string.numberNotActivatedAlert,  true);

                            } else if (message.equals("invalid  password")) {
                                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, R.string.WrongPass, Toast.LENGTH_LONG).show();

                                    }
                                });
                            } else if (message.equals("invalid mobile number")) {
                                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, R.string.MobNoIncorrectAlert, Toast.LENGTH_LONG).show();
                                    }
                                });


                            }else if (message.equals("{\"bdb_mobile\":[\"bdb mobile is not valid\"]}")){
                                ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, R.string.WrongMobNumberAlert, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }
                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();

                        }
                    });
                }


            }

        });

        Log.d("MessageResponse",mMessage);
    }
        //-------------------------------------------------------
        public  static  void  deleteAccount(final  String url, final String bdb_del_reason, final String bdb_del_message , final Context context){
            new AlertDialog.Builder(context)
                    .setTitle(R.string.DeleteAccount)
                    .setMessage(R.string.DeleteAccountMessage)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            MediaType MEDIA_TYPE = MediaType.parse("application/json");
                            pd=new ProgressDialog(context);
                            pd.show();

                            OkHttpClient client = new OkHttpClient();
                            JSONObject postdata = new JSONObject();
                            try {
                                postdata.put("bdb_del_reason", bdb_del_reason);
                                postdata.put("bdb_del_message", bdb_del_message);
                            } catch (JSONException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

                            okhttp3.Request request = new okhttp3.Request.Builder()
                                    .url(url)
                                    .post(body)
                                    .addHeader("Content-Type","application/json")
                                    .addHeader("X-Requested-With","XMLHttpRequest")
                                    .header("Authorization", "Bearer "+gettoken(context) )
                                    .build();

                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    mMessage = e.getMessage();
                                    Log.w("failure Response", mMessage);
                                    pd.dismiss();


                                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                                        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                final Dialog dialog = new Dialog(BeautyMainPage.context);
                                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                                TextView confirm = dialog.findViewById(R.id.confirm);
                                                TextView message = dialog.findViewById(R.id.message);
                                                TextView title = dialog.findViewById(R.id.title);
                                                title.setText(R.string.Null);
                                                message.setText(R.string.check_internet_con);
                                                confirm.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        dialog.cancel();
                                                    }
                                                });
                                                dialog.show();
                                            }
                                        });
                                    }else {
                                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }

                                }
                                @Override
                                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                                    mMessage = response.body().string();
                                    Log.e("TAG", mMessage);
                                    pd.dismiss();
                                    try {
                                        JSONObject jsonObject=new JSONObject(mMessage);
                                        String success=jsonObject.getString("success");
                                        if (success.equals("true")){
                                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(context, "تم حذف حسابك", Toast.LENGTH_LONG).show();
                                                }
                                            });

                                            SharedPreferences.Editor editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE).edit();
                                            editor.clear();
                                            editor.commit();
                                            Intent intent=new Intent(context, Login.class);
                                            context.startActivity(intent);
                                            ((AppCompatActivity) context).finish();

                                        }    else {
                                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(context, R.string.SmoeThingWrong, Toast.LENGTH_LONG).show();
                                                }
                                            });
                                        }
                                    }catch (JSONException je){
                                    }
                                }
                            });
                            Log.d("MessageResponse",mMessage);
                        }
                    })
                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
    }
        // ----------------------tested-------------
        public  static  void  addAddress(final  String url, final String bdb_loc_long, final String bdb_loc_lat, final String bdb_descr, final String my_description, final Context context){
                MediaType MEDIA_TYPE = MediaType.parse("application/json");
                pd=new ProgressDialog(context);
                pd.show();

                OkHttpClient client = new OkHttpClient();
                JSONObject postdata = new JSONObject();
                try {
                    postdata.put("bdb_loc_long", bdb_loc_long);
                    postdata.put("bdb_loc_lat", bdb_loc_lat);
                    postdata.put("bdb_descr", bdb_descr);
                    postdata.put("bdb_my_descr", my_description);
                }catch (JSONException je){
                    je.printStackTrace();
                }

                RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url(url)
                        .post(body)
                        .header("Authorization", "Bearer " + gettoken(context))
                        .addHeader("Content-Type","application/json")
                        .addHeader("X-Requested-With","XMLHttpRequest")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mMessage = e.getMessage();
                        Log.w("failure Response", mMessage);
                        pd.dismiss();


                        if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                            ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final Dialog dialog = new Dialog(BeautyMainPage.context);
                                    dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                    TextView confirm = dialog.findViewById(R.id.confirm);
                                    TextView message = dialog.findViewById(R.id.message);
                                    TextView title = dialog.findViewById(R.id.title);
                                    title.setText(R.string.Null);
                                    message.setText(R.string.check_internet_con);
                                    confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.cancel();
                                        }
                                    });
                                    dialog.show();
                                }
                            });

                        }else {
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                                }
                            });
                        }

                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        mMessage = response.body().string();
                        Log.e("AddAddress", mMessage);
                        try {
                            JSONObject jsonObject = new JSONObject(mMessage);
                            String success=jsonObject.getString("success");
                            if (success.equals("true")){
                                String id=jsonObject.getString("address id");
                                AccountFragment.locationTitles.add(new LocationTitles(id,new LatLng(Double.parseDouble(bdb_loc_lat),Double.parseDouble(bdb_loc_long))
                                        ,my_description,"","","",bdb_descr));
                                pd.dismiss();
                            }      else {

                            }
                        }catch (JSONException je){
                            je.printStackTrace();
                            pd.dismiss();

                        }
                    }
                });

                Log.d("MessageResponse",mMessage);
            }
        //-------------------------------------
        public  static  String  deleteAddress(final  String url, String bdb_id, final int id_location_titles, final Marker marker, final Context context){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd=new ProgressDialog(context);
            pd.show();
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try {
                postdata.put("bdb_id", bdb_id);
            }catch (JSONException je){
                je.printStackTrace();
            }

            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .header("Authorization", "Bearer " + gettoken(context))
                    .addHeader("Content-Type","application/json")
                    .addHeader("X-Requested-With","XMLHttpRequest")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage();
                    Log.w("failure Response", mMessage);
                    pd.dismiss();

                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(BeautyMainPage.context);
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                TextView confirm = dialog.findViewById(R.id.confirm);
                                TextView message = dialog.findViewById(R.id.message);
                                TextView title = dialog.findViewById(R.id.title);
                                title.setText(R.string.Null);
                                message.setText(R.string.check_internet_con);
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();

                                    }
                                });
                                dialog.show();

                            }
                        });


                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.e("TAG", mMessage);
                    try {
                        JSONObject jsonObject=new JSONObject(mMessage);
                        String success=jsonObject.getString("success");
                        final String message=jsonObject.getString("message");
                        if (success.equals(false)){
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context,"There is an error:"+message+", Please Try Again Later",Toast.LENGTH_LONG).show();
                                }
                            });
                        }else {

                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("Size",AccountFragment.locationTitles.size()+"");
                                    AccountFragment.locationTitles.remove(id_location_titles);
                                    Log.e("Size",AccountFragment.locationTitles.size()+"");
                                    MapFragment.mMap.clear();
                                    for (int i=0;i<AccountFragment.locationTitles.size();i++){
                                        MapFragment.mMap.addMarker(new MarkerOptions()
                                                .position(AccountFragment.locationTitles.get(i).getLatLng())
                                                .title(AccountFragment.locationTitles.get(i).getBdb_my_descr())
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.placeholder))
                                        );
                                    }
                                    Toast.makeText(context,"This Location is deleted",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }catch (JSONException je){
                        je.printStackTrace();
                    }
                    pd.dismiss();



                }




            });

            Log.d("MessageResponse",mMessage);
            return mMessage;

        }
        //---------------------------
        public  static  String  updateaddress(final  String url, final String bdb_loc_long, final String bdb_loc_lat, final String bdb_address_id, String bdb_descr, final String bdb_my_descr, final Context context){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pd=new ProgressDialog(context);
                }
            });

            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try {
                postdata.put("bdb_address_id", bdb_address_id);
                postdata.put("bdb_loc_long", bdb_loc_long);
                postdata.put("bdb_loc_lat", bdb_loc_lat);
                postdata.put("bdb_descr", bdb_descr);
                postdata.put("bdb_my_descr", bdb_my_descr);
            }catch (JSONException je){
                je.printStackTrace();
            }

            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .put(body)
                    .header("Authorization", "Bearer " + gettoken(context))
                    .addHeader("Content-Type","application/json")
                    .addHeader("X-Requested-With","XMLHttpRequest")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage();
                    Log.w("failure Response", mMessage);
                    pd.dismiss();

                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(BeautyMainPage.context);
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                TextView confirm = dialog.findViewById(R.id.confirm);
                                TextView message = dialog.findViewById(R.id.message);
                                TextView title = dialog.findViewById(R.id.title);
                                title.setText(R.string.Null);
                                message.setText(R.string.check_internet_con);
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();

                                    }
                                });
                                dialog.show();
                            }
                        });
                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.e("TAG", mMessage);
                    try {
                        JSONObject jsonObject = new JSONObject(mMessage);
                        String success = jsonObject.getString("success");
                        final String message = jsonObject.getString("message");
                        if (success.equals(false)) {
                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "There is an error:" + message + ", Please Try Again Later", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            getdetailsUser(context);
                        }
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                    pd.dismiss();
                }
            });

            Log.d("MessageResponse",mMessage);
            return mMessage;

        }
        //------------------------------
        public  static  void   update_user(final  String url, final String bdb_name, final String bdb_email, final String password, final String old_pass, final Context context) {
            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    final MediaType MEDIA_TYPE = MediaType.parse("application/json");
                    pd = new ProgressDialog(context);
                    pd.show();
                    Log.e("tt", "update_user");
//             String url = "http://clientapp.dcoret.com/api/service/Service";
                    OkHttpClient client = new OkHttpClient();
                    JSONObject postdata = new JSONObject();

                    RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

                    okhttp3.Request request = new okhttp3.Request.Builder()
                            .url("http://clientapp.dcoret.com/api/auth/user/getPassword")
                            .post(body)
                            .addHeader("Content-Type", "application/json")
                            .addHeader("X-Requested-With", "XMLHttpRequest")
                            .header("Authorization", "Bearer " + gettoken(context))
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            mMessage = e.getMessage();
                            Log.w("failure Response", mMessage);
                            pd.dismiss();

                            if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                                ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        final Dialog dialog = new Dialog(BeautyMainPage.context);
                                        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                        dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                        TextView confirm = dialog.findViewById(R.id.confirm);
                                        TextView message = dialog.findViewById(R.id.message);
                                        TextView title = dialog.findViewById(R.id.title);
                                        title.setText(R.string.Null);
                                        message.setText(R.string.check_internet_con);
                                        confirm.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                dialog.cancel();

                                            }
                                        });
                                        dialog.show();
                                    }
                                });


                            }else {
                                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                        @Override
                        public void onResponse(Call call, okhttp3.Response response) throws IOException {
                            mMessage = response.body().string();
                            Log.e("TAG", mMessage);
//                    pd.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(mMessage);
                                String success = jsonObject.getString("success");

                                if (success.equals("true")) {
                                    Log.e("getmd5vv", getMD5EncryptedValue(old_pass));
                                    Log.e("Pass", "ok");
                                    JSONObject data = jsonObject.getJSONObject("data");
                                    String pass_res = data.getString("password");
                                    Boolean check_flag;
                                    Boolean pass_flag;
                                    if (AccountFragment.edit_flag) {
                                        check_flag = validationPassword(password);
                                        pass_flag=getMD5EncryptedValue(old_pass).equals(pass_res);
                                    } else {
                                        check_flag = true;
                                        pass_flag=true;
                                    }
                                    if (pass_flag) {
                                        Log.e("validation", validationPassword(password) + "");

                                        if (check_flag) {
                                            Log.e("validation", validationPassword(password) + "");
                                            OkHttpClient client = new OkHttpClient();
                                            JSONObject postdata = new JSONObject();
                                            try {
                                                postdata.put("bdb_name", bdb_name);
                                                if (!context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("bdb_email", null).equals(bdb_email)) {
                                                    postdata.put("bdb_email", bdb_email);
                                                }
                                                postdata.put("password", password);

                                            } catch (JSONException je) {
                                                je.printStackTrace();
                                            }

                                            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

                                            okhttp3.Request request1 = new okhttp3.Request.Builder()
                                                    .url(url)
                                                    .put(body)
                                                    .addHeader("Content-Type", "application/json")
                                                    .addHeader("Accept", "application/json")
                                                    //.addHeader("X-Requested-With","XMLHttpRequest")
                                                    .header("Authorization", "Bearer " + gettoken(context))
                                                    .build();

                                            client.newCall(request1).enqueue(new Callback() {
                                                @Override
                                                public void onFailure(Call call, IOException e) {
                                                    mMessage = e.getMessage();
                                                    Log.w("failure Response", mMessage);
                                                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                                                        }
                                                    });

                                                    pd.dismiss();
                                                }

                                                @Override
                                                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                                                    mMessage = response.body().string();
                                                    Log.e("TAG", mMessage);
                                                    try {
                                                        final JSONObject jsonObject = new JSONObject(mMessage);
                                                        String success = jsonObject.getString("success");
                                                        if (success.equals("true")) {
                                                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    APICall.showSweetDialog(BeautyMainPage.context, R.string.Null, R.string.EditFinished);
                                                                }
                                                            });
                                                        } else {
                                                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                                                @Override
                                                                public void run() {
                                                                    try {
                                                                        APICall.showSweetDialog(BeautyMainPage.context, "", "Error:" + jsonObject.getString("message"));
                                                                    } catch (JSONException e) {
                                                                        e.printStackTrace();
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    } catch (JSONException je) {

                                                    } catch (Exception e) {

                                                    }
                                                    SharedPreferences.Editor editor = context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE).edit();
                                                    editor.putString("bdb_name", bdb_name);
                                                    editor.putString("bdb_email", bdb_email);
                                                    editor.commit();

                                                    pd.dismiss();


                                                }


                                            });
                                        } else {
                                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    pd.dismiss();
                                                    showSweetDialog(context, R.string.ExuseMeAlert, R.string.InvalidPassword);

                                                }
                                            });
                                        }
                                    } else {
                                        pd.dismiss();
                                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                showSweetDialog(context, R.string.ExuseMeAlert, R.string.old_password_wrong);
                                            }
                                        });
                                    }
                                } else {
                                    pd.dismiss();
                                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showSweetDialog(context, "",  "Wrong: "+mMessage );
                                        }
                                    });
                                }
                            } catch (JSONException je) {
                                je.printStackTrace();
                            }
                        }
                    });
                    Log.d("MessageResponse", mMessage);
                }
            });
        }
        public  static  void   update_user(final  String url, final String bdb_name, final String bdb_email, final Context context) {

            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd=new ProgressDialog(context);
            pd.show();

            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try {
            postdata.put("bdb_name", bdb_name);
            postdata.put("bdb_email", bdb_email);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .put(body)
                    .addHeader("Content-Type","application/json")
                    .header("Authorization", "Bearer "+gettoken(context))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
                    pd.dismiss();
                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(BeautyMainPage.context);
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                TextView confirm = dialog.findViewById(R.id.confirm);
                                TextView message = dialog.findViewById(R.id.message);
                                TextView title = dialog.findViewById(R.id.title);
                                title.setText(R.string.Null);
                                message.setText(R.string.check_internet_con);
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                                });
                                dialog.show();
                            }
                        });
                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.e("TAG", mMessage);
                    pd.dismiss();
                    SharedPreferences.Editor editor = context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE).edit();

                    try {
                        final JSONObject j=new JSONObject(mMessage);
                        String success=j.getString("success");
                        if (success.equals("true")) {
                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    APICall.showSweetDialog(BeautyMainPage.context, R.string.Null, R.string.EditFinished);
                                }
                            });
                            editor.putString("bdb_name", bdb_name);
                            editor.putString("bdb_email", bdb_email);
                        } else {
                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        APICall.showSweetDialog(BeautyMainPage.context,"", "Error: " + j.getString("message"));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                            editor.putString("bdb_name", null);
                            editor.putString("bdb_email", null);
                        }
                    } catch (JSONException je) {
                            je.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    editor.commit();
                    pd.dismiss();
                }
            });

            Log.d("MessageResponse",mMessage);
        }
        //------------------ استعراض معلومات خدمة معينة---------------------
        public  static  String  details_user(final  String url,final Context context){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd=new ProgressDialog(context);
            pd.show();

            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();

            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type","application/json")
                    .header("Authorization","Bearer "+gettoken(context))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage();
                    Log.w("failure Response", mMessage);
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });

                    pd.dismiss();
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.e("TAG", mMessage);
                    pd.dismiss();
                }
            });
            Log.d("MessageResponse",mMessage);
            return mMessage;
        }
        //------------------------------------------------------------
        public  static  void  reset_pass(final  String url,final Context context) {

            final Dialog d = new Dialog(context);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            d.setContentView(R.layout.map_title_layout);
            final TextView message = d.findViewById(R.id.message);
            TextView confirm = d.findViewById(R.id.confirm);
            final EditText number = d.findViewById(R.id.code);
            message.setText(R.string.EntermobnumberAlert);
            d.show();

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (number.getText().toString().isEmpty()) {
                    } else {
                        MediaType MEDIA_TYPE = MediaType.parse("application/json");
                        pd = new ProgressDialog(context);
                        pd.show();
                        OkHttpClient client = new OkHttpClient();
                        JSONObject postdata = new JSONObject();
                        try {
                            postdata.put("mobile", number.getText().toString());
                        } catch (JSONException je) {
                            Log.e("Err", je.getMessage());
                        }
                        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());
                        okhttp3.Request request = new okhttp3.Request.Builder()
                                .url(url)
                                .post(body)
                                .addHeader("Content-Type", "application/json")
                                .header("Authorization", "Bearer " + gettoken(context))
                                .build();

                        client.newCall(request).enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                mMessage = e.getMessage();
                                Log.w("failure Response", mMessage);

                                d.dismiss();
                                pd.dismiss();

                                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")) {
                                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            final Dialog dialog = new Dialog(context);
                                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                            TextView confirm = dialog.findViewById(R.id.confirm);
                                            TextView message = dialog.findViewById(R.id.message);
                                            TextView title = dialog.findViewById(R.id.title);
                                            title.setText(R.string.Null);
                                            message.setText(R.string.check_internet_con);
                                            confirm.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    dialog.cancel();
                                                }
                                            });
                                            dialog.show();

                                        }
                                    });
                                } else {
                                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                                mMessage = response.body().string();
                                Log.e("TAG", mMessage);
                                try {
                                    JSONObject jsonObject = new JSONObject(mMessage);
                                    String success = jsonObject.getString("success");
                                    if (success.equals("true")) {
                                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(context, R.string.SendNewPass, Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    } else {
                                        String message = jsonObject.getString("error");
                                        if (message.equals("We can not find a user with that mobile")) {
                                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    showSweetDialog(context, R.string.ExuseMeAlert, R.string.CantFindNum);
                                                }
                                            });
                                        }
                                    }
                                } catch (JSONException je) {
                                    je.printStackTrace();
                                }
                                d.dismiss();
                                pd.dismiss();
                            }
                        });
                    }
                }
            });
        }
        //---------------------------------------------
        public  static  String  logout(final  String url,final Context context){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd=new ProgressDialog(context);
            pd.show();

            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type","application/json")
                    .addHeader("X-Requested-With","XMLHttpRequest")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage();
                    Log.w("failure Response", mMessage);
                    pd.dismiss();

                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(BeautyMainPage.context);
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                TextView confirm = dialog.findViewById(R.id.confirm);
                                TextView message = dialog.findViewById(R.id.message);
                                TextView title = dialog.findViewById(R.id.title);
                                title.setText(R.string.Null);
                                message.setText(R.string.check_internet_con);
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                                });
                                dialog.show();

                            }
                        });
                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.d("token",gettoken(context));
                    Log.e("TAG", mMessage);
                    pd.dismiss();
                    SharedPreferences.Editor editor = ((AppCompatActivity)context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).edit();
                    editor.clear();
                    editor.commit();
                    Intent intent=new Intent(context, Login.class);
                    Login.logout=true;
                    ((AppCompatActivity) context).finish();
                    context.startActivity(intent);
                }
            });

            Log.d("MessageResponse",mMessage);
            return mMessage;

        }
        //------------------ add to fav----------
        public  static  String  addfav(final  String url,String bdb_type,String bdb_item_id,final Context context){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        pd=new ProgressDialog(context);
        pd.show();
//        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_type",bdb_type);
            postdata.put("bdb_item_id",bdb_item_id);
        }catch (JSONException je){

        }


        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Content-Type","application/json")
//                .addHeader("X-Requested-With","XMLHttpRequest")
                .header("Authorization","Bearer "+gettoken(context))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                pd.dismiss();

                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();

                        }
                    });


                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.d("token",gettoken(context));
                Log.e("TAG", mMessage);
                pd.dismiss();

                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();


                                }
                            });
                            dialog.show();

                        }
                    });


                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }


            }




        });

        Log.d("MessageResponse",mMessage);
        return mMessage;

    }
        //---------------- unfav-------------
        public  static  String  unfav(final  String url,String bdb_type,String bdb_item_id,final Context context){
    MediaType MEDIA_TYPE = MediaType.parse("application/json");
    pd=new ProgressDialog(context);
    pd.show();
//        String url = "http://clientapp.dcoret.com/api/service/Service";
    OkHttpClient client = new OkHttpClient();
    JSONObject postdata = new JSONObject();
    try {
        postdata.put("bdb_type",bdb_type);
        postdata.put("bdb_item_id",bdb_item_id);
    }catch (JSONException je){

    }


    RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

    okhttp3.Request request = new okhttp3.Request.Builder()
            .url(url)
            .post(body)
            .addHeader("Content-Type","application/json")
//                .addHeader("X-Requested-With","XMLHttpRequest")
            .header("Authorization","Bearer "+gettoken(context))
            .build();

    client.newCall(request).enqueue(new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            mMessage = e.getMessage();
            Log.w("failure Response", mMessage);
            pd.dismiss();

            if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final Dialog dialog = new Dialog(BeautyMainPage.context);
                        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                        TextView confirm = dialog.findViewById(R.id.confirm);
                        TextView message = dialog.findViewById(R.id.message);
                        TextView title = dialog.findViewById(R.id.title);
                        title.setText(R.string.Null);
                        message.setText(R.string.check_internet_con);
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                            }
                        });
                        dialog.show();

                    }
                });


            }else {
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                    }
                });
            }
        }

        @Override
        public void onResponse(Call call, okhttp3.Response response) throws IOException {
            mMessage = response.body().string();
            Log.d("token",gettoken(context));
            Log.e("TAG", mMessage);
            pd.dismiss();
        }
    });

    Log.d("MessageResponse",mMessage);
    return mMessage;

}

        //------------- automated Browse ----------------------
        public  static  String  automatedBrowse(final  String url, final String lang, final String itemPerPage, final String pageNum, final Context context){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd=new ProgressDialog(context);
            pd.show();
            OkHttpClient client = new OkHttpClient();
            String temp="{\"lang\":\""+ln+"\",\"ItemPerPage\":8,\"PageNum\":\""+pageNum+"\",\"Filter\":[" +
                    "{\"num\":6,\"value1\":4,\"value2\":0}," +
                    "{\"num\":34,\"value1\":36.47792,\"value2\":0}," +
                    "{\"num\":35,\"value1\":36.23389,\"value2\":0}" +
                    "]" +
                    "}";

            String jsonPostData="{\"lang\":\"en\"," +
                    "\"ItemPerPage\":20," +
                    "\"PageNum\":\""+pageNum+"\"," +
                    "\"Filter\":[" +
                    getCityId()+
                    getFilterList()+  // need to try catch
                    "\t\t\t]\n" +
//                    ",\"sort\":{\"num\":27,\"by\":\"desc\"}\n" +
                    "}";


            Log.e("JSONPOST",jsonPostData);
             final RequestBody body = RequestBody.create(MEDIA_TYPE, jsonPostData);
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type","application/json")
                    .addHeader("Accept","application/json")
                    .addHeader("X-Requested-With","XMLHttpRequest")
                    .header("Authorization","Bearer "+gettoken(context))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage();
                    Log.w("failure Response", mMessage);
                    pd.dismiss();
                    TabOne.pullToRefresh.setRefreshing(false);

                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(BeautyMainPage.context);
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                final TextView confirm = dialog.findViewById(R.id.confirm);
                                TextView message = dialog.findViewById(R.id.message);
                                TextView title = dialog.findViewById(R.id.title);
                                title.setText(R.string.ExuseMeAlert);
                                message.setText(R.string.check_internet_con);
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();

                                    }
                                });
                                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                                Log.e("refreshDialog","ok");
                                                final Dialog refreshDialog = new Dialog(BeautyMainPage.context);
                                                refreshDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                                refreshDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                                refreshDialog.setContentView(R.layout.refresh_btn_dialog);
                                                Button refresh=refreshDialog.findViewById(R.id.refresh);
                                                refresh.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        automatedBrowse(url,lang,itemPerPage,pageNum,context);
                                                        refreshDialog.cancel();
                                                    }
                                                });
                                                refreshDialog.show();
                                    }
                                });
                                dialog.show();

                            }
                        });

                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.e("TAG123", mMessage);
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            TabOne.pullToRefresh.setRefreshing(false);
                        }
                    });


                    try{
                        JSONObject jsonObject=new JSONObject(mMessage);
                        String success=jsonObject.getString("success");
                        Log.e("success",success);
                        String message;
                        try {
                             message=jsonObject.getString("message");
                        }catch (JSONException je){
                            message=jsonObject.getString("error");
                        }
                        if (success.equals("true")){
                            if (message.equals("success get services")){
                            JSONObject data=jsonObject.getJSONObject("data");
                            String totalitem=data.getString("TotalItem");
                            if (totalitem.equals("0")){
                                TabOne.arrayList.clear();
                                ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(BeautyMainPage.context,"there is no suppliered services with your search filters",Toast.LENGTH_LONG).show();
                                        TabOne.refreshRV();
                                    }
                                });

                            }else {
                            JSONArray sersup=data.getJSONArray("sersup");
                            Log.e("SizeSERSUP",sersup.length()+"");
                            TabOne.arrayList.clear();

                            for (int i=0;i<sersup.length();i++){
                                JSONObject jarray = sersup.getJSONObject(i);
                                String bdb_ser_sup_id=jarray.getString("bdb_ser_sup_id"),
                                        bdb_sup_name=jarray.getString("bdb_sup_name"),
                                        bdb_sup_rating=jarray.getString("bdb_sup_rating"),
                                        bdb_emp_rating=jarray.getString("bdb_emp_rating"),
                                        totalRating=jarray.getString("totalRating"),
                                        bdb_ser_home=jarray.getString("bdb_ser_home"),
                                        bdb_ser_hall=jarray.getString("bdb_ser_hall"),
                                        bdb_ser_salon=jarray.getString("bdb_ser_salon"),
                                        bdb_hotel=jarray.getString("bdb_hotel"),
                                        bdb_ser_home_price=jarray.getString("bdb_ser_home_price"),
                                        bdb_ser_hall_price=jarray.getString("bdb_ser_hall_price"),
                                        bdb_ser_salon_price=jarray.getString("bdb_ser_salon_price"),
                                        bdb_hotel_price=jarray.getString("bdb_hotel_price"),
                                        distance=jarray.getString("distance"),
                                        longitude=jarray.getString("longitude"),
                                        latitude=jarray.getString("latitude"),
                                        is_fav_sup=jarray.getString("is_fav_sup"),
                                bdb_booking_period=jarray.getString("bdb_booking_period");
                                BrowseServiceItem bsi = new BrowseServiceItem(bdb_ser_sup_id,
                                                                            bdb_sup_name,
                                                                            bdb_sup_rating,
                                                                            bdb_emp_rating,
                                                                            totalRating,
                                                                            bdb_ser_home,
                                                                            bdb_ser_hall,
                                                                            bdb_ser_salon,
                                                                            bdb_hotel,
                                                                            bdb_ser_home_price,
                                                                            bdb_ser_hall_price,
                                                                            bdb_ser_salon_price,
                                                                            bdb_hotel_price,
                                                                            distance,
                                                                            longitude,
                                                                            latitude,
                                                                            is_fav_sup,
                                                                            bdb_booking_period);
                                Log.e("lat",latitude);
                                Log.e("lng",longitude);
                                TabOne.arrayList.add(bsi);



                            }
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TabOne.refreshRV();
                                    Log.e("ARRAYLIST",TabOne.arrayList.size()+"");
                                    TabOne.recyclerView.invalidate();
                                }
                            });

                            }

                            }else if (message.equals("there is no providers with your search filters")){
                                TabOne.arrayList.clear();
                                ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        TabOne.refreshRV();
                                        Toast.makeText(BeautyMainPage.context,"There is no Provider with your search filter",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }catch (JSONException je){
//                        there is no suppliered services with your search filters
                        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          TabOne.arrayList.clear();
                                          TabOne.refreshRV();
                                      }
                                  });

                        je.printStackTrace();
                    }
                }
            });

            Log.d("MessageResponse",mMessage);
            return mMessage;
        }
        public  static  String  automatedBrowseOffers( final String itemPerPage, final String pageNum, final Context context){
        TabTwo.arrayList.clear();
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        pd=new ProgressDialog(context);
        pd.show();
        OkHttpClient client = new OkHttpClient();
        String temp="{\"lang\":\"en\",\"ItemPerPage\":10,\"PageNum\":1,\"SupplierId\":38,\"Filter\":[\n" +
                "\t{\"num\":7,\"value1\":1} ,  \n" +
                "\t{\"num\":36,\"value1\":40} ,  \n" +
                "\t{\"num\":34,\"value1\":21.1236547} , \n" +
                "\t{\"num\":35,\"value1\":39.1236547}  ,\n" +
                "\t{\"num\":2,\"value2\":1000}\n" +
                "\t]\n" +
                "}";

        String jsonPostData="{\"lang\":\"en\"," +
                "\"ItemPerPage\":20," +
                "\"PageNum\":\""+pageNum+"\"," +
                "\"Filter\":[" +
                "{\"num\":7,\"value1\":1}," +
                getCityId()+
                getFilterList()+  // need to try catch
                "\t\t\t]\n" +
//                    ",\"sort\":{\"num\":27,\"by\":\"desc\"}\n" +
                "}";


        Log.e("JSONPOST",jsonPostData);
        final RequestBody body = RequestBody.create(MEDIA_TYPE, jsonPostData);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/service/offer/automatedBrowse")
                .post(body)
                .addHeader("Content-Type","application/json")
                .addHeader("Accept","application/json")
                .addHeader("X-Requested-With","XMLHttpRequest")
                .header("Authorization","Bearer "+gettoken(context))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                pd.dismiss();
                TabTwo.pullToRefresh.setRefreshing(false);

                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            final TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.ExuseMeAlert);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();

                                }
                            });
                            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                @Override
                                public void onCancel(DialogInterface dialog) {
//
                                    Log.e("refreshDialog","ok");
                                    final Dialog refreshDialog = new Dialog(BeautyMainPage.context);
                                    refreshDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                    refreshDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    refreshDialog.setContentView(R.layout.refresh_btn_dialog);
                                    Button refresh=refreshDialog.findViewById(R.id.refresh);
                                    refresh.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            automatedBrowseOffers(itemPerPage,pageNum,context);
                                            refreshDialog.cancel();
                                        }
                                    });
                                    refreshDialog.show();
                                }
                            });
                            dialog.show();

                        }
                    });

                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
//                    Log.d("token",gettoken(context));
                Log.e("TAG123", mMessage);
                pd.dismiss();
                TabTwo.pullToRefresh.setRefreshing(false);

                try{
                    JSONObject jsonObject=new JSONObject(mMessage);
                    String success=jsonObject.getString("success");
                    Log.e("success",success);
                    String message;
                    try {
                        message=jsonObject.getString("message");
                    }catch (JSONException je){
                        message=jsonObject.getString("error");
                    }
                    if (success.equals("true")){
                        if (message.equals("success get offers")){
                            JSONObject data=jsonObject.getJSONObject("data");
                            String totalitem=data.getString("TotalItem");
                            if (totalitem.equals("0")){
                                TabTwo.arrayList.clear();
                                ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(BeautyMainPage.context,"there is no suppliered services with your search filters",Toast.LENGTH_LONG).show();
                                        TabOne.refreshRV();
                                    }
                                });

                            }else {
                                JSONArray offersArray=data.getJSONArray("offers");
                                Log.e("SizeOffers",offersArray.length()+"");
                                TabTwo.arrayList.clear();

                                for (int i=0;i<offersArray.length();i++){
                                    JSONObject jarray = offersArray.getJSONObject(i);
                                    String bdb_pack_code=jarray.getString("bdb_pack_code"),
                                            bdb_sup_name=jarray.getString("bdb_sup_name"),
                                            totalRating_to_Sup=jarray.getString("totalRating_to_Sup"),
                                            service_count=jarray.getString("service count"),
                                            is_fav_sup=jarray.getString("is_fav_sup"),
                                            bdb_offer_end=jarray.getString("bdb_offer_end"),
                                            num_of_times=jarray.getString("Num_of_times"),
                                            oldPrice=jarray.getString("oldPrice"),
                                            newPrice=jarray.getString("newPrice"),
                                            discount=jarray.getString("discount"),
                                            distance=jarray.getString("distance"),
                                            longitude=jarray.getString("longitude"),
                                            latitude=jarray.getString("latitude");
                                    JSONArray pack_data=jarray.getJSONArray("pack_data");

                                    DataOffer dof = new DataOffer(bdb_pack_code,bdb_sup_name,totalRating_to_Sup,service_count,is_fav_sup,bdb_offer_end,num_of_times,distance,longitude,latitude,oldPrice,newPrice,discount,pack_data);
                                    TabTwo.arrayList.add(dof);

                                }
                                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        TabTwo.refreshRV();
                                        Log.e("ARRAYLIST",TabTwo.arrayList.size()+"");

                                        TabTwo.recyclerView.invalidate();
                                    }
                                });

                            }

                        }else if (message.equals("there is no providers with your search filters")){
                            TabTwo.arrayList.clear();
                            ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TabOne.refreshRV();
                                    Toast.makeText(BeautyMainPage.context,"There is no Provider with your search filter",Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                }catch (JSONException je){
                    je.printStackTrace();
//                        there is no suppliered services with your search filters
                    ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TabTwo.arrayList.clear();
                            TabTwo.refreshRV();
                        }
                    });

                    je.printStackTrace();
                }
            }
        });

        Log.d("MessageResponse",mMessage);
        return mMessage;
    }

    //-----------------------dialogs-----------------------------------
        static Dialog dialog;
        public static void titlemapdialog(final Context context, final String texttitle, final String textmessage, final LatLng latLng, final GoogleMap mMap, final Marker marker, final int flag) {
            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (flag==2 || flag==0){
                        dialog = new Dialog(context);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.map_title_layout);
                        TextView message = dialog.findViewById(R.id.message);
                        final TextView title = dialog.findViewById(R.id.title);
                        title.setText(texttitle);
                        final EditText code = dialog.findViewById(R.id.code);
                        TextView confirm = dialog.findViewById(R.id.confirm);
                        message.setText(textmessage);
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                addtitle( code.getText().toString(),latLng,marker,flag,context);
                            }


                        });
                        dialog.show();
                       }else {
                        marker.showInfoWindow();
                    }
                }

            });

        }
        public static void titlemapdialog(final Context context, final int texttitle, final int textmessage, final LatLng latLng, final GoogleMap mMap, final Marker marker, final int flag) {
            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (flag==2 || flag==0){
                        dialog = new Dialog(context);
                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                        dialog.setContentView(R.layout.map_title_layout);
                        TextView message = dialog.findViewById(R.id.message);
                        final TextView title = dialog.findViewById(R.id.title);
                        title.setText(texttitle);
                        final EditText code = dialog.findViewById(R.id.code);
                        TextView confirm = dialog.findViewById(R.id.confirm);
                        message.setText(textmessage);
                        confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.cancel();
                                addtitle( code.getText().toString(),latLng,marker,flag,context);
                            }


                        });
                        dialog.show();
                       }else {
                        marker.showInfoWindow();
                    }
                }

            });

        }
     static String namelocality;
        private static void addtitle(final String title, final LatLng latLng , final Marker marker, final int flag, final Context context) {

            try {
                marker.setTitle(title);

                Geocoder geocoder=new Geocoder(context);
                List<Address> location=geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                namelocality=location.get(0).getAdminArea();
                Log.e("Location_name",location.get(0).toString());
                Log.e("Location_name",namelocality);
                final Dialog d=new Dialog(context);
                d.setContentView(R.layout.sweet_dialog_layout);
                TextView ok=d.findViewById(R.id.confirm);
                TextView message=d.findViewById(R.id.message);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                TextView et_title=d.findViewById(R.id.title);

                StringBuilder locatioinInfo=new StringBuilder();
                locatioinInfo.append("Country Name: "+location.get(0).getCountryName()+"\n");
                locatioinInfo.append("City Name: "+location.get(0).getAdminArea()+"\n");
                locatioinInfo.append("Area: "+location.get(0).getSubAdminArea()+"\n");
                locatioinInfo.append("Street: "+location.get(0).getFeatureName()+"\n");
                message.setText(locatioinInfo.toString());
                if (flag==0) {
                    et_title.setText("Do You want to Add This Location ?");
                }else {
                    et_title.setText("Do You want to edit This Location with?");
                }
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.cancel();
                        if (flag==0) {
                            addAddress("http://clientapp.dcoret.com/api/auth/user/addAddress", latLng.longitude + ""
                                    , latLng.latitude + "", namelocality, title, context);
                        }else {
                            SharedPreferences preferences=((AppCompatActivity)context).getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
//                           String data=preferences.getString("addressUser",null);
                            getdetailsUser("http://clientapp.dcoret.com/api/auth/user/detailsUser",preferences.getString("bdb_name",null),
                                    preferences.getString("bdb_email",null), preferences.getString("bdb_mobile",null),namelocality,title,latLng,BeautyMainPage.context);
                        }
                    }
                });
            d.show();


            }catch (NullPointerException ne){
                showSweetDialog(context,context.getResources().getString(R.string.ExuseMeAlert),"Please Selecte Location on map");
            }catch (Exception e){
                if (flag==0)
                marker.remove();
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context,"There is an Erorr,Please Try Again Later!",Toast.LENGTH_LONG).show();
                    }
                });
                e.printStackTrace();
            }

        }
        public  static  void showSweetDialog(Context context,String texttitle,String textmessage){

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.sweet_dialog_layout);
        TextView message = dialog.findViewById(R.id.message);
        TextView title = dialog.findViewById(R.id.title);
        TextView confirm = dialog.findViewById(R.id.confirm);
    //                TextView resend_code = dialog.findViewById(R.id.resend_code);
        title.setText(texttitle);
        message.setText(textmessage);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();

    }
        public  static  void showSweetDialog(Context context,int texttitle,int textmessage){
                   final Dialog dialog = new Dialog(context);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.sweet_dialog_layout);
            TextView message = dialog.findViewById(R.id.message);
            TextView title = dialog.findViewById(R.id.title);
            TextView confirm = dialog.findViewById(R.id.confirm);
            //                TextView resend_code = dialog.findViewById(R.id.resend_code);
            title.setText(texttitle);
            message.setText(textmessage);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
            dialog.show();

    }
        public static void showSweetDialog(final Context context, final String texttitle, final String textmessage, final String activation_number , final Boolean iscode, final String number) {
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (iscode) {
                    dialog = new Dialog(context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.confirm_code_layout);
                    TextView message = dialog.findViewById(R.id.message);
                    TextView title = dialog.findViewById(R.id.title);
                    title.setText(texttitle);
                    final EditText code = dialog.findViewById(R.id.code);
                    TextView confirm = dialog.findViewById(R.id.confirm);
                    TextView resend_code = dialog.findViewById(R.id.resend_code);
                    message.setText(textmessage);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            //----------toast for congratulations---------
                            activeAccount("http://clientapp.dcoret.com/api/auth/user/register/activate",
                                    code.getText().toString(),
                                    context);
                        }
                    });
                    dialog.show();
                } else {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.sweet_dialog_layout);
                    TextView message = dialog.findViewById(R.id.message);
                    TextView title = dialog.findViewById(R.id.title);
                    TextView confirm = dialog.findViewById(R.id.confirm);
                    title.setText(texttitle);
                    message.setText(textmessage);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            }
        });

    }
        public static void showSweetDialog(final Context context, final int texttitle, final int textmessage, final String activation_number , final Boolean iscode, final String number) {
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (iscode) {
                    dialog = new Dialog(context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.confirm_code_layout);
                    TextView message = dialog.findViewById(R.id.message);
                    TextView title = dialog.findViewById(R.id.title);
                    title.setText(texttitle);
                    final EditText code = dialog.findViewById(R.id.code);
                    TextView confirm = dialog.findViewById(R.id.confirm);
                    TextView resend_code = dialog.findViewById(R.id.resend_code);
                    message.setText(textmessage);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                            //----------toast for congratulations---------
                            activeAccount("http://clientapp.dcoret.com/api/auth/user/register/activate",
                                    code.getText().toString(),
                                    context);
                        }
                    });
                    dialog.show();
                } else {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.sweet_dialog_layout);
                    TextView message = dialog.findViewById(R.id.message);
                    TextView title = dialog.findViewById(R.id.title);
                    TextView confirm = dialog.findViewById(R.id.confirm);
                    title.setText(texttitle);
                    message.setText(textmessage);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            }
        });

    }
        public static void showSweetDialog(final Context context, final int texttitle, final int textmessage, final Boolean iscode) {
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (iscode) {
                    dialog = new Dialog(context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.confirm_code_layout);
                    TextView message = dialog.findViewById(R.id.message);
                    TextView title = dialog.findViewById(R.id.title);
                    title.setText(texttitle);
                    final EditText code = dialog.findViewById(R.id.code);
                    TextView confirm = dialog.findViewById(R.id.confirm);
                    TextView resend_code = dialog.findViewById(R.id.resend_code);
                    message.setText(textmessage);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            dialog.cancel();
                            //----------toast for congratulations---------
                            activeAccount("http://clientapp.dcoret.com/api/auth/user/register/activate",
                                    code.getText().toString(),
                                    context);
                        }
                    });
                    dialog.show();
                } else {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.sweet_dialog_layout);
                    TextView message = dialog.findViewById(R.id.message);
                    TextView title = dialog.findViewById(R.id.title);
                    TextView confirm = dialog.findViewById(R.id.confirm);
                    //                      TextView resend_code = dialog.findViewById(R.id.resend_code);
                    title.setText(texttitle);
                    message.setText(textmessage);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();
                }
            }
        });

    }
        public static void checkInternetConnectionDialog(final Context context, final int texttitle, final int textmessage) {
        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                    final Dialog dialog = new Dialog(context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                    TextView confirm = dialog.findViewById(R.id.confirm);
                    TextView message = dialog.findViewById(R.id.message);
                    TextView title = dialog.findViewById(R.id.title);
                    message.setText(textmessage);
                    title.setText(texttitle);
                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                    dialog.show();

                }

        });

    }



    public  static  void showSweetDialogOnBookingDone(final Context context){

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.sweet_dialog_layout_on_booking_done);
        TextView message = dialog.findViewById(R.id.message);
//        TextView title = dialog.findViewById(R.id.title);
        Button services = dialog.findViewById(R.id.service);
        Button finishBooking = dialog.findViewById(R.id.finishbooking);
        //                TextView resend_code = dialog.findViewById(R.id.resend_code);
//        title.setText(texttitle);
//        message.setText(textmessage);
        services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                ((AppCompatActivity)context).onBackPressed();
            }
        });

        finishBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"Service Reserved",Toast.LENGTH_SHORT).show();
                ((AppCompatActivity) context).finish();
                Intent intent=new Intent(context,BeautyMainPage.class);
                intent.putExtra("tabselected","bag");
                context.startActivity(intent);
            }
        });
        dialog.show();

    }

    public static boolean matching(String orig, String compare){
            String md5 = null;
            try{
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(compare.getBytes());
                byte[] digest = md.digest();
                md5 = new BigInteger(1, digest).toString(16);
                Log.e("MD5",md5);
                return md5.equals(orig);

            } catch (NoSuchAlgorithmException e) {
                return false;
            }


        }
        public static String getMD5EncryptedValue(String password) {
            final byte[] defaultBytes = password.getBytes();
            try {
                final MessageDigest md5MsgDigest = MessageDigest.getInstance("MD5");
                md5MsgDigest.reset();
                md5MsgDigest.update(defaultBytes);
                final byte messageDigest[] = md5MsgDigest.digest();
                final StringBuffer hexString = new StringBuffer();
                for (final byte element : messageDigest) {
                    final String hex = Integer.toHexString(0xFF & element);
                    if (hex.length() == 1) {
                        hexString.append('0');
                    }
                    hexString.append(hex);
                }
                password = hexString + "";
            } catch (final NoSuchAlgorithmException nsae) {
                nsae.printStackTrace();
            }
            return password;
        }
        public  static  boolean validationPassword(String pass){

           if (pass.length()>=6 &&pass.length()<=10){
               Log.e("length",pass.matches(".*[A-Z].*")+"");
               if (pass.matches(".*[A-Z].*")){
                   Log.e("A-Z","ok");
                   if(pass.matches(".*[0-9].*")){
                       Log.e("0-9","ok");
                       return  true;
               }
           }
           }

        return false;
        }

        //---------------- filter and sort model --------------------
        static String city;
        static double latt,lang;
        public static  void setCityId(int id){
            city= "" +
//                    "{\"num\":6,\"value1\":"+id+",\"value2\":0},"+
                    "\t{\"num\":34,\"value1\":36.47792,\"value2\":0},\n" +
                    "\t{\"num\":35,\"value1\":36.23389,\"value2\":0}";
        }

        public static String getCityId(){
            setCityId(1);
            return city;
        }

        public static void setlocation(double lat,double lng){
            latt=lat;
            lang=lng;
        }

        public static String getLatLng(){
            LatLng latLng=new LatLng(latt,lang);
            return  "\t{\"num\":34,\"value1\":"+latLng.latitude+",\"value2\":0},\n" +
                    "\t{\"num\":35,\"value1\":"+latLng.longitude+",\"value2\":0} ";
        }
        public static String getFilterList(){
            String filter=",";
            APICall.filterSortAlgorithm("33", "", "");

            for (int i=0;i<ServiceFragment.filterList.size();i++){

                if (!ServiceFragment.filterList.get(i).getNum().equals("")){
                filter=filter + "{\"num\":"+ServiceFragment.filterList.get(i).getNum()+",\"value1\":"+ServiceFragment.filterList.get(i).getValue1()+",\"value2\":"+ServiceFragment.filterList.get(i).getValue2()+"} ,";
                }
            }
            Log.e("STRINGFILTER",filter+"");
            try {
              filter=filter.substring(0,filter.length()-1);
                Log.e("STRINGFILTERAfter",filter);

            }catch (Exception e){
                e.printStackTrace();
            }
        return filter;
        }
        static  void initFilterList(){
             if (ServiceFragment.filterList.size()==0)
             for (int i=0;i<=35;i++){
                 ServiceFragment.filterList.add(new FilterAndSortModel("","",""));
             }
             Log.e("numberOFFilterList",ServiceFragment.filterList.size()+"");
         }
        public static void  filterSortAlgorithm(String num,String value1,String value2 ){
            initFilterList();
                int index = Integer.parseInt(num);
                if(!value1.equals(""))
                    ServiceFragment.filterList.set(index, new FilterAndSortModel(num, value1, value2));
                else
                    ServiceFragment.filterList.set(index, new FilterAndSortModel("", "", ""));

                Log.e("index",ServiceFragment.filterList.get(index).getNum()+":"+ServiceFragment.filterList.get(index).getValue1()+":"+ServiceFragment.filterList.get(index).getValue2()+"");

            }
        public  static void clearFilterList(){
                ServiceFragment.filterList.clear();
            }



         public    static void getdetailsUser(final Context context){
                String token = ((AppCompatActivity) context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("token", null);
                MediaType MEDIA_TYPE = MediaType.parse("application/json");
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd = new ProgressDialog(context);
                    }
                });

                OkHttpClient client = new OkHttpClient();
                JSONObject postdata = new JSONObject();

                RequestBody body = RequestBody.create(MEDIA_TYPE, "");

                okhttp3.Request request = new okhttp3.Request.Builder()
                        .url("http://clientapp.dcoret.com/api/auth/user/detailsUser")
                        .post(body)
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("X-Requested-With", "XMLHttpRequest")
                        .header("Authorization", "Bearer " + token)
                        //                .header("Content-Type", "application/json")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        mMessage = e.getMessage().toString();
                        Log.w("failure Response", mMessage);
                        pd.dismiss();


                        if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                            ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final Dialog dialog = new Dialog(BeautyMainPage.context);
                                    dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                    TextView confirm = dialog.findViewById(R.id.confirm);
                                    TextView message = dialog.findViewById(R.id.message);
                                    final TextView title = dialog.findViewById(R.id.title);
                                    title.setText(R.string.Null);
                                    message.setText(R.string.check_internet_con);
                                    confirm.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            dialog.cancel();

                                            final Dialog refreshDialog = new Dialog(BeautyMainPage.context);
                                            refreshDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                            refreshDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                            refreshDialog.setContentView(R.layout.refresh_btn_dialog);
                                            Button refresh=refreshDialog.findViewById(R.id.refresh);
                                            refresh.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    refreshDialog.cancel();
                                                    getdetailsUser(context);
                                                }
                                            });
                                            refreshDialog.show();
                                        }
                                    });
                                    dialog.show();

                                }
                            });

                        }else {
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                                }
                            });
                        }

                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        SharedPreferences.Editor editor=((AppCompatActivity)context).getSharedPreferences("LOGIN",Context.MODE_PRIVATE).edit();
                        mMessage = response.body().string();
                        pd.dismiss();
                        Log.e("TAG", mMessage);
                        try {
                            JSONObject jsonObject = new JSONObject(mMessage);
                            String success = jsonObject.getString("success");
                            if (success.equals("true")) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                String bdb_name = data.getString("bdb_name");
//                            e_bdb_name.setText(bdb_name);
                                String bdb_email = data.getString("bdb_email");
//                            e_bdb_email.setText(bdb_email);
                                String bdb_mobile = data.getString("bdb_mobile");
//                                JSONArray addressUser=jsonObject.getJSONArray("address");
//                            e_bdb_mobile.setText(bdb_mobile);

                                editor.putString("bdb_name", bdb_name);
                                editor.putString("bdb_email", bdb_email);
                                editor.putString("bdb_mobile", bdb_mobile);
                                editor.putString("addressUser", mMessage+"");
                                editor.apply();
                                editor.commit();
                                try {
//                                    Log.e("BDB_title_INFO",titleInfo);
//                                    Log.e("BDB_title_INFO",namelocality);
//                                JSONObject jsonObject1 = new JSONObject(data);
                                    AccountFragment.locationTitles.clear();
                                    JSONArray addressUser=jsonObject.getJSONArray("address");
                                    for (int i=0;i<addressUser.length();i++) {
                                        JSONObject address = addressUser.getJSONObject(i);
                                        String bdb_id = address.getString("bdb_id");
                                        String bdb_user_id = address.getString("bdb_user_id");
                                        String bdb_city_id = address.getString("bdb_city_id");
                                        String bdb_loc_lat = address.getString("bdb_loc_lat");
                                        String bdb_sup_id = address.getString("bdb_sup_id");
                                        String bdb_descr = address.getString("bdb_descr");
                                        String bdb_loc_long = address.getString("bdb_loc_long");
                                        String bdb_is_deleted = address.getString("bdb_is_deleted");
                                        String bdb_my_descr = address.getString("bdb_my_descr");
                                        LatLng ltlng = new LatLng(Double.parseDouble(bdb_loc_lat), Double.parseDouble(bdb_loc_long));
                                        AccountFragment.locationTitles.add(new LocationTitles(bdb_id, ltlng, bdb_my_descr, bdb_user_id, bdb_city_id, bdb_sup_id, bdb_descr));

                                       Log.e("LTAccFrag", AccountFragment.locationTitles.get(i).getId() + " : " + AccountFragment.locationTitles.get(i).getBdb_my_descr());
                                    }

                                    Log.e("Locations",AccountFragment.locationTitles.toString());
                                }catch (JSONException je){
                                    je.printStackTrace();
                                }
                            }
                        } catch (JSONException e) {

                        }

                        pd.dismiss();

                    }

                });

                Log.d("MessageResponse", mMessage);
            }

         static void getdetailsUser(final  String url, final String  e_bdb_name, final String e_bdb_email, final String e_bdb_mobile, final String namelocality, final String titleInfo, final LatLng latLng, final Context context){
        final SharedPreferences.Editor editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE).edit();
        SharedPreferences sh=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
            String token = ((AppCompatActivity) context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("token", null);
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd = new ProgressDialog(context);
            pd.show();
            //        String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();

            RequestBody body = RequestBody.create(MEDIA_TYPE, "");

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .header("Authorization", "Bearer " + token)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
                    pd.dismiss();


                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(BeautyMainPage.context);
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                TextView confirm = dialog.findViewById(R.id.confirm);
                                TextView message = dialog.findViewById(R.id.message);
                                final TextView title = dialog.findViewById(R.id.title);
                                title.setText(R.string.Null);
                                message.setText(R.string.check_internet_con);
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();

                                        final Dialog refreshDialog = new Dialog(BeautyMainPage.context);
                                        refreshDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                        refreshDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                        refreshDialog.setContentView(R.layout.refresh_btn_dialog);
                                        Button refresh=refreshDialog.findViewById(R.id.refresh);
                                        refresh.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                refreshDialog.cancel();
                                                getdetailsUser(url,e_bdb_name,e_bdb_email,e_bdb_mobile,namelocality,titleInfo,latLng,context);
                                            }
                                        });
                                        refreshDialog.show();
                                    }
                                });
                                dialog.show();

                            }
                        });


                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    pd.dismiss();
                    Log.e("TAG", mMessage);
                    try {
                        JSONObject jsonObject = new JSONObject(mMessage);
                        String success = jsonObject.getString("success");
                        if (success.equals("true")) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            String bdb_name = data.getString("bdb_name");
//                            e_bdb_name.setText(bdb_name);
                            String bdb_email = data.getString("bdb_email");
//                            e_bdb_email.setText(bdb_email);
                            String bdb_mobile = data.getString("bdb_mobile");
//                                JSONArray addressUser=jsonObject.getJSONArray("address");
//                            e_bdb_mobile.setText(bdb_mobile);
                            editor.putString("bdb_name", bdb_name);
                            editor.putString("bdb_email", bdb_email);
                            editor.putString("bdb_mobile", bdb_mobile);
                            editor.putString("addressUser", mMessage+"");
                            editor.apply();
                            editor.commit();
                            try {
                                Log.e("BDB_title_INFO",titleInfo);
                                Log.e("BDB_title_INFO",namelocality);

//                                JSONObject jsonObject1 = new JSONObject(data);
                                JSONArray addressUser=jsonObject.getJSONArray("address");
                                Double lat,lang;
                                for (int i=0;i<addressUser.length();i++){
                                    JSONObject adr=addressUser.getJSONObject(i);
                                    lat=adr.getDouble("bdb_loc_lat");
                                    lang=adr.getDouble("bdb_loc_long");
                                    if (latLng.latitude==lat && latLng.longitude==lang){
                                        String add_id= adr.getString("bdb_id");
                                        Log.e("BDB_title_INFO",titleInfo);
                                        pd.dismiss();
                                        updateaddress("http://clientapp.dcoret.com/api/auth/user/updateAddress",latLng.longitude+""
                                                ,latLng.latitude+"",add_id,namelocality,titleInfo,context);
                                        break;
                                    }
                                }
                            }catch (JSONException je){
                                je.printStackTrace();
                            }



                        }
                    } catch (JSONException e) {

                    }

                    pd.dismiss();

                }

            });

            Log.d("MessageResponse", mMessage);
        }

        //----------------search bookin API--------
//    "bdb_ser_sup_id":15,
//            "bdb_start_date":"2019-07-15",
//            "bdb_start_time":"13:00"
        public  static  void  searchBooking(final String bdb_ser_sup_id,String bdb_start_date ,final Context context){

            IndividualBooking.listHashMap.clear();
           IndividualBooking.listDataHeader.clear();


            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd=new ProgressDialog(context);
            pd.show();
//        String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try {

                postdata.put("bdb_ser_sup_id", bdb_ser_sup_id);
                postdata.put("bdb_start_date", bdb_start_date);
//            postdata.put("password", "12345");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://clientapp.dcoret.com/api/booking/searchBooking")
                    .post(body)
                    .addHeader("Content-Type","application/json")
                    .header("Authorization", "Bearer "+gettoken(context))
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
                    pd.dismiss();


                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(BeautyMainPage.context);
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                TextView confirm = dialog.findViewById(R.id.confirm);
                                TextView message = dialog.findViewById(R.id.message);
                                TextView title = dialog.findViewById(R.id.title);
                                title.setText(R.string.Null);
                                message.setText(R.string.check_internet_con);
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();
                                    }
                                });
                                dialog.show();

                            }
                        });

                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.e("SearchBooking", mMessage);
                    pd.dismiss();
                    try {
                        JSONObject j=new JSONObject(mMessage);
                        String success=j.getString("success");
                        if (success.equals("true"))
                        {
                            JSONArray times=j.getJSONArray("data");
                            IndividualBooking.layoutLists.clear();
                            IndividualBooking.empid.clear();
                            for (int i=0;i<times.length();i++){
                                ArrayList timelist=new ArrayList();
                                JSONObject timesItem=times.getJSONObject(i);
                                String sup_id=timesItem.getString("sup_id");
                                IndividualBooking.empid.add(sup_id);
                                String sup_name=timesItem.getString("sup_name");
                                //--------------- add Name Emp to list------------
//                                IndividualBooking.listDataHeader.add(sup_name);
                                String avaliablity=timesItem.getString("avaliablity1");
//                                if (avaliablity.equals("1")){
                                    JSONArray freeTimes=timesItem.getJSONArray("freeTimes");
                                ArrayList a=new ArrayList();
                                for (int k=0;k<freeTimes.length();k++){
                                        JSONObject actualTimeItem=freeTimes.getJSONObject(k);
                                        Log.e("Free Times",actualTimeItem.toString());
                                        String from=actualTimeItem.getString("from");
                                        String to=actualTimeItem.getString("to");
                                        Log.e("SizeALIST",a.size()+"");

                                        ArrayList atmp=IndividualBooking.splitTime(from,to);
                                        timelist.addAll(atmp);
                                        Log.e("atmp",atmp+"");
                                        a.addAll(atmp);
                                        IndividualBooking.alltimes.addAll(a);

                                }
                                Log.e("timelist",timelist+"");
                                Log.e("empid",IndividualBooking.empid+"");
                                IndividualBooking.addLayout(sup_name,avaliablity,timelist);

//                                IndividualBooking.listHashMap.put(IndividualBooking.listDataHeader.get(i),a);
                                //--------- remove iteration--------------
                                for (int c=0;c<IndividualBooking.alltimes.size();c++){
                                        for (int d=c+1;d<IndividualBooking.alltimes.size();d++)
                                    if(IndividualBooking.alltimes.get(c).equals(IndividualBooking.alltimes.get(d))){
                                        IndividualBooking.alltimes.remove(d);
                                    }
                                }


//                                }else {
//                                    ArrayList a=new ArrayList();
//                                    a.add("0");
//                                    IndividualBooking.listHashMap.put(IndividualBooking.listDataHeader.get(i),a);

//                                    IndividualBooking.addLayout(sup_name,a);

//                                }
                            }
//                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    IndividualBooking.listAdapter.notifyDataSetChanged();
//                                    IndividualBooking.listView.setAdapter(IndividualBooking.listAdapter);
//                                }
//                            });


                        }
                    }catch (final JSONException je){
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }

            });
//        Log.d("MessageResponse",mMessage);
        }
        public  static  void  searchBooking1(final String bdb_ser_sup_id,String bdb_start_date,String bdb_start_time ,final Context context){

        IndividualBooking.listHashMap.clear();
        IndividualBooking.listDataHeader.clear();

        bdb_start_date=  "2019-07-15";

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        pd=new ProgressDialog(context);
        pd.show();
//        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {

            postdata.put("bdb_ser_sup_id", bdb_ser_sup_id);
            postdata.put("bdb_start_date", bdb_start_date);
            postdata.put("bdb_start_time", bdb_start_time);
//            postdata.put("password", "12345");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/searchBooking")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
                .build();

        final String finalBdb_start_date = bdb_start_date;
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                pd.dismiss();


                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();

                        }
                    });

                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("SearchBooking", mMessage);
                pd.dismiss();
                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true"))
                    {
                            IndividualBooking.empid.clear();
                            JSONArray times=j.getJSONArray("data");
                        for (int i=0;i<times.length();i++){
                            JSONObject timesItem=times.getJSONObject(i);
                            String sup_id=timesItem.getString("sup_id");
                            IndividualBooking.empid.add(sup_id);
                            String sup_name=timesItem.getString("sup_name");
                            //--------------- add Name Emp to list------------
//                            IndividualBooking.listDataHeader.add(sup_name);

                                String avaliablity=timesItem.getString("avaliablity");
//                                if (avaliablity.equals("1")){
//                            JSONArray freeTimes=timesItem.getJSONArray("freeTimes");
//                            ArrayList a=new ArrayList();
//                            for (int k=0;k<freeTimes.length();k++){
//                                JSONObject actualTimeItem=freeTimes.getJSONObject(k);
//                                Log.e("Free Times",actualTimeItem.toString());
//                                String from=actualTimeItem.getString("from");
//                                String to=actualTimeItem.getString("to");
//                                Log.e("SizeALIST",a.size()+"");
//                                ArrayList atmp=IndividualBooking.splitTime(from,to);
//                                a.addAll(atmp);
////                                IndividualBooking.addLayout(sup_name,a);
//
////                                        IndividualBooking.addLayout(sup_name,a);
//                            }
                            IndividualBooking.addLayout(sup_name,avaliablity);

                            Log.e("SIZEALLTIMES",IndividualBooking.alltimes.size()+"");
                            Log.e("empid",IndividualBooking.empid+"");

//                            IndividualBooking.listHashMap.put(IndividualBooking.listDataHeader.get(i),a);

//                                }else {
//                                    ArrayList a=new ArrayList();
//                                    a.add("0");
//                                    IndividualBooking.listHashMap.put(IndividualBooking.listDataHeader.get(i),a);

//                                    IndividualBooking.addLayout(sup_name,a);

//                                }
                        }


//                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                IndividualBooking.listAdapter.notifyDataSetChanged();
//                                IndividualBooking.listView.setAdapter(IndividualBooking.listAdapter);
//                            }
//                        });



                    }
                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });
//        Log.d("MessageResponse",mMessage);
    }
    //   ------------------ rating the app =-------------------------
        public  static  void  getCart(final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        pd=new ProgressDialog(context);
        BagReservationFragment.pullToRefresh.setRefreshing(true);
        pd.show();
//        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();


        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/getCart")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
//                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        BagReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();

                                }
                            });
                            dialog.show();

                        }
                    });

                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        BagReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });


                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true"))
                    {
                        JSONArray data=j.getJSONArray("data");
                        for (int i=0;i<data.length();i++){
                            JSONObject items=data.getJSONObject(i);
                            String bdb_id=items.getString("bdb_id"),
                                    bdb_ser_sup_id=items.getString("bdb_ser_sup_id"),
                                    bdb_price=items.getString("bdb_price"),
                                    bdb_start_date=items.getString("bdb_start_date"),
                                    bdb_start_time=items.getString("bdb_start_time"),
                                    supplier_name=items.getString("supplier_name"),
                                    bdb_service_name_ar=items.getString("bdb_service_name_ar"),
                                    bdb_service_name_en=items.getString("bdb_service_name_en"),
                                    bdb_ser_hotel=items.getString("bdb_ser_hotel"),
                                    bdb_ser_hall=items.getString("bdb_ser_hall"),
                                    bdb_ser_home=items.getString("bdb_ser_home"),
                                    bdb_ser_salon=items.getString("bdb_ser_salon");
                            BagReservationFragment.getCarts.add(new GetCart(
                                     bdb_id
                                    ,bdb_ser_sup_id
                                    ,bdb_price
                                    ,bdb_start_date
                                    ,bdb_start_time
                                    ,supplier_name
                                    ,bdb_service_name_ar
                                    ,bdb_service_name_en
                                    ,bdb_ser_hotel
                                    ,bdb_ser_hall
                                    ,bdb_ser_home
                                    ,bdb_ser_salon
                            ));
                    }
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                BagReservationFragment.bagReservationAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });
//        Log.d("MessageResponse",mMessage);
    }

         public  static  void  getCart(final Context context,Boolean refresh){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
//        pd=new ProgressDialog(context);
//        BagReservationFragment.pullToRefresh.setRefreshing(false);
//        pd.show();
//        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();


        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/getCart")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
//                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        pd.dismiss();
//                        BagReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();

                                }
                            });
                            dialog.show();

                        }
                    });

                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        pd.dismiss();
//                        BagReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });


                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true"))
                    {
                        BagReservationFragment.getCarts.clear();
                        JSONArray data=j.getJSONArray("data");
                        for (int i=0;i<data.length();i++){
                            JSONObject items=data.getJSONObject(i);
                            String bdb_id=items.getString("bdb_id"),
                                    bdb_ser_sup_id=items.getString("bdb_ser_sup_id"),
                                    bdb_price=items.getString("bdb_price"),
                                    bdb_start_date=items.getString("bdb_start_date"),
                                    bdb_start_time=items.getString("bdb_start_time"),
                                    supplier_name=items.getString("supplier_name"),
                                    bdb_service_name_ar=items.getString("bdb_service_name_ar"),
                                    bdb_service_name_en=items.getString("bdb_service_name_en"),
                                    bdb_ser_hotel=items.getString("bdb_ser_hotel"),
                                    bdb_ser_hall=items.getString("bdb_ser_hall"),
                                    bdb_ser_home=items.getString("bdb_ser_home"),
                                    bdb_ser_salon=items.getString("bdb_ser_salon");
                            BagReservationFragment.getCarts.add(new GetCart(
                                    bdb_id
                                    ,bdb_ser_sup_id
                                    ,bdb_price
                                    ,bdb_start_date
                                    ,bdb_start_time
                                    ,supplier_name
                                    ,bdb_service_name_ar
                                    ,bdb_service_name_en
                                    ,bdb_ser_hotel
                                    ,bdb_ser_hall
                                    ,bdb_ser_home
                                    ,bdb_ser_salon
                            ));
                        }
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                BagReservationFragment.bagReservationAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });
//        Log.d("MessageResponse",mMessage);
    }

        //--------------get service time then addcart---------------
        public  static  void  getServiceTime(final String bdb_ser_sup_id, final String bdb_employee_id, final String bdb_price, final String bdb_ser_salon, final String bdb_ser_home, final String bdb_ser_hall, final String bdb_ser_hotel, final String bdb_start_date, final String bdb_start_time, final Context context){




            MediaType MEDIA_TYPE = MediaType.parse("application/json");
    //        pd=new ProgressDialog(context);
    //        BagReservationFragment.pullToRefresh.setRefreshing(true);
    //        pd.show();
    //        String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try{
                postdata.put("bdb_ser_sup_id",bdb_ser_sup_id);
            }catch (JSONException e){
                e.printStackTrace();
            }
            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://clientapp.dcoret.com/api/service/index")
                    .post(body)
                    .addHeader("Content-Type","application/json")
                    .header("Authorization", "Bearer "+gettoken(context))
    //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
    //                pd.dismiss();
                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(BeautyMainPage.context);
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                TextView confirm = dialog.findViewById(R.id.confirm);
                                TextView message = dialog.findViewById(R.id.message);
                                TextView title = dialog.findViewById(R.id.title);
                                title.setText(R.string.Null);
                                message.setText(R.string.check_internet_con);
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();

                                    }
                                });
                                dialog.show();

                            }
                        });

                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.e("TAG", mMessage);
    //                pd.dismiss();
    //                BagReservationFragment.pullToRefresh.setRefreshing(false);

                    try {
                        JSONObject j=new JSONObject(mMessage);
                        String success=j.getString("success");
                        if (success.equals("true"))
                        {
                            JSONObject data=j.getJSONObject("data");
                            Log.e("bdb_time:",data.toString());
                            String bdb_time=data.getString("bdb_time");
                            Log.e("bdb_time:",bdb_time);
                            APICall.addCart(bdb_ser_sup_id,bdb_employee_id,bdb_price,bdb_ser_salon,bdb_ser_home,bdb_ser_hall,bdb_ser_hotel,bdb_start_date,bdb_start_time,bdb_time,context);
                        }

                    }catch (final JSONException je){
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                je.printStackTrace();

                                Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }

            });
    //        Log.d("MessageResponse",mMessage);
        }
        public  static  void  addCart(String bdb_ser_sup_id,String bdb_employee_id, String bdb_price, String bdb_ser_salon,String bdb_ser_home,String bdb_ser_hall,String bdb_ser_hotel,String bdb_start_date,String bdb_start_time,String bdb_time,final Context context){
        Log.e("bdb_ser_sup_id",bdb_ser_sup_id);
        Log.e("bdb_employee_id",bdb_employee_id);
        Log.e("bdb_price",bdb_price);
        Log.e("bdb_ser_salon",bdb_ser_salon);
        Log.e("bdb_ser_home",bdb_ser_home);
        Log.e("bdb_ser_hall",bdb_ser_hall);
        Log.e("bdb_ser_hotel",bdb_ser_hotel);
        Log.e("bdb_start_date",bdb_start_date);
        Log.e("bdb_start_time",bdb_start_time);

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd=new ProgressDialog(context);
                pd.show();
            }
        });

//        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
                postdata.put("bdb_ser_sup_id",bdb_ser_sup_id);
                postdata.put("bdb_employee_id",bdb_employee_id);
                postdata.put("bdb_price",bdb_price);
                postdata.put("bdb_ser_salon",bdb_ser_salon);
                postdata.put("bdb_ser_home",bdb_ser_home);
                postdata.put("bdb_ser_hall",bdb_ser_hall);
                postdata.put("bdb_ser_hotel",bdb_ser_hotel);
                postdata.put("bdb_start_date",bdb_start_date);
                postdata.put("bdb_start_time",bdb_start_time);
                postdata.put("bdb_time",bdb_time);
        }catch (Exception e){
            e.printStackTrace();
        }


        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/addToCart")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
//                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                pd.dismiss();
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();

                        }
                    });

                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("TAG", mMessage);
                pd.dismiss();
                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
//                    if (success.equals("true")){

                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                APICall.showSweetDialogOnBookingDone(context);
//                                Toast.makeText(context,"Service Reserved",Toast.LENGTH_SHORT).show();
//                                ((AppCompatActivity) context).finish();
//                                Intent intent=new Intent(context,BeautyMainPage.class);
//                                intent.putExtra("tabselected","bag");
//                                context.startActivity(intent);
                            }
                        });
//                    }


                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });
//        Log.d("MessageResponse",mMessage);
    }
        //    http://clientapp.dcoret.com/api/booking/moveCartToBooking
        public  static  void  moveCartToBooking(String bdb_id, final int postion, final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd=new ProgressDialog(context);
                pd.show();
            }
        });

    //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_id",bdb_id);
        }catch (Exception e){
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/moveCartToBooking")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
    //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                pd.dismiss();
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();
                                }
                            });
                            dialog.show();

                        }
                    });

                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("TAG", mMessage);
                pd.dismiss();
                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true")){
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Success Reserve", Toast.LENGTH_LONG).show();
                                BagReservationFragment.getCarts.remove(postion);
                                BagReservationFragment.bagReservationAdapter.notifyDataSetChanged();
                            }
                        });

                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
                            }
                        });

                    }


                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });
    //        Log.d("MessageResponse",mMessage);
    }

        public  static  void  deletFromCart(String bdb_id, final int postion, final Context context){

            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pd=new ProgressDialog(context);
                    BagReservationFragment.pullToRefresh.setRefreshing(true);
                    pd.show();
                }
            });

    //        String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try {
                postdata.put("bdb_id",bdb_id);
            }catch (Exception e){
                e.printStackTrace();
            }


            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://clientapp.dcoret.com/api/booking/deleteFromCart")
                    .post(body)
                    .addHeader("Content-Type","application/json")
                    .header("Authorization", "Bearer "+gettoken(context))
    //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            BagReservationFragment.pullToRefresh.setRefreshing(false);
                        }
                    });

                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(BeautyMainPage.context);
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                TextView confirm = dialog.findViewById(R.id.confirm);
                                TextView message = dialog.findViewById(R.id.message);
                                TextView title = dialog.findViewById(R.id.title);
                                title.setText(R.string.Null);
                                message.setText(R.string.check_internet_con);
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();

                                    }
                                });
                                dialog.show();

                            }
                        });

                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.e("TAG", mMessage);
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            BagReservationFragment.pullToRefresh.setRefreshing(false);
                        }
                    });


                    try {
                        JSONObject j=new JSONObject(mMessage);
                        String success=j.getString("success");
                        if (success.equals("true"))
                        {
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context,"Reserve Deleted",Toast.LENGTH_SHORT).show();
                                    BagReservationFragment.getCarts.remove(postion);
                                    BagReservationFragment.bagReservationAdapter.notifyDataSetChanged();
                                }
                            });
                        }

                    }catch (final JSONException je){
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }

            });
    //        Log.d("MessageResponse",mMessage);
        }

        //------------------------------booking automated browse--------------------------------
        public static String bookingFilter(String filterNum,String val1,String val2){
            String filter;
//            if (val1.equals("4")){
//                filter="\"Filter\":[{\"num\":"+filterNum+",\"value1\":"+val1+",\"value2\":"+val2+"}," +
//                        "{\"num\":"+filterNum+",\"value1\":5,\"value2\":"+val2+"}" +
//                        "]";
//            }else {
                filter="\"Filter\":[{\"num\":"+filterNum+",\"value1\":"+val1+",\"value2\":"+val2+"}]";
//            }

                return filter;
        }
        public static String bookingSort(String num,String by){
                String sort="";
                sort="\"sort\":{\"num\":"+num+",\"by\":\""+by+"\"}";
                return  sort;
      }
        public  static  void  bookingAutomatedBrowse(String language,String itemPerPage,String pageNum,String filter,String sort,final Context context){

            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pd=new ProgressDialog(context);
                    ReservationFragment.pullToRefresh.setRefreshing(true);
                    pd.show();
                }
            });

    //        String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            //        {	"lang":"en",
            //	"ItemPerPage":18
            //	,"PageNum":"1"
            //	,"sort":{"num":1,"by":"desc"}
            //}
            String tmp="";
            if(filter.equals("") && sort.equals("")){
             tmp="{\t\"lang\":\""+language+"\",\n" +
                    "\t\"ItemPerPage\":"+itemPerPage+"\n" +
                    "\t,\"PageNum\":\""+pageNum+"\"\n " +
                    "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
                    "}";
            }else if (!filter.equals("")){
                if (sort.equals("")) {
                    tmp = "{\t\"lang\":\"" + language + "\",\n" +
                            "\t\"ItemPerPage\":" + itemPerPage + "\n" +
                            "\t,\"PageNum\":\"" + pageNum + "\"\n ," +
                            filter +
    //                    "\t,\"Filter\":{\"num\":1,\"value1\":\""\"}\n" +
                            "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
                            "}";
                }else {
                    tmp = "{\t\"lang\":\"" + language + "\",\n" +
                            "\t\"ItemPerPage\":" + itemPerPage + "\n" +
                            "\t,\"PageNum\":\"" + pageNum + "\"\n ," +
                            filter +","+
                            sort+
    //                        "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
                            "}";
                }
            }

            Log.e("bookingfilte",tmp);
            RequestBody body = RequestBody.create(MEDIA_TYPE, tmp);

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://clientapp.dcoret.com/api/booking/bookingAutomatedBrowse")
                    .post(body)
                    .addHeader("Content-Type","application/json")
                    .header("Authorization", "Bearer "+gettoken(context))
    //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            ReservationFragment.pullToRefresh.setRefreshing(false);
                        }
                    });
                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                        ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                final Dialog dialog = new Dialog(BeautyMainPage.context);
                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                                TextView confirm = dialog.findViewById(R.id.confirm);
                                TextView message = dialog.findViewById(R.id.message);
                                TextView title = dialog.findViewById(R.id.title);
                                title.setText(R.string.Null);
                                message.setText(R.string.check_internet_con);
                                confirm.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.cancel();

                                    }
                                });
                                dialog.show();

                            }
                        });

                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                            }
                        });
                    }

                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.e("TAG", mMessage);
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            pd.dismiss();
                            ReservationFragment.pullToRefresh.setRefreshing(false);
                        }
                    });


                    try {
                        JSONObject j=new JSONObject(mMessage);
                        String success=j.getString("success");
                        if (success.equals("true"))
                        {
                            JSONObject data=j.getJSONObject("data");
    //                        Log.e("ttttttttttt",data+"");
                            JSONArray bookings=data.getJSONArray("bookings");
    //                        Log.e("ttttttttttt",bookings+"");
                            String   totalItem=data.getString("totalItem");
                            for (int i=0;i<bookings.length();i++){
                                JSONObject jsonObject=bookings.getJSONObject(i);
                                Log.e("ttttttttttt",jsonObject+"");

                                String   bdb_id=jsonObject.getString("bdb_id");
                                String   bdb_price=jsonObject.getString("bdb_price");
                                String   bdb_status=jsonObject.getString("bdb_status");
                                String   bdb_start_date=jsonObject.getString("bdb_start_date");
                                String   bdb_start_time=jsonObject.getString("bdb_start_time");
                                String   supplier_name=jsonObject.getString("supplier_name");
                                String   employee_name=jsonObject.getString("employee name");
                                String   service_en_name=jsonObject.getString("service en name");
                                String   service_ar_name=jsonObject.getString("service ar name");
                                ReservationFragment.bookingAutomatedBrowseData.add(new BookingAutomatedBrowseData(
                                   bdb_id,bdb_price,bdb_status,bdb_start_date,bdb_start_time,supplier_name,employee_name,service_en_name,service_ar_name,totalItem
                                ));
                            }
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ReservationFragment.reservationsAdapter.notifyDataSetChanged();
                                }
                            });
                        }

                    }catch (final JSONException je){
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                            }
                        });

                    }

                }

            });
    //        Log.d("MessageResponse",mMessage);
        }
        public  static  void  bookingAutomatedBrowsecancel(final String language, final String itemPerPage, final String pageNum, String filter, String sort, final Boolean clearReq, final Context context) {

            MediaType MEDIA_TYPE = MediaType.parse("application/json");



            //        String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            //        {	"lang":"en",
            //	"ItemPerPage":18
            //	,"PageNum":"1"
            //	,"sort":{"num":1,"by":"desc"}
            //}
            String tmp = "";
//        if(filter.equals("") && sort.equals("")){
//            tmp="{\t\"lang\":\""+language+"\",\n" +
//                    "\t\"ItemPerPage\":"+itemPerPage+"\n" +
//                    "\t,\"PageNum\":\""+pageNum+"\"\n " +
//                    "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
//                    "}";
//        }else if (!filter.equals("")){
//            if (sort.equals("")) {
            tmp = "{\t\"lang\":\"" + language + "\",\n" +
                    "\t\"ItemPerPage\":" + itemPerPage + "\n" +
                    "\t,\"PageNum\":\"" + pageNum + "\"\n ," +
                    "\"Filter\":[{\"num\":1,\"value1\":5,\"value2\":0}]" +
            //                    "\t,\"Filter\":{\"num\":1,\"value1\":\""\"}\n" +
//                        "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
                        "}";
//            }else {
//                tmp = "{\t\"lang\":\"" + language + "\",\n" +
//                        "\t\"ItemPerPage\":" + itemPerPage + "\n" +
//                        "\t,\"PageNum\":\"" + pageNum + "\"\n ," +
//                        filter +","+
//                        sort+
//                        //                        "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
//                        "}";
//            }


//        Log.e("bookingfilte",tmp);
        RequestBody body = RequestBody.create(MEDIA_TYPE, tmp);

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/bookingAutomatedBrowse")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
//                                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);

                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();

                                }
                            });
                            dialog.show();

                        }
                    });

                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("Token", gettoken(context));
                Log.e("TAG", mMessage);


                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true"))
                    {
                        JSONObject data=j.getJSONObject("data");
                        //                        Log.e("ttttttttttt",data+"");
                        JSONArray bookings=data.getJSONArray("bookings");
                        //                        Log.e("ttttttttttt",bookings+"");
                        String   totalItem=data.getString("totalItem");
                        for (int i=0;i<bookings.length();i++){
                            JSONObject jsonObject=bookings.getJSONObject(i);
                            Log.e("ttttttttttt",jsonObject+"");

                            String   bdb_id=jsonObject.getString("bdb_id");
                            String   bdb_price=jsonObject.getString("bdb_price");
                            String   bdb_status=jsonObject.getString("bdb_status");
                            String   bdb_start_date=jsonObject.getString("bdb_start_date");
                            String   bdb_start_time=jsonObject.getString("bdb_start_time");
                            String   supplier_name=jsonObject.getString("supplier_name");
                            String   employee_name=jsonObject.getString("employee name");
                            String   service_en_name=jsonObject.getString("service en name");
                            String   service_ar_name=jsonObject.getString("service ar name");
                            ReservationFragment.bookingAutomatedBrowseData_cancel.add(new BookingAutomatedBrowseData(
                                    bdb_id,bdb_price,bdb_status,bdb_start_date,bdb_start_time,supplier_name,employee_name,service_en_name,service_ar_name,totalItem
                            ));
                        }


                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ReservationFragment.reservationsAdapter_cancel.notifyDataSetChanged();
//                               String tmp_filter=APICall.bookingFilter("1","4","0");
//                               if (clearReq==true)
//                                bookingAutomatedBrowsecancel(language,itemPerPage,pageNum,tmp_filter,ReservationFragment.sort,false,context);
                            }
                        });
                    }

                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });
        //        Log.d("MessageResponse",mMessage);
    }


















    public  static  void  getServices(String bdb_type, final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd=new ProgressDialog(context);
//                ReservationFragment.pullToRefresh.setRefreshing(true);
                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try{
            postdata.put("bdb_type",bdb_type);
        }catch (Exception e){
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/service/Service")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
                //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();

                                }
                            });
                            dialog.show();

                        }
                    });

                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("Token", gettoken(context));
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });


                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true"))
                    {
                        JSONArray data=j.getJSONArray("data");
                        for (int i=0;i<data.length();i++){
                            JSONObject jsonObject=data.getJSONObject(i);
                            Log.e("ttttttttttt",jsonObject+"");

                            String   bdb_ser_id=jsonObject.getString("bdb_ser_id");
                            String   bdb_name=jsonObject.getString("bdb_name");
                            String   bdb_name_ar=jsonObject.getString("bdb_name_ar");
                            String   bdb_descr=jsonObject.getString("bdb_descr");
                            String   bdb_type=jsonObject.getString("bdb_type");
                            String   bdb_is_fixed_price=jsonObject.getString("bdb_is_fixed_price");
                            String   images=jsonObject.getString("images");

                            GroupReservationFragment.servicesList.add(new ServiceItems(bdb_ser_id,bdb_name,bdb_name_ar,bdb_descr,bdb_type,bdb_is_fixed_price,images));
                            GroupReservationFragment.serviceNameList.add(bdb_name);

                        }


                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                ReservationFragment.reservationsAdapter.notifyDataSetChanged();
//                                String tmp_filter=APICall.bookingFilter("1","4","0");
//                                GroupReservationFragment.adapter.notifyDataSetChanged();
//                                bookingAutomatedBrowsecancel(language,itemPerPage,pageNum,tmp_filter,ReservationFragment.sort,false,context);
                            }
                        });
                    }

                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });
        //        Log.d("MessageResponse",mMessage);
    }


    public static String getlatlng(){
            String latlng;
            latlng="\t{\"num\":34,\"value1\":"+PlaceServiceGroupFragment.lat+",\"value2\":0},\n" +
                    "\t{\"num\":35,\"value1\":"+PlaceServiceGroupFragment.lng+",\"value2\":0},";



            latlng="\t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
                    "\t{\"num\":35,\"value1\":39.2147311,\"value2\":0},";
            Log.e("LatLong",latlng);
        return latlng;
    }
    public static String getPrice(){
            String price;
            price="{\"num\":11,\"value1\":"+PlaceServiceGroupFragment.placeId+",\"value2\":0}";
        Log.e("price",price);
        return price;
    }
    public static  String getDate(){
        String date;
        date="\"date\":\""+PlaceServiceGroupFragment.dateFilter+"\",";
        Log.e("Date",date);
        return date;
    }
    public static String getClients(){
            String clients = null;
        try {

            for (int i=0;i<GroupReservationFragment.clientsViewData.size();i++) {
                if (i == 0) {
                    clients = "\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+GroupReservationFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+GroupReservationFragment.clientsViewData.get(i).getIs_current_user()+",\"services\":[\n";
                } else {
//                    clients = clients + "\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                    clients = clients+"\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+GroupReservationFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+GroupReservationFragment.clientsViewData.get(i).getIs_current_user()+",\"services\":[\n";

                }
                Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());

                    for (int j = 0; j < GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                        if (j == 0) {
                            clients = clients + "{\"ser_id\":" +GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId() + ",\"ser_time\":60}\n";
                        } else {
                            clients = clients + ",{\"ser_id\":" + GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId()+ ",\"ser_time\":60}\n";
                        }
                    }
                   if (GroupReservationFragment.clientsViewData.size()==1){
                       clients=clients+"]}";
                   }else if (i==GroupReservationFragment.clientsViewData.size()-1){
                       clients=clients+"]}";

                   }else {
                       clients=clients+"]},";

                   }

                }
            }catch (Exception e){
            e.printStackTrace();
        }
        clients=clients+"]}";

            Log.e("clients",clients);
        return clients;
    }
    public static String GroupFilterBooking(){
            String filter;

            filter="{\"Filter\":\t[\n" +
                    getlatlng()+
                    getPrice()+
                    "\t],\n" +
                    getDate() +
                    "\t\t\"clients\":["+
                    getClients();

            Log.e("Filter",filter);
            return filter;
    }





    public static ArrayList<String> salons=new ArrayList<>();
    public static    Map<String,ArrayList<SearchBookingDataSTR>> stringArrayListMap=new HashMap<>();

    public  static  void  searchGroupBooking( final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                pd=new ProgressDialog(context);
//                ReservationFragment.pullToRefresh.setRefreshing(true);
                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();

            String filter=GroupFilterBooking();
            String ff="{\"Filter\": [\n" +
                    "\t{\"num\": 34,\"value1\": 21.529023,\"value2\": 0  },\n" +
                    "\t{\"num\": 35,\"value1\": 39.2147311,\"value2\": 0},\n" +
                    "\t{\"num\": 11,\"value1\": 1,\"value2\":0}  ],\n" +
                    "\t\"date\": \"2019-07-20\", \n" +
                    "\t\"clients\": [  \n" +
                    "\t\t\t\t\t\t\t{\"client_name\": \"basma\",\"client_phone\":\"0500112233\",\"is_current_user\":1,\"services\": [\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t{\"ser_id\": 2,\"ser_time\":60},\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t{\"ser_id\": 1,\"ser_time\": 30}\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t]},\n" +
                    "\t\t\t\t\t\t\t{\"client_name\":\"sara\",\"client_phone\":\"0522331144\",\"is_current_user\":0,\"services\": [\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t{\"ser_id\": 2,\"ser_time\": 30}\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t]},\n" +
                    "\t\t\t\t\t\t\t{\"client_name\":\"roro\",\"client_phone\":\"0556699888\",\"is_current_user\":0,\"services\": [\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t{\"ser_id\": 1,\"ser_time\": 60}\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t]}\n" +
                    "\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t]}";

        RequestBody body = RequestBody.create(MEDIA_TYPE, filter);

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/searchGroupBooking")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
                //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();

                                }
                            });
                            dialog.show();

                        }
                    });

                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("Token", gettoken(context));
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();

//                        searchBookingDataSTRS.clear();
//
// ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });


                try {
                    final JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    final String message=j.getString("message");
                    if (success.equals("true")) {
                        JSONObject d = j.getJSONObject("data");

                        JSONArray completeSolutions1 = d.getJSONArray("CompleteSolutions");
                        for (int l=0;l<completeSolutions1.length();l++) {
                            ArrayList<SearchBookingDataSTR> searchBookingDataSTRS=new ArrayList<>();
                            JSONArray completeSolutions = completeSolutions1.getJSONArray(l);

                            salons.add(completeSolutions.getJSONObject(0).getString("salon_name"));
                            try {
                                Log.e("SalonAdd",completeSolutions.getJSONObject(0).getString("salon_name")+"");
                                Log.e("completeSolutions1",completeSolutions1.length()+"");
                            }catch (Exception e){
                                e.printStackTrace();
                            }

                            for (int i = 0; i < completeSolutions.length(); i++) {
                                JSONObject data = completeSolutions.getJSONObject(i);
                                String salon_id = data.getString("salon_id");
//                                String salon_name = data.getString("salon_name");
                                JSONObject client_response = data.getJSONObject("client_response");
                                String is_current_user = client_response.getString("is_current_user");
                                String salon_name = client_response.getString("client_phone");
                                String client_name = client_response.getString("client_name");
                                JSONArray solutions = client_response.getJSONArray("solutions");
                                ArrayList<SerchGroupBookingData.Solutions> solutionsArrayList = new ArrayList<>();

                                ArrayList<SearchBookingDataSTR.Solution> solutionsArr=new ArrayList<>();
                                for (int k = 0; k < solutions.length(); k++) {
                                    JSONObject data1 = solutions.getJSONObject(k);
                                    Log.e("data1",data1+"");
                                    String ser_id = data1.getString("ser_id");
                                    String emp_id = data1.getString("emp_id");
                                    String sup_id = data1.getString("sup_id");
                                    String ser_sup_id = data1.getString("ser_sup_id");
                                    String from = data1.getString("from");
                                    String to = data1.getString("to");
                                    String old_from = data1.getString("old_from");
                                    String old_to = data1.getString("old_to");
                                    String new_from = data1.getString("new_from");
                                    String new_to = data1.getString("new_to");
                                    String client_name1 = data1.getString("client_name");
                                    String ser_name = data1.getString("ser_name");
                                    String ser_name_ar = data1.getString("ser_name_ar");


//                                    solutionsArrayList.add(new SerchGroupBookingData.Solutions(ser_id, emp_id, sup_id, ser_sup_id, from, to, old_from, old_to, new_from, new_to, client_name, ser_name, ser_name_ar,is_current_user));
                                    solutionsArr.add(new SearchBookingDataSTR.Solution(ser_id,ser_name,ser_name_ar,emp_id,sup_id,ser_sup_id,from,to));
                                }

//                                client_responseArrayList.add(new SerchGroupBookingData.ClientResponse(client_name, solutionsArrayList));
//
//                            }

                                GroupReservationFragment.solutionsCounts.add(new SerchGroupBookingData.SolutionsCount(salon_id, salon_name, new SerchGroupBookingData.ClientResponse(client_name, solutionsArrayList)));

                                searchBookingDataSTRS.add(new SearchBookingDataSTR(salon_id,salon_name,client_name,client_name,is_current_user,solutionsArr));
                               stringArrayListMap.put(salons.get(l),searchBookingDataSTRS);

                            }
                            GroupReservationFragment.serchGroupBookingData.add(new SerchGroupBookingData(GroupReservationFragment.solutionsCounts));

                        }
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                for (int i=0;i<salons.size();i++){


                                }



//                               GroupReservationResultFragment.listAdapter=new CustomExpandableListAdapter(BeautyMainPage.context,APICall.salons,APICall.searchBookingDataSTRS);
                               GroupReservationResultFragment.listAdapter=new CustomExpandableListAdapter(BeautyMainPage.context,APICall.salons,APICall.stringArrayListMap);
//                                GroupReservationResultFragment.listAdapter.notifyDataSetChanged();
                                GroupReservationResultFragment.listView.setAdapter(GroupReservationResultFragment.listAdapter);
                                GroupReservationResultFragment.listAdapter.notifyDataSetChanged();
                                Log.e("SalonSize",salons.size()+"");
//                                Log.e("searchBookingDataSTRS",searchBookingDataSTRS.size()+"");

                            }
                        });
                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                            Log.e("jeMessage",je.getMessage());
                        }
                    });

                }

            }


        });
        //        Log.d("MessageResponse",mMessage);
    }



    public  static  void  getSupName(final TextView emp_name , final String bdb_sup_id, final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
//        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                pd=new ProgressDialog(context);
////                ReservationFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
//            }
//        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_sup_id",bdb_sup_id);
            postdata.put("attr","bdb_owner_name");
        }catch (JSONException je){
            je.printStackTrace();
        }

        String filter=GroupFilterBooking();

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/get/getSupplierAttrById")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
                //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        pd.dismiss();
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
                            title.setText(R.string.Null);
                            message.setText(R.string.check_internet_con);
                            confirm.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    dialog.cancel();

                                }
                            });
                            dialog.show();

                        }
                    });

                }else {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();

                        }
                    });
                }

            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
                Log.e("Token", gettoken(context));
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        pd.dismiss();
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });


                try {
                    final JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    final String message=j.getString("message");
                    if (success.equals("true")) {
                        JSONObject d = j.getJSONObject("data");
                            final String bdb_owner_name=d.getString("bdb_owner_name");
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                emp_name.setText(bdb_owner_name);

                            }
                        });
                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }catch (final JSONException je){
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                            Log.e("jeMessage",je.getMessage());
                        }
                    });

                }

            }

        });
        //        Log.d("MessageResponse",mMessage);
    }


}




