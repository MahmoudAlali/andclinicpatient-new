package com.dcoret.beautyclient;

import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class BeautyMainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout layout;
    TextView service;
    View view;
    Toolbar toolbar;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         layout=findViewById(R.id.layout);
         service=findViewById(R.id.service);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
            toolbar=findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        view = View.inflate(this, R.layout.activity_main_page, null);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        searchView=findViewById(R.id.searchview);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Search.class);
                startActivity(intent);
            }
        });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Search.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
        Menu menu;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bar_menu2, menu);
        this.menu=menu;
        return true;
    }

    Boolean searchbool=false;
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

            searchbool=true;

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
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
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void mypoint(View view) {
        Intent intent=new Intent(this, Point.class);
        startActivity(intent);
    }

    public void services(View view) {

//        Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
//        Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
//
//        if(service.getVisibility()==View.INVISIBLE) {
//
//            service.startAnimation(slideUp);
//            service.setVisibility(View.VISIBLE);
//        }

        Intent intent=new Intent(this, ServiceType.class);
        startActivity(intent);

    }

    public void offers(View view) {//ابرز العروض
        Intent intent=new Intent(this,Offers.class);
        startActivity(intent);
    }

    public void myreservation(View view) {//حجوزاتي
        Intent intent=new Intent(this,Reservation.class);
        startActivity(intent);

    }


    public void manageAccount(View view) {
        Intent intent=new Intent(this,ManageAccounts.class);
        startActivity(intent);
    }

    public void extraServices(View view) {
        Intent intent=new Intent(this,ExtraServices.class);
        startActivity(intent);
    }

    public void favoriteslist(View view) {
        Intent intent=new Intent(this,FavoritesList.class);
        startActivity(intent);

    }

    public void customerServices(View view) {
        Intent intent=new Intent(this,CustomerService.class);
        startActivity(intent);
    }

}
