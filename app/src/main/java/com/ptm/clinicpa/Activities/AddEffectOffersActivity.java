package com.ptm.clinicpa.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.ptm.clinicpa.Adapters.EffectsOfferAdapter;
import com.ptm.clinicpa.DataModel.AddEffectsIndModel;
import com.ptm.clinicpa.R;

import java.util.ArrayList;

public class AddEffectOffersActivity extends AppCompatActivity {


    public static ArrayList<AddEffectsIndModel> addEffectsIndModels=new ArrayList<>();
    public static String filter="",rtype="",datetype="",date,offertype;
    public static Context context;
    Fragment fragment;
    FragmentManager fm;
    public  static EffectsOfferAdapter effectsAdapter;
    FragmentTransaction fragmentTransaction;
    Button next;
    RecyclerView recyclerView;
    public static  String searchgroup="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_effect_offers);
    }
}
