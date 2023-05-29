package com.ochy.ochy.cod;

public class flightDataList {

    String cost, ezda, date, places, duration;

    public flightDataList() {
    }

    public flightDataList(String cost, String ezda, String date, String places, String duration) {
        this.cost = cost;
        this.ezda = ezda;
        this.date = date;
        this.places = places;
        this.duration = duration;
    }

    public String getCost() {
        return cost;
    }

    public String getEzda() {
        return ezda;
    }

    public String getDate() {
        return date;
    }

    public String getPlaces() {
        return places;
    }

    public String getDuration() {
        return duration;
    }
}
