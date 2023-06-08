package com.example.ridesync;

public class _9_ModelBusData {
    String To,From, BusNumber, BusSeats, BusFair,BusTime,  BookedSeats;
    public _9_ModelBusData() {
    }
    public _9_ModelBusData(String to, String from, String busNumber, String busSeats, String busFair, String busTime, String busPassengers) {
        To = to;
        From = from;
        BusNumber = busNumber;
        BusSeats = busSeats;
        BusFair = busFair;
        BusTime = busTime;
        BookedSeats = busPassengers;
    }

    public String getTo() {
        return To;
    }

    public void setTo(String to) {
        To = to;
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public String getBusNumber() {
        return BusNumber;
    }

    public void setBusNumber(String busNumber) {
        BusNumber = busNumber;
    }


    public String getBusSeats() {
        return BusSeats;
    }

    public void setBusSeats(String busSeats) {
        BusSeats = busSeats;
    }

    public String getBusFair() {
        return BusFair;
    }

    public void setBusFair(String busFair) {
        BusFair = busFair;
    }

    public String getBusTime() {
        return BusTime;
    }

    public void setBusTime(String busTime) {
        BusTime = busTime;
    }

    public String getBookedSeats() {
        return BookedSeats;
    }

    public void setBookedSeats(String busPassengers) {
        BookedSeats = busPassengers;
    }
}
