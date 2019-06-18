package com.dcoret.beautyclient.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.Notification;
import com.dcoret.beautyclient.Activities.ShoppingCart;
import com.dcoret.beautyclient.Activities.TabOne;
import com.dcoret.beautyclient.Activities.TabTwo;
import com.dcoret.beautyclient.Activities.TabThree;
import com.dcoret.beautyclient.Adapters.ServicesAdapter;
import com.dcoret.beautyclient.R;

public class ServicesTabsFragment extends Fragment {


    Fragment fragment;
    android.app.FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    TextView servicetab,offertab,maptab;
    ImageButton filter;
    LinearLayout layout_bar;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_reservations2, container, false);

        BeautyMainPage.FRAGMENT_NAME="SERVICETABFRAGMENT";
        Log.d("doback",BeautyMainPage.FRAGMENT_NAME);

        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        filter =view.findViewById(R.id.filter);
        layout_bar =view.findViewById(R.id.layout_bar);
        servicetab=view.findViewById(R.id.servicetabclick);
        offertab=view.findViewById(R.id.offertabclick);
        maptab=view.findViewById(R.id.maptabclick);

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                PopupMenu popup = new PopupMenu(getActivity().getApplicationContext(), layout_bar);
//                //Inflating the Popup using xml file
//                popup.getMenuInflater()
//                        .inflate(R.menu.popup_menu, popup.getMenu());
//                //registering popup with OnMenuItemClickListener
//                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    public boolean onMenuItemClick(MenuItem item) {
//                        return true;
//                    }
//                });
//                popup.show(); //showing popup menu
                final Dialog dialog=new Dialog(BeautyMainPage.context);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                dialog.setContentView(R.layout.filter_dialog_layout);

                //-------------- range price filter--------------------
                final CheckBox price=dialog.findViewById(R.id.price);
                price.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                        if (isChecked) {
                            final Dialog rangePriceDialog = new Dialog(BeautyMainPage.context);
                            rangePriceDialog.setContentView(R.layout.price_range_dialog);
                            rangePriceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                            // get seekbar from view
                            final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) rangePriceDialog.findViewById(R.id.rangeSeekbar5);

                            // get min and max text view
                            final TextView tvMin = (TextView) rangePriceDialog.findViewById(R.id.textMin1);
                            final TextView tvMax = (TextView) rangePriceDialog.findViewById(R.id.textMax1);
                            final EditText Min = rangePriceDialog.findViewById(R.id.minval);
                            final EditText Max = rangePriceDialog.findViewById(R.id.maxval);
                            Button search = rangePriceDialog.findViewById(R.id.search);
                            // set listener
                            rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    tvMin.setText(String.valueOf(minValue));
                                    Min.setText(String.valueOf(minValue));
                                    Max.setText(String.valueOf(maxValue));
                                    tvMax.setText(String.valueOf(maxValue));
                                }
                            });

                            // set final value listener
                            rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
                                @Override
                                public void finalValue(Number minValue, Number maxValue) {
                                    Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
                                }
                            });

                            search.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rangePriceDialog.cancel();
                                    price.setText("السعر" + Min.getText().toString() + "-" + Max.getText().toString());
                                }
                            });

                            rangePriceDialog.show();


                        }
                    }


                });

                //------------------- rate service filte------------------
                final CheckBox rateService=dialog.findViewById(R.id.rate_service);
                rateService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            final Dialog rateServiceDialog = new Dialog(BeautyMainPage.context);
                            rateServiceDialog.setContentView(R.layout.rating_dialog);
                            Button ok = rateServiceDialog.findViewById(R.id.ok);
                            Button cancel = rateServiceDialog.findViewById(R.id.cancel);
                            final RatingBar ratingBar = rateServiceDialog.findViewById(R.id.ratingBar);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rateServiceDialog.cancel();
                                    rateService.setText("تقييم الخدمة " + ratingBar.getRating());
                                }
                            });


                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rateService.setChecked(false);
                                    rateServiceDialog.cancel();
                                }
                            });
                            rateServiceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            rateServiceDialog.show();
                        }
                        }
                });


                //------------------- rate provider filte------------------
                final CheckBox rateProvider=dialog.findViewById(R.id.rate_provoder);
                rateProvider.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                            final Dialog rateProviderDialog = new Dialog(BeautyMainPage.context);
                            rateProviderDialog.setContentView(R.layout.rating_dialog);
                            Button ok = rateProviderDialog.findViewById(R.id.ok);
                            Button cancel = rateProviderDialog.findViewById(R.id.cancel);
                            final RatingBar ratingBar = rateProviderDialog.findViewById(R.id.ratingBar);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rateProviderDialog.cancel();
                                    rateProvider.setText("تقييم المزودة " + ratingBar.getRating());
                                }
                            });


                            cancel.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rateProvider.setChecked(false);
                                    rateProviderDialog.cancel();
                                }
                            });
                            rateProviderDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                            rateProviderDialog.show();
                        }
                    }
                });
                //-------------- range price filter--------------------
                final CheckBox distance=dialog.findViewById(R.id.far);
                distance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                        if (isChecked) {
                            final Dialog rangeDistanceDialog = new Dialog(BeautyMainPage.context);
                            rangeDistanceDialog.setContentView(R.layout.price_range_dialog);
                            rangeDistanceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                            // get seekbar from view
                            final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) rangeDistanceDialog.findViewById(R.id.rangeSeekbar5);
                            rangeSeekbar.setMaxValue(100);
                            // get min and max text view
                            TextView title=rangeDistanceDialog.findViewById(R.id.title);
                            title.setText("Distance Range");
                            final TextView tvMin = (TextView) rangeDistanceDialog.findViewById(R.id.textMin1);
                            final TextView tvMax = (TextView) rangeDistanceDialog.findViewById(R.id.textMax1);
                            final EditText Min = rangeDistanceDialog.findViewById(R.id.minval);
                            final EditText Max = rangeDistanceDialog.findViewById(R.id.maxval);
                            Button search = rangeDistanceDialog.findViewById(R.id.search);
                            // set listener
                            rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                @Override
                                public void valueChanged(Number minValue, Number maxValue) {
                                    tvMin.setText(String.valueOf(minValue));
                                    Min.setText(String.valueOf(minValue));
                                    Max.setText(String.valueOf(maxValue));
                                    tvMax.setText(String.valueOf(maxValue));
                                }
                            });

                            // set final value listener
                            rangeSeekbar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
                                @Override
                                public void finalValue(Number minValue, Number maxValue) {
                                    Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
                                }
                            });

                            search.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    rangeDistanceDialog.cancel();
                                    distance.setText("البعد " + Min.getText().toString() + "-" + Max.getText().toString());
                                }
                            });

                            rangeDistanceDialog.show();


                        }
                    }


                });

                Button filter =dialog.findViewById(R.id.filter);
                filter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        TabOne.browseService();
                    }
                });



                dialog.show();

                    }
                });



        tabselected(servicetab,offertab,maptab);
         fragment = new TabOne();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

        servicetab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(servicetab,offertab,maptab);
                 fragment = new TabOne();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });
        offertab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(offertab,servicetab,maptab);
                 fragment = new TabTwo();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });
        maptab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tabselected(maptab,servicetab,offertab);
                 fragment = new TabThree();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
            }
        });



//
//        FloatingActionButton floatingActionButton=view.findViewById(R.id.fab1);
//        FloatingActionButton floatingActionButton2=view.findViewById(R.id.fab2);


//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Toast.makeText(BeautyMainPage.context,"ookkk",Toast.LENGTH_LONG).show();
//
//                @SuppressLint("ResourceType")
////              final Dialog  dialog = new Dialog(BeautyMainPage.context);
//                final Dialog dialog = new Dialog(BeautyMainPage.context);
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
//                ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(BeautyMainPage.context,R.array.cities,android.R.layout.simple_spinner_item);
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                city_filter_dialog.setAdapter(adapter);
//
//
////
//            }
//        });
//
//        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                final Dialog  dialog = new Dialog(BeautyMainPage.context);
//                final Dialog  dialog = new Dialog(BeautyMainPage.context);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                dialog.setContentView(R.layout.arrangement_dialog);
//                dialog.setTitle("Filtering");
//
//                dialog.show();
//            }
//        });




        return view;
    }


    void tabselected(TextView t1,TextView t2,TextView t3){
        t1.setTextSize(20);
        t1.setTextColor(Color.MAGENTA);
        t2.setTextSize(18);
        t2.setTextColor(Color.BLACK);
        t3.setTextSize(18);
        t3.setTextColor(Color.BLACK);

    }

    public static Menu menu;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.bar_menu_compare,menu);
        this.menu=menu;
        super.onCreateOptionsMenu(menu, inflater);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id==R.id.compare){
            if(ServicesAdapter.comparenum>=2) {
                Log.d("Compare", ServicesAdapter.comparenum+"");
                fragment = new CompareFragment();
                fm = getActivity().getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, fragment);
                fragmentTransaction.commit();
//                Toast.makeText(BeautyMainPage.context,"There no Comparation enugh",Toast.LENGTH_LONG).show();

            }else {
                Toast.makeText(BeautyMainPage.context,"Compare 2 items or more",Toast.LENGTH_LONG).show();

            }
        }else if(id==R.id.shoppingcart){
            Intent intent=new Intent(BeautyMainPage.context,ShoppingCart.class);
            startActivity(intent);

        }else if(id==R.id.notify){
            Intent intent=new Intent(BeautyMainPage.context,Notification.class);
            startActivity(intent);
        }else if(id==R.id.gridlist){
//            TabOne.gridlistitems();
            if (ServicesAdapter.list)
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_grid_on_black_24dp));
            else
            menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_view_list_black_24dp));
        }


        return super.onOptionsItemSelected(item);
    }
}
