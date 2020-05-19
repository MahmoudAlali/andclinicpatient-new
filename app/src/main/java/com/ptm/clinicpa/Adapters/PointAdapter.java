package com.ptm.clinicpa.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ptm.clinicpa.DataModel.PointModel;
import com.ptm.clinicpa.R;
import java.util.ArrayList;


public class PointAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    String items[];
    ArrayList<PointModel> pointModel;
    Boolean layout;

    public PointAdapter(Context context, String items[]){
        this.context=context;
        this.items=items;
    }
    public PointAdapter(Context context, ArrayList<PointModel> pointModels){
        this.context=context;
        this.pointModel=pointModels;
    }
    public PointAdapter(Context context, String items[], Boolean layout){
        this.context=context;
        this.items=items;
        this.layout=layout;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(context);
        View row=inflater.inflate(R.layout.point_layout,parent,false);
        Item item=new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((Item) holder).pointsCount.setText(pointModel.get(position).getPointsCount());
        ((Item) holder).pointsDesc.setText(pointModel.get(position).getPointsDesc());
        ((Item) holder).pointsDate.setText(pointModel.get(position).getPointsDate());
        if (pointModel.get(position).getPointOPtype().equals("1"))
            ((Item) holder).pointsIcon.setImageResource(R.drawable.up_arrow_icon);
//        ((ChartReservationAdapter.Item)holder).textView.setText(items[position]);
//        ((PointAdapter.Item) holder).barChart.setData(chart(items[position],((Item) holder).barChart));
//        ((ChartReservationAdapter.Item) holder).barChart.getData().setValueTextSize(15);
    }

    @Override
    public int getItemCount() {
        return pointModel.size();
    }


    public class Item extends RecyclerView.ViewHolder {

        public TextView pointsCount,pointsDate,pointsDesc;
        public ImageView pointsIcon;
        public TextView textView;
        public Item(View itemView){
            super(itemView);
            pointsCount = itemView.findViewById(R.id.pointsCount);
            pointsDate = itemView.findViewById(R.id.pointsDate);
            pointsDesc = itemView.findViewById(R.id.pointsDesc);
            pointsIcon = itemView.findViewById(R.id.pointsIcon);

        }
    }


/*
    public BarData chart(String text, BarChart chart){


        ArrayList<BarEntry> yvalues=new ArrayList<>();

        BarEntry b1=new BarEntry(1,15,"s1");

        yvalues.add(new BarEntry(0,15," 3"));
        yvalues.add(new BarEntry(1,11," 4"));
        yvalues.add(new BarEntry(2,17,"Service 6"));
        yvalues.add(new BarEntry(3,18,"Service 7"));
        yvalues.add(new BarEntry(4,30,"Service 8"));
        yvalues.add(new BarEntry(5,20,"Service 9"));


        BarDataSet dataSet=new BarDataSet(yvalues,text);
        //        dataSet.setColor(Color.BLUE);
        final String[] weekdays = {"serv1", "serv2", "serv3", "serv4", "serv5", "serv6"};

        // Set the value formatter
        XAxis xAxis = chart.getXAxis();
        xAxis.setTextColor(Color.BLUE);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekdays));
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        BarData data=new BarData(dataSet);
        chart.animateXY(3000,3000); // animate horizontal 3000 milliseconds

        return data;


    }
*/




}
