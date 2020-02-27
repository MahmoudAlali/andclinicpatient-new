package com.dcoret.beautyclient.Adapters;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dcoret.beautyclient.API.APICall;
import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.Activities.OfferBookingResult;
import com.dcoret.beautyclient.DataModel.SearchBookingDataSTR;
import com.dcoret.beautyclient.DataModel.SerchGroupBookingData;
import com.dcoret.beautyclient.Fragments.GroupBooking.AlterGroupReservationResultActivity;
import com.dcoret.beautyclient.Fragments.GroupBooking.GroupReservationFragment;
import com.dcoret.beautyclient.Fragments.GroupBooking.GroupReservationResultActivity;
import com.dcoret.beautyclient.Fragments.GroupBooking.PlaceServiceGroupFragment;
import com.dcoret.beautyclient.Fragments.IndividualBooking.BookingIndvidualActivity;
import com.dcoret.beautyclient.Fragments.IndividualBooking.PlaceServiceFragment;
import com.dcoret.beautyclient.Fragments.IndividualBooking.Tabs.TabOne;
import com.dcoret.beautyclient.Fragments.OtherGroupBooking.GroupReservationOtherResultActivity;
import com.dcoret.beautyclient.Fragments.OtherGroupBooking.GroupReservationOthersFragment;
import com.dcoret.beautyclient.Fragments.OtherGroupBooking.PlaceServiceGroupOthersFragment;
import com.dcoret.beautyclient.Fragments.SingleMultiBooking.MultiBookingIndividualResultActivity;
import com.dcoret.beautyclient.Fragments.SingleMultiBooking.MultiIndividualBookingReservationFragment;
import com.dcoret.beautyclient.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class
AltCustomExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<SerchGroupBookingData> expandableListTitle;
    private List<SerchGroupBookingData> expandableListDetail1;
    ArrayList<String> salons;
    ArrayList<SearchBookingDataSTR> searchBookingDataSTRS;
    ArrayList<Boolean> isopen=new ArrayList<>();
    ArrayList<TextView> listTitleTextViews=new ArrayList<>();
    Map<String,ArrayList<SearchBookingDataSTR>> stringArrayListHashMap;

    public AltCustomExpandableListAdapter(Context context, ArrayList<String> salons, Map<String, ArrayList<SearchBookingDataSTR>> stringArrayListHashMap) {
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
            final TextView book =  convertView
                    .findViewById(R.id.book);

            if ( BeautyMainPage.FRAGMENT_NAME.equals("BookingIndvidualActivity"))
            if (stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getSalon_id().equals(TabOne.bdb_sup_id)){
                listTitleTextView.setBackgroundResource(R.color.primev1);
            }
            if ( stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getIs_booked().equals("2") ||
                    stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getIs_booked().equals("1") ){
                book.setClickable(false);
                book.setVisibility(View.GONE);
            }
            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (!book.isClickable()){
                                    Toast.makeText(context,"this solution is disabled",Toast.LENGTH_LONG).show();
                                }else
//                                    if ( stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).)
                                if (BeautyMainPage.FRAGMENT_NAME.equals("GroupReservationResultFragment")) {
                                    Log.e("FilterAlt",APICall.getClientsInfoAlt(salons, stringArrayListHashMap, groupPosition,GroupReservationFragment.is_group_booking,APICall.dateforgroupbooking ));
                                    APICall.addGroupItem(APICall.getClientsInfoAlt(salons, stringArrayListHashMap, groupPosition,GroupReservationFragment.is_group_booking,APICall.dateforgroupbooking ), AlterGroupReservationResultActivity.context);
//                        Toast.makeText(context,"book is selected",Toast.LENGTH_SHORT).show();
                                }else if (BeautyMainPage.FRAGMENT_NAME.equals("BookingIndvidualActivity")) {
                                    APICall.addGroupItemInd(APICall.getClientsInfoInd(salons, stringArrayListHashMap, groupPosition,BookingIndvidualActivity.bdb_is_groupbooking, PlaceServiceFragment.date.getText().toString()), BookingIndvidualActivity.context);
//                        Toast.makeText(context,"book is selected",Toast.LENGTH_SHORT).show();
                                }else if (BeautyMainPage.FRAGMENT_NAME.equals("GroupReservationOtherResultFragment")){
                                    APICall.addGroupItemOther(APICall.getClientsInfoforOthers(salons, stringArrayListHashMap, groupPosition, GroupReservationOthersFragment.is_group_booking,APICall.dateforgroupbooking), GroupReservationOtherResultActivity.context);
                                }else if (BeautyMainPage.FRAGMENT_NAME.equals("MultiBookingIndividualResult")){
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
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        try {


//            for (int j = 0; j < expandableListDetail1.get(groupPosition).getCompleteSolutions().size(); j++) {
                if (convertView == null) {
                    LayoutInflater layoutInflater = (LayoutInflater) this.context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = layoutInflater.inflate(R.layout.result_group_reservation_layout_v1, null);
                }
//               listTitleTextViews.get(groupPosition).setText( listTitleTextViews.get(groupPosition).getText().toString()+":"+stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getTotal_price());
                TextView client_name;
                LinearLayout service_layout;
                client_name = convertView.findViewById(R.id.client_name);
//                salon_name = convertView.findViewById(R.id.salon_name);
                service_layout = convertView.findViewById(R.id.service_layout);
//                client_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getClient_name());
//                salon_name.setText(APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSalon_name()));

//                stringArrayListHashMap.clear();


//            client_name.setTextColor(Color.parseColor("#000000"));
//            salon_name.setTextColor(Color.parseColor("#000000"));
                //----------------- error in this if ----------
            Log.e("postionISCU",stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getIs_current_user());
            Log.e("postionISCU",salons.get(groupPosition));
            if (stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getIs_current_user().equals("1")){
                   Log.e("postionISCU",stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getIs_current_user());
                    client_name.setTextColor(Color.parseColor("#D3295F"));
//                    client_name.setHintTextColor(R.color.colorAccentend);
//                    salon_name.setTextColor(Color.parseColor("#D3295F"));
                }
                //--------------------------------------------------

                Log.e("postion " ,stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().size() + "");
//            stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().clear();

                service_layout.removeAllViews();
                for (int i = 0; i < stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().size(); i++) {
                    View layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.service_layout_v1, service_layout, false);
                    TextView service_name = layout2.findViewById(R.id.service_name);
                    TextView employee_name = layout2.findViewById(R.id.employee_name);
                    final TextView salon_name = layout2.findViewById(R.id.salon_name);
                    TextView client_name1 = layout2.findViewById(R.id.client_name);
                    final ImageView book_ser = layout2.findViewById(R.id.book_ser);
                    TextView time = layout2.findViewById(R.id.time);



                salon_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSalon_name());
                    client_name1.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getClient_name());

//                    APICall.getSupName(employee_name,stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSup_id(), BeautyMainPage.context);
                  String priceService="";
                    if (stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getIs_booked().equals("2")){
                        book_ser.setVisibility(View.GONE);
                    }
                    final int finalI = i;
                    book_ser.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getIs_booked().equals("2")){
                                Toast.makeText(context, "this solutios is disabled", Toast.LENGTH_LONG).show();

                            }else {
//                                Toast.makeText(context, "book", Toast.LENGTH_LONG).show();
                                stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).setIs_booked("1");


                                for (int kk=0;kk<salons.size();kk++){
                                    for (int ii=0;ii<stringArrayListHashMap.get(salons.get(kk)).size();ii++){
                                        if (stringArrayListHashMap.get(salons.get(kk)).get(ii).getIs_booked().equals("0")){
                                            stringArrayListHashMap.get(salons.get(kk)).get(ii).setIs_booked("2");
                                        }
                                    }
                                }

                                //------------ filter request----------------
                                String clients="",serRow="";
                                String clientId=stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getClient_id();
                                String isCurrentUser=stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getIs_current_user();
                                String bdb_is_group_booking="1";
                                if (isCurrentUser.equals("0")){
                                    bdb_is_group_booking="2";
                                }
                                String date="";
                                if (BeautyMainPage.FRAGMENT_NAME.equals("GroupReservationResultFragment"))
                                    date=APICall.dateforgroupbooking;
                                clients="{\"date\":\""+date+"\",\"bdb_is_group_booking\":"+bdb_is_group_booking+",\"clients\":[";

                                String clientName = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getClient_name();
                                String clientPhone = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getPhone();
                                String emp_id = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getEmp_id();
                                String emp_name = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getEmp_name();
                                String sup_id = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getSup_id();
                                String ser_sup_id = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getSer_sup_id();
                                String from = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getFrom();
                                String to = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getTo();
                                String bdb_part_num = stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getPart_num();



                                String bdb_ser_salon = null, bdb_ser_home = "", bdb_ser_hotel = "", bdb_ser_hall = "";
                                String price = "";
                                if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 1) {
                                    bdb_ser_salon = "1";
                                    bdb_ser_home = "0";
                                    bdb_ser_hall = "0";
                                    bdb_ser_hotel = "0";
                                    price=stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getBdb_ser_salon_price();
                                } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 2) {
                                    bdb_ser_salon = "0";
                                    bdb_ser_home = "1";
                                    bdb_ser_hall = "0";
                                    bdb_ser_hotel = "0";
                                    price=stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getBdb_ser_home_price();
                                } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 3) {
                                    bdb_ser_salon = "0";
                                    bdb_ser_home = "0";
                                    bdb_ser_hall = "1";
                                    bdb_ser_hotel = "0";
                                    price=stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getBdb_ser_hall_price();
                                } else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition() == 4) {
                                    bdb_ser_salon = "0";
                                    bdb_ser_home = "";
                                    bdb_ser_hall = "0";
                                    bdb_ser_hotel = "1";
                                    price=stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(finalI).getBdb_hotel_price();
                                }

                                    clients += "{\"client_name\":\"" + clientName + "\",\"client_phone\":\"" + clientPhone + "\",\"client_id\":" + clientId + ",\"is_current_user\":" + isCurrentUser + ",\"bookings\":[";


                                serRow = "{\"emp_id\":" + emp_id + ",\"emp_name\":\""+emp_name+"\",\"sup_id\":" + sup_id + ",\"ser_sup_id\":" + ser_sup_id + ",\"from\":\"" + from + "\",\"to\":\"" + to + "\",\"bdb_ser_salon\":" + bdb_ser_salon + ",\"bdb_ser_home\":" + bdb_ser_home + ",\"bdb_ser_hotel\":" + bdb_ser_hotel + ",\"bdb_ser_hall\":" + bdb_ser_hall + ",\"price\":"+price+",\"bdb_client_old\":1,\"bdb_part_num\":"+bdb_part_num+"}";
                                serRow=serRow+"]}";

                                clients+=serRow;

                                clients=clients+"]}";

                                Log.e("FilterForOne",clients);

                                APICall.addGroupItemAltOne(clients,book_ser, AlterGroupReservationResultActivity.context);

                            }



                        }
                    });
                   try {
                       if (BeautyMainPage.FRAGMENT_NAME.equals("OfferBookingResult")){
                           priceService=OfferBookingResult.place;


                           if (OfferBookingResult.place.equals("0")) {
                               priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_salon_price();
                           }else if (OfferBookingResult.place.equals("1")) {
                               priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_home_price();
                           }else if (OfferBookingResult.place.equals("2")) {
                               priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_hall_price();
                           }else if (OfferBookingResult.place.equals("3")) {
                               priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_hotel_price();

                           }

                       }else if (BeautyMainPage.FRAGMENT_NAME.equals("GroupReservationOtherResultFragment")){
                           if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==1) {
                               priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_salon_price();
                           }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==2) {
                               priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_home_price();
                           }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==3) {
                               priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_hall_price();
                           }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==4) {
                               priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_hotel_price();

                           }
                       }else
                       if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()==1) {
                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_salon_price();
                       }else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()==2) {
                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_home_price();
                       }else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()==3) {
                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_hall_price();
                       }else if (PlaceServiceGroupFragment.placeSpinner.getSelectedItemPosition()==4) {
                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_hotel_price();

                       }
                   }catch (Exception e){
                       e.printStackTrace();
//                       if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==1) {
//                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_salon_price();
//
//                       }else if (PlaceServiceGroupOthersFragment.placeSpinner.getSelectedItemPosition()==2) {
                           priceService=  stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getBdb_ser_home_price();
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
                   if (APICall.ln.equals("ar")){
                       service_name.setText(APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSer_name_ar()+" : "+priceService+" "+((AppCompatActivity)context).getResources().getString(R.string.ryal))+"-"+context.getResources().getString(R.string.date)+": "+
                               APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getDate()));
                   }else {
                       service_name.setText(APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getSer_name()+" : "+priceService+"-"+((AppCompatActivity)context).getResources().getString(R.string.ryal))+" "+context.getResources().getString(R.string.date)+": "+
                               APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getSolutions().get(i).getDate()));
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
