package com.cpsc304.HotelManagement.Model;

public class Charges {
    Integer bill_num;
    Integer cid;
    String service_name;

    public Charges() {
    }

    public Charges(Integer bill_num, Integer cid, String service_name) {
        this.bill_num = bill_num;
        this.cid = cid;
        this.service_name = service_name;
    }

    public Integer getBillNum() {
        return bill_num;
    }

    public void setBillNum(Integer bill_num) {
        this.bill_num = bill_num;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getServices() {
        return service_name;
    }

    public void setServices(String service_name) {
        this.service_name = service_name;
    }
}
