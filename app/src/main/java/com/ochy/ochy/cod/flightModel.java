package com.ochy.ochy.cod;

public class flightModel {

    public String otpr_city, date_otpr, prib_city, date_prib, cost;

    public flightModel() {
    }


    public flightModel(String otpr_city, String date_otpr, String prib_city, String date_prib, String cost) {
        this.otpr_city = otpr_city;
        this.date_otpr = date_otpr;
        this.prib_city = prib_city;
        this.date_prib = date_prib;
        this.cost = cost;
    }
}
