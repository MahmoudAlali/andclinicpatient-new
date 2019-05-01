package com.dcoret.beautyclient.DataExample;

import com.dcoret.beautyclient.DataClass.DataService;
import com.dcoret.beautyclient.DataClass.Location_Beauty;

import java.util.ArrayList;

public class ServicesData {

    static   String[] items={"Service1","Service2","Service3","Service4","Service5","Service6","Service7","Service8","Service9","Service10"};
    static String[] providers_name={"SANA'A","HIBA","NOUR","LAMA","NOUR","HIBA","FIHA'A","SANA'A","LAMA","KAMAR"};

    static   String[] prices={"100","500","450","123","345","411","800","900","600","300"};
    static   String[] rank={"4.1","3.2","3.5","4.7","4.4","3.0","3.0","2.5","2.0","1.5"};
    static  String[] city={"الرياض","الدمام","مكة","الرياض","جدة","الدمام","مكة","مكة","الطائف","مكة"};
    static Location_Beauty[] locations={
            new Location_Beauty(32.7792842,35.8816735),
            new Location_Beauty(31.964383, 35.918756),
            new Location_Beauty(32.709566, 36.137142),
            new Location_Beauty(32.777491, 35.935935),
            new Location_Beauty(32.755262, 35.986746),
            new Location_Beauty(32.725373, 35.944346),
            new Location_Beauty(32.688479, 35.992233),
            new Location_Beauty(32.670663, 36.052908),
            new Location_Beauty(33.506590, 36.299474),
            new Location_Beauty(33.546086, 36.200597),
    };
    static boolean[] fav={
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
    };


    static ArrayList<DataService> dataServices=new ArrayList<>();
            public ServicesData(ArrayList<DataService> dataServices){
                for (int i=0;i<items.length;i++) {
                dataServices.add(new DataService(0, items[i],providers_name[i],Double.parseDouble(prices[i]),Double.parseDouble(rank[i]),city[i],locations[i],fav[i],false));
                    }
            }

}
