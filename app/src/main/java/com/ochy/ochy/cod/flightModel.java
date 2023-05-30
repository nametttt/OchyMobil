package com.ochy.ochy.cod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class flightModel {

    public String otpr_city, date_otpr, prib_city, date_prib, cost;
    public List<String> seats;

    public flightModel() {
    }

    public flightModel(String otpr_city, String date_otpr, String prib_city, String date_prib, String cost, List<String> seats) {
        this.otpr_city = otpr_city;
        this.date_otpr = date_otpr;
        this.prib_city = prib_city;
        this.date_prib = date_prib;
        this.cost = cost;
        this.seats = seats;
    }

    public static flightModel convertToFlightModel(flightDataList data) {
        String[] ezdaSplit = data.getEzda().split(" ➔ ");
        String otpr_city = ezdaSplit[0];
        String prib_city = ezdaSplit[1];

        String[] dateSplit = data.getDate().split(" ➔ ");
        String date_otpr = formatDate(dateSplit[0]);
        String date_prib = formatDate(dateSplit[1]);

        String cost = data.getCost().replaceAll(" ₽", "");

        List<String> seats = generateSeats();

        return new flightModel(otpr_city, date_otpr, prib_city, date_prib, cost, seats);
    }

    private static String formatDate(String inputDate) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("d MMMM H:mm", new Locale("ru"));
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");

        try {
            Date date = inputFormat.parse(inputDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.YEAR, 2023); // Устанавливаем нужный год

            return outputFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static List<String> generateSeats() {
        List<String> seats = new ArrayList<>();
        for (int i = 1; i <= 36; i++) {
            seats.add("Место " + i);
        }
        return seats;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        flightModel other = (flightModel) obj;

        return otpr_city.equals(other.otpr_city) &&
                date_otpr.equals(other.date_otpr) &&
                prib_city.equals(other.prib_city) &&
                date_prib.equals(other.date_prib) &&
                cost.equals(other.cost) &&
                seats.equals(other.seats);
    }
}
