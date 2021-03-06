package com.ptm.clinicpa.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.NewBookingRequestsFragment;
//import com.dcoret.beautyclient.Adapters.BookingRequestsAdapter;
import com.ptm.clinicpa.Activities.PersonalOrderActivity;
import com.ptm.clinicpa.Adapters.BookingRequestsAdapter;
import com.ptm.clinicpa.DataModel.BookingAutomatedBrowseData;
import com.ptm.clinicpa.DataModel.BookingRequestDataModel;
import com.ptm.clinicpa.R;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MyBookingRequestsFragment extends Fragment {
    public static Fragment fragment;
    public static FragmentManager fm;

    public static FragmentTransaction fragmentTransaction;

    public static TextView oldRequests,newRequests;
    //           deposited_reservation;
    public static ArrayList<BookingAutomatedBrowseData> bookingAutomatedBrowseData=new ArrayList<>();
    public static ArrayList<BookingRequestDataModel> bookingRequestsArraylist=new ArrayList<>();

    public static BookingRequestsAdapter bookingRequestsAdapter;

    public static View view;
    public static String serviceId="",employee_id="";
    public static ImageView filterbtn,sortbtn,addIndivRequest,addGroupRequest;
    public static String serviceName="",empname="",startdate="",start_r_date="",bookingType="",startdateCr="";
    public static Button filter;
    public static Dialog dialog;
    public static CheckBox salonName,requestDate,requestType;
    public static boolean filtercheck=false;
    ArrayList<String> servicesList=new ArrayList<>();

    public static String service_date_txt="",tab="1";


    public static int syear,smonth,sday,eyear,emonth,eday;
    public static int syearCr,smonthCr,sdayCr,eyearCr,emonthCr,edayCr;
    public static int sryear,srmonth,srday,eryear,ermonth,erday;

    public static boolean isNew=true;
    public  static String tmp="0";

    public static String salonFilter,dateFilter,typeFilter,creationDateFilter;
    public static String salonFilterTemp,dateFilterTemp,typeFilterTemp,creationDateFilterTemp;
    public static String salonFilterOld="",dateFilterOld="",typeFilterOld="",creationDateFilterOld="";
    public static String salonFilterName="",dateFilterName="",typeFilterName="",creationDateFilterName="";
    public  static ProgressBar progressBar;
    public static Fragment oldFrag,newFrag;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_booking_requests_fragment, container, false);

        //salonFilter="";dateFilter="";typeFilter="";

        BeautyMainPage.FRAGMENT_NAME="MyBookingRequestsFragment";
        oldRequests=view.findViewById(R.id.oldRequests);
        newRequests=view.findViewById(R.id.newRequests);
        filterbtn=view.findViewById(R.id.filter);
        addGroupRequest=view.findViewById(R.id.addGroupRequest);
        addIndivRequest=view.findViewById(R.id.addIndivRequest);

        Toolbar toolbar;
        toolbar=view.findViewById(R.id.toolbarm);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)BeautyMainPage.context).onBackPressed();
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });
        if (isNew)
        {
            fragment = new NewBookingRequestsFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.request_tabs_fragment, fragment);
            fragmentTransaction.commit();
            tabselected(newRequests,oldRequests,false);
        }
        else
        {
            isNew=true;
            tabselected(oldRequests,newRequests,true);
            tab="2";
            fragment = new OldBookingRequestsFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.request_tabs_fragment, fragment);
            fragmentTransaction.commit();
        }


        addIndivRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int[] location = new int[2];
                Log.e("ERR","GGGGG");
                v.getLocationOnScreen(location);
                //Initialize the Point with x, and y positions
                Point point = new Point();
                point.x = location[0];
                point.y = location[1];
                showInfoPopup(BeautyMainPage.context,point);

            }
        });
        addGroupRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeautyMainPage.FRAGMENT_NAME = "freeGroupBookingFragment";
                fragment = new RequestProvidersFragment();

                Bundle b= new Bundle();
                b.putBoolean("isGroup",true);
                fm = getFragmentManager();
                fragment.setArguments(b);
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        //region CHECK_NOTIFICATIONS
        Bundle bundle = this.getArguments();
        String order_ids="";
        if (bundle != null) {
            if(bundle.getString("order_ids")!=null)
                order_ids = bundle.getString("order_ids");
        }

        if(!order_ids.equals(""))
        {

            Bundle bundle2 = new Bundle();
            bundle2.putString("order_ids", order_ids);
            tabselected(oldRequests,newRequests,true);
            tab="2";
            fragment = new OldBookingRequestsFragment();
            fragment.setArguments(bundle2);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.request_tabs_fragment, fragment);
            fragmentTransaction.commit();
        }

        String tab_id="";
        if (bundle != null) {
            if(bundle.getString("tab_id")!=null)

                tab_id = bundle.getString("tab_id");
        }

        if(!tab_id.equals(""))
        {
            if(tab_id.equals("2"))
            {
                tabselected(oldRequests,newRequests,true);
                tab="2";
                fragment = new OldBookingRequestsFragment();
            }
            else if(tab_id.equals("1"))
            {
                tabselected(newRequests,oldRequests,true);
                tab="1";
                fragment = new NewBookingRequestsFragment();
            }




            //  Bundle bundle2 = new Bundle();
            //  bundle2.putString("book_id", book_id);

            // fragment.setArguments(bundle2);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.request_tabs_fragment, fragment);
            fragmentTransaction.commit();
        }
        /*String execute_book_id="";
        if (bundle != null) {
            if(bundle.getString("execute_book_id")!=null)
                execute_book_id = bundle.getString("execute_book_id");
        }
        String type="";
        if (bundle != null) {
            if(bundle.getString("type")!=null)
                type = bundle.getString("type");
        }

        if(!execute_book_id.equals(""))
        {

            Intent intent=new Intent(BeautyMainPage.context, ExecuteBookActivity.class);
            intent.putExtra("execute_book_id", book_id);
            if(type.equals("4")||type.equals("5")||type.equals("6")||type.equals("7")||type.equals("8")||type.equals("9")||type.equals("11")||type.equals("12"))
                intent.putExtra("isOffer", true);
            else
                intent.putExtra("isOffer", false);

            BeautyMainPage.context.startActivity(intent);
        }*/


        //endregion



        //region FilterBtn




        filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new MyBookingRequestsFilters();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();

            }


        });

        //endregion

        oldRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(oldRequests,newRequests,true);
                tab="2";
                fragment = new OldBookingRequestsFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.request_tabs_fragment, fragment);
                fragmentTransaction.commit();
            }
        });
        newRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(newRequests,oldRequests,true);
                tab="1";
                fragment = new NewBookingRequestsFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.request_tabs_fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        sortbtn =view.findViewById(R.id.sort);

        sortbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), v);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu_sort2, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();
                        APICall.reservationModels.clear();
                      //  bookingRequestsAdapter.notifyDataSetChanged();
                        if (id==R.id.one){
                            APICall.sort=APICall.bookingSort("1","asc");
                            APICall.requestsAutomatedBrowse("en", "20", MyBookingRequestsFragment.serviceId, "1", APICall.filter, APICall.sort, BeautyMainPage.context, APICall.layout,tmp,"");

                        }else if (id==R.id.two) {
                            APICall.sort = APICall.bookingSort("1", "desc");
                            APICall.requestsAutomatedBrowse("en", "20", MyBookingRequestsFragment.serviceId, "1", APICall.filter, APICall.sort, BeautyMainPage.context, APICall.layout, tmp,"");
                        }
                        Log.e("Sort1",APICall.sort);
                        Log.e("filter1",APICall.filter);
                        return true;
                    }
                });
                popup.show(); //showing popup menu
            }
        });


        return view;
    }
    public static void tabselected(TextView t1, TextView t2, Boolean check){
        try {
            if (check){
                serviceId = "";
                employee_id = "";
//            service_name.setChecked(false);
//            emp_name.setChecked(false);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        t1.setTextSize(14);
        t1.setTextColor(BeautyMainPage.context.getResources().getColor(R.color.white));
        t1.setBackgroundResource(R.drawable.shadow_service_tab);
        t2.setTextSize(12);
        t2.setBackgroundResource(android.R.color.transparent);
        t2.setTextColor(BeautyMainPage.context.getResources().getColor(R.color.redClinic));



    }

    private void showInfoPopup(final Context context, Point p) {

        // Inflate the popup_layout.xml
        final PopupWindow changeInfoPopUp = new PopupWindow(context);
        //LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.llStatusChangePopup);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.emp_info_pop_up_menu, null);
        LinearLayout indivPersonal = layout.findViewById(R.id.empServicesLayout);
        indivPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new PersonalIndivRequest();
                Bundle b=new Bundle();
                b.putBoolean("isMe",true);
                b.putBoolean("is_offer",false);
                fragment.setArguments(b);
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();

                changeInfoPopUp.dismiss();
            }
        });
        LinearLayout indivOther = layout.findViewById(R.id.empWorkingLayout);
        indivOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragment = new PersonalIndivRequest();
                Bundle b=new Bundle();
                b.putBoolean("isMe",false);
                b.putBoolean("is_offer",false);
                fragment.setArguments(b);
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();

                changeInfoPopUp.dismiss();
            }
        });
        // Creating the PopupWindow
        changeInfoPopUp.setContentView(layout);
        changeInfoPopUp.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeInfoPopUp.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        changeInfoPopUp.setFocusable(true);
        int OFFSET_X = -20;
        int OFFSET_Y = 50;
        changeInfoPopUp.setBackgroundDrawable(new BitmapDrawable());
        changeInfoPopUp.showAtLocation(layout, Gravity.NO_GRAVITY, p.x + OFFSET_X, p.y + OFFSET_Y);
    }

}
