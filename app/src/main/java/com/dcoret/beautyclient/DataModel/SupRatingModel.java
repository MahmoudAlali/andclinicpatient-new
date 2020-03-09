package com.dcoret.beautyclient.DataModel;

import hyogeun.github.com.colorratingbarlib.ColorRatingBar;

public class SupRatingModel {
    String bdb_type,bdb_book_id;
    ColorRatingBar bdb_value;

    public SupRatingModel(String bdb_type, ColorRatingBar bdb_value, String bdb_book_id) {
        this.bdb_type = bdb_type;
        this.bdb_value = bdb_value;
        this.bdb_book_id = bdb_book_id;
    }

    public String getBdb_type() {
        return bdb_type;
    }

    public void setBdb_type(String bdb_type) {
        this.bdb_type = bdb_type;
    }

    public ColorRatingBar getBdb_value() {
        return bdb_value;
    }

    public void setBdb_value(ColorRatingBar bdb_value) {
        this.bdb_value = bdb_value;
    }

    public String getBdb_book_id() {
        return bdb_book_id;
    }

    public void setBdb_book_id(String bdb_book_id) {
        this.bdb_book_id = bdb_book_id;
    }
}
