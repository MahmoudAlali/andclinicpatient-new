package com.dcoret.beautyclient.Activities;



import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.DataClass.BrowseServiceItem;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.DataClass.Location_Beauty;
import com.dcoret.beautyclient.Fragments.ServicesTabsFragment;
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

public class TabOne extends Fragment {

   public static RecyclerView recyclerView;
 public static   ArrayList<BrowseServiceItem>  arrayList=new ArrayList<>();;
   static  ServicesAdapter servicesAdapter;
    static ProgressDialog pd;


    static   String[] items={"Service1","Service2","Service3","Service4","Service5","Service6","Service7","Service8","Service9","Service10"};
    static String data= null;

    static View view;
    ImageButton filter;
    public static SwipeRefreshLayout pullToRefresh;
    public static int pagenum=1;
    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.tab_one,container,false);
        filter =view.findViewById(R.id.filter);
         pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                browseService(true); // your code
                if (ServicesTabsFragment.cityId==0){

                }else {
                    APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "4", pagenum+"", BeautyMainPage.context);
                }
            }
        });


        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        servicesAdapter=new ServicesAdapter(BeautyMainPage.context,  TabOne.arrayList,R.layout.service_layout_adapter_last);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(servicesAdapter);
        if (arrayList.size()==0 ) {
           if (ServicesTabsFragment.cityId!=0){
//               APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "8", pagenum+"", BeautyMainPage.context);

           }else {

           }
        }else {
//            APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
//            APICall.automatedBrowse("")
//            arrayList.clear();
        }

        return view;
        }


    //---------- not used-----------------
    static String mMessage="";
//     public static void browseService(final boolean isRefresh){
//         arrayList.clear();
//        if (!isRefresh){
//            pd=new ProgressDialog(BeautyMainPage.context);
//            pd.setMessage("جار التحقق....");
//            pd.show();
//        }
//        pullToRefresh.setRefreshing(isRefresh);
//        MediaType MEDIA_TYPE = MediaType.parse("application/json");
//        String url = "http://clientapp.dcoret.com/api/service/browseService";
//        OkHttpClient client = new OkHttpClient();
//        JSONObject postdata = new JSONObject();
//        try {
//            postdata.put("bdb_ser_id", "3");
//        } catch (JSONException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        RequestBody body = RequestBody.create(MEDIA_TYPE, postdata.toString());
//
//        okhttp3.Request request = new okhttp3.Request.Builder()
//                .url(url)
//                .post(body)
//                .header("Authorization", "Bearer "+ APICall.gettoken(BeautyMainPage.context))
//                .header("Content-Type", "application/json")
//                .build();
//
//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                String mMessage = e.getMessage().toString();
////                    Log.w("failure Response", mMessage);
//                Log.e("failure Response", mMessage);
//                try {
//                    pd.dismiss();
//                    pullToRefresh.setRefreshing(false);
//                }catch (Exception ep){
//
//                }
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
//                                    final Dialog refreshDialog = new Dialog(BeautyMainPage.context);
//                                    refreshDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//                                    refreshDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                                    refreshDialog.setContentView(R.layout.refresh_btn_dialog);
//                                    Button refresh=refreshDialog.findViewById(R.id.refresh);
//                                    refresh.setOnClickListener(new View.OnClickListener() {
//                                        @Override
//                                        public void onClick(View v) {
//                                            refreshDialog.cancel();
//                                            browseService(true);
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
//
//                }
//
//            }
//
//            @Override
//            public void onResponse(Call call, okhttp3.Response response) throws IOException {
//                mMessage = response.body().string();
//                Log.e("TAG", mMessage);
//                data = mMessage;
//                try {
//                    JSONObject j = new JSONObject(mMessage);
//                    JSONArray dataArry = j.getJSONArray("data");
//
//                    for (int i = 0; i < dataArry.length(); i++) {
//                        JSONObject jsonObject = dataArry.getJSONObject(i);
//                        String bdb_ser_sup_id = jsonObject.getString("bdb_ser_sup_id"),
//                                bdb_ser_salon = jsonObject.getString("bdb_ser_salon"),
//                                bdb_ser_home = jsonObject.getString("bdb_ser_home"),
//                                bdb_ser_hall = jsonObject.getString("bdb_ser_hall"),
//                                bdb_ser_salon_price = jsonObject.getString("bdb_ser_salon_price"),
//                                bdb_ser_home_price = jsonObject.getString("bdb_ser_home_price"),
//                                bdb_ser_hall_price = jsonObject.getString("bdb_ser_hall_price"),
//                                bdb_time = jsonObject.getString("bdb_time"),
//                                bdb_max_clients = jsonObject.getString("bdb_max_clients"),
//                                bdb_ser_id = jsonObject.getString("bdb_ser_id"),
//                                bdb_sup_id = jsonObject.getString("bdb_sup_id"),
//                                bdb_is_offer = jsonObject.getString("bdb_is_offer"),
//                                bdb_offer_status = jsonObject.getString("bdb_offer_status"),
//                                bdb_offer_start = jsonObject.getString("bdb_offer_start"),
//                                bdb_offer_end = jsonObject.getString("bdb_offer_end"),
//                                bdb_pack_code = jsonObject.getString("bdb_pack_code"),
//                                bdb_is_best = jsonObject.getString("bdb_is_best"),
//                                bdb_orgin_id = jsonObject.getString("bdb_orgin_id"),
//                                bdb_is_current_price = jsonObject.getString("bdb_is_current_price"),
//                                bdb_user_offer_num = jsonObject.getString("bdb_user_offer_num"),
//                                bdb_hotel = jsonObject.getString("bdb_hotel"),
//                                bdb_hotel_price = jsonObject.getString("bdb_hotel_price");
//                        BrowseServiceItem bsi = new BrowseServiceItem(bdb_ser_sup_id, bdb_ser_salon, bdb_ser_home, bdb_ser_hall, bdb_ser_salon_price, bdb_ser_home_price, bdb_ser_hall_price, bdb_time, bdb_max_clients, bdb_ser_id, bdb_sup_id, bdb_is_offer, bdb_offer_status, bdb_offer_start, bdb_offer_end, bdb_pack_code, bdb_is_best, bdb_orgin_id, bdb_is_current_price,  bdb_user_offer_num, bdb_hotel, bdb_hotel_price);
//                        arrayList.add(bsi);
//                    }
//                    try {
//                        pd.dismiss();
//                        pullToRefresh.setRefreshing(false);
//                        if (isRefresh){
//                            ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(BeautyMainPage.context,"تم تحديث قائمة الخدمات",Toast.LENGTH_LONG).show();
//
//                                }
//                            });
//                        }
//                    }catch (Exception ep){
//                        ep.printStackTrace();
//                    }
//
//                    ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            servicesAdapter.notifyDataSetChanged();
//                        }
//                    });
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//    }


    public static void refreshRV(){
//        arrayList.add(null);
        servicesAdapter.notifyDataSetChanged();

    }


}


