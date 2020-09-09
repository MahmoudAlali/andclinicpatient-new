package com.ptm.clinicpa.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;


import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.API.Constants;
import com.ptm.clinicpa.API.HintArrayAdapter;
import com.ptm.clinicpa.Activities.BeautyMainPage;
import com.ptm.clinicpa.Activities.OldAppointmentsFiltersActivity;
import com.ptm.clinicpa.Adapters.ReservationsAdapter2;
import com.ptm.clinicpa.DataModel.BookingAutomatedBrowseData;
import com.ptm.clinicpa.PayFort.PayTestActivity;
import com.ptm.clinicpa.R;
import com.savvi.rangedatepicker.CalendarPickerView;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;


import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

//import ru.dimorinny.floatingtextbutton.FloatingTextButton;

public class MyReservationFragment extends Fragment  {

    public static Fragment fragment;
    public static FragmentManager fm;
public static ImageView sortbtn;
    public static FragmentTransaction fragmentTransaction;

   public static TextView incom_reservation,accept_reservation,deposit_reservation;
//           deposited_reservation;
    public static ArrayList<BookingAutomatedBrowseData> bookingAutomatedBrowseData=new ArrayList<>();

    public static ReservationsAdapter2 reservationsAdapter2;
    public static String salonFilterOld="",dateFilterOld="",typeFilterOld="",creationDateFilterOld="",doctorNameFilterOld="",serviceFilterOld="",specialityFilterOld="";
    public static String salonFilterName="",dateFilterName="",typeFilterName="",creationDateFilterName="",doctorNameFilterName="",serviceFilterName="",specialityFilterName="";
    public static View view;
    public static String serviceId="",employee_id="",supllierName="";
    public static ImageView filterbtn;
    public static String serviceName="",empname="",startdate="",start_r_date="",bookingType="";
    public static String serviceNamefilter="",empnamefilter="";
    public static Button filter;
    public static Dialog dialog;
    public static CheckBox service_name, ref_num,service_exec_date,emp_name,service_date,service_reservation_date,book_type;
    public static SearchableSpinner cSupplierSpinner;
    public static boolean filtercheck=false;
    public static ArrayList<String> filterNames=new ArrayList<>();
    public static ArrayList<Integer> filterPostions=new ArrayList<>();
    public static String groupbooking="";
    ArrayList<String> servicesList=new ArrayList<>();
    public static boolean [] checkitems;
    public static String  REF_NUMBER="-1";

    public static String service_date_txt="",tab="1";
    public static ProgressBar progressBar;


    public static int syear,smonth,sday,eyear,emonth,eday;
    public static int sryear,srmonth,srday,eryear,ermonth,erday;

    public  static String tmp="0";
    public static TextView note_cancel;


    public  static TextView action_floating_btn;
    //    public static FloatingTextButton floatingTextButton;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.my_reseravtion_fragment, container, false);

         if (filterNames.size()==0) {
             //----------- 5 is filter num in dialog
             for (int i = 0; i <5;i++)
             {
                filterNames.add("");
                filterPostions.add(-1);
             }
         }


         //---------- for testb -----------
        Log.e("SKEY","is"+APICall.SERVER_KEY);
        Log.e("GKEY","is"+APICall.GOOGLE_KEY);
        Log.e("PKEY","is"+APICall.PROVIDER_SERVER_KEY);


        Resources res = getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(APICall.ln.toLowerCase())); // API 17+ only.
        // Use conf.locale = new Locale(...) if targeting lower versions
        res.updateConfiguration(conf, dm);
         supllierName="";

        BeautyMainPage.FRAGMENT_NAME="MYRESERVATIONFRAGMENT";
        incom_reservation=view.findViewById(R.id.incom_reservation);
        action_floating_btn=view.findViewById(R.id.action_button);
        //note_cancel=view.findViewById(R.id.note_cancel);
//        floatingTextButton=view.findViewById(R.id.action_button);
        accept_reservation=view.findViewById(R.id.accept_reservation);
        deposit_reservation=view.findViewById(R.id.deposit_reservation);
//        deposited_reservation=view.findViewById(R.id.deposited_reservation);
        filterbtn=view.findViewById(R.id.filter);
        note_cancel=view.findViewById(R.id.cancelReserMsg);
        /*if(BeautyMainPage.context.getString(R.string.locale).equals("en"))
            note_cancel.setText(Constants.cancelReservationsEn);
        else
            note_cancel.setText(Constants.cancelReservationsAr);
*/
        Toolbar toolbar;
        toolbar=view.findViewById(R.id.toolbarm);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });

      reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.myAppointments,true);
        tab="2";
        tabselected(deposit_reservation,accept_reservation,incom_reservation,true);
        fragment = new DepositReservationFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.tabs_fragment, fragment);
        fragmentTransaction.commitAllowingStateLoss();

        //region CHECK_NOTIFICATIONS
        Bundle bundle = this.getArguments();
        String book_id="";
        if (bundle != null) {
            if(bundle.getString("book_id")!=null)
                book_id = bundle.getString("book_id");
        }

        if(!book_id.equals(""))
        {

            Bundle bundle2 = new Bundle();
            bundle2.putString("book_id", book_id);
            tab="3";
            tabselected(deposit_reservation,accept_reservation,incom_reservation,true);
            fragment = new DepositReservationFragment();
            fragment.setArguments(bundle2);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.tabs_fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }

        String tab_id="";
        if (bundle != null) {
            if(bundle.getString("tab_id")!=null)

                tab_id = bundle.getString("tab_id");
        }

        if(!tab_id.equals(""))
        {
            if(tab_id.equals("10"))
            {
                tabselected(incom_reservation,deposit_reservation,accept_reservation,true);
                fragment = new AcceptedReservationFragment();
            }
            else if(tab_id.equals("7"))
            {
                tabselected(deposit_reservation,accept_reservation,incom_reservation,true);
                fragment = new DepositReservationFragment();
            }
            else if(tab_id.equals("3"))
            {
                tabselected(accept_reservation,deposit_reservation,incom_reservation,true);
                fragment = new ExecutedReservationFragment();
            }



            //  Bundle bundle2 = new Bundle();
            //  bundle2.putString("book_id", book_id);

            // fragment.setArguments(bundle2);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.tabs_fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        String execute_book_id="";
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
            Log.e("execute_book_id", "issssss:" + execute_book_id);

            Intent intent=new Intent(BeautyMainPage.context, ExecuteBookActivity.class);
            intent.putExtra("execute_book_id", execute_book_id);
            if(type.equals("4")||type.equals("5")||type.equals("6")||type.equals("7")||type.equals("8")||type.equals("9")||type.equals("11")||type.equals("12"))
                intent.putExtra("isOffer", true);
            else
                intent.putExtra("isOffer", false);

            BeautyMainPage.context.startActivity(intent);
        }


        String book_id_for_filter="";
        if (bundle != null) {
            if(bundle.getString("book_id_for_filter")!=null)
                book_id_for_filter = bundle.getString("book_id_for_filter");
        }

        if(!book_id_for_filter.equals(""))
        {

            Bundle bundle2 = new Bundle();
            bundle2.putString("book_id_for_filter", book_id_for_filter);
            Log.e("GOINGTODEPO","true");
            tabselected(accept_reservation,incom_reservation,deposit_reservation,true);
            tab="2";
            tabselected(deposit_reservation,accept_reservation,incom_reservation,true);
            fragment = new DepositReservationFragment();
            fragment.setArguments(bundle2);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.tabs_fragment, fragment);
            fragmentTransaction.commit();
        }


        //endregion



        //region FilterBtn




        filterbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(BeautyMainPage.context, OldAppointmentsFiltersActivity.class);
                BeautyMainPage.context.startActivity(i);
            }});
        //endregion


        accept_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(accept_reservation,incom_reservation,deposit_reservation,true);
                tab="3";
                fragment = new ExecutedReservationFragment();
                reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.myAppointments,false);

                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.tabs_fragment, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
        deposit_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tab="2";
                tabselected(deposit_reservation,accept_reservation,incom_reservation,true);
                fragment = new DepositReservationFragment();
                reservationsAdapter2=new ReservationsAdapter2(BeautyMainPage.context,APICall.myAppointments,true);

                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.tabs_fragment, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });

         sortbtn=MyReservationFragment.view.findViewById(R.id.sort);

        sortbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), v);
                //Inflating the Popup using xml file
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu_sort, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id=item.getItemId();
                        APICall.reservationModels.clear();
                        reservationsAdapter2.notifyDataSetChanged();
                        if (id==R.id.one){
                            APICall.sort=APICall.bookingSort("1","asc");
                            APICall.appointmentsAutomatedBrowse("en","100",MyReservationFragment.serviceId,"1",ExecutedReservationFragment.filter,APICall.sort,BeautyMainPage.context,APICall.layout,tmp,false);

                        }else if (id==R.id.two){
                            APICall.sort=APICall.bookingSort("1","desc");
                            APICall.appointmentsAutomatedBrowse("en","100",MyReservationFragment.serviceId,"1",ExecutedReservationFragment.filter,APICall.sort,BeautyMainPage.context,APICall.layout,tmp,false);
                        }else if (id==R.id.three){
                            APICall.sort=APICall.bookingSort("2","asc");
                            APICall.appointmentsAutomatedBrowse("en","100",MyReservationFragment.serviceId,"1",ExecutedReservationFragment.filter,APICall.sort,BeautyMainPage.context,APICall.layout,tmp,false);
                        }else if (id==R.id.four){
                            APICall.sort=APICall.bookingSort("2","desc");
                            APICall.appointmentsAutomatedBrowse("en","100",MyReservationFragment.serviceId,"1",ExecutedReservationFragment.filter,APICall.sort,BeautyMainPage.context,APICall.layout,tmp,false);
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
   public static void tabselected(TextView t1, TextView t2,TextView t3, Boolean check){
        try {
            if (check){
            serviceId = "";
            employee_id = "";
//            service_name.setChecked(false);
//            emp_name.setChecked(false);
            for (int i = 0; i < filterNames.size(); i++) {
                filterNames.set(i, "");
                filterPostions.set(i, -1);
            }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        t1.setTextSize(12);
       t1.setTextColor(BeautyMainPage.context.getResources().getColor(R.color.white));
       t1.setBackgroundResource(R.drawable.shadow_service_tab);
        t2.setTextSize(10);
        t2.setBackgroundResource(android.R.color.transparent);
       t2.setTextColor(BeautyMainPage.context.getResources().getColor(R.color.redClinic));
       t3.setTextSize(10);
        t3.setBackgroundResource(android.R.color.transparent);
       t3.setTextColor(BeautyMainPage.context.getResources().getColor(R.color.redClinic));

   }
    public static void updateDeposit(){
        if (tab.equals("1")) {
            fragment = new AcceptedReservationFragment();
            fm = ((AppCompatActivity) BeautyMainPage.context).getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.tabs_fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }else if (tab.equals("2")){
            fragment = new ExecutedReservationFragment ();
            fm = ((AppCompatActivity) BeautyMainPage.context).getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.tabs_fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }else if (tab.equals("3")){
            fragment = new DepositReservationFragment ();
            fm = ((AppCompatActivity) BeautyMainPage.context).getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.tabs_fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
