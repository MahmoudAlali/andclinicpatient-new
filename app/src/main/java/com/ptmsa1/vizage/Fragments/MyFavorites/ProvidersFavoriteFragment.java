package com.ptmsa1.vizage.Fragments.MyFavorites;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Adapters.FavoritesAdapter;
import com.ptmsa1.vizage.R;

public class ProvidersFavoriteFragment extends Fragment {
    RecyclerView recyclerView;
    public  static FavoritesAdapter favoritesAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_providers_favorite, container, false);
        favoritesAdapter=new FavoritesAdapter(BeautyMainPage.context, APICall.favItemsArrayList);

        recyclerView=view.findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(BeautyMainPage.context));
        recyclerView.setAdapter(favoritesAdapter);

        APICall.getFavorites(BeautyMainPage.context,"1");
        return view;
    }
}