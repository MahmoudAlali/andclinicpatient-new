package com.dcoret.beautyclient.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.OffersAdapter;
import com.dcoret.beautyclient.DataClass.ServiceItem;
import com.dcoret.beautyclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class Offers extends AppCompatActivity {
    public static Context context;
    RecyclerView recyclerView;
    static   ArrayList<ServiceItem> arrayList;
    OffersAdapter servicesAdapter;
    ProgressDialog pd;
    String mMessage;
    String[] items={"Offer 1","Offer 2","Offer 3","Offer 4","Offer 5","Offer 6"};
    public static  String name="offers";



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_layout);
        pd=new ProgressDialog(this);
//        pd.setMessage("جار التحقق....");
        pd.show();
//        view= inflater.inflate(R.layout.tab_one,container,false);

        recyclerView=findViewById(R.id.offers_recycleview);
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        servicesAdapter=new OffersAdapter(this,arrayList);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(servicesAdapter);
//        String data=APICall.okHttpServiceBrowse(BeautyMainPage.context,"1");



        MediaType MEDIA_TYPE = MediaType.parse("application/json");
        String url = "http://clientapp.dcoret.com/api/service/Service";
        OkHttpClient client = new OkHttpClient();
        JSONObject postdata = new JSONObject();
        try {
            postdata.put("bdb_type", "1");
//            postdata.put("password", "12345");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .post(body)
                .header("Authorization", "Bearer "+ APICall.gettoken(Offers.this))
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

                try {
                    JSONObject j = new JSONObject(mMessage);
                    JSONArray dataArry = j.getJSONArray("data");
                    for (int i = 0; i < dataArry.length(); i++) {
                        JSONObject jsonObject = dataArry.getJSONObject(i);
                        String   id=jsonObject.getString("id"),
                                name=jsonObject.getString("name"),
                                description=jsonObject.getString("description"),
                                type=jsonObject.getString("type"),
                                image=jsonObject.getString("image");

                        ServiceItem si = new ServiceItem(id,name,description,type,image);
                        arrayList.add(si);
                    }
                    pd.dismiss();

                    runOnUiThread(new Runnable() {
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












    }

    public void servicesBeauty(View view) {
        Intent in=new Intent(this,BeautyMainPage.class);
        startActivity(in);
        finish();

    }


}
