package com.ptmsa1.vizage.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.DataModel.LocationTitles;
import com.ptmsa1.vizage.R;
import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.Locale;


public class AccountFragment extends Fragment  {
    Button add_del_site;
    public static boolean edit_flag=false;
    Toolbar toolbar;
    MapView mMapView;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    EditText e_bdb_mobile,e_bdb_name,e_bdb_email,e_pass,e_c_pass,old_pass;
    Button save,deleteaccount;
    TextView edit;
    Spinner language;
    SharedPreferences.Editor editor;
    public static String oldEmail="",oldName="";


    public  static ArrayList<LocationTitles> locationTitles=new ArrayList<>();
    public  static ArrayList<String> arrayList=new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_manage_accounts_frag, container, false);

        toolbar=view.findViewById(R.id.toolbar);
        e_bdb_email=view.findViewById(R.id.e_bdb_email);
        e_bdb_name=view.findViewById(R.id.e_bdb_name);
        e_bdb_mobile=view.findViewById(R.id.e_bdb_mobile);
        e_pass=view.findViewById(R.id.pass);
        e_c_pass=view.findViewById(R.id.c_pass);
        old_pass=view.findViewById(R.id.old_pass);
        save=view.findViewById(R.id.save);
        edit=view.findViewById(R.id.edit);
        deleteaccount=view.findViewById(R.id.deleteaccount);
        language = view.findViewById(R.id.language);




        old_pass.setEnabled(false);
        e_c_pass.setEnabled(false);
        e_pass.setEnabled(false);
        old_pass.setText("123456");
        e_c_pass.setText("123456");
        e_pass.setText("123456");
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                old_pass.setText("");
                e_c_pass.setText("");
                e_pass.setText("");
                old_pass.setEnabled(true);
                e_c_pass.setEnabled(true);
                e_pass.setEnabled(true);
                edit_flag=true;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)BeautyMainPage.context).onBackPressed();
            }
        });

        deleteaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                APICall.deleteAccount("http://clientapp.dcoret.com/api/auth/user/deleteAccount",BeautyMainPage.context);
                fragment = new DeleteAccountFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });


        APICall.detailsUser("http://clientapp.dcoret.com/api/auth/user/detailsUser",e_bdb_name,e_bdb_email,e_bdb_mobile,BeautyMainPage.context);
        BeautyMainPage.FRAGMENT_NAME="";
        BeautyMainPage.FRAGMENT_NAME="ACCOUNTFRAGMENT";
        add_del_site=view.findViewById(R.id.add_del_site);
        add_del_site.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new MapFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
            }
        });

        Log.d("fargmentname",BeautyMainPage.FRAGMENT_NAME);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_flag){
                    if (e_pass.getText().toString().equals(e_c_pass.getText().toString())){
                        if (e_bdb_email.getText().toString().length()!=0) {
                            APICall.update_user(APICall.API_PREFIX_NAME + "/api/auth/user/updateUser", e_bdb_name.getText().toString(), e_bdb_email.getText().toString(), e_pass.getText().toString(), old_pass.getText().toString(), BeautyMainPage.context);
                        }else {
                            APICall.showSweetDialog(BeautyMainPage.context,R.string.ExuseMeAlert,R.string.InvalidEmail);
                        }
                    }else {
                        APICall.showSweetDialog(BeautyMainPage.context,R.string.ExuseMeAlert,R.string.PsswordIncorrectAlert);
                    }


                }else {
                    APICall.update_user(APICall.API_PREFIX_NAME+"/api/auth/user/updateUser",e_bdb_name.getText().toString(),e_bdb_email.getText().toString(),BeautyMainPage.context);

                }
                Intent intent=new Intent(BeautyMainPage.context, BeautyMainPage.class);startActivity(intent);
                ((AppCompatActivity)BeautyMainPage.context).finish();

            }
        });
        final ArrayAdapter adapter = ArrayAdapter.createFromResource(BeautyMainPage.context, R.array.language, R.layout.simple_spinner_dropdown_item_v1);
        adapter.setDropDownViewResource(R.layout.spinner_center_item);
        language.setAdapter(adapter);
        editor =((AppCompatActivity)BeautyMainPage.context).getSharedPreferences("LOGIN", Context.MODE_PRIVATE).edit();

        language.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 2) {
                    Resources res = getResources();
                    // Change locale settings in the app.
                    DisplayMetrics dm = res.getDisplayMetrics();
                    android.content.res.Configuration conf = res.getConfiguration();
                    conf.setLocale(new Locale("ar".toLowerCase())); // API 17+ only.
                    // Use conf.locale = new Locale(...) if targeting lower versions
                    res.updateConfiguration(conf, dm);
                    editor.putString("lang","ar");
                    editor.commit();
                    editor.apply();

                    Log.e("applanguageset",BeautyMainPage.context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("lang","klsjjlkj"));
                    APICall.ln="ar";
                   Intent intent=new Intent(BeautyMainPage.context, BeautyMainPage.class);startActivity(intent);
                    ((AppCompatActivity)BeautyMainPage.context).finish();

                }
                else if(position == 1)
                {
                    Resources res = getResources();
                    // Change locale settings in the app.
                    DisplayMetrics dm = res.getDisplayMetrics();
                    android.content.res.Configuration conf = res.getConfiguration();
                    conf.setLocale(new Locale("en".toLowerCase())); // API 17+ only.
                    // Use conf.locale = new Locale(...) if targeting lower versions
                    res.updateConfiguration(conf, dm);
                    editor.putString("lang","en");
                    editor.commit();
                    editor.apply();
                    APICall.ln="en";
                    Log.e("applanguageset",BeautyMainPage.context.getSharedPreferences("LOGIN", Context.MODE_PRIVATE).getString("lang","klsjjlkj"));



                    Intent intent=new Intent(BeautyMainPage.context, BeautyMainPage.class);startActivity(intent);
                    ((AppCompatActivity)BeautyMainPage.context).finish();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

}
