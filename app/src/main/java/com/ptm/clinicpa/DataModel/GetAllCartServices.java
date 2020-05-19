package com.ptm.clinicpa.DataModel;

import java.util.ArrayList;

public class GetAllCartServices {
    String Phone;
    ArrayList<GetAllCart> getAllCarts;

    public GetAllCartServices(String phone, ArrayList<GetAllCart> getAllCarts) {
        Phone = phone;
        this.getAllCarts = getAllCarts;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public ArrayList<GetAllCart> getGetAllCarts() {
        return getAllCarts;
    }

    public void setGetAllCarts(ArrayList<GetAllCart> getAllCarts) {
        this.getAllCarts = getAllCarts;
    }
}
