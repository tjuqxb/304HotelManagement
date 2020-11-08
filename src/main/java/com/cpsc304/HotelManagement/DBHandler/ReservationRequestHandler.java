package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Model.ReservationGuest;
import com.cpsc304.HotelManagement.Model.ReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class ReservationRequestHandler {
    @Autowired
    JdbcTemplate jt;

    public Integer insertReservationRequest(ReservationRequest rr) {
        // auto index
        String sql1 = "SELECT MAX(rid) FROM reservation_req";
        List<Map<String, Object>> ret = jt.queryForList(sql1);
        Integer rid = (Integer) ret.get(0).get("max");
        if (rid == null) {
            rid = 0;
        } else {
            rid = rid + 1;
        }
        String sql2 = "INSERT INTO reservation_req VALUES(?,?,?,?,?,?,?,?);";
        jt.update(sql2,rid,rr.getGuest_id(),rr.getM_date(),rr.getM_time(),rr.getReq_code(),rr.getReq_status(),rr.getDate(),rr.getRm_number());
        return rid;
    }

    public List<Map<String,Object>> findSuccessRequests(Integer gid) {
        String sql = "SELECT * FROM reservation_req WHERE guest_id = ? AND req_status = 1 ORDER BY date DESC, rm_number ;";
        List<Map<String, Object>> ret = jt.queryForList(sql,gid);
        return ret;
    }


    public List<Map<String,Object>> findSuccessRequests(String photoID, String creditCard) {
        String sql = "SELECT reservation_guest.guest_id, rid, m_date, m_time, req_code, req_status, date, rm_number  FROM reservation_req, reservation_guest WHERE photo_identity = ? AND  credit_card = ? AND req_status = 1 " +
                " AND reservation_req.guest_id = reservation_guest.guest_id ORDER BY date DESC, rm_number ;";
        List<Map<String, Object>> ret = jt.queryForList(sql,photoID, creditCard);
        return ret;
    }


    public void cancelReservation(Integer rid) {
        String sql1 = "DELETE FROM reservation_req WHERE rid = ?;";
        jt.update(sql1, rid);
    }

}
