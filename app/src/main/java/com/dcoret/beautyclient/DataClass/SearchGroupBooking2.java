package com.dcoret.beautyclient.DataClass;

import java.util.ArrayList;

public class SearchGroupBooking2 {
    String salon_id,salon_name;
    ArrayList<SolutionGroupBooking2> solutionSearchGroupBooking2;

    public SearchGroupBooking2(String salon_id, String salon_name, ArrayList<SolutionGroupBooking2> solutionSearchGroupBooking2) {
        this.salon_id = salon_id;
        this.salon_name = salon_name;
        this.solutionSearchGroupBooking2 = solutionSearchGroupBooking2;
    }

    public String getSalon_id() {
        return salon_id;
    }

    public void setSalon_id(String salon_id) {
        this.salon_id = salon_id;
    }

    public String getSalon_name() {
        return salon_name;
    }

    public void setSalon_name(String salon_name) {
        this.salon_name = salon_name;
    }

    public ArrayList<SolutionGroupBooking2> getSolutionSearchGroupBooking2() {
        return solutionSearchGroupBooking2;
    }

    public void setSolutionSearchGroupBooking2(ArrayList<SolutionGroupBooking2> solutionSearchGroupBooking2) {
        this.solutionSearchGroupBooking2 = solutionSearchGroupBooking2;
    }
}
