package com.dcoret.beautyclient.Activities.support;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dcoret.beautyclient.R;

public class SupportActivity extends AppCompatActivity {



    Button whatsapp_support;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        context=this;

        whatsapp_support=findViewById(R.id.whatsapp_support);


        whatsapp_support.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsappChat(context);
            }
        });

    }
    public static void openWhatsappChat(Context context){
        try {
            String whatsAppRoot = "http://api.whatsapp.com/";
            String number = "send?phone=966551974839"; //here the mobile number with its international prefix
            String text = "&text=HERE YOUR TEXT";
            String uri = whatsAppRoot+number+text;

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            ((AppCompatActivity)context).startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(context,
                    "WhatsApp cannot be opened", Toast.LENGTH_SHORT).show();
        }
    }
}
