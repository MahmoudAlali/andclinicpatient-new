package com.dcoret.beautyclient.API;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.graphics.ColorUtils;
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
import com.dcoret.beautyclient.Activities.OfferBookingResult;
import com.dcoret.beautyclient.Activities.Offers;
import com.dcoret.beautyclient.Activities.ReservationDetails;
import com.dcoret.beautyclient.Activities.ReservatoinDetailsActivity;
import com.dcoret.beautyclient.Activities.Services;
import com.dcoret.beautyclient.Activities.SingleDateMultiClientOfferBooking;
import com.dcoret.beautyclient.Activities.SplashScreen;
import com.dcoret.beautyclient.Activities.TabOne;
import com.dcoret.beautyclient.Activities.TabOneBag;
import com.dcoret.beautyclient.Activities.TabSingleBag;
import com.dcoret.beautyclient.Activities.TabTwo;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapter;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapterForMultiInd;
import com.dcoret.beautyclient.Adapters.CustomExpandableListAdapterSearchGroupBooking2;
import com.dcoret.beautyclient.Adapters.CustomExpandableListBagAdapter;
import com.dcoret.beautyclient.Adapters.ListServicesAdapter;
import com.dcoret.beautyclient.Adapters.ReservationsAdapter2;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.Adapters.ShowServicesAdapter;
import com.dcoret.beautyclient.DataClass.AddToCart;
import com.dcoret.beautyclient.DataClass.BestOfferItem;
import com.dcoret.beautyclient.DataClass.BookingAutomatedBrowseData;
import com.dcoret.beautyclient.DataClass.BrowseServiceItem;
import com.dcoret.beautyclient.DataClass.Cities;
import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.EmployeeSalonClass;
import com.dcoret.beautyclient.DataClass.FilterAndSortModel;
import com.dcoret.beautyclient.DataClass.GetAllCart;
import com.dcoret.beautyclient.DataClass.GetAllCartServices;
import com.dcoret.beautyclient.DataClass.GetCart;
import com.dcoret.beautyclient.DataClass.ListServiceModel;
import com.dcoret.beautyclient.DataClass.LocationTitles;
import com.dcoret.beautyclient.DataClass.OfferClientsModel;
import com.dcoret.beautyclient.DataClass.OfferModel;
import com.dcoret.beautyclient.DataClass.ReservationModel;
import com.dcoret.beautyclient.DataClass.SearchBookingDataSTR;
import com.dcoret.beautyclient.DataClass.SearchGroupBooking2;
import com.dcoret.beautyclient.DataClass.SerchGroupBookingData;
import com.dcoret.beautyclient.DataClass.ServiceItems;
import com.dcoret.beautyclient.DataClass.ServicesClass;
import com.dcoret.beautyclient.DataClass.SolutionGroupBooking2;
import com.dcoret.beautyclient.DataClass.SupInfoClass;
import com.dcoret.beautyclient.Fragments.AccountFragment;
import com.dcoret.beautyclient.Fragments.BagReservationFragment;
import com.dcoret.beautyclient.Fragments.BagReservationTestFragment;
import com.dcoret.beautyclient.Fragments.ClientRelationsFragment;
import com.dcoret.beautyclient.Fragments.GroupReservationFragment;
import com.dcoret.beautyclient.Fragments.GroupReservationOtherResultFragment;
import com.dcoret.beautyclient.Fragments.GroupReservationOthersFragment;
import com.dcoret.beautyclient.Fragments.GroupReservationResultFragment;
import com.dcoret.beautyclient.Fragments.ListServicesBrideFragment;
import com.dcoret.beautyclient.Fragments.ListServicesFragment;
import com.dcoret.beautyclient.Fragments.MapFragment;
import com.dcoret.beautyclient.Fragments.MultiBookingIndividualResult;
import com.dcoret.beautyclient.Fragments.MultiIndividualBookingReservationFragment;
import com.dcoret.beautyclient.Fragments.MyReservationFragment;
import com.dcoret.beautyclient.Fragments.PlaceServiceFragment;
import com.dcoret.beautyclient.Fragments.PlaceServiceGroupFragment;
import com.dcoret.beautyclient.Fragments.PlaceServiceGroupOthersFragment;
import com.dcoret.beautyclient.Fragments.PlaceServiceMultipleBookingFragment;
import com.dcoret.beautyclient.Fragments.ReservationDetailsFragment;
import com.dcoret.beautyclient.Fragments.ReservationFragment;
import com.dcoret.beautyclient.Fragments.ServiceFragment;
import com.dcoret.beautyclient.Fragments.ServicesTabsFragment;
import com.dcoret.beautyclient.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.JsonArray;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class APICall {


    public static int layout;
    public static String idSerForOffer;
    public static ArrayList empNames=new ArrayList();
    public static ArrayList<String> servicesList=new ArrayList<>();
    public static String filter="";
    public static String sort="";
    public static String offertypeAll;
    public static String dateforgroupbooking="";
    public static ArrayList<OfferModel> dofs=new ArrayList<>();

//    public static Dialog pd;

    public APICall() {
    }
    private static final OkHttpClient client = new OkHttpClient();
    public static KProgressHUD pd;
    static String error = "";
    static   String ln= SplashScreen.ln;


    //-------------------------------------------


    public static void showDialog(Context context){
        int color = ((AppCompatActivity)context).getResources().getColor(android.R.color.transparent);
        int color50percent = ColorUtils.setAlphaComponent(color, 50);

        pd = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
//                            .setLabel("Please wait")
                .setCancellable(true)
                .setBackgroundColor(color50percent)
                .setMaxProgress(100)
                .show();
    }

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
            , final String loc_lat, final String description,final String my_description,String loc_details,String loc_detailsAr,final  String url, final Context context){
        if (validationPassword(password)){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
           showDialog(context);
            //        String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try {
                String fb_token= FirebaseInstanceId.getInstance().getToken();
                String server_key="AAAA6gZ1CO8:APA91bHEg19SqKpRdvifPk3-o-nWwDm350IZaNjqX0yy0eHkRUnv1hSBHN6zaQZR0ZvoINJUNX1zbRMDto0W4ePuFwckOOBabMECCscYuwyisY4YEGHhCr10kjEVPoifc9IOz_x7dP0q";
                postdata.put("bdb_mobile", phone);
                postdata.put("bdb_gender", gender);
                postdata.put("password", password);
                postdata.put("c_password", confirm_password);
                postdata.put("bdb_loc_long", loc_long);
                postdata.put("bdb_loc_lat", loc_lat);
                postdata.put("bdb_descr",description );
                postdata.put("bdb_my_descr", my_description);
                postdata.put("bdb_fb_token", fb_token);
                postdata.put("bdb_server_key", server_key);
                postdata.put("bdb_sys_type", "0");



                JSONArray array=new JSONArray();
                Log.e("Address",loc_details);
                Log.e("AddressAr",loc_detailsAr);
                array.put(new JSONObject(loc_details));
                array.put(new JSONObject(loc_detailsAr));
                postdata.put("address_details",array);
//                postdata.put("bdb_city", "1");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.e("POSTDATE",postdata.toString());

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




//    public  static  String  new_user( final String mobile1 ,final String password, final String confirm_password, final String loc_long
//            , final String loc_lat, final String description,final String my_description,String email, String bdb_owner_name,String bdb_nation_num , final  String url, final Context context){
//        if (validationPassword(password)){
//            MediaType MEDIA_TYPE = MediaType.parse("application/json");
//            showDialog(context);
////            pd.show();
//            //        String url = "http://clientapp.dcoret.com/api/service/Service";
//            OkHttpClient client = new OkHttpClient();
//            JSONObject postdata = new JSONObject();
//            try {
//                String fb_token= FirebaseInstanceId.getInstance().getToken();
//                String server_key="AAAA6gZ1CO8:APA91bHEg19SqKpRdvifPk3-o-nWwDm350IZaNjqX0yy0eHkRUnv1hSBHN6zaQZR0ZvoINJUNX1zbRMDto0W4ePuFwckOOBabMECCscYuwyisY4YEGHhCr10kjEVPoifc9IOz_x7dP0q";
//                postdata.put("owner_name",bdb_owner_name );
//                postdata.put("bdb_mobile1", mobile1);
//                postdata.put("bdb_nation_num", bdb_nation_num);
//                postdata.put("email",email);
//                postdata.put("password", password);
//                postdata.put("c_password", confirm_password);
//                postdata.put("bdb_loc_long", loc_long);
//                postdata.put("bdb_loc_lat", loc_lat);
//                postdata.put("bdb_descr",description );
//                postdata.put("bdb_my_descr", my_description);
//                postdata.put("bdb_fb_token", fb_token);
//                postdata.put("bdb_server_key", server_key);
//                postdata.put("bdb_sys_type", "0");
////                postdata.put("bdb_city", "1");
//            } catch (JSONException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//
//            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());
//            Log.e("NewUserLog",postdata.toString());
//
//            okhttp3.Request request = new okhttp3.Request.Builder()
//                    .url(url)
//                    .post(body)
//                    .addHeader("Content-Type","multipart/form-data")
//                    .addHeader("Accept","application/json")
//                    .build();
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    mMessage = e.getMessage().toString();
//                    Log.w("failure Response", mMessage);
//                    pd.dismiss();
//
//
//                    if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
////                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
//                        ((AppCompatActivity) context).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                final Dialog dialog = new Dialog(context);
//                                dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
////                                dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
////                                TextView confirm = dialog.findViewById(R.id.confirm);
////                                TextView message = dialog.findViewById(R.id.message);
////                                TextView title = dialog.findViewById(R.id.title);
////                                title.setText(R.string.Null);
////                                message.setText(R.string.check_internet_con);
////                                confirm.setOnClickListener(new View.OnClickListener() {
////                                    @Override
////                                    public void onClick(View v) {
////                                        dialog.cancel();
////
////                                    }
////                                });
//                                dialog.show();
//
//                            }
//                        });
//
//
//                    }else {
//                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
//
//                            }
//                        });
//                    }
//
//                }
//
//                @Override
//                public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                    mMessage = response.body().string();
//                    Log.e("TAG", mMessage);
//                    pd.dismiss();
//                    SharedPreferences.Editor editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE).edit();
//                    try {
//                        final JSONObject userresponse=new JSONObject(mMessage);
//                        String success=userresponse.getString("success");
//                        Log.e("data",userresponse.toString());
//                        if(success.equals("true")){
//                            JSONObject data=userresponse.getJSONObject("data");
//                            String ac_num=userresponse.getString("activation number :");
//                            Log.d("number",ac_num);
//                            APICall.name=name;
//                            APICall.token_temp=data.getString("token");
//                            showSweetDialog(context,R.string.ExuseMeAlert,R.string.EnterVerificationCode,true);
//                        }else if(success.equals("false")) {
//                            JSONObject err= userresponse.getJSONObject("message");
////                            JSONArray bdb_mobile=err.getJSONArray("bdb_mobile");
////                            String error=bdb_mobile.getString(0);
////                            if(error.equals("bdb_mobile is already exists and not activated")){
////                                activateAgain("http://clientapp.dcoret.com/api/auth/user/register/ActivateAgain",
////                                        phone,
////                                        context);
//                            showSweetDialog(context,context.getResources().getString(R.string.ExuseMeAlert),err.toString());
////                            }else if (error.equals("The bdb mobile format is invalid.")){
////                                showSweetDialog(context,R.string.ExuseMeAlert,R.string.InvalidFormatNum,false);
////                            for email address
////                            }else if (error.equals("The bdb mobile format is invalid.")){
////                                showSweetDialog(context,R.string.ExuseMeAlert,R.string.InvalidFormatNum,false);
////                            }else if(error.equals("bdb_mobile is already exists and activated")) {
////                                showSweetDialog(context,R.string.ExuseMeAlert,R.string.MobTakenAlert,false);
////                            }
//                        }
//                    }catch (final JSONException je){
//                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                String t=((AppCompatActivity)context).getResources().getString(R.string.wrong);
//                                showSweetDialog(context,t,je.getMessage().toString() );
//
//                            }
//                        });
//
//                    }
//
//                }
//
//            });
//        }else {
//            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    showSweetDialog(context,R.string.ExuseMeAlert,R.string.InvalidPassword,false);
//
//                }
//            });
//        }
//        return mMessage;
//
//    }


    //   ------------------ rating the app =-------------------------
    public  static  void  rateApp(final String bdb_rate,final  String url,final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        showDialog(context);
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
                showDialog(context);
//                pd.show();
                Offers.pullToRefresh.setRefreshing(true);

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

                    }else {
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showSweetDialog(context,((AppCompatActivity)context).getResources().getString(R.string.alert)
                                ,((AppCompatActivity)context).getResources().getString(R.string.no_offer));
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
                e_bdb_mobile.setText(convertToArabic(sh.getString("bdb_mobile",null)));

            }else {
                BeautyMainPage.isFirstOpen=false;
                String token = ((AppCompatActivity) context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("token", null);
                MediaType MEDIA_TYPE = MediaType.parse("application/json");

                        showDialog(context);
//                        pd.show();


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
                                        e_bdb_mobile.setText(convertToArabic(bdb_mobile));
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

//            showDialog(context);
////            pd.show();


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
        public  static  void  detailsUser2(final Context context, final EditText name, final EditText mobile){
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

//            showDialog(context);
////            pd.show();


            //-----------String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();

            RequestBody body = RequestBody.create(MEDIA_TYPE, "");

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://clientapp.dcoret.com/api/auth/user/detailsUser")
                    .post(body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .header("Authorization", "Bearer " + gettoken(context))
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
                                                detailsUser2(context,name,mobile);
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
                            String bdb_email = data.getString("bdb_email");
//                            e_bdb_email.setText(bdb_email);
                            String bdb_mobile = data.getString("bdb_mobile");

                            name.setText(bdb_name);
                            mobile.setText(bdb_mobile);
//                                editor.putString("bdb_name", bdb_name);
//                            editor.putString("bdb_email", bdb_email);
//                            editor.putString("bdb_name", bdb_name);
//                            editor.putString("bdb_mobile", bdb_mobile);
//                            editor.putString("addressUser", mMessage + "");
//                            editor.apply();
//                            editor.commit();


                        }
                    } catch (JSONException e) {
                    e.printStackTrace();
                    }
//                    pd.dismiss();
                }

            });

            Log.d("MessageResponse", mMessage);
        }
        public  static  void  detailsUser3(final Context context){
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
//            String token = ((AppCompatActivity) context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("token", null);
            MediaType MEDIA_TYPE = MediaType.parse("application/json");

//            showDialog(context);
////            pd.show();


            //-----------String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();

            RequestBody body = RequestBody.create(MEDIA_TYPE, "");

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url("http://clientapp.dcoret.com/api/auth/user/detailsUser")
                    .post(body)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("X-Requested-With", "XMLHttpRequest")
                    .header("Authorization", "Bearer " + gettoken(context))
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
                                                detailsUser3(context);
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
                            String bdb_email = data.getString("bdb_email");
//                            e_bdb_email.setText(bdb_email);
                            String bdb_mobile = data.getString("bdb_mobile");

//                            name.setText(bdb_name);
//                            mobile.setText(bdb_mobile);
                                editor.putString("bdb_name", bdb_name);
//                            editor.putString("bdb_email", bdb_email);
//                            editor.putString("bdb_name", bdb_name);
                            editor.putString("bdb_mobile", bdb_mobile);
//                            editor.putString("addressUser", mMessage + "");
//                            editor.apply();
//                            editor.commit();


                        }
                    } catch (JSONException e) {
                    e.printStackTrace();
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
        showDialog(context);
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
        showDialog(context);

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
                            if (message.equals("This user is not active") || message.equals("This user is already exist but not active")) {
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
                            showDialog(context);
//                            pd.show();

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
        public  static  void  addAddress(final  String url, final String bdb_loc_long, final String bdb_loc_lat, final String bdb_descr, final String my_description
                ,String lang,String admin,String locality,String bdb_is_origin,String sublocality,String thorourhfare,
                                         String langAR,String adminAr,String localityAr,String bdb_is_originAr,String sublocalityAr,String thorourhfareAr,
                                         final Context context){
                MediaType MEDIA_TYPE = MediaType.parse("application/json");
                showDialog(context);
//                pd.show();

                OkHttpClient client = new OkHttpClient();
                JSONObject postdata = new JSONObject();
//                try {
//                    postdata.put("bdb_loc_long", bdb_loc_long);
//                    postdata.put("bdb_loc_lat", bdb_loc_lat);
//                    postdata.put("bdb_descr", bdb_descr);
//                    postdata.put("bdb_my_descr", my_description);
//                }catch (JSONException je){
//                    je.printStackTrace();
//                }

        String postreq="\t{\t\n"+
                    "\t\"bdb_loc_long\":\""+bdb_loc_long+"\",\n" +
                    "\t\"bdb_loc_lat\":\""+bdb_loc_lat+"\",\n" +
                    "\t\"bdb_descr\":\""+bdb_descr+"\",\n" +
                    "\t\"bdb_my_descr\":\""+my_description+"\",\n" +
                    "\t\"bdb_is_origin\":\""+bdb_is_origin+"\",\n" +
                    "\t \"address_details\":[" +
                    "{" +
                    "\"lang\":\""+lang+"\"," +
                    "\"admin\":\""+admin+"\"," +
                    "\"locality\":\""+locality+"\"," +
                    "\"sub_locality\":\""+sublocality+"\"," +
                    "\"thoroughfare\":\""+thorourhfare+"\"" +
                    "}," +
                    "{" +
                    "\"lang\":\""+langAR+"\"," +
                    "\"admin\":\""+adminAr+"\"," +
                    "\"locality\":\""+localityAr+"\"," +
                    "\"sub_locality\":\""+sublocalityAr+"\"," +
                    "\"thoroughfare\":\""+thorourhfareAr+"\"" +
                    "}" +

                    "]" +
                    "}";

            Log.e("LocationPost",postreq.toString());

                RequestBody body = RequestBody.create(MEDIA_TYPE, postreq);

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
                                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(context,"Success add addrss",Toast.LENGTH_SHORT).show();
                                    }
                                });
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
           showDialog(context);
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
        public  static  String  updateaddress(final  String url,String bdb_address_id,final String bdb_loc_long, final String bdb_loc_lat, final String bdb_descr, final String my_description
                ,String lang,String admin,String locality,String bdb_is_origin,String sublocality,String thorourhfare,
                                              String langAR,String adminAr,String localityAr,String bdb_is_originAr,String sublocalityAr,String thorourhfareAr,
                                              final Context context){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showDialog(context);
                }
            });

            OkHttpClient client = new OkHttpClient();

            if(admin.equals("")){
                admin="null";
            }

            if(adminAr.equals("")){
                adminAr="null";
            }

//            if(adminAr.equals("")){
//                adminAr="null";
//            }
//            if(adminAr.equals("")){
//                adminAr="null";
//            }

            JSONObject postdata = new JSONObject();
            String postreq="\t{\t\n"+
                    "\t\"bdb_loc_long\":\""+bdb_loc_long+"\",\n" +
                    "\t\"bdb_address_id\":\""+bdb_address_id+"\",\n" +
                    "\t\"bdb_loc_lat\":\""+bdb_loc_lat+"\",\n" +
                    "\t\"bdb_descr\":\""+bdb_descr+"\",\n" +
                    "\t\"bdb_my_descr\":\""+my_description+"\",\n" +
                    "\t\"bdb_is_origin\":\""+bdb_is_origin+"\",\n" +
                    "\t \"address_details\":[" +
                    "{" +
                    "\"lang\":\""+lang+"\"," +
                    "\"admin\":\""+admin+"\"," +
                    "\"locality\":\""+locality+"\"," +
                    "\"sub_locality\":\""+sublocality+"\"," +
                    "\"thoroughfare\":\""+thorourhfare+"\"" +
                    "}," +
                    "{" +
                    "\"lang\":\""+langAR+"\"," +
                    "\"admin\":\""+adminAr+"\"," +
                    "\"locality\":\""+localityAr+"\"," +
                    "\"sub_locality\":\""+sublocalityAr+"\"," +
                    "\"thoroughfare\":\""+thorourhfareAr+"\"" +
                    "}" +
                    "]" +
                    "}";

            Log.e("POSTRequest",postreq);

            RequestBody body = RequestBody.create(MEDIA_TYPE, postreq.toString());

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
                    pd.dismiss();

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
                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Success Update address", Toast.LENGTH_LONG).show();
                                                                }
                            });
                            getdetailsUser(context);
                        }
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
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
                    showDialog(context);
//                    pd.show();
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
                                            String t=((AppCompatActivity)context).getResources().getString(R.string.wrong);
                                            showSweetDialog(context, t,  t+": "+mMessage );
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
           showDialog(context);

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
           showDialog(context);

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
                        showDialog(context);
//                        pd.show();
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
           showDialog(context);

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
        showDialog(context);
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
    showDialog(context);
//    pd.show();
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

            TabOne.arrayList.clear();
            try {
                TabOne.servicesAdapter.notifyDataSetChanged();
            }catch (Exception e){
                e.printStackTrace();
            }
            Log.e("PlaceId",PlaceServiceFragment.placeId+"");
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
           showDialog(context);
            ServicesTabsFragment.supInfoList.clear();
            OkHttpClient client = new OkHttpClient();
            String temp="{\"lang\":\""+ln+"\",\"ItemPerPage\":8,\"PageNum\":\""+pageNum+"\",\"Filter\":[" +
                    "{\"num\":6,\"value1\":4,\"value2\":0}," +
                    "{\"num\":34,\"value1\":36.47792,\"value2\":0}," +
                    "{\"num\":35,\"value1\":36.23389,\"value2\":0}" +
                    "]" +
                    "}";


            if (PlaceServiceFragment.placeId==0){
                PlaceServiceFragment.priceServiceValue="";
            }else {
                PlaceServiceFragment.priceServiceValue=",{\"num\":"+PlaceServiceFragment.placeId+",\"value1\":"+PlaceServiceFragment.minprice+",\"value2\":"+PlaceServiceFragment.maxprice+"}";
            }

            try {
                if (!ServicesTabsFragment.price.isChecked()) {
                    PlaceServiceFragment.priceServiceValue = "";
                }
                if (!ServicesTabsFragment.nameSalonOrProvider.isChecked()){
                    ServicesTabsFragment.bdb_name="";
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            String jsonPostData="{\"lang\":\"en\"," +
                    "\"ItemPerPage\":20," +
                    "\"PageNum\":\""+pageNum+"\"," +
                    ServicesTabsFragment.bdb_name+"\n"+
                    "\"Filter\":[" +
//                    getCityId()+
                    "{\"num\":33,\"value1\":"+ ListServicesAdapter.bdb_ser_id +",\"value2\":0},"+
//                    getFilterList()+  // need to try catch
                    PlaceServiceFragment.locOfferlat+"\n"+
                    PlaceServiceFragment.locOfferlong+"\n"+
                    PlaceServiceFragment.distanceOffer+"\n"+
                    PlaceServiceFragment.priceServiceValue+"\n"+
                    PlaceServiceFragment.rateOffer+"\n"+
                    PlaceServiceFragment.supRate+"\n"+

                    "]\n" +
                    ServicesTabsFragment.sortby+

//                    ",\"sort\":{\"num\":27,\"by\":\"desc\"}\n" +
                    "}";


            Log.e("ServicePost",jsonPostData);
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
                            JSONArray supInfo=data.getJSONArray("supplier info");
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

                            for (int i=0;i<supInfo.length();i++){
                                JSONObject info=supInfo.getJSONObject(i);
                                String name=info.getString("name");
                                String id=info.getString("id");
                                String address=info.getString("address");

                                ServicesTabsFragment.supInfoList.add(new SupInfoClass(name,id,address));

                            }






                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TabOne.refreshRV();
                                    Log.e("ARRAYLIST",TabOne.arrayList.size()+"");
//                                    TabOne.recyclerView.invalidate();
                                }
                            });

                            }

                            }else if (message.equals("there is no providers with your search filters")){
                                TabOne.arrayList.clear();
                                ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        TabOne.refreshRV();
                                        String t=((AppCompatActivity)context).getResources().getString(R.string.alert);
                                        String m=((AppCompatActivity)context).getResources().getString(R.string.there_is_no_provider);
                                        showSweetDialog(context,t,m);
//                                        Toast.makeText(BeautyMainPage.context,"There is no Provider with your search filter",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }catch (final JSONException je){
                        //there is no suppliered services with your search filters
                        ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          TabOne.arrayList.clear();
                                          TabOne.refreshRV();
                                          String t=((AppCompatActivity)context).getResources().getString(R.string.alert);

                                          showSweetDialog(context,t,je.getMessage());
                                      }
                                  });

                        je.printStackTrace();
                    }
                }
            });

            Log.d("MessageResponse",mMessage);
            return mMessage;
        }
       public static ArrayList<String> offerSupplier=new ArrayList<>();
        public  static  String  automatedBrowseOffers( final String itemPerPage, final String pageNum, final Context context){
            offerSupplier.clear();
            ServicesTabsFragment.supInfoList.clear();
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
           showDialog(context);
            OkHttpClient client = new OkHttpClient();
//        String temp="{\"lang\":\"en\",\"ItemPerPage\":10,\"PageNum\":1,\"SupplierId\":38,\"Filter\":[\n" +
//                "\t{\"num\":7,\"value1\":1} ,  \n" +
//                "\t{\"num\":36,\"value1\":40} ,  \n" +
//                "\t{\"num\":34,\"value1\":21.1236547} , \n" +
//                "\t{\"num\":35,\"value1\":39.1236547}  ,\n" +
//                "\t{\"num\":2,\"value2\":1000}\n" +
//                "\t]\n" +
//                "}";

        String jsonPostData="{\"lang\":\"en\"," +
                "\"ItemPerPage\":20," +
                "\"PageNum\":\""+pageNum+"\"," +
                ServicesTabsFragment.ServiceId+"\n"+
                "\"Filter\":[" +
                "{\"num\":7,\"value1\":1}," +
//                getCityId()+
                PlaceServiceFragment.locOfferlat+"\n"+
                PlaceServiceFragment.locOfferlong+"\n"+
                PlaceServiceFragment.distanceOffer+"\n"+
                PlaceServiceFragment.priceOffer+"\n"+
                PlaceServiceFragment.supRate+"\n"+
//                getFilterList()+  // need to try catch
                "\t\t\t]\n" +
                ServicesTabsFragment.sortby+
                "}";


        Log.e("OfferPost",jsonPostData);
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
              try {
                  TabTwo.pullToRefresh.setRefreshing(false);
              }catch (Exception e){
                  e.printStackTrace();
              }

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
                                JSONArray supInfo=data.getJSONArray("supplier info");

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
                                            bdb_offer_start=jarray.getString("bdb_offer_start"),
                                            num_of_times=jarray.getString("Num_of_times"),
                                            oldPrice=jarray.getString("oldPrice"),
                                            newPrice=jarray.getString("newPrice"),
                                            bdb_is_old_on=jarray.getString("bdb_is_old_on"),
                                            bdb_offer_place=jarray.getString("bdb_offer_place"),
                                            bdb_is_journey_on=jarray.getString("bdb_is_journey_on"),
                                            bdb_is_effects_on=jarray.getString("bdb_is_effects_on"),
                                            discount=jarray.getString("discount"),
                                            distance=jarray.getString("distance"),
                                            longitude=jarray.getString("longitude"),
                                            bdb_offer_type=jarray.getString("bdb_offer_type"),
                                            latitude=jarray.getString("latitude");

                                    JSONArray pack_data=jarray.getJSONArray("pack_data");
                                    ArrayList<DataOffer.SupIdClass> supIdClasses=new ArrayList<>();
                                    for (int j=0;j<pack_data.length();j++){
                                        JSONObject object=pack_data.getJSONObject(j);
                                        String bdb_ser_sup_id=object.getString("bdb_ser_sup_id");
                                        String bdb_name=object.getString("bdb_ser_name");
                                        String bdb_ser_id=object.getString("bdb_ser_id");
                                        supIdClasses.add(new DataOffer.SupIdClass(bdb_ser_sup_id,bdb_name,bdb_ser_id));
                                    }


                                    DataOffer dof = new DataOffer(bdb_pack_code,bdb_sup_name,totalRating_to_Sup,service_count,is_fav_sup,bdb_offer_start,bdb_offer_end,num_of_times,oldPrice,newPrice,discount,"",bdb_offer_type,longitude,latitude,distance,bdb_is_journey_on+"",bdb_is_old_on+"",bdb_offer_place+"",bdb_is_effects_on+"",supIdClasses);
                                    TabTwo.arrayList.add(dof);


                                    for (int j=0;j<supInfo.length();j++){
                                        JSONObject info=supInfo.getJSONObject(j);
                                        String name=info.getString("name");
                                        String id=info.getString("id");
                                        String address=info.getString("address");
                                        ServicesTabsFragment.supInfoList.add(new SupInfoClass(name,id,address));
                                    }

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
//        public  static  String  automatedBrowseOffers_v1( final String itemPerPage, final String pageNum, final Context context){
//            offerSupplier.clear();
//            ServicesTabsFragment.supInfoList.clear();
//            MediaType MEDIA_TYPE = MediaType.parse("application/json");
//            showDialog(context);
////            pd.show();
//            OkHttpClient client = new OkHttpClient();
////        String temp="{\"lang\":\"en\",\"ItemPerPage\":10,\"PageNum\":1,\"SupplierId\":38,\"Filter\":[\n" +
////                "\t{\"num\":7,\"value1\":1} ,  \n" +
////                "\t{\"num\":36,\"value1\":40} ,  \n" +
////                "\t{\"num\":34,\"value1\":21.1236547} , \n" +
////                "\t{\"num\":35,\"value1\":39.1236547}  ,\n" +
////                "\t{\"num\":2,\"value2\":1000}\n" +
////                "\t]\n" +
////                "}";
//
//        String jsonPostData="{\"lang\":\"en\"," +
//                "\"ItemPerPage\":20," +
//                "\"PageNum\":\""+pageNum+"\"," +
//                ServicesTabsFragment.bdb_name+"\n"+
//                "\"Filter\":[" +
//                "{\"num\":7,\"value1\":1}," +
////                getCityId()+
//                PlaceServiceFragment.locOfferlat+"\n"+
//                PlaceServiceFragment.locOfferlong+"\n"+
//                PlaceServiceFragment.distanceOffer+"\n"+
//                PlaceServiceFragment.priceOffer+"\n"+
//                PlaceServiceFragment.supRate+"\n"+
////                getFilterList()+  // need to try catch
//                "\t\t\t]\n" +
//                ServicesTabsFragment.sortby+
//                "}";
//
//
//        Log.e("OfferPost",jsonPostData);
//        final RequestBody body = RequestBody.create(MEDIA_TYPE, jsonPostData);
//        okhttp3.Request request = new okhttp3.Request.Builder()
//                .url("http://clientapp.dcoret.com/api/service/offer/automatedBrowse")
//                .post(body)
//                .addHeader("Content-Type","application/json")
//                .addHeader("Accept","application/json")
//                .addHeader("X-Requested-With","XMLHttpRequest")
//                .header("Authorization","Bearer "+gettoken(context))
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                mMessage = e.getMessage();
//                Log.w("failure Response", mMessage);
//                pd.dismiss();
//                TabTwo.pullToRefresh.setRefreshing(false);
//
//                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
////                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
//                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final Dialog dialog = new Dialog(BeautyMainPage.context);
//                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
//                            final TextView confirm = dialog.findViewById(R.id.confirm);
//                            TextView message = dialog.findViewById(R.id.message);
//                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.ExuseMeAlert);
//                            message.setText(R.string.check_internet_con);
//                            confirm.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.cancel();
//
//                                }
//                            });
//                            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                @Override
//                                public void onCancel(DialogInterface dialog) {
////
//                                    Log.e("refreshDialog","ok");
//                                    final Dialog refreshDialog = new Dialog(BeautyMainPage.context);
//                                    refreshDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                                    refreshDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                                    refreshDialog.setContentView(R.layout.refresh_btn_dialog);
//                                    Button refresh=refreshDialog.findViewById(R.id.refresh);
//                                    refresh.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            automatedBrowseOffers(itemPerPage,pageNum,context);
//                                            refreshDialog.cancel();
//                                        }
//                                    });
//                                    refreshDialog.show();
//                                }
//                            });
//                            dialog.show();
//
//                        }
//                    });
//
//                }else {
//                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                mMessage = response.body().string();
////                    Log.d("token",gettoken(context));
//                Log.e("TAG123", mMessage);
//                pd.dismiss();
//              try {
//                  TabTwo.pullToRefresh.setRefreshing(false);
//              }catch (Exception e){
//                  e.printStackTrace();
//              }
//
//                try{
//                    JSONArray jsonObject=new JSONArray(mMessage);
////                    String success=jsonObject.getString("success");
////                    Log.e("success",success);
//                    String message;
////                    try {
////                        message=jsonObject.getString("message");
////                    }catch (JSONException je){
////                        message=jsonObject.getString("error");
////                    }
//                    if (jsonObject.length()!=0){
//                       for (int j=0;j<jsonObject.length();j++){
//                            JSONObject data=jsonObject.getJSONObject(j);
////                            String totalitem=data.getString("TotalItem");
//                            if (jsonObject.length()==0){
////                                TabTwo.arrayList.clear();
////                                ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
////                                    @Override
////                                    public void run() {
////                                        Toast.makeText(BeautyMainPage.context,"there is no suppliered services with your search filters",Toast.LENGTH_LONG).show();
////                                        TabOne.refreshRV();
////                                    }
////                                });
//
//                            }else {
////                                JSONArray offersArray=data.getJSONArray("offers");
////                                JSONArray supInfo=data.getJSONArray("supplier info");
////
////                                Log.e("SizeOffers",offersArray.length()+"");
//                                TabTwo.arrayList.clear();
//
//                                for (int i=0;i<jsonObject.length();i++){
//                                    JSONObject jarray = jsonObject.getJSONObject(i);
//                                    String bdb_pack_code=jarray.getString("pack_id"),
////                                            bdb_sup_name=jarray.getString("bdb_sup_name"),
////                                            totalRating_to_Sup=jarray.getString("totalRating_to_Sup"),
////                                            service_count=jarray.getString("service count"),
////                                            is_fav_sup=jarray.getString("is_fav_sup"),
////                                            bdb_offer_end=jarray.getString("bdb_offer_end"),
////                                            bdb_offer_start=jarray.getString("bdb_offer_start"),
////                                            num_of_times=jarray.getString("Num_of_times"),
//                                            oldPrice=jarray.getString("oldPrice"),
//                                            newPrice=jarray.getString("newPrice"),
//                                            discount=jarray.getString("discount"),
//                                            distance=jarray.getString("distance"),
//                                            longitude=jarray.getString("longitude"),
//                                            bdb_offer_type=jarray.getString("bdb_offer_type"),
//                                            latitude=jarray.getString("latitude");
//
//                                    JSONArray pack_data=jarray.getJSONArray("pack_data");
//                                    ArrayList<DataOffer.SupIdClass> supIdClasses=new ArrayList<>();
//                                    for (int j=0;j<pack_data.length();j++){
//                                        JSONObject object=pack_data.getJSONObject(j);
//                                        String bdb_ser_sup_id=object.getString("bdb_ser_sup_id");
//                                        String bdb_name=object.getString("bdb_ser_name");
//                                        String bdb_ser_id=object.getString("bdb_ser_id");
//                                        supIdClasses.add(new DataOffer.SupIdClass(bdb_ser_sup_id,bdb_name,bdb_ser_id));
//                                    }
//
//
//                                    DataOffer dof = new DataOffer(bdb_pack_code,bdb_sup_name,totalRating_to_Sup,service_count,is_fav_sup,bdb_offer_start,bdb_offer_end,num_of_times,oldPrice,newPrice,discount,"",bdb_offer_type,longitude,latitude,distance,supIdClasses);
//                                    TabTwo.arrayList.add(dof);
//
//
////                                    for (int j=0;j<supInfo.length();j++){
////                                        JSONObject info=supInfo.getJSONObject(j);
////                                        String name=info.getString("name");
////                                        String id=info.getString("id");
////                                        String address=info.getString("address");
////                                        ServicesTabsFragment.supInfoList.add(new SupInfoClass(name,id,address));
////                                    }
//
//                                }
//                                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        TabTwo.refreshRV();
//                                        Log.e("ARRAYLIST",TabTwo.arrayList.size()+"");
//
//                                        TabTwo.recyclerView.invalidate();
//                                    }
//                                });
//
//                            }
//
//                        }else if (message.equals("there is no providers with your search filters")){
//                            TabTwo.arrayList.clear();
//                            ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    TabOne.refreshRV();
//                                    Toast.makeText(BeautyMainPage.context,"There is no Provider with your search filter",Toast.LENGTH_LONG).show();
//                                }
//                            });
//                        }
//                    }
//                }catch (JSONException je){
//                    je.printStackTrace();
////                        there is no suppliered services with your search filters
//                    ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            TabTwo.arrayList.clear();
//                            TabTwo.refreshRV();
//                        }
//                    });
//
//                    je.printStackTrace();
//                }
//            }
//        });
//
//        Log.d("MessageResponse",mMessage);
//        return mMessage;
//    }

    //-----------------------dialogs-----------------------------------
        static Dialog dialog;
//        public static void titlemapdialog(final Context context, final String texttitle, final String textmessage, final LatLng latLng, final GoogleMap mMap, final Marker marker, final int flag) {
//            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    if (flag==2 || flag==0){
//                        dialog = new Dialog(context);
//                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                        dialog.setContentView(R.layout.map_title_layout);
//                        TextView message = dialog.findViewById(R.id.message);
//                        final TextView title = dialog.findViewById(R.id.title);
//                        title.setText(texttitle);
//                        final EditText code = dialog.findViewById(R.id.code);
//                        TextView confirm = dialog.findViewById(R.id.confirm);
//                        message.setText(textmessage);
//                        confirm.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                dialog.cancel();
//                                addtitle( code.getText().toString(),latLng,"",marker,flag,context);
//                            }
//
//
//                        });
//                        dialog.show();
//                       }else {
//                        marker.showInfoWindow();
//                    }
//                }
//
//            });
//
//        }
        public static void titlemapdialog(final Context context, final int texttitle, final int textmessage, final LatLng latLng, final GoogleMap mMap, final Marker marker, final int flag, final String add_id) {
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
                                addtitle( code.getText().toString(),latLng,add_id,marker,flag,context);
                            }


                        });
                        dialog.show();
                       }else {
                        marker.showInfoWindow();
                    }
                }

            });

        }
//        static String namelocality;
    static String  namelocality,namelocalityAr;
    static String  bdb_desc,bdb_descAr;
    static String subLocality="",adminArea="",thorourhfare="";
    static String subLocalityAr="",adminAreaAr="",thorourhfareAr="";
        private static void addtitle(final String title, final LatLng latLng,final String add_id , final Marker marker, final int flag, final Context context) {

            try {
                marker.setTitle(title);

                Locale locale= new Locale("en");
                Locale localeAr= new Locale("ar");
                Geocoder geocoder=new Geocoder(context,locale);
                Geocoder geocoderAr=new Geocoder(context,localeAr);
                List<Address> location=geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                List<Address> locationAr=geocoderAr.getFromLocation(latLng.latitude,latLng.longitude,1);

                namelocality=location.get(0).getLocality();
                subLocality=location.get(0).getSubLocality();
//            String desc=location.get(0).describeContents();
                adminArea=location.get(0).getAdminArea();
                bdb_desc=location.get(0).getAddressLine(0);
                thorourhfare=location.get(0).getThoroughfare();
                Log.e("Location_name",location.get(0).toString());
                Log.e("Location_name",namelocality);

                namelocalityAr=locationAr.get(0).getLocality();
                subLocalityAr=locationAr.get(0).getSubLocality();
//            String desc=locationAr.get(0).describeContents();
                adminAreaAr=locationAr.get(0).getAdminArea();
                thorourhfareAr=locationAr.get(0).getThoroughfare();
                bdb_descAr=locationAr.get(0).getAddressLine(0);
                Log.e("Location_name",locationAr.get(0).toString());
                Log.e("Location_name",namelocality);

                final Dialog d=new Dialog(context);
                d.setContentView(R.layout.sweet_dialog_layout_v1);
                TextView ok=d.findViewById(R.id.confirm);
                TextView message=d.findViewById(R.id.message);
                d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                TextView et_title=d.findViewById(R.id.title);

                StringBuilder locatioinInfo=new StringBuilder();
                Log.e("Language1",APICall.ln);
                Log.e("LanguageAR",ln.contains("ar")+"");

                if (ln.contains("en")) {
                    bdb_desc=bdb_desc;
                    locatioinInfo.append("Country Name: " + location.get(0).getCountryName() + "\n");
                    locatioinInfo.append("City Name: " + location.get(0).getAdminArea() + "\n");
                    locatioinInfo.append("Area: " + location.get(0).getSubAdminArea() + "\n");
                    locatioinInfo.append("Street: " + location.get(0).getFeatureName() + "\n");
                }else {
                    bdb_desc=bdb_descAr;
                    Log.e("اسم البلد:",locationAr.get(0).getLocale()+" , "+locationAr.get(0).getCountryName() );
                    locatioinInfo.append("اسم البلد: " + locationAr.get(0).getCountryName() + "\n");
                    locatioinInfo.append("اسم المدينة: " + locationAr.get(0).getAdminArea() + "\n");
                    locatioinInfo.append("    لمنطقة: " + locationAr.get(0).getSubAdminArea() + "\n");
                    locatioinInfo.append("الشارع: " + locationAr.get(0).getAddressLine(0) + "\n");
//                locatioinInfo.append("Street: " + locationAr.get(0).getAddressLine(1) + "\n");

                }
                message.setText(locatioinInfo.toString());
                if (flag==0) {
                    et_title.setText("Do You want to Add This Location ?");
                }else {
                    et_title.setText("Do You want to edit This Location with?");
                }
                message.setText(locatioinInfo.toString());
                if (flag==0) {
                    String s=((AppCompatActivity)context).getResources().getString(R.string.do_you_want_add_loc);
                    et_title.setText(s);
                }else {
                    String s=((AppCompatActivity)context).getResources().getString(R.string.do_you_want_add_loc2);
                    et_title.setText(s);
                }
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.cancel();
                        if (flag==0) {
//                            addAddress("http://clientapp.dcoret.com/api/auth/user/addAddressv1", latLng.longitude + ""
//                                    , latLng.latitude + "", namelocality, title, context);
                            addAddress("http://clientapp.dcoret.com/api/auth/user/addAddress_v1", latLng.longitude + ""
                                    , latLng.latitude + "", bdb_desc+"", title+"",
                                    "en",adminArea,namelocality+"","1",subLocality,thorourhfare,
                                    "ar",adminAreaAr,namelocalityAr+"","1",subLocalityAr,thorourhfareAr,
                                    context);
                        }else {
//                            SharedPreferences preferences=((AppCompatActivity)context).getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
//                           String data=preferences.getString("addressUser",null);
//                            getdetailsUser("http://clientapp.dcoret.com/api/auth/user/detailsUser",preferences.getString("bdb_name",null),
//                                    preferences.getString("bdb_email",null), preferences.getString("bdb_mobile",null),namelocality,title,latLng,BeautyMainPage.context);
//
//

                            APICall.updateaddress("http://clientapp.dcoret.com/api/auth/user/updateAddress_v1", add_id,latLng.longitude + ""
                                                                , latLng.latitude + "", namelocality+"", marker.getTitle()+"",
                                                                "en",adminArea,namelocality+"","1",subLocality,thorourhfare,
                                                                "ar",adminAreaAr,namelocalityAr+"","1",subLocalityAr,thorourhfareAr,
                                                                BeautyMainPage.context);

//                            updateaddress("http://clientapp.dcoret.com/api/auth/user/updateAddress",latLng.longitude+""
//                                    ,latLng.latitude+"",add_id,namelocality,titleInfo,context);
//                            updateaddress("http://clientapp.dcoret.com/api/auth/supplier/updateAddress_v1", add_id,latLng.longitude + ""
//                                    , latLng.latitude + "", namelocality+"", title+"",
//                                    "en",adminArea,namelocality+"","1",subLocality,thorourhfare,
//                                    "ar",adminAreaAr,namelocalityAr+"","1",subLocalityAr,thorourhfareAr,
//                                    context);

                        }
                    }
                });
            d.show();


            }catch (NullPointerException ne){
                String s=((AppCompatActivity)context).getResources().getString(R.string.select_loc_on_map);
                showSweetDialog(context,context.getResources().getString(R.string.ExuseMeAlert),s);
            }catch (Exception e){
                if (flag==0)
                marker.remove();
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String s=((AppCompatActivity)context).getResources().getString(R.string.err);
                        Toast.makeText(context,s,Toast.LENGTH_LONG).show();
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
        public  static  void NotAvlBookingDialog(final Context context, String texttitle, String textmessage){

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
                try {
                    searchGroupBooking(context);
                }catch (Exception e){
                    searchGroupBookingMulti("http://clientapp.dcoret.com/api/service/automatedBrowse",context);
                }
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
                dialog.cancel();
                Toast.makeText(context,"Service Reserved",Toast.LENGTH_SHORT).show();
                ((AppCompatActivity) context).finish();
                Intent intent=new Intent(context,BeautyMainPage.class);
                intent.putExtra("tabselected","bag");
                context.startActivity(intent);
            }
        });

        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.show();
            }
        });

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
        public static  void setCityId(){
//            city= "" +
////                    "{\"num\":6,\"value1\":"+id+",\"value2\":0},"+
//                    "\t{\"num\":34,\"value1\":36.47792,\"value2\":0},\n" +
//                    "\t{\"num\":35,\"value1\":36.23389,\"value2\":0}";
            city=PlaceServiceFragment.locOfferlat+
                    PlaceServiceFragment.locOfferlong;
        }

        public static String getCityId(){
            setCityId();
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
//            APICall.filterSortAlgorithm("33", "", "");

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
                        showDialog(context);
//                        pd.show();
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
                        Log.e("failure Response", mMessage);
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

//                                       Log.e("LTAccFrag", AccountFragment.locationTitles.get(i).getId() + " : " + AccountFragment.locationTitles.get(i).getBdb_my_descr());
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

         public static void getdetailsUser(final String url, final String e_bdb_name, final String e_bdb_email, final String e_bdb_mobile, final String namelocality, final String titleInfo, final LatLng latLng, final Context context){
        final SharedPreferences.Editor editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE).edit();
        SharedPreferences sh=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
            String token = ((AppCompatActivity) context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("token", null);
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            showDialog(context);
//            pd.show();
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
//                                Log.e("BDB_title_INFO",titleInfo);
//                                Log.e("BDB_title_INFO",namelocality);

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
                                        updateaddress("http://clientapp.dcoret.com/api/auth/user/updateAddress_v1", add_id,latLng.longitude + ""
                                                , latLng.latitude + "", namelocality+"", titleInfo+"",
                                                "en",adminArea,namelocality+"","1",subLocality,thorourhfare,
                                                "ar",adminAreaAr,namelocalityAr+"","1",subLocalityAr,thorourhfareAr,
                                                context);
                                        break;
                                    }
                                }
                            }catch (JSONException je){
                                je.printStackTrace();
                            }



                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    pd.dismiss();

                }

            });

            Log.d("MessageResponse", mMessage);
        }
         public static void getdetailsUser(final String url,  final String namelocality, final String titleInfo, final LatLng latLng, final LatLng oldLatlng, final Context context){
        final SharedPreferences.Editor editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE).edit();
        SharedPreferences sh=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);
            String token = ((AppCompatActivity) context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("token", null);
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            showDialog(context);
//            pd.show();
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
                                                getdetailsUser(url,namelocality,titleInfo,latLng,oldLatlng,context);
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
//                                Log.e("BDB_title_INFO",titleInfo);
//                                Log.e("BDB_title_INFO",namelocality);

//                                JSONObject jsonObject1 = new JSONObject(data);
                                JSONArray addressUser=jsonObject.getJSONArray("address");
                                Double lat,lang;
                                for (int i=0;i<addressUser.length();i++){
                                    JSONObject adr=addressUser.getJSONObject(i);
                                    lat=adr.getDouble("bdb_loc_lat");
                                    lang=adr.getDouble("bdb_loc_long");
                                    if (oldLatlng.latitude==lat && oldLatlng.longitude==lang){
                                        String add_id= adr.getString("bdb_id");
                                        Log.e("BDB_title_INFO",titleInfo);
                                        pd.dismiss();
                                        updateaddress("http://clientapp.dcoret.com/api/auth/user/updateAddress_v1", add_id,latLng.longitude + ""
                                                , latLng.latitude + "", namelocality+"", titleInfo+"",
                                                "en",adminArea,namelocality+"","1",subLocality,thorourhfare,
                                                "ar",adminAreaAr,namelocalityAr+"","1",subLocalityAr,thorourhfareAr,
                                                context);
                                        break;
                                    }
                                }
                            }catch (JSONException je){
                                je.printStackTrace();
                            }



                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
           showDialog(context);
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

            Log.e("SERCHBook",postdata.toString());

//            Log.e("SERCHBook",);

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
                                ServicesAdapter.empid23=IndividualBooking.empid.get(i);
                                Log.e("empid23",IndividualBooking.empid.get(i)+"");
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
        showDialog(context);
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
                            Log.e("empid34",IndividualBooking.empid+"");

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
        showDialog(context);
        BagReservationFragment.pullToRefresh.setRefreshing(true);
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
//        showDialog(context);
//        BagReservationFragment.pullToRefresh.setRefreshing(false);
////        pd.show();
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
        public  static  void  getServiceTime(final String bdb_ser_sup_id,final String bdb_emp_name, final String bdb_employee_id, final String bdb_price, final String bdb_ser_salon, final String bdb_ser_home, final String bdb_ser_hall, final String bdb_ser_hotel, final String bdb_start_date, final String bdb_start_time, final Context context){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
    //        showDialog(context);
    //        BagReservationFragment.pullToRefresh.setRefreshing(true);
//    //        pd.show();
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
                            APICall.addCart(bdb_ser_sup_id,bdb_employee_id,bdb_emp_name,bdb_price,bdb_ser_salon,bdb_ser_home,bdb_ser_hall,bdb_ser_hotel,bdb_start_date,bdb_start_time,bdb_time,context);
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
        public  static  void  addCart(String bdb_ser_sup_id,String bdb_employee_id,String bdb_emp_name, String bdb_price, String bdb_ser_salon,String bdb_ser_home,String bdb_ser_hall,String bdb_ser_hotel,String bdb_start_date,String bdb_start_time,String bdb_time,final Context context){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                pd.show();
            }
        });

//        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
                postdata.put("bdb_ser_sup_id",bdb_ser_sup_id);
                postdata.put("bdb_client_old","25");
                postdata.put("bdb_employee_id",bdb_employee_id);
                postdata.put("bdb_emp_name",bdb_emp_name);
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


        String post=postdata.toString();
        Log.e("postdata",post);
        RequestBody body = RequestBody.create(MEDIA_TYPE, post);

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
                    String message=j.getString("message");
                    if (success.equals("true")){

                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                APICall.showSweetDialogOnBookingDone(context);
//
                            }
                        });
                    }else if (success.equals("false")
                    && message.equals("booking times you select not avaliable right now try find another")){
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                APICall.showSweetDialog(context,((AppCompatActivity)context).getResources().getString(R.string.ExuseMeAlert),"booking times you select not avaliable right now try find another");

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



    public static Fragment fragment;
    public static FragmentManager fm;
    public static FragmentTransaction fragmentTransaction;

        public  static  void  moveCartToBooking(String bdb_id, final String bdb_is_group_booking, final int postion, final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                pd.show();
            }
        });

    //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_id",bdb_id);
            postdata.put("bdb_is_group_booking",bdb_is_group_booking);
        }catch (Exception e){
            e.printStackTrace();
        }

        String datap=postdata.toString();
        Log.e("postData",datap);
        RequestBody body = RequestBody.create(MEDIA_TYPE, datap);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/moveCartToBooking")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
//                    .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
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
//                        if (!bdb_is_group_booking) {
//                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(context, "Success Reserve", Toast.LENGTH_LONG).show();
//
//
//                                }
//                            });
//                        }else if (bdb_is_group_booking){

                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Success Reserve", Toast.LENGTH_LONG).show();
                            }
                        });

//                            grBookingListMap.remove(postion);
//                            TabOneBag.listAdapter.notifyDataSetChanged();




//                        }
                        fragment = new BagReservationTestFragment();
                        fm = ((AppCompatActivity)context).getFragmentManager();
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, fragment);
                        fragmentTransaction.commit();

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
        public  static  void  moveAllCartToBooking(String bdb_id, final String bdb_is_group_booking, final int postion, final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
//        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                showDialog(context);
////                pd.show();
//            }
//        });

    //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_id",bdb_id);
            postdata.put("bdb_is_group_booking",bdb_is_group_booking);
        }catch (Exception e){
            e.printStackTrace();
        }

        String datap=postdata.toString();
        Log.e("postData",datap);
        RequestBody body = RequestBody.create(MEDIA_TYPE, datap);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/moveCartToBooking")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
//                    .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
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
                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true")){
//                        if (!bdb_is_group_booking) {
//                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(context, "Success Reserve", Toast.LENGTH_LONG).show();
//
//
//                                }
//                            });
//                        }else if (bdb_is_group_booking){

                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                Toast.makeText(context, "Success Reserve", Toast.LENGTH_LONG).show();
                            }
                        });

//                            grBookingListMap.remove(postion);
//                            TabOneBag.listAdapter.notifyDataSetChanged();




//                        }

                        if (postion==APICall.salonBooking.size()-1) {
                            fragment = new BagReservationTestFragment();
                            fm = ((AppCompatActivity) context).getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commit();
                        }
                    }
//                    else {
//                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
//                            }
//                        });




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
        public  static  void  moveofferCartToBooking(String bdb_id, final String bdb_is_group_booking, final int postion, final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                pd.show();
            }
        });

    //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_id",bdb_id);
            postdata.put("bdb_is_group_booking",bdb_is_group_booking);
        }catch (Exception e){
            e.printStackTrace();
        }

        String datap=postdata.toString();
        Log.e("postData",datap);
        RequestBody body = RequestBody.create(MEDIA_TYPE, datap);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/moveOfferToBooking")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
//                    .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
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
//                        if (!bdb_is_group_booking) {
                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Success Reserve", Toast.LENGTH_LONG).show();


                                }
                            });
//                        }
//                        else if (bdb_is_group_booking){
//                            Toast.makeText(context, "Success Reserve", Toast.LENGTH_LONG).show();
//                            grBookingListMap.remove(postion);
//                            TabOneBag.listAdapter.notifyDataSetChanged();
//
//
//
//
//                        }
                        fragment = new BagReservationTestFragment();
                        fm = ((AppCompatActivity)context).getFragmentManager();
                        fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, fragment);
                        fragmentTransaction.commit();

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
        public  static  void  moveAllofferCartToBooking(String bdb_id, final String bdb_is_group_booking, final int postion, final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
//        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                showDialog(context);
////                pd.show();
//            }
//        });

    //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_id",bdb_id);
            postdata.put("bdb_is_group_booking",bdb_is_group_booking);
        }catch (Exception e){
            e.printStackTrace();
        }

        String datap=postdata.toString();
        Log.e("postData",datap);
        RequestBody body = RequestBody.create(MEDIA_TYPE, datap);
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/moveOfferToBooking")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
//                    .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
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
                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true")){
//                        if (!bdb_is_group_booking) {
                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Success Reserve", Toast.LENGTH_LONG).show();


                                }
                            });
//                        }
//                        else if (bdb_is_group_booking){
//                            Toast.makeText(context, "Success Reserve", Toast.LENGTH_LONG).show();
//                            grBookingListMap.remove(postion);
//                            TabOneBag.listAdapter.notifyDataSetChanged();
//
//
//
//
//                        }

                        if (postion==APICall.salonBooking.size()-1) {
                            fragment = new BagReservationTestFragment();
                            fm = ((AppCompatActivity) context).getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commit();
                        }

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
                    showDialog(context);
                    BagReservationFragment.pullToRefresh.setRefreshing(true);
//                    pd.show();
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
                                    fragment = new BagReservationTestFragment();
                                    fm = ((AppCompatActivity)context).getFragmentManager();
                                    fragmentTransaction = fm.beginTransaction();
                                    fragmentTransaction.replace(R.id.fragment, fragment);
                                    fragmentTransaction.commit();
//                                    getAllCart(context);

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
        public  static  void  deletallFromCart(String bdb_id, final int postion, final Context context){

            MediaType MEDIA_TYPE = MediaType.parse("application/json");
//            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    showDialog(context);
//                    BagReservationFragment.pullToRefresh.setRefreshing(true);
////                    pd.show();
//                }
//            });

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
//                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            pd.dismiss();
//                            BagReservationFragment.pullToRefresh.setRefreshing(false);
//                        }
//                    });

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
//                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            pd.dismiss();
//                            BagReservationFragment.pullToRefresh.setRefreshing(false);
//                        }
//                    });


                    try {
                        JSONObject j=new JSONObject(mMessage);
                        String success=j.getString("success");
                        if (success.equals("true"))
                        {
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (postion== APICall.singleBookingList.size()-1) {
                                        Toast.makeText(context, "Reserve Deleted", Toast.LENGTH_SHORT).show();
                                        fragment = new BagReservationTestFragment();
                                        fm = ((AppCompatActivity) context).getFragmentManager();
                                        fragmentTransaction = fm.beginTransaction();
                                        fragmentTransaction.replace(R.id.fragment, fragment);
                                        fragmentTransaction.commit();
                                    }
//                                    getAllCart(context);

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
        public  static  void  deletallFromCartMulti(String bdb_id, final int postion, final Context context){

            MediaType MEDIA_TYPE = MediaType.parse("application/json");
//            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    showDialog(context);
//                    BagReservationFragment.pullToRefresh.setRefreshing(true);
////                    pd.show();
//                }
//            });

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
//                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            pd.dismiss();
//                            BagReservationFragment.pullToRefresh.setRefreshing(false);
//                        }
//                    });

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
//                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            pd.dismiss();
//                            BagReservationFragment.pullToRefresh.setRefreshing(false);
//                        }
//                    });


                    try {
                        JSONObject j=new JSONObject(mMessage);
                        String success=j.getString("success");
                        if (success.equals("true"))
                        {
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if (postion== APICall.salonBooking.size()-1) {
                                        Toast.makeText(context, "Reserve Deleted", Toast.LENGTH_SHORT).show();
                                        fragment = new BagReservationTestFragment();
                                        fm = ((AppCompatActivity) context).getFragmentManager();
                                        fragmentTransaction = fm.beginTransaction();
                                        fragmentTransaction.replace(R.id.fragment, fragment);
                                        fragmentTransaction.commit();
                                    }
//                                    getAllCart(context);

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

        public static ArrayList<ReservationModel> reservationModels=new ArrayList<>();
        public static ArrayList<String> suppliersBooking=new ArrayList<>();
        public static ArrayList<String> objectbrowseBoooking=new ArrayList<>();

    public  static  void  bookingAutomatedBrowse(String language, String itemPerPage, String serviceId,String pageNum, String filter, String sort, final Context context, final int layout){
        reservationModels.clear();
        suppliersBooking.clear();
        objectbrowseBoooking.clear();
//        suppliersBooking.add("Select Employee");
        MyReservationFragment.bookingAutomatedBrowseData.clear();
        try {
//                  MyReservationFragment.reservationsAdapter.notifyDataSetChanged();
            MyReservationFragment.reservationsAdapter2.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                MyReservationFragment.reservationsAdapter=new ReservationsAdapter(context,MyReservationFragment.bookingAutomatedBrowseData,layout);
                MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(context,reservationModels,0);

                showDialog(context);
//                Reservations.pullToRefresh.setRefreshing(true);
//                pd.show();
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

//    serviceName="",empname="",startdate="",start_r_date="",bookingType="";
        String sID="",emID="",sdate="",srdate="",btype="";
        Log.e("ServiceID",MyReservationFragment.serviceId);
        Log.e("EMID",MyReservationFragment.employee_id);
        if (!MyReservationFragment.serviceId.equals("")){
            sID="\"ServiceId\":"+MyReservationFragment.serviceId+",";
        }
        if (!MyReservationFragment.employee_id.equals("")){
//            bookingFilter("3",MyReservationFragment.employee_id,"");
            emID="\t,{\"num\":4,\"value1\":"+MyReservationFragment.employee_id+"}\n";
        }

        if (!MyReservationFragment.startdate.equals("")){
            sdate="\t,{\"num\":2,\"value1\":"+MyReservationFragment.startdate+"}\n";

        }

        if (!MyReservationFragment.start_r_date.equals("")){
            srdate="\t,{\"num\":3,\"value1\":"+MyReservationFragment.start_r_date+"}\n";
        }

        if (!MyReservationFragment.bookingType.equals("")){
            if (MyReservationFragment.bookingType.equals("Single Booking"))
            {
                btype="\t,{\"num\":5,\"value1\":0}\n";
            }else {
                btype="\t,{\"num\":5,\"value1\":1}\n";

            }
            Log.e("BBOOKTTYPEE",MyReservationFragment.bookingType);
        }

        String tmp="";
        if(filter.equals("") && sort.equals("")){
            tmp="{\t\"lang\":\""+language+"\",\n" +
                    sID+
                    "\t\"ItemPerPage\":"+itemPerPage+"\n" +
                    "\t,\"PageNum\":\""+pageNum+"\"\n " +
//                    "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
                    "}";
        }else if (!filter.equals("")){
            if (sort.equals("")) {
                tmp = "{\t\"lang\":\"" + language + "\",\n" +
                        sID+
                        "\t\"ItemPerPage\":" + itemPerPage + "\n" +
                        "\t,\"PageNum\":\"" + pageNum + "\"\n ," +
                        filter +
                        emID+
                        sdate+
                        srdate+
                        btype+

                        ""+
                        //                    "\t,\"Filter\":{\"num\":1,\"value1\":\""\"}\n" +
                        "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
                        "}";
            }else {
                tmp = "{\t\"lang\":\"" + language + "\",\n" +
                        sID+
                        "\t\"ItemPerPage\":" + itemPerPage + "\n" +
                        "\t,\"PageNum\":\"" + pageNum + "\"\n ," +
                        filter
                        +emID
                        +
                        sdate+
                        srdate+
                        btype+
                        "],"+
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
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.Null);
//                            message.setText(R.string.check_internet_con);
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
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });


                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true")) {
                        if (j.getString("message").equals("no booking")) {
                            showSweetDialog(context,((AppCompatActivity)context).getResources().getString(R.string.nobooking),((AppCompatActivity)context).getResources().getString(R.string.nobookingyet));
                        }else {
                            JSONObject data = j.getJSONObject("data");
                            JSONArray bookings = data.getJSONArray("bookings");

                            Log.e("ttttttttttt", bookings + "");
                            final String totalItem = data.getString("totalItem");
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    MyReservationFragment.floatingTextButton.setTitle(totalItem);

                                }
                            });
                            for (int i = 0; i < bookings.length(); i++) {
                                JSONObject jObject = bookings.getJSONObject(i);
                                Log.e("browseOne",jObject+"");
                                objectbrowseBoooking.add(jObject+"" +
                                        "");

                                String bdb_inner_booking=jObject.getString("bdb_inner_booking");

                                String booking_price="";

                                try {
                                    booking_price = jObject.getString("booking_price");
                                    Double p=Double.parseDouble(booking_price);
                                    String price=df2.format(p);
                                    booking_price=price;
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                String booking_type = jObject.getString("booking_type");

                                JSONArray booking = jObject.getJSONArray("booking");

                                ArrayList<BookingAutomatedBrowseData> bookingAutomatedBrowseData1=new ArrayList<>();
                                for (int g = 0; g < booking.length(); g++) {

                                    JSONObject jsonObject = booking.getJSONObject(g);

//                                Log.e("Object", jsonObject + "");

                                    String bdb_client_old = jsonObject.getString("bdb_client_old");
                                    String bdb_id = jsonObject.getString("bdb_id");
                                    String bdb_price = jsonObject.getString("bdb_price");
                                    String bdb_status = jsonObject.getString("bdb_status");
                                    String bdb_start_date = jsonObject.getString("bdb_start_date");
                                    String bdb_start_time = jsonObject.getString("bdb_start_time");
                                    String supplier_name = jsonObject.getString("supplier_name");
                                    String employee_name = jsonObject.getString("employee name");
                                    String service_en_name = jsonObject.getString("service en name");
                                    String service_ar_name = jsonObject.getString("service ar name");



                                    Boolean check=false;
                                    for (int k=0;k<suppliersBooking.size();k++){
                                        if(suppliersBooking.get(k).equals(supplier_name)){
                                            check=true;
                                        }
                                    }
                                    if (!check) {
                                        suppliersBooking.add(supplier_name);
                                    }
                                    MyReservationFragment.bookingAutomatedBrowseData.add(new BookingAutomatedBrowseData(
                                            bdb_id, bdb_price, bdb_status, bdb_start_date, bdb_start_time, supplier_name, employee_name, service_en_name, service_ar_name,"","", totalItem
                                    ));
                                    bookingAutomatedBrowseData1.add(new BookingAutomatedBrowseData(
                                            bdb_id, bdb_price, bdb_status, bdb_start_date, bdb_start_time, supplier_name, employee_name, service_en_name, service_ar_name, totalItem
                                    ));
                                }
                                //-----------------------------------------------------

                                if (booking_type.equals("0")) {
                                    reservationModels.add(new ReservationModel(booking_type, booking.getJSONObject(0).getString("bdb_price"), booking.getJSONObject(0).getString("bdb_start_date"), jObject.getString("booking_place"),jObject.getString("client_name"),bdb_inner_booking, bookingAutomatedBrowseData1));
                                    Log.e("BookTypeAdded","Single");
                                }else {
                                    reservationModels.add(new ReservationModel(booking_type, booking_price, jObject.getString("bdb_start_dates"), jObject.getString("booking_place"),jObject.getString("client_name"),bdb_inner_booking, bookingAutomatedBrowseData1));
                                    Log.e("BookTypeAdded","Group");
                                }
                            }
                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("APINotify","OK");
                                    MyReservationFragment.reservationsAdapter2.notifyDataSetChanged();
                                }
                            });
                        }
                    }

                }catch (final JSONException je){
                    je.printStackTrace();
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String s=((AppCompatActivity)context).getResources().getString(R.string.an_error_occurred);
                            String t=((AppCompatActivity)context).getResources().getString(R.string.error);
                            showSweetDialog(context,t,s);
//                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });
        //        Log.d("MessageResponse",mMessage);
    }
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public  static  void  bookingAutomatedBrowse1(String language, String itemPerPage, String serviceId,String pageNum, String filter, String sort, final Context context, final int layout){
        reservationModels.clear();
        suppliersBooking.clear();
        objectbrowseBoooking.clear();
//        suppliersBooking.add("Select Employee");
        MyReservationFragment.bookingAutomatedBrowseData.clear();
        try {
//                  MyReservationFragment.reservationsAdapter.notifyDataSetChanged();
            MyReservationFragment.reservationsAdapter2.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                MyReservationFragment.reservationsAdapter=new ReservationsAdapter(context,MyReservationFragment.bookingAutomatedBrowseData,layout);
                MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(context,reservationModels,0);

                showDialog(context);
//                Reservations.pullToRefresh.setRefreshing(true);
//                pd.show();
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

//    serviceName="",empname="",startdate="",start_r_date="",bookingType="";
        String sID="",emID="",sdate="",srdate="",btype="";
        Log.e("ServiceID",MyReservationFragment.serviceId);
        Log.e("EMID",MyReservationFragment.employee_id);
        if (!MyReservationFragment.serviceId.equals("")){
            sID="\"ServiceId\":["+MyReservationFragment.serviceId+"],";
        }
        if (!MyReservationFragment.employee_id.equals("")){
//            bookingFilter("3",MyReservationFragment.employee_id,"");
            emID="\t,{\"num\":4,\"value1\":"+MyReservationFragment.employee_id+"}\n";
        }
//
//        if (!MyReservationFragment.startdate.equals("")){
//            sdate="\t,{\"num\":2,\"value1\":\""+MyReservationFragment.startdate+"\"}\n";
//
//        }

        if (!MyReservationFragment.start_r_date.equals("")){
//            srdate="\t,{\"num\":3,\"value1\":\""+MyReservationFragment.start_r_date+"\"}\n";
        }

        if (!MyReservationFragment.bookingType.equals("")){
            if (MyReservationFragment.bookingType.equals("Single Booking"))
            {
                btype="\t,{\"num\":5,\"value1\":0}\n";
            }else {
                btype="\t,{\"num\":5,\"value1\":1}\n";

            }
            Log.e("BBOOKTTYPEE",MyReservationFragment.bookingType);
        }

        String tmp="";
        if(filter.equals("") && sort.equals("")){
            tmp="{\t\"lang\":\""+language+"\",\n" +
                    sID+
                    "\t\"ItemPerPage\":"+itemPerPage+"\n" +
                    "\t,\"PageNum\":\""+pageNum+"\"\n " +
                    "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
                    "}";
        }else if (!filter.equals("")){
            if (sort.equals("")) {
                tmp = "{\t\"lang\":\"" + language + "\",\n" +
                        sID+
                        "\t\"ItemPerPage\":" + itemPerPage + "\n" +
                        "\t,\"PageNum\":\"" + pageNum + "\"\n ," +
                        filter +
                        emID+
                        sdate+
                        srdate+
                        btype+

                        "]"+
                        //                    "\t,\"Filter\":{\"num\":1,\"value1\":\""\"}\n" +
                        "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
                        "}";
            }else {
                tmp = "{\t\"lang\":\"" + language + "\",\n" +
                        sID+
                        "\t\"ItemPerPage\":" + itemPerPage + "\n" +
                        "\t,\"PageNum\":\"" + pageNum + "\"\n ," +
                        filter
                        +emID
                        +
                        sdate+
                        srdate+
                        btype+
                        "],"+
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
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.Null);
//                            message.setText(R.string.check_internet_con);
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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                mMessage = response.body().string();
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
                    if (success.equals("true")) {
                        if (j.getString("message").equals("no booking")) {
                            showSweetDialog(context,((AppCompatActivity)context).getResources().getString(R.string.nobooking),((AppCompatActivity)context).getResources().getString(R.string.nobookingyet));
                        }else {
                            JSONObject data = j.getJSONObject("data");
                            JSONArray bookings = data.getJSONArray("bookings");

                            Log.e("ttttttttttt", bookings + "");
                            final String totalItem = data.getString("totalItem");
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    MyReservationFragment.floatingTextButton.setTitle(totalItem);

                                }
                            });
                            for (int i = 0; i < bookings.length(); i++) {
                                JSONObject jObject = bookings.getJSONObject(i);

                                String booking_type = jObject.getString("booking_type");
                                String bdb_inner_booking = jObject.getString("bdb_inner_booking");
                                String bdb_booked_at="",bdb_start_dates="",bdb_start_date="";
                                try {
                                    bdb_start_dates = jObject.getString("bdb_start_dates");
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                try {
                                     bdb_booked_at = jObject.getJSONArray("booking").getJSONObject(0).getString("bdb_booked_at");
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                String client_name = jObject.getString("client_name");

                                String booking_price="";
                                try {
                                    booking_price = jObject.getString("booking_price");
                                    Double p=Double.parseDouble(booking_price);
                                    String price=df2.format(p);
                                    booking_price=price;
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                JSONArray booking = jObject.getJSONArray("booking");

                                ArrayList<BookingAutomatedBrowseData> bookingAutomatedBrowseData1=new ArrayList<>();
                                for (int g = 0; g < booking.length(); g++) {

                                    JSONObject jsonObject = booking.getJSONObject(g);

//                                Log.e("Object", jsonObject + "");

                                    String bdb_client_old = jsonObject.getString("bdb_client_old");
                                    String bdb_id = jsonObject.getString("bdb_id");
                                    String bdb_price = jsonObject.getString("bdb_price");
                                    String bdb_status = jsonObject.getString("bdb_status");
                                     bdb_start_date = jsonObject.getString("bdb_start_date");
                                    String bdb_start_time = jsonObject.getString("bdb_start_time");
                                    String supplier_name = jsonObject.getString("supplier_name");
                                    String employee_name = jsonObject.getString("employee name");
                                    String service_en_name = jsonObject.getString("service en name");
                                    String service_ar_name = jsonObject.getString("service ar name");

                                    Double p=Double.parseDouble(bdb_price);
                                    String price=df2.format(p);


                                    Boolean check=false;
                                    for (int k=0;k<suppliersBooking.size();k++){
                                        if(suppliersBooking.get(k).equals(supplier_name)){
                                            check=true;
                                        }
                                    }
                                    if (!check) {
                                        suppliersBooking.add(supplier_name);
                                    }


                                    MyReservationFragment.bookingAutomatedBrowseData.add(new BookingAutomatedBrowseData(bdb_id, price, bdb_status, bdb_start_date, bdb_start_time, supplier_name, employee_name, service_en_name, service_ar_name,client_name,booking_price, totalItem));
                                    bookingAutomatedBrowseData1.add(new BookingAutomatedBrowseData(bdb_id, price, bdb_status, bdb_start_date, bdb_start_time, supplier_name, employee_name, service_en_name, service_ar_name,client_name,booking_price, totalItem));

                                }
                                //-----------------------------------------------------
                                Log.e("grBooking",MyReservationFragment.groupbooking);
                                Log.e("sdate",MyReservationFragment.startdate);
                                Log.e("sdates",bdb_start_dates);
                                Log.e("s_d_txt",MyReservationFragment.service_date_txt);
                                Log.e("book at",bdb_booked_at);

                                Boolean execDateCheck=true;
                                Boolean bookatCehck=true;

                                if ( !MyReservationFragment.startdate.equals(""))
                                execDateCheck=checkRangeDate(MyReservationFragment.startdate,bdb_start_date,MyReservationFragment.sday,MyReservationFragment.smonth
                                ,MyReservationFragment.eday,MyReservationFragment.emonth);

                                    if ( !bdb_booked_at.equals("null") && !MyReservationFragment.service_date_txt.equals(""))
                                      bookatCehck=checkRangeDate(MyReservationFragment.service_date_txt,bdb_booked_at,MyReservationFragment.srday,MyReservationFragment.srmonth
                                        ,MyReservationFragment.erday,MyReservationFragment.ermonth);
                                Log.e("ExecCehck",execDateCheck+"");
                                Log.e("BookCehck",bookatCehck+"");
                                Log.e("BookCehck1",MyReservationFragment.startdate+"");


                                if (MyReservationFragment.tab.equals("1")){
                                if (booking_type.equals("0") || booking_type.equals("10")) {
                                    if (MyReservationFragment.groupbooking.equals("0") ||MyReservationFragment.groupbooking.equals("")){
//                                        if (MyReservationFragment.startdate.equals("")|| MyReservationFragment.startdate.equals(bdb_booked_at)) {
//                                            if (MyReservationFragment.service_date_txt.equals("") || MyReservationFragment.service_date_txt.equals(bdb_start_date))

                                            if (execDateCheck && bookatCehck)
                                                reservationModels.add(new ReservationModel(booking_type, booking.getJSONObject(0).getString("bdb_price"), booking.getJSONObject(0).getString("bdb_start_date"), jObject.getString("booking_place"),jObject.getString("client_name"),bdb_inner_booking, bookingAutomatedBrowseData1));
                                            Log.e("BookTypeAdded", "Single");
//                                        }
                                    }
                                }else if (booking_type.equals("1") || booking_type.equals("2") || booking_type.equals("3")||
                                    booking_type.equals("11") || booking_type.equals("12") || booking_type.equals("13")){
                                    if(booking_type.equals(MyReservationFragment.groupbooking) || MyReservationFragment.groupbooking.equals("")) {
//                                        if (MyReservationFragment.startdate.equals("") || MyReservationFragment.startdate.equals(bdb_booked_at)){
//                                            if (MyReservationFragment.service_date_txt.equals("") || MyReservationFragment.service_date_txt.equals(bdb_start_dates))
                                        if (execDateCheck && bookatCehck)
                                                reservationModels.add(new ReservationModel(booking_type, booking_price, jObject.getString("bdb_start_dates"), jObject.getString("booking_place"),jObject.getString("client_name"),bdb_inner_booking, bookingAutomatedBrowseData1));
                                            Log.e("BookTypeAdded", "Group");
//                                        }
                                    }
                                }
                                }else if(MyReservationFragment.tab.equals("2")) {
                                    if (booking_type.equals("4") || booking_type.equals("5") || booking_type.equals("6") ||
                                            booking_type.equals("7") || booking_type.equals("8") || booking_type.equals("9")){
                                        if(booking_type.equals(MyReservationFragment.groupbooking) || MyReservationFragment.groupbooking.equals("")) {
                                            if (execDateCheck && bookatCehck)
                                                reservationModels.add(new ReservationModel(booking_type, booking_price, jObject.getString("bdb_start_dates"), jObject.getString("booking_place"),jObject.getString("client_name"),bdb_inner_booking, bookingAutomatedBrowseData1));
                                                Log.e("BookTypeAdded", "Group");
//                                            }
                                        }
                                    }
                                }
                            }


                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("APINotify","OK");
                                    MyReservationFragment.reservationsAdapter2.notifyDataSetChanged();
                                }
                            });
                        }
                    }

                }catch (final JSONException je){
                    je.printStackTrace();
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


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean checkRangeDate(String txt, String start_date, int sday, int smonth, int eday, int emonth ){
        SimpleDateFormat formatter1=new SimpleDateFormat("yyyy-MM-dd");

        try {
                if (!txt.equals("")) {
                    Date date1 = formatter1.parse(start_date);
                    LocalDate date = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    if (date.getMonthValue() >= smonth && date.getMonthValue() <= emonth
                            && date.getDayOfMonth() >= sday && date.getDayOfMonth() <= eday
                    ) {
                        Log.e("ExecCehck0", true + "1");
                        return true;

                    } else {
                        Log.e("ExecCehck1", false + "1");
                        return false;
                    }
                }


        }catch (Exception e){
            e.printStackTrace();
        }
        return false;

    }


    public  static  void  bookingAutomatedBrowseoffer(String language, String itemPerPage, String serviceId,String pageNum, String filter, String sort, final Context context, final int layout){
        reservationModels.clear();
        suppliersBooking.clear();
        objectbrowseBoooking.clear();
//        suppliersBooking.add("Select Employee");
        MyReservationFragment.bookingAutomatedBrowseData.clear();
        try {
//                  MyReservationFragment.reservationsAdapter.notifyDataSetChanged();
            MyReservationFragment.reservationsAdapter2.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
        }
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                MyReservationFragment.reservationsAdapter=new ReservationsAdapter(context,MyReservationFragment.bookingAutomatedBrowseData,layout);
                MyReservationFragment.reservationsAdapter2=new ReservationsAdapter2(context,reservationModels,0);

                showDialog(context);
//                Reservations.pullToRefresh.setRefreshing(true);
//                pd.show();
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

//    serviceName="",empname="",startdate="",start_r_date="",bookingType="";
        String sID="",emID="",sdate="",srdate="",btype="";
        Log.e("ServiceID",MyReservationFragment.serviceId);
        Log.e("EMID",MyReservationFragment.employee_id);
        if (!MyReservationFragment.serviceId.equals("")){
            sID="\"ServiceId\":"+MyReservationFragment.serviceId+",";
        }
        if (!MyReservationFragment.employee_id.equals("")){
//            bookingFilter("3",MyReservationFragment.employee_id,"");
            emID="\t,{\"num\":4,\"value1\":"+MyReservationFragment.employee_id+"}\n";
        }

        if (!MyReservationFragment.startdate.equals("")){
            sdate="\t,{\"num\":2,\"value1\":"+MyReservationFragment.startdate+"}\n";

        }

        if (!MyReservationFragment.start_r_date.equals("")){
            srdate="\t,{\"num\":3,\"value1\":"+MyReservationFragment.start_r_date+"}\n";
        }

        if (!MyReservationFragment.bookingType.equals("")){
            if (MyReservationFragment.bookingType.equals("Single Booking"))
            {
                btype="\t,{\"num\":5,\"value1\":0}\n";
            }else {
                btype="\t,{\"num\":5,\"value1\":1}\n";

            }
            Log.e("BBOOKTTYPEE",MyReservationFragment.bookingType);
        }

        String tmp="";
//        if(filter.equals("") && sort.equals("")){
            tmp="{\t\"lang\":\""+language+"\",\n" +
                    sID+
                    "\t\"ItemPerPage\":"+itemPerPage+"\n" +
                    "\t,\"PageNum\":\""+pageNum+"\"\n " +
//                    "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
                    "}";
//        }
//        else if (!filter.equals("")){
//            if (sort.equals("")) {
//                tmp = "{\t\"lang\":\"" + language + "\",\n" +
//                        sID+
//                        "\t\"ItemPerPage\":" + itemPerPage + "\n" +
//                        "\t,\"PageNum\":\"" + pageNum + "\"\n ," +
//                        filter +
//                        emID+
//                        sdate+
//                        srdate+
//                        btype+
//
//                        ""+
//                        //                    "\t,\"Filter\":{\"num\":1,\"value1\":\""\"}\n" +
//                        "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
//                        "}";
//            }else {
//                tmp = "{\t\"lang\":\"" + language + "\",\n" +
//                        sID+
//                        "\t\"ItemPerPage\":" + itemPerPage + "\n" +
//                        "\t,\"PageNum\":\"" + pageNum + "\"\n ," +
//                        filter
//                        +emID
//                        +
//                        sdate+
//                        srdate+
//                        btype+
//                        "],"+
//                        sort+
//                        //                        "\t,\"sort\":{\"num\":1,\"by\":\"desc\"}\n" +
//                        "}";
//            }
//        }

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
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.Null);
//                            message.setText(R.string.check_internet_con);
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
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });


                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true")) {
                        if (j.getString("message").equals("no booking")) {
                            showSweetDialog(context,((AppCompatActivity)context).getResources().getString(R.string.nobooking),((AppCompatActivity)context).getResources().getString(R.string.nobookingyet));
                        }else {
                            JSONObject data = j.getJSONObject("data");
                            JSONArray bookings = data.getJSONArray("bookings");

                            Log.e("ttttttttttt", bookings + "");
                            final String totalItem = data.getString("totalItem");
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
//                                    MyReservationFragment.floatingTextButton.setTitle(totalItem);

                                }
                            });
                            for (int i = 0; i < bookings.length(); i++) {
                                JSONObject jObject = bookings.getJSONObject(i);
                                Log.e("browseOne",jObject+"");

                                String bdb_inner_booking=jObject.getString("bdb_inner_booking");


                                objectbrowseBoooking.add(jObject+"" +
                                        "");
                                String booking_price="";

                                try {
                                    booking_price = jObject.getString("booking_price");
                                    Double p=Double.parseDouble(booking_price);
                                    String price=df2.format(p);
                                    booking_price=price;
                                }catch (Exception e){
                                    e.printStackTrace();
                                }

                                String booking_type = jObject.getString("booking_type");
                                JSONArray booking = jObject.getJSONArray("booking");

                                ArrayList<BookingAutomatedBrowseData> bookingAutomatedBrowseData1=new ArrayList<>();
                                for (int g = 0; g < booking.length(); g++) {

                                    JSONObject jsonObject = booking.getJSONObject(g);

//                                Log.e("Object", jsonObject + "");

                                    String bdb_client_old = jsonObject.getString("bdb_client_old");
                                    String bdb_id = jsonObject.getString("bdb_id");
                                    String bdb_price = jsonObject.getString("bdb_price");
                                    String bdb_status = jsonObject.getString("bdb_status");
                                    String bdb_start_date = jsonObject.getString("bdb_start_date");
                                    String bdb_start_time = jsonObject.getString("bdb_start_time");
                                    String supplier_name = jsonObject.getString("supplier_name");
                                    String employee_name = jsonObject.getString("employee name");
                                    String service_en_name = jsonObject.getString("service en name");
                                    String service_ar_name = jsonObject.getString("service ar name");
                                    Boolean check=false;
                                    for (int k=0;k<suppliersBooking.size();k++){
                                        if(suppliersBooking.get(k).equals(supplier_name)){
                                            check=true;
                                        }
                                    }
                                    if (!check) {
                                        suppliersBooking.add(supplier_name);
                                    }
                                    MyReservationFragment.bookingAutomatedBrowseData.add(new BookingAutomatedBrowseData(
                                            bdb_id, bdb_price, bdb_status, bdb_start_date, bdb_start_time, supplier_name, employee_name, service_en_name, service_ar_name, totalItem
                                    ));
                                    bookingAutomatedBrowseData1.add(new BookingAutomatedBrowseData(
                                            bdb_id, bdb_price, bdb_status, bdb_start_date, bdb_start_time, supplier_name, employee_name, service_en_name, service_ar_name, totalItem
                                    ));
                                }
                                //-----------------------------------------------------

//                                if (booking_type.equals("0")) {
//                                    reservationModels.add(new ReservationModel(booking_type, booking.getJSONObject(0).getString("bdb_price"), booking.getJSONObject(0).getString("bdb_start_date"), jObject.getString("booking_place"), bookingAutomatedBrowseData1));
//                                    Log.e("BookTypeAdded","Single");
//                                }else
                                    if (booking_type.equals("4") ||booking_type.equals("5") ||booking_type.equals("6")
                                    ||booking_type.equals("7") ||booking_type.equals("8") ||booking_type.equals("9")){
                                    reservationModels.add(new ReservationModel(booking_type, booking_price, jObject.getString("bdb_start_dates"), jObject.getString("booking_place"),jObject.getString("client_name"),bdb_inner_booking, bookingAutomatedBrowseData1));
                                    Log.e("BookTypeAdded","Group");
                                }
                            }
                            ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.e("APINotify","OK");
                                    MyReservationFragment.reservationsAdapter2.notifyDataSetChanged();
                                }
                            });
                        }
                    }

                }catch (final JSONException je){
                    je.printStackTrace();
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String t=((AppCompatActivity)context).getResources().getString(R.string.an_error_occurred);
                            String m=((AppCompatActivity)context).getResources().getString(R.string.error);
                            showSweetDialog(context,m,t);
//                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }

        });
        //        Log.d("MessageResponse",mMessage);
    }



    public  static  void  bookingAutomatedBrowse(String language,String itemPerPage,String pageNum,String filter,String sort,final Context context){

            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showDialog(context);
                    ReservationFragment.pullToRefresh.setRefreshing(true);
//                    pd.show();
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



    public  static  void  getServices(final String bdb_is_bride, final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                ReservationFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try{
            postdata.put("bdb_type",bdb_is_bride);
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
                            String   bdb_is_bride_service=jsonObject.getString("bdb_is_bride_service");
                            String   bdb_is_fixed_price=jsonObject.getString("bdb_is_fixed_price");
                            String   images=jsonObject.getString("images");


                            if(bdb_is_bride.equals("2")){
                                GroupReservationFragment.servicesList.add(new ServiceItems(bdb_ser_id, bdb_name, bdb_name_ar, bdb_descr, bdb_type, bdb_is_fixed_price,bdb_is_bride_service, images));
                                GroupReservationOthersFragment.servicesList.add(new ServiceItems(bdb_ser_id, bdb_name, bdb_name_ar, bdb_descr, bdb_type, bdb_is_fixed_price, bdb_is_bride_service,images));
                                GroupReservationFragment.serviceNameList.add(bdb_name);
                                GroupReservationOthersFragment.serviceNameList.add(bdb_name);
                            }else
                            if (bdb_is_bride.equals(bdb_is_bride_service)) {
                                GroupReservationFragment.servicesList.add(new ServiceItems(bdb_ser_id, bdb_name, bdb_name_ar, bdb_descr, bdb_type, bdb_is_fixed_price,bdb_is_bride_service, images));
                                GroupReservationOthersFragment.servicesList.add(new ServiceItems(bdb_ser_id, bdb_name, bdb_name_ar, bdb_descr, bdb_type, bdb_is_fixed_price, bdb_is_bride_service,images));
                                GroupReservationFragment.serviceNameList.add(bdb_name);
                                GroupReservationOthersFragment.serviceNameList.add(bdb_name);
                            }
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

    public static ArrayList<ServiceItems> serviceForFilter=new ArrayList<>();
    public  static  void  getServicesForFilter(final String bdb_is_bride, final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
//        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                showDialog(context);
////                ReservationFragment.pullToRefresh.setRefreshing(true);
////                pd.show();
//            }
//        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try{
            postdata.put("bdb_type",bdb_is_bride);
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
//                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pd.dismiss();
////                        ReservationFragment.pullToRefresh.setRefreshing(false);
//                    }
//                });
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
//                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pd.dismiss();
////                        ReservationFragment.pullToRefresh.setRefreshing(false);
//                    }
//                });


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
                            String   bdb_is_bride_service=jsonObject.getString("bdb_is_bride_service");
                            String   bdb_is_fixed_price=jsonObject.getString("bdb_is_fixed_price");
                            String   images=jsonObject.getString("images");


//                            if(bdb_is_bride.equals("2")){
//                                GroupReservationFragment.servicesList.add(new ServiceItems(bdb_ser_id, bdb_name, bdb_name_ar, bdb_descr, bdb_type, bdb_is_fixed_price,bdb_is_bride_service, images));
                            serviceForFilter.add(new ServiceItems(bdb_ser_id, bdb_name, bdb_name_ar, bdb_descr, bdb_type, bdb_is_fixed_price, bdb_is_bride_service,images));
//                                GroupReservationFragment.serviceNameList.add(bdb_name);
//                                GroupReservationOthersFragment.serviceNameList.add(bdb_name);
//                            }else
//                            if (bdb_is_bride.equals(bdb_is_bride_service)) {
//                                GroupReservationFragment.servicesList.add(new ServiceItems(bdb_ser_id, bdb_name, bdb_name_ar, bdb_descr, bdb_type, bdb_is_fixed_price,bdb_is_bride_service, images));
//                                GroupReservationOthersFragment.servicesList.add(new ServiceItems(bdb_ser_id, bdb_name, bdb_name_ar, bdb_descr, bdb_type, bdb_is_fixed_price, bdb_is_bride_service,images));
//                                GroupReservationFragment.serviceNameList.add(bdb_name);
//                                GroupReservationOthersFragment.serviceNameList.add(bdb_name);
//                            }
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


    public  static  void  getServicesForMulti(final String is_bride, final Context context){
            Log.e("CVDSIZE","11111 : "+GroupReservationFragment.clientsViewData.size()+"");

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                ReservationFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try{
//            postdata.put("bdb_type",bdb_type);
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
                            String   bdb_is_bride_service=jsonObject.getString("bdb_is_bride_service");
                            String   images=jsonObject.getString("images");

                            if(is_bride.equals("2")){
                                MultiIndividualBookingReservationFragment.servicesList.add(new ServiceItems(bdb_ser_id, bdb_name, bdb_name_ar, bdb_descr, bdb_type, bdb_is_fixed_price,bdb_is_fixed_price, images));
//                            MultiIndividualBookingReservationFragment.servicesList.add(new ServiceItems(bdb_ser_id,bdb_name,bdb_name_ar,bdb_descr,bdb_type,bdb_is_fixed_price,images));
                                MultiIndividualBookingReservationFragment.serviceNameList.add(bdb_name);
//                            MultiIndividualBookingReservationFragment.serviceNameList.add(bdb_name);
                            }else
                            if (is_bride.equals(bdb_is_bride_service)) {
                                MultiIndividualBookingReservationFragment.servicesList.add(new ServiceItems(bdb_ser_id, bdb_name, bdb_name_ar, bdb_descr, bdb_type, bdb_is_fixed_price,bdb_is_fixed_price, images));
//                            MultiIndividualBookingReservationFragment.servicesList.add(new ServiceItems(bdb_ser_id,bdb_name,bdb_name_ar,bdb_descr,bdb_type,bdb_is_fixed_price,images));
                                MultiIndividualBookingReservationFragment.serviceNameList.add(bdb_name);
//                            MultiIndividualBookingReservationFragment.serviceNameList.add(bdb_name);
                            }
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

//
//            latlng="\t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
//                    "\t{\"num\":35,\"value1\":39.2147311,\"value2\":0},";
//            Log.e("LatLong",latlng);
        return latlng;
    }
    public static String getPrice(){
            String price="";

            Log.e("Price",PlaceServiceGroupFragment.minPrice+"-"+PlaceServiceGroupFragment.maxPrice);
            if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()==1) {
                price = "{\"num\":32,\"value1\":" + PlaceServiceGroupFragment.minPrice + ",\"value2\":"+ PlaceServiceGroupFragment.maxPrice+"},";
                price=price+"{\"num\":9,\"value1\":1,\"value2\":0}";

            }else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()==2) {

                price = "{\"num\":1,\"value1\":" + PlaceServiceGroupFragment.minPrice + ",\"value2\":"+ PlaceServiceGroupFragment.maxPrice+"},";
                price=price+"{\"num\":8,\"value1\":1,\"value2\":0}";

            }else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()==3) {
                price = "{\"num\":30,\"value1\":" + PlaceServiceGroupFragment.minPrice + ",\"value2\":"+ PlaceServiceGroupFragment.maxPrice+"},";
                price=price+"{\"num\":10,\"value1\":1,\"value2\":0}";

            }else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()==4) {
                price = "{\"num\":31,\"value1\":" + PlaceServiceGroupFragment.minPrice + ",\"value2\":"+ PlaceServiceGroupFragment.maxPrice+"},";
                price=price+"{\"num\":11,\"value1\":1,\"value2\":0}";

            }
        return price;
    }
    public static String getPricePlace(){
            String price;
            price="{\"num\":11,\"value1\":"+PlaceServiceGroupFragment.placeId+",\"value2\":0}";
//        Log.e("price",price);
        return price;
    }
    public static  String getDate(){
        String date;
        date="\"date\":\""+PlaceServiceGroupFragment.dateFilter+"\",\"multi_salon_client\": "+ClientRelationsFragment.multi_salon_client+",  \"multi_salon_clients_rel\": "+ClientRelationsFragment.multi_salon_clients_rel+",";
//        Log.e("Date",date);
        return date;
    }
    public static String getClients(){
            String clients = null;
        try {

            for (int i=0;i<GroupReservationFragment.clientsViewData.size();i++) {
                if (i == 0) {
                    clients = "\t{\"client_name\":\"" +
                            GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+
                            GroupReservationFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+
                            GroupReservationFragment.clientsViewData.get(i).getIs_current_user()+
//                            ",\"rel\": \""+
                            ",\"rel\":\"0\" "+
//                            ClientRelationsFragment.relations.get(i)
//                            +
                                    ",\"services\":[\n";
                } else {
//                    clients = clients + "\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                    clients = clients+"\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+GroupReservationFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+GroupReservationFragment.clientsViewData.get(i).getIs_current_user()
                            +",\"rel\":\"0\" "
// \""+ClientRelationsFragment.relations.get(i)
                            +",\"services\":[\n";

                }
                Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());

                    for (int j = 0; j < GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                        if (j == 0) {
                            clients = clients + "{\"ser_id\":" +GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId() + ",\"ser_time\":60}\n";
                        } else {
                            clients = clients + ",{\"ser_id\":" + GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId()+ ",\"ser_time\":60}\n";
                        }
                        Log.e("Ser_Id",GroupReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId());
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

//            Log.e("clients",clients);
        return clients;
    }
    public static String GroupFilterBooking(){
            String filter;

            filter="{\"Filter\":\t[\n" +
                    getlatlng()+
                    getPrice()+
//                    getPricePlace()+
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
        salons.clear();
        stringArrayListMap.clear();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
                GroupReservationResultFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();

            String filter=GroupFilterBooking();
//            String ff="{\"Filter\":\t[\n" +
//                    "    \t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
//                    "    \t{\"num\":35,\"value1\":39.2147311,\"value2\":0},{\"num\":1,\"value1\":0,\"value2\":1000},{\"num\":8,\"value1\":1,\"value2\":0}\t],\n" +
//                    "    \"date\":\"2019-7-4\",\t\t\"clients\":[\t{\"client_name\":\"null\",\"client_phone\":\"0500500500\",\"is_current_user\":1,\"services\":[\n" +
//                    "    {\"ser_id\":2,\"ser_time\":60}\n" +
//                    "    ]},\t{\"client_name\":\"c1\",\"client_phone\":\"1\",\"is_current_user\":0,\"services\":[\n" +
//                    "    {\"ser_id\":1,\"ser_time\":60}\n" +
//                    "    ,{\"ser_id\":2,\"ser_time\":60}\n" +
//                    "    ]}]}";

        Log.e("GroupFilter",filter);

        RequestBody body = RequestBody.create(MEDIA_TYPE, filter);

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/searchGroupBooking")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
                //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();
//        Log.e("Req-Search",request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        GroupReservationResultFragment.pullToRefresh.setRefreshing(false);

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
                        GroupReservationResultFragment.pullToRefresh.setRefreshing(false);
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
                            JSONObject completeSolutions = completeSolutions1.getJSONObject(l);

                            //------------------ not used until now-------------
                            String total_price=completeSolutions.getString("total_price");

                            salons.add(completeSolutions.getJSONArray("solution").getJSONObject(0).getString("salon_name"));

                                    JSONArray sol=completeSolutions.getJSONArray("solution");

                                    for (int i = 0; i < sol.length(); i++) {

                                        JSONObject data = sol.getJSONObject(i);
                                String salon_id = data.getString("salon_id");
                                String salonName = data.getString("salon_name");
                                JSONObject client_response = data.getJSONObject("client_response");
                                String is_current_user = client_response.getString("is_current_user");
                                //-----------phone number=----------
                                String salon_name = client_response.getString("client_phone");
                                String client_id = client_response.getString("client_id");
                                String client_name = client_response.getString("client_name");
                                JSONArray solutions = client_response.getJSONArray("solutions");
//                                ArrayList<SerchGroupBookingData.Solutions> solutionsArrayList = new ArrayList<>();

                                ArrayList<SearchBookingDataSTR.Solution> solutionsArr=new ArrayList<>();
                                for (int k = 0; k < solutions.length(); k++) {
                                    JSONObject data1 = solutions.getJSONObject(k);
                                    Log.e("data1",data1+"");
                                    String ser_id = data1.getString("ser_id");
                                    String ser_name = data1.getString("ser_name");
                                    String ser_name_ar = data1.getString("ser_name_ar");
                                    String emp_id = data1.getString("emp_id");
                                    String emp_name = data1.getString("emp_name");
                                    String sup_id = data1.getString("sup_id");
                                    String ser_sup_id = data1.getString("ser_sup_id");
                                    String from = data1.getString("from");
                                    String to = data1.getString("to");
                                    String client_name1 = data1.getString("client_name");
                                    String bdb_ser_home_price = data1.getString("bdb_ser_home_price");
                                    String bdb_ser_hall_price = data1.getString("bdb_ser_hall_price");
                                    String bdb_hotel_price = data1.getString("bdb_hotel_price");
                                    String bdb_ser_salon_price = data1.getString("bdb_ser_salon_price");
                                    String bdb_ser_home = data1.getString("bdb_ser_home");
                                    String bdb_ser_salon = data1.getString("bdb_ser_salon");
                                    String bdb_ser_hall = data1.getString("bdb_ser_salon");
                                    String bdb_hotel = data1.getString("bdb_hotel");


//                                    solutionsArrayList.add(new SerchGroupBookingData.Solutions(ser_id, emp_id, sup_id, ser_sup_id, from, to, old_from, old_to, new_from, new_to, client_name, ser_name, ser_name_ar,is_current_user));
                                    solutionsArr.add(new SearchBookingDataSTR.Solution(ser_id,ser_name,ser_name_ar,emp_id,emp_name,sup_id,ser_sup_id,from,to,bdb_ser_home_price,bdb_ser_hall_price,bdb_hotel_price,bdb_ser_salon_price,bdb_ser_home,bdb_ser_salon,bdb_ser_hall,bdb_hotel));
                                }


                                searchBookingDataSTRS.add(new SearchBookingDataSTR(salon_id,salon_name,total_price,client_name,client_name,is_current_user,client_id,solutionsArr));
                               stringArrayListMap.put(salons.get(l),searchBookingDataSTRS);

                            }
                            GroupReservationFragment.serchGroupBookingData.add(new SerchGroupBookingData(GroupReservationFragment.solutionsCounts));

                        }
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


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

                        searchGroupBooking2(context);
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


    public  static  void  searchGroupBookingBag( final Context context){
        salons.clear();
        stringArrayListMap.clear();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                ReservationFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();

        String ff="{\"Filter\":\t[\n" +
                "    \t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
                "    \t{\"num\":35,\"value1\":39.2147311,\"value2\":0},{\"num\":1,\"value1\":0,\"value2\":1000},{\"num\":8,\"value1\":1,\"value2\":0}\t],\n" +
                "    \"date\":\"2019-7-4\",\t\t\"clients\":[\t{\"client_name\":\"null\",\"client_phone\":\"0500500500\",\"is_current_user\":1,\"services\":[\n" +
                "    {\"ser_id\":2,\"ser_time\":60}\n" +
                "    ]},\t{\"client_name\":\"c1\",\"client_phone\":\"1\",\"is_current_user\":0,\"services\":[\n" +
                "    {\"ser_id\":1,\"ser_time\":60}\n" +
                "    ,{\"ser_id\":2,\"ser_time\":60}\n" +
                "    ]}]}";

        RequestBody body = RequestBody.create(MEDIA_TYPE, ff);

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
                            JSONObject completeSolutions = completeSolutions1.getJSONObject(l);

                            //------------------ not used until now-------------
                            String total_price=completeSolutions.getString("total_price");

                            salons.add(completeSolutions.getJSONArray("solution").getJSONObject(0).getString("salon_name"));

                            JSONArray sol=completeSolutions.getJSONArray("solution");

                            for (int i = 0; i < sol.length(); i++) {
                                JSONObject data = sol.getJSONObject(i);
                                String salon_id = data.getString("salon_id");
                                String salonName = data.getString("salon_name");
                                JSONObject client_response = data.getJSONObject("client_response");
                                String is_current_user = client_response.getString("is_current_user");
                                //-----------phone number=----------
                                String salon_name = client_response.getString("client_phone");
                                String client_id = client_response.getString("client_id");
                                String client_name = client_response.getString("client_name");
                                JSONArray solutions = client_response.getJSONArray("solutions");
//                                ArrayList<SerchGroupBookingData.Solutions> solutionsArrayList = new ArrayList<>();

                                ArrayList<SearchBookingDataSTR.Solution> solutionsArr=new ArrayList<>();
                                for (int k = 0; k < solutions.length(); k++) {
                                    JSONObject data1 = solutions.getJSONObject(k);
                                    Log.e("data1",data1+"");
                                    String ser_id = data1.getString("ser_id");
                                    String ser_name = data1.getString("ser_name");
                                    String ser_name_ar = data1.getString("ser_name_ar");
                                    String emp_id = data1.getString("emp_id");
                                    String emp_name = data1.getString("emp_name");
                                    String sup_id = data1.getString("sup_id");
                                    String ser_sup_id = data1.getString("ser_sup_id");
                                    String from = data1.getString("from");
                                    String to = data1.getString("to");
//                                    String old_from = data1.getString("old_from");
//                                    String old_to = data1.getString("old_to");
//                                    String new_from = data1.getString("new_from");
//                                    String new_to = data1.getString("new_to");
                                    String client_name1 = data1.getString("client_name");
                                    String bdb_ser_home_price = data1.getString("bdb_ser_home_price");
                                    String bdb_ser_hall_price = data1.getString("bdb_ser_hall_price");
                                    String bdb_hotel_price = data1.getString("bdb_hotel_price");
                                    String bdb_ser_salon_price = data1.getString("bdb_ser_salon_price");
                                    String bdb_ser_home = data1.getString("bdb_ser_home");
                                    String bdb_ser_salon = data1.getString("bdb_ser_salon");
                                    String bdb_ser_hall = data1.getString("bdb_ser_salon");
                                    String bdb_hotel = data1.getString("bdb_hotel");


//                                    solutionsArrayList.add(new SerchGroupBookingData.Solutions(ser_id, emp_id, sup_id, ser_sup_id, from, to, old_from, old_to, new_from, new_to, client_name, ser_name, ser_name_ar,is_current_user));
                                    solutionsArr.add(new SearchBookingDataSTR.Solution(ser_id,ser_name,ser_name_ar,emp_id,emp_name,sup_id,ser_sup_id,from,to,bdb_ser_home_price,bdb_ser_hall_price,bdb_hotel_price,bdb_ser_salon_price,bdb_ser_home,bdb_ser_salon,bdb_ser_hall,bdb_hotel));
                                }


                                searchBookingDataSTRS.add(new SearchBookingDataSTR(salon_id,salon_name,total_price,client_name,client_name,is_current_user,client_id,solutionsArr));
                                stringArrayListMap.put(salons.get(l),searchBookingDataSTRS);

                            }
                            GroupReservationFragment.serchGroupBookingData.add(new SerchGroupBookingData(GroupReservationFragment.solutionsCounts));

                        }
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


//                               GroupReservationResultFragment.listAdapter=new CustomExpandableListAdapter(BeautyMainPage.context,APICall.salons,APICall.searchBookingDataSTRS);
//                                TabOneBag.listAdapter=new CustomExpandableListBagAdapter(BeautyMainPage.context,APICall.salons,APICall.stringArrayListMap);
//                                GroupReservationResultFragment.listAdapter.notifyDataSetChanged();
                                TabOneBag.listView.setAdapter(TabOneBag.listAdapter);
                                TabOneBag.listAdapter.notifyDataSetChanged();
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


    public  static  void  searchGroupBookingOther( final Context context){
        salons.clear();
        stringArrayListMap.clear();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
                GroupReservationOtherResultFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();

        String filter=GroupFilterBookingforOther();
        String ff=" {\"Filter\":\t[\n" +
                "    \t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
                "    \t{\"num\":35,\"value1\":39.2147311,\"value2\":0},{\"num\":1,\"value1\":0,\"value2\":1000},{\"num\":8,\"value1\":1,\"value2\":0}\t],\n" +
                "    \"date\":\"2019-7-1\",\t\t\"clients\":[\t{\"client_name\":\"basma\",\"client_phone\":\"0500112233\",\"is_current_user\":1,\"services\":[\n" +
                "    {\"ser_id\":2,\"ser_time\":60}\n" +
                "    ,{\"ser_id\":1,\"ser_time\":60}\n" +
                "    ]},\t{\"client_name\":\"c1\",\"client_phone\":\"11221\",\"is_current_user\":0,\"services\":[\n" +
                "    {\"ser_id\":2,\"ser_time\":60}\n" +
                "    ]}]}";

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
                        GroupReservationOtherResultFragment.pullToRefresh.setRefreshing(false);
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
                        GroupReservationOtherResultFragment.pullToRefresh.setRefreshing(false);
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
                            JSONObject completeSolutions = completeSolutions1.getJSONObject(l);

                            //------------------ not used until now-------------
                            String total_price=completeSolutions.getString("total_price");

                            salons.add(completeSolutions.getJSONArray("solution").getJSONObject(0).getString("salon_name"));
//                            try {
//                                Log.e("SalonAdd",completeSolutions.getJSONObject(0).getString("salon_name")+"");
//                                Log.e("completeSolutions1",completeSolutions1.length()+"");
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }

                            JSONArray sol=completeSolutions.getJSONArray("solution");

                            for (int i = 0; i < sol.length(); i++) {
                                JSONObject data = sol.getJSONObject(i);
                                String salon_id = data.getString("salon_id");
                                String salonName = data.getString("salon_name");
                                JSONObject client_response = data.getJSONObject("client_response");
                                String is_current_user = client_response.getString("is_current_user");
                                //-----------phone number=----------
                                String salon_name = client_response.getString("client_phone");
                                String client_id = client_response.getString("client_id");
                                String client_name = client_response.getString("client_name");
                                JSONArray solutions = client_response.getJSONArray("solutions");
//                                ArrayList<SerchGroupBookingData.Solutions> solutionsArrayList = new ArrayList<>();

                                ArrayList<SearchBookingDataSTR.Solution> solutionsArr=new ArrayList<>();
                                for (int k = 0; k < solutions.length(); k++) {
                                    JSONObject data1 = solutions.getJSONObject(k);
                                    Log.e("data1",data1+"");
                                    String ser_id = data1.getString("ser_id");
                                    String ser_name = data1.getString("ser_name");
                                    String ser_name_ar = data1.getString("ser_name_ar");
                                    String emp_id = data1.getString("emp_id");
                                    String emp_name = data1.getString("emp_name");
                                    String sup_id = data1.getString("sup_id");
                                    String ser_sup_id = data1.getString("ser_sup_id");
                                    String from = data1.getString("from");
                                    String to = data1.getString("to");
//                                    String old_from = data1.getString("old_from");
//                                    String old_to = data1.getString("old_to");
//                                    String new_from = data1.getString("new_from");
//                                    String new_to = data1.getString("new_to");
                                    String client_name1 = data1.getString("client_name");
                                    String bdb_ser_home_price = data1.getString("bdb_ser_home_price");
                                    String bdb_ser_hall_price = data1.getString("bdb_ser_hall_price");
                                    String bdb_hotel_price = data1.getString("bdb_hotel_price");
                                    String bdb_ser_salon_price = data1.getString("bdb_ser_salon_price");
                                    String bdb_ser_home = data1.getString("bdb_ser_home");
                                    String bdb_ser_salon = data1.getString("bdb_ser_salon");
                                    String bdb_ser_hall = data1.getString("bdb_ser_salon");
                                    String bdb_hotel = data1.getString("bdb_hotel");


//                                    solutionsArrayList.add(new SerchGroupBookingData.Solutions(ser_id, emp_id, sup_id, ser_sup_id, from, to, old_from, old_to, new_from, new_to, client_name, ser_name, ser_name_ar,is_current_user));
                                    solutionsArr.add(new SearchBookingDataSTR.Solution(ser_id,ser_name,ser_name_ar,emp_id,emp_name,sup_id,ser_sup_id,from,to,bdb_ser_home_price,bdb_ser_hall_price,bdb_hotel_price,bdb_ser_salon_price,bdb_ser_home,bdb_ser_salon,bdb_ser_hall,bdb_hotel));
                                }

//                                client_responseArrayList.add(new SerchGroupBookingData.ClientResponse(client_name, solutionsArrayList));
//
//                            }

//                                GroupReservationFragment.solutionsCounts.add(new SerchGroupBookingData.SolutionsCount(salon_id, salon_name, new SerchGroupBookingData.ClientResponse(client_name, solutionsArrayList)));

                                searchBookingDataSTRS.add(new SearchBookingDataSTR(salon_id,salon_name,total_price,client_name,client_name,is_current_user,client_id,solutionsArr));
                                stringArrayListMap.put(salons.get(l),searchBookingDataSTRS);

                            }
                            GroupReservationOthersFragment.serchGroupBookingData.add(new SerchGroupBookingData(GroupReservationOthersFragment.solutionsCounts));

                        }
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

//                               GroupReservationResultFragment.listAdapter=new CustomExpandableListAdapter(BeautyMainPage.context,APICall.salons,APICall.searchBookingDataSTRS);
                                GroupReservationOtherResultFragment.listAdapter=new CustomExpandableListAdapter(BeautyMainPage.context,APICall.salons,APICall.stringArrayListMap);
//                                GroupReservationResultFragment.listAdapter.notifyDataSetChanged();
                                GroupReservationOtherResultFragment.listView.setAdapter(GroupReservationOtherResultFragment.listAdapter);
                                GroupReservationOtherResultFragment.listAdapter.notifyDataSetChanged();
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

    //---- get supplier name for any service------------
    public  static  void  getSupName(final TextView emp_name , final String bdb_sup_id, final Context context){

        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_sup_id",bdb_sup_id);
            postdata.put("attr","bdb_owner_name");
        }catch (JSONException je){
            je.printStackTrace();
        }

//        String filter=GroupFilterBooking();

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

    //    get date just date
    static String getFilterAddGroupItem(String bdb_is_group_booking,String date){
        String filter="";
        filter="{\"date\":\""+date+"\",\"bdb_is_group_booking\":"+bdb_is_group_booking+",\"clients\":[";
        return filter;
    }
    static String getFilterAddGroupItemothers(String bdb_is_group_booking,String date){
        String filter="";
        filter="{\"date\":\""+date+"\",\"bdb_is_group_booking\":"+bdb_is_group_booking+",\"clients\":[";
        return filter;
    }
    static String getFilterAddGroupItemIndMulti(String bdb_is_group_booking){
        String filter="";
        filter="{\"date\":\""+PlaceServiceMultipleBookingFragment.dateFilter+"\",\"bdb_is_group_booking\":"+bdb_is_group_booking+",\"clients\":[";
        return filter;
    }
    //    get post data for add Group Item
    public static String getClientsInfo(ArrayList salons,Map<String,ArrayList<SearchBookingDataSTR>> stringArrayListHashMap,int bkPostion,String bdb_is_group_booking,String date){
       String filter= "";

        String clients="";
        try {

            for (int i = 0; i < stringArrayListHashMap.get(salons.get(bkPostion)).size(); i++) {

                if (i==0){
                String clientName = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_name();
                String clientPhone = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSalon_name();
                String clientId = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_id();
                String isCurrentUser = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getIs_current_user();
                clients = "{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";
                String serRow = "";


                for (int j = 0; j < stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().size(); j++) {
//                    {"emp_id":67,"sup_id":38,"ser_sup_id":62,"from":"13:00:00","to":"13:30:00","bdb_ser_salon":1,"bdb_ser_home":0,"bdb_ser_hotel":0,"bdb_ser_hall":0,"price":200},

                    if (j == 0) {
                        String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                        String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                        String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                        String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                        String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                        String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                        String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                        String price = "";
                        if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 1) {
                            bdb_ser_salon = "1";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                        } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 2) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "1";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                        } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 3) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "1";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                        } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 4) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "1";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                        }
                        serRow = "{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                        Log.e("clientsFilter", serRow);

                    } else {
                        String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                        String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                        String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                        String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                        String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                        String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                        String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                        String price = "";
                        if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 1) {
                            bdb_ser_salon = "1";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                        } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 2) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "1";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                        } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 3) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "1";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                        } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 4) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "1";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                        }
                        serRow = serRow + ",{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                        Log.e("clientsFilter", serRow);
                    }

                }
                serRow=serRow+"]}";
                //------------ for test----------
                clients=clients+serRow;
//                filter=filter+clients;
//                Log.e("clientsFilter","0");
                Log.e("clientsFilter",clients);


            }else {
                    String clientName = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_name();
                    String clientPhone = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSalon_name();
                    String clientId = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_id();
                    String isCurrentUser = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getIs_current_user();
                    clients = clients+",{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";
                    String serRow = "";


                    for (int j = 0; j < stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().size(); j++) {
//                    {"emp_id":67,"sup_id":38,"ser_sup_id":62,"from":"13:00:00","to":"13:30:00","bdb_ser_salon":1,"bdb_ser_home":0,"bdb_ser_hotel":0,"bdb_ser_hall":0,"price":200},

                        if (j == 0) {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            String price="";
                            if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                bdb_ser_salon = "1";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "1";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "1";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "1";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                            }
                            serRow = "{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                            Log.e("clientsFilter", serRow);

                        } else {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            String price="";
                            if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                bdb_ser_salon = "1";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "1";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "1";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "1";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                            }
                            serRow = serRow + ",{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                            Log.e("clientsFilter", serRow);
                        }

                    }

//                    Log.e("clientsFilter", i+"");

                    serRow=serRow+"]}";
                    //------------ for test----------
                    String price = "200";
                    clients=clients+serRow;
//                    filter=filter+clients;
                }



            }
            clients=clients+"]}";
            filter=filter+clients;
            Log.e("clientsFilter", filter);

        }catch (Exception e){
            e.printStackTrace();
        }
       Log.e("FilterAddGroupItm", getFilterAddGroupItem(bdb_is_group_booking,date)+filter);

       return getFilterAddGroupItem(bdb_is_group_booking,date)+filter;
    }
    public static String getClientsInfoforOthers(ArrayList salons,Map<String,ArrayList<SearchBookingDataSTR>> stringArrayListHashMap,int bkPostion,String bdb_is_group_booking,String date){
       String filter= "";

        String clients="";
        try {

            for (int i = 0; i < stringArrayListHashMap.get(salons.get(bkPostion)).size(); i++) {

                if (i==0){
                String clientName = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_name();
                String clientPhone = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSalon_name();
                String clientId = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_id();
                String isCurrentUser = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getIs_current_user();
                clients = "{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";
                String serRow = "";


                for (int j = 0; j < stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().size(); j++) {
//                    {"emp_id":67,"sup_id":38,"ser_sup_id":62,"from":"13:00:00","to":"13:30:00","bdb_ser_salon":1,"bdb_ser_home":0,"bdb_ser_hotel":0,"bdb_ser_hall":0,"price":200},

                    if (j == 0) {
                        String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                        String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                        String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                        String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                        String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                        String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                        String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                        String price = "";
                        if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 1) {
                            bdb_ser_salon = "1";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                        } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 2) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "1";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                        } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 3) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "1";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                        } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 4) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "1";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                        }
                        serRow = "{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                        Log.e("clientsFilter", serRow);

                    } else {
                        String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                        String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                        String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                        String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                        String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                        String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                        String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                        String price = "";
                        if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 1) {
                            bdb_ser_salon = "1";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                        } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 2) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "1";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                        } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 3) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "1";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                        } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 4) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "1";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                        }
                        serRow = serRow + ",{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                        Log.e("clientsFilter", serRow);
                    }

                }
                serRow=serRow+"]}";
                //------------ for test----------
                clients=clients+serRow;
//                filter=filter+clients;
//                Log.e("clientsFilter","0");
                Log.e("clientsFilter",clients);


            }else {
                    String clientName = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_name();
                    String clientPhone = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSalon_name();
                    String clientId = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_id();
                    String isCurrentUser = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getIs_current_user();
                    clients = clients+",{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";
                    String serRow = "";


                    for (int j = 0; j < stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().size(); j++) {
//                    {"emp_id":67,"sup_id":38,"ser_sup_id":62,"from":"13:00:00","to":"13:30:00","bdb_ser_salon":1,"bdb_ser_home":0,"bdb_ser_hotel":0,"bdb_ser_hall":0,"price":200},

                        if (j == 0) {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            String price="";
                            if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                bdb_ser_salon = "1";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                            } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "1";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                            } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "1";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                            } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "1";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                            }
                            serRow = "{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                            Log.e("clientsFilter", serRow);

                        } else {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            String price="";
                            if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                bdb_ser_salon = "1";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                            } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "1";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                            } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "1";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                            } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "1";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                            }
                            serRow = serRow + ",{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                            Log.e("clientsFilter", serRow);
                        }

                    }

//                    Log.e("clientsFilter", i+"");

                    serRow=serRow+"]}";
                    //------------ for test----------
                    String price = "200";
                    clients=clients+serRow;
//                    filter=filter+clients;
                }



            }
            clients=clients+"]}";
            filter=filter+clients;
            Log.e("clientsFilter", filter);

        }catch (Exception e){
            e.printStackTrace();
        }
       Log.e("FilterAddGroupItm", getFilterAddGroupItemothers(bdb_is_group_booking,date)+filter);

       return getFilterAddGroupItemothers(bdb_is_group_booking,date)+filter;
    }
    public static String getClientsInfoforIndividual(ArrayList salons,Map<String,ArrayList<SearchBookingDataSTR>> stringArrayListHashMap,int bkPostion,String bdb_is_group_booking){
       String filter= "";

        String clients="";
        try {


            for (int i = 0; i < stringArrayListHashMap.get(salons.get(bkPostion)).size(); i++) {

                if (i==0){
                String clientName = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_name();
                String clientPhone = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSalon_name();
                String clientId = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_id();
                String isCurrentUser = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getIs_current_user();
                clients = "{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";
                String serRow = "";


                for (int j = 0; j < stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().size(); j++) {
//                    {"emp_id":67,"sup_id":38,"ser_sup_id":62,"from":"13:00:00","to":"13:30:00","bdb_ser_salon":1,"bdb_ser_home":0,"bdb_ser_hotel":0,"bdb_ser_hall":0,"price":200},

                    if (j == 0) {
                        String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                        String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                        String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                        String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                        String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                        String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                        String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                        String price = "";
                        if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                            bdb_ser_salon = "1";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                        } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "1";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                        } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 3) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "1";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                        } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "1";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                        }
                        String date = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getDate();
                        serRow = "{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                        Log.e("clientsFilter", serRow);

                    } else {
                        String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                        String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                        String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                        String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                        String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                        String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                        String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                        String price = "";
                        if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                            bdb_ser_salon = "1";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                        } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "1";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                        } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 3) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "1";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                        } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "1";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                        }
                        String date = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getDate();
                        serRow = serRow +",{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                        Log.e("clientsFilter", serRow);
                    }

                }
                serRow=serRow+"]}";
                //------------ for test----------
                clients=clients+serRow;
//                filter=filter+clients;
//                Log.e("clientsFilter","0");
                Log.e("clientsFilter",clients);


            }else {
                    String clientName = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_name();
                    String clientPhone = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSalon_name();
                    String clientId = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_id();
                    String isCurrentUser = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getIs_current_user();
                    clients = clients+",{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";
                    String serRow = "";


                    for (int j = 0; j < stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().size(); j++) {
//                    {"emp_id":67,"sup_id":38,"ser_sup_id":62,"from":"13:00:00","to":"13:30:00","bdb_ser_salon":1,"bdb_ser_home":0,"bdb_ser_hotel":0,"bdb_ser_hall":0,"price":200},

                        if (j == 0) {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            String price="";
                            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                bdb_ser_salon = "1";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "1";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "1";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "1";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                            }
                            String date = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getDate();
                            serRow = "{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                            Log.e("clientsFilter", serRow);

                        } else {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            String price="";
                            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                bdb_ser_salon = "1";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "1";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "1";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "1";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                            }
                            String date = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getDate();
                            serRow = serRow +",{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                            Log.e("clientsFilter", serRow);
                        }

                    }

//                    Log.e("clientsFilter", i+"");

                    serRow=serRow+"]}";
                    //------------ for test----------
                    String price = "200";

                    clients=clients+serRow;
//                    filter=filter+clients;
                    Log.e("clientsFilterelse",clients);

                }



            }
            clients=clients+"]}";
            filter=filter+clients;
            Log.e("clientsFilter", filter);

        }catch (Exception e){
            e.printStackTrace();
        }
       Log.e("FilterAddGroupItm", getFilterAddGroupItemIndMulti(bdb_is_group_booking)+filter);

       return getFilterAddGroupItemIndMulti(bdb_is_group_booking)+filter;
    }
    public static String getClientsInfoforIndividualMultiDates(ArrayList salons,Map<String,ArrayList<SearchBookingDataSTR>> stringArrayListHashMap,int bkPostion,String bdb_is_group_booking){
       String filter= "";

        String clients="";
        try {

            for (int i = 0; i < stringArrayListHashMap.get(salons.get(bkPostion)).size(); i++) {

                if (i==0){
                String clientName = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_name();
                String clientPhone = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSalon_name();
                String clientId = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_id();
                String isCurrentUser = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getIs_current_user();
                clients = "{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";
                String serRow = "";


                for (int j = 0; j < stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().size(); j++) {
//                    {"emp_id":67,"sup_id":38,"ser_sup_id":62,"from":"13:00:00","to":"13:30:00","bdb_ser_salon":1,"bdb_ser_home":0,"bdb_ser_hotel":0,"bdb_ser_hall":0,"price":200},

                    if (j == 0) {
                        String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                        String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                        String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                        String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                        String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                        String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                        String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                        String price = "";
                        if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                            bdb_ser_salon = "1";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                        } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "1";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                        } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 3) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "1";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                        } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "1";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                        }
                        String date = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getDate();
                        serRow = "{\"emp_id\":" + emp_id + ",\"date\":\""+date+"\",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                        Log.e("clientsFilter", serRow);

                    } else {
                        String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                        String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                        String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                        String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                        String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                        String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                        String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                        String price = "";
                        if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                            bdb_ser_salon = "1";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                        } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "1";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                        } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 3) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "1";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                        } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                            bdb_ser_salon = "0";
                            bdb_ser_home = "";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "1";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                        }
                        String date = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getDate();
//                        serRow = serRow +",{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
                        serRow =serRow +",{\"emp_id\":" + emp_id + ",\"date\":\""+date+"\",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                        Log.e("clientsFilter", serRow);
                    }

                }
                serRow=serRow+"]}";
                //------------ for test----------
                clients=clients+serRow;
//                filter=filter+clients;
//                Log.e("clientsFilter","0");
                Log.e("clientsFilter",clients);


            }else {
                    String clientName = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_name();
                    String clientPhone = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSalon_name();
                    String clientId = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_id();
                    String isCurrentUser = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getIs_current_user();
                    clients = clients+",{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";
                    String serRow = "";


                    for (int j = 0; j < stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().size(); j++) {
//                    {"emp_id":67,"sup_id":38,"ser_sup_id":62,"from":"13:00:00","to":"13:30:00","bdb_ser_salon":1,"bdb_ser_home":0,"bdb_ser_hotel":0,"bdb_ser_hall":0,"price":200},

                        if (j == 0) {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            String price="";
                            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                bdb_ser_salon = "1";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "1";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "1";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "1";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                            }
                            String date = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getDate();
                            serRow = "{\"emp_id\":" + emp_id + ",\"date\":\""+date+"\",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                            Log.e("clientsFilter", serRow);

                        } else {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            String price="";
                            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                bdb_ser_salon = "1";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "1";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "1";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "1";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                            }
                            String date = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getDate();
                            serRow =serRow+ ",{\"emp_id\":" + emp_id + ",\"date\":\""+date+"\",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25}";
//                            Log.e("clientsFilter", serRow);
                        }

                    }

//                    Log.e("clientsFilter", i+"");

                    serRow=serRow+"]}";
                    //------------ for test----------
                    String price = "200";
                    clients=clients+serRow;
//                    filter=filter+clients;
                }



            }
            clients=clients+"]}";
            filter=filter+clients;
            Log.e("clientsFilter", filter);

        }catch (Exception e){
            e.printStackTrace();
        }
       Log.e("FilterAddGroupItm", getFilterAddGroupItemIndMulti(bdb_is_group_booking)+filter);

       return getFilterAddGroupItemIndMulti(bdb_is_group_booking)+filter;
    }

    public  static  void  addGroupItem( String filter,final Context context){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                ReservationFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();



        RequestBody body = RequestBody.create(MEDIA_TYPE, filter);
        Log.e("ITEMREQ",body.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/addGroupItems")
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
                        }
                });


                try {
                    final JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    final String message=j.getString("message");
                    if (success.equals("true")) {
//                        JSONObject d = j.getJSONObject("data");
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                APICall.showSweetDialogOnBookingDone(context);
                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {


                        //------- another one has booked it-------------------
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                APICall.NotAvlBookingDialog(context,"Alert!","This time is not available because another customer has booked it.");
                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                            }
                        });
                        //---------------------------------------------------------
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
    public  static  void  addGroupItemMultDates( String filter,final Context context){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                ReservationFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();



        RequestBody body = RequestBody.create(MEDIA_TYPE, filter);
        Log.e("ITEMREQ",body.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/addGroupItems3_13")
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
                        }
                });


                try {
                    final JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    final String message=j.getString("message");
                    if (success.equals("true")) {
//                        JSONObject d = j.getJSONObject("data");
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                APICall.showSweetDialogOnBookingDone(context);
                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {


                        //------- another one has booked it-------------------
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                String alert=((AppCompatActivity)context).getResources().getString(R.string.alert);
                                String message=((AppCompatActivity)context).getResources().getString(R.string.this_time_not_avl);
                                APICall.NotAvlBookingDialog(context,message,message);
                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                            }
                        });
                        //---------------------------------------------------------
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


    public static String getlatlngforOther(){
        String latlng;
        latlng="\t{\"num\":34,\"value1\":"+PlaceServiceGroupFragment.lat+",\"value2\":0},\n" +
                "\t{\"num\":35,\"value1\":"+PlaceServiceGroupFragment.lng+",\"value2\":0},";


        latlng="\t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
                "\t{\"num\":35,\"value1\":39.2147311,\"value2\":0},";
//            Log.e("LatLong",latlng);
        return latlng;
    }
    public static String getPriceforOther(){
        String price="";
        if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==1) {
            price = "{\"num\":32,\"value1\":" + PlaceServiceGroupOthersFragment.minPrice + ",\"value2\":"+ PlaceServiceGroupOthersFragment.maxPrice+"},";
            price=price+"{\"num\":9,\"value1\":1}";

        }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==2) {

            price = "{\"num\":1,\"value1\":" + PlaceServiceGroupOthersFragment.minPrice + ",\"value2\":"+ PlaceServiceGroupOthersFragment.maxPrice+"},";
            price=price+"{\"num\":8,\"value1\":1}";

        }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==3) {
            price = "{\"num\":30,\"value1\":" + PlaceServiceGroupOthersFragment.minPrice + ",\"value2\":"+ PlaceServiceGroupOthersFragment.maxPrice+"},";
            price=price+"{\"num\":10,\"value1\":1}";

        }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==4) {
            price = "{\"num\":31,\"value1\":" + PlaceServiceGroupOthersFragment.minPrice + ",\"value2\":"+ PlaceServiceGroupOthersFragment.maxPrice+"},";
            price=price+"{\"num\":11,\"value1\":1}";

        }
        return price;
    }
    public static String getPricePlaceforOther(){
        String price;
        price="{\"num\":11,\"value1\":"+PlaceServiceGroupOthersFragment.placeId+",\"value2\":0}";
//        Log.e("price",price);
        return price;
    }
    public static  String getDateforOther(){
        String date;
        date="\"date\":\""+ServiceFragment.date+"\",";
//        Log.e("Date",date);
        return date;
    }
    public static String getClientsforOther(){
        String clients = null;
        try {

            for (int i = 0; i< GroupReservationOthersFragment.clientsViewData.size(); i++) {
                if (i == 0) {
                    clients = "\t{\"client_name\":\"" + GroupReservationOthersFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+GroupReservationOthersFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+GroupReservationOthersFragment.clientsViewData.get(i).getIs_current_user()+",\"services\":[\n";
                } else {
//                    clients = clients + "\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                    clients = clients+"\t{\"client_name\":\"" + GroupReservationOthersFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+GroupReservationOthersFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+GroupReservationOthersFragment.clientsViewData.get(i).getIs_current_user()+",\"services\":[\n";

                }
                Log.e("SIZE",""+GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().size());

                for (int j = 0; j < GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                    if (j == 0) {
                        clients = clients + "{\"ser_id\":" +GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().get(j).getId() + ",\"ser_time\":60}\n";
                    } else {
                        clients = clients + ",{\"ser_id\":" + GroupReservationOthersFragment.clientsViewData.get(i).getServicesSelected().get(j).getId()+ ",\"ser_time\":60}\n";
                    }
                }
                if (GroupReservationOthersFragment.clientsViewData.size()==1){
                    clients=clients+"]}";
                }else if (i==GroupReservationOthersFragment.clientsViewData.size()-1){
                    clients=clients+"]}";

                }else {
                    clients=clients+"]},";

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        clients=clients+"]}";

//            Log.e("clients",clients);
        return clients;
    }
    public static String GroupFilterBookingforOther(){
        String filter;

        filter="{\"Filter\":\t[\n" +
                getlatlngforOther()+
                getPriceforOther()+
//                    getPricePlace()+
                "\t],\n" +
                getDateforOther() +
                "\t\t\"clients\":["+
                getClientsforOther();

        Log.e("Filter",filter);
        return filter;
    }







    public static ArrayList<GetAllCart> singleBookingList=new ArrayList<>();
//    public static ArrayList<GetAllCart> groupBookingList=new ArrayList<>();
    public static ArrayList<String> salonBooking=new ArrayList<>();
    public static ArrayList<String> salonBookingOthers=new ArrayList<>();
    public static ArrayList<String> salonBookingNormal=new ArrayList<>();
    public static Map<String ,ArrayList<GetAllCartServices>> grBookingListMap=new HashMap<>();
    public static Map<String ,ArrayList<GetAllCartServices>> grBookingListMapNormal=new HashMap<>();
    public static Map<String ,ArrayList<GetAllCartServices>> grBookingListMapOthers=new HashMap<>();

    public  static  void  getAllCart(final Context context){

        singleBookingList.clear();
        salonBooking.clear();
        salonBookingNormal.clear();
        salonBookingOthers.clear();

        grBookingListMap.clear();
        grBookingListMapNormal.clear();
        grBookingListMapOthers.clear();



        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                ReservationFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/newGetCart")
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
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        try {
                            TabSingleBag.pullToRefresh.setRefreshing(false);
                        }catch (Exception e){
                            TabOneBag.pullToRefresh.setRefreshing(false);

                        }
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
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        try {
                            TabSingleBag.pullToRefresh.setRefreshing(false);
                        }catch (Exception e){

                        }

                        try {
                            TabOneBag.pullToRefresh.setRefreshing(false);

                        }catch (Exception e){

                        }
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });


                try {
                    JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    if (success.equals("true"))
                    {
                        JSONObject data=j.getJSONObject("data");
                        JSONArray singleBookingArr=data.getJSONArray("single_booking");
                        for (int i=0;i<singleBookingArr.length();i++){
                            JSONObject sBookingObj=singleBookingArr.getJSONObject(i);
                        String    bdb_id=sBookingObj.getString("bdb_id"),
                                    bdb_ser_sup_id=sBookingObj.getString("bdb_ser_sup_id"),
                                    bdb_employee_id=sBookingObj.getString("bdb_employee_id"),
                                bdb_emp_name=sBookingObj.getString("bdb_emp_name"),
                                    bdb_price=sBookingObj.getString("bdb_price"),
                                    bdb_start_date=sBookingObj.getString("bdb_start_date"),
                                    bdb_start_time=sBookingObj.getString("bdb_start_time"),
                                    supplier_name=sBookingObj.getString("supplier_name"),
                                    bdb_service_name_ar=sBookingObj.getString("bdb_service_name_ar"),
                                    bdb_service_name_en=sBookingObj.getString("bdb_service_name_en"),
                                    bdb_ser_hotel=sBookingObj.getString("bdb_ser_hotel"),
                                    bdb_ser_hall=sBookingObj.getString("bdb_ser_hall"),
                                    bdb_ser_home=sBookingObj.getString("bdb_ser_home"),
                                    bdb_ser_salon=sBookingObj.getString("bdb_ser_salon"),
                                    bdb_is_group_booking=sBookingObj.getString("bdb_is_group_booking"),
                                    bdb_user_name=sBookingObj.getString("bdb_user_name"),
                                    bdb_user_phone=sBookingObj.getString("bdb_user_phone"),
                                    bdb_pack_booking=sBookingObj.getString("bdb_pack_booking"),
                                    bdb_main_booking=sBookingObj.getString("bdb_main_booking");


                        Log.e("bdb_pack_booking",bdb_pack_booking);


                        singleBookingList.add(new GetAllCart(bdb_id,
                                                bdb_ser_sup_id,
                                                bdb_employee_id,
                                                bdb_emp_name,
                                                bdb_price,
                                                bdb_start_date,
                                                bdb_start_time,
                                                supplier_name,
                                                bdb_service_name_ar,
                                                bdb_service_name_en,
                                                bdb_ser_hotel,
                                                bdb_ser_hall,
                                                bdb_ser_home,
                                                bdb_ser_salon,
                                                bdb_is_group_booking,
                                                bdb_user_name,
                                                bdb_user_phone,
                                                bdb_pack_booking,
                                                bdb_main_booking));

                        }


                        JSONArray groupBookingArr=data.getJSONArray("group_booking");
                        String salon="";
                        Log.e("group_bookingLength",groupBookingArr.length()+"");

                        int bookingid=0;
                        for (int i=0;i<groupBookingArr.length();i++){
                            bookingid=i;
                            JSONObject gBookingObj = groupBookingArr.getJSONObject(i);
                            ArrayList<GetAllCartServices>getAllCartServices=new ArrayList<>();
//                            salon=gBookingObj.getJSONArray(gBookingObj.names().getString(0)).getJSONObject(0).getString("supplier_name");
//                            Log.e("SALON Name: ",salon);
                            salonBooking.add("book"+bookingid+"");
                            String bdb_is_group_booking="";
                            for (int b=0;b<gBookingObj.names().length();b++){
                                ArrayList<GetAllCart> groupCartBooking=new ArrayList<>();

                                String phoneNum=gBookingObj.names().getString(b);
//                                Log.e("Phone",gBookingObj.names().getString(b));
                               JSONArray databooking= gBookingObj.getJSONArray(gBookingObj.names().getString(b));
                               Log.e("databooking",databooking+"");
                               for (int l=0;l<databooking.length();l++){
                                   JSONObject jsonObjectitem=databooking.getJSONObject(l);
                                   String bdb_id=jsonObjectitem.getString("bdb_id"),
                                           bdb_ser_sup_id=jsonObjectitem.getString("bdb_ser_sup_id"),
                                           bdb_employee_id=jsonObjectitem.getString("bdb_employee_id"),
                                           bdb_emp_name=jsonObjectitem.getString("bdb_emp_name"),
                                           bdb_price=jsonObjectitem.getString("bdb_price"),
                                           bdb_start_date=jsonObjectitem.getString("bdb_start_date"),
                                           bdb_start_time=jsonObjectitem.getString("bdb_start_time"),
                                           supplier_name=jsonObjectitem.getString("supplier_name"),
                                           bdb_service_name_ar=jsonObjectitem.getString("bdb_service_name_ar"),
                                           bdb_service_name_en=jsonObjectitem.getString("bdb_service_name_en"),
                                           bdb_ser_hotel=jsonObjectitem.getString("bdb_ser_hotel"),
                                           bdb_ser_hall=jsonObjectitem.getString("bdb_ser_hall"),
                                           bdb_ser_home=jsonObjectitem.getString("bdb_ser_home"),
                                           bdb_ser_salon=jsonObjectitem.getString("bdb_ser_salon"),
                                           bdb_user_name=jsonObjectitem.getString("bdb_user_name"),
                                           bdb_user_phone=jsonObjectitem.getString("bdb_user_phone"),
                                           bdb_pack_booking=jsonObjectitem.getString("bdb_pack_booking"),
                                           bdb_main_booking=jsonObjectitem.getString("bdb_main_booking");
                                   bdb_is_group_booking=jsonObjectitem.getString("bdb_is_group_booking");
//                                        Log.e("UserName",bdb_user_name);

                                   groupCartBooking.add(new GetAllCart(bdb_id,bdb_ser_sup_id,
                                           bdb_employee_id,
                                           bdb_emp_name,
                                           bdb_price,
                                           bdb_start_date,
                                           bdb_start_time,
                                           supplier_name,
                                           bdb_service_name_ar,
                                           bdb_service_name_en,
                                           bdb_ser_hotel,
                                           bdb_ser_hall,
                                           bdb_ser_home,
                                           bdb_ser_salon,
                                           bdb_is_group_booking,
                                           bdb_user_name,
                                           bdb_user_phone,
                                           bdb_pack_booking,
                                           bdb_main_booking));
                               }
                                getAllCartServices.add(new GetAllCartServices(phoneNum,groupCartBooking));

                            }

                            if (bdb_is_group_booking.equals("2")
                                || bdb_is_group_booking.equals("12")){
                                Log.e("GRP_TEST",bdb_is_group_booking);
                                salonBookingOthers.add(salonBooking.get(i));
                                grBookingListMapOthers.put(salonBooking.get(i),getAllCartServices);
                            }else {
                                Log.e("GRP_TEST_Normal",bdb_is_group_booking);
                                salonBookingNormal.add(salonBooking.get(i));
                                grBookingListMapNormal.put(salonBooking.get(i), getAllCartServices);
                            }

                            Log.e("SalonBookingS",salonBooking.size()+"");

                        }


//
//                        try {
//                            for (int i = 0; i < grBookingListMap.size(); i++) {
//                                if (grBookingListMap.get(salons.get(i)).get(0).getGetAllCarts().get(0).getBdb_is_group_booking().equals("2")
//                                        ||
//                                        grBookingListMap.get(salons.get(i)).get(0).getGetAllCarts().get(0).getBdb_is_group_booking().equals("12")
//                                ) {
//                                    grBookingListMapOthers.put(salons.get(i), grBookingListMap.get(salons.get(i)));
//                                } else {
//                                    grBookingListMapNormal.put(salons.get(i), grBookingListMap.get(salons.get(i)));
//                                }
//                            }
//                        }catch (Exception e){
//                            e.printStackTrace();
//                        }
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//
                                TabSingleBag.bagReservationAdapter.notifyDataSetChanged();
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
    }


    public static String getlatlngMulti(){
        String latlng;
        latlng="\t{\"num\":34,\"value1\":"+PlaceServiceGroupFragment.lat+",\"value2\":0},\n" +
                "\t{\"num\":35,\"value1\":"+PlaceServiceGroupFragment.lng+",\"value2\":0},";


        latlng="\t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
                "\t{\"num\":35,\"value1\":39.2147311,\"value2\":0},";
//            Log.e("LatLong",latlng);
        return latlng;
    }
    public static String getPriceMulti(){
        String price="";
        if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==1) {
            price = "{\"num\":32,\"value1\":" + PlaceServiceMultipleBookingFragment.minPrice + ",\"value2\":"+ PlaceServiceMultipleBookingFragment.maxPrice+"},";
            price=price+"{\"num\":9,\"value1\":1}";

        }else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==2) {

            price = "{\"num\":1,\"value1\":" + PlaceServiceMultipleBookingFragment.minPrice + ",\"value2\":"+ PlaceServiceMultipleBookingFragment.maxPrice+"},";
            price=price+"{\"num\":8,\"value1\":1}";

        }else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==3) {
            price = "{\"num\":30,\"value1\":" + PlaceServiceMultipleBookingFragment.minPrice + ",\"value2\":"+ PlaceServiceMultipleBookingFragment.maxPrice+"},";
            price=price+"{\"num\":10,\"value1\":1}";

        }else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==4) {
            price = "{\"num\":31,\"value1\":" + PlaceServiceMultipleBookingFragment.minPrice + ",\"value2\":"+ PlaceServiceMultipleBookingFragment.maxPrice+"},";
            price=price+"{\"num\":11,\"value1\":1}";

        }
        return price;
    }
    public static String getPricePlaceMulti(){
        String price;
        price="{\"num\":11,\"value1\":"+PlaceServiceGroupFragment.placeId+",\"value2\":0}";
//        Log.e("price",price);
        return price;
    }
    public static  String getDateMulti(){
        String date;
        date="\"date\":\""+ServiceFragment.date+"\",";
//        Log.e("Date",date);
        return date;
    }
    public static String getClientsMulti(){
        String clients = null;
        try {

            for (int i=0;i<MultiIndividualBookingReservationFragment.clientsViewData.size();i++) {
                if (i == 0) {
                    clients = "\t{\"client_name\":\"" + MultiIndividualBookingReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+MultiIndividualBookingReservationFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+MultiIndividualBookingReservationFragment.clientsViewData.get(i).getIs_current_user()+",\"date\": \"2019-10-31\",\"services\":[\n";
                } else {
//                    clients = clients + "\t{\"client_name\":\"" + GroupReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"services\":[\n";
                    clients = clients+"\t{\"client_name\":\"" + MultiIndividualBookingReservationFragment.clientsViewData.get(i).getClient_name().getText().toString() + "\",\"client_phone\":\""+MultiIndividualBookingReservationFragment.clientsViewData.get(i).getPhone_number().getText()+"\",\"is_current_user\":"+MultiIndividualBookingReservationFragment.clientsViewData.get(i).getIs_current_user()+",\"date\":\"2019-10-31\",\"services\":[\n";

                }
                Log.e("SIZE",""+MultiIndividualBookingReservationFragment.clientsViewData.get(i).getServicesSelected().size());

                for (int j = 0; j < MultiIndividualBookingReservationFragment.clientsViewData.get(i).getServicesSelected().size(); j++) {
//                        Log.e("SIZE",""+MultiIndividualBookingReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                    if (j == 0) {
                        clients = clients + "{\"ser_id\":" +MultiIndividualBookingReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId() + ",\"ser_time\":60}\n";
                    } else {
                        clients = clients + ",{\"ser_id\":" + MultiIndividualBookingReservationFragment.clientsViewData.get(i).getServicesSelected().get(j).getId()+ ",\"ser_time\":60}\n";
                    }
                }
                if (MultiIndividualBookingReservationFragment.clientsViewData.size()==1){
                    clients=clients+"]}";
                }else if (i==MultiIndividualBookingReservationFragment.clientsViewData.size()-1){
                    clients=clients+"]}";

                }else {
                    clients=clients+"]},";

                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        clients=clients+"]}";

//            Log.e("clients",clients);
        return clients;
    }
    public static String GroupFilterBookingMulti(){
        String filter;

        filter="{\"Filter\":\t[\n" +
                getlatlngMulti()+
                getPriceMulti()+
//                    getPricePlace()+
                "\t],\n" +
                getDateMulti() +
//                "\t\t\"clients\":["+
//                getClientsMulti();
                MultiIndividualBookingReservationFragment.clientf;


        Log.e("FilterGRP",filter);
        return filter;
    }

    public  static  void  searchGroupBookingMulti( String url,final Context context){
        salons.clear();
        stringArrayListMap.clear();

        Log.e("URL",url);
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                MultiBookingIndividualResult.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();

        String filter=GroupFilterBookingMulti();
        String ff=" {\"Filter\":\t[\n" +
                "    \t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
                "    \t{\"num\":35,\"value1\":39.2147311,\"value2\":0},{\"num\":1,\"value1\":0,\"value2\":1000},{\"num\":8,\"value1\":1,\"value2\":0}\t],\n" +
                "    \"date\":\"2019-7-1\",\t\t\"clients\":[\t{\"client_name\":\"basma\",\"client_phone\":\"0500112233\",\"is_current_user\":1,\"services\":[\n" +
                "    {\"ser_id\":2,\"ser_time\":60}\n" +
                "    ,{\"ser_id\":1,\"ser_time\":60}\n" +
                "    ]},\t{\"client_name\":\"c1\",\"client_phone\":\"11221\",\"is_current_user\":0,\"services\":[\n" +
                "    {\"ser_id\":2,\"ser_time\":60}\n" +
                "    ]}]}";

        RequestBody body = RequestBody.create(MEDIA_TYPE, filter);

        okhttp3.Request request = new okhttp3.Request.Builder()
//                .url("http://clientapp.dcoret.com/api/booking/searchGroupBooking")
                .url(url)
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
//                        MultiBookingIndividualResult.pullToRefresh.setRefreshing(false);
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
//                        MultiBookingIndividualResult.pullToRefresh.setRefreshing(false);
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
                            JSONObject completeSolutions = completeSolutions1.getJSONObject(l);

                            //------------------ not used until now-------------
                            String total_price=completeSolutions.getString("total_price");

                            salons.add(completeSolutions.getJSONArray("solution").getJSONObject(0).getString("salon_name"));
//                            try {
//                                Log.e("SalonAdd",completeSolutions.getJSONObject(0).getString("salon_name")+"");
//                                Log.e("completeSolutions1",completeSolutions1.length()+"");
//                            }catch (Exception e){
//                                e.printStackTrace();
//                            }

                            JSONArray sol=completeSolutions.getJSONArray("solution");

                            for (int i = 0; i < sol.length(); i++) {
                                JSONObject data = sol.getJSONObject(i);
                                String salon_id = data.getString("salon_id");
                                String salonName = data.getString("salon_name");
                                JSONObject client_response = data.getJSONObject("client_response");
                                String is_current_user = client_response.getString("is_current_user");
                                //-----------phone number=----------
                                String salon_name = client_response.getString("client_phone");
                                String client_id = client_response.getString("client_id");
                                String client_name = client_response.getString("client_name");
                                JSONArray solutions = client_response.getJSONArray("solutions");
//                                ArrayList<SerchGroupBookingData.Solutions> solutionsArrayList = new ArrayList<>();

                                ArrayList<SearchBookingDataSTR.Solution> solutionsArr=new ArrayList<>();
                                for (int k = 0; k < solutions.length(); k++) {
                                    JSONObject data1 = solutions.getJSONObject(k);
                                    Log.e("data1",data1+"");
                                    String ser_id = data1.getString("ser_id");
                                    String ser_name = data1.getString("ser_name");
                                    String ser_name_ar = data1.getString("ser_name_ar");
                                    String emp_id = data1.getString("emp_id");
                                    String emp_name = data1.getString("emp_name");
                                    String sup_id = data1.getString("sup_id");
                                    String ser_sup_id = data1.getString("ser_sup_id");
                                    String from = data1.getString("from");
                                    String to = data1.getString("to");
                                    String date="";
                                    try {
                                         date = data1.getString("date");
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
//                                    String old_from = data1.getString("old_from");
//                                    String old_to = data1.getString("old_to");
//                                    String new_from = data1.getString("new_from");
//                                    String new_to = data1.getString("new_to");
                                    String client_name1 = data1.getString("client_name");
                                    String bdb_ser_home_price = data1.getString("bdb_ser_home_price");
                                    String bdb_ser_hall_price = data1.getString("bdb_ser_hall_price");
                                    String bdb_hotel_price = data1.getString("bdb_hotel_price");
                                    String bdb_ser_salon_price = data1.getString("bdb_ser_salon_price");
                                    String bdb_ser_home = data1.getString("bdb_ser_home");
                                    String bdb_ser_salon = data1.getString("bdb_ser_salon");
                                    String bdb_ser_hall = data1.getString("bdb_ser_salon");
                                    String bdb_hotel = data1.getString("bdb_hotel");


//                                    solutionsArrayList.add(new SerchGroupBookingData.Solutions(ser_id, emp_id, sup_id, ser_sup_id, from, to, old_from, old_to, new_from, new_to, client_name, ser_name, ser_name_ar,is_current_user));
                                    solutionsArr.add(new SearchBookingDataSTR.Solution(ser_id,ser_name,ser_name_ar,emp_id,emp_name,sup_id,ser_sup_id,from,to,bdb_ser_home_price,bdb_ser_hall_price,bdb_hotel_price,bdb_ser_salon_price,bdb_ser_home,bdb_ser_salon,bdb_ser_hall,bdb_hotel,date));
                                }

//                                client_responseArrayList.add(new SerchGroupBookingData.ClientResponse(client_name, solutionsArrayList));
//
//                            }

//                                GroupReservationFragment.solutionsCounts.add(new SerchGroupBookingData.SolutionsCount(salon_id, salon_name, new SerchGroupBookingData.ClientResponse(client_name, solutionsArrayList)));

                                searchBookingDataSTRS.add(new SearchBookingDataSTR(salon_id,salon_name,total_price,client_name,client_name,is_current_user,client_id,solutionsArr));
                                stringArrayListMap.put(salons.get(l),searchBookingDataSTRS);

                            }
                            MultiIndividualBookingReservationFragment.serchGroupBookingData.add(new SerchGroupBookingData(MultiIndividualBookingReservationFragment.solutionsCounts));

                        }
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

//                               GroupReservationResultFragment.listAdapter=new CustomExpandableListAdapter(BeautyMainPage.context,APICall.salons,APICall.searchBookingDataSTRS);
                                MultiBookingIndividualResult.listAdapter=new CustomExpandableListAdapterForMultiInd(BeautyMainPage.context,APICall.salons,APICall.stringArrayListMap);
//                                GroupReservationResultFragment.listAdapter.notifyDataSetChanged();
                                MultiBookingIndividualResult.listView.setAdapter(MultiBookingIndividualResult.listAdapter);
                                MultiBookingIndividualResult.listAdapter.notifyDataSetChanged();
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


    public static String getClientsInfoForMulti(ArrayList salons,Map<String,ArrayList<SearchBookingDataSTR>> stringArrayListHashMap,int bkPostion){
        String filter= "";

        String clients="";
        try {
            for (int i = 0; i < stringArrayListHashMap.get(salons.get(bkPostion)).size(); i++) {

                if (i==0){
                    String clientName = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_name();
                    String clientPhone = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSalon_name();
                    String clientId = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_id();
                    String isCurrentUser = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getIs_current_user();
                    clients = "{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";
                    String serRow = "";


                    for (int j = 0; j < stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().size(); j++) {
//                    {"emp_id":67,"sup_id":38,"ser_sup_id":62,"from":"13:00:00","to":"13:30:00","bdb_ser_salon":1,"bdb_ser_home":0,"bdb_ser_hotel":0,"bdb_ser_hall":0,"price":200},

                        if (j == 0) {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                bdb_ser_salon = "1";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "1";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "1";
                                bdb_ser_hotel = "0";
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "1";
                            }
                            serRow = "{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":200}";
//                        Log.e("clientsFilter", serRow);

                        } else {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            String price = "";
                            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                bdb_ser_salon = "1";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "1";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "1";
                                bdb_ser_hotel = "0";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "1";
                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
                            }
                            serRow = serRow + ",{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+"}";
//                        Log.e("clientsFilter", serRow);
                        }

                    }
                    serRow=serRow+"]}";
                    //------------ for test----------
                    clients=clients+serRow;
//                filter=filter+clients;
//                Log.e("clientsFilter","0");
                    Log.e("clientsFilter",clients);


                }else {
                    String clientName = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_name();
                    String clientPhone = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSalon_name();
                    String clientId = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_id();
                    String isCurrentUser = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getIs_current_user();
                    clients = clients+",{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";
                    String serRow = "";


                    for (int j = 0; j < stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().size(); j++) {
//                    {"emp_id":67,"sup_id":38,"ser_sup_id":62,"from":"13:00:00","to":"13:30:00","bdb_ser_salon":1,"bdb_ser_home":0,"bdb_ser_hotel":0,"bdb_ser_hall":0,"price":200},

                        if (j == 0) {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                bdb_ser_salon = "1";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "1";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "1";
                                bdb_ser_hotel = "0";
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "1";
                            }
                            serRow = "{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":200}";
//                            Log.e("clientsFilter", serRow);

                        } else {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                bdb_ser_salon = "1";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "1";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "0";
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "0";
                                bdb_ser_hall = "1";
                                bdb_ser_hotel = "0";
                            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                bdb_ser_salon = "0";
                                bdb_ser_home = "";
                                bdb_ser_hall = "0";
                                bdb_ser_hotel = "1";
                            }
                            serRow = serRow + ",{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":200}";
//                            Log.e("clientsFilter", serRow);
                        }

                    }

//                    Log.e("clientsFilter", i+"");

                    serRow=serRow+"]}";
                    //------------ for test----------
                    String price = "200";
                    clients=clients+serRow;
//                    filter=filter+clients;
                }



            }
            clients=clients+"]}";
            filter=filter+clients;
            Log.e("clientsFilter", filter);

        }catch (Exception e){
            e.printStackTrace();
        }
//        Log.e("clientsDate", getFilterAddGroupItem()+filter);

        return getFilterAddGroupItem("","")+filter;
    }


    public static boolean checkNumber(String text,Context context){
            Boolean check;
            String prefix=text.substring(0,2);
        String t=((AppCompatActivity)context).getResources().getString(R.string.alert);

        //        String prefix=text.substring(0,1);
        if (prefix.matches("05")) {
            if (text.matches(".*[A-Z].*")||text.matches(".*[a-z].*")){
                check = false;
                String m=((AppCompatActivity)context).getResources().getString(R.string.cant_insert_char);
                APICall.showSweetDialog(context,t,m);
            }else{
                if(text.matches(".*[0-9].*") && text.length()==10){
                    check = true;
                }else {
                    String m=((AppCompatActivity)context).getResources().getString(R.string.number_great_than_10);
                    APICall.showSweetDialog(context,t,m);
                    check=false;
                }
            }
        }else {
            String s=((AppCompatActivity)context).getResources().getString(R.string.alert);
            String m=((AppCompatActivity)context).getResources().getString(R.string.prefix_05);
            APICall.showSweetDialog(context,s,m);
            check=false;
        }
        Log.e("Prefix",prefix);
        Log.e("Prefix",text.matches(".*[A-Z].*")+"");
        Log.e("Prefixnum",text.matches(".*[A-Z].*")+"");
        Log.e("Prefix",prefix);
        Log.e("CheckNumber",check+"");
            return check;
    }


//    static ArrayList<SearchGroupBooking2> searchGroupBooking2s=new ArrayList<>();
//    public static    Map<String,ArrayList<ArrayList<SearchGroupBooking2>>> stringArrayListHashMap=new HashMap<>();
//    public static ArrayList<String> solutionssalon=new ArrayList<>();
//
//
//    public  static  void  searchGroupBooking2( final Context context){
//        solutionssalon.clear();
//        stringArrayListHashMap.clear();
//
//        MediaType MEDIA_TYPE = MediaType.parse("application/json");
//        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                showDialog(context);
//                GroupReservationResultFragment.pullToRefresh.setRefreshing(true);
////                pd.show();
//            }
//        });
//
//        //        String url = "http://clientapp.dcoret.com/api/service/Service";
//        OkHttpClient client = new OkHttpClient();
//        JSONObject postdata = new JSONObject();
//
//        String filter=GroupFilterBooking();
////            String ff="{\"Filter\":\t[\n" +
////                    "    \t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
////                    "    \t{\"num\":35,\"value1\":39.2147311,\"value2\":0},{\"num\":1,\"value1\":0,\"value2\":1000},{\"num\":8,\"value1\":1,\"value2\":0}\t],\n" +
////                    "    \"date\":\"2019-7-4\",\t\t\"clients\":[\t{\"client_name\":\"null\",\"client_phone\":\"0500500500\",\"is_current_user\":1,\"services\":[\n" +
////                    "    {\"ser_id\":2,\"ser_time\":60}\n" +
////                    "    ]},\t{\"client_name\":\"c1\",\"client_phone\":\"1\",\"is_current_user\":0,\"services\":[\n" +
////                    "    {\"ser_id\":1,\"ser_time\":60}\n" +
////                    "    ,{\"ser_id\":2,\"ser_time\":60}\n" +
////                    "    ]}]}";
//
//        RequestBody body = RequestBody.create(MEDIA_TYPE, filter);
//
//        okhttp3.Request request = new okhttp3.Request.Builder()
//                .url("http://clientapp.dcoret.com/api/booking/searchGroupBooking2")
//                .post(body)
//                .addHeader("Content-Type","application/json")
//                .header("Authorization", "Bearer "+gettoken(context))
//                //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
//                .build();
////        Log.e("Req-Search",request.toString());
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                mMessage = e.getMessage().toString();
//                Log.w("failure Response", mMessage);
//                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pd.dismiss();
//                        GroupReservationResultFragment.pullToRefresh.setRefreshing(false);
//
////                        ReservationFragment.pullToRefresh.setRefreshing(false);
//                    }
//                });
//                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
//                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final Dialog dialog = new Dialog(BeautyMainPage.context);
//                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
//                            TextView confirm = dialog.findViewById(R.id.confirm);
//                            TextView message = dialog.findViewById(R.id.message);
//                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.Null);
//                            message.setText(R.string.check_internet_con);
//                            confirm.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.cancel();
//
//                                }
//                            });
//                            dialog.show();
//
//                        }
//                    });
//
//                }else {
//                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, mMessage, Toast.LENGTH_LONG).show();
//
//                        }
//                    });
//                }
//
//            }
//
//            @Override
//            public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                mMessage = response.body().string();
//                Log.e("Token", gettoken(context));
//                Log.e("TAG", mMessage);
//                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        pd.dismiss();
//                        GroupReservationResultFragment.pullToRefresh.setRefreshing(false);
//                    }
//                });
//
//
//                try {
//                    final JSONObject j=new JSONObject(mMessage);
//                    String success=j.getString("success");
//                    final String message=j.getString("message");
//                    if (success.equals("true")) {
//                        JSONObject d = j.getJSONObject("data");
//
//                        JSONArray completeSolutions1 = d.getJSONArray("CompleteSolutions");
//                        Log.e("ComSolS",completeSolutions1.length()+"");
//
//                        for (int l=0;l<completeSolutions1.length();l++) {
//                            Log.e("solutionSalon-->","Sol"+l);
//                            solutionssalon.add("sol"+l);
//
//                            JSONObject completeSolutions = completeSolutions1.getJSONObject(l);
//                            JSONArray sol=completeSolutions.getJSONArray("solution");
//                            //------------------ not used until now-------------
//                            String total_price=completeSolutions.getString("total_price");
//
////                            salons.add(completeSolutions.getJSONArray("solution").getJSONObject(0).getString("salon_name"));
////                            Log.e("->SalonName: ",completeSolutions.getJSONArray("solution").getJSONObject(0).getString("salon_name"));
//                            ArrayList<ArrayList<SolutionGroupBooking2>> solutionsArrs=new ArrayList<>();
//
//                            for (int i = 0; i < sol.length(); i++) {
//                                JSONObject data = sol.getJSONObject(i);
//                                String salon_id = data.getString("salon_id");
//                                String salonName = data.getString("salon_name");
//                                JSONObject client_response = data.getJSONObject("client_response");
//                                JSONArray solutions = client_response.getJSONArray("solutions");
//
//                                ArrayList<SolutionGroupBooking2> solutionsArr=new ArrayList<>();
////                                Log.e("SolutionsL",solutions.length()+"");
//                                for (int k = 0; k < solutions.length(); k++) {
//                                    JSONObject data1 = solutions.getJSONObject(k);
////                                    Log.e("data1",data1+"");
//                                    String ser_id = data1.getString("ser_id");
//                                    String ser_name = data1.getString("ser_name");
//                                    String ser_name_ar = data1.getString("ser_name_ar");
//                                    String emp_id = data1.getString("emp_id");
//                                    String emp_name = data1.getString("emp_name");
//                                    String sup_id = data1.getString("sup_id");
//                                    String ser_sup_id = data1.getString("ser_sup_id");
//                                    String from = data1.getString("from");
//                                    String to = data1.getString("to");
//                                    String client_name1 = data1.getString("client_name");
//                                    String is_current_user = data1.getString("is_current_user");
//                                    String rel = data1.getString("rel");
//                                    String bdb_ser_home_price = data1.getString("bdb_ser_home_price");
//                                    String bdb_ser_hall_price = data1.getString("bdb_ser_hall_price");
//                                    String bdb_hotel_price = data1.getString("bdb_hotel_price");
//                                    String bdb_ser_salon_price = data1.getString("bdb_ser_salon_price");
//                                    String bdb_ser_home = data1.getString("bdb_ser_home");
//                                    String bdb_ser_salon = data1.getString("bdb_ser_salon");
//                                    String bdb_ser_hall = data1.getString("bdb_ser_salon");
//                                    String bdb_hotel = data1.getString("bdb_hotel");
//                                    String client_phone = data1.getString("client_phone");
////                                    Log.e("-->ServiceName: "+k,ser_name);
//
////                                    solutionsArrayList.add(new SerchGroupBookingData.Solutions(ser_id, emp_id, sup_id, ser_sup_id, from, to, old_from, old_to, new_from, new_to, client_name, ser_name, ser_name_ar,is_current_user));
//                                    solutionsArr.add(new SolutionGroupBooking2(ser_id,ser_name,ser_name_ar,emp_id,emp_name,sup_id,
//                                            ser_sup_id,from,to,client_name1,is_current_user,rel,bdb_ser_home_price,bdb_ser_hall_price,bdb_hotel_price,bdb_ser_salon_price,bdb_ser_home,bdb_ser_salon,bdb_ser_hall,bdb_hotel,client_phone));
//
//                                }
//
//
//                                searchGroupBooking2s.add(new SearchGroupBooking2(salon_id,salonName,solutionsArr));
//
//
//                            }
//                            stringArrayListHashMap.put(solutionssalon.get(l),searchGroupBooking2s);
//                            GroupReservationFragment.serchGroupBookingData.add(new SerchGroupBookingData(GroupReservationFragment.solutionsCounts));
//
//                        }
//                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//
////                               GroupReservationResultFragment.listAdapter=new CustomExpandableListAdapter(BeautyMainPage.context,APICall.salons,APICall.searchBookingDataSTRS);
//                                GroupReservationResultFragment.listAdapter2=new CustomExpandableListAdapterSearchGroupBooking2(BeautyMainPage.context,APICall.solutionssalon,APICall.stringArrayListHashMap);
////                                GroupReservationResultFragment.listAdapter.notifyDataSetChanged();
//                                GroupReservationResultFragment.listView.setAdapter(GroupReservationResultFragment.listAdapter2);
//                                GroupReservationResultFragment.listAdapter2.notifyDataSetChanged();
////                                Log.e("SalonSize",salons.size()+"");
////                                Log.e("searchBookingDataSTRS",searchBookingDataSTRS.size()+"");
//
//                            }
//                        });
//                    }else {
//                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
//                            }
//                        });
//                    }
//                }catch (final JSONException je){
//                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context,je.getMessage(),Toast.LENGTH_LONG).show();
//                            Log.e("jeMessage",je.getMessage());
//                        }
//                    });
//
//                }
//                Log.e("SizeHashMap",stringArrayListHashMap.size()+"");
//                for (int i=0;i<stringArrayListHashMap.size();i++){
//                    Log.e("Sol",solutionssalon.get(i));
//                    Log.e("SizeSalons",stringArrayListHashMap.get(solutionssalon.get(i)).size()+"");
//                    for (int j=0;j<stringArrayListHashMap.get(solutionssalon.get(i)).size();j++){
//                        Log.e("-->",stringArrayListHashMap.get(solutionssalon.get(i)).get(j).getSalon_name());
//                        Log.e("Sizesolution",stringArrayListHashMap.get(solutionssalon.get(i)).get(j).getSolutionSearchGroupBooking2().size()+"");
//                        for (int k=0;k<stringArrayListHashMap.get(solutionssalon.get(i)).get(j).getSolutionSearchGroupBooking2().size();k++){
//                            Log.e("---->",stringArrayListHashMap.get(solutionssalon.get(i)).get(j).getSolutionSearchGroupBooking2().get(k).getClient_name());
//                        }
//                    }
//
//                }
//
//            }
//
//
//        });
//        //        Log.d("MessageResponse",mMessage);
//    }












    public  static  void  searchGroupBooking2( final Context context){
        salons.clear();
        stringArrayListMap.clear();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
                GroupReservationResultFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();

        String filter=GroupFilterBooking();
//            String ff="{\"Filter\":\t[\n" +
//                    "    \t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
//                    "    \t{\"num\":35,\"value1\":39.2147311,\"value2\":0},{\"num\":1,\"value1\":0,\"value2\":1000},{\"num\":8,\"value1\":1,\"value2\":0}\t],\n" +
//                    "    \"date\":\"2019-7-4\",\t\t\"clients\":[\t{\"client_name\":\"null\",\"client_phone\":\"0500500500\",\"is_current_user\":1,\"services\":[\n" +
//                    "    {\"ser_id\":2,\"ser_time\":60}\n" +
//                    "    ]},\t{\"client_name\":\"c1\",\"client_phone\":\"1\",\"is_current_user\":0,\"services\":[\n" +
//                    "    {\"ser_id\":1,\"ser_time\":60}\n" +
//                    "    ,{\"ser_id\":2,\"ser_time\":60}\n" +
//                    "    ]}]}";

        RequestBody body = RequestBody.create(MEDIA_TYPE, filter);

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/searchGroupBooking2")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
                //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();
//        Log.e("Req-Search",request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                        GroupReservationResultFragment.pullToRefresh.setRefreshing(false);

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
                        GroupReservationResultFragment.pullToRefresh.setRefreshing(false);
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
                            JSONObject completeSolutions = completeSolutions1.getJSONObject(l);

                            //------------------ not used until now-------------
                            String total_price=completeSolutions.getString("total_price");

//                            salons.add(completeSolutions.getJSONArray("solution").getJSONObject(0).getString("salon_name"));
                            salons.add("Sol"+l);

                            JSONArray sol=completeSolutions.getJSONArray("solution");

                            for (int i = 0; i < sol.length(); i++) {

                                JSONObject data = sol.getJSONObject(i);
                                String salon_id = data.getString("salon_id");
                                String salonName = data.getString("salon_name");
                                JSONObject client_response = data.getJSONObject("client_response");

                                JSONArray solutions = client_response.getJSONArray("solutions");
//                                ArrayList<SerchGroupBookingData.Solutions> solutionsArrayList = new ArrayList<>();

                                ArrayList<SearchBookingDataSTR.Solution> solutionsArr=new ArrayList<>();
                                String is_current_user = solutions.getJSONObject(0).getString("is_current_user");
                                //-----------phone number=----------
                                String salon_name = solutions.getJSONObject(0).getString("client_phone");
//                                String client_id = solutions.getJSONObject(0).getString("client_id");
                                String client_name = solutions.getJSONObject(0).getString("client_name");
                                for (int k = 0; k < solutions.length(); k++) {
                                    JSONObject data1 = solutions.getJSONObject(k);
                                    Log.e("data1",data1+"");
//                                    String is_current_user = data1.getString("is_current_user");
//                                    //-----------phone number=----------
//                                    String salon_name = data1.getString("client_phone");
//                                    String client_id = data1.getString("client_id");
//                                    String client_name = data1.getString("client_name");
                                    String ser_id = data1.getString("ser_id");
                                    String ser_name = data1.getString("ser_name");
                                    String ser_name_ar = data1.getString("ser_name_ar");
                                    String emp_id = data1.getString("emp_id");
                                    String emp_name = data1.getString("emp_name");
                                    String sup_id = data1.getString("sup_id");
                                    String ser_sup_id = data1.getString("ser_sup_id");
                                    String from = data1.getString("from");
                                    String to = data1.getString("to");
                                    String client_name1 = data1.getString("client_name");
                                    String bdb_ser_home_price = data1.getString("bdb_ser_home_price");
                                    String bdb_ser_hall_price = data1.getString("bdb_ser_hall_price");
                                    String bdb_hotel_price = data1.getString("bdb_hotel_price");
                                    String bdb_ser_salon_price = data1.getString("bdb_ser_salon_price");
                                    String bdb_ser_home = data1.getString("bdb_ser_home");
                                    String bdb_ser_salon = data1.getString("bdb_ser_salon");
                                    String bdb_ser_hall = data1.getString("bdb_ser_salon");
                                    String bdb_hotel = data1.getString("bdb_hotel");


//                                    solutionsArrayList.add(new SerchGroupBookingData.Solutions(ser_id, emp_id, sup_id, ser_sup_id, from, to, old_from, old_to, new_from, new_to, client_name, ser_name, ser_name_ar,is_current_user));
                                    solutionsArr.add(new SearchBookingDataSTR.Solution(ser_id,ser_name,ser_name_ar,emp_id,emp_name,sup_id,ser_sup_id,from,to,bdb_ser_home_price,bdb_ser_hall_price,bdb_hotel_price,bdb_ser_salon_price,bdb_ser_home,bdb_ser_salon,bdb_ser_hall,bdb_hotel));
                                }


                                searchBookingDataSTRS.add(new SearchBookingDataSTR(salon_id,salonName,total_price,client_name,client_name,is_current_user,"",solutionsArr));
                                stringArrayListMap.put(salons.get(l),searchBookingDataSTRS);

                            }
                            GroupReservationFragment.serchGroupBookingData.add(new SerchGroupBookingData(GroupReservationFragment.solutionsCounts));

                        }
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


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
//                        searchGroupBooking2(context);
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


    public  static  String  browseOneOfferv2(String bdb_pack_code, final ArrayList<OfferClientsModel> offerClientsModels, final ShowServicesAdapter offerAdapter, final Context context){


        offerClientsModels.clear();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {

//                if (oftype.equals("1") || oftype.equals("4"))
//                {
                showDialog(context);
//                pd.show();
//                    swipeRefreshLayout.setRefreshing(true);

//                }
            }
        });
        OkHttpClient client = new OkHttpClient();
        JSONObject jsonPostData=new JSONObject();
        try {
            jsonPostData.put("bdb_pack_code",bdb_pack_code);
        }catch (Exception e){
            e.printStackTrace();
        }


        Log.e("JSONPOST",jsonPostData.toString());
        final RequestBody body = RequestBody.create(MEDIA_TYPE, jsonPostData.toString());
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/service/offer/browse")
                .post(body)
                .addHeader("Content-Type","application/json")
//                .addHeader("Accept","application/json")
//                .addHeader("X-Requested-With","XMLHttpRequest")
                .header("Authorization","Bearer "+gettoken(context))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                pd.dismiss();
//                swipeRefreshLayout.setRefreshing(false);
//                TabTwo.pullToRefresh.setRefreshing(false);

                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
//                            final TextView confirm = dialog.findViewById(R.id.confirm);
//                            TextView message = dialog.findViewById(R.id.message);
//                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.ExuseMeAlert);
//                            message.setText(R.string.check_internet_con);
//                            confirm.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.cancel();
//
//                                }
//                            });
//                            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                @Override
//                                public void onCancel(DialogInterface dialog) {
////
//                                    Log.e("refreshDialog","ok");
//                                    final Dialog refreshDialog = new Dialog(ProviderMainPage.context);
//                                    refreshDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                                    refreshDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                                    refreshDialog.setContentView(R.layout.refresh_btn_dialog);
//                                    Button refresh=refreshDialog.findViewById(R.id.refresh);
//                                    refresh.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            automatedBrowseOffers(itemPerPage,pageNum,context);
//                                            refreshDialog.cancel();
//                                        }
//                                    });
//                                    refreshDialog.show();
//                                }
//                            });
//                            dialog.show();
//
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
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        if (oftype.equals("3") ||oftype.equals("4")) {
                        pd.dismiss();
//                            swipeRefreshLayout.setRefreshing(false);

//                        }
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
                        JSONObject data=jsonObject.getJSONObject("data");

                        JSONArray groups=data.getJSONArray("groups");
                        String bdb_pack_code=data.getString("bdb_pack_code");
                        ArrayList<OfferClientsModel.ServiceDetails> serviceDetails;

                        for (int i=0;i<groups.length();i++){
                            JSONArray arrayobject=groups.getJSONArray(i);
                            Log.e("Groups",arrayobject.toString());
                            serviceDetails=new ArrayList<>();
                            for (int j=0;j<arrayobject.length();j++){
                                JSONObject object=arrayobject.getJSONObject(j);
                                String bdb_ser_sup_id=object.getString("bdb_ser_sup_id");
//                           String bdb_ser_id=object.getString("bdb_ser_id");
                                String bdb_name_ar="";
                                try {
                                    bdb_name_ar=object.getString("bdb_name_ar");
                                }catch (Exception e){
                                    bdb_name_ar=object.getString("bdb_ser_name_ar");
                                }
                                String bdb_ser_name_en=object.getString("bdb_ser_name_en");
                                String bdb_time=object.getString("bdb_time");
                                String is_bride=object.getString("is_bride");
//                           String bdb_ext_pack_code=object.getString("bdb_ext_pack_code");
                                serviceDetails.add(new OfferClientsModel.ServiceDetails(bdb_ser_sup_id,"",bdb_name_ar,bdb_ser_name_en,bdb_time,is_bride));

                                Log.e("SerDetailSize",j+"");
                            }

                            Log.e("OfferDetailSize",i+"");
                            offerClientsModels.add(new OfferClientsModel(bdb_pack_code,serviceDetails));
                        }

                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                offerAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }catch (JSONException je){
                    je.printStackTrace();
//                        there is no suppliered services with your search filters
//                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            TabTwo.arrayList.clear();
////                            TabTwo.refreshRV();
//                        }
//                    });

                    je.printStackTrace();
                }
            }
        });

//        Log.d("MessageResponse",mMessage);
        return mMessage;
    }

    public  static  String  browseOneOffer(String bdb_pack_code, final Context context){


        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {

//                if (oftype.equals("1") || oftype.equals("4"))
//                {
                showDialog(context);
//                pd.show();
//                    swipeRefreshLayout.setRefreshing(true);

//                }
            }
        });
        OkHttpClient client = new OkHttpClient();
        JSONObject jsonPostData=new JSONObject();
        try {
            jsonPostData.put("bdb_pack_code",bdb_pack_code);
        }catch (Exception e){
            e.printStackTrace();
        }


        Log.e("JSONPOST",jsonPostData.toString());
        final RequestBody body = RequestBody.create(MEDIA_TYPE, jsonPostData.toString());
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/service/offer/browse")
                .post(body)
                .addHeader("Content-Type","application/json")
//                .addHeader("Accept","application/json")
//                .addHeader("X-Requested-With","XMLHttpRequest")
                .header("Authorization","Bearer "+gettoken(context))
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                pd.dismiss();
//                swipeRefreshLayout.setRefreshing(false);
//                TabTwo.pullToRefresh.setRefreshing(false);

                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
//                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
//                            final TextView confirm = dialog.findViewById(R.id.confirm);
//                            TextView message = dialog.findViewById(R.id.message);
//                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.ExuseMeAlert);
//                            message.setText(R.string.check_internet_con);
//                            confirm.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.cancel();
//
//                                }
//                            });
//                            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                @Override
//                                public void onCancel(DialogInterface dialog) {
////
//                                    Log.e("refreshDialog","ok");
//                                    final Dialog refreshDialog = new Dialog(ProviderMainPage.context);
//                                    refreshDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                                    refreshDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                                    refreshDialog.setContentView(R.layout.refresh_btn_dialog);
//                                    Button refresh=refreshDialog.findViewById(R.id.refresh);
//                                    refresh.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            automatedBrowseOffers(itemPerPage,pageNum,context);
//                                            refreshDialog.cancel();
//                                        }
//                                    });
//                                    refreshDialog.show();
//                                }
//                            });
//                            dialog.show();
//
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
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        if (oftype.equals("3") ||oftype.equals("4")) {
                        pd.dismiss();
//                            swipeRefreshLayout.setRefreshing(false);

//                        }
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
                        JSONObject data=jsonObject.getJSONObject("data");

                        JSONArray groups=data.getJSONArray("groups");
                        String bdb_pack_code=data.getString("bdb_pack_code");
                        ArrayList<OfferClientsModel.ServiceDetails> serviceDetails;

                        for (int i=0;i<groups.length();i++){
                            JSONArray arrayobject=groups.getJSONArray(i);
                            Log.e("Groups",arrayobject.toString());
                            serviceDetails=new ArrayList<>();
                            for (int j=0;j<arrayobject.length();j++){
                                JSONObject object=arrayobject.getJSONObject(j);
                                String bdb_ser_sup_id=object.getString("bdb_ser_sup_id");
//                           String bdb_ser_id=object.getString("bdb_ser_id");
                                String bdb_name_ar="";
                                try {
                                    bdb_name_ar=object.getString("bdb_name_ar");
                                }catch (Exception e){
                                    bdb_name_ar=object.getString("bdb_ser_name_ar");
                                }
                                String bdb_ser_name_en=object.getString("bdb_ser_name_en");
                                String bdb_time=object.getString("bdb_time");
                                String is_bride=object.getString("is_bride");
                                String bdb_ext_pack_code=object.getString("bdb_ext_pack_code");
                                serviceDetails.add(new OfferClientsModel.ServiceDetails(bdb_ser_sup_id,"",bdb_name_ar,bdb_ser_name_en,is_bride));

                            }


                            SingleDateMultiClientOfferBooking.offerClientsModels.add(new OfferClientsModel(bdb_pack_code,serviceDetails));
                        }

                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                SingleDateMultiClientOfferBooking.offerAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }catch (JSONException je){
                    je.printStackTrace();
//                        there is no suppliered services with your search filters
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            TabTwo.arrayList.clear();
//                            TabTwo.refreshRV();
                        }
                    });

                    je.printStackTrace();
                }
            }
        });

//        Log.d("MessageResponse",mMessage);
        return mMessage;
    }

    public  static  void  searchGroupOfferBooking( final Context context,String filter){
        salons.clear();
        stringArrayListMap.clear();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                GroupReservationResultFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
//        JSONObject postdata = new JSONObject();

//        String filter=GroupFilterBooking();
//            String ff="{\"Filter\":\t[\n" +
//                    "    \t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
//                    "    \t{\"num\":35,\"value1\":39.2147311,\"value2\":0},{\"num\":1,\"value1\":0,\"value2\":1000},{\"num\":8,\"value1\":1,\"value2\":0}\t],\n" +
//                    "    \"date\":\"2019-7-4\",\t\t\"clients\":[\t{\"client_name\":\"null\",\"client_phone\":\"0500500500\",\"is_current_user\":1,\"services\":[\n" +
//                    "    {\"ser_id\":2,\"ser_time\":60}\n" +
//                    "    ]},\t{\"client_name\":\"c1\",\"client_phone\":\"1\",\"is_current_user\":0,\"services\":[\n" +
//                    "    {\"ser_id\":1,\"ser_time\":60}\n" +
//                    "    ,{\"ser_id\":2,\"ser_time\":60}\n" +
//                    "    ]}]}";

//        String filter;
//        if (ProviderMainPage.FRAGMENT_NAME.equals("GroupBookingActivity")){
//             filter=GroupBookingActivity.postdata;
//        }else {
//             filter=SingleMultiActivity.postdata;
//        }
//        String filtertmp="{\"booking_place\":\"bdb_ser_salon_price\",\n" +
//                "    \"date\":\"2019-9-8\",\"clients\":[{\"client_name\":\"c1\",\"client_phone\":\"0555\",\n" +
//                "    \t\t\t\"is_current_user\":0,\"services\":[\n" +
//                "    \t\t\t\t{\"ser_id\":9,\"ser_time\":90}]\n" +
//                "    \t\t\t\t\t},{\"client_name\":\"c2\",\"client_phone\":\"0600\",\n" +
//                "    \t\t\t\"is_current_user\":0,\"services\":[\n" +
//                "    \t\t\t\t{\"ser_id\":3,\"ser_time\":100}]\n" +
//                "    \t\t\t\t\t}]}";

        RequestBody body = RequestBody.create(MEDIA_TYPE, filter);

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/service/offer/searchOfferBooking")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
                //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();
//        Log.e("Req-Search",request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
//                        GroupReservationResultFragment.pullToRefresh.setRefreshing(false);

//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.Null);
//                            message.setText(R.string.check_internet_con);
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
//                        GroupReservationResultFragment.pullToRefresh.setRefreshing(false);
                    }
                });


                try {
                    final JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    final String message=j.getString("message");
                    if (success.equals("true")) {
                        JSONObject d = j.getJSONObject("data");
                        String total_price=d.getString("total_price");


                        JSONArray completeSolutions1 = d.getJSONArray("CompleteSolutions");
                        for (int l=0;l<completeSolutions1.length();l++) {

                            ArrayList<SearchBookingDataSTR> searchBookingDataSTRS=new ArrayList<>();
                            JSONObject completeSolutions = completeSolutions1.getJSONObject(l);

                            //------------------ not used until now-------------
//                            String total_price=completeSolutions.getString("total_price");
                            String date=completeSolutions.getString("date");


                            salons.add(completeSolutions.getJSONArray("solution").getJSONObject(0).getString("salon_name"));

                            JSONArray sol=completeSolutions.getJSONArray("solution");

                            for (int i = 0; i < sol.length(); i++) {

                                JSONObject data = sol.getJSONObject(i);
                                String salon_id = data.getString("salon_id");
                                String salonName = data.getString("salon_name");
                                JSONObject client_response = data.getJSONObject("client_response");
                                String is_current_user = client_response.getString("is_current_user");
                                //-----------phone number=----------
                                String salon_name = client_response.getString("client_phone");
                                String client_id = client_response.getString("client_id");
                                String client_name = client_response.getString("client_name");
                                JSONArray solutions = client_response.getJSONArray("solutions");
//                                ArrayList<SerchGroupBookingData.Solutions> solutionsArrayList = new ArrayList<>();

                                ArrayList<SearchBookingDataSTR.Solution> solutionsArr=new ArrayList<>();
                                for (int k = 0; k < solutions.length(); k++) {
                                    JSONObject data1 = solutions.getJSONObject(k);
                                    Log.e("data1",data1+"");
                                    String ser_id = data1.getString("ser_id");
                                    String ser_name = data1.getString("ser_name");
                                    String ser_name_ar = data1.getString("ser_name_ar");
                                    String emp_id = data1.getString("emp_id");
                                    String emp_name = data1.getString("emp_name");
                                    String sup_id = data1.getString("sup_id");
                                    String ser_sup_id = data1.getString("ser_sup_id");
                                    String from = data1.getString("from");
                                    String to = data1.getString("to");
                                    String client_name1 = data1.getString("client_name");
                                    String bdb_ser_home_price = data1.getString("bdb_ser_home_price");
                                    String bdb_ser_hall_price = data1.getString("bdb_ser_hall_price");
                                    String bdb_hotel_price = data1.getString("bdb_hotel_price");
                                    String bdb_ser_salon_price = data1.getString("bdb_ser_salon_price");
                                    String bdb_ser_home = data1.getString("bdb_ser_home");
                                    String bdb_ser_salon = data1.getString("bdb_ser_salon");
                                    String bdb_ser_hall = data1.getString("bdb_ser_salon");
                                    String bdb_hotel = data1.getString("bdb_hotel");


//                                    solutionsArrayList.add(new SerchGroupBookingData.Solutions(ser_id, emp_id, sup_id, ser_sup_id, from, to, old_from, old_to, new_from, new_to, client_name, ser_name, ser_name_ar,is_current_user));
                                    solutionsArr.add(new SearchBookingDataSTR.Solution(ser_id,ser_name,ser_name_ar,emp_id,emp_name,sup_id,ser_sup_id,from,to,bdb_ser_home_price,bdb_ser_hall_price,bdb_hotel_price,bdb_ser_salon_price,bdb_ser_home,bdb_ser_salon,bdb_ser_hall,bdb_hotel,date));
                                }


                                searchBookingDataSTRS.add(new SearchBookingDataSTR(salon_id,salon_name,total_price,client_name,client_name,is_current_user,client_id,solutionsArr));
                                stringArrayListMap.put(salons.get(l),searchBookingDataSTRS);

                            }
//                            GroupReservationFragment.serchGroupBookingData.add(new SerchGroupBookingData(GroupReservationFragment.solutionsCounts));

                        }
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


//                               GroupReservationResultFragment.listAdapter=new CustomExpandableListAdapter(BeautyMainPage.context,APICall.salons,APICall.searchBookingDataSTRS);
                                OfferBookingResult.listAdapter=new CustomExpandableListAdapter(context,APICall.salons,APICall.stringArrayListMap);
//                                GroupReservationResultFragment.listAdapter.notifyDataSetChanged();
                                OfferBookingResult.listView.setAdapter(OfferBookingResult.listAdapter);
                                OfferBookingResult.listAdapter.notifyDataSetChanged();
                                Log.e("SalonSize",salons.size()+"");
//                                Log.e("searchBookingDataSTRS",searchBookingDataSTRS.size()+"");

                            }
                        });
                    }else {

//                        searchGroupBooking2(context);
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
    public  static  void  searchGroupOfferBooking25( final Context context,String filter){
        salons.clear();
        stringArrayListMap.clear();

        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                GroupReservationResultFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
//        JSONObject postdata = new JSONObject();

//        String filter=GroupFilterBooking();
//            String ff="{\"Filter\":\t[\n" +
//                    "    \t{\"num\":34,\"value1\":21.529023,\"value2\":0},\n" +
//                    "    \t{\"num\":35,\"value1\":39.2147311,\"value2\":0},{\"num\":1,\"value1\":0,\"value2\":1000},{\"num\":8,\"value1\":1,\"value2\":0}\t],\n" +
//                    "    \"date\":\"2019-7-4\",\t\t\"clients\":[\t{\"client_name\":\"null\",\"client_phone\":\"0500500500\",\"is_current_user\":1,\"services\":[\n" +
//                    "    {\"ser_id\":2,\"ser_time\":60}\n" +
//                    "    ]},\t{\"client_name\":\"c1\",\"client_phone\":\"1\",\"is_current_user\":0,\"services\":[\n" +
//                    "    {\"ser_id\":1,\"ser_time\":60}\n" +
//                    "    ,{\"ser_id\":2,\"ser_time\":60}\n" +
//                    "    ]}]}";

//        String filter;
//        if (ProviderMainPage.FRAGMENT_NAME.equals("GroupBookingActivity")){
//             filter=GroupBookingActivity.postdata;
//        }else {
//             filter=SingleMultiActivity.postdata;
//        }
//        String filtertmp="{\"booking_place\":\"bdb_ser_salon_price\",\n" +
//                "    \"date\":\"2019-9-8\",\"clients\":[{\"client_name\":\"c1\",\"client_phone\":\"0555\",\n" +
//                "    \t\t\t\"is_current_user\":0,\"services\":[\n" +
//                "    \t\t\t\t{\"ser_id\":9,\"ser_time\":90}]\n" +
//                "    \t\t\t\t\t},{\"client_name\":\"c2\",\"client_phone\":\"0600\",\n" +
//                "    \t\t\t\"is_current_user\":0,\"services\":[\n" +
//                "    \t\t\t\t{\"ser_id\":3,\"ser_time\":100}]\n" +
//                "    \t\t\t\t\t}]}";

        RequestBody body = RequestBody.create(MEDIA_TYPE, filter);

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/service/offer/searchOfferBooking")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
                //                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6Ijg5MzY2Yjk1NTM3NTg4ZjRhYTdlZTVmOTdlODY0MGQzOGQ4NWI4NTI0M2Y5MjQ2ZWYzNGM3MmI1OTgxZmIzNmU4ZGI3NWY4OTNlOTQxNzVjIn0.eyJhdWQiOiI5IiwianRpIjoiODkzNjZiOTU1Mzc1ODhmNGFhN2VlNWY5N2U4NjQwZDM4ZDg1Yjg1MjQzZjkyNDZlZjM0YzcyYjU5ODFmYjM2ZThkYjc1Zjg5M2U5NDE3NWMiLCJpYXQiOjE1NjMzNTU2MTMsIm5iZiI6MTU2MzM1NTYxMywiZXhwIjoxNTk0OTc4MDEzLCJzdWIiOiIyNDEiLCJzY29wZXMiOltdfQ.KXJ_ee6Oy4-sSEDYF9TQqfBOwj6kWVjxoxXY6ygXMKmx3mc9kPz3grwy87PEsltszjKJeTW4Mn72mthRU4VSezsO8t7z2OKLt_SOWrgaptvvGS6S3eFj9BzOY1F6RYlfLmnCKUBEMem7joAYSNTBdy6KHDVZ3leOLAtkvyCquFQsoSL1IT1x_7m3WTedYivBPHcF99XU_dmNxDvdrWc6-0Ci28MTO2LaCVf3UEV4SA7tIkzrCBBEI35Wvpev9uKha46rRYg_MtFN8RYoMnwF-pbj92wmy-DvMrljCuStJ_K45v8N7Q_in9MwnQK0bAz5i8yDGdLqmsPF92hbaMRHE1nbS0WofUCtlu5_8BCXpIVIPJXGaQReeZA7IuQLF7X0hJf12oM_MRp6PeuDQRvB1iw1Gh9H5ZcCeX2WV8MQ8LxEF1RA_TBdGa1SPOqTINzbLllMFt69ni2v5SMatRijjnLd-Du_9CTnaHz9e2QEL7Pzf64wogQz2LzcQ0UkI2sCOcOHaZ4vpAwhPXgjZBux9fLNkO18Yksk3sppD-4FTwn6TQRKaOfD7fQRaSjky9m3hLBr2YV3Vg6rvlpun3nYFdG130mwhb3lBBzFLsmTdX-evobpUPFLP8h-Y7fNk7P8NMqxIpNRJQWTJbxNsVE4TWf_IOSppYEh_llNzPJ1d_k")
                .build();
//        Log.e("Req-Search",request.toString());

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
//                        GroupReservationResultFragment.pullToRefresh.setRefreshing(false);

//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
                    ((AppCompatActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.Null);
//                            message.setText(R.string.check_internet_con);
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
//                        GroupReservationResultFragment.pullToRefresh.setRefreshing(false);
                    }
                });


                try {
                    final JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    final String message=j.getString("message");
                    if (success.equals("true")) {
                        JSONObject d = j.getJSONObject("data");
                        String total_price=d.getString("total_price");
//                        String date=d.getString("date");
                        String salon_tmp="";


                        JSONArray completeSolutions1 = d.getJSONArray("CompleteSolutions");
                        for (int l=0;l<completeSolutions1.length();l++) {



                            ArrayList<SearchBookingDataSTR> searchBookingDataSTRS=new ArrayList<>();
                            JSONObject completeSolutions = completeSolutions1.getJSONObject(l);
                            String date=completeSolutions.getString("date");
                            Log.e("Date",date);

                            //------------------ not used until now-------------
//                            String total_price=completeSolutions.getString("total_price");

                            if (l!=0 && salons.get(l-1).equals(completeSolutions.getJSONArray("solution").getJSONObject(0).getString("salon_name"))) {
                                Log.e("salonName",salons.get(l-1));
                            }else {
                                salons.add(completeSolutions.getJSONArray("solution").getJSONObject(0).getString("salon_name"));
                                try {
                                    Log.e("salonNameEls",salons.get(l));
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }




                            JSONArray sol=completeSolutions.getJSONArray("solution");

                            for (int i = 0; i < sol.length(); i++) {

                                JSONObject data = sol.getJSONObject(i);
                                String salon_id = data.getString("salon_id");
                                String salonName = data.getString("salon_name");
                                JSONObject client_response = data.getJSONObject("client_response");
                                String is_current_user = client_response.getString("is_current_user");
                                //-----------phone number=----------
                                String salon_name = client_response.getString("client_phone");
                                String client_id = client_response.getString("client_id");
                                String client_name = client_response.getString("client_name");
                                JSONArray solutions = client_response.getJSONArray("solutions");
//                                ArrayList<SerchGroupBookingData.Solutions> solutionsArrayList = new ArrayList<>();
                                ArrayList<SearchBookingDataSTR.Solution> solutionsArr=new ArrayList<>();

                                for (int k = 0; k < solutions.length(); k++) {
                                    JSONObject data1 = solutions.getJSONObject(k);
                                    Log.e("data1",data1+"");
                                    String ser_id = data1.getString("ser_id");
                                    String ser_name = data1.getString("ser_name");
                                    String ser_name_ar = data1.getString("ser_name_ar");
                                    String emp_id = data1.getString("emp_id");
                                    String emp_name = data1.getString("emp_name");
                                    String sup_id = data1.getString("sup_id");
                                    String ser_sup_id = data1.getString("ser_sup_id");
                                    String from = data1.getString("from");
                                    String to = data1.getString("to");
                                    String client_name1 = data1.getString("client_name");
                                    String bdb_ser_home_price = data1.getString("bdb_ser_home_price");
                                    String bdb_ser_hall_price = data1.getString("bdb_ser_hall_price");
                                    String bdb_hotel_price = data1.getString("bdb_hotel_price");
                                    String bdb_ser_salon_price = data1.getString("bdb_ser_salon_price");
                                    String bdb_ser_home = data1.getString("bdb_ser_home");
                                    String bdb_ser_salon = data1.getString("bdb_ser_salon");
                                    String bdb_ser_hall = data1.getString("bdb_ser_salon");
                                    String bdb_hotel = data1.getString("bdb_hotel");
                                    Log.e("SName",ser_name);



//                                    solutionsArrayList.add(new SerchGroupBookingData.Solutions(ser_id, emp_id, sup_id, ser_sup_id, from, to, old_from, old_to, new_from, new_to, client_name, ser_name, ser_name_ar,is_current_user));
                                    solutionsArr.add(new SearchBookingDataSTR.Solution(ser_id,ser_name,ser_name_ar,emp_id,emp_name,sup_id,ser_sup_id,from,to,bdb_ser_home_price,bdb_ser_hall_price,bdb_hotel_price,bdb_ser_salon_price,bdb_ser_home,bdb_ser_salon,bdb_ser_hall,bdb_hotel,date));
                                }


                                Log.e("SOLARR",solutionsArr.size()+"");
                                Log.e("sTmp",salon_tmp+"");
                                Log.e("sname",salonName+"");
                                Log.e("iiiiiiii",i+"");
                                Log.e("llllll",l+"");


                                if (l!=0 && salons.get(l-1).equals(salonName)){
                                    Log.e(salonName,salonName);
////                                    searchBookingDataSTRS.add(new SearchBookingDataSTR(salon_id, salon_name, total_price, client_name, client_name, is_current_user, client_id, solutionsArr));

                                    for (int s=0;s<solutionsArr.size();s++)
                                        stringArrayListMap.get(salons.get(l-1)).get(0).getSolutions().add(solutionsArr.get(s));
                                }else {
                                    Log.e("i0",i+"");
                                    Log.e(salonName,salonName);

                                    searchBookingDataSTRS.add(new SearchBookingDataSTR(salon_id, salon_name, total_price, client_name, client_name, is_current_user, client_id, solutionsArr));
                                    stringArrayListMap.put(salons.get(l),searchBookingDataSTRS);

                                }
                                Log.e("searchBookin",searchBookingDataSTRS.size()+"");

                                salon_tmp=salonName;

                            }
//                            GroupReservationFragment.serchGroupBookingData.add(new SerchGroupBookingData(GroupReservationFragment.solutionsCounts));

                        }
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {


//                               GroupReservationResultFragment.listAdapter=new CustomExpandableListAdapter(BeautyMainPage.context,APICall.salons,APICall.searchBookingDataSTRS);
                                OfferBookingResult.listAdapter=new CustomExpandableListAdapter(context,salons,stringArrayListMap);
//                                GroupReservationResultFragment.listAdapter.notifyDataSetChanged();
                                OfferBookingResult.listView.setAdapter(OfferBookingResult.listAdapter);
                                OfferBookingResult.listAdapter.notifyDataSetChanged();
                                Log.e("SalonSize","salonSizw"+salons.size()+"");
                                Log.e("SalonSize","salonSizw"+stringArrayListMap.get(salons.get(0)).size()+"");
//                                Log.e("searchBookingDataSTRS",searchBookingDataSTRS.size()+"");

                            }
                        });
                    }else {

//                        searchGroupBooking2(context);
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




                for (int j=0;j<salons.size();j++){
                    for (int i=0;i<stringArrayListMap.get(salons.get(j)).size();i++){
                        Log.e("po"+j+"-"+i,stringArrayListMap.get(salons.get(j)).get(i).getSolutions().size()+"");
                    }

                }


            }


        });
        //        Log.d("MessageResponse",mMessage);
    }
    public static String getClientsInfoOffer(ArrayList salons,Map<String,ArrayList<SearchBookingDataSTR>> stringArrayListHashMap,int bkPostion){
        String filter= "";

        String clients="";
        try {
            for (int i = 0; i < stringArrayListHashMap.get(salons.get(bkPostion)).size(); i++) {

                if (i==0){
                    String clientName = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_name();
                    String clientPhone = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSalon_name();
                    String clientId = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_id();
                    String isCurrentUser = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getIs_current_user();
                    clients = "{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";
                    String serRow = "";


                    for (int j = 0; j < stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().size(); j++) {
//                    {"emp_id":67,"sup_id":38,"ser_sup_id":62,"from":"13:00:00","to":"13:30:00","bdb_ser_salon":1,"bdb_ser_home":0,"bdb_ser_hotel":0,"bdb_ser_hall":0,"price":200},

                        if (j == 0) {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();
                            String date = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getDate();

                            String price = "";
                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
//                            if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 1) {
                            bdb_ser_salon = "1";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
//                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 2) {
//                                bdb_ser_salon = "0";
//                                bdb_ser_home = "1";
//                                bdb_ser_hall = "0";
//                                bdb_ser_hotel = "0";
//                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 3) {
//                                bdb_ser_salon = "0";
//                                bdb_ser_home = "0";
//                                bdb_ser_hall = "1";
//                                bdb_ser_hotel = "0";
//                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 4) {
//                                bdb_ser_salon = "0";
//                                bdb_ser_home = "";
//                                bdb_ser_hall = "0";
//                                bdb_ser_hotel = "1";
//                            }
                            serRow = "{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25\n    ,\"date\": \""+date+"\" }";
//                        Log.e("clientsFilter", serRow);

                        } else {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();
                            String date = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getDate();

                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                            String price = "";
//                            if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 1) {
                            bdb_ser_salon = "1";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
//                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 2) {
//                                bdb_ser_salon = "0";
//                                bdb_ser_home = "1";
//                                bdb_ser_hall = "0";
//                                bdb_ser_hotel = "0";
//                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_home_price();
//                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 3) {
//                                bdb_ser_salon = "0";
//                                bdb_ser_home = "0";
//                                bdb_ser_hall = "1";
//                                bdb_ser_hotel = "0";
//                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_hall_price();
//                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 4) {
//                                bdb_ser_salon = "0";
//                                bdb_ser_home = "";
//                                bdb_ser_hall = "0";
//                                bdb_ser_hotel = "1";
//                                price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_hotel_price();
//                            }
                            serRow = serRow + ",{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25\n   ,\"date\": \""+date+"\"  }";
//                        Log.e("clientsFilter", serRow);
                        }

                    }
                    serRow=serRow+"]}";

                    //------------ for test----------
                    clients=clients+serRow;
//                filter=filter+clients;
//                Log.e("clientsFilter","0");
                    Log.e("clientsFilter",clients);


                }else {
                    String clientName = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_name();
                    String clientPhone = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSalon_name();
                    String clientId = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getClient_id();
                    String isCurrentUser = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getIs_current_user();
                    clients = clients+",{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";
                    String serRow = "";


                    for (int j = 0; j < stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().size(); j++) {
//                    {"emp_id":67,"sup_id":38,"ser_sup_id":62,"from":"13:00:00","to":"13:30:00","bdb_ser_salon":1,"bdb_ser_home":0,"bdb_ser_hotel":0,"bdb_ser_hall":0,"price":200},

                        if (j == 0) {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();
                            String date = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getDate();

                            String price = "";
                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
//                            if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 1) {
                            bdb_ser_salon = "1";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
//                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 2) {
//                                bdb_ser_salon = "0";
//                                bdb_ser_home = "1";
//                                bdb_ser_hall = "0";
//                                bdb_ser_hotel = "0";
//                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 3) {
//                                bdb_ser_salon = "0";
//                                bdb_ser_home = "0";
//                                bdb_ser_hall = "1";
//                                bdb_ser_hotel = "0";
//                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 4) {
//                                bdb_ser_salon = "0";
//                                bdb_ser_home = "";
//                                bdb_ser_hall = "0";
//                                bdb_ser_hotel = "1";
//                            }
                            serRow = "{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25\n   ,\"date\": \""+date+"\"  }";
//                            Log.e("clientsFilter", serRow);

                        } else {
                            String emp_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_id();
                            String emp_name = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getEmp_name();
                            String sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSup_id();
                            String ser_sup_id = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getSer_sup_id();
                            String from = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getFrom();
                            String to = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getTo();
                            String date = stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getDate();

                            String price = "";
                            String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
//                            if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 1) {
                            bdb_ser_salon = "1";
                            bdb_ser_home = "0";
                            bdb_ser_hall = "0";
                            bdb_ser_hotel = "0";
                            price=stringArrayListHashMap.get(salons.get(bkPostion)).get(i).getSolutions().get(j).getBdb_ser_salon_price();
//                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 2) {
//                                bdb_ser_salon = "0";
//                                bdb_ser_home = "1";
//                                bdb_ser_hall = "0";
//                                bdb_ser_hotel = "0";
//                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 3) {
//                                bdb_ser_salon = "0";
//                                bdb_ser_home = "0";
//                                bdb_ser_hall = "1";
//                                bdb_ser_hotel = "0";
//                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 4) {
//                                bdb_ser_salon = "0";
//                                bdb_ser_home = "";
//                                bdb_ser_hall = "0";
//                                bdb_ser_hotel = "1";
//                            }
                            serRow = serRow + ",{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":25\n ,\"date\": \""+date+"\"    }";
//                            Log.e("clientsFilter", serRow);
                        }

                    }

//                    Log.e("clientsFilter", i+"");

                    serRow=serRow+"]}";
                    //------------ for test----------
                    String price = "200";
                    clients=clients+serRow;
//                    filter=filter+clients;
                }



            }
            clients=clients+"]}";
            filter=filter+clients;
            Log.e("clientsFilter", filter);

        }catch (Exception e){
            e.printStackTrace();
        }
        Log.e("FilterAddGroupItm", getFilterAddGroupItemOffer()+filter);

        return getFilterAddGroupItemOffer()+filter;
    }
    static String getFilterAddGroupItemOffer(){
        String filter="";
        String offerTpe="";
        if (OfferBookingResult.offertype.equals("1")){
            offerTpe="4";
        }else if (OfferBookingResult.offertype.equals("2")){
            offerTpe="5";
        }else if (OfferBookingResult.offertype.equals("3")){
            offerTpe="6";
        }else if (OfferBookingResult.offertype.equals("4")){
            offerTpe="7";
        }else if (OfferBookingResult.offertype.equals("5")){
            offerTpe="8";
        }else if (OfferBookingResult.offertype.equals("6")){
            offerTpe="9";
        }



        filter="{\"bdb_is_group_booking\":\""+offerTpe+"\",\"clients\":[";
        return filter;
    }
    public  static  void  addtocartOffer( String filter,final Context context){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                ReservationFragment.pullToRefresh.setRefreshing(true);
//                pd.show();
            }
        });

        //        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();



        RequestBody body = RequestBody.create(MEDIA_TYPE, filter);
        Log.e("ITEMREQ",body.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/addOfferToCart")
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
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
                            TextView confirm = dialog.findViewById(R.id.confirm);
                            TextView message = dialog.findViewById(R.id.message);
                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.Null);
//                            message.setText(R.string.check_internet_con);
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
                    }
                });


                try {
                    final JSONObject j=new JSONObject(mMessage);
                    String success=j.getString("success");
                    final String message=j.getString("message");
                    if (success.equals("true")) {
//                        JSONObject d = j.getJSONObject("data");
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                API.showSweetDialogOnBookingDone(context);
                                Toast.makeText(context,R.string.group_book_done,Toast.LENGTH_LONG).show();



                                showSweetDialogOnBookingDone(context);



//                                if (ProviderMainPage.FRAGMENT_NAME.equals("GroupBookingActivity")){
//
//                                    Intent intent=new Intent(context,GroupBookingActivity.class);
//                                    ((AppCompatActivity)context).startActivity(intent);
//
//                                }else {
//
//                                    Intent intent=new Intent(context,SingleMultiActivity.class);
//                                    ((AppCompatActivity)context).startActivity(intent);
//                                }
//                                ((AppCompatActivity)context).finish();


                            }
                        });
                    }else {


                        //------- another one has booked it-------------------
                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
//                                API.NotAvlBookingDialog(context,"Alert!","This time is not available because another customer has booked it.");
                                Toast.makeText(context,message,Toast.LENGTH_LONG).show();
                            }
                        });
                        //---------------------------------------------------------
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
    public static ArrayList<EmployeeSalonClass> employeeSalonList=new ArrayList<>();
    public  static  void getEmpSalonS( final Context context){
        employeeSalonList.clear();
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showDialog(context);
//                pd.show();
            }
        });

        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
//        try {
//            postdata.put("token", token);
////            postdata.put("password", "12345");
//        } catch (JSONException e) {
////         TODO Auto-generated catch block
//            e.printStackTrace();
//        }

        RequestBody body = RequestBody.create(MEDIA_TYPE,postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://providerapp.dcoret.com/api/auth/supplier/getSalonEmployees")
                .post(body)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + gettoken(context))
//                .header("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImQzNGYzMGUwZTliZTI4NDQ1ZWFiNTg4YmM2YzVjYzNjN2MwODZlY2E5NTdhMWNiYzRmZjNhMzQ4ZGZiNjA0NWMzMmQ3ZDA2NWEyZTU4ZTUxIn0.eyJhdWQiOiI5IiwianRpIjoiZDM0ZjMwZTBlOWJlMjg0NDVlYWI1ODhiYzZjNWNjM2M3YzA4NmVjYTk1N2ExY2JjNGZmM2EzNDhkZmI2MDQ1YzMyZDdkMDY1YTJlNThlNTEiLCJpYXQiOjE1Njc0MDY5MjcsIm5iZiI6MTU2NzQwNjkyNywiZXhwIjoxNTk5MDI5MzI3LCJzdWIiOiI0OCIsInNjb3BlcyI6W119.CFoTC294Z9suaczjlMeQBU5njHyEUNScjPmI5XjaBG8hesv0oGj5irruJfE9sv1F_CnRGfU0aqQbkxc5HVnN6G76SaBFTSREef6ElqB_1XdsF5CF4d4bMkr2cAs8aC2D2ydHq25AHfCqUYlYseqnQeWzKr-cxwBCnPFx8lCszqj6WN8iviUnXaZbHYdJqw_PmPtweiQy8xP7TFUixtxwJnOguhFiPgbJaCAJREVqx6eWwOZfSAtN-B5JtCWUEUCubnW0t00EhWDNw0K-8Zz45STyDAkiWq_5B0zUvhmqUuvfgJ9P192pK4AKDxRuJxSUcyYKf1Wko9VJLFJRGO7KSsDX63jwFqGKKJfCsI8htxmRihi0yMNTvGZBDrRvGnIoHBKmj_A-JBCLB_P1X-huZvFrb0MfpQz4-r-QzK5qx1tCcSuT3iD52AG-3YyN3-XkF0PeS3h302KUJWm03nPB11M4exI-Ws2rfH5sv_xa7ujBHf9iNNHMun-VIaqzu5MRf6waaDqoRRTe0R9W_XCy6ljQWUhxKYYE9eKImWcVZy3PSp10JyBR8v7YfAqMtadw6dKKRsEIs9xQNZUfNPRzi1Nblo9PioUTmRoCEuQzKgA-zXK3xqBIrn0Kn2CtdZnr80l51cP_6EcOgCtb7utqlrkeiSDKe1iG8ftISPF424Q")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mMessage = e.getMessage();
                Log.w("failure Response", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pd.dismiss();
                    }
                });

                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final Dialog dialog = new Dialog(BeautyMainPage.context);
                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
//                            TextView confirm = dialog.findViewById(R.id.confirm);
//                            TextView message = dialog.findViewById(R.id.message);
//                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.Null);
//                            message.setText(R.string.check_internet_con);
//                            confirm.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.cancel();
//                                }
//                            });
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
                    if (success.equals("true")) {

                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                pd.dismiss();
                            }
                        });
                        JSONArray array=j.getJSONArray("data");
                        for (int i=0;i<array.length();i++){
                            JSONObject emp=array.getJSONObject(i);
                            String bdb_id=emp.getString("bdb_id");
                            String bdb_name=emp.getString("bdb_name");
                            String bdb_owner_name=emp.getString("bdb_owner_name");
                            String TotalRating=emp.getString("TotalRating");
                            employeeSalonList.add(new EmployeeSalonClass(bdb_id,bdb_name,bdb_owner_name,TotalRating));
                        }


                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    for (int i=0;i<employeeSalonList.size();i++){
                                        empNames.add(employeeSalonList.get(i).getBdb_name());
                                    }
//                                    AddServiceEmpFragment.adapter.notifyDataSetChanged();
                                }catch (Exception e){
//
//                                    AddServiceEmpBrideFragment.adapter.notifyDataSetChanged();
//
                                }
                            }
                        });


                    }
                }catch (JSONException je){
                }
                Log.e("TAG", mMessage);

                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        pd.dismiss();

//                        Toast.makeText(context,mMessage,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
//        Log.d("MessageResponse",mMessage);
    }
    public static ArrayList<ServicesClass> servicesArrayList=new ArrayList<>();
    public  static  void  browseOneBooking(String book_id,final Context context){
        servicesArrayList.clear();
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    showDialog(context);
//                    pd.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("book_id",book_id);
            Log.e("BookID",book_id);
        }catch (JSONException e){
            e.printStackTrace();
        }

        Log.e("BookID",postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/booking/browse")
//                .url("")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
//                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImIyZjc3MjA4MWE1ZjZhNzc4ZTY0NWQ4NjdmOWYxZGVkM2YxY2I5ZTNmYzllZjU5YTc2ZmM5NzE3MzIzMzg3OThhMDg4NmJiZjJjNWUyMmIxIn0.eyJhdWQiOiIxIiwianRpIjoiYjJmNzcyMDgxYTVmNmE3NzhlNjQ1ZDg2N2Y5ZjFkZWQzZjFjYjllM2ZjOWVmNTlhNzZmYzk3MTczMjMzODc5OGEwODg2YmJmMmM1ZTIyYjEiLCJpYXQiOjE1NTg5MDY3MDEsIm5iZiI6MTU1ODkwNjcwMSwiZXhwIjoxNTkwNTI5MTAxLCJzdWIiOiI0OCIsInNjb3BlcyI6W119.SzlRGvvR1MLqNG2uYU8OCFRm0nzTNXqKK_3Y9nPUqy7CAGcqWWS_kzGbrMn3DR1dck7-rManDR1OlxpErRyQ-8EDrFgVpHzfFIdGoha_Jtnjgk7SoHO24PElfbxbQzPLdqOBRWY2du5tjQuconUeWY1TsouglH6L_Uvn-DqgbDHqGkv6yqwGSwtHEzLgDI72Dd4BMMmBnliKBtLYBArDQEfmUXjNI220X1VVa0NzCgYsvVebYW80OZ-E0vq8PJD3uOEgl4huO6dOsWSDQN_h2IQR0tVN_9fxPMasaP9oWjjW5Rs-wDb2qHKZ15zC0GBYAeEqAqXyfU2qRT_yqAFLHAbzlFRAk3dQ3Hzcfaa2twEVPJvYNi7DcOkQTMU14yvcemBOcG4iDuWWrblJyD6Z3iWPkv5e8bhgkSPyDvkDEx-X2z0wCpYyQXihHXmoiXYmwHVT4Kw2_GctLxqGZNkHEAhs_uW8tDmbCh_eISsbljRjvz6Mjxn_VBmP4GiAjgE6JykTZvm--Wrv767cHe95tK8ppuL18caeBYcdG6HjEmW3uPoOBIflMcv3iaXXeH_hfDoZ-c0Jf6FrwuioLN-C-X8eU_ztC6e67rPk5vNog3kX6C-lpTpjyC5hdTJpdsNJjm4o99nsbB7GvvctB8NhpsGm1L36VGvIi6QVrbaF8nc")
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
//                        swipeRefreshLayout.setRefreshing(false);

//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
//                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final Dialog dialog = new Dialog(BeautyMainPage.context);
//                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
//                            TextView confirm = dialog.findViewById(R.id.confirm);
//                            TextView message = dialog.findViewById(R.id.message);
//                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.Null);
//                            message.setText(R.string.check_internet_con);
//                            confirm.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.cancel();
//                                }
//                            });
//                            dialog.show();
//                        }
//                    });
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
//                Log.e("Token", gettoken(context));
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                          pd.dismiss();
//                        swipeRefreshLayout.setRefreshing(false);
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                });

                try {
                    JSONObject jsonrespone = new JSONObject(mMessage);
                    String success=jsonrespone.getString("success");
                    if (success.equals("true")){
                        JSONObject data=jsonrespone.getJSONObject("data");
                        JSONArray booking=data.getJSONArray("booking");
                        String booking_type=data.getString("booking_type");
                        String salon_name=data.getString("salon_name");
                        ReservatoinDetailsActivity.salonName.setText(salon_name);
                        String booking_price="";

                        try {
                            booking_price = data.getString("booking_price");
                            Double p=Double.parseDouble(booking_price);
                            String price=df2.format(p);
                            booking_price=price;
                        }catch (Exception e){
                            e.printStackTrace();
                        }



                        try {
                            String date = data.getString("bdb_start_date");
                            String cname = data.getString("client_name");
                            ReservatoinDetailsActivity.time.setText(convertToArabic(date));
                            ReservatoinDetailsActivity.client_name.setText(cname);
                        }catch (Exception e){
                            e.printStackTrace();
                        }




                        ReservatoinDetailsActivity.price.setText(booking_price+((AppCompatActivity)context).getResources().getString(R.string.ryal));




                        if (booking_type.equals("0")) {
                            ReservatoinDetailsActivity.booktype.setText(context.getResources().getString(R.string.single_booking));
                            String cname=booking.getJSONObject(0).getString("bdb_user_name");
//                            String date=data.getString("bdb_start_date");

                            ReservatoinDetailsActivity.client_name.setText(cname);
//                            ReservatoinDetailsActivity.time.setText(date);

                        }else if (booking_type.equals("1")) {
                            String date=data.getString("bdb_start_date");
                            String cname=data.getString("client_name");
                            ReservatoinDetailsActivity.time.setText(convertToArabic(date));
                            ReservatoinDetailsActivity.client_name.setText(cname);


                            ReservatoinDetailsActivity.booktype.setText(context.getResources().getString(R.string.multi_booking));
//                            String cnametmp=booking.getJSONObject(0).getString("name");
//                            ReservatoinDetailsActivity.client_name.setText(cname);
                        }else if (booking_type.equals("2")){

                            ReservatoinDetailsActivity.booktype.setText(context.getResources().getString(R.string.multi_booking_others));

                        }else if (booking_type.equals("3")){

                            ReservatoinDetailsActivity.booktype.setText(context.getResources().getString(R.string.multi_booking));

                        }else if (booking_type.equals("4")){

                            ReservatoinDetailsActivity.booktype.setText(context.getResources().getString(R.string.offer_single_booking));

                        }else if (booking_type.equals("6")){

                            ReservatoinDetailsActivity.booktype.setText(context.getResources().getString(R.string.offer_single_multi_booking));

                        }else if (booking_type.equals("7")){

                            ReservatoinDetailsActivity.booktype.setText(context.getResources().getString(R.string.offer_multi_booking));

                        }else if (booking_type.equals("8")){

                            ReservatoinDetailsActivity.booktype.setText(context.getResources().getString(R.string.multi_booking_others));

                        }else if (booking_type.equals("9")){

                            ReservatoinDetailsActivity.booktype.setText(context.getResources().getString(R.string.multi_booking_others));

                        }else if (booking_type.equals("10")){
                            String cname=booking.getJSONObject(0).getString("bdb_user_name");
//                            String date=data.getString("bdb_start_date");

                            ReservatoinDetailsActivity.client_name.setText(cname);
                            String s=((AppCompatActivity)context).getResources().getString(R.string.Single_bride_reservation);
                            ReservatoinDetailsActivity.booktype.setText(s);

                        }else if (booking_type.equals("11")){
                            String s=((AppCompatActivity)context).getResources().getString(R.string.bride_group_offer);
                            ReservatoinDetailsActivity.booktype.setText(s);

                        }else if (booking_type.equals("12")){
                            String s=((AppCompatActivity)context).getResources().getString(R.string.bride_group_offer_other);
                            ReservatoinDetailsActivity.booktype.setText(s);

                        }else if (booking_type.equals("13")){
                            String s=((AppCompatActivity)context).getResources().getString(R.string.Multiple_booking_bride);

                            ReservatoinDetailsActivity.booktype.setText(s);

                        }



                         booking_price=data.getString("booking_price");
                        ReservatoinDetailsActivity.price.setText(convertToArabic(booking_price)+((AppCompatActivity)context).getResources().getString(R.string.ryal));
                        String booking_place=data.getString("booking_place");
                        if (booking_place.equals("0") ||booking_place.equals("10")){
//                            ReservatoinDetailsActivity.place.setText(context.getResources().getString(R.string.salontxt));
                            ReservatoinDetailsActivity.place.setText(context.getResources().getString(R.string.salon));
                        }else if (booking_place.equals("1")){
                            ReservatoinDetailsActivity.place.setText(context.getResources().getString(R.string.home));
                        }else if (booking_place.equals("2")){
                            ReservatoinDetailsActivity.place.setText(context.getResources().getString(R.string.hall));
                        }else if (booking_place.equals("3")){
                            ReservatoinDetailsActivity.place.setText(context.getResources().getString(R.string.hotel));
                        }
                        if (booking_type.equals("0") || booking_type.equals("10")){
                            Log.e("bookType","Single");


                            for (int i=0;i<booking.length();i++) {
                                JSONObject object=booking.getJSONObject(i);
                                String
                                        bdb_price = object.getString("bdb_price"),
                                        bdb_client_old = object.getString("bdb_client_old"),
                                        bdb_start_date = object.getString("bdb_start_date"),
                                        bdb_start_time = object.getString("bdb_start_time"),
                                        bdb_end_time = object.getString("bdb_end_time")
                                        , bdb_booked_at = object.getString("bdb_booked_at");
                                String bdb_emp_name = object.getString("bdb_emp_name");
                                String bdb_name = object.getString("bdb_name"),
                                        bdb_name_ar = object.getString("bdb_name_ar");
                                //-----------------------------------
                                Double p=Double.parseDouble(bdb_price);
                                String price=df2.format(p);
                                Log.e(price,price);
                                //----------------------------------
                                ReservatoinDetailsActivity.time.setText(convertToArabic(bdb_start_date));
                                String r=((AppCompatActivity)context).getResources().getString(R.string.ryal);
                                String details=booking.getJSONObject(0).getString("bdb_user_name")+" , "+((AppCompatActivity)context).getResources().getString(R.string.prc)+": "+bdb_price;

                                ReservatoinDetailsActivity.addLayout(ReservatoinDetailsActivity.myroot,details,
                                        bdb_name_ar,price+r,bdb_start_date+" | "+bdb_start_time
                                        ,bdb_end_time,bdb_booked_at,bdb_emp_name);

                            }
                        }else {
                            Log.e("bookType","Multi");
                            for (int i=0;i<booking.length();i++){
                                JSONObject object1=booking.getJSONObject(i);
                                JSONArray bookings=object1.getJSONArray("bookings");
                                String phone = object1.getString("phone");
//                                String bdb_price = object1.getString("bdb_price");

                                String  bdb_client_old = bookings.getJSONObject(0).getString("bdb_client_old");
                                String  bdb_price = bookings.getJSONObject(0).getString("bdb_price");
                                String age="";
                                if(bdb_client_old.equals("0")){
                                    age="طفلة";
                                }else {
                                    age="بالغة";
                                }


                                ReservatoinDetailsActivity. addHeaderLayout(ReservatoinDetailsActivity.myroot,object1.getString("name"),((AppCompatActivity)context).getResources().getString(R.string.prc)+":"+bdb_price);
                                Log.e("Bookings",bookings.toString());
                                for (int j=0;j<bookings.length();j++){
                                    JSONObject object=bookings.getJSONObject(j);
                                    Log.e("objectBookings",object.toString());

                                      bdb_price = object.getString("bdb_price");
                                          String  bdb_start_date = object.getString("bdb_start_date"),
                                            bdb_start_time = object.getString("bdb_start_time"),
                                            bdb_end_time = object.getString("bdb_end_time"),
                                            bdb_booked_at = object.getString("bdb_booked_at");
                                    String bdb_emp_name = object.getString("bdb_emp_name");
                                    String bdb_name = object.getString("bdb_name"),
                                            bdb_name_ar = object.getString("bdb_name_ar");

                                    ReservatoinDetailsActivity.addMainLayout(ReservatoinDetailsActivity.myroot,
                                            bdb_name_ar,bdb_price,bdb_start_date+" | "+bdb_start_time
                                            ,bdb_end_time,bdb_booked_at,bdb_emp_name);
                                }
                            }
                        }
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }



            }

        });
        //        Log.d("MessageResponse",mMessage);
    }
    public  static  void  browseOneBooking(int book_id,final Context context){
        servicesArrayList.clear();
        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    showDialog(context);
//                    pd.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_booking_id",book_id+"");
//            Log.e("BookID",book_id);
        }catch (JSONException e){
            e.printStackTrace();
        }

        Log.e("BookID",postdata.toString());

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
//                .url("http://clientapp.dcoret.com/api/booking/browse")
                .url("http://clientapp.dcoret.com/api/booking/bookingAutomatedBrowse")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
//                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImIyZjc3MjA4MWE1ZjZhNzc4ZTY0NWQ4NjdmOWYxZGVkM2YxY2I5ZTNmYzllZjU5YTc2ZmM5NzE3MzIzMzg3OThhMDg4NmJiZjJjNWUyMmIxIn0.eyJhdWQiOiIxIiwianRpIjoiYjJmNzcyMDgxYTVmNmE3NzhlNjQ1ZDg2N2Y5ZjFkZWQzZjFjYjllM2ZjOWVmNTlhNzZmYzk3MTczMjMzODc5OGEwODg2YmJmMmM1ZTIyYjEiLCJpYXQiOjE1NTg5MDY3MDEsIm5iZiI6MTU1ODkwNjcwMSwiZXhwIjoxNTkwNTI5MTAxLCJzdWIiOiI0OCIsInNjb3BlcyI6W119.SzlRGvvR1MLqNG2uYU8OCFRm0nzTNXqKK_3Y9nPUqy7CAGcqWWS_kzGbrMn3DR1dck7-rManDR1OlxpErRyQ-8EDrFgVpHzfFIdGoha_Jtnjgk7SoHO24PElfbxbQzPLdqOBRWY2du5tjQuconUeWY1TsouglH6L_Uvn-DqgbDHqGkv6yqwGSwtHEzLgDI72Dd4BMMmBnliKBtLYBArDQEfmUXjNI220X1VVa0NzCgYsvVebYW80OZ-E0vq8PJD3uOEgl4huO6dOsWSDQN_h2IQR0tVN_9fxPMasaP9oWjjW5Rs-wDb2qHKZ15zC0GBYAeEqAqXyfU2qRT_yqAFLHAbzlFRAk3dQ3Hzcfaa2twEVPJvYNi7DcOkQTMU14yvcemBOcG4iDuWWrblJyD6Z3iWPkv5e8bhgkSPyDvkDEx-X2z0wCpYyQXihHXmoiXYmwHVT4Kw2_GctLxqGZNkHEAhs_uW8tDmbCh_eISsbljRjvz6Mjxn_VBmP4GiAjgE6JykTZvm--Wrv767cHe95tK8ppuL18caeBYcdG6HjEmW3uPoOBIflMcv3iaXXeH_hfDoZ-c0Jf6FrwuioLN-C-X8eU_ztC6e67rPk5vNog3kX6C-lpTpjyC5hdTJpdsNJjm4o99nsbB7GvvctB8NhpsGm1L36VGvIi6QVrbaF8nc")
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
//                        swipeRefreshLayout.setRefreshing(false);

//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                    }
                });
                if (mMessage.equals("Unable to resolve host \"clientapp.dcoret.com\": No address associated with hostname")){
                    //                        APICall.checkInternetConnectionDialog(BeautyMainPage.context,R.string.Null,R.string.check_internet_con);
//                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final Dialog dialog = new Dialog(BeautyMainPage.context);
//                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
//                            TextView confirm = dialog.findViewById(R.id.confirm);
//                            TextView message = dialog.findViewById(R.id.message);
//                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.Null);
//                            message.setText(R.string.check_internet_con);
//                            confirm.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.cancel();
//
//                                }
//                            });
//                            dialog.show();
//
//                        }
//                    });

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
                mMessage=objectbrowseBoooking.get(ReservationsAdapter2.postionBook);
//                Log.e("Token", gettoken(context));
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            pd.dismiss();
//                            swipeRefreshLayout.setRefreshing(false);
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                });

                try {
//                    JSONObject jsonrespone = new JSONObject(mMessage);
//                    String success=jsonrespone.getString("success");
//                    if (success.equals("true")){
                        JSONObject data=new JSONObject(mMessage);
                        JSONArray booking=data.getJSONArray("booking");
                        String booking_type=data.getString("booking_type");
                        if (booking_type.equals("0")) {
                            ReservationDetailsFragment.booktype.setText(context.getResources().getString(R.string.single_booking));
                            String cname=booking.getJSONObject(0).getString("bdb_user_name");
//                            String date=data.getString("bdb_start_date");

                            ReservationDetailsFragment.client_name.setText(cname);
//                            ReservationDetailsFragment.time.setText(date);

                        }else if (booking_type.equals("1")) {
                            String date=data.getString("bdb_start_dates");
                            ReservationDetailsFragment.time.setText(convertToArabic(date));


                            ReservationDetailsFragment.booktype.setText(context.getResources().getString(R.string.multi_booking));
//                            String cname=booking.getJSONObject(0).getString("name");
//                            ReservationDetailsFragment.client_name.setText(cname);
                        }else {

                            ReservationDetailsFragment.booktype.setText(context.getResources().getString(R.string.multi_booking));

                        }



                        String booking_price=data.getString("booking_price");
                        ReservationDetailsFragment.price.setText(booking_price+((AppCompatActivity)context).getResources().getString(R.string.ryal));
                        String booking_place=data.getString("booking_place");
                        if (booking_place.equals("0")){
//                            ReservationDetailsFragment.place.setText(context.getResources().getString(R.string.salontxt));
                            ReservationDetailsFragment.place.setText("صالون");
                        }else if (booking_place.equals("1")){
                            ReservationDetailsFragment.place.setText("بيت");
                        }else if (booking_place.equals("2")){
                            ReservationDetailsFragment.place.setText("صالة");
                        }else if (booking_place.equals("3")){
                            ReservationDetailsFragment.place.setText("فندق");
                        }
                        if (booking_type.equals("0") || booking_type.equals("1")){
                            Log.e("bookType","Single");
                            String detalis=data.getString("bdb_user_name");
                            for (int i=0;i<booking.length();i++) {
                                JSONObject object=booking.getJSONObject(i);
                                String
                                        bdb_price = object.getString("bdb_price"),
                                        bdb_start_date = object.getString("bdb_start_date"),
                                        bdb_end_time = object.getString("bdb_end_time"),
                                        bdb_start_time = object.getString("bdb_start_time");
                                        String bdb_booked_at="";
                                       try {
                                            bdb_booked_at = object.getString("bdb_booked_at");
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                String bdb_emp_name = object.getString("employee name");
                                String bdb_name = object.getString("service en name"),
                                        bdb_name_ar = object.getString("service ar name");
                                String r=((AppCompatActivity)context).getResources().getString(R.string.ryal);
                                //-----------------------------------
                                Double p=Double.parseDouble(bdb_price);
                                String price=df2.format(p);
                                Log.e(price,price);
                                //------------------------------------


                                ReservationDetailsFragment.addLayout(ReservationDetailsFragment.myroot,detalis,
                                        bdb_name_ar,price+r,bdb_start_date+" | " +bdb_start_time
                                        ,bdb_end_time,bdb_booked_at,bdb_emp_name);

                            }
                        }

//                        else if (booking_type.equals("1")){
//                            Log.e("bookType","Multi");
//                            for (int i=0;i<booking.length();i++){
//                                JSONObject object1=booking.getJSONObject(i);
//                                JSONArray bookings=object1.getJSONArray("bookings");
//                                String phone = object1.getString("phone");
//                                String  bdb_client_old = bookings.getJSONObject(0).getString("bdb_client_old");
//
//
//                                ReservationDetailsFragment.addHeaderLayout(ReservationDetailsFragment.myroot,object1.getString("name"),"Age:"+bdb_client_old);
//                                Log.e("Bookings",bookings.toString());
//                                for (int j=0;j<bookings.length();j++){
//                                    JSONObject object=bookings.getJSONObject(j);
//                                    Log.e("objectBookings",object.toString());
//
//                                    String  bdb_price = object.getString("bdb_price"),
//                                            bdb_start_date = object.getString("bdb_start_date"),
//                                            bdb_start_time = object.getString("bdb_start_time"),
//                                            bdb_booked_at = object.getString("bdb_booked_at");
//                                    String bdb_emp_name = object.getString("bdb_emp_name");
//                                    String bdb_name = object.getString("bdb_name"),
//                                            bdb_name_ar = object.getString("bdb_name_ar");
//
//                                    ReservationDetailsFragment.addMainLayout(ReservationDetailsFragment.myroot,
//                                            bdb_name_ar,bdb_price,bdb_start_date+", "+bdb_start_time
//                                            ,bdb_booked_at,bdb_emp_name);
//                                }
//                            }
//                        }

                }catch (JSONException e){
                    e.printStackTrace();
                }



            }

        });
        //        Log.d("MessageResponse",mMessage);
    }

    public static ArrayList<ListServiceModel> itemArrayList=new ArrayList<>();
    public  static  void  getBasicService( final Context context){

        itemArrayList.clear();

        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    showDialog(context);
//                    pd.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
//        try {
//            postdata.put("bdb_is_bride_ser",bdb_is_bride_ser);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/service/Service")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
//                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImIyZjc3MjA4MWE1ZjZhNzc4ZTY0NWQ4NjdmOWYxZGVkM2YxY2I5ZTNmYzllZjU5YTc2ZmM5NzE3MzIzMzg3OThhMDg4NmJiZjJjNWUyMmIxIn0.eyJhdWQiOiIxIiwianRpIjoiYjJmNzcyMDgxYTVmNmE3NzhlNjQ1ZDg2N2Y5ZjFkZWQzZjFjYjllM2ZjOWVmNTlhNzZmYzk3MTczMjMzODc5OGEwODg2YmJmMmM1ZTIyYjEiLCJpYXQiOjE1NTg5MDY3MDEsIm5iZiI6MTU1ODkwNjcwMSwiZXhwIjoxNTkwNTI5MTAxLCJzdWIiOiI0OCIsInNjb3BlcyI6W119.SzlRGvvR1MLqNG2uYU8OCFRm0nzTNXqKK_3Y9nPUqy7CAGcqWWS_kzGbrMn3DR1dck7-rManDR1OlxpErRyQ-8EDrFgVpHzfFIdGoha_Jtnjgk7SoHO24PElfbxbQzPLdqOBRWY2du5tjQuconUeWY1TsouglH6L_Uvn-DqgbDHqGkv6yqwGSwtHEzLgDI72Dd4BMMmBnliKBtLYBArDQEfmUXjNI220X1VVa0NzCgYsvVebYW80OZ-E0vq8PJD3uOEgl4huO6dOsWSDQN_h2IQR0tVN_9fxPMasaP9oWjjW5Rs-wDb2qHKZ15zC0GBYAeEqAqXyfU2qRT_yqAFLHAbzlFRAk3dQ3Hzcfaa2twEVPJvYNi7DcOkQTMU14yvcemBOcG4iDuWWrblJyD6Z3iWPkv5e8bhgkSPyDvkDEx-X2z0wCpYyQXihHXmoiXYmwHVT4Kw2_GctLxqGZNkHEAhs_uW8tDmbCh_eISsbljRjvz6Mjxn_VBmP4GiAjgE6JykTZvm--Wrv767cHe95tK8ppuL18caeBYcdG6HjEmW3uPoOBIflMcv3iaXXeH_hfDoZ-c0Jf6FrwuioLN-C-X8eU_ztC6e67rPk5vNog3kX6C-lpTpjyC5hdTJpdsNJjm4o99nsbB7GvvctB8NhpsGm1L36VGvIi6QVrbaF8nc")
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
//                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final Dialog dialog = new Dialog(BeautyMainPage.context);
//                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
//                            TextView confirm = dialog.findViewById(R.id.confirm);
//                            TextView message = dialog.findViewById(R.id.message);
//                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.Null);
//                            message.setText(R.string.check_internet_con);
//                            confirm.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.cancel();
//
//                                }
//                            });
//                            dialog.show();
//
//                        }
//                    });

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
//                Log.e("Token", gettoken(context));
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            pd.dismiss();
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                });

                try {
                    JSONObject jsonrespone = new JSONObject(mMessage);
                    String success=jsonrespone.getString("success");
                    if (success.equals("true")){

                        JSONArray jsonArray=jsonrespone.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            String bdb_is_bride_service = data.getString("bdb_is_bride_service");
                            String bdb_ser_id = data.getString("bdb_ser_id");
                            String bdb_name = data.getString("bdb_name");
                            String bdb_descr = data.getString("bdb_descr");
                            String bdb_type = data.getString("bdb_type");
                            String bdb_name_ar = data.getString("bdb_name_ar");
                            String bdb_is_fixed_time = data.getString("bdb_is_fixed_time");
                            String bdb_is_fixed_price = data.getString("bdb_is_fixed_price");
                            String bdb_is_hair_service = data.getString("bdb_is_hair_service");
                            String image = data.getString("images");


//                            Log.e("bdb_is_bride",bdb_is_bride);
                            Log.e("bdb_is_bride_service",bdb_is_bride_service);
                            if (bdb_is_bride_service.equals("0"))
                            itemArrayList.add(new ListServiceModel(bdb_ser_id,bdb_name,bdb_name_ar,bdb_descr,bdb_type,bdb_is_fixed_price,bdb_is_fixed_time,
                                    bdb_is_hair_service,bdb_is_bride_service,image));

                        }



                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ListServicesFragment.servicesAdapter.notifyDataSetChanged();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });



                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }



            }

        });
        //        Log.d("MessageResponse",mMessage);
    }
    public  static  void  getBasicServicebride( final Context context){

        itemArrayList.clear();

        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {

                    showDialog(context);
//                    pd.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        MediaType MEDIA_TYPE = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
//        try {
//            postdata.put("bdb_is_bride_ser",bdb_is_bride_ser);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url("http://clientapp.dcoret.com/api/service/Service")
                .post(body)
                .addHeader("Content-Type","application/json")
                .header("Authorization", "Bearer "+gettoken(context))
//                .header("Authorization", "Bearer "+"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImIyZjc3MjA4MWE1ZjZhNzc4ZTY0NWQ4NjdmOWYxZGVkM2YxY2I5ZTNmYzllZjU5YTc2ZmM5NzE3MzIzMzg3OThhMDg4NmJiZjJjNWUyMmIxIn0.eyJhdWQiOiIxIiwianRpIjoiYjJmNzcyMDgxYTVmNmE3NzhlNjQ1ZDg2N2Y5ZjFkZWQzZjFjYjllM2ZjOWVmNTlhNzZmYzk3MTczMjMzODc5OGEwODg2YmJmMmM1ZTIyYjEiLCJpYXQiOjE1NTg5MDY3MDEsIm5iZiI6MTU1ODkwNjcwMSwiZXhwIjoxNTkwNTI5MTAxLCJzdWIiOiI0OCIsInNjb3BlcyI6W119.SzlRGvvR1MLqNG2uYU8OCFRm0nzTNXqKK_3Y9nPUqy7CAGcqWWS_kzGbrMn3DR1dck7-rManDR1OlxpErRyQ-8EDrFgVpHzfFIdGoha_Jtnjgk7SoHO24PElfbxbQzPLdqOBRWY2du5tjQuconUeWY1TsouglH6L_Uvn-DqgbDHqGkv6yqwGSwtHEzLgDI72Dd4BMMmBnliKBtLYBArDQEfmUXjNI220X1VVa0NzCgYsvVebYW80OZ-E0vq8PJD3uOEgl4huO6dOsWSDQN_h2IQR0tVN_9fxPMasaP9oWjjW5Rs-wDb2qHKZ15zC0GBYAeEqAqXyfU2qRT_yqAFLHAbzlFRAk3dQ3Hzcfaa2twEVPJvYNi7DcOkQTMU14yvcemBOcG4iDuWWrblJyD6Z3iWPkv5e8bhgkSPyDvkDEx-X2z0wCpYyQXihHXmoiXYmwHVT4Kw2_GctLxqGZNkHEAhs_uW8tDmbCh_eISsbljRjvz6Mjxn_VBmP4GiAjgE6JykTZvm--Wrv767cHe95tK8ppuL18caeBYcdG6HjEmW3uPoOBIflMcv3iaXXeH_hfDoZ-c0Jf6FrwuioLN-C-X8eU_ztC6e67rPk5vNog3kX6C-lpTpjyC5hdTJpdsNJjm4o99nsbB7GvvctB8NhpsGm1L36VGvIi6QVrbaF8nc")
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
//                    ((AppCompatActivity) BeautyMainPage.context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final Dialog dialog = new Dialog(BeautyMainPage.context);
//                            dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                            dialog.setContentView(R.layout.check_internet_alert_dialog__layout);
//                            TextView confirm = dialog.findViewById(R.id.confirm);
//                            TextView message = dialog.findViewById(R.id.message);
//                            TextView title = dialog.findViewById(R.id.title);
//                            title.setText(R.string.Null);
//                            message.setText(R.string.check_internet_con);
//                            confirm.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    dialog.cancel();
//
//                                }
//                            });
//                            dialog.show();
//
//                        }
//                    });

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
//                Log.e("Token", gettoken(context));
                Log.e("TAG", mMessage);
                ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            pd.dismiss();
//                        ReservationFragment.pullToRefresh.setRefreshing(false);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                });

                try {
                    JSONObject jsonrespone = new JSONObject(mMessage);
                    String success=jsonrespone.getString("success");
                    if (success.equals("true")){

                        JSONArray jsonArray=jsonrespone.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            String bdb_is_bride_service = data.getString("bdb_is_bride_service");
                            String bdb_ser_id = data.getString("bdb_ser_id");
                            String bdb_name = data.getString("bdb_name");
                            String bdb_descr = data.getString("bdb_descr");
                            String bdb_type = data.getString("bdb_type");
                            String bdb_name_ar = data.getString("bdb_name_ar");
                            String bdb_is_fixed_time = data.getString("bdb_is_fixed_time");
                            String bdb_is_fixed_price = data.getString("bdb_is_fixed_price");
                            String bdb_is_hair_service = data.getString("bdb_is_hair_service");
                            String image = data.getString("images");


//                            Log.e("bdb_is_bride",bdb_is_bride);
                            Log.e("bdb_is_bride_service",bdb_is_bride_service);
                            if (bdb_is_bride_service.equals("1"))
                                itemArrayList.add(new ListServiceModel(bdb_ser_id,bdb_name,bdb_name_ar,bdb_descr,bdb_type,bdb_is_fixed_price,bdb_is_fixed_time,
                                    bdb_is_hair_service,bdb_is_bride_service,image));

                        }



                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    ListServicesBrideFragment.servicesAdapter.notifyDataSetChanged();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        });



                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }



            }

        });
        //        Log.d("MessageResponse",mMessage);
    }


    private static final String arabic = "\u06f0\u06f1\u06f2\u06f3\u06f4\u06f5\u06f6\u06f7\u06f8\u06f9";
    public static String arabicToDecimal(String number) {
        char[] chars = new char[number.length()];
        for(int i=0;i<number.length();i++) {
            char ch = number.charAt(i);
            if (ch >= 0x0660 && ch <= 0x0669)
                ch -= 0x0660 - '0';
            else if (ch >= 0x06f0 && ch <= 0x06F9)
                ch -= 0x06f0 - '0';
            chars[i] = ch;
        }
        return new String(chars);
    }
    public static String convertToArabic(String value){
        String newValue;
        if (ln.equals("ar")) {
            newValue = (((((((((((value + "")
                    .replaceAll("1", "١")).replaceAll("2", "٢"))
                    .replaceAll("3", "٣")).replaceAll("4", "٤"))
                    .replaceAll("5", "٥")).replaceAll("6", "٦"))
                    .replaceAll("7", "٧")).replaceAll("8", "٨"))
                    .replaceAll("9", "٩")).replaceAll("0", "٠"));
        }else {
            newValue=value;
        }
        return newValue;
    }

}




