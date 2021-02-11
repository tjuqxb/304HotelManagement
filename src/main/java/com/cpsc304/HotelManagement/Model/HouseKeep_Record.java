package com.cpsc304.HotelManagement.Model;

import java.util.Date;

public class HouseKeep_Record {
    Integer kp_id;
    Date date;
    Date time;
    Integer sid;
    Integer rm_number;

    public HouseKeep_Record() {
    }

    public HouseKeep_Record(Integer kp_id,Date date,Date time,Integer sid,Integer rm_number) {
        this.kp_id = kp_id;
        this.date = date;
        this.time = time;
        this.rm_number = rm_number;
        this.sid = sid;
    }
    public Integer getKp_id() {
        return kp_id;
    }

    public void setKp_id(Integer kp_id) {
        this.kp_id = kp_id;
    }

    public Date getKp_Date() {
        return date;
    }

    public void setKp_Date(Date date) {
        this.date = date;
    }

    public Date getKp_Time() {
        return time;
    }

    public void setKp_Time(Date time) {
        this.time = time;
    }

    public Integer getKp_sid() {return sid;}

    public void setKp_sid(Integer sid) {this.sid = sid;}

    public Integer getRm_number() {return rm_number;}

    public void setRm_number(Integer rm_number) { this.rm_number = rm_number;}
}
