package com.dcoret.beautyclient.Activities;

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
import com.dcoret.beautyclient.Adapters.SelectDateOfferAdapter;
import com.dcoret.beautyclient.DataModel.IDNameService;
import com.dcoret.beautyclient.Fragments.IndividualBooking.Tabs.TabTwo;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class MultiDateOfferBooking extends AppCompatActivity {

    ArrayList<String> strings=new ArrayList<>();
    RecyclerView recyclerView;
    SelectDateOfferAdapter selectDateOfferAdapter;
    Context context;
    Button next;
    EditText phone_number,client_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multi_date_offer_booking);
        final int postion=getIntent().getIntExtra("postion",0);
        final String offertype=getIntent().getStringExtra("offertype");
        context=this;
        selectDateOfferAdapter.dates.clear();

//        int postion=getIntent().getIntExtra("postion",0);
//        client_name=findViewById(R.id.client_name);
//        phone_number=findViewById(R.id.phone_number);
        next=findViewById(R.id.next);


        for (int i = 0; i< TabTwo.arrayList.get(postion).getSersup_ids().size(); i++){
            strings.add(TabTwo.arrayList.get(postion).getSersup_ids().get(i).getBdb_name());
        }

        recyclerView=findViewById(R.id.recycleview);

        selectDateOfferAdapter=new SelectDateOfferAdapter(context,strings);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(selectDateOfferAdapter);
        APICall.detailsUser3(context);



        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 SharedPreferences editor=context.getSharedPreferences("LOGIN",Context.MODE_PRIVATE);

                 String name=editor.getString("bdb_name",null);
                 String mobile=editor.getString("bdb_mobile",null);
                String bdb_pack_code = TabTwo.arrayList.get(postion).getBdb_pack_code();
                    String postdata = "{\n" +
                            "\t\"bdb_pack_code\":" + bdb_pack_code + ",\n" +
                            "\t\"clients\": [  ";
//                    String cname = client_name.getText().toString();
//                    String phone = phone_number.getText().toString();


                    String services = sortdates(selectDateOfferAdapter.dates,name,mobile, postion);

                    postdata = postdata + services + "],\"offer_type\":" + offertype + "}";


                    Log.e("POSTDATA", postdata);


//                    String client="";
//                    ArrayList<ArrayList<String>> arrayLists=new ArrayList<>();
//                    HashMap<String,ArrayList<String>> listHashMap=new HashMap<>();
//                    Log.e("selectDateOff.size()",selectDateOfferAdapter.dates.size()+"");
//                    for (int i=0;i<selectDateOfferAdapter.dates.size();i++) {
////                        View view = recyclerView.getLayoutManager().findViewByPosition(1);
//                        String bdb_ser_sup_id = API.dofs.get(postion).getSersup_ids().get(i).getBdb_ser_sup_id();
////                        String ser_time=API.dofs.get(postion).getSersup_ids().get(i).getBdb_ser_sup_id();
//                        if (i==0){
//                            ArrayList list=new ArrayList();
//                            list.add(bdb_ser_sup_id);
//                            listHashMap.put(selectDateOfferAdapter.dates.get(i).getText().toString(),list);
//                        }else {
//                            for (int j=0;j<listHashMap.size();j++){
////                                if (listHashMap.)
//                            }
////
//                            }
//                        }

//                        for (int i=0;i<arrayLists.size();i++){
//                            for (int j=0;j<arrayLists.get(i).size();j++){
//                                Log.e(i+j+"",arrayLists.get(i).get(j));
//                            }
//                        }


//                        if (i == 0 && selectDateOfferAdapter.dates.get(i).getText().toString().length() != 0){
//
//                            client = "{\n" +
//                                    "\t\t\"date\":\"" + selectDateOfferAdapter.dates.get(i).getText().toString() + "\",\n" +
//                                    "\t\t\"client_name\": \"" + cname + "\",\n" +
//                                    "\t\t\"client_phone\": \"" + phone + "\",\n" +
//                                    "\t\t\"is_current_user\": 1,\n" +
//                                    "\t\t\"services\": [\n" +
//                                    "\t\t\t{\n" +
//                                    "\t\t\t\t\"bdb_ser_sup_id\": " + bdb_ser_sup_id + ",\n" +
//                                    "\t\t\t\t\"ser_time\": 60,\n" +
//                                    "           }      \n" +
//
//                                    "\t\t   ]\n" +
//                                    "\t\t   }";
//                        }else if ( selectDateOfferAdapter.dates.get(i).getText().toString().length() != 0){
//
//                            client =client+ ",{\n" +
//                                    "\t\t\"date\":\"" + selectDateOfferAdapter.dates.get(i).getText().toString() + "\",\n" +
//                                    "\t\t\"client_name\": \"" + cname + "\",\n" +
//                                    "\t\t\"client_phone\": \"" + phone + "\",\n" +
//                                    "\t\t\"is_current_user\": 1,\n" +
//                                    "\t\t\"services\": [\n" +
//                                    "\t\t\t{\n" +
//                                    "\t\t\t\t\"bdb_ser_sup_id\": " + bdb_ser_sup_id + ",\n" +
//                                    "\t\t\t\t\"ser_time\": 60,\n" +
//                                    "           }      \n" +
//                                    "\t\t   ]\n" +
//                                    "\t\t   }";
//                        }

//                    }

//            postdata=postdata+client+"],\"offer_type\":"+offertype+"\n" +
//                    "\t\t   }\n" +
//                    "\t\t";


//                    Log.e("PostData",postdata);

                    Intent intent = new Intent(context, OfferBookingResult.class);

                    intent.putExtra("filter", postdata);
                    intent.putExtra("offertype", offertype);
                    startActivity(intent);



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

            services=services+"\n ]}";

        }
        Log.e("ServicesDate",services);
        return services;
    }
}
