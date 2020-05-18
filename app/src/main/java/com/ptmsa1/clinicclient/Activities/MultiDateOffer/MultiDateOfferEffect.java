package com.ptmsa1.clinicclient.Activities.MultiDateOffer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.Activities.OfferBookingResult;
import com.ptmsa1.clinicclient.Adapters.EffectAdapter;
import com.ptmsa1.clinicclient.DataModel.IDNameService;
import com.ptmsa1.clinicclient.Fragments.MyEffects.MyEffectsActivity;
import com.ptmsa1.clinicclient.Fragments.PlaceServiceFragment;
import com.ptmsa1.clinicclient.Activities.TabTwo;
import com.ptmsa1.clinicclient.Fragments.MultiIndividualBookingReservationFragment;
import com.ptmsa1.clinicclient.R;

import java.util.ArrayList;

public class MultiDateOfferEffect extends AppCompatActivity {

    RecyclerView recyclerView;
    EffectAdapter effectAdapter;
    Button update;

    Context context;

    public  static String postdata,offertype,place;
    public  static int postion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_effects);
        context=this;
        EffectAdapter.checkClick=false;

        Log.e("OFFER_CLASS_NAME2","is"+APICall.OFFER_CLASS_NAME);

//        select_cat=findViewById(R.id.select_cat);
        update=findViewById(R.id.update);
        recyclerView=findViewById(R.id.recycleview);
        effectAdapter=new EffectAdapter(BeautyMainPage.context, APICall.clientEffectModels,false);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(effectAdapter);

        postdata= getIntent().getStringExtra("filter");
        offertype=getIntent().getStringExtra("offertype");
        place=getIntent().getStringExtra("place");
        postion=getIntent().getIntExtra("position",0);


        String filter=
//                " \"multi_salon_client\": \""+"0"+"\",\n" +
//                "       \"multi_salon_clients_rel\": \""+"0"+"\",\n"  +
                "       { \"clients\": [\n" +
                "           {\n" +
                "            \"client_name\": \""+ BeautyMainPage.client_name+"\",\n" +
                "            \"client_phone\": \""+BeautyMainPage.client_number+"\",\n" +
//                "            \"rel\": \""+"0"+"\",\n" +
                "            \"multi_salon_client\": \""+BeautyMainPage.client_number+"\",\n" +
                "            \"is_current_user\": 1,\n" +
                "            \"services\": [\n" +
                "                \n" ;


        for (int i = 0; i< TabTwo.arrayList.get(postion).getSersup_ids().size(); i++){
            if (i==0){
                filter+="{\"ser_id\": "+ TabTwo.arrayList.get(postion).getSersup_ids().get(i).getBdb_ser_id()+"}\n" ;
            }else {
                filter+=",{\"ser_id\": "+ TabTwo.arrayList.get(postion).getSersup_ids().get(i).getBdb_ser_id()+"}\n" ;
            }
        }

        filter+=    "                \n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}\n";

        APICall.getMyEffectsClient(context,filter,effectAdapter);


//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getId());
//        Log.e("servicesForClientGroups","sfcg"+MultiIndividualBookingReservationFragment.servicesForClientGroups.get(MultiIndividualBookingReservationFragment.servicesForClientGroups.size()-1).getName());
//                     if (position!=0){
//                         postions.add(position-1);
//                     }
        update.setText(R.string.next);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f=getEffectFilter();
                Log.e("filter",getFilter(f));
                Log.e("Effectfilter",f);
                if (EffectAdapter.checkClick){
                    Intent intent=new Intent(context, OfferBookingResult.class);
                    intent.putExtra("filter",getFilter(f));
                    intent.putExtra("offertype", offertype);
                    intent.putExtra("place", place);
                    intent.putExtra("position", postion);
                    APICall.showUpdateEffectsDialog(context,intent, MyEffectsActivity.getEffectFilter());

                }else {
                    Intent intent=new Intent(context, OfferBookingResult.class);
                    intent.putExtra("filter",getFilter(f));
                    intent.putExtra("offertype", offertype);
                    intent.putExtra("place", place);
                    intent.putExtra("position", postion);
                    startActivity(intent);
                }
//                Intent intent=new Intent(context, OfferBookingResult.class);
//                intent.putExtra("filter",getFilter(f));
//                intent.putExtra("offertype", offertype);
//                intent.putExtra("place", place);
//                intent.putExtra("position", postion);
//                startActivity(intent);
            }
        });
    }


    static String effectFilter="";
    public static String getEffectFilter(){
        effectFilter="";
        for (int i=0;i< APICall.clientEffectModels.size();i++){
            for (int j=0;j<APICall.clientEffectModels.get(i).getEffects().size();j++){
                if (effectFilter.equals("")){
                    effectFilter="{\"effect_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_effect_id()+"\n,\"effect_value\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_value()+"}\n";
                }else {
                    effectFilter+=",{\"effect_id\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_effect_id()+"\n,\"effect_value\": "+APICall.clientEffectModels.get(i).getEffects().get(j).getBdb_value()+"}\n";

                }
            }
        }
        return effectFilter;
    }


    public static String getfilter(String eff_filter){
        String clientf="";
//        SharedPreferences sh = BeautyMainPage.context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE);


        String username = BeautyMainPage.client_name;
        String phone = BeautyMainPage.client_number;

        if (MultiIndividualBookingReservationFragment.choose_date.getVisibility() == View.VISIBLE) {
            String date = MultiIndividualBookingReservationFragment.choose_date.getText().toString();

            clientf = " \"clients\": [\n" +
                    "\t\t{\"client_name\": \"" + username + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 1,\"is_adult\":1,\"date\": \"" + date + "\",\"services\": [ ";
            String services = "";
            for (int i = 0; i < MultiIndividualBookingReservationFragment.servicesForClientGroups.size(); i++) {

                if (i == 0) {
                    services = "{\"ser_id\": " + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }";
                } else {
                    services = services + ",{\"ser_id\": " + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }";
                }

            }

            clientf = clientf + services + "" +
                    "]" +
                    ",\"effect\":["+eff_filter+"]" +
                    "}\n]}";
        }
        else {
            clientf = " \"clients\": [\n";
            for (int i = 0; i < MultiIndividualBookingReservationFragment.servicesForClientGroups.size(); i++) {

                if (i == 0) {
                    clientf = clientf + "\t\t{\"client_name\": \"" + username + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 1,\"is_adult\":1,\"date\": \"" + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getDate().getText().toString() + "\",\"services\": [ ";
                    String services = "";
//                        for (int i = 0; i < MultiIndividualBookingReservationFragment.servicesForClientGroups.size(); i++) {


                    clientf += "{\"ser_id\": " + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }]" +
                            ",\"effect\":["+eff_filter+"]\n}";

                } else {

                    clientf = clientf + "\t\t,{\"client_name\": \"" + username + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 1,\"is_adult\":1,\"date\": \"" + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getDate().getText().toString() + "\",\"services\": [ ";
                    String services = "";
//                        for (int i = 0; i < MultiIndividualBookingReservationFragment.servicesForClientGroups.size(); i++) {

                    clientf += "{\"ser_id\": " + MultiIndividualBookingReservationFragment.servicesForClientGroups.get(i).getId() + ",\"ser_time\": 60 }]" +
                            ",\"effect\":["+eff_filter+"]\n}";
                }

            }
            clientf = clientf + "]}";
        }
        return clientf;
    }
    public  static String place_num,price_num;

   public static String getFilter(String effect){
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



        try {
            switch (PlaceServiceFragment.placeSpinner.getSelectedItemPosition()) {
                case 1:
                    place_num = "9";
                    price_num = "32";
                    break;
                case 2:
                    place_num = "8";
                    price_num = "1";
                    break;
                case 3:
                    place_num = "10";
                    price_num = "30";
                    break;
                case 4:
                    place_num = "11";
                    price_num = "31";
                    break;

            }
        }catch (Exception e){


        }
        String name= BeautyMainPage.client_name;
        String mobile=BeautyMainPage.client_number;
        String bdb_pack_code = TabTwo.arrayList.get(postion).getBdb_pack_code();

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
                        "  " +prices+
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


        String services = sortdates(MultiDateOfferBooking.selectDateOfferAdapter.dates,name,mobile,effect, postion);

        postdata = postdata + services + "],\"offer_type\":" + offertype + "}";


        Log.e("POSTDATA", postdata);


        return postdata;
    }
   public static String getFilter(String effect,String offertype){
        String prices="";
        if (PlaceServiceFragment.maxprice.equals("") || PlaceServiceFragment.maxprice.equals("")){
            prices= " {\n" +
                    "            \"num\": "+MultiDateOfferBooking.price_num+",\n" +
                    "            \"value1\": 0,\n" +
                    "            \"value2\": 10000\n" +
                    "        },\n" ;
        }else {
            prices="{\n" +
                    "            \"num\": "+MultiDateOfferBooking.price_num+",\n" +
                    "            \"value1\": "+PlaceServiceFragment.minprice+",\n" +
                    "            \"value2\": "+PlaceServiceFragment.maxprice+"\n" +
                    "        },\n";
        }



        try {
            switch (PlaceServiceFragment.placeSpinner.getSelectedItemPosition()) {
                case 1:
                    place_num = "9";
                    price_num = "32";
                    break;
                case 2:
                    place_num = "8";
                    price_num = "1";
                    break;
                case 3:
                    place_num = "10";
                    price_num = "30";
                    break;
                case 4:
                    place_num = "11";
                    price_num = "31";
                    break;

            }
        }catch (Exception e){


        }
        String name= BeautyMainPage.client_name;
        String mobile=BeautyMainPage.client_number;
        String bdb_pack_code = TabTwo.arrayList.get(postion).getBdb_pack_code();

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
                        "  " +prices+
                        "        {\n" +
                        "            \"num\": "+MultiDateOfferBooking.place_num+",\n" +
                        "            \"value1\": 1,\n" +
                        "            \"value2\": 0\n" +
                        "        }\n" +
                        "    ],\n" +
                        "\n" +
                        "\t\"bdb_pack_code\":" + MultiDateOfferBooking.bdb_pack_id + ",\n" +
                        "\t\"clients\": [  ";
//                    String cname = client_name.getText().toString();
//                    String phone = phone_number.getText().toString();


        String services = sortdates(MultiDateOfferBooking.selectDateOfferAdapter.dates,name,mobile,effect, postion);

        postdata = postdata + services + "],\"offer_type\":" + offertype + "}";


        Log.e("POSTDATA", postdata);


        return postdata;
    }
    static String sortdates(ArrayList<TextView> dates, String cname, String phone,String effect, int postion){
        ArrayList<ArrayList<IDNameService>> arrayList=new ArrayList<>();
        ArrayList<Integer> index=new ArrayList<>();

        for (int i=0;i<dates.size();i++){
            String ser_sup_id=TabTwo.arrayList.get(postion).getSersup_ids().get(i).getBdb_ser_sup_id();

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

            services=services+"\n ],\"effect\":["+effect+"]}";

        }
        Log.e("ServicesDate",services);
        return services;
    }

}
