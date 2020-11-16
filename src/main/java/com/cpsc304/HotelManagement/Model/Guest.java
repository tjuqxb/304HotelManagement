package com.cpsc304.HotelManagement.Model;

public class Guest {
    String name;
    String phone;
    Integer guest_id;

    public Guest() {
    }

    public Guest(String name, String phone, Integer guest_id) {
        this.name = name;
        this.phone = phone;
        this.guest_id = guest_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(Integer guest_id) {
        this.guest_id = guest_id;
    }
}
