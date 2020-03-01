package com.dcoret.beautyclient.Activities;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dcoret.beautyclient.DataModel.DataService;
import com.dcoret.beautyclient.DataModel.Location_Beauty;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

public class ServiceTab extends Fragment {

    RecyclerView recyclerView;

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
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        for (int i = 0; i < items.length; i++) {
            dataServices.add(new DataService(0, items[i], providers_name[i], Double.parseDouble(prices[i]), Double.parseDouble(rank[i]), city[i], locations[i], fav[i], false));
        }


        view = inflater.inflate(R.layout.tab_one, container, false);
//        FloatingActionButton floatingActionButton=view.findViewById(R.id.fab1);
//        FloatingActionButton floatingActionButton2=view.findViewById(R.id.fab2);
////        Toast.makeText(Offers.context,"ookkk",Toast.LENGTH_LONG).show();
//        BottomNavigationView navigation = (BottomNavigationView) view.findViewById(R.id.navigation);
//        navigation.setSelectedItemId(R.id.list);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
//
//
//
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Toast.makeText(Offers.context,"ookkk",Toast.LENGTH_LONG).show();
//
//                @SuppressLint("ResourceType")
//                final Dialog  dialog = new Dialog(Offers.context);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                dialog.setContentView(R.layout.filter_dialog);
//                dialog.setTitle("Filtering");
//
//                dialog.show();
//                final EditText filtername=dialog.findViewById(R.id.filter_name);
//                final EditText filterpricefrom=dialog.findViewById(R.id.filter_price_from);
//                final EditText filterpriceto=dialog.findViewById(R.id.filter_price_to);
//                final EditText rankfilter=dialog.findViewById(R.id.rank_dialog_filter);
//                TextView filterapply=dialog.findViewById(R.id.filter_apply);
//                Spinner city_filter_dialog=dialog.findViewById(R.id.city_filter_dialog);
//                ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(Offers.context,R.array.cities,android.R.layout.simple_spinner_item);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                city_filter_dialog.setAdapter(adapter);
//
////                filterapply.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        String filter=filtername.getText().toString();
////                        String pfrom=filterpricefrom.getText().toString();
////                        String pto=filterpriceto.getText().toString();
////                        String rnk=rankfilter.getText().toString();
////                        itemstmp.clear();
////                        pricestmp.clear();
////                        ranktmp.clear();
////                        citiestmp.clear();
////                        locationstmp.clear();
////                        int i=0;
////                        int j=0;
////                        for (i=0;i<items.length;i++){
////
////                            Location locationA = new Location("point A");
////                            locationA.setLatitude(32.7792842);
////                            locationA.setLongitude(35.8816735);
////                            Location locationB = new Location("point B");
////                            locationB.setLatitude(locations[i].getLatitude());
////                            locationB.setLongitude(locations[i].getLongtude());
////
////                            double distance = locationA.distanceTo(locationB);
////                            distance=distance/1000;
////                            Toast.makeText(Offers.context,distance+"",Toast.LENGTH_LONG).show();
////                                if(distance<=Double.parseDouble(filter)){
////
////                                    itemstmp.add(items[i]);
////                                    pricestmp.add(prices[i]);
////                                    ranktmp.add(rank[i]);
////                                    citiestmp.add(city[i]);
////                                    locationstmp.add(locations[i]);
////                                    j++;
////
////                                }
////
////
////                            if(filter.equals(items[i] )&& !filter.isEmpty()){
////                                itemstmp.add(items[i]);
////                                pricestmp.add(prices[i]);
////                                ranktmp.add(rank[i]);
////                                citiestmp.add(city[i]);
////                                locationstmp.add(locations[i]);
////                                j++;
////
////                            }else if(!pfrom.isEmpty() && !pto.isEmpty()){
////
////
////                                    int pfromdata=Integer.parseInt(prices[i]);
////                                    int ptodata=Integer.parseInt(prices[i]);
////                                    int pfdialog=Integer.parseInt(pfrom);
////                                    int ptodialog=Integer.parseInt(pto);
////                                    if(pfromdata>=pfdialog && ptodata<=ptodialog ){
////                                        itemstmp.add(items[i]);
////                                        pricestmp.add(prices[i]);
////                                        ranktmp.add(rank[i]);
////                                        citiestmp.add(city[i]);
////                                        locationstmp.add(locations[i]);
////                                        j++;
////
////                                    }
////
////
////                            }else if(!rnk.isEmpty()){
////
////                                    double rnkdialog=Double.parseDouble(rnk);
////                                    double rnkdata=Double.parseDouble(rank[i]);
////                                    if(rnkdata>=rnkdialog){
////                                        itemstmp.add(items[i]);
////                                        pricestmp.add(prices[i]);
////                                        ranktmp.add(rank[i]);
////                                        citiestmp.add(city[i]);
////                                        locationstmp.add(locations[i]);
////                                        j++;
////
////                                }
////                            }
////                        }
////
////                        if(!itemstmp.isEmpty()){
////                        String[] arr1 = itemstmp.toArray(new String[j]);
////                        String[] arr2 = pricestmp.toArray(new String[j]);
////                        String[] arr3 = ranktmp.toArray(new String[j]);
////                        String[] arr4 = citiestmp.toArray(new String[j]);
////                        Location_Beauty[] arr5 = locationstmp.toArray(new Location_Beauty[j]);
////                        if(grid==false){
////                        recyclerView=view.findViewById(R.id.recycleview);
////                        recyclerView.setLayoutManager(new LinearLayoutManager(Offers.context));
////                        recyclerView.setAdapter(new ServicesAdapter1(Offers.context,arr1,arr2,arr3,arr4,arr5,fav));
////
////                        }else{
////                            recyclerView = view.findViewById(R.id.recycleview);
////                            recyclerView.setLayoutManager(new GridLayoutManager(Offers.context, 2));
////                            recyclerView.setAdapter(new ServicesAdapter1(Offers.context,items,prices,rank,city,locations,grid,fav));
////                        }
////                            dialog.cancel();
////                        }else{
////                            Toast.makeText(Offers.context,"NO thing to show",Toast.LENGTH_LONG).show();
////
////                        }
////                    }
////                });
////
//            }
//        });
//
//        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final Dialog  dialog = new Dialog(Offers.context);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                dialog.setContentView(R.layout.arrangement_dialog);
//                dialog.setTitle("Filtering");
//
//                dialog.show();
//            }
//        });
//
//        recyclerView=view.findViewById(R.id.recycleview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(Offers.context));
////        recyclerView.setAdapter(new ServicesAdapter1(Offers.context,items,prices,rank,city,locations,fav));
//        recyclerView.setAdapter(new ServicesAdapter(getActivity().getApplicationContext(),items));
//        return view;
//
//
//    }
//    boolean grid=false;
//
//    public BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                    switch (item.getItemId()) {
//                        case R.id.list:
//                            grid=false;
//                            recyclerView=view.findViewById(R.id.recycleview);
//                            recyclerView.setLayoutManager(new LinearLayoutManager(Offers.context));
////                            recyclerView.setAdapter(new ServicesAdapter1(Offers.context,items,prices,rank,city,locations,grid,fav));
//                            recyclerView.setAdapter(new ServicesAdapter(Offers.context,items));
//
//                            return true;
//                        case R.id.grid:
//                            grid=true;
//                            recyclerView = view.findViewById(R.id.recycleview);
//                            recyclerView.setLayoutManager(new GridLayoutManager(Offers.context, 2));
////                            recyclerView.setAdapter(new ServicesAdapter1(Offers.context,items,prices,rank,city,locations,grid,fav));
//                            recyclerView.setAdapter(new ServicesAdapter(Offers.context,items));
//
//                            return true;
//                    }
//
//                    return false;
//                }
//            };
        return view;
    }
}


