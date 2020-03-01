package com.dcoret.beautyclient.DataModel;

import java.util.ArrayList;

public class ReservationClients {
    String clientName,salonName;
    ArrayList <ReservationClientsEmployee> reservationClientsEmployees;

    public ReservationClients(String clientName, String salonName, ArrayList<ReservationClientsEmployee> reservationClientsEmployees) {
        this.clientName = clientName;
        this.salonName = salonName;
        this.reservationClientsEmployees = reservationClientsEmployees;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getSalonName() {
        return salonName;
    }

    public void setSalonName(String salonName) {
        this.salonName = salonName;
    }

    public ArrayList<ReservationClientsEmployee> getReservationClientsEmployees() {
        return reservationClientsEmployees;
    }

    public void setReservationClientsEmployees(ArrayList<ReservationClientsEmployee> reservationClientsEmployees) {
        this.reservationClientsEmployees = reservationClientsEmployees;
    }
}
