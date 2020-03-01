package com.dcoret.beautyclient.Adapters;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.DataModel.DateClass;
import com.dcoret.beautyclient.DataModel.ListServiceModel;
import com.dcoret.beautyclient.Fragments.IndividualBooking.ListServicesFragment;
import com.dcoret.beautyclient.Fragments.IndividualBooking.PlaceServiceFragment;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;

/**
 * This class show me the items of the services in recycle view \n
 *hhhhhhhhhhh
 * @see RecyclerView.Adapter
 * @author Mahmoud Alali
 */
public class ListServicesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static String empid="";
    public static String empid23="";
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    private static int categoryIndex;
    int [] servicsImgsBasic ={ R.drawable.hair_basic,
            R.drawable.makeup_basic,
            R.drawable.massage_basic,
            R.drawable.spa_basic,
            R.drawable.nails_basic,
            R.drawable.body_basic,
            R.drawable.skin_basic,
            R.drawable.eyebrows_basic
    };
    int [] servicsImgs ={ R.drawable.hair,
            R.drawable.makeup2,
            R.drawable.massage,
            R.drawable.spa,
            R.drawable.nails_basic,
            R.drawable.body,
            R.drawable.skin,
            R.drawable.eyebrows
    };

    Context context;
    public static boolean list;
    static   String[] items={"Service1","Service2","Service3","Service4","Service5","Service6","Service7","Service8","Service9","Service10"};
    public  static ArrayList<DateClass> dateClasses=new ArrayList<>() ;
    public static String srName1,srName2,srName3,spname1,spname2,spname3,ev1,ev2,ev3,place1,place2,place3,address1,address2,price1,price2,price3;
    public static String bdb_ser_home,bdb_ser_hall,bdb_ser_salon,bdb_hotel;
    public static String bdb_ser_home1,bdb_ser_hall1,bdb_ser_salon1,bdb_hotel1,ser_sup_id;
//    public static String bdb_ser_home2,bdb_ser_hall2,bdb_ser_salon2,bdb_hotel2;

    public static String bdb_ser_id="";
    int layout;

    ArrayList<ListServiceModel> itemArrayList;

    public ListServicesAdapter(Context context, String[] items){
        this.context=context;
        this.items=items;
    }
    public ListServicesAdapter(Context context, ArrayList<ListServiceModel> itemArrayList){
        this.context=context;
        this.itemArrayList=itemArrayList;
        this.layout=layout;
    }

    public ListServicesAdapter(Context context, String[] items, Boolean list, int layout){
        this.context=context;
        this.items=items;
        this.list=list;
        this.layout=layout;
    }


    boolean grid ;


    /**
     * @param parent
     * @param viewType
     * @return
     * <b>items</b> that are contains the service layout
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context.getApplicationContext());
        View row;
        row = inflater.inflate(R.layout.item_service_layout_v1, parent, false);
        ListServicesAdapter.Item item=new ListServicesAdapter.Item(row);
        return item;
    }

    String date;
    Dialog dialog1,dialog;
    public static int comparenum=0;
    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        if(context.getResources().getString(R.string.locale).equals("ar"))
        ((Item)holder).service_name.setText(itemArrayList.get(position).getBdb_name_ar());
        else
            ((Item)holder).service_name.setText(itemArrayList.get(position).getBdb_name());

        ((Item)holder).item_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("SER_ID","SER_ID::"+ itemArrayList.get(position).getBdb_ser_id());
                Log.e("ClickedClicked","Clicked::");
                APICall.idSerForOffer=itemArrayList.get(position).getBdb_ser_id();
                APICall.filterSortAlgorithm("33", itemArrayList.get(position).getBdb_ser_id(), "0");
                bdb_ser_id=itemArrayList.get(position).getBdb_ser_id();
                ListServicesFragment.serviceName=((Item) holder).service_name.getText().toString();
                fragment = new PlaceServiceFragment();
                fm =((AppCompatActivity)context).getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();

            }
        });
        if(itemArrayList.get(position).getBdb_is_hair_service().equals(categoryIndex+"")) {
            ((Item) holder).serviceImg.setImageResource(servicsImgs[categoryIndex]);

        }
        else {

            categoryIndex = Integer.parseInt(itemArrayList.get(position).getBdb_is_hair_service());

            ((Item)holder).serviceImg.setImageResource(servicsImgs[categoryIndex]);

        }

    }

    @Override
    public int getItemCount() {
            return itemArrayList.size();
    }

    /**
     * @see RecyclerView.ViewHolder
     */
    public static class Item extends RecyclerView.ViewHolder {

        TextView  service_name;

        RelativeLayout item_click;
        ImageView serviceImg;

        /**
         * @param itemView
         */
        public Item(View itemView) {
            super(itemView);

            service_name=itemView.findViewById(R.id.service_name);
            item_click=itemView.findViewById(R.id.item_click);
            serviceImg=itemView.findViewById(R.id.img_company);


        }
    }
}
