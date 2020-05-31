package com.ptm.clinicpa.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptm.clinicpa.API.APICall;
import com.ptm.clinicpa.Activities.ProviderSerAndOfferPKG.MainProviderActivity;
import com.ptm.clinicpa.DataModel.HealthCenterImages;
import com.ptm.clinicpa.R;

import java.util.ArrayList;
public class HealthCenterImagesAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    ArrayList<HealthCenterImages> itemArrayList;
    /**
     * @param context

     */

    public HealthCenterImagesAdapter(Context context,ArrayList<HealthCenterImages> itemArrayList){
        this.context=context;
        this.itemArrayList=itemArrayList;

    }


    /**
     * @param parent
     * @param viewType
     * @return
     * <b>items</b> that are contains the service layout
     */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View row;

        row = inflater.inflate(R.layout.health_center_image, parent, false);

        Item item=new Item(row);
        return item;
    }




    String date;
    Dialog dialog1,dialog;
    public static int placePos=0;
    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        String s;
        if(context.getString(R.string.locale).equals("en"))
             s=itemArrayList.get(position).getBdb_description();
        else
             s=itemArrayList.get(position).getBdb_description();

        ((Item)holder).name.setText(s);

        APICall.getSalonLogo(context,itemArrayList.get(position).getBdb_id(),((Item)holder).image);

        ((Item)holder).image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainProviderActivity.image.setImageDrawable(((Item)holder).image.getDrawable());
                MainProviderActivity.image.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    /**
     * @see RecyclerView.ViewHolder
     */
    public static class Item extends RecyclerView.ViewHolder {

        TextView name;
        ImageView image;

        /**
         * @param itemView
         */
        public Item(View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.name);
            image=itemView.findViewById(R.id.image);
        }
    }
}

