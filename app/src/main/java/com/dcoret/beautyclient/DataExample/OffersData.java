package com.dcoret.beautyclient.DataExample;

import com.dcoret.beautyclient.DataClass.DataOffer;
import com.dcoret.beautyclient.DataClass.DataService;

import java.util.ArrayList;

public class OffersData {
    public static   DataService[] services=new DataService[]{
            new DataService(1,"service1","SANA'A",30,4.5,false,true),
            new DataService(1,"service2","SANA'A",40,4.5,false,true),
//            new DataService(1,"service3","SANA'A",20,4.5,false,true),
//            new DataService(1,"service4","SANA'A",50,4.5,false,true),
//            new DataService(1,"service5","SANA'A",50,4.5,false,true),

    };
    public static   DataService[] services1=new DataService[]{
            new DataService(2,"خدمة1","KAMAR",30,4.5,false,true),
            new DataService(2,"خدمة2","KAMAR",40,4.5,false,true),
            new DataService(2,"خدمة3","KAMAR",20,4.5,false,true),
            new DataService(2,"خدمة4","KAMAR",50,4.5,false,true),
            new DataService(2,"خدمة5","KAMAR",50,4.5,false,true),

    };
    public static   DataService[] service2=new DataService[]{
            new DataService(3,"serv1","SAMAR",30,4.5,false,true),
            new DataService(3,"serv2","SAMAR",40,4.5,false,true),
            new DataService(3,"serv3","SAMAR",20,4.5,false,true),
            new DataService(3,"serv4","SAMAR",50,4.5,false,true),
            new DataService(3,"serv5","SAMAR",50,4.5,false,true),

    };
    public static   DataService[] service4=new DataService[]{
            new DataService(4,"serv1","LAMA",40,4.5,false,true),
            new DataService(4,"serv2","LAMA",40,4.5,false,true),
            new DataService(4,"serv3","LAMA",20,4.5,false,true),
            new DataService(4,"serv4","LAMA",50,4.5,false,true),
            new DataService(4,"serv5","LAMA",50,4.5,false,true),

    };
    public static   DataService[] service5=new DataService[]{
            new DataService(5,"serv1","SANA'A",50,4.5,false,true),
            new DataService(5,"serv2","SANA'A",40,4.5,false,true),
            new DataService(5,"serv3","SANA'A",20,4.5,false,true),
            new DataService(5,"serv4","SANA'A",50,4.5,false,true),
            new DataService(5,"serv5","SANA'A",50,4.5,false,true),

    };

    ArrayList<DataService[]> allservices=new ArrayList<>();



//   public static ArrayList<DataOffer> offers;
//            new DataOffer("offer1",services,150,false),
//            new DataOffer("offer2",services1,150,false),
//            new DataOffer("offer3",service2,150,false),
//            new DataOffer("offer4",service4,150,false),
//            new DataOffer("offer5",service5,150,false),
//
//    };

   public OffersData(ArrayList<DataOffer> offers){
      allservices.add(services);
      allservices.add(service2);
      allservices.add(services1);
      allservices.add(service4);
      allservices.add(service5);
//       for (int i=0;i<allservices.size();i++){
//           if(i>2) {
//               offers.add(new DataOffer("offer" + i + 1, allservices.get(i), getprice(allservices.get(i)), 4.5, false,"o"));
//           }else {
//               offers.add(new DataOffer("offer" + i + 1, allservices.get(i), getprice(allservices.get(i)), 4.5, false,"os"));
//
//           }
//           }

       }

       double getprice(DataService[] dataService){
       double price=0;
       for (int i=0;i<dataService.length;i++){
           price=price+dataService[i].getPrice();
           }
           return price;
       }

   }







