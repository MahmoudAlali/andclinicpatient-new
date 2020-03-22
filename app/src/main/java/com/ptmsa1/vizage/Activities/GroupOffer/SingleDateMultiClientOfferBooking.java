package com.ptmsa1.vizage.Activities.GroupOffer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Activities.OfferBookingResult;
import com.ptmsa1.vizage.Adapters.OfferBookingMultiClientsAdapter;
import com.ptmsa1.vizage.DataModel.OfferClientsModel;
import com.ptmsa1.vizage.Fragments.OffersForRequest;
import com.ptmsa1.vizage.Fragments.PlaceServiceFragment;
import com.ptmsa1.vizage.Activities.TabTwo;
import com.ptmsa1.vizage.Fragments.freeBookingFragment;
import com.ptmsa1.vizage.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SingleDateMultiClientOfferBooking extends AppCompatActivity {


    static LinearLayout show_clients;
    public static ArrayAdapter<CharSequence> adapter, adapter1, adapter2;
    public static ArrayList sname = new ArrayList();
    public static ArrayList sname1 = new ArrayList();
    RecyclerView recyclerView;
    public static OfferBookingMultiClientsAdapter offerAdapter;
    static TextView add_date;
    Context context;
    Button next;
    public static String place="";
    public static String end_date;
    String bdb_pack_id;
    String is_effects_on;

    public static ArrayList<OfferClientsModel> offerClientsModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_date_multi_client_offer_booking);
        recyclerView = findViewById(R.id.recycleview);
        add_date = findViewById(R.id.add_date);
        context = this;
        offerClientsModels.clear();

        if (OfferBookingMultiClientsAdapter.selectDateOfferModels.size()>0){
            OfferBookingMultiClientsAdapter.selectDateOfferModels.clear();
        }

        final int postion = getIntent().getIntExtra("postion", 0);


        if(BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
        {
            end_date = OffersForRequest.arrayList.get(postion).getBdb_offer_end();
            bdb_pack_id = OffersForRequest.arrayList.get(postion).getBdb_pack_code();
            is_effects_on = OffersForRequest.arrayList.get(postion).getBdb_is_effects_on();
        }
        else {
            try {
                end_date=TabTwo.arrayList.get(postion).getBdb_offer_end();
                bdb_pack_id = TabTwo.arrayList.get(postion).getBdb_pack_code();
                is_effects_on = TabTwo.arrayList.get(postion).getBdb_is_effects_on();
            }
            catch (Exception e)
            {
                Log.e("eRR",e.getMessage());
            }

        }

        // String bdb_pack_id = TabTwo.arrayList.get(postion).getBdb_pack_code();

        //region CHECK_NOTIFICATION
        String notification = "";
        try {
            notification=getIntent().getStringExtra("notification");

        }
        catch (Exception e){
            notification="";
        }
        try {
            if (!notification.equals("")) {
                bdb_pack_id = getIntent().getStringExtra("bdb_pack_id");
                is_effects_on = getIntent().getStringExtra("is_effects_on");
                end_date = getIntent().getStringExtra("offer_end");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //endregion


        recyclerView = findViewById(R.id.recycleview);
        next = findViewById(R.id.next);
        offerAdapter = new OfferBookingMultiClientsAdapter(context, offerClientsModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(offerAdapter);
        add_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.select_date);
                TextView confirm = dialog.findViewById(R.id.confirm);
                TextView cancel = dialog.findViewById(R.id.cancel);
                final DatePicker datePicker = dialog.findViewById(R.id.date_picker);
                datePicker.setMinDate(System.currentTimeMillis());

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                    Date date = sdf.parse(end_date);

                    datePicker.setMaxDate(date.getTime());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        int month=datePicker.getMonth()+1;
                        add_date.setText(datePicker.getYear() + "-" + month+ "-" + datePicker.getDayOfMonth());
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        add_date.setText(R.string.select_date);
                    }
                });


                dialog.show();
            }
        });


        APICall.browseOneOffer(bdb_pack_id, context);

        if(BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
        {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String bdb_pack_code = bdb_pack_id;
                    if (add_date.getText().toString().equals(getResources().getString(R.string.select_date))) {
                        APICall.showSweetDialog(context, getResources().getString(R.string.alert),getResources().getString(R.string.Please_enter_Date) );
                    } else {

                        JSONArray clients =new JSONArray();


                        for (int i = 0; i < offerClientsModels.size(); i++) {
                            JSONObject clientJ = new JSONObject();

                            if (OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getCname().getText().toString().length() == 0) {
                                APICall.showSweetDialog(context, getResources().getString(R.string.alert), getResources().getString(R.string.enter_client_name));
                            } else if (OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getPhone_number().getText().toString().length() == 0) {
                                APICall.showSweetDialog(context, getResources().getString(R.string.alert),getResources().getString(R.string.enter_phone_num) );
                            } else {
                                String cname = OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getCname().getText().toString();
                                String phone = OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getPhone_number().getText().toString();
                                try {
                                    clientJ.put("client_name",cname);
                                    clientJ.put("client_phone",phone);
                                    clientJ.put("start_date",APICall.arabicToDecimal(add_date.getText().toString()));
                                    if(phone.equals(BeautyMainPage.client_number))
                                        clientJ.put("is_current_user","1");
                                    else
                                        clientJ.put("is_current_user","0");

                                    clientJ.put("old","1");

                                    JSONArray services=new JSONArray() ;
//                                    JSONObject effects=new JSONObject("") ;
//                                    clientJ.put("effect",effects);

                                    for (int j = 0; j < offerClientsModels.get(i).getServiceDetails().size(); j++) {
                                        JSONObject servic = new JSONObject();
                                        servic.put("bdb_ser_sup_id",offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id());
                                        services.put(servic);

                                    }
                                    clientJ.put("services",services);
                                    clients.put(clientJ);



                                }
                                catch (Exception e)
                                { Log.e("ERR",e.getMessage());}


//                        View view = recyclerView.getLayoutManager().findViewByPosition(1);
//                            String bdb_ser_sup_id = API.dofs.get(i).getSersup_ids().get(i).getBdb_ser_sup_id();
//                        String ser_time=API.dofs.get(postion).getSersup_ids().get(i).getBdb_ser_sup_id();


                            }
                        }
                        if (is_effects_on.equals("1")) {
                            Intent intent = new Intent(context, MultiClientOfferEffect.class);
                            intent.putExtra("filter", "");
                            intent.putExtra("place", place);
                            intent.putExtra("position", postion);
                            intent.putExtra("bdb_pack_id",bdb_pack_id);
                            intent.putExtra("notification","true");
                            intent.putExtra("offertype", getIntent().getStringExtra("offertype"));
                            startActivity(intent);
                        }else {
                            APICall.addBookingRequest(freeBookingFragment.lat+"",freeBookingFragment.lng+"", "",freeBookingFragment.Place,bdb_pack_code,"25",clients,context);

                            /*Intent intent = new Intent(context, OfferBookingResult.class);
                            intent.putExtra("filter", postdata);
                            intent.putExtra("place", place);
                            intent.putExtra("offertype", getIntent().getStringExtra("offertype"));
                            startActivity(intent);*/
                        }

                    }
                }
            });
        }
        else {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String place_num="",price_num="";

                    switch (PlaceServiceFragment.placeSpinner.getSelectedItemPosition()){
                        case 1:
                            place_num="9";
                            price_num="32";
                            break;
                        case 2:
                            place_num="8";
                            price_num="1";
                            break;
                        case 3:
                            place_num="10";
                            price_num="30";
                            break;
                        case 4:
                            place_num="11";
                            price_num="31";
                            break;

                    }

                    String bdb_pack_code = bdb_pack_id;
                    if (add_date.getText().toString().equals(getResources().getString(R.string.select_date))) {
                        APICall.showSweetDialog(context, getResources().getString(R.string.alert),getResources().getString(R.string.Please_enter_Date) );
                    } else {
                        String postdata = "\n" +
                                "{\n" +
                                "    \"Filter\": [\n" +
                                "        {\n" +
                                "            \"num\": 34,\n" +
                                "            \"value1\": "+ PlaceServiceFragment.lat+",\n" +
                                "            \"value2\": 0\n" +
                                "        },\n" +
                                "        {\n" +
                                "            \"num\": 35,\n" +
                                "            \"value1\": "+PlaceServiceFragment.lng+",\n" +
                                "            \"value2\": 0\n" +
                                "        },\n" +
                                "        {\n" +
                                "            \"num\": "+ price_num+",\n" +
                                "            \"value1\": "+PlaceServiceFragment.minprice+",\n" +
                                "            \"value2\": "+PlaceServiceFragment.maxprice+"\n" +
                                "        },\n" +
                                "        {\n" +
                                "            \"num\": "+place_num+",\n" +
                                "            \"value1\": 1,\n" +
                                "            \"value2\": 0\n" +
                                "        }\n" +
                                "    ],\n" +
                                "\"date\":\"" + add_date.getText().toString() + "\"," +
                                "\t\"bdb_pack_code\":" + bdb_pack_code + ",\n" +
                                "\t\"clients\": [ \n ";
//                offerClientsModels
                        Log.e("offerClientsModels", offerClientsModels.size() + "");
                        Log.e("selectDateOfferModels", OfferBookingMultiClientsAdapter.selectDateOfferModels.size() + "");
                        String client = "";

                        for (int i = 0; i < offerClientsModels.size(); i++) {
                            if (OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getCname().getText().toString().length() == 0) {
                                APICall.showSweetDialog(context, getResources().getString(R.string.alert), getResources().getString(R.string.enter_client_name));
                            } else if (OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getPhone_number().getText().toString().length() == 0) {
                                APICall.showSweetDialog(context, getResources().getString(R.string.alert),getResources().getString(R.string.enter_phone_num) );
                            } else {
                                String cname = OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getCname().getText().toString();
                                String phone = OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getPhone_number().getText().toString();
                                if (i == 0) {
                                    client = client + "{\"client_name\": \"" + cname + "\",\"client_phone\": \"" + phone + "\",\"is_adult\":1,\"date\":\""+APICall.arabicToDecimal(add_date.getText().toString())+"\",\"is_current_user\": 0,\"services\": [ \n";
                                    String services = "";
                                    for (int j = 0; j < offerClientsModels.get(i).getServiceDetails().size(); j++) {
                                        if (j == 0) {
                                            services = "{ \"bdb_ser_sup_id\": " + offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id() + ",  \"ser_time\": 60,  \"bdb_ext_pack_code\":0 }\n";
                                        } else {
                                            services += ",{ \"bdb_ser_sup_id\": " + offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id() + ",  \"ser_time\": 60,  \"bdb_ext_pack_code\":0 }\n";
                                        }
                                    }
                                    client = client + services + "],\"effect\":[]}";
                                    Log.e("selectDateOff.size()", OfferBookingMultiClientsAdapter.selectDateOfferModels.size() + "");
                                } else {
                                    client = client + ",{\"client_name\": \"" + cname + "\",\"client_phone\": \"" + phone + "\",\"is_adult\":1,\"date\":\""+APICall.arabicToDecimal(add_date.getText().toString())+"\",\"is_current_user\": 0,\"services\": [ \n";
                                    String services = "";
                                    for (int j = 0; j < offerClientsModels.get(i).getServiceDetails().size(); j++) {
                                        if (j == 0) {
                                            services = "{ \"bdb_ser_sup_id\": " + offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id() + ",  \"ser_time\": 60,  \"bdb_ext_pack_code\":0 }\n";
                                        } else {
                                            services += "\n,{ \"bdb_ser_sup_id\": " + offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id() + ",  \"ser_time\": 60,  \"bdb_ext_pack_code\":0 }\n";
                                        }
                                    }
                                    client = client + services + "],\"effect\":[]}";
                                    Log.e("selectDateOff.size()", OfferBookingMultiClientsAdapter.selectDateOfferModels.size() + "");
                                }
//                        View view = recyclerView.getLayoutManager().findViewByPosition(1);
//                            String bdb_ser_sup_id = API.dofs.get(i).getSersup_ids().get(i).getBdb_ser_sup_id();
//                        String ser_time=API.dofs.get(postion).getSersup_ids().get(i).getBdb_ser_sup_id();


                            }
                        }
                        postdata = postdata + client + "],\"offer_type\":" + getIntent().getStringExtra("offertype") + "\n" +
                                "\t\t   }\n" +
                                "\t\t";


                        Log.e("PostData", postdata);
                        if (is_effects_on.equals("1")) {
                            Intent intent = new Intent(context, MultiClientOfferEffect.class);
                            intent.putExtra("filter", postdata);
                            intent.putExtra("place", place);
                            intent.putExtra("position", postion);
                            intent.putExtra("bdb_pack_id",bdb_pack_id);
                            intent.putExtra("notification","true");
                            intent.putExtra("offertype", getIntent().getStringExtra("offertype"));
                            startActivity(intent);
                        }else {
                            Intent intent = new Intent(context, OfferBookingResult.class);
                            intent.putExtra("filter", postdata);
                            intent.putExtra("place", place);
                            intent.putExtra("offertype", getIntent().getStringExtra("offertype"));
                            startActivity(intent);
                        }

                    }
                }
            });
        }
    }
}


