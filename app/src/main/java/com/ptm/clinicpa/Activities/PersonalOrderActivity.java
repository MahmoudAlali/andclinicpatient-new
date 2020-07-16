package com.ptm.clinicpa.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Filters;
import com.ptm.clinicpa.Fragments.RequestProvidersFragment;
import com.ptm.clinicpa.Fragments.freeBookingFragment;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

public class PersonalOrderActivity extends AppCompatActivity
        implements LocationListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    RadioButton home,center;
    Button showDoctorsBtn;
    public static Spinner medFileNum,companionName;
    static ArrayList<String> filesList=new ArrayList<>();
    String filterCenterName="";
    public static ArrayAdapter adapter,adapter2;
    public static boolean isPersonalReser,skipDoctors;
    static ArrayList<String> relativesList=new ArrayList<>();
    public static String sup_id,max_age,min_age,supported_gender;
    public static String clientName,clientAge,clientGender,clientRelation,HealthRecord="";
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_order);

        home=findViewById(R.id.home);
        center=findViewById(R.id.center);
        showDoctorsBtn=findViewById(R.id.ok);
        companionName=findViewById(R.id.companionName);
        medFileNum=findViewById(R.id.providerName);
        isPersonalReser=getIntent().getBooleanExtra("isMe",false);
        skipDoctors=getIntent().getBooleanExtra("fromDoctors",false);
        sup_id=getIntent().getStringExtra("sup_id");
        context=this;

        //final Spinner doctorSpeciality = layout2.findViewById(R.id.doctorSpeciality);

        // APICall.getdetailsUser(BeautyMainPage.context);
        if (!isPersonalReser) {

            max_age = getIntent().getStringExtra("max_age");
            min_age = getIntent().getStringExtra("min_age");
            supported_gender = getIntent().getStringExtra("supported_gender");
            int max = Integer.parseInt(max_age);
            int min = Integer.parseInt(min_age);
            APICall.getFollowersForBooking(context, max, min, supported_gender, false);

        }

        //region Medical file number


        if(skipDoctors)
        {
            showDoctorsBtn.setText(R.string.next);
            showDoctorsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    freeBookingFragment.filterMyLocationLat="{\"num\":34,\"value1\":"+ Offers.Lat +",\"value2\":0}";
                    freeBookingFragment.filterMyLocationLng="{\"num\":35,\"value1\":"+Offers.Long+",\"value2\":0}";
                    freeBookingFragment.filterDistance="{\"num\":2,\"value1\":0,\"value2\":100000}";
                    if(medFileNum.getSelectedItemPosition()==0) {
                        APICall.showSweetDialog(context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.filenum_proceed));
                        return;

                    }
                    else
                    {
                        freeBookingFragment.filterSupplierName=filterCenterName;
                        Log.e("filterCenterName","filterCenterName"+freeBookingFragment.filterSupplierName);

                    }
                    if (center.isChecked()) {
                        freeBookingFragment.filterServicePlace = ",{\"num\":9,\"value1\":1}";
                        freeBookingFragment.Place="center";
                    }else if (home.isChecked()){
                        freeBookingFragment.filterServicePlace = ",{\"num\":8,\"value1\":1}";
                        freeBookingFragment.Place="home";

                    }
                    else
                    {
                        APICall.showSweetDialog(context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.place_proceed));
                        return;
                    }
                    Intent i = new Intent(context, CreateRequestActivity.class);
                    i.putExtra("sup_id",sup_id);
                    i.putExtra("age", clientAge);
                    i.putExtra("relation",clientRelation);
                    i.putExtra("gender",clientGender);
                    i.putExtra("client_name",clientName);


               /* Intent i = new Intent(context, RelativesActivity.class);
                i.putExtra("sup_id",itemArrayList.get(position).getSup_id());
                i.putExtra("center_id",itemArrayList.get(position).getHealthCntr_id());
                i.putExtra("isBooking",true);
                i.putExtra("max_age",itemArrayList.get(position).getMax_age());
                i.putExtra("min_age",itemArrayList.get(position).getMin_age());
                i.putExtra("supported_gender",itemArrayList.get(position).getSupported_gender());
*/
                    context.startActivity(i);

                }
            });

        }
        else
        {
            showDoctorsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    freeBookingFragment.filterMyLocationLat="{\"num\":34,\"value1\":"+ Offers.Lat +",\"value2\":0}";
                    freeBookingFragment.filterMyLocationLng="{\"num\":35,\"value1\":"+Offers.Long+",\"value2\":0}";
                    freeBookingFragment.filterDistance="{\"num\":2,\"value1\":0,\"value2\":100000}";
                    if(medFileNum.getSelectedItemPosition()==0) {
                        APICall.showSweetDialog(context, getResources().getString(R.string.ExuseMeAlert), getResources().getString(R.string.filenum_proceed));
                        return;

                    }
                    else
                    {
                        freeBookingFragment.filterSupplierName=filterCenterName;
                        Log.e("filterCenterName","filterCenterName"+freeBookingFragment.filterSupplierName);

                    }
                    if (center.isChecked()) {
                        freeBookingFragment.filterServicePlace = ",{\"num\":9,\"value1\":1}";
                        freeBookingFragment.Place="center";
                    }else if (home.isChecked()){
                        freeBookingFragment.filterServicePlace = ",{\"num\":8,\"value1\":1}";
                        freeBookingFragment.Place="home";

                    }
                    else
                    {
                        APICall.showSweetDialog(context,getResources().getString(R.string.ExuseMeAlert),getResources().getString(R.string.place_proceed));
                        return;
                    }
                    {
                        Fragment fragment = new RequestProvidersFragment();
                        Bundle b= new Bundle();
                        b.putBoolean("isGroup",false);
                        FragmentManager fm = getFragmentManager();
                        fragment.setArguments(b);
                        FragmentTransaction fragmentTransaction = fm.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment, fragment);
                        fragmentTransaction.commit();
                    }

                }
            });

        }

        adapter2 = new ArrayAdapter(context, R.layout.simple_spinner_item_layout_v1, filesList);
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);
        medFileNum.setAdapter(adapter2);
        medFileNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (medFileNum.getSelectedItem().equals(context.getString(R.string.new_center)))
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
            adapter = new ArrayAdapter(context, R.layout.simple_spinner_item_layout_v1, relativesList);
            adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item_layout_v3);


            medFileNum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                    // else
                    {
                        if (medFileNum.getSelectedItem().equals(context.getString(R.string.new_center)))
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
                        filesList.add(context.getString(R.string.med_id2));

                        for (int i=0;i<APICall.allRelativesList.get(index).getRecords().size();i++){
                            if(context.getResources().getString(R.string.locale).equals("en"))
                                filesList.add(APICall.allRelativesList.get(index).getRecords().get(i).getHealth_center_en()+": " + APICall.allRelativesList.get(index).getRecords().get(i).getHealth_record());
                            else
                                filesList.add(APICall.allRelativesList.get(index).getRecords().get(i).getHealth_center_ar()+": " + APICall.allRelativesList.get(index).getRecords().get(i).getHealth_record());

                        }
                        filesList.add(context.getString(R.string.new_center));

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
            companionName.setVisibility(View.GONE);
            APICall.getAllFileNumbers(context,false);
            clientAge=BeautyMainPage.bdb_old;
            clientGender=BeautyMainPage.client_gender;
            clientName=BeautyMainPage.client_name;
            clientRelation="0";
        }

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
    public static void fillFiles()
    {
        if (filesList.size()==0)
        {
            filesList.add(context.getString(R.string.med_id2));

            for (int i=0;i<APICall.allFileNumbers.size();i++){
                if(context.getResources().getString(R.string.locale).equals("en"))
                    filesList.add(APICall.allFileNumbers.get(i).getHealth_center_en()+": " + APICall.allFileNumbers.get(i).getHealth_record());
                else
                    filesList.add(APICall.allFileNumbers.get(i).getHealth_center_ar()+": " + APICall.allFileNumbers.get(i).getHealth_record());

            }
            filesList.add(context.getString(R.string.new_center));

            adapter2.notifyDataSetChanged();
        }

    }
    public static void fillRelatives()
    {
        if (relativesList.size()==0) {
            relativesList.add(context.getString(R.string.relative_name));
            for (int i = 0; i < APICall.allRelativesList.size(); i++) {
                relativesList.add(APICall.allRelativesList.get(i).getBdb_user_name());
            }
        }

        adapter.notifyDataSetChanged();

    }

}
