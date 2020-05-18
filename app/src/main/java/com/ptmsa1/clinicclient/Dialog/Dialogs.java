package com.ptmsa1.clinicclient.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.ptmsa1.clinicclient.R;

public class Dialogs extends Dialog {
    public String EnteredText="";


    public Dialogs(@NonNull Context context, int title, int message, int btnText, final MyRunnable onClickFun)
    {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.sweetalert_layout);
        TextView Dialogmessage = findViewById(R.id.message);
        final EditText enteredMsg = findViewById(R.id.code);
        final TextView Dialogtitle = findViewById(R.id.title);
        Dialogtitle.setText(title);
        enteredMsg.setHint("");
        TextView confirm = findViewById(R.id.confirm);
        TextView cancel = findViewById(R.id.cancel);
        confirm.setText(btnText);
        Dialogmessage.setText(message);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFun.setValue(enteredMsg.getText().toString());
                onClickFun.run();
                cancel();
            }


        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }


        });
    }
    public Dialogs(@NonNull Context context, int message, int btnText, final MyRunnable onClickFun)
    {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.sweet_dialog_layout_v3);
        TextView Dialogmessage = findViewById(R.id.message);
        final EditText enteredMsg = findViewById(R.id.code);
        TextView confirm = findViewById(R.id.confirm);
        TextView cancel = findViewById(R.id.cancel);
        confirm.setText(btnText);
        Dialogmessage.setText(message);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickFun.run();
                cancel();
            }


        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }


        });
    }


    public Dialogs(@NonNull Context context, int message)
    {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.sweet_dialog_layout_v2);
        TextView Dialogmessage = findViewById(R.id.message);
        TextView confirm = findViewById(R.id.confirm);
        Dialogmessage.setText(message);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancel();
            }


        });

    }
    public Dialogs(@NonNull Context context, int message,int message2)
    {
        super(context);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.sweet_dialog_layout_v5);
        TextView Dialogmessage = findViewById(R.id.message);
        TextView Dialogmessage2 = findViewById(R.id.message2);
        TextView confirm = findViewById(R.id.confirm);
        Dialogmessage.setText(message);
        Dialogmessage2.setText(message2);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cancel();
            }


        });

    }
}
