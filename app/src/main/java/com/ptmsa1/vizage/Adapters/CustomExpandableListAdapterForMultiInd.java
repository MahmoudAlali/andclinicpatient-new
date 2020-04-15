package com.ptmsa1.vizage.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Activities.MapActivity;
import com.ptmsa1.vizage.DataModel.SearchBookingDataSTR;
import com.ptmsa1.vizage.DataModel.SerchGroupBookingData;
import com.ptmsa1.vizage.Fragments.MultiIndividualBookingReservationFragment;
import com.ptmsa1.vizage.Fragments.PlaceServiceMultipleBookingFragment;
import com.ptmsa1.vizage.MapsActivityLocation;
import com.ptmsa1.vizage.R;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class
CustomExpandableListAdapterForMultiInd extends BaseExpandableListAdapter {
    private Context context;
    private List<SerchGroupBookingData> expandableListTitle;
    private List<SerchGroupBookingData> expandableListDetail1;
    ArrayList<String> salons;
    ArrayList<SearchBookingDataSTR> searchBookingDataSTRS;
    ArrayList<Boolean> isopen=new ArrayList<>();
    ArrayList<TextView> listTitleTextViews=new ArrayList<>();
    Map<String,ArrayList<SearchBookingDataSTR>> stringArrayListHashMap;

    public CustomExpandableListAdapterForMultiInd(Context context, ArrayList<String> salons, Map<String, ArrayList<SearchBookingDataSTR>> stringArrayListHashMap) {
        this.context = context;
        this.salons = salons;
        this.stringArrayListHashMap = stringArrayListHashMap;
    }

    @Override
    public int getGroupCount() {
        return salons.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return stringArrayListHashMap.get(salons.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return salons.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition);
    }
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        //------------------- titles--------------------------
        String listTitle =  (String) getGroup(groupPosition)+" : "+APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getTotal_price())+"  "+((AppCompatActivity)context).getResources().getString(R.string.ryal);
            Log.e("LISTTITLE",listTitle);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
            final LinearLayout listTitleTextView =convertView
                    .findViewById(R.id.listTitle);
            final TextView listTitleText =convertView
                    .findViewById(R.id.listTitle2);
            ImageView book =  convertView
                    .findViewById(R.id.book);
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(APICall.isGuest(context).equals("0"))

                            {
                                Log.e("choose",MultiIndividualBookingReservationFragment.choose_occision.getSelectedItem().toString());
                                if (MultiIndividualBookingReservationFragment.choose_occision.getSelectedItemPosition()==2) {
                                    APICall.addGroupItem(APICall.getClientsInfoforIndividual(salons, stringArrayListHashMap, groupPosition, MultiIndividualBookingReservationFragment.is_group_booking), BeautyMainPage.context);
                                }else {
                                    APICall.addGroupItemMultDates(APICall.getClientsInfoforIndividualMultiDates(salons, stringArrayListHashMap, groupPosition,MultiIndividualBookingReservationFragment.is_group_booking), BeautyMainPage.context);

                                }
                            }
                            else
                                APICall.showNeedToSignInDialog(context);

//                        Toast.makeText(context,"book is selected",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            listTitleText.setText(listTitle);

            ImageView location=convertView.findViewById(R.id.location);


            location.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getBdb_loc_lat()!=null
                            && !stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getBdb_loc_lat().equals("null")
                            &&!stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getBdb_loc_lat().equals("")
                            && stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getBdb_loc_long()!=null
                            && !stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getBdb_loc_long().equals("null")
                            && !stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getBdb_loc_long().equals("")

                    ) {
                        Intent intent = new Intent(context, MapsActivityLocation.class);
                        intent.putExtra("lat", Double.parseDouble(stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getBdb_loc_lat()));
                        intent.putExtra("lang", Double.parseDouble(stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getBdb_loc_long()));

                        context.startActivity(intent);
                    }
                }
            });
//            listTitleTextViews.add(listTitleTextView);
//            listTitleTextViews.get(groupPosition).setText( listTitleTextViews.get(groupPosition).getText().toString()+" : "+stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getTotal_price()+" R");

//            Log.e("pricegroup",stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getTotal_price());
//            Log.e("pricegroup",groupPosition+"");
            String place="";
            TextView costDetails=convertView.findViewById(R.id.costDetails);
//            TextView costDetails=convertView.findViewById(R.id.costDetails);
            try {
                double main_cost = Double.parseDouble(stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getTotal_price()) ;
                double jcost = Double.parseDouble(stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getJourneyCost());

                BigDecimal a = new BigDecimal(main_cost);
                BigDecimal a1 = new BigDecimal(jcost);
                BigDecimal m_cost = a.setScale(0, RoundingMode.UP);
                BigDecimal j_cost = a1.setScale(0, RoundingMode.UP);
                costDetails.setText(context.getResources().getString(R.string.cost_main)+" "+context.getResources().getString(R.string.ryal)+", "+m_cost+" "+context.getResources().getString(R.string.journey_cost)+": "+j_cost+" "+context.getResources().getString(R.string.ryal));

            }catch (Exception e){
                e.printStackTrace();
            }
            if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 1) {
                place=context.getResources().getString(R.string.salon);
                costDetails.setVisibility(View.GONE);
            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 2) {
                place=context.getResources().getString(R.string.home);
                costDetails.setVisibility(View.VISIBLE);
            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 3) {
                place=context.getResources().getString(R.string.hall);
                costDetails.setVisibility(View.VISIBLE);
            } else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition() == 4) {
                place=context.getResources().getString(R.string.hotel);
                costDetails.setVisibility(View.VISIBLE);
            }

            TextView place1=convertView.findViewById(R.id.place);
            place1.setText(place);

            listTitleText.setTypeface(null, Typeface.BOLD);
            return convertView;
        }else {
            return convertView;

        }

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        try {


//            for (int j = 0; j < expandableListDetail1.get(groupPosition).getCompleteSolutions().size(); j++) {
                if (convertView == null) {
                    LayoutInflater layoutInflater = (LayoutInflater) this.context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = layoutInflater.inflate(R.layout.result_group_reservation_layout, null);
                }
//               listTitleTextViews.get(groupPosition).setText( listTitleTextViews.get(groupPosition).getText().toString()+":"+stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getTotal_price());
                TextView client_name, salon_name;
                LinearLayout service_layout;
                client_name = convertView.findViewById(R.id.client_name);
                salon_name = convertView.findViewById(R.id.salon_name);
                service_layout = convertView.findViewById(R.id.service_layout);
                client_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getClient_name());
                salon_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSalon_name());

//                stringArrayListHashMap.clear();


            client_name.setTextColor(Color.parseColor("#000000"));
            salon_name.setTextColor(Color.parseColor("#000000"));
                //----------------- error in this if ----------
            Log.e("postionISCU",stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getIs_current_user());
            Log.e("postionISCU",salons.get(groupPosition));
            if (stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getIs_current_user().equals("1")){
                   Log.e("postionISCU",stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getIs_current_user());
                    client_name.setTextColor(Color.parseColor("#D3295F"));
//                    client_name.setHintTextColor(R.color.colorAccentend);
                    salon_name.setTextColor(Color.parseColor("#D3295F"));
                }
                //--------------------------------------------------

                Log.e("postion " ,stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().size() + "");
//            stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().clear();

                service_layout.removeAllViews();
                for (int i = 0; i < stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().size(); i++) {
                    View layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.service_layout, service_layout, false);
                    TextView service_name = layout2.findViewById(R.id.service_name);
                    TextView employee_name = layout2.findViewById(R.id.employee_name);
//                    TextView day = layout2.findViewById(R.id.day);
                    TextView time = layout2.findViewById(R.id.time);

//                    APICall.getSupName(employee_name,stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSup_id(), BeautyMainPage.context);
                  String priceService="";

                   try {
                       if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==1) {
                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_salon_price();
                       }else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==2) {
                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_home_price();
                       }else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==3) {
                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_hall_price();
                       }else if (PlaceServiceMultipleBookingFragment.placeSpinner.getSelectedItemPosition()==4) {
                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_hotel_price();

                       }
                   }catch (Exception e){
                       e.printStackTrace();
//                       if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==1) {
//                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_salon_price();
//
//                       }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==2) {
                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_salon_price();
//
//                       }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==3) {
//                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_hall_price();
//
//                       }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==4) {
//                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_hotel_price();
//
//                       }


                   }
                   if (!stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getPart_num().equals("2"))
                   if (BeautyMainPage.context.getResources().getString(R.string.locale).equals("ar")){
                       service_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSer_name_ar()+" : "+APICall.convertToArabic(priceService)+" "+((AppCompatActivity)context).getResources().getString(R.string.ryal));
                   }else {
                       service_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSer_name()+" : "+APICall.convertToArabic(priceService)+" "+((AppCompatActivity)context).getResources().getString(R.string.ryal));
                   }


                    employee_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getEmp_name());
//                    day.setText(PlaceServiceGroupFragment.dateFilter);
                    time.setText(APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getFrom())+"~"+APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getTo()));
                    service_layout.addView(layout2);
                }

        }catch (Exception e){
            e.printStackTrace();
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
