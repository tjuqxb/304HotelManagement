package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Model.Guest;
import com.cpsc304.HotelManagement.Model.ReservationGuest;
import com.cpsc304.HotelManagement.Model.ReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReservationGuestHandler {
    @Autowired
    JdbcTemplate jt;
    @Autowired
    GuestHandler gh;

    public Integer findReservationGuest(ReservationGuest rg) {
        String sql = "SELECT * FROM reservation_guest WHERE photo_identity = ? AND credit_card = ?;";
        List<Map<String, Object>> ret = jt.queryForList(sql,rg.getPhoto_identity(),rg.getCredit_card());
        if(ret.size() > 0) {
            Integer index =  (Integer) ret.get(0).get("guest_id");
            return index;
        }
        return -1;
    }

    public Integer insertReservationGuest(ReservationGuest rg) {
        Integer index = findReservationGuest(rg);
        if (index != -1) return index;
        Integer id = gh.insertGuest(rg);
        String sql2 = "INSERT INTO reservation_guest VALUES (?, ?, ?, ?, ?);";
        jt.update(sql2, id, rg.getCredit_card(),rg.getPhoto_identity(),rg.getEmail(), rg.getMembership());
        return id;
    }







}
