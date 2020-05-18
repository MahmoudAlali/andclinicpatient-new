package com.ptmsa1.clinicclient.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ptmsa1.clinicclient.R;

public class Setting extends AppCompatActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        listView=findViewById(R.id.listview_setting);
        String[] array = new String[] {"اللغة",  "الاشعارات ","حول"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, array);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

//                    Toast.makeText(getActivity().getApplicationContext(), parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                } else if (position == 1) {

                } else if (position == 2) {

                }
            }
    });
}
}
