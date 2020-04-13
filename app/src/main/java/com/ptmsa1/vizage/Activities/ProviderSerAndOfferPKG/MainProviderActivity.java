package com.ptmsa1.vizage.Activities.ProviderSerAndOfferPKG;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Activities.MyReservations;
import com.ptmsa1.vizage.Activities.Offers;
import com.ptmsa1.vizage.Activities.TabOne;
import com.ptmsa1.vizage.Adapters.OffersAdapterTab;
import com.ptmsa1.vizage.Adapters.ServicesAdapter;
import com.ptmsa1.vizage.Adapters.ServicesProviderAdapter;
import com.ptmsa1.vizage.DataModel.BrowseServiceItem;
import com.ptmsa1.vizage.DataModel.DataOffer;
import com.ptmsa1.vizage.DataModel.SupInfoClass;
import com.ptmsa1.vizage.Fragments.AccountFragment;
import com.ptmsa1.vizage.Fragments.MapFragment;
import com.ptmsa1.vizage.Fragments.PlaceServiceFragment;
import com.ptmsa1.vizage.Fragments.Points.PointsFragment;
import com.ptmsa1.vizage.Fragments.Points.PointsSecondFragment;
import com.ptmsa1.vizage.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainProviderActivity extends AppCompatActivity {


    public static OffersAdapterTab offersAdapterTab;
    static ServicesProviderAdapter servicesProviderAdapter;
    public static ArrayList<String> mylocation = new ArrayList();
    public static double lat,lng;
    LinearLayout fra;
    android.app.FragmentTransaction fragmentTransaction;


    public static TextView service_Sw,offer_sw,date,my_location,map;
    public static RecyclerView recycleview;
    public static ArrayList<SupInfoClass> supInfoList=new ArrayList<>();
    public static   ArrayList<BrowseServiceItem>  arrayList=new ArrayList<>();
    public static   ArrayList<DataOffer>  list=new ArrayList<>();

    public static  String mylocationId="", distanceOffer=",{\"num\":2,\"value1\":0,\"value2\":100000}";

    public static String
            locOfferlat="{\"num\":34,\"value1\":"+Offers.Lat+",\"value2\":0}",
    locOfferlong="," +
            "{\"num\":35,\"value1\":"+Offers.Long+",\"value2\":0}",place_service="";
    Context context;
    public static Double lat1,lang1;
    public static String bdb_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_provider);
        BeautyMainPage.FRAGMENT_NAME="MainProviderActivity";

        context=this;


        //------ get sup id------------------
        bdb_name="\"SupplierId\":"+getIntent().getStringExtra("provider_id")+",";



        //---------- find view by Id
        service_Sw=findViewById(R.id.service_Sw);
        offer_sw=findViewById(R.id.offer_sw);
        map=findViewById(R.id.map);
        date=findViewById(R.id.date);
        my_location=findViewById(R.id.my_location);
        recycleview=findViewById(R.id.recycleview);
        fra=findViewById(R.id.fra);
//        APICall.getdetailsUser(context);

//        my_location.setText(PlaceServiceFragment.mylocationId);
        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(context,v);
                mylocation.clear();
                mylocation.add(getResources().getString(R.string.MyLocation));
                mylocation.add(getResources().getString(R.string.current_location));
                for (int i = 0; i < AccountFragment.locationTitles.size(); i++)
                    mylocation.add(AccountFragment.locationTitles.get(i).getBdb_my_descr());
                mylocation.add(getResources().getString(R.string.new_location));
                for (int i=0;i<mylocation.size();i++) {
                    popupMenu.getMenu().add(mylocation.get(i));
                }

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals(getResources().getString(R.string.current_location))) {
//                            mylocationId=item.getTitle().toString();
//                            mylocationbtn.setText(mylocationId);
                            LocationManager locationManager = (LocationManager)
                                    ((AppCompatActivity) context).getSystemService(Context.LOCATION_SERVICE);
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                // TODO: Consider calling
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.

                            }
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    lat=location.getLatitude();
                                    lng=location.getLongitude();
                                    Log.e("LATLANG",lat+":"+lng);
                                    APICall.setlocation(lat,lng);
                                    locOfferlat="{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                                    locOfferlong="," +
                                            "{\"num\":35,\"value1\":"+lng+",\"value2\":0}";

                                }
                                @Override
                                public void onStatusChanged(String provider, int status, Bundle extras) {
                                }
                                @Override
                                public void onProviderEnabled(String provider) {
                                }
                                @Override
                                public void onProviderDisabled(String provider) {
                                }

                            });
                        }else if (item.getTitle().equals(getResources().getString(R.string.new_location))){
//                            fragment = new MapFragment();
//                            fm = getActivity().getFragmentManager();
//                            fragmentTransaction = fm.beginTransaction();
//                            fragmentTransaction.replace(R.id.fragment, fragment);
//                            fragmentTransaction.commit();
//                            .FRAGMENT_NAME="SPINNER";

                        }else {
                            for (int i=0;i<AccountFragment.locationTitles.size();i++){
                                if (item.getTitle().equals(AccountFragment.locationTitles.get(i).getBdb_my_descr())){
                                    Log.e("Spinner",item.getTitle().toString());
                                    Log.e("Loction_title",AccountFragment.locationTitles.get(i).getBdb_my_descr()+":"+AccountFragment.locationTitles.get(i).getLatLng());
                                    lat=AccountFragment.locationTitles.get(i).getLatLng().latitude;
                                    PlaceServiceFragment.lat=AccountFragment.locationTitles.get(i).getLatLng().latitude;
                                    lng=AccountFragment.locationTitles.get(i).getLatLng().longitude;
                                    PlaceServiceFragment.lng=AccountFragment.locationTitles.get(i).getLatLng().longitude;
                                    Log.e("LATLANG",lat+":"+lng);
                                    APICall.setlocation(lat,lng);
                                    locOfferlat="{\"num\":34,\"value1\":"+lat+",\"value2\":0}";
                                    locOfferlong=",{\"num\":35,\"value1\":"+lng+",\"value2\":0}";
                                }
                            }
                            mylocationId=item.getTitle().toString();
                            my_location.setText(mylocationId);
                        }


                        return false;
                    }
                });


                popupMenu.show();


            }
        });
        //---------- get my locations --------
        APICall.getdetailsUser(this);

        //------- min max price------
        PlaceServiceFragment.minprice="0";
        PlaceServiceFragment.maxprice="1000000";
        //------------------  date   --------------------
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog=new Dialog(context);
                dialog.setContentView(R.layout.active_date_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                Button search=dialog.findViewById(R.id.search);
                final DatePicker datePicker=dialog.findViewById(R.id.date);
                datePicker.setMinDate(System.currentTimeMillis() - 1000);
                Calendar calendar=Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_MONTH,Offers.bdb_booking_period);
                datePicker.setMaxDate(calendar.getTimeInMillis());


                search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        int month=datePicker.getMonth()+1;
                        date.setText(datePicker.getYear()+"-"+month+"-"+datePicker.getDayOfMonth());
                        APICall.DATE_FOR_SER_OFR=date.getText().toString();
//                        dateFilter=date.getText().toString();
//                        ServiceFragment.date=dateFilter;
                    }
                });
                dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        date.setText(R.string.date);
                    }
                });
                dialog.show();
            }

        });


        //-----------------------------------------
        final int pagenum=1;

        service_Sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offer_sw.setBackgroundResource(android.R.color.transparent);
                map.setBackgroundResource(android.R.color.transparent);
                service_Sw.setBackgroundResource(R.drawable.shadow_service_tab);

                fra.setVisibility(View.GONE);
                recycleview.setHasFixedSize(true);
                servicesProviderAdapter=new ServicesProviderAdapter(context,  arrayList);
                LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
                recycleview.setLayoutManager(manager);
                recycleview.setAdapter(servicesProviderAdapter);

                APICall.automatedBrowseProvider(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "20", pagenum+"", context);


            }
        });



        offer_sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!date.getText().toString().equals(context.getResources().getString(R.string.date)) &&
                        !my_location.getText().toString().equals(context.getResources().getString(R.string.MyLocation))
                ) {
                    service_Sw.setBackgroundResource(android.R.color.transparent);
                    map.setBackgroundResource(android.R.color.transparent);
                    fra.setVisibility(View.GONE);
                    offer_sw.setBackgroundResource(R.drawable.shadow_service_tab);
                    arrayList.clear();
                    servicesProviderAdapter.notifyDataSetChanged();
                    LinearLayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recycleview.setLayoutManager(manager);
                    offersAdapterTab = new OffersAdapterTab(context, list);
                    recycleview.setAdapter(offersAdapterTab);
                    String date1 = "  ,{\"num\":13,\"value1\":\"" + date.getText().toString() + "\"}\n" +
                            "     ,{\"num\":44,\"value1\":\"" + date.getText().toString() + "\"}";

                    APICall.automatedBrowseProviderOffers("8", "1", date1, context);
                }else {
                    APICall.showSweetDialog(context,"","plese select date and location before browse offers");
                }

            }
        });


        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offer_sw.setBackgroundResource(android.R.color.transparent);
                service_Sw.setBackgroundResource(android.R.color.transparent);
                map.setBackgroundResource(R.drawable.shadow_service_tab);
                fra.setVisibility(View.VISIBLE);
                lang1=null;
                lat1=null;
                Log.e("pro_name_id","is "+getIntent().getStringExtra("provider_id"));
                for (int i=0;i<supInfoList.size();i++){
                    if (getIntent().getStringExtra("provider_id").equals(supInfoList.get(i).getId())){
                        lat1=Double.parseDouble(supInfoList.get(i).getBdb_loc_lat());
                        lang1=Double.parseDouble(supInfoList.get(i).getBdb_loc_long());
                    }
                }

                Fragment fragment = new MapTap();
//                fragment = new MapFragment();
                fragmentTransaction =  getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fra, fragment);
                fragmentTransaction.commitAllowingStateLoss();

            }
        });

        //--------------------------
        offer_sw.setBackgroundResource(android.R.color.transparent);
        map.setBackgroundResource(android.R.color.transparent);
        service_Sw.setBackgroundResource(R.drawable.shadow_service_tab);

        recycleview.setHasFixedSize(true);
        servicesProviderAdapter=new ServicesProviderAdapter(context,  arrayList);
        LinearLayoutManager manager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        recycleview.setLayoutManager(manager);
        recycleview.setAdapter(servicesProviderAdapter);


        APICall.automatedBrowseProvider(APICall.API_PREFIX_NAME+"/api/service/automatedBrowse", "en", "20", pagenum+"", context);







    }
    //------------- when refresh DATA you must notify adapter---------
    public static void refreshRV(){
        servicesProviderAdapter.notifyDataSetChanged();
//        recyclerView.invalidate();
    }
    public static void refreshRVOffers(){
        offersAdapterTab.notifyDataSetChanged();
//        recyclerView.invalidate();
    }

}
