package com.dcoret.beautyclient.Activities.MultiDateOffer;

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

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.OfferBookingResult;
import com.dcoret.beautyclient.Adapters.SelectDateOfferAdapter;
import com.dcoret.beautyclient.DataModel.IDNameService;
import com.dcoret.beautyclient.DataModel.OfferClientsModel;
import com.dcoret.beautyclient.Fragments.IndividualBooking.PlaceServiceFragment;
import com.dcoret.beautyclient.Fragments.IndividualBooking.Tabs.TabTwo;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class MultiDateOfferBooking extends AppCompatActivity {

    public static ArrayList<String> strings=new ArrayList<>();
    public static ArrayList<OfferClientsModel.ServiceDetails> serviceDetails=new ArrayList<>();
    RecyclerView recyclerView;
    public static SelectDateOfferAdapter selectDateOfferAdapter;
    Context context;
    Button next;
    EditText phone_number,client_name;
    String place_num="",price_num="";
    static String place="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_date_offer_booking);
        final int postion=getIntent().getIntExtra("postion",0);
        final String offertype=getIntent().getStringExtra("offertype");
        context=this;
        serviceDetails.clear();
        selectDateOfferAdapter.dates.clear();

//        int postion=getIntent().getIntExtra("postion",0);
//        client_name=findViewById(R.id.client_name);
//        phone_number=findViewById(R.id.phone_number);
        next=findViewById(R.id.next);
        String bdb_pack_id = TabTwo.arrayList.get(postion).getBdb_pack_code();


        Log.e("PackCode",bdb_pack_id);
//        for (int i = 0; i< TabTwo.arrayList.get(postion).getSersup_ids().size(); i++){
//            if (APICall.ln.equals("en")) {
//                strings.add(TabTwo.arrayList.get(postion).getSersup_ids().get(i).getBdb_name());
//            }else {
//                strings.add(TabTwo.arrayList.get(postion).getSersup_ids().get(i).getBdb_name_ar());
//            }
//        }
        place=TabTwo.arrayList.get(postion).getBdb_offer_place();
        recyclerView=findViewById(R.id.recycleview);

        selectDateOfferAdapter=new SelectDateOfferAdapter(context,serviceDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(selectDateOfferAdapter);
        APICall.browseOneMultiOffer(bdb_pack_id, context);

        APICall.detailsUser3(context);

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

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 SharedPreferences editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);

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
                                    "\n" +
                            "\t\"bdb_pack_code\":" + bdb_pack_code + ",\n" +
                            "\t\"clients\": [  ";
//                    String cname = client_name.getText().toString();
//                    String phone = phone_number.getText().toString();


                    String services = sortdates(selectDateOfferAdapter.dates,name,mobile, postion);

                    postdata = postdata + services + "],\"offer_type\":" + offertype + "}";


                    Log.e("POSTDATA", postdata);



                if (TabTwo.arrayList.get(postion).getBdb_is_effects_on().equals("1")) {
                    Intent intent = new Intent(context, MultiDateOfferEffect.class);
                    intent.putExtra("filter", postdata);
                    intent.putExtra("offertype", offertype);
                    intent.putExtra("place", place);
                    intent.putExtra("position", postion);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(context, OfferBookingResult.class);
                    intent.putExtra("filter", postdata);
                    intent.putExtra("offertype", offertype);
                    intent.putExtra("place", place);
                    intent.putExtra("position", postion);
                    startActivity(intent);
                }


            }
//                if ()

        });




    }

    static String sortdates(ArrayList<TextView> dates,String cname,String phone, int postion){
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

            services=services+"\n ],\"effect\":[]}";

        }
        Log.e("ServicesDate",services);
        return services;
    }
}
