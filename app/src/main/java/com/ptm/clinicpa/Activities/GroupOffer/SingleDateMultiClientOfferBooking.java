package com.ptm.clinicpa.Activities.GroupOffer;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.OfferBookingResult;
import com.ptm.clinicpa.Adapters.OfferBookingMultiClientsAdapter;
import com.ptm.clinicpa.Adapters.OffersAdapter;
import com.ptm.clinicpa.Adapters.OffersAdapterTab;
import com.ptm.clinicpa.DataModel.DataOffer;
import com.ptm.clinicpa.DataModel.OfferClientsModel;
import com.ptm.clinicpa.Fragments.OffersForRequest;
import com.ptm.clinicpa.Fragments.PlaceServiceFragment;
import com.ptm.clinicpa.Activities.TabTwo;
import com.ptm.clinicpa.Fragments.freeBookingFragment;
import com.ptm.clinicpa.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SingleDateMultiClientOfferBooking extends AppCompatActivity {


    static LinearLayout show_clients;
    public static HintArrayAdapter adapter, adapter1, adapter2;
    public static ArrayList sname = new ArrayList();
    public static ArrayList sname1 = new ArrayList();
    RecyclerView recyclerView;
    public static OfferBookingMultiClientsAdapter offerAdapter;
    static TextView add_date;
    Context context;
    Button next;
    public static String place="";
    public static String end_date;
    public static String bdb_pack_id;
    String is_effects_on;
    String notification2="";
    public static String place_num="",price_num="";

    String maxPrice="0",minPrice="10000";
    public  static int booking_period;
    public static ArrayList<OfferClientsModel> offerClientsModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_date_multi_client_offer_booking);
        recyclerView = findViewById(R.id.recycleview);
        add_date = findViewById(R.id.add_date);
        context = this;
        offerClientsModels.clear();


        APICall.OFFER_CLASS_NAME="MultiClientOfferEffect";

        if (OfferBookingMultiClientsAdapter.selectDateOfferModels.size()>0){
            OfferBookingMultiClientsAdapter.selectDateOfferModels.clear();
        }




        final int postion = getIntent().getIntExtra("postion", 0);

        Boolean check=true;


     try {
         if(BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
         {
             end_date = OffersForRequest.arrayList.get(postion).getBdb_offer_end();
             bdb_pack_id = OffersForRequest.arrayList.get(postion).getBdb_pack_code();
             is_effects_on = OffersForRequest.arrayList.get(postion).getBdb_is_effects_on();
         }else if (BeautyMainPage.FRAGMENT_NAME.equals("Offers")){
             end_date = OffersAdapter.bestOItem.getEnd_date();
             bdb_pack_id = OffersAdapter.bestOItem.getPack_code();


             ArrayList<DataOffer.SupIdClass> supIdClasses=new ArrayList<>();
             for (int i=0;i<OffersAdapter.bestOItem.getSersup_ids().length();i++){
                 try {
                     JSONObject object=OffersAdapter.bestOItem.getSersup_ids().getJSONObject(i);
                     String bdb_ser_sup_id=object.getString("bdb_ser_sup_id");
                     String bdb_name=object.getString("bdb_name");
                     String bdb_name_ar=object.getString("bdb_name_ar");
                     String bdb_ser_id=object.getString("bdb_ser_id");
                     if (APICall.ln.equals("ar")){
                         bdb_name=bdb_name_ar;
                     }
                     supIdClasses.add(new DataOffer.SupIdClass(bdb_ser_sup_id,bdb_name,bdb_ser_id));
                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }

             TabTwo.arrayList.clear();
             TabTwo.arrayList.add(new DataOffer(bdb_pack_id,"","","","","","","","","","","","","","","","","","","",supIdClasses));
             booking_period =Integer.parseInt(OffersAdapter.bestOItem.getBdb_booking_period());
             is_effects_on=APICall.bdb_is_effects_on;
         }
         else {
             check=false;
             try {
                 add_date.setText(APICall.DATE_FOR_SER_OFR);
                 end_date=TabTwo.arrayList.get(postion).getBdb_offer_end();
                 bdb_pack_id = TabTwo.arrayList.get(postion).getBdb_pack_code();
                 is_effects_on = TabTwo.arrayList.get(postion).getBdb_is_effects_on();
                 minPrice=PlaceServiceFragment.minprice;
                 maxPrice=PlaceServiceFragment.maxprice;
             }
             catch (Exception e)
             {
                 Log.e("eRR",e.getMessage());
             }

         }
     }
     catch (Exception e)
     {
         Log.e("ERROR",e.getMessage());
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
                Log.e("MMMMMMMMMM",notification);
                notification2=notification;
                bdb_pack_id = getIntent().getStringExtra("bdb_pack_id");
                is_effects_on = getIntent().getStringExtra("is_effects_on");
                end_date = getIntent().getStringExtra("offer_end");
                booking_period =Integer.parseInt(getIntent().getStringExtra("booking_period"));
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
        if (check) {

            SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            Calendar calendar=Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH,booking_period);
            Date bpriod=calendar.getTime();
            Date endDate=null;
            try {
                endDate= format.parse(end_date);
            }catch (Exception e){
                e.printStackTrace();
            }


            String bdb_offer_end="";
            if (endDate.compareTo(bpriod)==1){
                bdb_offer_end=calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DAY_OF_MONTH);
            }else {
                Calendar c=Calendar.getInstance();
                c.setTime(endDate);
                bdb_offer_end=c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1)+"-"+c.get(Calendar.DAY_OF_MONTH);

            }


            Log.e("Bpriod","is"+bpriod);
            Log.e("endDate","is"+end_date);
            Log.e("endDate","is"+bdb_offer_end);
            Log.e("endDate.compareTo","is"+endDate.compareTo(bpriod));


            final String finalBdb_offer_end = bdb_offer_end;
            add_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.select_date);

                    TextView confirm = dialog.findViewById(R.id.confirm);
                    TextView cancel = dialog.findViewById(R.id.cancel);
                    final DatePicker datePicker = dialog.findViewById(R.id.date_picker);
                    datePicker.setCalendarViewShown(false);
                    datePicker.setMinDate(System.currentTimeMillis());
                    Calendar calendar=Calendar.getInstance();
                    calendar.add(Calendar.DAY_OF_MONTH,booking_period);
                    datePicker.setMaxDate(calendar.getTimeInMillis());

                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    //                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                        Date date = sdf.parse(finalBdb_offer_end);
                        if (date.getTime()>datePicker.getMinDate())
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

                    datePicker.setCalendarViewShown(false);

                    dialog.show();
                }
            });
        }


        APICall.browseOneOffer(bdb_pack_id, context);

        if(BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
        {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    is_effects_on=APICall.bdb_is_effects_on;
                    String bdb_pack_code = bdb_pack_id;
                    if (add_date.getText().toString().equals(getResources().getString(R.string.select_date))) {
                        APICall.showSweetDialog(context, getResources().getString(R.string.alert),getResources().getString(R.string.Please_enter_Date) );
                    } else {

                        JSONArray clients =new JSONArray();


                        for (int i = 0; i < offerClientsModels.size(); i++) {
                            JSONObject clientJ = new JSONObject();

                            if (OfferBookingMultiClientsAdapter.selectDateOfferModels.get(i).getCname().getText().toString().length() == 0  ) {
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
                                        APICall.addBookingRequest(freeBookingFragment.lat + "", freeBookingFragment.lng + "", "", freeBookingFragment.Place, bdb_pack_code, "25", clients, context);
                                    }

                                }
                                catch (Exception e)
                                { Log.e("ERR",e.getMessage());}


//                        View view = recyclerView.getLayoutManager().findViewByPosition(1);
//                            String bdb_ser_sup_id = API.dofs.get(i).getSersup_ids().get(i).getBdb_ser_sup_id();
//                        String ser_time=API.dofs.get(postion).getSersup_ids().get(i).getBdb_ser_sup_id();



                        }

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


                    int place1;
                    Log.e("OffersAdapterTab.place",OffersAdapterTab.placePos+"is");
                    Log.e("placeFromOCM",offerClientsModels.get(0).getBdb_offer_place()+"is");
                    if (OffersAdapterTab.placePos==-1) {
                       place1=Integer.parseInt(offerClientsModels.get(0).getBdb_offer_place());
                    }else {
                        place1=OffersAdapterTab.placePos-1;
                    }
                    try {
                        switch (place1) {
                            case 0:
                                place_num = "9";
                                price_num = "32";
                                break;
                            case 1:
                                place_num = "8";
                                price_num = "1";
                                break;
                            case 2:
                                place_num = "10";
                                price_num = "30";
                                break;
                            case 3:
                                place_num = "11";
                                price_num = "31";
                                break;

                        }
                    }catch (Exception e){
                     e.printStackTrace();
                    }
                    String prices="";
                    if (PlaceServiceFragment.maxprice.equals("") || PlaceServiceFragment.maxprice.equals("")){
                        prices= " {\n" +
                                "            \"num\": "+price_num+",\n" +
                                "            \"value1\": 0,\n" +
                                "            \"value2\": 10000\n" +
                                "        },\n" ;
                    }else {
                        prices="{\n" +
                                "            \"num\": "+price_num+",\n" +
                                "            \"value1\": "+PlaceServiceFragment.minprice+",\n" +
                                "            \"value2\": "+PlaceServiceFragment.maxprice+"\n" +
                                "        },\n";
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
                                "        " +
                                "" +prices+
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
                            intent.putExtra("notification",notification2);
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


