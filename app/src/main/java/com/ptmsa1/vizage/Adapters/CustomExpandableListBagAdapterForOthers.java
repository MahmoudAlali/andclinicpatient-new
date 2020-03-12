package com.ptmsa1.vizage.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ptmsa1.vizage.API.APICall;
import com.ptmsa1.vizage.Activities.BeautyMainPage;
import com.ptmsa1.vizage.DataModel.GetAllCartServices;
import com.ptmsa1.vizage.R;

import java.util.ArrayList;
import java.util.Map;

public class CustomExpandableListBagAdapterForOthers extends BaseExpandableListAdapter {
    private Context context;
    ArrayList<String> salons;
    Map<String,ArrayList<GetAllCartServices>> stringArrayListHashMap;

//    public CustomExpandableListBagAdapter(Context context, ArrayList<String> salons, Map<String, ArrayList<SearchBookingDataSTR>> stringArrayListHashMap) {
//        this.context = context;
//        this.salons = salons;
//        this.stringArrayListHashMap = stringArrayListHashMap;
//    }


    public CustomExpandableListBagAdapterForOthers(Context context, ArrayList<String> salons, Map<String, ArrayList<GetAllCartServices>> stringArrayListHashMap) {
        this.context = context;
        this.salons = salons;
        this.stringArrayListHashMap = stringArrayListHashMap;
    }

    @Override
    public int getGroupCount() {
        Log.e("GrpCount",salons.size()+"");
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
//        String listTitle =  (String) getGroup(groupPosition);
        String listTitle = stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getGetAllCarts().get(0).getSupplier_name();
//            Log.e("LISTTITLE",listTitle);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group, null);
            final TextView listTitleTextView = (TextView) convertView
                    .findViewById(R.id.listTitle);
            TextView book =  convertView
                    .findViewById(R.id.book);

            Log.e("is_group_booking11",stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getGetAllCarts().get(0).getBdb_is_group_booking());

            book.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((AppCompatActivity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if(APICall.isGuest(context).equals("0"))

                            {
                                if (Integer.parseInt(stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getGetAllCarts().get(0).getBdb_is_group_booking())>=4&&
                                        Integer.parseInt(stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getGetAllCarts().get(0).getBdb_is_group_booking())<=9) {
                                    APICall.moveofferCartToBooking(stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getGetAllCarts().get(0).getBdb_pack_booking(), stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getGetAllCarts().get(0).getBdb_is_group_booking(), groupPosition, context);
                                }else {
                                    APICall.moveCartToBooking(stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getGetAllCarts().get(0).getBdb_pack_booking(),stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getGetAllCarts().get(0).getBdb_is_group_booking(),groupPosition,context);
                                }
                            }
                            else
                                APICall.showNeedToSignInDialog(context);

//                            APICall.addGroupItem(   APICall.getClientsInfo(salons,stringArrayListHashMap,groupPosition),BeautyMainPage.context);
//                        Toast.makeText(context,"book Reserved"+stringArrayListHashMap.get(salons.get(groupPosition)).get(0).getGetAllCarts().get(0).getBdb_id(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
            listTitleTextView.setText(listTitle);

            listTitleTextView.setTypeface(null, Typeface.BOLD);
            return convertView;
        }else {
            return convertView;

        }

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.result_group_bag_reservation_layout, null);
            }
        TextView cName=convertView.findViewById(R.id.client_name);
        TextView salon_name=convertView.findViewById(R.id.salon_name);
//        TextView service_value=convertView.findViewById(R.id.service_value);
//        TextView service_price=convertView.findViewById(R.id.service_price);
        final LinearLayout service_layout = convertView.findViewById(R.id.service_layout);

        cName.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().get(0).getBdb_user_name());
        salon_name.setText(APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().get(0).getBdb_user_phone()));

        service_layout.removeAllViews();
        for (int i=0;i<stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().size();i++){
            final View layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.service__baglayout, service_layout, false);
            TextView service_name = layout2.findViewById(R.id.service_name);
            TextView employee_name = layout2.findViewById(R.id.employee_name);
//                    TextView day = layout2.findViewById(R.id.day);
            TextView time = layout2.findViewById(R.id.time);
            service_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().get(i).getBdb_service_name_en()
                                +" : "+APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().get(i).getBdb_price())+" "+((AppCompatActivity)context).getResources().getString(R.string.ryal));
            employee_name.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().get(i).getBdb_emp_name());
            time.setText(APICall.convertToArabic(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().get(0).getBdb_start_time()));

//            Button delete=layout2.findViewById(R.id.delete);
            final String serviceName=stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().get(i).getBdb_service_name_en();
//            delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().size()>1) {
//                        service_layout.removeView(layout2);
//                        for (int j = 0; j < stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().size(); j++) {
//                            if (stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().get(j).getBdb_service_name_en().equals(serviceName)) {
//                                stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().remove(j);
//                                Log.e("RemoveOkS:", stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().size() + "");
//                            }
//                        }
//                    }else {
//                        String t=((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.alert);
//                        String m=((AppCompatActivity)BeautyMainPage.context).getResources().getString(R.string.cant_remove_last_item);
//                        APICall.showSweetDialog(context,t,m);
//                    }
//                }
//            });

            service_layout.addView(layout2);
        }
//        service_value.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().get(0).getBdb_service_name_en());
//        service_price.setText(stringArrayListHashMap.get(salons.get(groupPosition)).get(childPosition).getGetAllCarts().get(0).getBdb_price());


            return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
