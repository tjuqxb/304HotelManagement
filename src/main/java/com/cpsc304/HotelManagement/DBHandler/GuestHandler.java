package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Model.Guest;
import com.cpsc304.HotelManagement.Model.ReservationGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GuestHandler {
    @Autowired
    JdbcTemplate jt;

    public void insertGuest(ReservationGuest rg) {
        String sql = "INSERT INTO guest VALUES (?, ?, ?);";
        Guest g = rg;
        jt.update(sql, g.getId(), g.getName(), g.getPhone());
    }

    public List<Map<String, Object>> getAllGuests() {
        String sql = "SELECT * FROM guest;";
        List<Map<String, Object>> ret = jt.queryForList(sql);
        return ret;
    }



}
