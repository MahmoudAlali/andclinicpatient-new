package com.ptm.clinicpa.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ptm.clinicpa.API.APICall;
//import com.dcoret.beautyclient.Activities.support.SupportActivity;
import com.ptm.clinicpa.Activities.support.SupportActivity;
import com.ptm.clinicpa.Fragments.AccountFragment;
//import com.dcoret.beautyclient.Fragments.MyFavorites.FavoriteFragment;
import com.ptm.clinicpa.Fragments.GroupReservationFragment;
//import com.dcoret.beautyclient.Fragments.Notifications.NotificationsFragment;
import com.ptm.clinicpa.Fragments.GroupReservationOthersFragment;
import com.ptm.clinicpa.Fragments.ListServicesBrideFragment;
import com.ptm.clinicpa.Fragments.ListServicesFragment;
import com.ptm.clinicpa.Fragments.MyBookingRequestsFragment;
import com.ptm.clinicpa.Fragments.MyEffects.MyEffectsActivity;
import com.ptm.clinicpa.Fragments.MyFavorites.FavoriteFragment;
import com.ptm.clinicpa.Fragments.Notifications.NotificationsFragment;
import com.ptm.clinicpa.Fragments.Points.PointsMainFragment;
import com.ptm.clinicpa.Fragments.MultiIndividualBookingReservationFragment;
import com.ptm.clinicpa.Fragments.MyReservationFragment;
import com.ptm.clinicpa.Fragments.PlaceServiceFragment;
import com.ptm.clinicpa.Fragments.PlaceServiceGroupFragment;
import com.ptm.clinicpa.Fragments.PlaceServiceMultipleBookingFragment;
import com.ptm.clinicpa.Fragments.ReservationFragment;
import com.ptm.clinicpa.Fragments.ServiceFragment;
import com.ptm.clinicpa.Fragments.ServicesTabsFragment;
import com.ptm.clinicpa.PayFort.IPaymentRequestCallBack;
import com.ptm.clinicpa.PayFort.PayFortData;
import com.ptm.clinicpa.PayFort.PayFortPayment;
import com.ptm.clinicpa.R;
import com.google.firebase.messaging.FirebaseMessaging;
import android.widget.TextView;

import com.ptm.clinicpa.Activities.GroupOffer.SingleDateMultiClientOfferBooking;
import com.ptm.clinicpa.Activities.MultiDateOffer.MultiDateOfferBooking;
import com.ptm.clinicpa.Activities.SingleOffer.SingleDateOfferBooking;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

//------------------ main page---------------

public class BeautyMainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, IPaymentRequestCallBack {

    //---------static variables for permissions----------
    public static int ACCESS_FINE_LOCATION=90;
    public static Fragment currentFragment;
    public static String is_bride_service;
    public static String bdb_email,bdb_is_guest="0",bdb_id="";
    public static Bitmap ImageLogo;
    private int READ_EXTERNAL_STORAGE=93;
    public static Boolean RELOADAPP=false;
    public static String client_name="";
    public static String client_gender="";
    public static String client_number="";
    public static  IPaymentRequestCallBack iPaymentRequestCallBack;

    //--------------------- for call some of APIs for the first time ofopen app ---------------------
    public static Boolean isFirstOpen=true;

    public static NavigationView navigationView;
    public static String FRAGMENT_NAME="";
    LinearLayout layout;
    View view;
    public  static DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    public static Context context;
    BottomNavigationView navigation;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    public static Menu menu,sideMenu;
    NavigationView sideNavBar;
    public static TextView profileNameText;
//  ------------ not used------------
//  public static ArrayList<Cities> cities=new ArrayList();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_tabs_layout);
        context=this;

        Log.e("applanguageset",BeautyMainPage.context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("lang","klsjjlkj"));

       try {
           if (APICall.ln.equals("")|| !APICall.ln.equals("ar") || !APICall.ln.equals("en"))
         APICall.ln=getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("lang", "en");
           Resources res = getResources();
           // Change locale settings in the app.
           DisplayMetrics dm = res.getDisplayMetrics();
           android.content.res.Configuration conf = res.getConfiguration();
           conf.setLocale(new Locale(APICall.ln.toLowerCase())); // API 17+ only.
           // Use conf.locale = new Locale(...) if targeting lower versions
           res.updateConfiguration(conf, dm);
           refreshNavigationView();


       }catch (Exception e){
           e.printStackTrace();
       }
        FirebaseMessaging.getInstance().subscribeToTopic("Beauty");



        //------- test notificatoin----------

        try {
            Log.e("fbToken", FirebaseInstanceId.getInstance().getToken());
        }catch (Exception e){
            e.printStackTrace();
        }
        //        PushNotifications.sendnotification_client(BeautyMainPage.this,"","Hello","Hi","","");
        //------------------------- permissions check------------------
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
        ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
        }else {
            Log.e("requestLoc","OK");
            requestLocationPermission();
        }



        Log.e("tokeen", "token is:"+APICall.gettoken(context));
        Log.e("LastUpdate", "This");

        SharedPreferences editor = getSharedPreferences("REG_ID", MODE_PRIVATE);


        SharedPreferences settings = getSharedPreferences("LOGIN", MODE_PRIVATE);
//        if (settings.getString("client_name","").equals("")
//                || settings.getString("client_number","").equals("")) {
//            APICall.details_user(APICall.API_PREFIX_NAME+"/api/auth/user/detailsUser", context);
//        }else if(settings.getString("client_name","").equals("Guest"))
//        {
            APICall.details_user(APICall.API_PREFIX_NAME+"/api/user/detailsUser", context);
//        }else {
            client_name=settings.getString("client_name","");
            client_number=settings.getString("client_number","");
            client_gender=settings.getString("client_gender","");
            bdb_email=settings.getString("bdb_email","");

//        }



        Log.e("Tokenc",editor.getString("token_client",""));
        Log.e("client_number123",editor.getString("client_number","qwqwqwq"));
        navigationView =  findViewById(R.id.nav_view);
        navigation=findViewById(R.id.navigation);
        sideNavBar=findViewById(R.id.nav_view);
        mDrawerLayout=findViewById(R.id.drawer);
        layout=findViewById(R.id.fragment);
        menu = navigation.getMenu();
        sideMenu = sideNavBar.getMenu();
        menu.findItem(R.id.services).setIcon(R.drawable.side_requests_icon);
        menu.findItem(R.id.reservations).setIcon(R.drawable.reservations_grey);
        menu.findItem(R.id.favorites).setIcon(R.drawable.favorite_grey);
        menu.findItem(R.id.centers).setIcon(R.drawable.services_grey);
        menu.findItem(R.id.main).setIcon(R.drawable.main_grey);
        navigation.setItemIconTintList(null);
        sideNavBar.setItemIconTintList(null);

        View hView =  navigationView.getHeaderView(0);
        LogoImageView = hView.findViewById(R.id.img_profile);
        profileNameText = hView.findViewById(R.id.profile_name);

        if(client_name.equals("Guest"))
            profileNameText.setText(R.string.guestAccount);
        else
            profileNameText.setText(client_name);
        Log.e("ISGUEST :", APICall.isGuest(context));

        if(APICall.isGuest(context).equals("1"))
 //       if(bdb_is_guest.equals("1"))
        {
            Log.e("guestToken", "hhhh");
            sideMenu.findItem(R.id.signin).setVisible(true);
            sideMenu.findItem(R.id.signout).setVisible(false);
            sideMenu.findItem(R.id.manageaccount).setVisible(false);
            sideMenu.findItem(R.id.points).setVisible(false);
            sideMenu.findItem(R.id.effcts).setVisible(false);
            sideMenu.findItem(R.id.requests).setVisible(false);
            sideMenu.findItem(R.id.setting).setVisible(true);
            menu.findItem(R.id.favorites).setEnabled(false);
            menu.findItem(R.id.notification).setEnabled(false);
            menu.findItem(R.id.reservations).setEnabled(false);
        }
        else if(APICall.isGuest(context).equals("0"))
 //       else if(bdb_is_guest.equals("0"))
        {
            sideMenu.findItem(R.id.signin).setVisible(false);
            sideMenu.findItem(R.id.signout).setVisible(true);
            sideMenu.findItem(R.id.setting).setVisible(false);

        }
        Log.e("GUESTIS","isss"+APICall.isGuest(context).equals("1"));

        //------------------- show Service Fragment -------------


        Intent intent=getIntent();
//        try {
//            String tabselected = intent.getStringExtra("tabselected");
////            Log.e("tabddddd",tabselected);
//            if (tabselected.equals("bag")) {
////                navigation=findViewById(R.id.navigation);
//                navigation.setSelectedItemId(R.id.service_bag);
//                fragment = new BagReservationTestFragment();
//                fm = getFragmentManager();
//                fragmentTransaction = fm.beginTransaction();
//                fragmentTransaction.replace(R.id.fragment, fragment);
//                                fragmentTransaction.commitAllowingStateLoss();

//
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mDrawerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_INHERIT);
        mToggle=new ActionBarDrawerToggle(BeautyMainPage.this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigation.setSelectedItemId(R.id.main);

        //---------------get cities in background-----------------


        //region CHECK_NOTIFICATIONS
        String notificationTitle,notificationPairs="",code="",codeObject="";
        JSONArray j=new JSONArray();
        try
        {
            Log.e("Notif", "BeautyMain Page is trying to get pairs");

            notificationPairs=getIntent().getStringExtra("notify_pairs");
            j=new JSONArray(notificationPairs);
            code = j.getJSONObject(0).getString("code");
        }
        catch (Exception e)
        {
            Log.e("NotifErr",e.getMessage());
        }

        Log.e("NotifCode",code);
        Log.e("Notif", " pairs :"+notificationPairs);

        if(code.equals("2")||code.equals("3")||code.equals("25")||code.equals("49")||code.equals("50")||code.equals("46"))
        {
            String book_id="";
            for (int i=0;i<j.length();i++)
            {
                Log.e("Notif","i :"+i);
                try{
                    JSONObject object = j.getJSONObject(i);
                    book_id = object.getString("bdb_name_booking");
                    break;
                }
                catch (Exception e)
                {
                    Log.e("NotifErr",i+" : "+e.getMessage());

                }
            }

            Bundle bundle = new Bundle();
            bundle.putString("book_id", book_id);
            menu.findItem(R.id.services).setIcon(R.drawable.services_grey);
            menu.findItem(R.id.favorites).setIcon(R.drawable.favorite_grey);
            menu.findItem(R.id.notification).setIcon(R.drawable.notifications_grey);
            menu.findItem(R.id.main).setIcon(R.drawable.main_selected);
            menu.findItem(R.id.reservations).setIcon(R.drawable.reservations_selected);
            navigation.setSelectedItemId(R.id.reservations);
            FRAGMENT_NAME="";
            fragment = new MyReservationFragment();
            fragment.setArguments(bundle);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        else if(code.equals("20"))
        {
            //Bundle bundle = new Bundle();
            fragment = new PointsMainFragment();
            //fragment.setArguments(bundle);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();

        }

       /* if(code.equals("32"))
        {
            Bundle bundle = new Bundle();
            bundle.putString("tab_id", "2");
            menu.findItem(R.id.reservations).setIcon(R.drawable.reservations_selected);
            FRAGMENT_NAME="";
            fragment = new MyReservationFragment();
            fragment.setArguments(bundle);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }*/

        else if(code.equals("22")||code.equals("32")||code.equals("40")||code.equals("52"))
        {
            String tab_id="";
            for (int i=0;i<j.length();i++)
            {
                Log.e("Notif","i :"+i);
                try{
                    JSONObject object = j.getJSONObject(i);
                    tab_id = object.getString("filter");
                    break;
                }
                catch (Exception e)
                {
                    Log.e("NotifErr",i+" : "+e.getMessage());

                }
            }
            Bundle bundle = new Bundle();
            bundle.putString("tab_id", tab_id);
            menu.findItem(R.id.services).setIcon(R.drawable.services_grey);
            menu.findItem(R.id.favorites).setIcon(R.drawable.favorite_grey);
            menu.findItem(R.id.notification).setIcon(R.drawable.notifications_grey);
            menu.findItem(R.id.main).setIcon(R.drawable.main_selected);
            menu.findItem(R.id.reservations).setIcon(R.drawable.reservations_selected);
            navigation.setSelectedItemId(R.id.reservations);
            FRAGMENT_NAME="";
            fragment = new MyReservationFragment();
            fragment.setArguments(bundle);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        else if(code.equals("16")||code.equals("18"))
        {
            String bdb_offer_type="";
            for (int i=0;i<j.length();i++)
            {
                Log.e("Notif","i :"+i);
                try{
                    JSONObject object = j.getJSONObject(i);
                    bdb_offer_type = object.getString("bdb_offer_type");
                    break;
                }
                catch (Exception e)
                {
                    Log.e("NotifErr",i+" : "+e.getMessage());

                }
            }
            String bdb_pack_id="";
            for (int i=0;i<j.length();i++)
            {
                Log.e("Notif","i :"+i);
                try{
                    JSONObject object = j.getJSONObject(i);
                    bdb_pack_id = object.getString("bdb_pack_code");
                    break;
                }
                catch (Exception e)
                {
                    Log.e("NotifErr",i+" : "+e.getMessage());

                }
            }

            String is_effect_on="";
            for (int i=0;i<j.length();i++)
            {
                Log.e("Notif","i :"+i);
                try{
                    JSONObject object = j.getJSONObject(i);
                    is_effect_on = object.getString("is_effects_on");
                    break;
                }
                catch (Exception e)
                {
                    Log.e("NotifErr",i+" : "+e.getMessage());

                }
            }
            String offer_end="";
            for (int i=0;i<j.length();i++)
            {
                Log.e("Notif","i :"+i);
                try{
                    JSONObject object = j.getJSONObject(i);
                    offer_end = object.getString("bdb_offer_end");
                    break;
                }
                catch (Exception e)
                {
                    Log.e("NotifErr",i+" : "+e.getMessage());

                }
            }

            String booking_period="";
            for (int i=0;i<j.length();i++)
            {
                Log.e("Notif","i :"+i);
                try{
                    JSONObject object = j.getJSONObject(i);
                    booking_period = object.getString("bdb_booking_period");
                    break;
                }
                catch (Exception e)
                {
                    Log.e("NotifErr",i+" : "+e.getMessage());

                }
            }
            FRAGMENT_NAME="";

            if (bdb_offer_type.equals("2")
                    || bdb_offer_type.equals("5")){

                Intent intent2=new Intent(context, MultiDateOfferBooking.class);
                intent2.putExtra("bdb_pack_id",bdb_pack_id);
                intent2.putExtra("notification","true");
                intent2.putExtra("is_effects_on",is_effect_on);
                intent2.putExtra("offer_end",offer_end);
                intent2.putExtra("offertype",bdb_offer_type);
                intent2.putExtra("booking_period",booking_period);
                ((AppCompatActivity)context).startActivity(intent2);

            }else if (bdb_offer_type.equals("1")
                    || bdb_offer_type.equals("4")){
                Intent  intent3=new Intent(context, SingleDateOfferBooking.class);
                intent3.putExtra("bdb_pack_id",bdb_pack_id);
                intent3.putExtra("notification","true");
                intent3.putExtra("is_effects_on",is_effect_on);
                intent3.putExtra("offer_end",offer_end);
                intent3.putExtra("offertype",bdb_offer_type);
                intent3.putExtra("booking_period",booking_period);
                ((AppCompatActivity)context).startActivity(intent3);
            }else if (bdb_offer_type.equals("3")
                    || bdb_offer_type.equals("6")){

                Intent  intent4=new Intent(context, SingleDateMultiClientOfferBooking.class);
                intent4.putExtra("bdb_pack_id",bdb_pack_id);
                intent4.putExtra("notification","true");
                intent4.putExtra("notification","true");
                intent4.putExtra("is_effects_on",is_effect_on);
                intent4.putExtra("offer_end",offer_end);
                intent4.putExtra("offertype",bdb_offer_type);
                intent4.putExtra("booking_period",booking_period);
                ((AppCompatActivity)context).startActivity(intent4);
            }
        }
        else if(code.equals("15"))
        {
            String book_id="";
            for (int i=0;i<j.length();i++)
            {
                Log.e("Notif","i :"+i);
                try{
                    JSONObject object = j.getJSONObject(i);
                    book_id = object.getString("bdb_name_booking");
                    break;
                }
                catch (Exception e)
                {
                    Log.e("NotifErr",i+" : "+e.getMessage());

                }
            }
            String type="";
            for (int i=0;i<j.length();i++)
            {
                Log.e("Notif","i :"+i);
                try{
                    JSONObject object = j.getJSONObject(i);
                    type = object.getString("booking_type");
                    break;
                }
                catch (Exception e)
                {
                    Log.e("NotifErr",i+" : "+e.getMessage());

                }
            }

            Bundle bundle = new Bundle();
            Log.e("execute_book_id", "is:" + book_id);

            bundle.putString("execute_book_id", book_id);
            bundle.putString("type", type);
            menu.findItem(R.id.services).setIcon(R.drawable.services_grey);
            menu.findItem(R.id.favorites).setIcon(R.drawable.favorite_grey);
            menu.findItem(R.id.notification).setIcon(R.drawable.notifications_grey);
            menu.findItem(R.id.main).setIcon(R.drawable.main_selected);
            menu.findItem(R.id.reservations).setIcon(R.drawable.reservations_selected);
            navigation.setSelectedItemId(R.id.reservations);
            FRAGMENT_NAME="";
            fragment = new MyReservationFragment();
            fragment.setArguments(bundle);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
        else if(code.equals("45"))
        {
            String order_id="";
            for (int i=0;i<j.length();i++)
            {
                Log.e("Notif","i :"+i);
                try{
                    JSONObject object = j.getJSONObject(i);
                    order_id = object.getString("bdb_id");
                    break;
                }
                catch (Exception e)
                {
                    Log.e("NotifErr",i+" : "+e.getMessage());

                }
            }

            Bundle bundle = new Bundle();
            bundle.putString("order_id", order_id);
            FRAGMENT_NAME="";
            fragment = new MyBookingRequestsFragment();
            fragment.setArguments(bundle);
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }

        else {
            Log.e("ELSSSSe","go to else");
           /* fragment = new Offers();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();*/

        }
        //endregion

        //region CHECK_REQUEST
        boolean goToRequests;
        try
        {
            Log.e("Notif", "goToRequests");

            goToRequests=getIntent().getBooleanExtra("goToRequests",false);
            if (goToRequests==true)
            {
                fragment = new MyBookingRequestsFragment();
                //fragment.setArguments(bundle);
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }

        }
        catch (Exception e)
        {
            Log.e("goToRequests",e.getMessage());
        }

        //endregion
        //region CHECK_REQ_TO_RESERVATION
        String goToReservation;
        try
        {
            Log.e("Notif", "goToReservation");

            goToReservation=getIntent().getStringExtra("goToReservation");
            if (!goToReservation.equals(""))
            {
                Bundle bundle = new Bundle();
                bundle.putString("book_id", goToReservation);
                menu.findItem(R.id.services).setIcon(R.drawable.services_grey);
                menu.findItem(R.id.favorites).setIcon(R.drawable.favorite_grey);
                menu.findItem(R.id.notification).setIcon(R.drawable.notifications_grey);
                menu.findItem(R.id.main).setIcon(R.drawable.main_selected);
                menu.findItem(R.id.reservations).setIcon(R.drawable.reservations_selected);
                navigation.setSelectedItemId(R.id.reservations);
                FRAGMENT_NAME="";
                fragment = new MyReservationFragment();
                fragment.setArguments(bundle);
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }

        }
        catch (Exception e)
        {
            Log.e("goToReservation",e.getMessage());
        }

        //endregion




        //        APICall.getcities("http://clientapp.dcoret.com/api/auth/user/getCities",BeautyMainPage.context);

        cutdownBagReservation();
    }

   //-----------  refresh bag reservation every 5 minutes
    public void cutdownBagReservation(){
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
//                Toast.makeText(context,"seconds remaining: " + millisUntilFinished / 1000,Toast.LENGTH_SHORT).show();
//                mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
//                APICall.getCart(BeautyMainPage.context,false);
//                mTextField.setText("done!");
                cutdownBagReservation();
            }
        }.start();
    }

    //---- request permission for application =------------
    public static void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) BeautyMainPage.context,Manifest.permission.ACCESS_COARSE_LOCATION)
                &&
            ActivityCompat.shouldShowRequestPermissionRationale((Activity) BeautyMainPage.context,Manifest.permission.ACCESS_COARSE_LOCATION)
        ){

            new AlertDialog.Builder((Activity) BeautyMainPage.context)
                    .setTitle("Permission Needed")
                    .setMessage("This Permission Needed because of This and That")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) BeautyMainPage.context,new String[]{
                                    Manifest.permission.ACCESS_FINE_LOCATION
                                    , Manifest.permission.ACCESS_COARSE_LOCATION
                            },ACCESS_FINE_LOCATION);
                        }
                    })
                    .setNegativeButton(R.string.CancelAlert, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        }
                    }).create().show();
        }else {
            ActivityCompat.requestPermissions((Activity) BeautyMainPage.context,new String[]{
                     Manifest.permission.ACCESS_FINE_LOCATION
                    ,Manifest.permission.ACCESS_COARSE_LOCATION
                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ,Manifest.permission.READ_EXTERNAL_STORAGE
                    ,Manifest.permission.READ_PHONE_STATE
                    ,Manifest.permission.ACCESS_NETWORK_STATE
                    },ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==ACCESS_FINE_LOCATION){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Permission Granted",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"Permission Denied",Toast.LENGTH_LONG).show();
            }
        }
    }

    /*
    * ACCOUNTFRAGMENT
    * MAPFRAGMENT
    * SERVICETABFRAGMENT
    * COMPAREFRAGMENT
    * DELETEACCOUNT
    * */

    //------------- When press Back or nav back in navbar--------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }else
        if (FRAGMENT_NAME.equals("MAPFRAGMENT")) {
            fragment = new AccountFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

            FRAGMENT_NAME="";
        }else if (FRAGMENT_NAME.equals("TABS")){
            fragment = new ServicesTabsFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }else if (FRAGMENT_NAME.equals("GroupReservationFragment")){
            fragment = new PlaceServiceGroupFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }else if (FRAGMENT_NAME.equals("HairSpecificationsFragment")){
            fragment = new GroupReservationFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }else if (FRAGMENT_NAME.equals("GroupReservationResultFragment")){
            fragment = new GroupReservationFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }else if (FRAGMENT_NAME.equals("ListServicesFragment")){
            fragment = new ServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

            APICall.sendFavorites(context,ListServicesFragment.getFavorites(APICall.itemArrayList));

        }else if (FRAGMENT_NAME.equals("PointsFragment")){
            fragment = new ServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();


            //        PLACESERVICEFRAGMENT
//                PLACESERVICEFRAGMENTBRIDE
//        PLACESERVICEFRAGMENTOTHER
//                PLACESERVICEFRAGMENTOTHER
//        PLACESERVICEFRAGMENTBRIDEOTHER
//                multiple_individual_booking
//        multiple_individual_booking_bride

        }else if (
                FRAGMENT_NAME.equals("PLACESERVICEFRAGMENT") ||
                        FRAGMENT_NAME.equals("PLACESERVICEFRAGMENTBRIDE") ||
                        FRAGMENT_NAME.equals("PLACESERVICEFRAGMENTOTHER") ||
                        FRAGMENT_NAME.equals("PLACESERVICEFRAGMENTOTHER") ||
                        FRAGMENT_NAME.equals("PLACESERVICEFRAGMENTBRIDEOTHER") ||
                        FRAGMENT_NAME.equals("PLACESERVICEFRAGMENTOTHER") ||
                        FRAGMENT_NAME.equals("multiple_individual_booking") ||
                        FRAGMENT_NAME.equals("multiple_individual_booking_bride")
        ){
            fragment = new ServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }else if (FRAGMENT_NAME.equals("MultiIndividualBookingReservationFragment")){
            fragment = new PlaceServiceMultipleBookingFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }else if (FRAGMENT_NAME.equals("MultiBookingIndividualResult")){
            fragment = new MultiIndividualBookingReservationFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }else if (FRAGMENT_NAME.equals("ACCOUNTFRAGMENT")){
            FRAGMENT_NAME="";
            fragment = new ServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

            navigation.setSelectedItemId(R.id.services);
//
        }else if (FRAGMENT_NAME.equals("MAPFRAGMENTSPINNER")){
            fragment = new PlaceServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }else if (FRAGMENT_NAME.equals("SETTING")){
            fragment = new ServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }else if (FRAGMENT_NAME.equals("SERVICETABFRAGMENT")){
            fragment = new PlaceServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

            FRAGMENT_NAME="";
        }else if (FRAGMENT_NAME.equals("COMPAREFRAGMENT")){
            fragment = new ServicesTabsFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }else if (FRAGMENT_NAME.equals("INVOICEFRAGMENT")){
            fragment = new ReservationFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }else if (FRAGMENT_NAME.equals("GroupReservationOtherResultFragment")){
            fragment = new GroupReservationOthersFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        }else if (FRAGMENT_NAME.equals("DELETEACCOUNT")){
            fragment = new AccountFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

        }else if (FRAGMENT_NAME.equals("ListServicesBrideFragment")){
            fragment = new ServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

            APICall.sendFavorites(context,ListServicesFragment.getFavorites(APICall.itemArrayList));
        }else if (FRAGMENT_NAME.equals("PLACESERVICEFRAGMENT")){
            if (is_bride_service.equals("1")){
                fragment = new ListServicesBrideFragment();
            }else {
                fragment = new ListServicesFragment();
            }
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commitAllowingStateLoss();

            FRAGMENT_NAME="SERVICEFRAGMENT";
        }else  {
            if(navigation.getSelectedItemId()!=R.id.services){
                navigation.setSelectedItemId(R.id.services);
                fragment = new ServiceFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commitAllowingStateLoss();

            }else {
                AlertDialog.Builder builder=  new AlertDialog.Builder(context);
                builder.setTitle(R.string.Exit)
                        .setMessage(R.string.ExitMessage)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                System.exit(0);
                            }
                        }).setNegativeButton(android.R.string.no,null);
                builder.show();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    //------------------show interface fragment depend on item selected from nav drawer-------------
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.manageaccount) {
            fragment = new AccountFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commitAllowingStateLoss();


        }else if (id == R.id.setting) {
            Intent intent=new Intent(this, AccountFragment.class);
            startActivity(intent);

        }else if (id == R.id.points) {
            fragment = new PointsMainFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();

        }else if (id == R.id.compare) {
            Intent intent=new Intent(getApplicationContext(),Compartion.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
        } else if (id == R.id.nav_share) {

            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String sharesub="Beauty";
            intent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
            intent.putExtra(Intent.EXTRA_TEXT,R.string.shareAppMessage);
            startActivity(Intent.createChooser(intent,context.getResources().getString(R.string.share)));


        } else if (id == R.id.support) {
            Intent intent=new Intent(getApplicationContext(), SupportActivity.class);
            startActivity(intent);
        }else if (id == R.id.effcts) {
            Intent intent=new Intent(getApplicationContext(), MyEffectsActivity.class);
            startActivity(intent);
        }else if (id == R.id.requests) {

            fragment = new ServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.detach(fragment);
            fragmentTransaction.attach(fragment);
            fragmentTransaction.commitAllowingStateLoss();
       /* } else if (id == R.id.rate_app) {
         launchMarket();*/
        }else if (id == R.id.notification) {

            fragment = new NotificationsFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.detach(fragment);
            fragmentTransaction.attach(fragment);
            fragmentTransaction.commitAllowingStateLoss();

       /* } else if (id == R.id.rate_app) {
         launchMarket();*/
        }else if (id == R.id.signout) {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.sign_out)
                    .setMessage(R.string.Signout)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            APICall.logout(APICall.API_PREFIX_NAME+"/api/user/logout",BeautyMainPage.context);
                        }
                    })
                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }else if (id == R.id.signin) {
            Intent intent=new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    //------------- when click nav bottom (Service,reservations......)
    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    menu.findItem(R.id.services).setIcon(R.drawable.services_grey);
                    menu.findItem(R.id.reservations).setIcon(R.drawable.reservations_grey);
                    menu.findItem(R.id.favorites).setIcon(R.drawable.favorite_grey);
                    menu.findItem(R.id.centers).setIcon(R.drawable.services_grey);
                    menu.findItem(R.id.main).setIcon(R.drawable.main_selected);

                    switch (item.getItemId()) {
                        case R.id.services:
                            if (RELOADAPP){

                                finish();
                                RELOADAPP=false;
                                startActivity(getIntent());
                            }else {
                                menu.findItem(R.id.services).setIcon(R.drawable.services_selected);
                                fragment = new MyReservationFragment();
                                fm = getFragmentManager();
                                fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment, fragment);
                                fragmentTransaction.commitAllowingStateLoss();

                            }
                            return true;
                        case R.id.reservations:
                            if (RELOADAPP){

                                finish();
                                RELOADAPP=false;
                                startActivity(getIntent());
                            }else {
                                menu.findItem(R.id.reservations).setIcon(R.drawable.reservations_selected);
                                FRAGMENT_NAME = "";
                                fragment = new MyBookingRequestsFragment();
                                fm = getFragmentManager();
                                fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment, fragment);
                                fragmentTransaction.commit();

                            }
                            return true;
//                        case R.id.service_bag:
//                            FRAGMENT_NAME="";
//                            fragment = new BagReservationTestFragment();
//                            fm = getFragmentManager();
//                            fragmentTransaction = fm.beginTransaction();
//                            fragmentTransaction.replace(R.id.fragment, fragment);
//                                            fragmentTransaction.commitAllowingStateLoss();

//                            return true;
                        case R.id.favorites:
                            FRAGMENT_NAME="";
                            if (RELOADAPP){

                                finish();
                                                                RELOADAPP=false;
                                startActivity(getIntent());
                            }else {
                                menu.findItem(R.id.favorites).setIcon(R.drawable.favorite_selected);

                                fragment = new FavoriteFragment();
                                fm = getFragmentManager();
                                fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment, fragment);
                                fragmentTransaction.commitAllowingStateLoss();
                            }
                            return true;
                        case R.id.centers:
                            FRAGMENT_NAME="";
                            if (RELOADAPP){

                                finish();
                                RELOADAPP=false;
                                startActivity(getIntent());
                            }else {
                                menu.findItem(R.id.centers).setIcon(R.drawable.services_selected);
                                fragment = new HealthCentersFilters();
                                fm = getFragmentManager();
                                fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment, fragment);
                                fragmentTransaction.commitAllowingStateLoss();


                            }
                            return true;
                        case R.id.main:
                            if (RELOADAPP){

                                finish();
                                RELOADAPP=false;
                                startActivity(getIntent());
                            }else {
                                FRAGMENT_NAME = "";
                                menu.findItem(R.id.main).setIcon(R.drawable.main_grey);
                                fragment = new Offers();
                                fm = getFragmentManager();
                                fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment, fragment);
                                fragmentTransaction.commitAllowingStateLoss();
                            }
                            return true;
                    }

                    return false;
                }
            };

    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + "com.ubnt.umobile");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onPaymentRequestResponse(int responseType, PayFortData responseData) {
        if (responseType == PayFortPayment.RESPONSE_GET_TOKEN) {
            Toast.makeText(this, "Token not generated", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Token not generated");
        } else if (responseType == PayFortPayment.RESPONSE_PURCHASE_CANCEL) {
            Toast.makeText(this, "Payment cancelled", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment cancelled");
            APICall.getPurchaseResponseFromFrontEnd(context,"","","","","","","","","","","","","","","","","","","","1");

        } else if (responseType == PayFortPayment.RESPONSE_PURCHASE_FAILURE) {
            Toast.makeText(this, "Payment failed", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment failed");
        } else {
            Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();
            Log.e("onPaymentResponse", "Payment successful");

            APICall.getPurchaseResponseFromFrontEnd(context,"","","","","","","","","","","","","","","","","","","","0");

        }
    }
    private void refreshNavigationView(){

        setContentView(R.layout.services_tabs_layout);
         navigationView =  findViewById(R.id.nav_view);

    }
    public  static  ImageView LogoImageView;
    public static void setLogo(Bitmap bitmap) {
        LogoImageView.setImageBitmap(bitmap);   //MyPhoto is image control.

    }
    public static Bitmap getLogo(){
        return  ImageLogo;
    }
}