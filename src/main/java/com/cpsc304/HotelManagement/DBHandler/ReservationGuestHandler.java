package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Model.Guest;
import com.cpsc304.HotelManagement.Model.ReservationGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ReservationGuestHandler {
    @Autowired
    JdbcTemplate jt;

    public void insertReservationGuest(ReservationGuest rg) {
        String sql1 = "SELECT MAX(guest_id) FROM guest";
        List<Map<String, Object>> ret = jt.queryForList(sql1);
        Integer id = (Integer) ret.get(0).get("max");
        if (id == null) {
            id = 0;
        } else {
            id = id + 1;
        }
        String sql = "INSERT INTO guest VALUES (?, ?, ?);";
        Guest g = rg;
        jt.update(sql, id,g.getName(), g.getPhone());
        String sql2 = "INSERT INTO reservation_guest VALUES (?, ?, ?, ?, ?);";
        jt.update(sql2, id, rg.getCredit_card(),rg.getPhoto_identity(),rg.getEmail(), rg.getMembership());
    }





}
