package com.ptmsa1.clinicclient.API;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ptmsa1.clinicclient.R;

public class HintArrayAdapter<T> extends ArrayAdapter<T> {

        Context mContext;

        public HintArrayAdapter(Context context, int resource) {
            super(context, resource);
            this.mContext = context;
        }

        @Override
        public View  getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.simple_spinner_item_layout_v1, parent, false);
            TextView texview = (TextView) view.findViewById(R.id.text1);

            if(position == 0) {
                texview.setText(getItem(position).toString()); //"Hint to be displayed"
            } else {
                texview.setText(getItem(position).toString());
            }

            return view;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view;

            if(position == 0){
                view = inflater.inflate(R.layout.simple_spinner_dropdown_item_layout_accent, parent, false); // Hide first row
                TextView texview = (TextView) view.findViewById(R.id.text1);
                texview.setText(getItem(position).toString());
            } else {
                view = inflater.inflate(R.layout.simple_spinner_dropdown_item_layout_v3, parent, false);
                TextView texview = (TextView) view.findViewById(R.id.text1);
                texview.setText(getItem(position).toString());
            }

            return view;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }
