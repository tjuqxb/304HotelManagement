package com.cpsc304.HotelManagement.Model;

import java.util.Date;

public class Bill {
    Integer bill_num;
    Date bill_date;
    Integer rm_number;
    Date date;

    public Bill() {
    }

    public Bill(Integer bill_num, Date bill_date, Integer rm_number, Date date) {
        this.bill_num = bill_num;
        this.bill_date = bill_date;
        this.rm_number = rm_number;
        this.date = date;
    }

    public Integer getBillNum() {
        return bill_num;
    }

    public void setBillNum(Integer bill_num) {
        this.bill_num = bill_num;
    }

    public Date getBillDate() {
        return bill_date;
    }

    public void setBillDate(Date bill_date) {
        this.bill_date = bill_date;
    }

    public Integer getRm_number() {return rm_number;}

    public void setRm_number(Integer rm_number) {this.rm_number = rm_number;}

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
