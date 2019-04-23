package com.dcoret.beautyclient.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dcoret.beautyclient.MyProfile;
import com.dcoret.beautyclient.Point;
import com.dcoret.beautyclient.R;

public class AccountFragment extends Fragment {
    String[] array = new String[] {"بروفايلي", "نقاطي", "ايقاف الحساب","حذف الحساب"};
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_manage_accounts_frag, container, false);

        listView=view.findViewById(R.id.list_view);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, array);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), MyProfile.class);
                    startActivity(intent);
//                    Toast.makeText(getApplicationContext(), parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                } else if (position == 1) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), Point.class);
                    startActivity(intent);
//                    Toast.makeText(getApplicationContext(), parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                }
            }
        });

        return view;
    }
}
