package com.ptm.clinicpa.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptm.clinicpa.DataModel.FilterAndSortModel;
import com.ptm.clinicpa.DataModel.ServiceFilter;
import com.ptm.clinicpa.R;

import java.util.ArrayList;
import java.util.Locale;

public class SettingActivity extends AppCompatActivity {
    LinearLayout bride_service,service_hair;
    TextView arabic,english;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    Toolbar toolbar;
    SharedPreferences.Editor editor;
    //    EditText token;
    public static ArrayList<FilterAndSortModel> filterList=new ArrayList<>();
    public static ArrayList<ServiceFilter> serviceFilters=new ArrayList<>();

    public static int bdb_ser_id;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_fragment);

        BeautyMainPage.FRAGMENT_NAME="SETTING";
        //service_hair=view.findViewById(R.id.service_hair);

        toolbar= findViewById(R.id.toolbar);
        arabic= findViewById(R.id.arabic);
        english= findViewById(R.id.english);
//        token= view.findViewById(R.id.token);
//        String tokentxt=((AppCompatActivity)BeautyMainPage.context).getSharedPreferences("REG_ID",Context.MODE_PRIVATE).getString("token_client",null);
//        token.setText(tokentxt);
        editor =((AppCompatActivity)BeautyMainPage.context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).edit();

        arabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                // Change locale settings in the app.
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                conf.setLocale(new Locale("ar".toLowerCase())); // API 17+ only.
                // Use conf.locale = new Locale(...) if targeting lower versions
                res.updateConfiguration(conf, dm);
                editor.putString("lang","ar");
                editor.commit();
                Intent intent=new Intent(BeautyMainPage.context, BeautyMainPage.class);startActivity(intent);
                ((AppCompatActivity)BeautyMainPage.context).finish();
            }
        });

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                // Change locale settings in the app.
                DisplayMetrics dm = res.getDisplayMetrics();
                android.content.res.Configuration conf = res.getConfiguration();
                conf.setLocale(new Locale("en".toLowerCase())); // API 17+ only.
                // Use conf.locale = new Locale(...) if targeting lower versions
                res.updateConfiguration(conf, dm);
                editor.putString("lang","en");
                editor.commit();
                Intent intent=new Intent(BeautyMainPage.context, BeautyMainPage.class);startActivity(intent);
                ((AppCompatActivity)BeautyMainPage.context).finish();
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BeautyMainPage.navigationView.setForegroundGravity(Gravity.END);
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });

    }
}
