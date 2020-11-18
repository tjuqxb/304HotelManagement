package com.cpsc304.HotelManagement.Model;

import java.util.Date;

public class CheckedInOutRecord {
    Integer ck_id;
    Date in_date;
    Date out_date;
    Date in_time;
    Date out_time;
    Integer guest_id;
    Integer rm_number;
    Integer ck_in;
    Integer ck_out;

    public CheckedInOutRecord(Date in_date, Date out_date, Date in_time, Date out_time, Integer guest_id, Integer rm_number, Integer ck_in, Integer ck_out) {
        this.in_date = in_date;
        this.out_date = out_date;
        this.in_time = in_time;
        this.out_time = out_time;
        this.guest_id = guest_id;
        this.rm_number = rm_number;
        this.ck_in = ck_in;
        this.ck_out = ck_out;
    }

    public Integer getCk_id() {
        return ck_id;
    }

    public void setCk_id(Integer ck_id) {
        this.ck_id = ck_id;
    }

    public Date getIn_date() {
        return in_date;
    }

    public void setIn_date(Date in_date) {
        this.in_date = in_date;
    }

    public Date getOut_date() {
        return out_date;
    }

    public void setOut_date(Date out_date) {
        this.out_date = out_date;
    }

    public Date getIn_time() {
        return in_time;
    }

    public void setIn_time(Date in_time) {
        this.in_time = in_time;
    }

    public Date getOut_time() {
        return out_time;
    }

    public void setOut_time(Date out_time) {
        this.out_time = out_time;
    }

    public Integer getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(Integer guest_id) {
        this.guest_id = guest_id;
    }

    public Integer getRm_number() {
        return rm_number;
    }

    public void setRm_number(Integer rm_number) {
        this.rm_number = rm_number;
    }

    public Integer getCk_in() {
        return ck_in;
    }

    public void setCk_in(Integer ck_in) {
        this.ck_in = ck_in;
    }

    public Integer getCk_out() {
        return ck_out;
    }

    public void setCk_out(Integer ck_out) {
        this.ck_out = ck_out;
    }
}
