package com.dcoret.beautyclient.Fragments.IndividualBooking;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Adapters.ListServicesAdapter;
import com.dcoret.beautyclient.DataModel.ListServiceModel;
import com.dcoret.beautyclient.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListServicesFragment extends Fragment {


    RecyclerView recyclerView;
    public  static ListServicesAdapter servicesAdapter;
    public static GridLayoutManager manager;
    static LinearLayout root;
    public static String serviceName="";
    public static String bdb_ser_id="";
    private static int categoryIndex=0;
    static Fragment fragment;
    static FragmentManager fm;
    static FragmentTransaction fragmentTransaction;
    static int [] categoryImages ={ R.drawable.hair_basic,
            R.drawable.makeup_basic,
            R.drawable.massage_basic,
            R.drawable.spa_basic,
            R.drawable.nails_basic,
            R.drawable.body_basic,
            R.drawable.skin_basic,
            R.drawable.eyebrows_basic
    };
    static int [] servicsImgs ={ R.drawable.hair,
            R.drawable.makeup2,
            R.drawable.massage,
            R.drawable.spa,
            R.drawable.nails_basic,
            R.drawable.body,
            R.drawable.skin,
            R.drawable.eyebrows
    };
    static private int[] categoryNames={
            R.string.hair,
            R.string.makeup2,
            R.string.massage,
            R.string.spa,
            R.string.nails,
            R.string.body,
            R.string.skin,
            R.string.eyebrows,

    };
    public static ArrayList<ListServiceModel> favoritesList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_services_layout, container, false);
        root = view.findViewById(R.id.root);
        recyclerView=view.findViewById(R.id.recycleview);
        BeautyMainPage.FRAGMENT_NAME="ListServicesFragment";
        BeautyMainPage.is_bride_service="0";

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
         servicesAdapter=new ListServicesAdapter(BeautyMainPage.context, APICall.itemArrayList);
         manager = new GridLayoutManager(BeautyMainPage.context,3);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(servicesAdapter);


        APICall.getBasicService(BeautyMainPage.context);

       Toolbar toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AppCompatActivity)BeautyMainPage.context).onBackPressed();
            }
        });

        return view;
    }
    public static void SetServicesView(final ArrayList<ListServiceModel> itemArrayList)
    {
        categoryIndex=0;
        boolean end=false;
        int index = 0;
        // addCategoryLayout();
        while (index<itemArrayList.size())
        {

            final View layout3;
            Log.e("Category",index+"");
            layout3 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.category_layout,root, false);
            final TextView categoryName =  layout3.findViewById(R.id.categoryName);
            final ImageView categoryImg =  layout3.findViewById(R.id.categoryImg);
            final LinearLayout grid =  layout3.findViewById(R.id.categoryItems);

            //grid.addView(new TextView(BeautyMainPage.context));
            categoryName.setText(categoryNames[categoryIndex]);
            categoryImg.setImageResource(categoryImages[categoryIndex]);
            while (index<itemArrayList.size()&&itemArrayList.get(index).getBdb_is_hair_service().equals(categoryIndex+""))
            {
                Log.e("CategoryIn",index+" "+itemArrayList.get(index).getBdb_is_hair_service()+" "+categoryIndex);
                int innerIndex=0;

                LinearLayout row = new LinearLayout(BeautyMainPage.context);
                row.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setWeightSum(3);
                for (int i=0;i<3;i++)
                {
                    final View layout2;
                    final int k =index;
                    layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.item_service_layout_v2,root, false);

                    final TextView service_name =  layout2.findViewById(R.id.service_name);
                    final ImageView img_company =  layout2.findViewById(R.id.img_company);
                    final ImageView is_fav =  layout2.findViewById(R.id.is_fav);
                    LinearLayout item_click =layout2.findViewById(R.id.item_click);
                    if(index<itemArrayList.size()&&itemArrayList.get(index).getBdb_is_hair_service().equals(categoryIndex+""))
                    {

                        if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar"))
                            service_name.setText(itemArrayList.get(index).getBdb_name_ar());
                        else
                            service_name.setText(itemArrayList.get(index).getBdb_name());
                        img_company.setImageResource(servicsImgs[categoryIndex]);
                        if(itemArrayList.get(index).getIs_fav().equals("1"))
                            is_fav.setImageResource(R.drawable.favorite);
                        else
                            is_fav.setImageResource(R.drawable.un_favorite);

                        item_click.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                APICall.sendFavorites(BeautyMainPage.context,getFavorites(itemArrayList));
                                APICall.idSerForOffer=itemArrayList.get(k).getBdb_ser_id();
                                APICall.filterSortAlgorithm("33", itemArrayList.get(k).getBdb_ser_id(), "0");
                                bdb_ser_id=itemArrayList.get(k).getBdb_ser_id();
                                serviceName=service_name.getText().toString();
                                Log.e("SER_ID", itemArrayList.get(k).getBdb_ser_id());
                                fragment = new PlaceServiceFragment();
                                fm =((AppCompatActivity)BeautyMainPage.context).getFragmentManager();
                                fragmentTransaction = fm.beginTransaction();
                                fragmentTransaction.replace(R.id.fragment, fragment);
                                fragmentTransaction.commit();

                            }

                        });
                        is_fav.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(itemArrayList.get(k).getNew_fav().equals("0"))
                                {
                                    itemArrayList.get(k).setNew_fav("1");
                                    is_fav.setImageResource(R.drawable.favorite);
                                }
                                else {
                                    itemArrayList.get(k).setNew_fav("0");
                                    is_fav.setImageResource(R.drawable.un_favorite);
                                    APICall.sendUnFavorites(BeautyMainPage.context,itemArrayList.get(k).getBdb_ser_id(),"0");
                                }


                            }
                        });
                        row.addView(layout2);
                        index++;
                    }
                    else
                    {
                        if(BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar"))
                            service_name.setText(itemArrayList.get(0).getBdb_name_ar());
                        else
                            service_name.setText(itemArrayList.get(0).getBdb_name());
                        img_company.setImageResource(servicsImgs[categoryIndex]);

                        item_click.setVisibility(View.INVISIBLE);

                        row.addView(layout2);
                    }


                }
                grid.addView(row);



            }
            root.addView(layout3);
            categoryIndex++;
        }

    }

    public static JSONArray getFavorites(final ArrayList<ListServiceModel> itemArrayList)
    {
       // String favoriteStr="\" items \" :[";
        JSONArray favoriteStr =new JSONArray();
        JSONObject temp;
        for(int i=0;i<itemArrayList.size();i++)
        {
            if(!itemArrayList.get(i).getNew_fav().equals(itemArrayList.get(i).getIs_fav())&&itemArrayList.get(i).getNew_fav().equals("1"))
            {
                temp=new JSONObject();
                try {
                    temp.put("bdb_item_id",itemArrayList.get(i).getBdb_ser_id());
                    temp.put("bdb_type","0");
                    favoriteStr.put(temp);
                }
                catch (Exception e){}
            }
        }
        return favoriteStr;
    }
    public static JSONArray getUnFavorites(final ArrayList<ListServiceModel> itemArrayList)
    {
        // String favoriteStr="\" items \" :[";
        JSONArray favoriteStr =new JSONArray();
        JSONObject temp;
        for(int i=0;i<itemArrayList.size();i++)
        {
            if(!itemArrayList.get(i).getNew_fav().equals(itemArrayList.get(i).getIs_fav())&&itemArrayList.get(i).getNew_fav().equals("0"))
            {
                temp=new JSONObject();
                try {
                    temp.put("bdb_item_id",itemArrayList.get(i).getBdb_ser_id());
                    temp.put("bdb_type","0");
                    favoriteStr.put(temp);
                }
                catch (Exception e){}
            }
        }
        return favoriteStr;
    }



}
