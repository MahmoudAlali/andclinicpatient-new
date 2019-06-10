package com.dcoret.beautyclient.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.ReservationDialog;
import com.dcoret.beautyclient.Adapters.OffersAdapter;
import com.dcoret.beautyclient.Adapters.OffersAdapterNew;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapterNew;
import com.dcoret.beautyclient.DataClass.BrowseServiceItem;
import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.DataClass.ServiceItem;
import com.dcoret.beautyclient.DataExample.OffersData;
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
    OffersAdapterNew servicesAdapter;
    ProgressDialog pd;
    String mMessage;
    String[] items={"Offer 1","Offer 2","Offer 3","Offer 4","Offer 5","Offer 6"};
    public static  String name="offers";
    BottomNavigationView navigation;
    TextView offer_text;
ArrayList<DataService> arr=new ArrayList<>();


    DataService[] services=new DataService[]{
      new DataService(1,"service1",30,4.5,false,true),
      new DataService(1,"service2",40,4.5,false,true),
      new DataService(1,"service3",20,4.5,false,true),
      new DataService(1,"service4",50,4.5,false,true),
      new DataService(1,"service5",50,4.5,false,true),

    };
    DataService[] services1=new DataService[]{
            new DataService(2,"خدمة1",30,4.5,false,true),
            new DataService(2,"خدمة2",40,4.5,false,true),
            new DataService(2,"خدمة3",20,4.5,false,true),
            new DataService(2,"خدمة4",50,4.5,false,true),
            new DataService(2,"خدمة5",50,4.5,false,true),

    };
    DataService[] service2=new DataService[]{
            new DataService(3,"serv1",30,4.5,false,true),
            new DataService(3,"serv2",40,4.5,false,true),
            new DataService(3,"serv3",20,4.5,false,true),
            new DataService(3,"serv4",50,4.5,false,true),
            new DataService(3,"serv5",50,4.5,false,true),

    };
    DataService[] service4=new DataService[]{
            new DataService(4,"serv1",40,4.5,false,true),
            new DataService(4,"serv2",40,4.5,false,true),
            new DataService(4,"serv3",20,4.5,false,true),
            new DataService(4,"serv4",50,4.5,false,true),
            new DataService(4,"serv5",50,4.5,false,true),

    };
    DataService[] service5=new DataService[]{
            new DataService(5,"serv1",50,4.5,false,true),
            new DataService(5,"serv2",40,4.5,false,true),
            new DataService(5,"serv3",20,4.5,false,true),
            new DataService(5,"serv4",50,4.5,false,true),
            new DataService(5,"serv5",50,4.5,false,true),

    };

    ArrayList<DataOffer> offers= new ArrayList<>();

//            new DataOffer[]{
//      new DataOffer("offer2",services1,150,false),
//      new DataOffer("offer3",service2,150,false),
//      new DataOffer("offer4",service4,150,false),
//      new DataOffer("offer5",service5,150,false),
//    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.offers_layout);
        setTitle("ابرز العروض");
        new ReservationDialog("offers");
        arr.add(services1[0]);
        arr.add(services1[1]);
        arr.add(services1[2]);

//        context = this;
//
//        offer_text = findViewById(R.id.offer_text);
//        navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setSelectedItemId(R.id.beauty);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//        recyclerView = findViewById(R.id.offers_recycleview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(new OffersAdapterNew(getApplicationContext(), items ));









        pd=new ProgressDialog(this);
        pd.setMessage("جار التحقق....");
        pd.show();
//        view= inflater.inflate(R.layout.tab_one,container,false);

        recyclerView=findViewById(R.id.offers_recycleview);
        recyclerView.setHasFixedSize(true);
        arrayList=new ArrayList<>();
        servicesAdapter=new OffersAdapterNew(this,arrayList);
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

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.beauty:
                            offer_text.setText("ابرز عروض التجميل");
//                            navigation.setItemIconTintList(ColorStateList.valueOf(Color.parseColor("#66BBF3")));
//                            navigation.setItemTextColor(ColorStateList.valueOf(Color.parseColor("#66BBF3")));
                            recyclerView = findViewById(R.id.offers_recycleview);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(new OffersAdapterNew(getApplicationContext(), items ));
                            return true;
                        case R.id.fashion:
                            offer_text.setText("ابرز عروض الازياء");
                            recyclerView =findViewById(R.id.offers_recycleview);
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(new ServicesAdapter(getApplicationContext(), items));
                            return true;
                    }

                    return false;
                }
            };




    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    static int section;

    public void servicesBeauty(View view) {
        Intent in=new Intent(this,BeautyMainPage.class);
        startActivity(in);
        finish();

    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menu3, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }else if(id==R.id.shoppingcart){
            Intent intent=new Intent(this,ShoppingCart.class);
            startActivity(intent);

        }else if(id==R.id.notify){
            Intent intent=new Intent(this,Notification.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

}
