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

    public Integer insertGuest(Guest g) {
        // auto index
        String sql1 = "SELECT MAX(guest_id) FROM guest";
        List<Map<String, Object>> ret = jt.queryForList(sql1);
        Integer id = (Integer) ret.get(0).get("max");
        if (id == null) {
            id = 0;
        } else {
            id = id + 1;
        }
        String sql = "INSERT INTO guest VALUES (?, ?, ?);";
        jt.update(sql, id, g.getName(), g.getPhone());
        return id;
    }

    public List<Map<String, Object>> getAllGuests() {
        String sql = "SELECT * FROM guest;";
        List<Map<String, Object>> ret = jt.queryForList(sql);
        return ret;
    }



}
