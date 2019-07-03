package com.dcoret.beautyclient.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
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
import com.dcoret.beautyclient.DataClass.Cities;
import com.dcoret.beautyclient.DataClass.ServiceFilter;
import com.dcoret.beautyclient.R;
import com.elconfidencial.bubbleshowcase.BubbleShowCaseBuilder;

import java.util.ArrayList;
import java.util.Date;

public class ServicesTabsFragment extends Fragment implements View.OnClickListener {

    CheckBox price,distance,rateService,rateProvider,servicePlace,nameSalonOrProvider,discountVal,activeDate;
    Fragment fragment;
    android.app.FragmentManager fm;
    FragmentTransaction fragmentTransaction;
   public static TextView servicetab,offertab,maptab;
    static ImageButton filter,compare,gridlist;
    LinearLayout layout_bar;
    static ServicesAdapter servicesAdapter;
    Toolbar toolbar;
//    Spinner citiesSpinner;
    public static Boolean gridlistcheck=false;
    String service_place_name="";
//    public static ArrayList<ServiceFilter> ServiceFragment.serviceFilters=new ArrayList<>();
    LinearLayout pages;
    int TABFLAG=1;
    public static int ItemPageNum=4;
    ArrayList<String> citiyname=new ArrayList<>();
    public static int cityId=4;
    TextView pagenum;
    LinearLayout pageNext,pagePrev;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_my_reservations2, container, false);

        //-------------- init service filter array-----------
        // 1 city name, 2 price, 3 rate service ,4 rate provider, 5 distance , 6 saloon or pro name, 7 service place
        pagenum=view.findViewById(R.id.pagenum);
        pageNext=view.findViewById(R.id.pageNext);
        pagePrev=view.findViewById(R.id.pagePrev);
        pagenum.setText("Page"+TabOne.pagenum);
        BeautyMainPage.FRAGMENT_NAME="SERVICETABFRAGMENT";
//        Log.d("doback",BeautyMainPage.FRAGMENT_NAME);
        pageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "4", (TabOne.pagenum+1)+"", BeautyMainPage.context);

            }
        });
        pagePrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "4", (TabOne.pagenum-1)+"", BeautyMainPage.context);

            }
        });

         toolbar = view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
//        citiesSpinner =view.findViewById(R.id.citiesSpinner);
        filter =view.findViewById(R.id.filter);
        compare =view.findViewById(R.id.compare);
        gridlist =view.findViewById(R.id.gridlist);
        layout_bar =view.findViewById(R.id.layout_bar);
        servicetab=view.findViewById(R.id.servicetabclick);
        offertab=view.findViewById(R.id.offertabclick);
        maptab=view.findViewById(R.id.maptabclick);
        pages=view.findViewById(R.id.pages);




        //---------------- cities----------------
        for (int i=0;i<8;i++){
            ServiceFragment.serviceFilters.add(new ServiceFilter(false,""));
        }
        Log.e("ServiceFilterSize",ServiceFragment.serviceFilters.size()+"");
        citiyname.add("Select Citiy");
        for (int i = 0; i < BeautyMainPage.cities.size(); i++) {
            citiyname.add(BeautyMainPage.cities.get(i).getBdb_name());
        }
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<String>(BeautyMainPage.context,  android.R.layout.simple_spinner_dropdown_item,citiyname);
//        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
//        citiesSpinner.setAdapter(adapter);
//
//        if (adapter.getCount()==1){
//            Log.e("getCities","http://clientapp.dcoret.com/api/auth/user/getCities");
//            citiyname.clear();
//            adapter.notifyDataSetChanged();
//        }else {
//            if (citiyname.size()!=0){
//                citiesSpinner.setSelection(cityId);
//            }
//        }

        //bubble info
//        if (cityId==0){
////               new BubbleShowCaseBuilder(getActivity()) //Activity instance
////                    .title(getResources().getString(R.string.ExuseMeAlert))//Any title for the bubble view
////                     .description("Please Select an City for Filter Service and Offers... ")
////                    .targetView(citiesSpinner) //View to point out
////                    .show(); //Display the ShowCase
//        }else {
//            APICall.getcities("http://clientapp.dcoret.com/api/auth/user/getCities",BeautyMainPage.context);
//        }
//        adapter.notifyDataSetChanged();
//        citiesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if (position!=0){
//                    cityId=position;
//                    APICall.setCityId(position);
//                    APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
//
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//        citiesSpinner.setSelection(PlaceServiceFragment.citiyitemSelected);

        //--------------------- back press--
        toolbar.setNavigationOnClickListener(this);

        //-------------------- Grid List---------------
        gridlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridlist();
            }
        });

        //-------------------- compare services----------
        compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ServicesAdapter.comparenum>=2) {
                    Log.d("Compare", ServicesAdapter.comparenum+"");
                    fragment = new CompareFragment();
                    fm = getActivity().getFragmentManager();
                    fragmentTransaction = fm.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment, fragment);
                    fragmentTransaction.commit();

                }else {
                    Toast.makeText(BeautyMainPage.context,"Compare 2 items or more",Toast.LENGTH_LONG).show();

                }
            }
        });




        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (citiesSpinner.getSelectedItemPosition()==0){
//                    ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            APICall.showSweetDialog(BeautyMainPage.context,R.string.ExuseMeAlert,R.string.select_city_alert);
//                        }
//                    });
//                }else
                if (TABFLAG == 1) {

                    final Dialog dialog = new Dialog(BeautyMainPage.context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    dialog.setContentView(R.layout.filter_dialog_layout);

                    //-------------- range price filter--------------------
                    price = dialog.findViewById(R.id.price);
                    price.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                if (price.isChecked()) {
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
                                        rangePriceDialog.dismiss();
                                        price.setText("السعر " + Min.getText().toString() + "-" + Max.getText().toString());
                                        APICall.filterSortAlgorithm(PlaceServiceFragment.placeId+"", Min.getText().toString(), Max.getText().toString());
                                        ServiceFragment.serviceFilters.set(2, new ServiceFilter(true, price.getText().toString()));

                                    }
                                });

                                rangePriceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        price.setChecked(false);
                                        price.setText("السعر");
                                        APICall.filterSortAlgorithm(PlaceServiceFragment.placeId+"", "", "");
                                        ServiceFragment.serviceFilters.set(2, new ServiceFilter(false, price.getText().toString()));
                                    }
                                });

                                rangePriceDialog.show();


                            } else {
                                price.setText("السعر");
                                APICall.filterSortAlgorithm(PlaceServiceFragment.placeId+"", "", "");
                                ServiceFragment.serviceFilters.set(2, new ServiceFilter(false, price.getText().toString()));

                            }
                        }


                    });

                    //------------------- rate service filte------------------
                    rateService = dialog.findViewById(R.id.rate_service);
                    rateService.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                if (rateService.isChecked()) {
                                final Dialog rateServiceDialog = new Dialog(BeautyMainPage.context);
                                rateServiceDialog.setContentView(R.layout.rating_dialog);
                                Button ok = rateServiceDialog.findViewById(R.id.ok);
                                Button cancel = rateServiceDialog.findViewById(R.id.cancel);
                                final RatingBar ratingBar = rateServiceDialog.findViewById(R.id.ratingBar);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        rateServiceDialog.dismiss();
                                        rateService.setText("تقييم الخدمة " + (int) ratingBar.getRating());
                                        APICall.filterSortAlgorithm("5", (int) ratingBar.getRating() + "", (int) ratingBar.getRating() + "");
                                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(true, rateService.getText().toString()));


                                    }
                                });


                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        rateService.setChecked(false);
                                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, rateService.getText().toString()));
                                        rateServiceDialog.dismiss();
                                    }
                                });
                                rateServiceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                rateServiceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        rateService.setChecked(false);
                                        rateService.setText("تقييم الخدمة");
                                        ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, rateService.getText().toString()));

                                        APICall.filterSortAlgorithm("5", "", "");

                                    }
                                });

                                rateServiceDialog.show();
                            } else {
                                rateService.setText("تقييم الخدمة");
                                APICall.filterSortAlgorithm("22", "", "");
                                ServiceFragment.serviceFilters.set(3, new ServiceFilter(false, rateService.getText().toString()));


                            }
                        }
                    });


                    //------------------- rate provider filte------------------
                    rateProvider = dialog.findViewById(R.id.rate_provoder);
                    rateProvider.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                  if (rateProvider.isChecked()) {
                                final Dialog rateProviderDialog = new Dialog(BeautyMainPage.context);
                                rateProviderDialog.setContentView(R.layout.rating_dialog);
                                Button ok = rateProviderDialog.findViewById(R.id.ok);
                                Button cancel = rateProviderDialog.findViewById(R.id.cancel);
                                final RatingBar ratingBar = rateProviderDialog.findViewById(R.id.ratingBar);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        rateProviderDialog.dismiss();
                                        rateProvider.setText("تقييم المزودة " + (int) ratingBar.getRating());
                                        APICall.filterSortAlgorithm("28", (int) ratingBar.getRating() + "", (int) ratingBar.getRating() + "");
                                        ServiceFragment.serviceFilters.set(4, new ServiceFilter(true, rateProvider.getText().toString()));
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

                                rateProviderDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        rateProvider.setChecked(false);
                                        rateProvider.setText("تقييم المزودة");
                                        APICall.filterSortAlgorithm("28", "", "");
                                        ServiceFragment.serviceFilters.set(4, new ServiceFilter(false, rateProvider.getText().toString()));
                                    }
                                });
                                rateProviderDialog.show();
                            } else {
                                rateProvider.setText("تقييم المزودة");
                                APICall.filterSortAlgorithm("28", "", "");
                                ServiceFragment.serviceFilters.set(4, new ServiceFilter(false, rateProvider.getText().toString()));

                            }
                        }
                    });
                    //-------------- range distance filter--------------------
                    distance = dialog.findViewById(R.id.far);
                    distance.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                if (distance.isChecked()) {
                                final Dialog rangeDistanceDialog = new Dialog(BeautyMainPage.context);
                                rangeDistanceDialog.setContentView(R.layout.price_range_dialog);
                                rangeDistanceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                // get seekbar from view
                                final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) rangeDistanceDialog.findViewById(R.id.rangeSeekbar5);
//                            rangeSeekbar.setMaxValue(100);
                                // get min and max text view
                                TextView title = rangeDistanceDialog.findViewById(R.id.title);
                                title.setText("Distance Range");
                                final TextView tvMin = rangeDistanceDialog.findViewById(R.id.textMin1);
                                final TextView tvMax = rangeDistanceDialog.findViewById(R.id.textMax1);
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
                                        rangeDistanceDialog.dismiss();
                                        distance.setText("البعد " + Min.getText().toString() + "-" + Max.getText().toString());
                                        APICall.filterSortAlgorithm("2", Min.getText().toString(), Max.getText().toString());
                                        ServiceFragment.serviceFilters.set(5, new ServiceFilter(true, distance.getText().toString()));

                                    }
                                });


                                rangeDistanceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        distance.setChecked(false);
                                        distance.setText("البعد");
                                        APICall.filterSortAlgorithm("2", "", "");
                                        ServiceFragment.serviceFilters.set(5, new ServiceFilter(false, distance.getText().toString()));

                                    }
                                });
                                rangeDistanceDialog.show();

                            } else {
                                distance.setText("البعد");
                                APICall.filterSortAlgorithm("2", "", "");
                                ServiceFragment.serviceFilters.set(5, new ServiceFilter(false, distance.getText().toString()));

                            }
                        }


                    });




                    Button filterBtnDialog = dialog.findViewById(R.id.filter);
                    filterBtnDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
//                        TabOne.browseService();

                            APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
//                            APICall.clearFilterList();
//                            APICall.filterSortAlgorithm("6",  citiesSpinner.getSelectedItemPosition()+"", "0");



                        }
                    });

                    nameSalonOrProvider=dialog.findViewById(R.id.salon_name_provider);
                    nameSalonOrProvider.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (nameSalonOrProvider.isChecked()) {
                                final Dialog namesalonDialog = new Dialog(BeautyMainPage.context);
                                namesalonDialog.setContentView(R.layout.name_saloon_or_provider);
                                namesalonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                    final EditText name=namesalonDialog.findViewById(R.id.name);
                                Button search = namesalonDialog.findViewById(R.id.search);
                                search.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (!name.getText().toString().isEmpty()){
                                            namesalonDialog.dismiss();
                                            nameSalonOrProvider.setText("الصالون او اسم المزودة: " +name.getText().toString());
                                            APICall.filterSortAlgorithm("3","\""+name.getText().toString()+"\"" , null);
                                            ServiceFragment.serviceFilters.set(6, new ServiceFilter(true, nameSalonOrProvider.getText().toString()));

                                        }else {
                                            namesalonDialog.cancel();
                                            nameSalonOrProvider.setText("الصالون او اسم المزودة");
                                            APICall.filterSortAlgorithm("3", "", "");
                                            ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, nameSalonOrProvider.getText().toString()));

                                        }




                                    }
                                });
                                namesalonDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        nameSalonOrProvider.setChecked(false);
                                        nameSalonOrProvider.setText("الصالون او اسم المزودة");
                                        APICall.filterSortAlgorithm("3", "", "");
                                        ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, nameSalonOrProvider.getText().toString()));

                                    }
                                });
                                namesalonDialog.show();

                            }else {
//                                nameSalonOrProvider.setChecked(false);
                                nameSalonOrProvider.setText("الصالون او اسم المزودة");
                                APICall.filterSortAlgorithm("3", "", "");
                                ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, nameSalonOrProvider.getText().toString()));

                            }
                            }
                    });




//
//                    servicePlace=dialog.findViewById(R.id.service_place);
//                    servicePlace.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            if (servicePlace.isChecked()) {
//                                final Dialog servicePlaceDialog = new Dialog(BeautyMainPage.context);
//                                servicePlaceDialog.setContentView(R.layout.service_place);
//                                servicePlaceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//                                final RadioGroup service_radio=servicePlaceDialog.findViewById(R.id.service_radio);
//                                final CheckBox salon=servicePlaceDialog.findViewById(R.id.salon);
//                                final CheckBox home=servicePlaceDialog.findViewById(R.id.home);
//                                final CheckBox hall=servicePlaceDialog.findViewById(R.id.hall);
//                                final CheckBox hotel=servicePlaceDialog.findViewById(R.id.hotel);
//                                Button search = servicePlaceDialog.findViewById(R.id.search);
//
//
//                                service_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//                                    @Override
//                                    public void onCheckedChanged(RadioGroup group, int checkedId) {
//                                        switch (checkedId){
//                                            case R.id.salon:
//                                                service_place_name="salon";
//                                                break;
//                                            case R.id.home:
//                                                service_place_name="home";
//                                                break;
//                                            case R.id.hall:
//                                                service_place_name="hall";
//                                                break;
//                                            case R.id.hotel:
//                                                service_place_name="hotel";
//                                                break;
//                                        }
//                                    }
//                                });
//
//                                search.setOnClickListener(new View.OnClickListener() {
//                                    @Override
//                                    public void onClick(View v) {
//                                            servicePlaceDialog.dismiss();
//                                            servicePlace.setText("مكان الخدمة: "+service_place_name );
//                                            APICall.filterSortAlgorithm("3","\""+service_place_name+ "\"", null);
//                                            ServiceFragment.serviceFilters.set(7, new ServiceFilter(true, servicePlace.getText().toString()));
//
//                                    }
//                                });
//                                servicePlaceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                                    @Override
//                                    public void onCancel(DialogInterface dialog) {
//                                        servicePlace.setChecked(false);
//                                        servicePlace.setText("مكان الخدمة");
//                                        APICall.filterSortAlgorithm("3", "", "");
//                                        ServiceFragment.serviceFilters.set(7, new ServiceFilter(false, nameSalonOrProvider.getText().toString()));
//
//                                    }
//                                });
//                                servicePlaceDialog.show();
//
//                            }else {
//                                servicePlace.setChecked(false);
//                                servicePlace.setText("مكان الخدمة");
//                                APICall.filterSortAlgorithm("3", "", "");
//                                ServiceFragment.serviceFilters.set(7, new ServiceFilter(false, nameSalonOrProvider.getText().toString()));
//
//                            }
//                        }
//                    });




                    for (int i = 0; i < ServiceFragment.serviceFilters.size(); i++) {
                        if (i == 2) {
                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                                price.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
                                price.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());

                            }
                        } else if (i == 3) {
                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                                rateService.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
                                rateService.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                            }
                        } else if (i == 4) {
                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                                rateProvider.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
                            rateProvider.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                            }
                        } else if (i == 5) {
                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                                distance.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
                                distance.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                            }
                        } else if (i == 6) {
                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                                nameSalonOrProvider.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
                                nameSalonOrProvider.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                            }
                        } else if (i == 7) {
                            if (!ServiceFragment.serviceFilters.get(i).getFilterName().equals("")) {
                                servicePlace.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
                                servicePlace.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                            }
                        } else if (i == 1) {
//                                    price.setText(ServiceFragment.serviceFilters.get(i).getFilterName());
//                                    price.setChecked(ServiceFragment.serviceFilters.get(i).getIschecked());
                        }

                    }


                    dialog.show();
                } else if (TABFLAG == 2) {
                    final Dialog dialog = new Dialog(BeautyMainPage.context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    dialog.setContentView(R.layout.filter_dialog_offer_layout);

                    //-------------- range price filter--------------------
                    final CheckBox price = dialog.findViewById(R.id.price);
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
                                final TextView tvMin = rangePriceDialog.findViewById(R.id.textMin1);
                                final TextView tvMax = rangePriceDialog.findViewById(R.id.textMax1);
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
                                        rangePriceDialog.dismiss();
                                        price.setText("السعر " + Min.getText().toString() + "-" + Max.getText().toString());
                                        APICall.filterSortAlgorithm("19", Max.getText().toString(), Min.getText().toString());
                                    }
                                });

                                rangePriceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        price.setChecked(false);
                                        price.setText("السعر");
                                        APICall.filterSortAlgorithm("19", "", "");

                                    }
                                });

                                rangePriceDialog.show();


                            } else {
                                price.setText("السعر");
                                APICall.filterSortAlgorithm("19", "", "");
                            }
                        }


                    });

                    //------------------- rate service filte------------------
                    final CheckBox rateService = dialog.findViewById(R.id.rate_service);
                    //------------- this service not available now ------------
                    rateService.setText("تقييم العرض");
                    rateService.setEnabled(false);
                    //-----------------------------------------------------
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
                                        rateServiceDialog.dismiss();
                                        rateService.setText("تقييم العرض " + (int) ratingBar.getRating());
                                        APICall.filterSortAlgorithm("5", (int) ratingBar.getRating() + "", (int) ratingBar.getRating() + "");
                                    }
                                });


                                cancel.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        rateService.setChecked(false);
                                        rateServiceDialog.dismiss();
                                    }
                                });
                                rateServiceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                rateServiceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        rateService.setChecked(false);
                                        rateService.setText("تقييم العرض");
                                        APICall.filterSortAlgorithm("22", "", "");

                                    }
                                });

                                rateServiceDialog.show();
                            } else {
                                rateService.setText("تقييم العرض");
                                APICall.filterSortAlgorithm("22", "", "");

                            }
                        }
                    });


                    //------------------- rate provider filter------------------
                    final CheckBox rateProvider = dialog.findViewById(R.id.rate_provoder);
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
                                        rateProviderDialog.dismiss();
                                        rateProvider.setText("تقييم المزودة " + (int) ratingBar.getRating());
                                        APICall.filterSortAlgorithm("28", (int) ratingBar.getRating() + "", (int) ratingBar.getRating() + "");
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

                                rateProviderDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        rateProvider.setChecked(false);
                                        rateProvider.setText("تقييم المزودة");
                                        APICall.filterSortAlgorithm("26", "", "");
                                    }
                                });
                                rateProviderDialog.show();
                            } else {
                                rateProvider.setText("تقييم المزودة");
                                APICall.filterSortAlgorithm("26", "", "");
                            }
                        }
                    });
                    //-------------- range distance filter--------------------
                    final CheckBox distance = dialog.findViewById(R.id.far);
                    distance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                            if (isChecked) {
                                final Dialog rangeDistanceDialog = new Dialog(BeautyMainPage.context);
                                rangeDistanceDialog.setContentView(R.layout.price_range_dialog);
                                rangeDistanceDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                                // get seekbar from view
                                final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) rangeDistanceDialog.findViewById(R.id.rangeSeekbar5);
//                            rangeSeekbar.setMaxValue(100);
                                // get min and max text view
                                TextView title = rangeDistanceDialog.findViewById(R.id.title);
                                title.setText("Distance Range");
                                final TextView tvMin = rangeDistanceDialog.findViewById(R.id.textMin1);
                                final TextView tvMax = rangeDistanceDialog.findViewById(R.id.textMax1);
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
                                        rangeDistanceDialog.dismiss();
                                        distance.setText("البعد " + Min.getText().toString() + "-" + Max.getText().toString());
                                        APICall.filterSortAlgorithm("2", Min.getText().toString(), Max.getText().toString());
                                    }
                                });


                                rangeDistanceDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        distance.setChecked(false);
                                        distance.setText("البعد");
                                        APICall.filterSortAlgorithm("2", "", "");
                                    }
                                });
                                rangeDistanceDialog.show();

                            } else {
                                distance.setText("البعد");
                                APICall.filterSortAlgorithm("2", "", "");
                            }
                        }


                    });

                    final Button filterBtnDialog = dialog.findViewById(R.id.filter);
                    filterBtnDialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
//                        TabOne.browseService();

                            APICall.automatedBrowse("http://clientapp.dcoret.com/api/service/automatedBrowse", "en", "4", "1", BeautyMainPage.context);
                            APICall.clearFilterList();

                        }
                    });

                    //---------------- active Date--------------------
                    activeDate=dialog.findViewById(R.id.activeDate);
                    activeDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (activeDate.isChecked()) {
                                final Dialog namesalonDialog = new Dialog(BeautyMainPage.context);
                                namesalonDialog.setContentView(R.layout.active_date_dialog);
                                namesalonDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                final DatePicker date=namesalonDialog.findViewById(R.id.date);
                                Button search = namesalonDialog.findViewById(R.id.search);
                                search.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (date.getDayOfMonth()!=0){
                                            namesalonDialog.dismiss();
                                            String dateText=date.getMonth()+":"+date.getDayOfMonth();
                                            activeDate.setText("التاريخ النشط: " +dateText);
                                            APICall.filterSortAlgorithm("3","\""+dateText+"\"" , null);
                                            ServiceFragment.serviceFilters.set(6, new ServiceFilter(true, activeDate.getText().toString()));

                                        }else {
                                            namesalonDialog.cancel();
                                            activeDate.setText("التاريخ النشط");
                                            APICall.filterSortAlgorithm("3", "", "");
                                            ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, activeDate.getText().toString()));

                                        }




                                    }
                                });
                                namesalonDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        activeDate.setChecked(false);
                                        activeDate.setText("التاريخ النشط");
                                        APICall.filterSortAlgorithm("3", "", "");
                                        ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, activeDate.getText().toString()));

                                    }
                                });
                                namesalonDialog.show();

                            }else {
//                                nameSalonOrProvider.setChecked(false);
                                activeDate.setText("التاريخ النشط");
                                APICall.filterSortAlgorithm("3", "", "");
                                ServiceFragment.serviceFilters.set(6, new ServiceFilter(false, activeDate.getText().toString()));

                            }
                        }
                    });

                    //---------------- Discount val--------------------
                    discountVal=dialog.findViewById(R.id.discountVal);
                    discountVal.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (discountVal.isChecked()) {
                                final Dialog discountDialog = new Dialog(BeautyMainPage.context);

                                discountDialog.setContentView(R.layout.discount_value_percent_dialog);
                                discountDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                // get seekbar from view
                                final CrystalRangeSeekbar rangeSeekbar = (CrystalRangeSeekbar) discountDialog.findViewById(R.id.rangeSeekbar5);
                                rangeSeekbar.setMaxValue(100);
                                // get min and max text discountDialog
                                final TextView tvMin = discountDialog.findViewById(R.id.textMin1);
                                final TextView tvMax = discountDialog.findViewById(R.id.textMax1);
                                final EditText Min = discountDialog.findViewById(R.id.minval);
                                final EditText Max = discountDialog.findViewById(R.id.maxval);
                                Button search = discountDialog.findViewById(R.id.search);
                                // set listener
                                rangeSeekbar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
                                    @Override
                                    public void valueChanged(Number minValue, Number maxValue) {
                                        tvMin.setText(String.valueOf(minValue)+"%");
                                        Min.setText(String.valueOf(minValue));
                                        Max.setText(String.valueOf(maxValue));
                                        tvMax.setText(String.valueOf(maxValue)+"%");
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
                                        discountDialog.dismiss();
                                        discountVal.setText("نسبة الخصم المئوية:  " + Min.getText().toString() + "% -" + Max.getText().toString()+"%");
                                        APICall.filterSortAlgorithm("19", Max.getText().toString(), Min.getText().toString());
                                    }
                                });

                                discountDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        discountVal.setChecked(false);
                                        discountVal.setText("نسبة الخصم المئوية");
                                        APICall.filterSortAlgorithm("19", "", "");

                                    }
                                });

                                discountDialog.show();


                            } else {
                                discountVal.setText("نسبة الخصم المئوية");
                                APICall.filterSortAlgorithm("19", "", "");
                            }
                        }
                    });


                    dialog.show();
                }


            }

                });


        gridlist.setImageResource(R.drawable.ic_view_list_black_24dp);
        tabselected(servicetab,offertab,maptab);
         fragment = new TabOne();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.commit();

        servicetab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pages.setVisibility(View.VISIBLE);
                TABFLAG=1;
                gridlist.setImageResource(R.drawable.ic_view_list_black_24dp);
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
                TABFLAG=2;
                gridlist.setImageResource(R.drawable.ic_view_list_black_24dp);
                pages.setVisibility(View.VISIBLE);
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
                TABFLAG=3;
                gridlist.setImageResource(R.drawable.ic_view_list_black_24dp);
                fragment = new TabThree();
//                fragment = new MapFragment();
                fm = getFragmentManager();
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.container, fragment);
                fragmentTransaction.commit();
                pages.setVisibility(View.GONE);

            }
        });


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

    public static void gridlist(){
        if (!gridlistcheck){
            ((AppCompatActivity)BeautyMainPage.context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    servicesAdapter=new ServicesAdapter(BeautyMainPage.context,TabOne.arrayList,R.layout.service_layout_adapter_grid_last);
                    LinearLayoutManager manager = new GridLayoutManager(BeautyMainPage.context,2);
                    TabOne.recyclerView.setLayoutManager(manager);
                    TabOne.recyclerView.setAdapter(servicesAdapter);
                    servicesAdapter.notifyDataSetChanged();
                    gridlistcheck=true;
                    gridlist.setImageResource(R.drawable.ic_grid_on_black_24dp);
                }
            });
        }else {
            servicesAdapter=new ServicesAdapter(BeautyMainPage.context,  TabOne.arrayList,R.layout.service_layout_adapter_last);
            LinearLayoutManager manager = new LinearLayoutManager(BeautyMainPage.context,LinearLayoutManager.VERTICAL,false);
            TabOne.recyclerView.setLayoutManager(manager);
            TabOne.recyclerView.setAdapter(servicesAdapter);
            gridlistcheck=false;
            gridlist.setImageResource(R.drawable.ic_view_list_black_24dp);

        }


    }

    //------------ not used---------------
    public static Menu menu;
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.bar_menu_compare,menu);
//        this.menu=menu;
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

    @Override
    public void onClick(View v) {
        ((AppCompatActivity)BeautyMainPage.context).onBackPressed();
    }
}
