package com.ochy.ochy.cod;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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


    public static flightDataList convertToFlightDataList(flightModel data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        LocalDateTime dateTime1 = LocalDateTime.parse(data.date_otpr, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse(data.date_prib, formatter);

        DateTimeFormatter f = DateTimeFormatter.ofPattern("d MMMM H:mm", new Locale("ru"));
        String formattedDateTime1 = dateTime1.format(f);
        String formattedDateTime2 = dateTime2.format(f);

        Duration duration = Duration.between(dateTime1, dateTime2);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        List<String> seats = data.seats;
        int emptyCount = 0;

        for (String seat : seats) {
            if (seat.equals("")) {
                emptyCount++;
            }
        }
        String difference;
        String emptyCountString = String.valueOf(emptyCount);

        if (days > 0) {
            difference = String.format("%d дней", days);
            if (hours > 0) {
                if (minutes > 0) {
                    difference += String.format(", %d часов, %d минут", hours, minutes);
                } else {
                    difference += String.format(", %d часов", hours);
                }
            }
        } else if (hours > 0) {
            if (minutes > 0) {
                difference = String.format("%d часов, %d минут", hours, minutes);
            } else {
                difference = String.format("%d часов", hours);
            }
        } else if (minutes > 0) {
            difference = String.format("%d минут", minutes);
        } else {
            difference = "0 минут";
        }

        return  new flightDataList(data.cost+" ₽", data.otpr_city + " ➔ " + data.prib_city, formattedDateTime1 + " ➔ " + formattedDateTime2, emptyCountString + " мест", difference);
    }
}
