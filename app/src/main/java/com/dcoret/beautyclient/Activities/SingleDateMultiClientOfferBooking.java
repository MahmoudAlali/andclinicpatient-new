package com.dcoret.beautyclient.Activities;

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

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.OfferBookingMultiClientsAdapter;
import com.dcoret.beautyclient.DataModel.OfferClientsModel;
import com.dcoret.beautyclient.Fragments.IndividualBooking.Tabs.TabTwo;
import com.dcoret.beautyclient.R;

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
    TextView add_date;
    Context context;
    Button next;


    public static ArrayList<OfferClientsModel> offerClientsModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_date_multi_client_offer_booking);
        recyclerView = findViewById(R.id.recycleview);
        add_date = findViewById(R.id.add_date);
        context = this;
        offerClientsModels.clear();


        final int postion = getIntent().getIntExtra("postion", 0);


        String bdb_pack_id = TabTwo.arrayList.get(postion).getBdb_pack_code();


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

                    Date date = sdf.parse(TabTwo.arrayList.get(postion).getBdb_offer_end());

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
                        add_date.setText("Select Date");
                    }
                });


                dialog.show();
            }
        });


        APICall.browseOneOffer(bdb_pack_id, context);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String bdb_pack_code = TabTwo.arrayList.get(postion).getBdb_pack_code();
                if (add_date.getText().toString().equals(getResources().getString(R.string.select_date))) {
                    APICall.showSweetDialog(context, getResources().getString(R.string.alert),getResources().getString(R.string.Please_enter_Date) );
                } else {
                    String postdata = "{\n" +
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
                                client = client + "{\"client_name\": \"" + cname + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 0,\"services\": [ \n";
                                String services = "";
                                for (int j = 0; j < offerClientsModels.get(i).getServiceDetails().size(); j++) {
                                    if (j == 0) {
                                        services = "{ \"bdb_ser_sup_id\": " + offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id() + ",  \"ser_time\": 60,  \"bdb_ext_pack_code\":0 }\n";
                                    } else {
                                        services = ",{ \"bdb_ser_sup_id\": " + offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id() + ",  \"ser_time\": 60,  \"bdb_ext_pack_code\":0 }\n";
                                    }
                                }
                                client = client + services + "]}";
                                Log.e("selectDateOff.size()", OfferBookingMultiClientsAdapter.selectDateOfferModels.size() + "");
                            } else {
                                client = client + ",{\"client_name\": \"" + cname + "\",\"client_phone\": \"" + phone + "\",\"is_current_user\": 0,\"services\": [ \n";
                                String services = "";
                                for (int j = 0; j < offerClientsModels.get(i).getServiceDetails().size(); j++) {
                                    if (j == 0) {
                                        services = "{ \"bdb_ser_sup_id\": " + offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id() + ",  \"ser_time\": 60,  \"bdb_ext_pack_code\":0 }\n";
                                    } else {
                                        services = "\n,{ \"bdb_ser_sup_id\": " + offerClientsModels.get(i).getServiceDetails().get(j).getBdb_ser_sup_id() + ",  \"ser_time\": 60,  \"bdb_ext_pack_code\":0 }\n";
                                    }
                                }
                                client = client + services + "]}";
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
                    Intent intent = new Intent(context, OfferBookingResult.class);
                    intent.putExtra("filter", postdata);
                    intent.putExtra("offertype",getIntent().getStringExtra("offertype")  );
                    startActivity(intent);

                }
            }
        });
    }
}


