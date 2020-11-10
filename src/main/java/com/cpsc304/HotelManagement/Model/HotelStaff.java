package com.cpsc304.HotelManagement.Model;

public class HotelStaff {
    Integer sid;
    String phone;
    String name;

    public HotelStaff() {
    }

    public HotelStaff(Integer sid, String phone, String name) {
        this.sid = sid;
        this.name = name;
        this.phone = phone;
    }
    public Integer getGuest_id() {
        return sid;
    }

    public void setGuest_id(Integer sid) {
        this.sid = sid;
    }

    public String getHSName() {
        return name;
    }

    public void setHSName(String name) {
        this.name = name;
    }

    public String getHSPhone() {
        return phone;
    }

    public void setHSPhone(String phone) {
        this.phone = phone;
    }
}
