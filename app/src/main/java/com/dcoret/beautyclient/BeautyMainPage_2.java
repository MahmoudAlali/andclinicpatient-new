package com.dcoret.beautyclient;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.Fragments.AccountFragment;
import com.dcoret.beautyclient.Fragments.MoreFragment;
import com.dcoret.beautyclient.Fragments.OfferFragment;
import com.dcoret.beautyclient.Fragments.ReservationFragment;
import com.dcoret.beautyclient.Fragments.ServiceFragment;

public class BeautyMainPage_2 extends AppCompatActivity
//        implements NavigationView.OnNavigationItemSelectedListener
{

   public static Context context;
    Dialog dialog=null ;
    LinearLayout offers,service,reservation,account,frag;
    LinearLayout map_layout,shopping_cart_layout,notification_layout,more_layout;
    FloatingActionButton fab;
    TextView offer_text,account_text,service_text,reservation_text;
    Boolean isFABOpen=false;
    FloatingActionButton map,shopping_cart,notification,more;
    TextView map_text,shopping_cart_text,notification_text,more_text;
    CoordinatorLayout coor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beauty_main_page_2);
        Toolbar toolbar=findViewById(R.id.bottom_app_bar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);
        context=this;
        offers=findViewById(R.id.offers);
        service=findViewById(R.id.services);
        reservation=findViewById(R.id.reservation);
        account=findViewById(R.id.account);
        frag=findViewById(R.id.fragment);
        coor=findViewById(R.id.coor);
        fab=findViewById(R.id.fab);


        offer_text=findViewById(R.id.offer_text);
        service_text=findViewById(R.id.service_text);
        reservation_text=findViewById(R.id.reservation_text);
        account_text=findViewById(R.id.account_text);


        shopping_cart_text=findViewById(R.id.shopping_cart_text);
        more_text=findViewById(R.id.more_text);
        notification_text=findViewById(R.id.notification_text);
        map_text=findViewById(R.id.map_text);

        shopping_cart_layout=findViewById(R.id.shopping_cart_layout);
        more_layout=findViewById(R.id.more_layout);
        notification_layout=findViewById(R.id.notification_layout);
        map_layout=findViewById(R.id.map_layout);

        offer_text.setTextColor(Color.MAGENTA);
        service_text.setTextColor(Color.WHITE);
        reservation_text.setTextColor(Color.WHITE);
        account_text.setTextColor(Color.WHITE);

        fragment = new OfferFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();





//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
////        view = View.inflate(this, R.layout.activity_main_page, null);
//        toggle.syncState();
//
//
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);









        frag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFABOpen){
                    closeFABMenu();
                }
            }
        });

        map = (FloatingActionButton) findViewById(R.id.map);
        shopping_cart = (FloatingActionButton) findViewById(R.id.shopping_cart);
        notification = (FloatingActionButton) findViewById(R.id.notification);
        more = (FloatingActionButton) findViewById(R.id.more);





        coor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFABOpen){
                    closeFABMenu();
                }
            }
        });






        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                ObjectAnimator.ofFloat(fab, "rotation", 0f, 360f).setDuration(800).start();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isFABOpen){
                            showFABMenu();
                            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_close_black_24dp));

                        } else {
                            closeFABMenu();
                            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_add_black_24dp));

                        }
                    }
                }, 400);
//                if(!isFABOpen){
//                    showFABMenu();
////                    WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
////                    WindowManager.LayoutParams p = (WindowManager.LayoutParams) frag.getLayoutParams();
////                    p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
////                    p.dimAmount = 0.4f;
////                    wm.updateViewLayout(frag, p);
//
////                    fab.setBackgroundResource(R.drawable.ic_close_black_24dp);
//
//
//                }else{
//                    closeFABMenu();
//                    frag.getBackground().setAlpha(255);
////                    fab.setBackgroundResource(R.drawable.ic_add_black_24dp);
//
//
//                }
            }
        });
        shopping_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent intent=new Intent(getApplicationContext(),ShoppingCart.class);
                startActivity(intent);

            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent intent=new Intent(getApplicationContext(),Notification.class);
                startActivity(intent);

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
                Intent intent=new Intent(getApplicationContext(),MapFiltering.class);
                startActivity(intent);

            }
        });



        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
//                Intent intent=new Intent(getApplicationContext(),More.class);
//                finish();
//                startActivity(intent);
//        Toast.makeText(this, "account tab", Toast.LENGTH_SHORT).show();
                offer_text.setTextColor(Color.WHITE);
                service_text.setTextColor(Color.WHITE);
                reservation_text.setTextColor(Color.WHITE);
                account_text.setTextColor(Color.WHITE);

                fragment = new MoreFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();

            }
        });

    }



    private void showFABMenu(){
        isFABOpen=true;

        coor.setBackgroundColor(Color.GRAY);
        frag.setVisibility(View.INVISIBLE);
        map_layout.animate().translationX(-getResources().getDimension(R.dimen.standard_105));
        map_layout.animate().translationY(-getResources().getDimension(R.dimen.standard_40));
        map_text.setText("خريطة");
        shopping_cart_layout.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        shopping_cart_layout.animate().translationX(-getResources().getDimension(R.dimen.standard_40));
        shopping_cart_text.setText("السلة");
        notification_layout.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        notification_layout.animate().translationX(getResources().getDimension(R.dimen.standard_40));
        notification_text.setText("الاشعارات");
        more_layout.animate().translationX(getResources().getDimension(R.dimen.standard_105));
        more_layout.animate().translationY(-getResources().getDimension(R.dimen.standard_40));
        more_text.setText("المزيد");
//        fab.setImageResource(R.drawable.ic_close_black_24dp);




    }

    private void closeFABMenu(){
        isFABOpen=false;

        coor.setBackgroundColor(Color.TRANSPARENT);
        frag.setVisibility(View.VISIBLE);
        map_layout.animate().translationY(0);
        map_layout.animate().translationX(0);
        map_text.setText("");
        shopping_cart_layout.animate().translationY(0);
        shopping_cart_layout.animate().translationX(0);
        shopping_cart_text.setText("");
        notification_layout.animate().translationY(0);
        notification_layout.animate().translationX(0);
        notification_text.setText("");
        more_layout.animate().translationY(0);
        more_layout.animate().translationX(0);
        more_text.setText("");
        fab.setImageResource(R.drawable.ic_add_black_24dp);

    }

    public void animateFAB() {

        if (isFABOpen) {
            Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_forward);
            fab.startAnimation(animation);
//            fab1.startAnimation(fab_close);
//            fab2.startAnimation(fab_close);
//            fab1.setClickable(false);
//            fab2.setClickable(false);
//            isFABOpen = false;
            Log.d("Raj", "close");

        } else {
            Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate_backward);

            fab.startAnimation(animation);
//            fab1.startAnimation(fab_open);
//            fab2.startAnimation(fab_open);
//            fab1.setClickable(true);
//            fab2.setClickable(true);
//            isFABOpen = true;
            Log.d("Raj", "open");

        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.bar_menu3, menu);
//
//        return true;
//    }
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    public void offers(View view) {
//        Toast.makeText(this, "offers tab", Toast.LENGTH_SHORT).show();
        offer_text.setTextColor(Color.MAGENTA);
        service_text.setTextColor(Color.WHITE);
        reservation_text.setTextColor(Color.WHITE);
        account_text.setTextColor(Color.WHITE);
        fragment = new OfferFragment();

//        mDrawable.setColorFilter(new
//                PorterDuffColorFilter(Color.MAGENTA, PorterDuff.Mode.MULTIPLY));   Drawable mDrawable = context.getResources().getDrawable(R.drawable.ic_leaf);


        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
        closeFABMenu();

    }

    public void service(View view) {
        closeFABMenu();
        offer_text.setTextColor(Color.WHITE);
        service_text.setTextColor(Color.MAGENTA);
        reservation_text.setTextColor(Color.WHITE);
        account_text.setTextColor(Color.WHITE);

        fragment = new ServiceFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    public void reservation(View view) {
        closeFABMenu();
//        Toast.makeText(this, "reservation tab", Toast.LENGTH_SHORT).show();
        offer_text.setTextColor(Color.WHITE);
        service_text.setTextColor(Color.WHITE);
        reservation_text.setTextColor(Color.MAGENTA);
        account_text.setTextColor(Color.WHITE);

        fragment = new ReservationFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    public void account(View view) {
        closeFABMenu();
//        Toast.makeText(this, "account tab", Toast.LENGTH_SHORT).show();
        offer_text.setTextColor(Color.WHITE);
        service_text.setTextColor(Color.WHITE);
        reservation_text.setTextColor(Color.WHITE);
        account_text.setTextColor(Color.MAGENTA);

        fragment = new AccountFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        }else if (id == R.id.compare) {
//
//            Intent intent=new Intent(getApplicationContext(),Compartion.class);
//            startActivity(intent);
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.help) {
//            Intent intent=new Intent(getApplicationContext(),Help.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_share) {
//            Intent intent=new Intent(Intent.ACTION_SEND);
//            intent.setType("text/plain");
//            String sharebody="تطبيق beauty لخدمات التجميل و التصميم حمله الآن من App Store";
//            String sharesub="Beauty";
//            intent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
//            intent.putExtra(Intent.EXTRA_TEXT,sharebody);
//            startActivity(Intent.createChooser(intent,"Share using"));
//
//        } else if (id == R.id.favorites) {
//            Intent intent=new Intent(getApplicationContext(),Favorites.class);
//            startActivity(intent);
//
//        } else if (id == R.id.rate_app) {
//            Dialog dialog=new Dialog(this);
//            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//            dialog.setContentView(R.layout.rating_dialog);
//            dialog.show();
//        }else if (id == R.id.signout) {
//            new AlertDialog.Builder(context)
//                    .setTitle("Logout")
//                    .setMessage("هل تريد تسجيل الخروج ؟")
//                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
//                            editor.remove("name"); // will delete key name
//                            editor.remove("pass"); // will delete key pass
//                            editor.commit();
//                            Intent intent=new Intent(context,MainActivity.class);
//                            MainActivity.logout=true;
//                            startActivity(intent);
//
//                        }
//                    })
//
//                    // A null listener allows the button to dismiss the dialog and take no further action.
//                    .setNegativeButton(android.R.string.no, null)
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
}
