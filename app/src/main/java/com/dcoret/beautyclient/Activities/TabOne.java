package com.dcoret.beautyclient.Activities;



import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapterNew;
import com.dcoret.beautyclient.DataClass.BrowseServiceItem;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.DataClass.Location_Beauty;
import com.dcoret.beautyclient.DataExample.ServicesData;
import com.dcoret.beautyclient.Fragments.ServicesTabsFragment;
import com.dcoret.beautyclient.Fragments.ServicesTabsFragment2;
import com.dcoret.beautyclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class TabOne extends Fragment {

   static RecyclerView recyclerView;
 static   ArrayList<BrowseServiceItem> arrayList;
   ServicesAdapterNew servicesAdapter;
    ProgressDialog pd;


    static   String[] items={"Service1","Service2","Service3","Service4","Service5","Service6","Service7","Service8","Service9","Service10"};
   static String[] providers_name={"SANA'A","HIBA","NOUR","LAMA","NOUR","HIBA","FIHA'A","SANA'A","LAMA","KAMAR"};

    static   String[] prices={"100","500","450","123","345","411","800","900","600","300"};
    static   String[] rank={"4.1","3.2","3.5","4.7","4.4","3.0","3.0","2.5","2.0","1.5"};
    static  String[] city={"الرياض","الدمام","مكة","الرياض","جدة","الدمام","مكة","مكة","الطائف","مكة"};
    static Location_Beauty[] locations={
            new Location_Beauty(32.7792842,35.8816735),
            new Location_Beauty(31.964383, 35.918756),
            new Location_Beauty(32.709566, 36.137142),
            new Location_Beauty(32.777491, 35.935935),
            new Location_Beauty(32.755262, 35.986746),
            new Location_Beauty(32.725373, 35.944346),
            new Location_Beauty(32.688479, 35.992233),
            new Location_Beauty(32.670663, 36.052908),
            new Location_Beauty(33.506590, 36.299474),
            new Location_Beauty(33.546086, 36.200597),
    };
    static boolean[] fav={
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
    };


    static ArrayList<DataService> dataServices=new ArrayList<>();
    String data= null;

    ArrayList<String> itemstmp=new ArrayList<>();
    ArrayList<String> pricestmp=new ArrayList<>();
    ArrayList<String> ranktmp=new ArrayList<>();
    ArrayList<String> citiestmp=new ArrayList<>();
    ArrayList<Location_Beauty> locationstmp=new ArrayList<Location_Beauty>();
    static View view;

    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        if(dataServices.isEmpty()) {
            new ServicesData(dataServices);
        }
        pd=new ProgressDialog(BeautyMainPage.context);
        pd.setMessage("جار التحقق....");
        pd.show();
        view= inflater.inflate(R.layout.tab_one,container,false);

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        servicesAdapter=new ServicesAdapterNew(BeautyMainPage.context,arrayList,R.layout.service_layout_adapter_last);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(servicesAdapter);
//        String data=APICall.okHttpServiceBrowse(BeautyMainPage.context,"1");



            MediaType MEDIA_TYPE = MediaType.parse("application/json");
            String url = "http://clientapp.dcoret.com/api/service/browseService";
            OkHttpClient client = new OkHttpClient();
            JSONObject postdata = new JSONObject();
            try {
                postdata.put("bdb_ser_id", "1");
//            postdata.put("password", "12345");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

            okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjQyZmZlN2Q5NmYzNzM4OWRhZDNjMDllMDk5NGQwZDY4ODdmNGI0YzMxMzlmYjZhOTE1MTc5NWIwMzVmYTE5MDBhODI0NzI3NThmYTM2MDFhIn0.eyJhdWQiOiIxIiwianRpIjoiNDJmZmU3ZDk2ZjM3Mzg5ZGFkM2MwOWUwOTk0ZDBkNjg4N2Y0YjRjMzEzOWZiNmE5MTUxNzk1YjAzNWZhMTkwMGE4MjQ3Mjc1OGZhMzYwMWEiLCJpYXQiOjE1NTgyOTY3ODEsIm5iZiI6MTU1ODI5Njc4MSwiZXhwIjoxNTg5OTE5MTgxLCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.MG04uKIgEjPHwU_bo3ai_f1oGhOJdk0DQr5_KFQYKAZOj5EUM7ZVi08JnefgSB9R9rPAE3VcZaIBCGJ2OrjI0zO3K7PQZEQ2m-gmdsMye4sMGL2LeRrm6aHOKpJDY_jdpJMgFgbOL3oFh9XbVxbha0f0ofhOhiSl4oIfZ8-G7VzWZPuwA1dIt1QfgqjfWBSpWd9JPe7s3PrwrcUcXIF8qObjl6MqWQ3I33I8g2-Vc9O7b356_21Un_XP6lYbZMN4VWKUifqYiO2t509M6RjUovDI_cd9a30EHB7hTdIkmxHP2JKudwHbHil7cEkel7UQAvfJrbcapm60Jb8fucWoAtedtuPpxYEgxAZ0HqZNi5ynPoO1VIygHOiYvI8iNzNwkRMJ5quV4PIK4SaGArZs6Nd5Pz9vXKc-apWo2WzDZ9R1KQg0y3LNNRyMPmGjVN_8u3QixbomiXuPoOAsKuZzzCsRZMdQ2sug0nlm69BiCSbq3Zn40gmIqTXAhG1AIcm2WqgCqi9SKWyWOBc8Tv2NnnccH_FCkUCPCa54ZRMsMGrkycG6oV1wYQkpBKF1lS0yx2NCX0RJGFLEATkMKRX1wdKOgjmyALtk5IBsN9KOr6rBl4sWEQb0zsVgzaTdHqex4j0a03jtsbq8RplKJeY5SbJOUv-o8EC6gjMbJzCa5ik")
                    .header("Content-Type", "application/json")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    String mMessage = e.getMessage().toString();
                    Log.w("failure Response", mMessage);
                    //call.cancel();
                    pd.dismiss();
                }

                @Override
                public void onResponse(Call call, okhttp3.Response response) throws IOException {
                    mMessage = response.body().string();
                    Log.e("TAG", mMessage);
                    data = mMessage;
                    try {
                        JSONObject j = new JSONObject(mMessage);
                        JSONArray dataArry = j.getJSONArray("data");

                        for (int i = 0; i < dataArry.length(); i++) {
                            JSONObject jsonObject = dataArry.getJSONObject(i);
                            String bdb_ser_sup_id = jsonObject.getString("bdb_ser_sup_id"),
                                    bdb_ser_salon = jsonObject.getString("bdb_ser_salon"),
                                    bdb_ser_home = jsonObject.getString("bdb_ser_home"),
                                    bdb_ser_hall = jsonObject.getString("bdb_ser_hall"),
                                    bdb_ser_salon_price = jsonObject.getString("bdb_ser_salon_price"),
                                    bdb_ser_home_price = jsonObject.getString("bdb_ser_home_price"),
                                    bdb_ser_hall_price = jsonObject.getString("bdb_ser_hall_price"),
                                    bdb_time = jsonObject.getString("bdb_time"),
                                    bdb_max_clients = jsonObject.getString("bdb_max_clients"),
                                    bdb_ser_id = jsonObject.getString("bdb_ser_id"),
                                    bdb_sup_id = jsonObject.getString("bdb_sup_id"),
                                    bdb_is_offer = jsonObject.getString("bdb_is_offer"),
                                    bdb_offer_status = jsonObject.getString("bdb_offer_status"),
                                    bdb_offer_start = jsonObject.getString("bdb_offer_start"),
                                    bdb_offer_end = jsonObject.getString("bdb_offer_end"),
                                    bdb_pack_code = jsonObject.getString("bdb_pack_code"),
                                    bdb_is_best = jsonObject.getString("bdb_is_best"),
                                    bdb_orgin_id = jsonObject.getString("bdb_orgin_id"),
                                    bdb_is_current_price = jsonObject.getString("bdb_is_current_price"),
//                                    bdb_is_deleted = jsonObject.getString("bdb_is_deleted"),
                                    bdb_user_offer_num = jsonObject.getString("bdb_user_offer_num"),
                                    bdb_hotel = jsonObject.getString("bdb_hotel"),
                                    bdb_hotel_price = jsonObject.getString("bdb_hotel_price");
                            BrowseServiceItem bsi = new BrowseServiceItem(bdb_ser_sup_id, bdb_ser_salon, bdb_ser_home, bdb_ser_hall, bdb_ser_salon_price, bdb_ser_home_price, bdb_ser_hall_price, bdb_time, bdb_max_clients, bdb_ser_id, bdb_sup_id, bdb_is_offer, bdb_offer_status, bdb_offer_start, bdb_offer_end, bdb_pack_code, bdb_is_best, bdb_orgin_id, bdb_is_current_price,  bdb_user_offer_num, bdb_hotel, bdb_hotel_price);
                            arrayList.add(bsi);
                        }
                        pd.dismiss();

                        getActivity().runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              servicesAdapter.notifyDataSetChanged();
                          }
                      });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });


//        recyclerView.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
//        recyclerView.setAdapter(new ServicesAdapter(BeautyMainPage.context,items,prices,rank,city,locations,fav));
//        recyclerView.setAdapter(new ServicesAdapterNew(BeautyMainPage.context,items,true,R.layout.service_layout_adapter_last));
            return view;

        }


    public static void gridlistitems(){
        if (ServicesAdapterNew.list==true) {
        Log.d("gridlist",ServicesAdapterNew.list+"");
            recyclerView = view.findViewById(R.id.recycleview);
            recyclerView.setLayoutManager(new GridLayoutManager(BeautyMainPage.context,2));
//        recyclerView.setAdapter(new ServicesAdapter(BeautyMainPage.context,items,prices,rank,city,locations,fav));
            recyclerView.setAdapter(new ServicesAdapterNew(BeautyMainPage.context, items,ServicesAdapterNew.list,R.layout.service_layout_adapter_grid_last));
            ServicesAdapterNew.list=false;
        }else {
            Log.d("gridlist",ServicesAdapterNew.list+"");
            recyclerView = view.findViewById(R.id.recycleview);
            recyclerView.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
//        recyclerView.setAdapter(new ServicesAdapter(BeautyMainPage.context,items,prices,rank,city,locations,fav));
            recyclerView.setAdapter(new ServicesAdapterNew(BeautyMainPage.context, items,ServicesAdapterNew.list,R.layout.service_layout_adapter_last));
            ServicesAdapterNew.list=true;
        }
    }
boolean grid=false;

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.list:
                            grid=false;
                            recyclerView=view.findViewById(R.id.recycleview);
                            recyclerView.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
//                            recyclerView.setAdapter(new ServicesAdapter(BeautyMainPage.context,items,prices,rank,city,locations,grid,fav));
                            recyclerView.setAdapter(new ServicesAdapterNew(BeautyMainPage.context,items));
                            return true;
                        case R.id.grid:
                            grid=true;
                            recyclerView = view.findViewById(R.id.recycleview);
                            recyclerView.setLayoutManager(new GridLayoutManager(BeautyMainPage.context, 2));
//                            recyclerView.setAdapter(new ServicesAdapter(BeautyMainPage.context,items,prices,rank,city,locations,grid,fav));
                            recyclerView.setAdapter(new ServicesAdapterNew(BeautyMainPage.context,items));

                            return true;
                    }

                    return false;
                }
            };

    String mMessage="";
    public String postRequest() throws IOException {
        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "http://clientapp.dcoret.com/api/service/browseService";

        OkHttpClient client = new OkHttpClient();

        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_ser_id", "1");
//            postdata.put("password", "12345");
        } catch(JSONException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6IjQyZmZlN2Q5NmYzNzM4OWRhZDNjMDllMDk5NGQwZDY4ODdmNGI0YzMxMzlmYjZhOTE1MTc5NWIwMzVmYTE5MDBhODI0NzI3NThmYTM2MDFhIn0.eyJhdWQiOiIxIiwianRpIjoiNDJmZmU3ZDk2ZjM3Mzg5ZGFkM2MwOWUwOTk0ZDBkNjg4N2Y0YjRjMzEzOWZiNmE5MTUxNzk1YjAzNWZhMTkwMGE4MjQ3Mjc1OGZhMzYwMWEiLCJpYXQiOjE1NTgyOTY3ODEsIm5iZiI6MTU1ODI5Njc4MSwiZXhwIjoxNTg5OTE5MTgxLCJzdWIiOiIyIiwic2NvcGVzIjpbXX0.MG04uKIgEjPHwU_bo3ai_f1oGhOJdk0DQr5_KFQYKAZOj5EUM7ZVi08JnefgSB9R9rPAE3VcZaIBCGJ2OrjI0zO3K7PQZEQ2m-gmdsMye4sMGL2LeRrm6aHOKpJDY_jdpJMgFgbOL3oFh9XbVxbha0f0ofhOhiSl4oIfZ8-G7VzWZPuwA1dIt1QfgqjfWBSpWd9JPe7s3PrwrcUcXIF8qObjl6MqWQ3I33I8g2-Vc9O7b356_21Un_XP6lYbZMN4VWKUifqYiO2t509M6RjUovDI_cd9a30EHB7hTdIkmxHP2JKudwHbHil7cEkel7UQAvfJrbcapm60Jb8fucWoAtedtuPpxYEgxAZ0HqZNi5ynPoO1VIygHOiYvI8iNzNwkRMJ5quV4PIK4SaGArZs6Nd5Pz9vXKc-apWo2WzDZ9R1KQg0y3LNNRyMPmGjVN_8u3QixbomiXuPoOAsKuZzzCsRZMdQ2sug0nlm69BiCSbq3Zn40gmIqTXAhG1AIcm2WqgCqi9SKWyWOBc8Tv2NnnccH_FCkUCPCa54ZRMsMGrkycG6oV1wYQkpBKF1lS0yx2NCX0RJGFLEATkMKRX1wdKOgjmyALtk5IBsN9KOr6rBl4sWEQb0zsVgzaTdHqex4j0a03jtsbq8RplKJeY5SbJOUv-o8EC6gjMbJzCa5ik")
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                 mMessage = response.body().string();
                Log.e("TAG", mMessage);
                data=mMessage;
            }
        });
        return mMessage;
    }

}


