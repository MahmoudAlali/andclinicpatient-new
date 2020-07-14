package com.ptm.clinicpa.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Filters;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.CreateRequestActivity;
import com.ptm.clinicpa.Activities.Offers;
import com.ptm.clinicpa.Activities.RelativesActivity;
import com.ptm.clinicpa.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;

public class PersonalIndivOfferRequest extends Fragment implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    RadioButton home,center;
    Button showDoctorsBtn;
    public static Spinner medFileNum,companionName;
    static ArrayList<String> filesList=new ArrayList<>();
    String filterCenterName="";
    public static ArrayAdapter adapter,adapter2;
    public static boolean isPersonalReser,isOffer;
    static ArrayList<String> relativesList=new ArrayList<>();

    public static String clientName,clientAge,clientGender,clientRelation,HealthRecord="";
    public static String sup_id,max_age,min_age,supported_gender,center_id="",pack_code,longNum,latNum;
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_free_booking_filters2, container, false);
        home=view.findViewById(R.id.home);
        center=view.findViewById(R.id.center);
        view.findViewById(R.id.medPlace).setVisibility(View.GONE);
        view.findViewById(R.id.placeLayout).setVisibility(View.GONE);
        showDoctorsBtn=view.findViewById(R.id.ok);
        companionName=view.findViewById(R.id.companionName);
        medFileNum=view.findViewById(R.id.providerName);
        isPersonalReser=getArguments().getBoolean("isMe");
        isOffer=getArguments().getBoolean("is_offer");
        sup_id=getArguments().getString("sup_id");
        max_age=getArguments().getString("max_age");
        min_age=getArguments().getString("min_age");
        supported_gender=getArguments().getString("supported_gender");
        latNum=getArguments().getString("latNum");
        longNum=getArguments().getString("longNum");
        pack_code=getArguments().getString("pack_code");
        center_id=getArguments().getString("center_id");
        int max=Integer.parseInt(max_age);
        int min =Integer.parseInt(min_age);
        //final Spinner doctorSpeciality = layout2.findViewById(R.id.doctorSpeciality);

        // APICall.getdetailsUser(BeautyMainPage.context);
        if (!isPersonalReser)
                APICall.getFollowersForBooking(BeautyMainPage.context,max,min,supported_gender,true);


        Log.e("IsOffer","o "+isOffer);
        Log.e("IsMe","o "+isPersonalReser);
        //region Medical file number



        adapter2 = new ArrayAdapter(BeautyMainPage.context, R.layout.simple_spinner_item_layout_v1, filesList);
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        medFileNum.setAdapter(adapter2);
        medFileNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (medFileNum.getSelectedItem().equals(BeautyMainPage.context.getString(R.string.new_center)))
                {
                    Log.e("filterCenterName","filterCenterName is new center");

                }
                else
                {
                    if(position!=0)
                    {
                        filterCenterName = Filters.getString(Filters.CLINIC_ID, APICall.allFileNumbers.get(position - 1).getHealth_center_id());
                        Log.e("filterCenterName","filterCenterName"+filterCenterName);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                filterCenterName="";
                //  filterSpeciality="";
            }
        });



        //endregion

        if(!isPersonalReser)
        {


            /* adapter = new ArrayAdapter<String>(BeautyMainPage.context,
                    android.R.layout.simple_dropdown_item_1line, relativesList);*/
            adapter = new ArrayAdapter(BeautyMainPage.context, R.layout.simple_spinner_item_layout_v1, relativesList);
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);


            medFileNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    // else
                    {
                        if (medFileNum.getSelectedItem().equals(BeautyMainPage.context.getString(R.string.new_center)))
                        {
                            Log.e("filterCenterName","filterCenterName is new center");

                        }
                        else
                        {
                            if(position!=0)
                            {
                                filterCenterName = Filters.getString(Filters.CLINIC_ID,APICall.allRelativesList.get(companionName.getSelectedItemPosition()+1).getRecords().get(position-1).getHealth_center_id());
                                Log.e("filterCenterName","filterCenterName"+filterCenterName);
                            }
                            else
                            {

                            }

                        }

                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                    filterCenterName="";
                    //  filterSpeciality="";
                }
            });


            companionName.setAdapter(adapter);
            companionName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("onItemSelected","Item "+position);
                    if(position!=0)
                    {
                        int index=position-1;
                        clientGender=APICall.allRelativesList.get(index).getBdb_gender();
                        clientRelation=APICall.allRelativesList.get(index).getBdb_relation();
                        clientAge=APICall.allRelativesList.get(index).getBdb_old();
                        clientName=APICall.allRelativesList.get(index).getBdb_user_name();
                        filesList.clear();
                        filesList.add(BeautyMainPage.context.getString(R.string.med_id2));

                        for (int i=0;i<APICall.allRelativesList.get(index).getRecords().size();i++){
                                if(center_id.equals(APICall.allRelativesList.get(index).getRecords().get(i).getHealth_center_id()))
                                {
                                    if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("en"))
                                        filesList.add(APICall.allRelativesList.get(index).getRecords().get(i).getHealth_center_en()+": " + APICall.allRelativesList.get(index).getRecords().get(i).getHealth_record());
                                    else
                                        filesList.add(APICall.allRelativesList.get(index).getRecords().get(i).getHealth_center_ar()+": " + APICall.allRelativesList.get(index).getRecords().get(i).getHealth_record());
                                }

                        }
                        if(filesList.size()==1)
                        filesList.add(BeautyMainPage.context.getString(R.string.new_center));

                        adapter2.notifyDataSetChanged();
                    }


                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
        else
        {
            view.findViewById(R.id.compNameLayout).setVisibility(View.GONE);
            APICall.getAllFileNumbers(BeautyMainPage.context,true);
            clientAge=BeautyMainPage.bdb_old;
            clientGender=BeautyMainPage.client_gender;
            clientName=BeautyMainPage.client_name;
            clientRelation="0";
        }

        showDoctorsBtn.setText(R.string.next);
        showDoctorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                freeBookingFragment.filterMyLocationLat="{\"num\":34,\"value1\":"+ Offers.Lat +",\"value2\":0}";
                freeBookingFragment.filterMyLocationLng="{\"num\":35,\"value1\":"+Offers.Long+",\"value2\":0}";
                freeBookingFragment.filterDistance="{\"num\":2,\"value1\":0,\"value2\":100000}";
                if(medFileNum.getSelectedItemPosition()==0) {
                    APICall.showSweetDialog(BeautyMainPage.context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.filenum_proceed));
                    return;

                }
                else
                {
                    freeBookingFragment.filterSupplierName=filterCenterName;
                    Log.e("filterCenterName","filterCenterName"+freeBookingFragment.filterSupplierName);

                }
                freeBookingFragment.filterServicePlace = ",{\"num\":9,\"value1\":1}";
                freeBookingFragment.Place="center";

                    Intent i = new Intent(BeautyMainPage.context, CreateRequestActivity.class);
                    i.putExtra("sup_id", sup_id);
                    i.putExtra("age",clientAge);
                    i.putExtra("relation",clientRelation);
                    i.putExtra("gender",clientGender);
                    i.putExtra("client_name",clientName);
                    i.putExtra("is_offer",true);
                    i.putExtra("pack_code",pack_code);
                    i.putExtra("longNum",longNum);
                    i.putExtra("latNum",latNum);
                    i.putExtra("health_record",medFileNum.getSelectedItem().toString());

                startActivity(i);
            }
        });

        return view;
    }

    public static void fillFiles()
    {
        if (filesList.size()==0)
        {
            filesList.add(BeautyMainPage.context.getString(R.string.med_id2));

            for (int i=0;i<APICall.allFileNumbers.size();i++){
                if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("en"))
                    filesList.add(APICall.allFileNumbers.get(i).getHealth_center_en()+": " + APICall.allFileNumbers.get(i).getHealth_record());
                else
                    filesList.add(APICall.allFileNumbers.get(i).getHealth_center_ar()+": " + APICall.allFileNumbers.get(i).getHealth_record());

            }
            if(filesList.size()==1)
            {
                filesList.add(BeautyMainPage.context.getString(R.string.new_center));
            }

            adapter2.notifyDataSetChanged();
        }
    }
    public static void fillRelatives()
    {
        if (relativesList.size()==0) {
            relativesList.add(BeautyMainPage.context.getString(R.string.relative_name));
            for (int i = 0; i < APICall.allRelativesList.size(); i++) {
                relativesList.add(APICall.allRelativesList.get(i).getBdb_user_name());
            }
        }

        adapter.notifyDataSetChanged();

    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

    }
}
