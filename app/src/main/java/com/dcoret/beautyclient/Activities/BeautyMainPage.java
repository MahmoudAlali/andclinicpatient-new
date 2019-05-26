package com.dcoret.beautyclient.Activities;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.dcoret.beautyclient.Adapters.OffersAdapterNew;
import com.dcoret.beautyclient.Adapters.ServicesAdapterNew;
import com.dcoret.beautyclient.Fragments.AccountFragment;
import com.dcoret.beautyclient.Fragments.BagReservationFragment;
import com.dcoret.beautyclient.Fragments.FavoriteFragment;
import com.dcoret.beautyclient.Fragments.MapFragment;
import com.dcoret.beautyclient.Fragments.NotificationFragment;
import com.dcoret.beautyclient.Fragments.OfferFragment;
import com.dcoret.beautyclient.Fragments.ReservationFragment;
import com.dcoret.beautyclient.Fragments.ServiceFragment;
import com.dcoret.beautyclient.Fragments.ServicesTabsFragment2;
import com.dcoret.beautyclient.R;

public class BeautyMainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    public static String FRAGMENT_NAME="";
    LinearLayout layout;
    View view;
    Toolbar toolbar;
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mToggle;
    public static Context context;
    BottomNavigationView navigation;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_tabs_layout);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        context=this;
        navigation=findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.services);

        fragment = new ServiceFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mDrawerLayout=findViewById(R.id.drawer);
        mToggle=new ActionBarDrawerToggle(BeautyMainPage.this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


//    toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//        navigationView.setForegroundGravity(Gravity.NO_GRAVITY);
//        }
//    });
    }

    /*
    * ACCOUNTFRAGMENT
    * MAPFRAGMENT
    *
    * SERVICETABFRAGMENT
    * COMPAREFRAGMENT
    * */

    int doback=0,doback2=0;
    @Override
    public void onBackPressed() {
            if (FRAGMENT_NAME.equals("MAPFRAGMENT")) {
            Log.d("doback",2+"");
            doback=0;
            fragment = new AccountFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
                FRAGMENT_NAME="";
            }else if (FRAGMENT_NAME.equals("ACCOUNTFRAGMENT")){
            Log.d("doback",1+"");
            FRAGMENT_NAME="";
            fragment = new ServiceFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();
                FRAGMENT_NAME="";
        }else if (FRAGMENT_NAME.equals("SERVICETABFRAGMENT")){
                fragment = new ServiceFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
                FRAGMENT_NAME="";
            }else if (FRAGMENT_NAME.equals("COMPAREFRAGMENT")){
                fragment = new ServicesTabsFragment2();
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
            builder.setTitle("خروج")
                    .setMessage("هل تريد الخروج من التطبيق؟")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            BeautyMainPage.super.onBackPressed();
                        }
                    }).setNegativeButton(android.R.string.no,null);

            builder.show();
        }
            Log.d("doback","NO");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }
    }
//      public static   Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.bar_menu2, menu);
//        this.menu=menu;
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            PopupMenu popup = new PopupMenu(this,toolbar );
//            popup.getMenuInflater().inflate(R.menu.bar_menu3, popup.getMenu());
//
//            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                public boolean onMenuItemClick(MenuItem item) {
//                    Toast.makeText(getApplicationContext(), "You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
//                    return true;
//                }
//            });
//            popup.show();//showing popup menu
            return true;
        }else if(id==R.id.shoppingcart){
            Intent intent=new Intent(this,ShoppingCart.class);
            startActivity(intent);


        }else if(id==R.id.notify){
            Intent intent=new Intent(this,Notification.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.manageaccount) {
//            toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp);
            fragment = new AccountFragment();
            fm = getFragmentManager();
            fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, fragment);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        }else if (id == R.id.compare) {

            Intent intent=new Intent(getApplicationContext(),Compartion.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {

         } else if (id == R.id.help) {
            Intent intent=new Intent(getApplicationContext(),Help.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            String sharebody="تطبيق beauty لخدمات التجميل و التصميم حمله الآن من App Store";
            String sharesub="Beauty";
            intent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
            intent.putExtra(Intent.EXTRA_TEXT,sharebody);
            startActivity(Intent.createChooser(intent,"Share using"));

        } else if (id == R.id.favorites) {
            Intent intent=new Intent(getApplicationContext(),Favorites.class);
            startActivity(intent);

        } else if (id == R.id.rate_app) {
            Dialog dialog=new Dialog(this);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.setContentView(R.layout.rating_dialog);
            dialog.show();
        }else if (id == R.id.signout) {
            new AlertDialog.Builder(context)
                    .setTitle("Logout")
                    .setMessage("هل تريد تسجيل الخروج ؟")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
                            editor.remove("name"); // will delete key name
                            editor.remove("pass"); // will delete key pass
                            editor.commit();
                            Intent intent=new Intent(context,MainActivity.class);
                            MainActivity.logout=true;
                            finish();
                            startActivity(intent);

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
                            fragmentTransaction.addToBackStack("mainpage");
                            fragmentTransaction.detach(fragment);
                            fragmentTransaction.attach(fragment);
                            fragmentTransaction.commit();
                            return true;
                        case R.id.reservations:
                            FRAGMENT_NAME="";
                            fragment = new ReservationFragment();
                            fm = getFragmentManager();
                            fragmentTransaction = fm.beginTransaction();
                            fragmentTransaction.replace(R.id.fragment, fragment);
                            fragmentTransaction.commit();

                            return true;
                        case R.id.service_bag:
                            FRAGMENT_NAME="";
                            fragment = new BagReservationFragment();
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
    protected OnBackPressedListener onBackPressedListener;
    public static int dobacknumber;
    public interface OnBackPressedListener {
        int doBack();
    }
    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }



    protected OnBackPressedListener2 onBackPressedListener2;
    public interface OnBackPressedListener2 {
        int doBack();
    }
    public void setOnBackPressedListener2(OnBackPressedListener2 onBackPressedListener) {
        this.onBackPressedListener2 = onBackPressedListener2;
    }



    @Override
    protected void onDestroy() {
        onBackPressedListener = null;
        super.onDestroy();
    }


}
