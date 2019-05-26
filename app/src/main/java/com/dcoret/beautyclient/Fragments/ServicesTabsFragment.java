package com.dcoret.beautyclient.Fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.Compartion;
import com.dcoret.beautyclient.Activities.MainActivity;
import com.dcoret.beautyclient.Activities.Notification;
import com.dcoret.beautyclient.Activities.ShoppingCart;
import com.dcoret.beautyclient.Activities.TabOne;
import com.dcoret.beautyclient.Activities.TabThree;
import com.dcoret.beautyclient.Activities.TabTwo;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapterNew;
import com.dcoret.beautyclient.R;

public class ServicesTabsFragment extends Fragment
//        implements BeautyMainPage.OnBackPressedListener
{

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_reservations, container, false);
//        mSectionsPagerAdapter = new MyReservations.SectionsPagerAdapter(getSupportFragmentManager());
//
        Toolbar toolbar=view.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.

        final FragmentManager fragmentManager=((AppCompatActivity) getActivity()).getSupportFragmentManager();

        mSectionsPagerAdapter = new SectionsPagerAdapter(fragmentManager);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment = new ServiceFragment();
                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
//                getActivity().getFragmentManager().popBackStack();
                fragmentManager.popBackStackImmediate();
                fragmentTransaction.commit();

            }
        });

//        ((BeautyMainPage) getActivity()).setOnBackPressedListener(this);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bar_menu_compare,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

//    @Override
//    public void doBack() {
//        getActivity().getFragmentManager().popBackStack();
//        Log.d("doback","ok");
//        fragment = new ServiceFragment();
//        fm = getFragmentManager();
//        fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment, fragment);
//        fragmentTransaction.commit();
//    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        TabOne one;
        TabTwo two;
        TabThree three;
        public SectionsPagerAdapter(FragmentManager fm) {

            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==0){
                 one=new TabOne();
                return null;
            }else if(position==1) {
                 two=new TabTwo();
                return null;
            }else if(position==2) {
                 three=new TabThree();
                return null;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
    }



    Fragment fragment;
    android.app.FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    @Override
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
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onPause() {
        super.onPause();
        onDestroy();
    }

}
