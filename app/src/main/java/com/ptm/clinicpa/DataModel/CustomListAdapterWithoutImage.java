package com.ptm.clinicpa.DataModel;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptm.clinicpa.R;

import java.util.ArrayList;


public class CustomListAdapterWithoutImage extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> itemname;


    public CustomListAdapterWithoutImage(Activity context, ArrayList<String> itemname) {
        super(context, R.layout.row_layout,itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.row_layout_without_image, null,true);

        TextView txtTitle =  rowView.findViewById(R.id.name);
        ImageView imageView =  rowView.findViewById(R.id.icon);
//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname.get(position));

//        extratxt.setText("Description "+itemname[position]);
        return rowView;

//        return super.getDropDownView(position, convertView, parent);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.row_layout, null,true);

        TextView txtTitle =  rowView.findViewById(R.id.name);
        ImageView imageView =  rowView.findViewById(R.id.icon);
//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(itemname.get(position));

//        extratxt.setText("Description "+itemname[position]);
        return rowView;

    };
}