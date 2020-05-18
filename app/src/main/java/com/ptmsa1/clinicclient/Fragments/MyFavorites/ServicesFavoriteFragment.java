package com.ptmsa1.clinicclient.Fragments.MyFavorites;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptmsa1.clinicclient.API.APICall;
import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.Adapters.FavoritesAdapter;
import com.ptmsa1.clinicclient.R;

public class ServicesFavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    public  static FavoritesAdapter favoritesAdapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_services_favorite, container, false);
        favoritesAdapter=new FavoritesAdapter(BeautyMainPage.context, APICall.favItemsArrayList);

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
        recyclerView.setAdapter(favoritesAdapter);

        APICall.getFavorites(BeautyMainPage.context,"0");
        return view;
    }
}
