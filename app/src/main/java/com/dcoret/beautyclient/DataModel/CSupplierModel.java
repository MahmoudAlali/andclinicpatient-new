package com.dcoret.beautyclient.DataModel;

public class CSupplierModel {

    String bdb_id,bdb_owner_name;

    public CSupplierModel(String bdb_id, String bdb_owner_name) {
        this.bdb_id = bdb_id;
        this.bdb_owner_name = bdb_owner_name;
    }

    public String getBdb_id() {
        return bdb_id;
    }

    public void setBdb_id(String bdb_id) {
        this.bdb_id = bdb_id;
    }

    public String getBdb_owner_name() {
        return bdb_owner_name;
    }

    public void setBdb_owner_name(String bdb_owner_name) {
        this.bdb_owner_name = bdb_owner_name;
    }
}
