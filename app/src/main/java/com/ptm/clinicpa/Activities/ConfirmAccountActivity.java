package com.ptm.clinicpa.Activities;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.R;

public class ConfirmAccountActivity extends AppCompatActivity {

    public  static CountDownTimer countDownTimer;
    Long timeMelSec=30000L;
    TextView timerTV,msg1;
    Button resend_code;
    EditText num1,num2,num3,num4;
    Context context;
    String number,token;
    String key="",value="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_account);
        context=this;
        timerTV=findViewById(R.id.timerTV);
        num1=findViewById(R.id.num1);
        msg1=findViewById(R.id.msg1);
        num2=findViewById(R.id.num2);
        num3=findViewById(R.id.num3);
        num4=findViewById(R.id.num4);
        resend_code=findViewById(R.id.resend_code);
        key=getIntent().getStringExtra("key");
        value=getIntent().getStringExtra("value");


        number= getIntent().getStringExtra("phone");
        token= getIntent().getStringExtra("bdb_token");

        msg1.setText(context.getResources().getString(R.string.number_sended)+" "+number);





        num2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                if (i!=KeyEvent.KEYCODE_DEL  ){

//                    num3.getText().clear();
                if (i==KeyEvent.KEYCODE_DEL ) {

                    Selection.setSelection((Editable) num1.getText(), num2.getSelectionStart());
                    num1.requestFocus();
                }
                return false;
            }
        });
        num3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

//                    Selection.setSelection((Editable) num4.getText(), num3.getSelectionStart());


                if (i==KeyEvent.KEYCODE_DEL) {
                    Selection.setSelection((Editable) num2.getText(), num3.getSelectionStart());
                    num2.requestFocus();
                }
                return false;
            }
        });

        num4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i!=KeyEvent.KEYCODE_DEL ){
                    resend_code.setText(R.string.send);
                    resend_code.setVisibility(View.VISIBLE);
                }else if (i==KeyEvent.KEYCODE_DEL ) {
                    Selection.setSelection((Editable) num3.getText(), num4.getSelectionStart());
                    num3.requestFocus();
                    resend_code.setText(R.string.resend_code);
                    resend_code.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });



        num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                num2.requestFocus();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (num2.getText().toString().length()==1)
                    num3.requestFocus();

            }
        });
        num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (num3.getText().toString().length()==1)
                    num4.requestFocus();
            }
        });
        num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                resend_code.setText(R.string.send);
                resend_code.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        timer();
    }


    public String timer(){
        String time="";
        countDownTimer=new CountDownTimer(timeMelSec,1000) {
            @Override
            public void onTick(long l) {
                timerTV.setText((l/1000)+" "+context.getResources().getString(R.string.second));
            }

            @Override
            public void onFinish() {
                //------------ set resend code visible--------------
                resend_code.setVisibility(View.VISIBLE);

            }
        };
        countDownTimer.start();

        return time;
    }

    public void getAction(View view) {
        if (resend_code.getText().toString().equals(getResources().getString(R.string.Re_send_A_C)) && timerTV.getText().toString().equals(0+" "+context.getResources().getString(R.string.second))){

            Log.e("ddddd","ssssss");
            APICall.activateAgain(APICall.API_PREFIX_NAME+"/api/user/register/activateAgain",
                    number,
                    context);
        }else {
            String code=num1.getText().toString()+num2.getText().toString()+num3.getText().toString()+num4.getText().toString();
            APICall.activeAccount(APICall.API_PREFIX_NAME+"/api/user/register/activate",
                    code,
                    context,key,value);
        }

    }
}
