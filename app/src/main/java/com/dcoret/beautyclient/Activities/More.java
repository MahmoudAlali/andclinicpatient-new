package com.dcoret.beautyclient.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dcoret.beautyclient.R;

public class More extends AppCompatActivity {
    ListView listView;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        context=this;

        listView=findViewById(R.id.more_listview);
        String[] array = new String[] {"قائمة المفضلة", "تقييم التطبيق", "مشاركة التطبيق", "خدمات اضافية","مساعدة","حول","تسجيل الخروج"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intent = new Intent(context, Favorites.class);
                    startActivity(intent);
//                    Toast.makeText(context, parent.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                } else if (position == 1) {
                    Dialog dialog=new Dialog(context);
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
                    Intent intent=new Intent(context,Help.class);
                    startActivity(intent);
                } else if (position == 3) {
                    Intent intent=new Intent(context,ExtraServices.class);
                    startActivity(intent);
                }else if (position == 4) {

                } else if (position == 6) {

                }else if (position == 7) {
                    new AlertDialog.Builder(context)
                            .setTitle("Logout")
                            .setMessage("هل تريد تسجيل الخروج ؟")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences.Editor editor = getSharedPreferences("LOGIN", MODE_PRIVATE).edit();
                                    editor.remove("name"); // will delete key name
                                    editor.remove("pass"); // will delete key pass
                                    editor.commit();
                                    Intent intent=new Intent(context, Login.class);
                                    Login.logout=true;
                                    finish();

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




    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(context,BeautyMainPage_2.class);
        finish();
        startActivity(intent);
    }
}
