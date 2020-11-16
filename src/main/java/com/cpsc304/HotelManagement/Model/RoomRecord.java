package com.cpsc304.HotelManagement.Model;

import java.util.Date;

public class RoomRecord {
    Integer rm_number;
    Integer price;
    Date date;
    Integer last_req;

    public RoomRecord(Integer rm_number, Integer price, Date date, Integer last_req) {
        this.rm_number = rm_number;
        this.price = price;
        this.date = date;
        this.last_req = last_req;
    }

    public RoomRecord() {
    }

    public Integer getRm_number() {
        return rm_number;
    }

    public void setRm_number(Integer rm_number) {
        this.rm_number = rm_number;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getLast_req() {
        return last_req;
    }

    public void setLast_req(Integer last_req) {
        this.last_req = last_req;
    }
}
