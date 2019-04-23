package com.dcoret.beautyclient;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Compartion extends AppCompatActivity   implements NavigationView.OnNavigationItemSelectedListener {
    LinearLayout service_com_name,therd_col,service_comperation;
    TextView therd_serv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartion);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        service_com_name=findViewById(R.id.service_name_compare);
        therd_col=findViewById(R.id.therd_col_serv);
        service_comperation=findViewById(R.id.service_compare);
        therd_serv=findViewById(R.id.therd_serv_compare);
        if(ServicesAdapter.comparenum==2) {
            service_comperation.setWeightSum(3);
            therd_col.setVisibility(View.GONE);
        }else {

        }



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bar_menu3, menu);
        return true;
    }




    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

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
}
