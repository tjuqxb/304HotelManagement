package com.cpsc304.HotelManagement.Model;

import java.util.Date;

public class ReservationRequest {
    Integer guest_id;
    Integer rid;
    Date m_date;
    Date m_time;
    Integer req_code;   // 1 reserve 0 cancel
    Integer req_status; // 1 success 0 fail
    Date date;
    Integer rm_number;

    public ReservationRequest(Integer guest_id, Date m_date, Date m_time, Integer req_code, Integer req_status, Date date, Integer rm_number) {
        this.guest_id = guest_id;
        this.m_date = m_date;
        this.m_time = m_time;
        this.req_code = req_code;
        this.req_status = req_status;
        this.date = date;
        this.rm_number = rm_number;
    }

    public ReservationRequest() {
    }

    public Integer getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(Integer guest_id) {
        this.guest_id = guest_id;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Date getM_date() {
        return m_date;
    }

    public void setM_date(Date m_date) {
        this.m_date = m_date;
    }

    public Date getM_time() {
        return m_time;
    }

    public void setM_time(Date m_time) {
        this.m_time = m_time;
    }

    public Integer getReq_code() {
        return req_code;
    }

    public void setReq_code(Integer req_code) {
        this.req_code = req_code;
    }

    public Integer getReq_status() {
        return req_status;
    }

    public void setReq_status(Integer req_status) {
        this.req_status = req_status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getRm_number() {
        return rm_number;
    }

    public void setRm_number(Integer rm_number) {
        this.rm_number = rm_number;
    }
}
