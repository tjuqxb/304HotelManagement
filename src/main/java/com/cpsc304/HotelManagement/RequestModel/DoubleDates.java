package com.cpsc304.HotelManagement.RequestModel;

public class DoubleDates {
    String inDate;
    String outDate;

    public DoubleDates() {

    }

    public DoubleDates(String inDate, String outDate) {
        this.inDate = inDate;
        this.outDate = outDate;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }
}
