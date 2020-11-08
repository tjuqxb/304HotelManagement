package com.cpsc304.HotelManagement.Model;

import java.util.Date;

public class RoomRecord {
    Long rm_number;
    Integer price;
    Date date;
    Long last_req;

    public RoomRecord(Long rm_number, Integer price, Date date, Long last_req) {
        this.rm_number = rm_number;
        this.price = price;
        this.date = date;
        this.last_req = last_req;
    }

    public RoomRecord() {
    }

    public Long getRm_number() {
        return rm_number;
    }

    public void setRm_number(Long rm_number) {
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

    public Long getLast_req() {
        return last_req;
    }

    public void setLast_req(Long last_req) {
        this.last_req = last_req;
    }
}
