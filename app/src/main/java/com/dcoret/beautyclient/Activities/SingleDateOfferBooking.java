package com.dcoret.beautyclient.Activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.ShowServicesAdapter;
import com.dcoret.beautyclient.DataClass.OfferClientsModel;
import com.dcoret.beautyclient.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SingleDateOfferBooking extends AppCompatActivity {
    EditText client_name,phone_number;
    public static ArrayList<OfferClientsModel> offerClientsModels=new ArrayList<>();

    //    DatePicker datePicker;
    Button next;
    TextView showDate;
    CoordinatorLayout selectdate;
    public static ShowServicesAdapter offerAdapter;
    Context context;
    RecyclerView recyclerView;
    public static ArrayList<String> services=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_date_offer_booking);

        context=this;
        final int postion=getIntent().getIntExtra("postion",0);
        final String otype=getIntent().getStringExtra("offertype");


        client_name=findViewById(R.id.client_name);
        phone_number=findViewById(R.id.phone_number);
        selectdate=findViewById(R.id.select_date);
        showDate=findViewById(R.id.date);
        next=findViewById(R.id.next);


        recyclerView=findViewById(R.id.recycleview);
        selectdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.select_date);
                TextView ok=dialog.findViewById(R.id.confirm);
                TextView cancel=dialog.findViewById(R.id.cancel);
                final DatePicker datePicker=dialog.findViewById(R.id.date_picker);
                datePicker.setMinDate(System.currentTimeMillis());

                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

                    Date date=sdf.parse(TabTwo.arrayList.get(postion).getBdb_offer_end());

                    datePicker.setMaxDate(date.getTime());

                }catch (Exception e){
                    e.printStackTrace();
                }
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        int month=datePicker.getMonth()+1;
                        showDate.setText(datePicker.getYear()+"-"+month+"-"+datePicker.getDayOfMonth());
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
//                        ((Item)holder).select_time.setText(datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth());
                    }
                });

                dialog.show();
            }
        });

        offerAdapter=new ShowServicesAdapter(context,offerClientsModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(offerAdapter);

        APICall.browseOneOfferv2(TabTwo.arrayList.get(postion).getBdb_pack_code(),offerClientsModels,offerAdapter,context);






        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bdb_pack_code=TabTwo.arrayList.get(postion).getBdb_pack_code();
                String date=showDate.getText().toString();
                String cname="c1";
                String cphone="0500600700";
                String offerType=otype;
//                    ArrayList<String> arrayList =new ArrayList<>();
                String services="";
                String bdb_ser_sup_id="";
                for (int i=0;i<TabTwo.arrayList.get(postion).getSersup_ids().size();i++){
                    bdb_ser_sup_id=TabTwo.arrayList.get(postion).getSersup_ids().get(i).getBdb_ser_sup_id();
                    if (i==0){
                        services="\t\t{\"bdb_ser_sup_id\": "+bdb_ser_sup_id+",\"ser_time\": 60 }\n";
                    }else {
                        services=services+"\t\t,{\"bdb_ser_sup_id\": "+bdb_ser_sup_id+",\"ser_time\": 60 }\n";
                    }
                }
                String postdata="{\"bdb_pack_code\":"+bdb_pack_code+",\"date\":\""+date+"\",\"clients\": [        \n" +
                        "\t{\"client_name\": \""+cname+"\",\"client_phone\": \""+cphone+"\",\"is_current_user\": 0,\n" +
                        "\t\"services\": [\n" +
                        services+
                        "\t\t]        \n" +
                        "\t}\n" +
                        "\t],\"offer_type\":"+offerType+"\n" +
                        "}";
                Log.e("postdata",postdata);

                Intent intent=new Intent(context,OfferBookingResult.class);
                intent.putExtra("filter",postdata);
                intent.putExtra("offertype",offerType);
                startActivity(intent);
            }



        });






    }
}

