package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Model.Guest;
import com.cpsc304.HotelManagement.Model.ReservationGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;

@Component
public class ReservationGuestHandler {
    @Autowired
    JdbcTemplate jt;

    public void insertReservationGuest(ReservationGuest rg) {
        String sql = "INSERT INTO guest VALUES (?, ?, ?);";
        Guest g = rg;
        jt.update(sql, g.getId(), g.getName(), g.getPhone());
    }





}
