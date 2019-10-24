package com.dcoret.beautyclient.Activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Fragments.AccountFragment;
import com.dcoret.beautyclient.Fragments.BagReservationFragment;
import com.dcoret.beautyclient.Fragments.BagReservationTestFragment;
import com.dcoret.beautyclient.Fragments.FavoriteFragment;
import com.dcoret.beautyclient.Fragments.GroupReservationFragment;
import com.dcoret.beautyclient.Fragments.GroupReservationOthersFragment;
import com.dcoret.beautyclient.Fragments.MultiIndividualBookingReservationFragment;
import com.dcoret.beautyclient.Fragments.MyReservationFragment;
import com.dcoret.beautyclient.Fragments.NotificationFragment;
import com.dcoret.beautyclient.Fragments.PlaceServiceFragment;
import com.dcoret.beautyclient.Fragments.PlaceServiceGroupFragment;
import com.dcoret.beautyclient.Fragments.PlaceServiceMultipleBookingFragment;
import com.dcoret.beautyclient.Fragments.ReservationFragment;
import com.dcoret.beautyclient.Fragments.ServiceFragment;
import com.dcoret.beautyclient.Fragments.ServicesTabsFragment;
import com.dcoret.beautyclient.Fragments.SettingFragment;
import com.dcoret.beautyclient.R;
import com.dcoret.beautyclient.test.Main2Activity;
import com.google.firebase.messaging.FirebaseMessaging;


//------------------ main page---------------

public class BeautyMainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //---------static variables for permissions----------
    public static int ACCESS_FINE_LOCATION=90;
    public static Fragment currentFragment;
    private int READ_EXTERNAL_STORAGE=93;

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
//  ------------ not used------------
//  public static ArrayList<Cities> cities=new ArrayList();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_tabs_layout);


        FirebaseMessaging.getInstance().subscribeToTopic("Beauty");


        //------- test notificatoin-----------
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


        context=this;
        Log.e("TOKEN", APICall.gettoken(context));

        navigation=findViewById(R.id.navigation);
        mDrawerLayout=findViewById(R.id.drawer);
        layout=findViewById(R.id.fragment);

        //------------------- show Service Fragment -------------
        navigation.setSelectedItemId(R.id.services);
        fragment = new ServiceFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();


        Intent intent=getIntent();
        try {
            String tabselected = intent.getStringExtra("tabselected");
//            Log.e("tabddddd",tabselected);
            if (tabselected.equals("bag")) {
//                navigation=findViewById(R.id.navigation);
                navigation.setSelectedItemId(R.id.service_bag);
                fragment = new BagReservationTestFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();

            }
        }catch (Exception e){
            e.printStackTrace();
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mDrawerLayout.setLayoutDirection(View.LAYOUT_DIRECTION_INHERIT);
        mToggle=new ActionBarDrawerToggle(BeautyMainPage.this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //---------------get cities in background-----------------
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
    public void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)
                &&
            ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)
        ){

            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This Permission Needed because of This and That")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(BeautyMainPage.this,new String[]{
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
            ActivityCompat.requestPermissions(this,new String[]{
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
            fragmentTransaction.commit();
                FRAGMENT_NAME="";
            }else if (FRAGMENT_NAME.equals("TABS")){
                fragment = new ServicesTabsFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }else if (FRAGMENT_NAME.equals("GroupReservationFragment")){
                fragment = new PlaceServiceGroupFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }else if (FRAGMENT_NAME.equals("HairSpecificationsFragment")){
                fragment = new GroupReservationFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }else if (FRAGMENT_NAME.equals("GroupReservationResultFragment")){
                fragment = new GroupReservationFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }else if (FRAGMENT_NAME.equals("MultiIndividualBookingReservationFragment")){
                fragment = new PlaceServiceMultipleBookingFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }else if (FRAGMENT_NAME.equals("MultiBookingIndividualResult")){
                fragment = new MultiIndividualBookingReservationFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }else if (FRAGMENT_NAME.equals("ACCOUNTFRAGMENT")){
            FRAGMENT_NAME="";
            fragment = new ServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
                FRAGMENT_NAME="";
//
        }else if (FRAGMENT_NAME.equals("MAPFRAGMENTSPINNER")){
                fragment = new PlaceServiceFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }else if (FRAGMENT_NAME.equals("SETTING")){
                fragment = new ServiceFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }else if (FRAGMENT_NAME.equals("SERVICETABFRAGMENT")){
                fragment = new PlaceServiceFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                FRAGMENT_NAME="";
            }else if (FRAGMENT_NAME.equals("COMPAREFRAGMENT")){
                fragment = new ServicesTabsFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }else if (FRAGMENT_NAME.equals("INVOICEFRAGMENT")){
                fragment = new ReservationFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }else if (FRAGMENT_NAME.equals("GroupReservationOtherResultFragment")){
                fragment = new GroupReservationOthersFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }else if (FRAGMENT_NAME.equals("DELETEACCOUNT")){
                fragment = new AccountFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }else if (FRAGMENT_NAME.equals("PLACESERVICEFRAGMENT")){
                fragment = new ServiceFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                FRAGMENT_NAME="SERVICEFRAGMENT";
            }else  {
        if(navigation.getSelectedItemId()!=R.id.services){
            navigation.setSelectedItemId(R.id.services);
            fragment = new ServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
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
            fragmentTransaction.commit();
        }else if (id == R.id.setting) {
            fragment = new SettingFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
        }else if (id == R.id.compare) {
            Intent intent=new Intent(getApplicationContext(),Compartion.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
         } else if (id == R.id.help) {
            Intent intent=new Intent(getApplicationContext(), Main2Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String sharesub="Beauty";
            intent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
            intent.putExtra(Intent.EXTRA_TEXT,R.string.shareAppMessage);
            startActivity(Intent.createChooser(intent,"Share using"));
        } else if (id == R.id.favorites) {

        } else if (id == R.id.rate_app) {
            final Dialog dialog=new Dialog(this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.rating_dialog);
            final RatingBar ratingBar=dialog.findViewById(R.id.ratingBar);
            TextView ok=dialog.findViewById(R.id.ok);
            ok.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                    APICall.rateApp(ratingBar.getRating()+"","http://clientapp.dcoret.com/api/rating/rateApp",BeautyMainPage.context);
                }
            });
            dialog.show();
        }else if (id == R.id.signout) {
            new AlertDialog.Builder(context)
                    .setTitle(R.string.sigin_out)
                    .setMessage(R.string.Signout)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            APICall.logout("http://clientapp.dcoret.com/api/auth/user/logout",BeautyMainPage.context);
                        }
                    })
                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
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
                    switch (item.getItemId()) {
                        case R.id.services:
                            fragment = new ServiceFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.detach(fragment);
                            fragmentTransaction.attach(fragment);
                            fragmentTransaction.commit();
                            return true;
                        case R.id.reservations:
                            FRAGMENT_NAME="";
                            fragment = new MyReservationFragment();
//                            fragment = new ReservationFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commit();
                            return true;
                        case R.id.service_bag:
                            FRAGMENT_NAME="";
                            fragment = new BagReservationTestFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commit();
                            return true;
                        case R.id.favorites:
                            FRAGMENT_NAME="";
                           fragment = new FavoriteFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commit();
                            return true;
                        case R.id.notification:
                            FRAGMENT_NAME="";
                            fragment = new NotificationFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commit();
                            return true;
                    }

                    return false;
                }
            };
}
