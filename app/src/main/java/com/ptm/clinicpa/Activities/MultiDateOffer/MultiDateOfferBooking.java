package com.ptm.clinicpa.Activities.MultiDateOffer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.OfferBookingResult;
import com.ptm.clinicpa.Adapters.OffersAdapter;
import com.ptm.clinicpa.Adapters.OffersAdapterTab;
import com.ptm.clinicpa.Adapters.SelectDateOfferAdapter;
import com.ptm.clinicpa.DataModel.DataOffer;
import com.ptm.clinicpa.DataModel.IDNameService;
import com.ptm.clinicpa.DataModel.OfferClientsModel;
import com.ptm.clinicpa.Fragments.OffersForRequest;
import com.ptm.clinicpa.Fragments.PlaceServiceFragment;
import com.ptm.clinicpa.Activities.TabTwo;
import com.ptm.clinicpa.Fragments.freeBookingFragment;
import com.ptm.clinicpa.R;
import com.ptm.clinicpa.Service.NotificationsBeauty;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MultiDateOfferBooking extends AppCompatActivity {

    public static ArrayList<String> strings=new ArrayList<>();
    public static ArrayList<OfferClientsModel.ServiceDetails> serviceDetails=new ArrayList<>();
    public static ArrayList<OfferClientsModel> offerClientsModels=new ArrayList<>();
    RecyclerView recyclerView;
    public static SelectDateOfferAdapter selectDateOfferAdapter;
    Context context;
    Button next;
    EditText phone_number,client_name;
    public static String place_num="",price_num="";
    static String place="";
    static ArrayList<DataOffer.SupIdClass> supIdClasses;
    public static String bdb_pack_id;
    String is_effects_on,end_date;
    public  static String bdb_offer_end="";
    String notification2 = "";


    int booking_period;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_date_offer_booking);
        final int postion=getIntent().getIntExtra("postion",0);
        final String offertype=getIntent().getStringExtra("offertype");
        context=this;
        serviceDetails.clear();
        selectDateOfferAdapter.dates.clear();
        APICall.OFFER_CLASS_NAME="MultiDateOfferEffect";

//        int postion=getIntent().getIntExtra("postion",0);
//        client_name=findViewById(R.id.client_name);
//        phone_number=findViewById(R.id.phone_number);
        next=findViewById(R.id.next);

        boolean check=true;

        try {
            if (BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment")) {

                bdb_pack_id = OffersForRequest.arrayList.get(postion).getBdb_pack_code();
                is_effects_on = OffersForRequest.arrayList.get(postion).getBdb_is_effects_on();
                place = OffersForRequest.arrayList.get(postion).getBdb_offer_place();
                supIdClasses = OffersForRequest.arrayList.get(postion).getSersup_ids();
            } else if (BeautyMainPage.FRAGMENT_NAME.equals("SERVICETABFRAGMENT")) {
                Log.e("SERVICETABFRAGMENT", "SERVICETABFRAGMENT1");

                try {
                    bdb_pack_id = TabTwo.arrayList.get(postion).getBdb_pack_code();
                    is_effects_on = TabTwo.arrayList.get(postion).getBdb_is_effects_on();
                    place = TabTwo.arrayList.get(postion).getBdb_offer_place();
                    supIdClasses = TabTwo.arrayList.get(postion).getSersup_ids();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("eRR", e.getMessage());
                }

            } else if (BeautyMainPage.FRAGMENT_NAME.equals("MainProviderActivity")) {
                check = false;
                int postion1 = getIntent().getIntExtra("postion", 0);
                Log.e("TabTwo.arrayList.", TabTwo.arrayList.size() + "is");
                Log.e("TabTwo.arrayList.", postion1 + "is");
                Log.e("bdb_pack_id123", TabTwo.arrayList.get(postion1).getBdb_pack_code() + "is");
                bdb_pack_id = TabTwo.arrayList.get(postion1).getBdb_pack_code();
                APICall.PERIOD_FOR_SER_OFR = Integer.parseInt(TabTwo.arrayList.get(postion1).getBdb_booking_period());
                end_date = TabTwo.arrayList.get(postion1).getBdb_offer_end();
                is_effects_on = TabTwo.arrayList.get(postion1).getBdb_is_effects_on();
                supIdClasses = TabTwo.arrayList.get(postion).getSersup_ids();
                place = TabTwo.arrayList.get(postion).getBdb_offer_place();
            } else if (BeautyMainPage.FRAGMENT_NAME.equals("Offers")) {
//            end_date = OffersAdapter.bestOItem.getEnd_date();
                bdb_pack_id = OffersAdapter.bestOItem.getPack_code();
                booking_period = Integer.parseInt(OffersAdapter.bestOItem.getBdb_booking_period());
                ArrayList<DataOffer.SupIdClass> supIdClasses = new ArrayList<>();
                for (int i = 0; i < OffersAdapter.bestOItem.getSersup_ids().length(); i++) {
                    try {
                        JSONObject object = OffersAdapter.bestOItem.getSersup_ids().getJSONObject(i);
                        String bdb_ser_sup_id = object.getString("bdb_ser_sup_id");
                        String bdb_name = object.getString("bdb_name");
                        String bdb_name_ar = object.getString("bdb_name_ar");
                        String bdb_ser_id = object.getString("bdb_ser_id");
                        if (APICall.ln.equals("ar")) {
                            bdb_name = bdb_name_ar;
                        }
                        supIdClasses.add(new DataOffer.SupIdClass(bdb_ser_sup_id, bdb_name, bdb_ser_id));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                TabTwo.arrayList.clear();
                TabTwo.arrayList.add(new DataOffer(bdb_pack_id, "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", supIdClasses));

                is_effects_on = APICall.bdb_is_effects_on;
            }
        }
        catch (Exception e)
        {
            Log.e("ERROR",e.getMessage());
        }

        //region CHECK_NOTIFICATION
        String notification = "";
        try {
            notification=getIntent().getStringExtra("notification");
            notification2=notification;

        }
        catch (Exception e){}
        try {
            if (!notification.equals("")) {
                Log.e("MMMMMMMMMM",notification);
                notification2=notification;
                bdb_pack_id = getIntent().getStringExtra("bdb_pack_id");
                is_effects_on = getIntent().getStringExtra("is_effects_on");
                place = NotificationsBeauty.offer_place;
                supIdClasses = NotificationsBeauty.supIdClasses;
                booking_period =Integer.parseInt(getIntent().getStringExtra("booking_period"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //endregion







//        for (int i = 0; i< TabTwo.arrayList.get(postion).getSersup_ids().size(); i++){
//            if (APICall.ln.equals("en")) {
//                strings.add(TabTwo.arrayList.get(postion).getSersup_ids().get(i).getBdb_name());
//            }else {
//                strings.add(TabTwo.arrayList.get(postion).getSersup_ids().get(i).getBdb_name_ar());
//            }
//        }
//        place=TabTwo.arrayList.get(postion).getBdb_offer_place();
        recyclerView=findViewById(R.id.recycleview);

        selectDateOfferAdapter=new SelectDateOfferAdapter(context,serviceDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(selectDateOfferAdapter);
        APICall.browseOneMultiOffer(bdb_pack_id, context);

        APICall.detailsUser3(context);


        if(BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
        {
            next.setText(R.string.createRequest);

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int place1;
                    if (OffersAdapterTab.placePos==-1) {
                        place1=Integer.parseInt(offerClientsModels.get(0).getBdb_offer_place());
                    }else {
                        place1=OffersAdapterTab.placePos-1;
                    }


                    if(BeautyMainPage.FRAGMENT_NAME.equals("freeBookingFragment"))
                    {
                        switch (freeBookingFragment.placeSpinner.getSelectedItemPosition()){
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

                    }
                    else
                    {
                        try {
                            switch (place1){
                                case 0:
                                    place_num="9";
                                    price_num="32";
                                    break;
                                case 1:
                                    place_num="8";
                                    price_num="1";
                                    break;
                                case 2:
                                    place_num="10";
                                    price_num="30";
                                    break;
                                case 3:
                                    place_num="11";
                                    price_num="31";
                                    break;

                            }
                        }
                        catch (Exception e)
                        {

                        }
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
                    SharedPreferences editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);

                    String name= BeautyMainPage.client_name;
                    String mobile=BeautyMainPage.client_number;
                    String bdb_pack_code = bdb_pack_id;

                    String postdata =
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
                                    " " +prices+
                                    "        {\n" +
                                    "            \"num\": "+place_num+",\n" +
                                    "            \"value1\": 1,\n" +
                                    "            \"value2\": 0\n" +
                                    "        }\n" +
                                    "    ],\n" +
                                    "\n" +
                                    "\t\"bdb_pack_code\":" + bdb_pack_code + ",\n" +
                                    "\t\"clients\": [  ";
//                    String cname = client_name.getText().toString();
//                    String phone = phone_number.getText().toString();


                    String services =
                            sortdates(selectDateOfferAdapter.dates,name,mobile);

                    postdata = postdata + services + "],\"offer_type\":" + offertype + "}";


                    Log.e("POSTDATA", postdata);



                    if (is_effects_on.equals("1")) {
                        Intent intent = new Intent(context, MultiDateOfferEffect.class);
                        intent.putExtra("filter", postdata);
                        intent.putExtra("offertype", offertype);
                        intent.putExtra("place", place);
                        intent.putExtra("bdb_pack_id",bdb_pack_id);
                        intent.putExtra("notification","true");
                        intent.putExtra("position", postion);
                        startActivity(intent);
                    }else {

                     //   APICall.addBookingRequest(freeBookingFragment.lat+"",freeBookingFragment.lng+"", "",freeBookingFragment.Place,bdb_pack_code,"24",getClients(1),BeautyMainPage.context);

                       /* Intent intent = new Intent(context, OfferBookingResult.class);
                        intent.putExtra("filter", postdata);
                        intent.putExtra("offertype", offertype);
                        intent.putExtra("place", place);
                        startActivity(intent);*/
                    }


                }
//                if ()

            });
        }
        else
        {
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    is_effects_on=APICall.bdb_is_effects_on;
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

                    SharedPreferences editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);

                    String name= BeautyMainPage.client_name;
                    String mobile=BeautyMainPage.client_number;
                    String bdb_pack_code = bdb_pack_id;

                    String postdata =
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
                                    "        \n" +prices+
                                    "        {\n" +
                                    "            \"num\": "+place_num+",\n" +
                                    "            \"value1\": 1,\n" +
                                    "            \"value2\": 0\n" +
                                    "        }\n" +
                                    "    ],\n" +
                                    "\n" +
                                    "\t\"bdb_pack_code\":" + bdb_pack_code + ",\n" +
                                    "\t\"clients\": [  ";
//                    String cname = client_name.getText().toString();
//                    String phone = phone_number.getText().toString();


                    String services = sortdates(selectDateOfferAdapter.dates,name,mobile);

                    postdata = postdata + services + "],\"offer_type\":" + offertype + "}";


                    Log.e("POSTDATA", postdata);



                    if (is_effects_on.equals("1")) {
                        Intent intent = new Intent(context, MultiDateOfferEffect.class);
                        intent.putExtra("filter", postdata);
                        intent.putExtra("offertype", offertype);
                        intent.putExtra("place", place);
                        intent.putExtra("bdb_pack_id",bdb_pack_id);
                        intent.putExtra("notification",notification2);
                        intent.putExtra("position", postion);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(context, OfferBookingResult.class);
                        intent.putExtra("filter", postdata);
                        intent.putExtra("offertype", offertype);
                        intent.putExtra("place", place);
                        startActivity(intent);
                    }


                }
//                if ()

            });
        }





    }

    static String sortdates(ArrayList<TextView> dates,String cname,String phone){
        ArrayList<ArrayList<IDNameService>> arrayList=new ArrayList<>();
        ArrayList<Integer> index=new ArrayList<>();

        for (int i=0;i<dates.size();i++){
            String ser_sup_id=supIdClasses.get(i).getBdb_ser_sup_id();

            if (i==0){
                ArrayList<IDNameService> list=new ArrayList();
                list.add(new IDNameService(ser_sup_id,dates.get(i).getText().toString()));
                arrayList.add(list);
                index.add(i);
            }else {
                Boolean check=false;
                for (int k=0;k<arrayList.size();k++){
                    for (int j=0;j<arrayList.get(k).size();j++) {
                        if (dates.get(i).getText().toString().equals(arrayList.get(k).get(j).getName())) {
//                            ArrayList list = new ArrayList();
                            arrayList.get(k).add(new IDNameService(ser_sup_id,dates.get(i).getText().toString()));
//                            .add(dates.get(i).getText().toString());
//                            arrayList.add(list);
                            index.add(i);
                            check = true;
                            break;
                        }

                    }
                }

                if (!check){
                    ArrayList<IDNameService> list=new ArrayList();
                    list.add(new IDNameService(ser_sup_id,dates.get(i).getText().toString()));
                    arrayList.add(list);
                    index.add(i);
                }


            }
        }




        String services="";






        for (int k=0;k<arrayList.size();k++) {
            String tmp="\t\t   {\"date\":\""+arrayList.get(k).get(0).getName()+"\",\n" +
                    "\t\t   \"client_name\": \""+cname+"\",\n" +
                    "\t\t   \"client_phone\": \""+phone+"\",\n" +
                    "\t\t   \"is_current_user\":1 ,\n" +
                    "\t\t   \"is_adult\":1 ,\n" +
                    "\t\t   \"services\": [\n" ;
            if (k==0) {
                services = services + tmp;
            }

//            else {
//                services=services+",[";
//            }
            else {
                services = services + ","+tmp;
            }
            for (int j = 0; j < arrayList.get(k).size(); j++) {

                Log.e("date"+k+""+j,arrayList.get(k).get(j).getName());


                if (j==0) {
                    services =services+ " \t\t\t{\n" +
                            "    \t\t\t\t\"bdb_ser_sup_id\": "+arrayList.get(k).get(j).getId()+",\n" +
                            "    \t\t\t\t\"ser_time\": 60\n" +
                            "    \t\t\t\t\n" +
                            "      }";
                }else {
                    services =services+ " ,\t\t\t{\n" +
                            "    \t\t\t\t\"bdb_ser_sup_id\": "+arrayList.get(k).get(j).getId()+",\n" +
                            "    \t\t\t\t\"ser_time\": 60\n" +
                            "    \t\t\t\t\n" +
                            "      }";
                }

//                if (j==arrayList.get(k).size()-1){
//                    services=services+"]";
//                }
            }

            services=services+"\n ],\"effect\":[]}";

        }
        Log.e("ServicesDate",services);
        return services;
    }
   /* static JSONArray sortdatesJson(ArrayList<TextView> dates, String cname, String phone){
        ArrayList<ArrayList<IDNameService>> arrayList=new ArrayList<>();
        ArrayList<Integer> index=new ArrayList<>();

        for (int i=0;i<dates.size();i++){
            String ser_sup_id=supIdClasses.get(i).getBdb_ser_sup_id();

            if (i==0){
                ArrayList<IDNameService> list=new ArrayList();
                list.add(new IDNameService(ser_sup_id,dates.get(i).getText().toString()));
                arrayList.add(list);
                index.add(i);
            }else {
                Boolean check=false;
                for (int k=0;k<arrayList.size();k++){
                    for (int j=0;j<arrayList.get(k).size();j++) {
                        if (dates.get(i).getText().toString().equals(arrayList.get(k).get(j).getName())) {
//                            ArrayList list = new ArrayList();
                            arrayList.get(k).add(new IDNameService(ser_sup_id,dates.get(i).getText().toString()));
//                            .add(dates.get(i).getText().toString());
//                            arrayList.add(list);
                            index.add(i);
                            check = true;
                            break;
                        }

                    }
                }

                if (!check){
                    ArrayList<IDNameService> list=new ArrayList();
                    list.add(new IDNameService(ser_sup_id,dates.get(i).getText().toString()));
                    arrayList.add(list);
                    index.add(i);
                }


            }
        }




        String services="";






        for (int k=0;k<arrayList.size();k++) {
            String tmp="\t\t   {\"date\":\""+arrayList.get(k).get(0).getName()+"\",\n" +
                    "\t\t   \"client_name\": \""+cname+"\",\n" +
                    "\t\t   \"client_phone\": \""+phone+"\",\n" +
                    "\t\t   \"is_current_user\":1 ,\n" +
                    "\t\t   \"is_adult\":1 ,\n" +
                    "\t\t   \"services\": [\n" ;
            if (k==0) {
                services = services + tmp;
            }

//            else {
//                services=services+",[";
//            }
            else {
                services = services + ","+tmp;
            }
            for (int j = 0; j < arrayList.get(k).size(); j++) {

                Log.e("date"+k+""+j,arrayList.get(k).get(j).getName());


                if (j==0) {
                    services =services+ " \t\t\t{\n" +
                            "    \t\t\t\t\"bdb_ser_sup_id\": "+arrayList.get(k).get(j).getId()+",\n" +
                            "    \t\t\t\t\"ser_time\": 60\n" +
                            "    \t\t\t\t\n" +
                            "      }";
                }else {
                    services =services+ " ,\t\t\t{\n" +
                            "    \t\t\t\t\"bdb_ser_sup_id\": "+arrayList.get(k).get(j).getId()+",\n" +
                            "    \t\t\t\t\"ser_time\": 60\n" +
                            "    \t\t\t\t\n" +
                            "      }";
                }

//                if (j==arrayList.get(k).size()-1){
//                    services=services+"]";
//                }
            }

            services=services+"\n ],\"effect\":[]}";

        }
        Log.e("ServicesDate",services);
        return services;
    }
    public static JSONArray getClients(int c)
    {
        JSONArray clients =new JSONArray();
        try {

            for (int i=0;i<CreateRequestActivity.clientsArrayList.size();i++) {

                JSONObject client = new JSONObject();

                client.put("client_name",CreateRequestActivity.clientsArrayList.get(i).getClientName().getText().toString());
                client.put("client_phone",CreateRequestActivity.clientsArrayList.get(i).getPhoneNumber().getText().toString());
                client.put("is_current_user","1");
                client.put("old",(CreateRequestActivity.clientsArrayList.get(i).getAgeRange().getSelectedItemPosition()-1));
                JSONArray services=new JSONArray() ;
                JSONObject effects=new JSONObject(effectsArr.get(i)) ;

                client.put("effect",effects);

                //  Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());

                for (int j = 0; j < CreateRequestActivity.clientsArrayList.get(i).getServicesModels().size(); j++) {
//                        Log.e("SIZE",""+GroupReservationFragment.clientsViewData.get(i).getServicesSelected().size());
                    JSONObject servic = new JSONObject();

                    servic.put("bdb_ser_sup_id",CreateRequestActivity.clientsArrayList.get(i).getServicesModels().get(j).getBdb_ser_id());
                    services.put(servic);
                }
                client.put("services",services);

                String eff="";
                Log.e("index-i"+i,"index-i"+i);
                try {
                    eff=effectsArr.get(i);
                }catch (Exception e){
                    e.printStackTrace();
                }
               *//* if (CreateRequestActivity.clientsArrayList.size()==1){

                    clients=clients+"],\"effect\":["+eff+"]}";
                }else if (i==CreateRequestActivity.clientsArrayList.size()-1){
                    clients=clients+"],\"effect\":["+eff+"]}";

                }else {
                    clients=clients+"],\"effect\":["+eff+"]},";

                }*//*

                clients.put(client);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        //clients=clients+"]";

        Log.e("clients",clients.toString());
        return clients;
    }*/
}
