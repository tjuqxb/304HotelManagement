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
    @Autowired
    GuestHandler gh;

    public Integer insertReservationGuest(ReservationGuest rg) {
        Integer id = gh.insertGuest(rg);
        String sql2 = "INSERT INTO reservation_guest VALUES (?, ?, ?, ?, ?);";
        jt.update(sql2, id, rg.getCredit_card(),rg.getPhoto_identity(),rg.getEmail(), rg.getMembership());
        return id;
    }





}
