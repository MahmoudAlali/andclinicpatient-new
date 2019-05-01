package com.dcoret.beautyclient.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dcoret.beautyclient.Adapters.OffersAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.DataExample.OffersData;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class Offers extends AppCompatActivity {
    public static Context context;
    RecyclerView recyclerView;
    String[] items={"Offer 1","Offer 2","Offer 3","Offer 4","Offer 5","Offer 6"};
    public static  String name="offers";

ArrayList<DataService> arr=new ArrayList<>();


    DataService[] services=new DataService[]{
      new DataService(1,"service1",30,4.5,false,true),
      new DataService(1,"service2",40,4.5,false,true),
      new DataService(1,"service3",20,4.5,false,true),
      new DataService(1,"service4",50,4.5,false,true),
      new DataService(1,"service5",50,4.5,false,true),

    };
    DataService[] services1=new DataService[]{
            new DataService(2,"خدمة1",30,4.5,false,true),
            new DataService(2,"خدمة2",40,4.5,false,true),
            new DataService(2,"خدمة3",20,4.5,false,true),
            new DataService(2,"خدمة4",50,4.5,false,true),
            new DataService(2,"خدمة5",50,4.5,false,true),

    };
    DataService[] service2=new DataService[]{
            new DataService(3,"serv1",30,4.5,false,true),
            new DataService(3,"serv2",40,4.5,false,true),
            new DataService(3,"serv3",20,4.5,false,true),
            new DataService(3,"serv4",50,4.5,false,true),
            new DataService(3,"serv5",50,4.5,false,true),

    };
    DataService[] service4=new DataService[]{
            new DataService(4,"serv1",40,4.5,false,true),
            new DataService(4,"serv2",40,4.5,false,true),
            new DataService(4,"serv3",20,4.5,false,true),
            new DataService(4,"serv4",50,4.5,false,true),
            new DataService(4,"serv5",50,4.5,false,true),

    };
    DataService[] service5=new DataService[]{
            new DataService(5,"serv1",50,4.5,false,true),
            new DataService(5,"serv2",40,4.5,false,true),
            new DataService(5,"serv3",20,4.5,false,true),
            new DataService(5,"serv4",50,4.5,false,true),
            new DataService(5,"serv5",50,4.5,false,true),

    };

    ArrayList<DataOffer> offers= new ArrayList<>();

//            new DataOffer[]{
//      new DataOffer("offer1",services,150,false),
//      new DataOffer("offer2",services1,150,false),
//      new DataOffer("offer3",service2,150,false),
//      new DataOffer("offer4",service4,150,false),
//      new DataOffer("offer5",service5,150,false),
//    };

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        setTitle("ابرز العروض");
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        arr.add(services1[0]);
        arr.add(services1[1]);
        arr.add(services1[2]);
        context=this;


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.list);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

//        recyclerView=findViewById(R.id.review);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new OffersAdapter(getApplicationContext(),offers,false,"offers"));



        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }




    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.list:
                            recyclerView = findViewById(R.id.review);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(new OffersAdapter(getApplicationContext(), offers, false,"offers"));
                            return true;
                        case R.id.grid:
                            recyclerView =findViewById(R.id.review);
//                            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(new ServicesAdapter(getApplicationContext(), arr));
                            return true;
                    }

                    return false;
                }
            };




    @Override
    public boolean onNavigateUp() {
        finish();
        return true;
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    static int section;
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */


        public static Offers.PlaceholderFragment newInstance(int sectionNumber) {
            Offers.PlaceholderFragment fragment = new Offers.PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            section=sectionNumber;
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView;

            rootView = inflater.inflate(R.layout.tab_one, container, false);
            return rootView;


//            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position == 0) {
//                return PlaceholderFragment.newInstance(0);
                ServiceTab one = new ServiceTab();

                return one;
            } else if (position == 1) {
                OffersTab two = new OffersTab();
                return two;


            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
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

}
