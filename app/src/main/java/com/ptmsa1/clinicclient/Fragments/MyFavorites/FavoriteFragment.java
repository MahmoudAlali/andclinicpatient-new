package com.ptmsa1.clinicclient.Fragments.MyFavorites;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ptmsa1.clinicclient.Activities.BeautyMainPage;
import com.ptmsa1.clinicclient.R;

public class FavoriteFragment extends Fragment {
    static   String[] items={"Service1","Service2","Service3","Service4","Service5","Service6","Service7","Service8","Service9","Service10"};

    RecyclerView recyclerView;
    public static  String name="offerfragment";

    Toolbar toolbar;
    TextView servicesFav,providersFav;
    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_favoriate_fragment, container, false);
        servicesFav = view.findViewById(R.id.serviceFav);
        providersFav = view.findViewById(R.id.providersFav);


        toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });

        providersFav.setBackgroundResource(android.R.color.transparent);
        servicesFav.setBackgroundResource(R.drawable.shadow_blue_c7);

        fragment=new ServicesFavoriteFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment2, fragment);
        fragmentTransaction.commit();

        servicesFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                providersFav.setBackgroundResource(android.R.color.transparent);

                servicesFav.setBackgroundResource(R.drawable.shadow_blue_c7);
                fragment=new ServicesFavoriteFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment2, fragment);
                fragmentTransaction.commit();
            }
        });
        providersFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                servicesFav.setBackgroundResource(android.R.color.transparent);

                providersFav.setBackgroundResource(R.drawable.shadow_blue_c7);
                fragment=new ProvidersFavoriteFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment2, fragment);
                fragmentTransaction.commit();
            }
        });
        /*recyclerView= view.findViewById(R.id.review);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(new FavouriteAdapter(getActivity().getApplicationContext(),items));
        toolbar=view.findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If the navigation drawer is not open then open it, if its already open then close it.
                if(!BeautyMainPage.mDrawerLayout.isDrawerOpen(GravityCompat.START)) BeautyMainPage.mDrawerLayout.openDrawer(Gravity.START);
                else BeautyMainPage.mDrawerLayout.closeDrawer(Gravity.END);
            }
        });
*/

        return view;
    }
}
