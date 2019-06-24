package com.dcoret.beautyclient.API;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.dcoret.beautyclient.Activities.Login;
import com.dcoret.beautyclient.Activities.Offers;
import com.dcoret.beautyclient.Activities.Services;
import com.dcoret.beautyclient.Activities.TabOne;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.DataClass.BrowseServiceItem;
import com.dcoret.beautyclient.DataClass.Cities;
import com.dcoret.beautyclient.DataClass.FilterAndSortModel;
import com.dcoret.beautyclient.DataClass.LocationTitles;
import com.dcoret.beautyclient.Fragments.AccountFragment;
import com.dcoret.beautyclient.Fragments.MapFragment;
import com.dcoret.beautyclient.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
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

    //-------------------------------------------
    public static String gettoken(Context context){
        String shared_token=((AppCompatActivity)context).getSharedPreferences("LOGIN",Context.MODE_PRIVATE).getString("token",null);
    return shared_token;
    }
    //-------------------------------------not used----------------
    public static void register(final String email, final String name, final String phone, final String password, final String confirm_password, String loc_long
            , String loc_lat, String city, String gender,final Context context){
//        pd=new ProgressDialog(context);
        Thread a=new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
//       pd.setMessage("جار التحقق...");
//        pd.show();
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("bdb_email", email)
                        .addFormDataPart("bdb_name", name)
                        .addFormDataPart("bdb_mobile", phone)
                        .addFormDataPart("password", password)
                        .addFormDataPart("c_password", confirm_password)
                        .addFormDataPart("bdb_loc_long", "154")
                        .addFormDataPart("bdb_loc_lat", "-34")
                        .addFormDataPart("bdb_city", "1")
                        .addFormDataPart("bdb_gender", "1")
                        .build();
                String URL = "http://clientapp.dcoret.com/api/auth/user/register/new_user";
                final okhttp3.Request requestb = new okhttp3.Request.Builder()
                        .url(URL)
                        .post(requestBody)
                        .build();
                Log.d("request",requestb.toString());


                try (okhttp3.Response response = client.newCall(requestb).execute()) {
                    Log.d("Response",response.toString());
//                       pd.dismiss();
                    if (!response.isSuccessful()){
                        error =response.toString();
//                       pd.dismiss();
                    }

                    Headers responseHeaders = response.headers();
                    for (int i = 0; i < responseHeaders.size(); i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
        a.start();
//           Toast.makeText(Register.context,error,Toast.LENGTH_LONG).show();
    }
        //----------------------------Not Used---------------------------
        public static void browseclass(Context context){
    //        pd=new ProgressDialog(context);
            Thread a=new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
    //       pd.setMessage("جار التحقق...");
    //        pd.show();
                    RequestBody requestBody = new MultipartBody.Builder()
                            .setType(MultipartBody.FORM)
                            .addFormDataPart("bdb_type", "1")
                            .build();
                    String URL = "http://clientapp.dcoret.com/api/auth/user/register/new_user";
                    final okhttp3.Request requestb = new okhttp3.Request.Builder()
                            .url(URL)
                            .post(requestBody)
                            .build();
                    Log.d("request",requestb.toString());


                    try (okhttp3.Response response = client.newCall(requestb).execute()) {
                        Log.d("Response",response.toString());
    //                       pd.dismiss();
                        if (!response.isSuccessful()){
                            error =response.toString();
    //                       pd.dismiss();
                        }

                        Headers responseHeaders = response.headers();
                        for (int i = 0; i < responseHeaders.size(); i++) {
                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                        }

                        System.out.println(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            });
            a.start();
    //           Toast.makeText(Register.context,error,Toast.LENGTH_LONG).show();
        }
        //---استعراض خدمات المزودين لخدمة معينة-----------------------------done
        static  String json;
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        //------------------ not used-------------
        public static String okHttpServiceBrowse(final Context context, final String bdb_ser_id){
    //        pd=new ProgressDialog(context);
    //        Thread a=new Thread(new Runnable() {
    //            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    //            @Override
    //            public void run() {
    //       pd.setMessage("جار التحقق...");
    //        pd.show();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)

                    .addFormDataPart("bdb_ser_id", bdb_ser_id)
                    .build();
            String URL = "http://clientapp.dcoret.com/api/service/browseService";
            final okhttp3.Request requestb = new okhttp3.Request.Builder()
                    .url(URL)
                    .post(requestBody)
                    .addHeader("Content-Type","application/json")
                    .addHeader("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjQyZmZlN2Q5NmYzNzM4OWRhZDNjMDllMDk5NGQwZDY4ODdmNGI0YzMxMzlmYjZhOTE1MTc5NWIwMzVmYTE5MDBhODI0NzI3NThmYTM2MDFhIn0.eyJhdWQiOiIxIiwianRpIjoiNDJmZmU3ZDk2ZjM3Mzg5ZGFkM2MwOWUwOTk0ZDBkNjg4N2Y0YjRjMzEzOWZiNmE5MTUxNzk1YjAzNWZhMTkwMGE4MjQ3Mjc1OGZhMzYwMWEiLCJpYXQiOjE1NTgyOTY3ODEsIm5iZiI6MTU1ODI5Njc4MSwiZXhwIjoxNTg5OTE5MTgxLCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.MG04uKIgEjPHwU_bo3ai_f1oGhOJdk0DQr5_KFQYKAZOj5EUM7ZVi08JnefgSB9R9rPAE3VcZaIBCGJ2OrjI0zO3K7PQZEQ2m-gmdsMye4sMGL2LeRrm6aHOKpJDY_jdpJMgFgbOL3oFh9XbVxbha0f0ofhOhiSl4oIfZ8-G7VzWZPuwA1dIt1QfgqjfWBSpWd9JPe7s3PrwrcUcXIF8qObjl6MqWQ3I33I8g2-Vc9O7b356_21Un_XP6lYbZMN4VWKUifqYiO2t509M6RjUovDI_cd9a30EHB7hTdIkmxHP2JKudwHbHil7cEkel7UQAvfJrbcapm60Jb8fucWoAtedtuPpxYEgxAZ0HqZNi5ynPoO1VIygHOiYvI8iNzNwkRMJ5quV4PIK4SaGArZs6Nd5Pz9vXKc-apWo2WzDZ9R1KQg0y3LNNRyMPmGjVN_8u3QixbomiXuPoOAsKuZzzCsRZMdQ2sug0nlm69BiCSbq3Zn40gmIqTXAhG1AIcm2WqgCqi9SKWyWOBc8Tv2NnnccH_FCkUCPCa54ZRMsMGrkycG6oV1wYQkpBKF1lS0yx2NCX0RJGFLEATkMKRX1wdKOgjmyALtk5IBsN9KOr6rBl4sWEQb0zsVgzaTdHqex4j0a03jtsbq8RplKJeY5SbJOUv-o8EC6gjMbJzCa5ik")
                    .build();
            Log.d("request",requestb.toString());


            try (okhttp3.Response response = client.newCall(requestb).execute()) {
                Log.d("Response",response.toString());
    //                       pd.dismiss();
                if (!response.isSuccessful()){
                    error =response.toString();
    //                       pd.dismiss();
                }

                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                json=response.body().string();
                System.out.println(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return json;


        }
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
        public  static  String  new_user(final String email, final String name, final String phone, final String password, final String confirm_password, final String loc_long
                , final String loc_lat, final String city, final String gender, final  String url, final Context context){
            if (validationPassword(password)){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd=new ProgressDialog(context);
            pd.show();
    //        String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try {
                postdata.put("bdb_email", email);
                postdata.put("bdb_name", name);
                postdata.put("bdb_mobile", phone);
                postdata.put("bdb_gender", "1");
                postdata.put("password", password);
                postdata.put("c_password", confirm_password);
                postdata.put("bdb_loc_long", "134");
                postdata.put("bdb_loc_lat", "54");
                postdata.put("bdb_city", "1");
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
//                .addHeader("Accept","application/json")
                .header("Authorization", "Bearer "+gettoken(context))

//                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjQyZmZlN2Q5NmYzNzM4OWRhZDNjMDllMDk5NGQwZDY4ODdmNGI0YzMxMzlmYjZhOTE1MTc5NWIwMzVmYTE5MDBhODI0NzI3NThmYTM2MDFhIn0.eyJhdWQiOiIxIiwianRpIjoiNDJmZmU3ZDk2ZjM3Mzg5ZGFkM2MwOWUwOTk0ZDBkNjg4N2Y0YjRjMzEzOWZiNmE5MTUxNzk1YjAzNWZhMTkwMGE4MjQ3Mjc1OGZhMzYwMWEiLCJpYXQiOjE1NTgyOTY3ODEsIm5iZiI6MTU1ODI5Njc4MSwiZXhwIjoxNTg5OTE5MTgxLCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.MG04uKIgEjPHwU_bo3ai_f1oGhOJdk0DQr5_KFQYKAZOj5EUM7ZVi08JnefgSB9R9rPAE3VcZaIBCGJ2OrjI0zO3K7PQZEQ2m-gmdsMye4sMGL2LeRrm6aHOKpJDY_jdpJMgFgbOL3oFh9XbVxbha0f0ofhOhiSl4oIfZ8-G7VzWZPuwA1dIt1QfgqjfWBSpWd9JPe7s3PrwrcUcXIF8qObjl6MqWQ3I33I8g2-Vc9O7b356_21Un_XP6lYbZMN4VWKUifqYiO2t509M6RjUovDI_cd9a30EHB7hTdIkmxHP2JKudwHbHil7cEkel7UQAvfJrbcapm60Jb8fucWoAtedtuPpxYEgxAZ0HqZNi5ynPoO1VIygHOiYvI8iNzNwkRMJ5quV4PIK4SaGArZs6Nd5Pz9vXKc-apWo2WzDZ9R1KQg0y3LNNRyMPmGjVN_8u3QixbomiXuPoOAsKuZzzCsRZMdQ2sug0nlm69BiCSbq3Zn40gmIqTXAhG1AIcm2WqgCqi9SKWyWOBc8Tv2NnnccH_FCkUCPCa54ZRMsMGrkycG6oV1wYQkpBKF1lS0yx2NCX0RJGFLEATkMKRX1wdKOgjmyALtk5IBsN9KOr6rBl4sWEQb0zsVgzaTdHqex4j0a03jtsbq8RplKJeY5SbJOUv-o8EC6gjMbJzCa5ik")
//                .header("Content-Type", "application/json")
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

        Log.d("MessageResponse",mMessage);
//        return mMessage;

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

            if(!sh.getString("bdb_name",null).equals("")){
                e_bdb_name.setText(sh.getString("bdb_name",null));
                e_bdb_email.setText(sh.getString("bdb_email",null));
                e_bdb_mobile.setText(sh.getString("bdb_mobile",null));

            }else {
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
    //                .header("Content-Type", "application/json")
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
                                String bdb_name = data.getString("bdb_name");
                                e_bdb_name.setText(bdb_name);
                                String bdb_email = data.getString("bdb_email");
                                e_bdb_email.setText(bdb_email);
                                String bdb_mobile = data.getString("bdb_mobile");
                                e_bdb_mobile.setText(bdb_mobile);
                                editor.putString("bdb_name", bdb_name);
                                editor.putString("bdb_email", bdb_email);
                                editor.putString("bdb_mobile", bdb_mobile);
                                editor.apply();
                                editor.commit();

                            }
                        } catch (JSONException e) {

                        }

                        pd.dismiss();

                    }

                });

                Log.d("MessageResponse", mMessage);
            }

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
//            .addHeader("Content-Type","application/x-www-form-urlencoded")
//            .addHeader("X-Requested-With","XMLHttpRequest")
//            .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjQyZmZlN2Q5NmYzNzM4OWRhZDNjMDllMDk5NGQwZDY4ODdmNGI0YzMxMzlmYjZhOTE1MTc5NWIwMzVmYTE5MDBhODI0NzI3NThmYTM2MDFhIn0.eyJhdWQiOiIxIiwianRpIjoiNDJmZmU3ZDk2ZjM3Mzg5ZGFkM2MwOWUwOTk0ZDBkNjg4N2Y0YjRjMzEzOWZiNmE5MTUxNzk1YjAzNWZhMTkwMGE4MjQ3Mjc1OGZhMzYwMWEiLCJpYXQiOjE1NTgyOTY3ODEsIm5iZiI6MTU1ODI5Njc4MSwiZXhwIjoxNTg5OTE5MTgxLCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.MG04uKIgEjPHwU_bo3ai_f1oGhOJdk0DQr5_KFQYKAZOj5EUM7ZVi08JnefgSB9R9rPAE3VcZaIBCGJ2OrjI0zO3K7PQZEQ2m-gmdsMye4sMGL2LeRrm6aHOKpJDY_jdpJMgFgbOL3oFh9XbVxbha0f0ofhOhiSl4oIfZ8-G7VzWZPuwA1dIt1QfgqjfWBSpWd9JPe7s3PrwrcUcXIF8qObjl6MqWQ3I33I8g2-Vc9O7b356_21Un_XP6lYbZMN4VWKUifqYiO2t509M6RjUovDI_cd9a30EHB7hTdIkmxHP2JKudwHbHil7cEkel7UQAvfJrbcapm60Jb8fucWoAtedtuPpxYEgxAZ0HqZNi5ynPoO1VIygHOiYvI8iNzNwkRMJ5quV4PIK4SaGArZs6Nd5Pz9vXKc-apWo2WzDZ9R1KQg0y3LNNRyMPmGjVN_8u3QixbomiXuPoOAsKuZzzCsRZMdQ2sug0nlm69BiCSbq3Zn40gmIqTXAhG1AIcm2WqgCqi9SKWyWOBc8Tv2NnnccH_FCkUCPCa54ZRMsMGrkycG6oV1wYQkpBKF1lS0yx2NCX0RJGFLEATkMKRX1wdKOgjmyALtk5IBsN9KOr6rBl4sWEQb0zsVgzaTdHqex4j0a03jtsbq8RplKJeY5SbJOUv-o8EC6gjMbJzCa5ik")
                .header("Content-Type", "application/json")
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

//                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(context,j.toString(),Toast.LENGTH_LONG).show();
//
//                            }
//                        });
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
//        String url = "http://clientapp.dcoret.com/api/service/Service";
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
        public  static  void  deleteAccount(final  String url,final Context context){
            new AlertDialog.Builder(context)
                    .setTitle(R.string.DeleteAccount)
                    .setMessage(R.string.DeleteAccountMessage)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
//                            APICall.logout("http://clientapp.dcoret.com/api/auth/user/logout",BeautyMainPage.context);
                            MediaType MEDIA_TYPE = MediaType.parse("application/json");
                            pd=new ProgressDialog(context);
                            pd.show();
//        String url = "http://clientapp.dcoret.com/api/service/Service";
                            OkHttpClient client = new OkHttpClient();
                            JSONObject postdata = new JSONObject();


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
        public  static  void  addAddress(final  String url,String bdb_city_id,String bdb_loc_long,String bdb_loc_lat,String bdb_descr,final Context context){
                MediaType MEDIA_TYPE = MediaType.parse("application/json");
                pd=new ProgressDialog(context);
                pd.show();
        //        String url = "http://clientapp.dcoret.com/api/service/Service";
                OkHttpClient client = new OkHttpClient();
                JSONObject postdata = new JSONObject();
                try {
                    postdata.put("bdb_city_id", bdb_city_id);
                    postdata.put("bdb_loc_long", bdb_loc_long);
                    postdata.put("bdb_loc_lat", bdb_loc_lat);
                    postdata.put("bdb_descr", bdb_descr);
                }catch (JSONException je){
                    je.printStackTrace();
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





                    }




                });

                Log.d("MessageResponse",mMessage);
//                return mMessage;

            }
        //-------------------------------------
        public  static  String  deleteAddress(final  String url,String bdb_id,final Context context){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd=new ProgressDialog(context);
            pd.show();
        //        String url = "http://clientapp.dcoret.com/api/service/Service";
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



                }




            });

            Log.d("MessageResponse",mMessage);
            return mMessage;

        }
        //---------------------------
        public  static  String  updateaddress(final  String url,String bdb_city_id,String bdb_loc_long,String bdb_loc_lat,String bdb_descr,final Context context){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd=new ProgressDialog(context);
            pd.show();
        //        String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try {
                postdata.put("bdb_city_id", bdb_city_id);
                postdata.put("bdb_loc_long", bdb_loc_long);
                postdata.put("bdb_loc_lat", bdb_loc_lat);
                postdata.put("bdb_descr", bdb_descr);
            }catch (JSONException je){
                je.printStackTrace();
            }

            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .put(body)
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
//                    pd.dismiss();
                            try {
                                JSONObject jsonObject = new JSONObject(mMessage);
                                String success = jsonObject.getString("success");

                                if (success.equals("true")) {
//                         Log.e("getmd5"  , getMD5(old_pass));
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
//
//

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
//        String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try {
            postdata.put("bdb_name", bdb_name);
//            postdata.put("bdb_email", bdb_email);
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
                        final JSONObject j=new JSONObject(mMessage);
                        String success=j.getString("success");
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
                                        APICall.showSweetDialog(BeautyMainPage.context,"", "Error: " + j.getString("message"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (JSONException je) {
                            je.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();

                    }
                    SharedPreferences.Editor editor = context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE).edit();
                    editor.putString("bdb_name", bdb_name);
//                    editor.putString("bdb_email", bdb_email);
                    editor.commit();

                    pd.dismiss();


                }

            });

            Log.d("MessageResponse",mMessage);
//        return mMessage;
        }
        //------------------ استعراض معلومات خدمة معينة---------------------
        public  static  String  details_user(final  String url,final Context context){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd=new ProgressDialog(context);
            pd.show();
            //        String url = "http://clientapp.dcoret.com/api/service/Service";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();


            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type","application/json")
//                    .addHeader("X-Requested-With","XMLHttpRequest")
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
        public  static  void  reset_pass(final  String url,final Context context){

        final Dialog d=new Dialog(context);
            d.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            d.setContentView(R.layout.map_title_layout);
        final TextView message=d.findViewById(R.id.message);
        TextView confirm=d.findViewById(R.id.confirm);
        final EditText number=d.findViewById(R.id.code);
        message.setText(R.string.EntermobnumberAlert);
        d.show();

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number.getText().toString().isEmpty()){

                }else {
                    MediaType MEDIA_TYPE = MediaType.parse("application/json");
                    pd=new ProgressDialog(context);
                    pd.show();
                    OkHttpClient client = new OkHttpClient();
                    JSONObject postdata = new JSONObject();
                    try {
                        postdata.put("mobile", number.getText().toString());
                    }catch (JSONException je){
                        Log.e("Err",je.getMessage());
                    }

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

                            d.dismiss();
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
                            try {
                                JSONObject jsonObject=new JSONObject(mMessage);
                                String success=jsonObject.getString("success");
                                if (success.equals("true")){
                                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(context, R.string.SendNewPass, Toast.LENGTH_LONG).show();
                                        }
                                    });
                                }else{
                                    String message=jsonObject.getString("error");
                                    if (message.equals("We can not find a user with that mobile")){
                                        ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                showSweetDialog(context,R.string.ExuseMeAlert,R.string.CantFindNum);
//                                                Toast.makeText(context, R.string.CantFindNum, Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }
                            }catch (JSONException je){
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
        //        String url = "http://clientapp.dcoret.com/api/service/Service";
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

//                SharedPreferences.Editor editor = ((AppCompatActivity)context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).edit();
//                editor.remove("name"); // will delete key name
//                editor.remove("pass"); // will delete key pass
//                editor.commit();
//                Intent intent=new Intent(context,Login.class);
//                Login.logout=true;
//                ((AppCompatActivity) context).finish();
//                context.startActivity(intent);

//            Intent intent=new Intent(context, Login.class);
//            context.startActivity(intent);
//            ((AppCompatActivity) context).finish();


        }




    });

    Log.d("MessageResponse",mMessage);
    return mMessage;

}
        //---------------- get cities--------------------
        public  static  String  getcities(final  String url,final Context context){
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
//        pd=new ProgressDialog(context);
//        pd.show();
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();



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
                Log.d("token",gettoken(context));
                Log.e("TAGCities", mMessage);
                try{
                    JSONObject jsonObject=new JSONObject(mMessage);
                    String success=jsonObject.getString("success");
                    if (success.equals("true")){
                        JSONArray citiesArr=jsonObject.getJSONArray("cities");
                        for (int i=0;i<citiesArr.length();i++){
                            JSONObject city=citiesArr.getJSONObject(i);
                            String bdb_id=city.getString("bdb_id");
                            String bdb_region_id=city.getString("bdb_region_id");
                            String bdb_name=city.getString("bdb_name");
                            String bdb_name_ar=city.getString("bdb_name_ar");
                            String bdb_default_dim=city.getString("bdb_default_dim");
                            BeautyMainPage.cities.add(new Cities(bdb_id,bdb_region_id,bdb_name,bdb_name_ar,bdb_default_dim));
                        }
                        Log.e("CITIES",BeautyMainPage.cities.size()+"");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
//                pd.dismiss();
            }
        });

        Log.d("MessageResponse",mMessage);
        return mMessage;

}
        //------------- automated Browse ----------------------
        public  static  String  automatedBrowse(final  String url,String lang,String itemPerPage,String pageNum,final Context context){
            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            pd=new ProgressDialog(context);
            pd.show();
            OkHttpClient client = new OkHttpClient();


            String temp="{\t\"lang\":\"en\",\n" +
                    "\t\t\"ItemPerPage\":4,\t\n" +
                    "\t\t\"PageNum\":1,\n" +
                    "\t\t\"Filter\":[ \n" +
                    "\t\t\t{\"num\":5,\"value1\":1,\"value2\":1} ,\n" +
                    "\t\t\t{\"num\":6,\"value1\":4,\"value2\":0}  ,\n" +
                    "\t\t\t{\"num\":34,\"value1\":36.47792,\"value2\":0}  ,\n" +
                    "\t\t\t{\"num\":35,\"value1\":36.23389,\"value2\":0} \n" +
                    "\t\t\t\n" +
                    "\t\t ]\n" +
                    "\t\n" +
                    "}";

            String ttt="{\t\"lang\":\"en\",\n" +
                    "\t\t\"ItemPerPage\":4,\t\n" +
                    "\t\t\"PageNum\":1,\n" +
                    "\t\t\"Filter\":[ \n" +
                    "\t{\"num\":34,\"value1\":36.47792,\"value2\":0}," +
                    "\t\t\t{\"num\":35,\"value1\":36.23389,\"value2\":0},"+
                    getFilterList()+  // need to try catch
                    "\t\t\t]\n" +
                    "}";


            Log.e("JSONPOST",ttt);
             final RequestBody body = RequestBody.create(MEDIA_TYPE, ttt);
            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Content-Type","application/json")
                    .addHeader("Accept","application/json")
                    .addHeader("X-Requested-With","XMLHttpRequest")
                    .header("Authorization","Bearer "+gettoken(context))
//                    .header()
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
//                    Log.d("token",gettoken(context));
                    Log.e("TAG123", mMessage);
                    pd.dismiss();

                    try{
                        JSONObject jsonObject=new JSONObject(mMessage);
                        String success=jsonObject.getString("success");
                        Log.e("success",success);
                        final String message=jsonObject.getString("message");
                        if (success.equals("true")){
                            if (message.equals("success get services")){
                            JSONObject data=jsonObject.getJSONObject("data");
                            JSONArray sersup=data.getJSONArray("sersup");
                            Log.e("SizeSERSUP",sersup.length()+"");
                            TabOne.arrayList.clear();

                            for (int i=0;i<sersup.length();i++){
                                JSONObject jarray = sersup.getJSONObject(i);
                                String bdb_ser_sup_id = jarray.getString("bdb_ser_sup_id");
//                                        bdb_ser_salon = jarray.getString("bdb_ser_salon"),
//                                        bdb_ser_home = jarray.getString("bdb_ser_home"),
//                                        bdb_ser_hall = jarray.getString("bdb_ser_hall"),
//                                        bdb_ser_salon_price = jarray.getString("bdb_ser_salon_price"),
//                                        bdb_ser_home_price = jarray.getString("bdb_ser_home_price"),
//                                        bdb_ser_hall_price = jarray.getString("bdb_ser_hall_price"),
//                                        bdb_time = jarray.getString("bdb_time"),
//                                        bdb_max_clients = jarray.getString("bdb_max_clients"),
//                                        bdb_ser_id = jarray.getString("bdb_ser_id"),
//                                        bdb_sup_id = jarray.getString("bdb_sup_id"),
//                                        bdb_is_offer = jarray.getString("bdb_is_offer"),
//                                        bdb_offer_status = jarray.getString("bdb_offer_status"),
//                                        bdb_offer_start = jarray.getString("bdb_offer_start"),
//                                        bdb_offer_end = jarray.getString("bdb_offer_end"),
//                                        bdb_pack_code = jarray.getString("bdb_pack_code"),
//                                        bdb_is_best = jarray.getString("bdb_is_best"),
//                                        bdb_orgin_id = jarray.getString("bdb_orgin_id"),
//                                        bdb_is_current_price = jarray.getString("bdb_is_current_price"),
//                                        bdb_user_offer_num = jarray.getString("bdb_user_offer_num"),
//                                        bdb_hotel = jarray.getString("bdb_hotel"),
//                                        bdb_hotel_price = jarray.getString("bdb_hotel_price");
                                BrowseServiceItem bsi = new BrowseServiceItem(bdb_ser_sup_id, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",  "", "", "");
                                TabOne.arrayList.add(bsi);



                            }
                            ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    TabOne.refreshRV();

                                }
                            });
                            Log.e("ARRAYLIST",TabOne.arrayList.size()+"");

                            TabOne.recyclerView.invalidate();
                        }else if (message.equals("there is no providers with your search filters")){
                                TabOne.arrayList.clear();
                                ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        TabOne.refreshRV();
                                        Toast.makeText(BeautyMainPage.context,message,Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }
                    }catch (JSONException je){

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
                                addtitle( code.getText().toString(),latLng,marker);
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
                                addtitle( code.getText().toString(),latLng,marker);
                            }


                        });
                        dialog.show();
                       }else {
                        marker.showInfoWindow();
                    }
                }

            });

        }
        private static void addtitle(String title,LatLng latLng ,Marker marker) {
            marker.setTitle(title);
            MapFragment.locationTitles.add(new LocationTitles(latLng,title));
            MapFragment.arrayList.add(title);
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

                            //                                if (code.getText().toString().equals(activation_number)) {
                            dialog.cancel();
                            //----------toast for congratulations---------

                            //                        active_account(context,activation_number);
                            activeAccount("http://clientapp.dcoret.com/api/auth/user/register/activate",
                                    code.getText().toString(),
                                    context);

                            //                        http://clientapp.dcoret.com/api/auth/user/register/activate/6552
                            //                                }
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
     static ArrayList<FilterAndSortModel> filterList=new ArrayList<>();
        public static String getFilterList(){
            String filter="";
//            Log.e("filterlist",filterList.get(19).getValue1());
            for (int i=0;i<filterList.size();i++){
//                    "{\"num\":"+num1+",\"value1\":"+value11+",\"value2\":"+value12+"} ,\n" +

                if (!filterList.get(i).getNum().equals("")){
                filter=filter + "{\"num\":"+filterList.get(i).getNum()+",\"value1\":"+filterList.get(i).getValue1()+",\"value2\":"+filterList.get(i).getValue2()+"} ,";
                }
            }
            Log.e("STRINGFILTER",filter.length()+"");
            try {
              filter=filter.substring(0,filter.length()-1);
                Log.e("STRINGFILTERAfter",filter);

            }catch (Exception e){
                e.printStackTrace();
            }
        return filter;
        }
        static  void initFilterList(){
             if (filterList.size()==0)
             for (int i=0;i<=35;i++){
                 filterList.add(new FilterAndSortModel("","",""));
             }
             Log.e("numberOFFilterList",filterList.size()+"");
         }
        public static void  filterSortAlgorithm(String num,String value1,String value2 ){
            initFilterList();
                int index = Integer.parseInt(num);

                if(!value1.equals(""))
                    filterList.set(index, new FilterAndSortModel(num, value1, value2));

                Log.e("index",filterList.get(index).getNum()+":"+filterList.get(index).getValue1()+":"+filterList.get(index).getValue2()+"");

            }
        public  static void clearFilterList(){
                filterList.clear();
            }

//
//    String num2,String value21,String value22,
//    String num3,String value31,String value32,
//    String num4,String value41,String value42,
//    String num5,String value51,String value52,
//    String num6,String value61,String value62,
//    String num7,String value71,String value72,
//    String num8,String value81,String value82,
//    String num9,String value91,String value92,
//    String num10,String value101,String value102,
//    String num11,String value111,String value112,
//    String num12,String value121,String value122,
//    String num13,String value131,String value132,
//    String num14,String value141,String value142,
//    String num15,String value151,String value152,
//    String num16,String value161,String value162,
//    String num17,String value171,String value172,
//    String num18,String value181,String value182,
//    String num19,String value191,String value192,
//    String num20,String value201,String value202,
//    String num21,String value211,String value212,
//    String num22,String value221,String value222,
//    String num23,String value231,String value232,
//    String num24,String value241,String value242,
//    String num25,String value251,String value252,
//    String num26,String value261,String value262,
//    String num27,String value271,String value272,
//    String num28,String value281,String value282,
//    String num29,String value291,String value292,
//    String num30,String value301,String value302




}

