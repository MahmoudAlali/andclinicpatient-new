package com.dcoret.beautyclient.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.dcoret.beautyclient.Activities.BeautyMainPage_2;
import com.dcoret.beautyclient.Activities.ExtraServices;
import com.dcoret.beautyclient.Activities.Favorites;
import com.dcoret.beautyclient.Activities.Help;
import com.dcoret.beautyclient.Activities.MainActivity;
import com.dcoret.beautyclient.R;
import com.dcoret.beautyclient.Activities.Setting;

public class MoreFragment extends Fragment {
LinearLayout services_tabs;
ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_more, container, false);

        listView=view.findViewById(R.id.more_listview);
        String[] array = new String[] {"قائمة المفضلة", "تقييم التطبيق", "مشاركة التطبيق","خدمات اضافية", "الضبط","مساعدة","حول","تسجيل الخروج"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, array);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(getActivity().getApplicationContext(), Favorites.class);
                    startActivity(intent);
//                    Toast.makeText(getActivity().getApplicationContext(), parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                } else if (position == 1) {
                    Dialog dialog=new Dialog(BeautyMainPage_2.context);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                    dialog.setContentView(R.layout.rating_dialog);
                    dialog.show();
                } else if (position == 2) {
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String sharebody="تطبيق beauty لخدمات التجميل و التصميم حمله الآن من App Store";
                    String sharesub="Beauty";
                    intent.putExtra(Intent.EXTRA_SUBJECT,sharesub);
                    intent.putExtra(Intent.EXTRA_TEXT,sharebody);
                    startActivity(Intent.createChooser(intent,"Share using"));

                } else if (position == 5) {
                    Intent intent=new Intent(getActivity().getApplicationContext(), Help.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent=new Intent(getActivity().getApplicationContext(), ExtraServices.class);
                    startActivity(intent);
                } else if (position == 4) {
                    Intent intent=new Intent(getActivity().getApplicationContext(), Setting.class);
                    startActivity(intent);
                } else if (position == 6) {

                } else if (position == 7) {
                    new AlertDialog.Builder(BeautyMainPage_2.context)
                            .setTitle("Logout")
                            .setMessage("هل تريد تسجيل الخروج ؟")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = getActivity().getApplicationContext().getSharedPreferences("LOGIN", getActivity().getApplicationContext().MODE_PRIVATE).edit();
                                    editor.remove("name"); // will delete key name
                                    editor.remove("pass"); // will delete key pass
                                    editor.commit();
                                    Intent intent=new Intent(getActivity().getApplicationContext(), MainActivity.class);
                                    MainActivity.logout=true;
                                    getActivity().finish();

                                    startActivity(intent);

                                }
                            })

                            // A null listener allows the button to dismiss the dialog and take no further action.
                            .setNegativeButton(android.R.string.no, null)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }
            }
        });





        return view;
    }
}
