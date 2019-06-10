package com.dcoret.beautyclient.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.Notification;
import com.dcoret.beautyclient.Activities.ShoppingCart;
import com.dcoret.beautyclient.Activities.TabOne;
import com.dcoret.beautyclient.Activities.TabThree;
import com.dcoret.beautyclient.Activities.TabTwo;
import com.dcoret.beautyclient.Adapters.ServicesAdapterNew;
import com.dcoret.beautyclient.R;

public class ServicesTabsFragment2 extends Fragment
//        implements BeautyMainPage.OnBackPressedListener
{

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3;

    Fragment fragment;
    android.app.FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    TextView servicetab,offertab,maptab;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_reservations2, container, false);

        BeautyMainPage.FRAGMENT_NAME="SERVICETABFRAGMENT";
        Log.d("doback",BeautyMainPage.FRAGMENT_NAME);



        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        servicetab=view.findViewById(R.id.servicetabclick);
        offertab=view.findViewById(R.id.offertabclick);
        maptab=view.findViewById(R.id.maptabclick);

        tabselected(servicetab,offertab,maptab);
         fragment = new TabOne();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fragmentTransaction.detach(fragment);
                fragment = new ServiceFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);

                fragmentTransaction.commit();

            }
        });

//        ((BeautyMainPage) getActivity()).setOnBackPressedListener(this);

        servicetab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(servicetab,offertab,maptab);
                 fragment = new TabOne();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });
        offertab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(offertab,servicetab,maptab);
                 fragment = new TabThree();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });
        maptab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(maptab,servicetab,offertab);
                 fragment = new TabTwo();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });




        FloatingActionButton floatingActionButton=view.findViewById(R.id.fab1);
        FloatingActionButton floatingActionButton2=view.findViewById(R.id.fab2);


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Toast.makeText(BeautyMainPage.context,"ookkk",Toast.LENGTH_LONG).show();

                @SuppressLint("ResourceType")
//              final Dialog  dialog = new Dialog(BeautyMainPage.context);
                final Dialog dialog = new Dialog(BeautyMainPage.context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                dialog.setContentView(R.layout.filter_dialog);
                dialog.setTitle("Filtering");

                dialog.show();
                final EditText filtername=dialog.findViewById(R.id.filter_name);
                final EditText filterpricefrom=dialog.findViewById(R.id.filter_price_from);
                final EditText filterpriceto=dialog.findViewById(R.id.filter_price_to);
                final EditText rankfilter=dialog.findViewById(R.id.rank_dialog_filter);
                TextView filterapply=dialog.findViewById(R.id.filter_apply);
                Spinner city_filter_dialog=dialog.findViewById(R.id.city_filter_dialog);
                ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.cities,android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                city_filter_dialog.setAdapter(adapter);


//
            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final Dialog  dialog = new Dialog(BeautyMainPage.context);
                final Dialog  dialog = new Dialog(BeautyMainPage.context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                dialog.setContentView(R.layout.arrangement_dialog);
                dialog.setTitle("Filtering");

                dialog.show();
            }
        });




        return view;
    }


    void tabselected(TextView t1,TextView t2,TextView t3){
        t1.setTextSize(20);
        t1.setTextColor(Color.MAGENTA);
        t2.setTextSize(18);
        t2.setTextColor(Color.BLACK);
        t3.setTextSize(18);
        t3.setTextColor(Color.BLACK);

    }
    @Override
    public void onResume() {
        super.onResume();
    }


    public static Menu menu;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bar_menu_compare,menu);
        this.menu=menu;
        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id==R.id.compare){
            if(ServicesAdapterNew.comparenum>=2) {
                Log.d("Compare",ServicesAdapterNew.comparenum+"");
                fragment = new CompareFragment();
                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
//                Toast.makeText(BeautyMainPage.context,"There no Comparation enugh",Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(BeautyMainPage.context,"There no Comparation enugh",Toast.LENGTH_LONG).show();

            }
        }else if(id==R.id.shoppingcart){
            Intent intent=new Intent(BeautyMainPage.context,ShoppingCart.class);
            startActivity(intent);

        }else if(id==R.id.notify){
            Intent intent=new Intent(BeautyMainPage.context,Notification.class);
            startActivity(intent);
        }else if(id==R.id.gridlist){
//            TabOne.gridlistitems();
            if (ServicesAdapterNew.list)
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_grid_on_black_24dp));
            else
                menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_view_list_black_24dp));

        }


        return super.onOptionsItemSelected(item);
    }
}
