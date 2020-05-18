package com.ptmsa1.clinicclient.Activities;



import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.Adapters.BagReservationAdapter;
import com.ptmsa1.clinicclient.Adapters.CustomExpandableListBagAdapterForOthers;
import com.ptmsa1.clinicclient.Adapters.GroupReservationsAdapter;
import com.ptmsa1.clinicclient.Adapters.ServicesAdapter;
import com.ptmsa1.clinicclient.DataModel.BrowseServiceItem;
import com.ptmsa1.clinicclient.DataModel.GetCart;
import com.ptmsa1.clinicclient.R;

import java.util.ArrayList;

public class TabOthersBag extends Fragment {

    public static RecyclerView recyclerView;
    public static   ArrayList<BrowseServiceItem>  arrayList=new ArrayList<>();
    static  ServicesAdapter servicesAdapter;

    public static SwipeRefreshLayout pullToRefresh;


    static View view;
    public static BagReservationAdapter bagReservationAdapter;
    public  static  ArrayList<GetCart> getCarts=new ArrayList<>();
//    ExpandableListView listView;

    public static ExpandableListView listView;
    public static CustomExpandableListBagAdapterForOthers listAdapter;
    public static GroupReservationsAdapter adapter;

    Button res_all;
    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_one_bag, container, false);

//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//        bagReservationAdapter=new BagReservationAdapter(getActivity().getApplicationContext(),getCarts);
//        recyclerView.setAdapter(bagReservationAdapter);
        listView=view.findViewById(R.id.listview);
        res_all=view.findViewById(R.id.res_all);

        pullToRefresh=view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCarts.clear();
                APICall.getAllCart(BeautyMainPage.context);
            }
        });

//        APICall.searchGroupBookingBag(BeautyMainPage.context);
//        APICall.getAllCart(BeautyMainPage.context);

        listAdapter=new CustomExpandableListBagAdapterForOthers(BeautyMainPage.context,APICall.salonBookingOthers,APICall.grBookingListMapOthers);
//       GroupReservationResultFragment.listAdapter.notifyDataSetChanged();
        listView.setAdapter(TabOthersBag.listAdapter);
        listAdapter.notifyDataSetChanged();



        //------------------- new for test //----------------- reserve all single reservations
        res_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                  --------- reserve offer and reserve group booking-------------
                for (int i=0;i<APICall.salonBooking.size();i++) {
                    if (Integer.parseInt(APICall.grBookingListMap.get(APICall.salonBooking.get(i)).get(0).getGetAllCarts().get(0).getBdb_is_group_booking())>=4 &&
                            Integer.parseInt(APICall.grBookingListMap.get(APICall.salonBooking.get(i)).get(0).getGetAllCarts().get(0).getBdb_is_group_booking())<10 ) {
                        APICall.moveAllofferCartToBooking(APICall.grBookingListMap.get(APICall.salonBooking.get(i)).get(0).getGetAllCarts().get(0).getBdb_pack_booking(), APICall.grBookingListMap.get(APICall.salonBooking.get(i)).get(0).getGetAllCarts().get(0).getBdb_is_group_booking(), i, BeautyMainPage.context);
                    }else {
                        APICall.moveAllCartToBooking(APICall.grBookingListMap.get(APICall.salonBooking.get(i)).get(0).getGetAllCarts().get(0).getBdb_pack_booking(),APICall.grBookingListMap.get(APICall.salonBooking.get(i)).get(0).getGetAllCarts().get(0).getBdb_is_group_booking(),i,BeautyMainPage.context);
                    }

//                    APICall.moveCartToBooking( APICall.singleBookingList.get(i).getBdb_id(), APICall.singleBookingList.get(i).getBdb_is_group_booking(),i,BeautyMainPage.context);
                }
            }
        });

        Button delete_all=view.findViewById(R.id.delete_all);



        delete_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  --------- delete offer and reserve group booking
                for (int i=0;i<APICall.salonBooking.size();i++) {
//                    APICall.moveCartToBooking(APICall.grBookingListMap.get(APICall.salonBooking.get(i)).get(0).getGetAllCarts().get(0).getBdb_pack_booking(),APICall.grBookingListMap.get(APICall.salonBooking.get(i)).get(0).getGetAllCarts().get(0).getBdb_is_group_booking(),i,BeautyMainPage.context);
//                    APICall.moveAllCartToBooking( APICall.singleBookingList.get(i).getBdb_id(), APICall.singleBookingList.get(i).getBdb_is_group_booking(),i,BeautyMainPage.context);

                  for (int k=0;k<APICall.grBookingListMap.get(APICall.salonBooking.get(i)).size();k++)
                    for (int j=0;j<APICall.grBookingListMap.get(APICall.salonBooking.get(i)).get(0).getGetAllCarts().size();j++)
                    APICall.deletallFromCartMulti( APICall.grBookingListMap.get(APICall.salonBooking.get(i)).get(k).getGetAllCarts().get(j).getBdb_id(),i,BeautyMainPage.context);
                }
            }
        });


        return view;
    }
}


