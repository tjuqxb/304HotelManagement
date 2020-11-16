package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Model.HotelStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class HotelStaffHandler {
    @Autowired
    JdbcTemplate jt;

    public Integer insertHotelStaff(HotelStaff h) {
        // auto index
        String sql1 = "SELECT MAX(sid) FROM hotel_staff";
        List<Map<String, Object>> ret = jt.queryForList(sql1);
        Integer id = (Integer) ret.get(0).get("max");
        if (id == null) {
            id = 0;
        } else {
            id = id + 1;
        }
        String sql = "INSERT INTO hotel_staff VALUES (?, ?, ?);";
        jt.update(sql, id, h.getHSName(), h.getHSPhone());
        return id;
    }

    public List<Map<String, Object>> getAllHotelStaff() {
        String sql = "SELECT * FROM hotel_staff;";
        List<Map<String, Object>> ret = jt.queryForList(sql);
        return ret;
    }
}
