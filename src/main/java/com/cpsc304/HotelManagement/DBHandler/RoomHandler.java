package com.cpsc304.HotelManagement.DBHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class RoomHandler {
    @Autowired
    JdbcTemplate jt;

    public List<Map<String, Object>> getAllRooms() {
        String sql = "SELECT * FROM room";
        return jt.queryForList(sql);
    }

}
