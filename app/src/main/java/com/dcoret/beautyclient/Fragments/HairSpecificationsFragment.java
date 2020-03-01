package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.dcoret.beautyclient.Activities.BeautyMainPage;
import com.dcoret.beautyclient.DataModel.CustomListAdapter;
import com.dcoret.beautyclient.R;

public class HairSpecificationsFragment extends Fragment {

    LinearLayout hair_items;
    Button next;

    Fragment fragment;
    FragmentManager fm;
    FragmentTransaction fragmentTransaction;
    String[] itemname ={
            "طول الشعر",
            "درجة رقم1",
            "درجة رقم2",
            "درجة رقم3",
            "درجة رقم4"
    };


    String itemname1[]={
            "كثافة الشعر",
            "درجة رقم1",
            "درجة رقم2",
            "درجة رقم3",
            "درجة رقم4"
    };
    String itemname2[]={
            "درجة تجعيد الشعر",
            "درجة رقم1",
            "درجة رقم2",
            "درجة رقم3",
            "درجة رقم4"
    };

    Integer[] imgid={
            R.drawable.ic_keyboard_arrow_down_black_24dp,
            R.drawable.pic1,
            R.drawable.pic2,
//            R.drawable.pic3,
            R.drawable.pic4
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.hair_specification_layout, container, false);


        BeautyMainPage.FRAGMENT_NAME="HairSpecificationsFragment";

        hair_items=view.findViewById(R.id.hair_layout);
        next=view.findViewById(R.id.next);

        for (int i = 0; i< GroupReservationFragment.ishairService.size(); i++) {
            View layout2 = LayoutInflater.from(BeautyMainPage.context).inflate(R.layout.hair_layout, hair_items, false);

            Spinner hair_lngth = layout2.findViewById(R.id.Hair_length);
            TextView service_name = layout2.findViewById(R.id.service_name);
//            service_name.setText(GroupReservationFragment.clientsViewData.get(i).getAdd_service().getSelectedItem()+"");
            CustomListAdapter adapter=new CustomListAdapter(getActivity(), itemname, imgid);
            hair_lngth.setAdapter(adapter);

            TextView name=layout2.findViewById(R.id.name);
            TextView phone_number=layout2.findViewById(R.id.phone_number);
            name.setText(GroupReservationFragment.clientsViewData.get(GroupReservationFragment.ishairService.get(i) ).getClient_name().getText().toString());
            phone_number.setText(GroupReservationFragment.clientsViewData.get(GroupReservationFragment.ishairService.get(i)).getPhone_number().getText().toString());

            Spinner hair_dnsty = layout2.findViewById(R.id.Hair_density);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(BeautyMainPage.context,
                    R.layout.row_simple_layout, R.id.name, itemname1);
            hair_dnsty.setAdapter(adapter1);
//
//
//
//
//
            Spinner curls = layout2.findViewById(R.id.curls);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(BeautyMainPage.context,
                    R.layout.row_simple_layout, R.id.name, itemname2);
            curls.setAdapter(adapter2);
//            Spinner add_service = layout2.findViewById(R.id.add_service);
//            ArrayAdapter adapter = ArrayAdapter.createFromResource(BeautyMainPage.context, R.array.add_service, android.R.layout.simple_spinner_item);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            add_service.setAdapter(adapter);
//
//
//            Spinner age_range = layout2.findViewById(R.id.age_range);
//            ArrayAdapter adapter_age_range = ArrayAdapter.createFromResource(BeautyMainPage.context, R.array.age_range, android.R.layout.simple_spinner_item);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            age_range.setAdapter(adapter_age_range);
//
//
//            Spinner client_status = layout2.findViewById(R.id.client_status);
//            ArrayAdapter adapter_client_status = ArrayAdapter.createFromResource(BeautyMainPage.context, R.array.client_status, android.R.layout.simple_spinner_item);
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//            client_status.setAdapter(adapter_client_status);


            hair_items.addView(layout2);


        }
next.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        fragment = new GroupReservationResultFragment();
        fm = getFragmentManager();
        fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }
});

        return view;
    }
}
