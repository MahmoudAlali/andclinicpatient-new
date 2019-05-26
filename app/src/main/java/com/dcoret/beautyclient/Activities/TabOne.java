package com.dcoret.beautyclient.Activities;



import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.Adapters.ServicesAdapterNew;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.DataClass.Location_Beauty;
import com.dcoret.beautyclient.DataExample.ServicesData;
import com.dcoret.beautyclient.Fragments.ServicesTabsFragment;
import com.dcoret.beautyclient.Fragments.ServicesTabsFragment2;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class TabOne extends Fragment {

   static RecyclerView recyclerView;

  static   String[] items={"Service1","Service2","Service3","Service4","Service5","Service6","Service7","Service8","Service9","Service10"};
   static String[] providers_name={"SANA'A","HIBA","NOUR","LAMA","NOUR","HIBA","FIHA'A","SANA'A","LAMA","KAMAR"};

    static   String[] prices={"100","500","450","123","345","411","800","900","600","300"};
    static   String[] rank={"4.1","3.2","3.5","4.7","4.4","3.0","3.0","2.5","2.0","1.5"};
    static  String[] city={"الرياض","الدمام","مكة","الرياض","جدة","الدمام","مكة","مكة","الطائف","مكة"};
    static Location_Beauty[] locations={
            new Location_Beauty(32.7792842,35.8816735),
            new Location_Beauty(31.964383, 35.918756),
            new Location_Beauty(32.709566, 36.137142),
            new Location_Beauty(32.777491, 35.935935),
            new Location_Beauty(32.755262, 35.986746),
            new Location_Beauty(32.725373, 35.944346),
            new Location_Beauty(32.688479, 35.992233),
            new Location_Beauty(32.670663, 36.052908),
            new Location_Beauty(33.506590, 36.299474),
            new Location_Beauty(33.546086, 36.200597),
    };
    static boolean[] fav={
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
    };


    static ArrayList<DataService> dataServices=new ArrayList<>();


    ArrayList<String> itemstmp=new ArrayList<>();
    ArrayList<String> pricestmp=new ArrayList<>();
    ArrayList<String> ranktmp=new ArrayList<>();
    ArrayList<String> citiestmp=new ArrayList<>();
    ArrayList<Location_Beauty> locationstmp=new ArrayList<Location_Beauty>();
    static View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        if(dataServices.isEmpty()) {
            new ServicesData(dataServices);
        }

        view= inflater.inflate(R.layout.tab_one,container,false);

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
//        recyclerView.setAdapter(new ServicesAdapter(BeautyMainPage.context,items,prices,rank,city,locations,fav));
        recyclerView.setAdapter(new ServicesAdapterNew(BeautyMainPage.context,items,true,R.layout.service_layout_adapter_last));
        return view;


    }

    public static void gridlistitems(){
        if (ServicesAdapterNew.list==true) {
        Log.d("gridlist",ServicesAdapterNew.list+"");
            recyclerView = view.findViewById(R.id.recycleview);
            recyclerView.setLayoutManager(new GridLayoutManager(BeautyMainPage.context,2));
//        recyclerView.setAdapter(new ServicesAdapter(BeautyMainPage.context,items,prices,rank,city,locations,fav));
            recyclerView.setAdapter(new ServicesAdapterNew(BeautyMainPage.context, items,ServicesAdapterNew.list,R.layout.service_layout_adapter_grid_last));
            ServicesAdapterNew.list=false;
        }else {
            Log.d("gridlist",ServicesAdapterNew.list+"");
            recyclerView = view.findViewById(R.id.recycleview);
            recyclerView.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
//        recyclerView.setAdapter(new ServicesAdapter(BeautyMainPage.context,items,prices,rank,city,locations,fav));
            recyclerView.setAdapter(new ServicesAdapterNew(BeautyMainPage.context, items,ServicesAdapterNew.list,R.layout.service_layout_adapter_last));
            ServicesAdapterNew.list=true;
        }
    }
boolean grid=false;

    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.list:
                            grid=false;
                            recyclerView=view.findViewById(R.id.recycleview);
                            recyclerView.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
//                            recyclerView.setAdapter(new ServicesAdapter(BeautyMainPage.context,items,prices,rank,city,locations,grid,fav));
                            recyclerView.setAdapter(new ServicesAdapterNew(BeautyMainPage.context,items));
                            return true;
                        case R.id.grid:
                            grid=true;
                            recyclerView = view.findViewById(R.id.recycleview);
                            recyclerView.setLayoutManager(new GridLayoutManager(BeautyMainPage.context, 2));
//                            recyclerView.setAdapter(new ServicesAdapter(BeautyMainPage.context,items,prices,rank,city,locations,grid,fav));
                            recyclerView.setAdapter(new ServicesAdapterNew(BeautyMainPage.context,items));

                            return true;
                    }

                    return false;
                }
            };
}


