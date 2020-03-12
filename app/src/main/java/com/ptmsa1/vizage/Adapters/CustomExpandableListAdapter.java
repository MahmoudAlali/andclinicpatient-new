package com.ptmsa1.vizage.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.Activities.OfferBookingResult;
import com.ptmsa1.vizage.DataModel.SearchBookingDataSTR;
import com.ptmsa1.vizage.DataModel.SerchGroupBookingData;
import com.ptmsa1.vizage.Fragments.GroupReservationFragment;
import com.ptmsa1.vizage.Fragments.GroupReservationResultActivity;
import com.ptmsa1.vizage.Fragments.GroupReservationOtherResultActivity;
import com.ptmsa1.vizage.Fragments.GroupReservationOthersFragment;
import com.ptmsa1.vizage.Fragments.BookingIndvidualActivity;
import com.ptmsa1.vizage.Fragments.PlaceServiceFragment;
import com.ptmsa1.vizage.Activities.TabOne;
import com.ptmsa1.vizage.Fragments.PlaceServiceGroupOthersFragment;
import com.ptmsa1.vizage.Fragments.MultiBookingIndividualResultActivity;
import com.ptmsa1.vizage.Fragments.MultiIndividualBookingReservationFragment;
import com.ptmsa1.vizage.Fragments.PlaceServiceGroupFragment;
import com.ptmsa1.vizage.Fragments.PlaceServiceMultipleBookingFragment;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class
CustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<SerchGroupBookingData> expandableListTitle;
    private List<SerchGroupBookingData> expandableListDetail1;
    ArrayList<String> salons;
    ArrayList<SearchBookingDataSTR> searchBookingDataSTRS;
    ArrayList<Boolean> isopen=new ArrayList<>();
    ArrayList<TextView> listTitleTextViews=new ArrayList<>();
    Map<String,ArrayList<SearchBookingDataSTR>> stringArrayListHashMap;

    public CustomExpandableListAdapter(Context context, ArrayList<String> salons, Map<String, ArrayList<SearchBookingDataSTR>> stringArrayListHashMap) {
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
        String listTitle =  (String) getGroup(groupPosition);
//                +" : "+stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getTotal_price()+" "+((AppCompatActivity)context).getResources().getString(R.string.ryal);
            Log.e("LISTTITLE",listTitle);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
            final TextView listTitleTextView = (TextView) convertView
                    .findViewById(R.id.listTitle);
            TextView book =  convertView
                    .findViewById(R.id.book);

            if ( BeautyMainPage.FRAGMENT_NAME.equals("BookingIndvidualActivity"))
            if (stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getSalon_id().equals(TabOne.bdb_sup_id)){
                listTitleTextView.setBackgroundResource(R.color.primev1);
            }


            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(APICall.isGuest(context).equals("0"))

                            {
                                try {
                                    if (BeautyMainPage.FRAGMENT_NAME.equals("GroupReservationResultFragment")) {
                                        APICall.addGroupItem(APICall.getClientsInfo(salons, stringArrayListHashMap, groupPosition,GroupReservationFragment.is_group_booking,APICall.dateforgroupbooking ), GroupReservationResultActivity.context);
//                        Toast.makeText(context,"book is selected",Toast.LENGTH_SHORT).show();
                                    }else if (BeautyMainPage.FRAGMENT_NAME.equals("BookingIndvidualActivity")) {
                                        APICall.addGroupItemInd(APICall.getClientsInfoInd(salons, stringArrayListHashMap, groupPosition,BookingIndvidualActivity.bdb_is_groupbooking, PlaceServiceFragment.date.getText().toString()), BookingIndvidualActivity.context);
//                        Toast.makeText(context,"book is selected",Toast.LENGTH_SHORT).show();
                                    }else if (BeautyMainPage.FRAGMENT_NAME.equals("GroupReservationOtherResultFragment")){
                                        APICall.addGroupItemOther(APICall.getClientsInfoforOthers(salons, stringArrayListHashMap, groupPosition, GroupReservationOthersFragment.is_group_booking,APICall.dateforgroupbooking), GroupReservationOtherResultActivity.context);
                                    }else if (BeautyMainPage.FRAGMENT_NAME.equals("MultiBookingIndividualResult") || BeautyMainPage.FRAGMENT_NAME.equals("multiple_individual_booking") ){
                                        Log.e("INDBooking","ok");
                                        APICall.addGroupItemMulti(APICall.getClientsInfoforIndividual(salons, stringArrayListHashMap, groupPosition, MultiIndividualBookingReservationFragment.is_group_booking), MultiBookingIndividualResultActivity.context);

                                    }else if (BeautyMainPage.FRAGMENT_NAME.equals("OfferBookingResult")){
                                        APICall.addtocartOffer(APICall.getClientsInfoOffer(salons, stringArrayListHashMap, groupPosition), OfferBookingResult.context);

                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                    Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                                APICall.showNeedToSignInDialog(context);

                        }
                    });
                }
            });
            listTitleTextView.setText(listTitle);

//            listTitleTextViews.add(listTitleTextView);
//            listTitleTextViews.get(groupPosition).setText( listTitleTextViews.get(groupPosition).getText().toString()+" : "+stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getTotal_price()+" R");

//            Log.e("pricegroup",stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getTotal_price());
//            Log.e("pricegroup",groupPosition+"");

            listTitleTextView.setTypeface(null, Typeface.BOLD);
            return convertView;
        }else {
            return null;

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
            if (stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getClient_name().equals("booking_wast_time")) {
                client_name.setText(context.getResources().getString(R.string.lost_time_emp));
                salon_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSalon_name());

                salon_name.setVisibility(View.GONE);
//                    salon_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSalon_name());
            }else {
                client_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getClient_name());
                salon_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSalon_name());
            }

//                client_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getClient_name());
//
//                salon_name.setText(APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSalon_name()));

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
                    int layout=0;
                    if (stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getClient_name().equals("booking_wast_time")){
                        layout=R.layout.service_layout_lost;
                    }
                    else {
                        layout=R.layout.service_layout;
                    }
                    View layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(layout, service_layout, false);
                    TextView service_name = layout2.findViewById(R.id.service_name);
                    TextView employee_name = layout2.findViewById(R.id.employee_name);
//                    TextView day = layout2.findViewById(R.id.day);
                    TextView time = layout2.findViewById(R.id.time);
                    TextView reason = layout2.findViewById(R.id.reason);

//                    APICall.getSupName(employee_name,stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSup_id(), BeautyMainPage.context);


                    String partnum="";
                    String priceService = "";
                    String date = "";

                    if (layout==R.layout.service_layout_lost){

                        if (stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getReason().equals("0")) {
                            reason.setText(R.string.journey_lost_time);
                        }else if (stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getReason().equals("1")) {
                            reason.setText(R.string.rel_ser_lost_time);
                        }else if (stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getReason().equals("2")) {
                            reason.setText(R.string.wait_emp_lost_time);
                        }
                    }else {
                        try {
                            partnum = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getPart_num();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            try
                            {
                                if (!stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getDate().equals("")) {
                                    date = APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getDate());
                                }
                                else {
                                    try {
                                        if (BeautyMainPage.FRAGMENT_NAME.equals("GroupReservationResultFragment")) {
                                            date = APICall.convertToArabic(PlaceServiceGroupFragment.dateFilter);
                                        } else if (BeautyMainPage.FRAGMENT_NAME.equals("BookingIndvidualActivity")) {
                                            date = APICall.convertToArabic(PlaceServiceGroupFragment.dateFilter);
                                        } else if (BeautyMainPage.FRAGMENT_NAME.equals("BookingIndvidualActivity") || BeautyMainPage.FRAGMENT_NAME.equals("OfferBookingResult")) {
                                            date = APICall.convertToArabic(PlaceServiceFragment.date.getText().toString());
                                        } else if (BeautyMainPage.FRAGMENT_NAME.equals("GroupReservationOtherResultFragment")) {
                                            date = APICall.convertToArabic(PlaceServiceGroupOthersFragment.dateFilter);
                                        } else if (BeautyMainPage.FRAGMENT_NAME.equals("MultiBookingIndividualResult") || BeautyMainPage.FRAGMENT_NAME.equals("multiple_individual_booking")) {
                                            date = APICall.convertToArabic(PlaceServiceMultipleBookingFragment.dateFilter);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                        Log.e("ErrCustomAdapter", "ERR");

                                    }

                                }

                            }catch (Exception e){
                                e.printStackTrace();
                                try {
                                    if (BeautyMainPage.FRAGMENT_NAME.equals("GroupReservationResultFragment")) {
                                        date = APICall.convertToArabic(PlaceServiceGroupFragment.dateFilter);
                                    } else if (BeautyMainPage.FRAGMENT_NAME.equals("BookingIndvidualActivity")) {
                                        date = APICall.convertToArabic(PlaceServiceGroupFragment.dateFilter);
                                    } else if (BeautyMainPage.FRAGMENT_NAME.equals("BookingIndvidualActivity") || BeautyMainPage.FRAGMENT_NAME.equals("OfferBookingResult")) {
                                        date = APICall.convertToArabic(PlaceServiceFragment.date.getText().toString());
                                    } else if (BeautyMainPage.FRAGMENT_NAME.equals("GroupReservationOtherResultFragment")) {
                                        date = APICall.convertToArabic(PlaceServiceGroupOthersFragment.dateFilter);
                                    } else if (BeautyMainPage.FRAGMENT_NAME.equals("MultiBookingIndividualResult") || BeautyMainPage.FRAGMENT_NAME.equals("multiple_individual_booking")) {
                                        date = APICall.convertToArabic(PlaceServiceMultipleBookingFragment.dateFilter);
                                    }
                                } catch (Exception ee) {
                                    e.printStackTrace();
                                    Log.e("ErrCustomAdapter", "ERR");

                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                        try {
                            if (BeautyMainPage.FRAGMENT_NAME.equals("OfferBookingResult")) {
                                priceService = OfferBookingResult.place;


                                if (OfferBookingResult.place.equals("0")) {
                                    priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_salon_price();
                                } else if (OfferBookingResult.place.equals("1")) {
                                    priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_home_price();
                                } else if (OfferBookingResult.place.equals("2")) {
                                    priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_hall_price();
                                } else if (OfferBookingResult.place.equals("3")) {
                                    priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_hotel_price();

                                }
                            } else if (BeautyMainPage.FRAGMENT_NAME.equals("GroupReservationOtherResultFragment")) {
                                if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                    priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_salon_price();
                                } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                    priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_home_price();
                                } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                    priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_hall_price();
                                } else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                    priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_hotel_price();

                                }
                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_salon_price();
                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_home_price();
                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_hall_price();
                            } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_hotel_price();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
//                       if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==1) {
//                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_salon_price();
//
//                       }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==2) {
                            priceService = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_home_price();
//
//                       }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==3) {
//                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_hall_price();
//
//                       }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==4) {
//                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_hotel_price();
//
//                       }


                        }

                    }
                    if (!stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getPart_num().equals("2")){
//                   if (APICall.ln.equals("ar")){
//                       service_name.setText(APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSer_name_ar()+" : "+priceService+" "+((AppCompatActivity)context).getResources().getString(R.string.ryal))+"-"+context.getResources().getString(R.string.date)+": "+
//                               APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getDate()));
//                   }else {
//                       service_name.setText(APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSer_name()+" : "+priceService+"-"+((AppCompatActivity)context).getResources().getString(R.string.ryal))+" "+context.getResources().getString(R.string.date)+": "+
//                               APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getDate()));
//                   }
                        if (partnum.equals("1")) {
                            if (APICall.ln.equals("ar")) {
                                service_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSer_name_ar() + " : " + priceService + " " + ((AppCompatActivity) context).getResources().getString(R.string.ryal) + " - " + ((AppCompatActivity) context).getResources().getString(R.string.date) + ": " + APICall.convertToArabic(date));
                            } else
                                service_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSer_name() + " : " + priceService + " " + ((AppCompatActivity) context).getResources().getString(R.string.ryal) + " - " + ((AppCompatActivity) context).getResources().getString(R.string.date) + ": " + APICall.convertToArabic(date));
                        } else if (partnum.equals("0")) {
                            if (APICall.ln.equals("ar")) {
                                service_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSer_name_ar() + " : " + priceService + " " + ((AppCompatActivity) context).getResources().getString(R.string.ryal) + " - " + ((AppCompatActivity) context).getResources().getString(R.string.date) + ": " + APICall.convertToArabic(date));

                            } else
                                service_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSer_name() + " : " + priceService + " " + ((AppCompatActivity) context).getResources().getString(R.string.ryal) + " - " + ((AppCompatActivity) context).getResources().getString(R.string.date) + ": " + APICall.convertToArabic(date));
                        }
                }
                    employee_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getEmp_name());
//                    day.setText(PlaceServiceGroupFragment.dateFilter);
                    time.setText(APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getFrom())
                    +" ~ "+APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getTo()));
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

//    static Str
}
