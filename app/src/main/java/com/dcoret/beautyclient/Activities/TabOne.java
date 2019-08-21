package com.dcoret.beautyclient.Activities;



import android.app.Dialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.DataClass.BrowseServiceItem;
import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.DataClass.Location_Beauty;
import com.dcoret.beautyclient.Fragments.ServicesTabsFragment;
import com.dcoret.beautyclient.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class TabOne extends Fragment {

    public static RecyclerView recyclerView;
    public static   ArrayList<BrowseServiceItem>  arrayList=new ArrayList<>();
    public static  ServicesAdapter servicesAdapter;

    public static SwipeRefreshLayout pullToRefresh;


    static View view;
    ImageButton filter;
    public static int pagenum=1;
    @Nullable
    @Override
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.tab_one,container,false);
        filter =view.findViewById(R.id.filter);
         pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                arrayList.clear();
                servicesAdapter.notifyDataSetChanged();
                    //---------------------call API for Services and get items-------------
                    APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "4", pagenum+"", BeautyMainPage.context);
            }
        });

        if (ServicesTabsFragment.updateServ){
            APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "4", pagenum+"", BeautyMainPage.context);
            ServicesTabsFragment.updateServ=false;
            ServicesTabsFragment.updateoffr=true;
        }

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setHasFixedSize(true);
        servicesAdapter=new ServicesAdapter(BeautyMainPage.context,  TabOne.arrayList,R.layout.service_layout_adapter_last);
        LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(servicesAdapter);

            return view;
        }


    //------------- when refresh DATA you must notify adapter---------
    public static void refreshRV(){
        servicesAdapter.notifyDataSetChanged();
    }


}


