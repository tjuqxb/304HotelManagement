package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Model.Guest;
import com.cpsc304.HotelManagement.Model.ReservationGuest;
import com.cpsc304.HotelManagement.Model.Services;
import com.cpsc304.HotelManagement.Model.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Component
public class ServicesHandler {
    @Autowired
    JdbcTemplate jt;

    public Integer insertService(Services s) {
        // auto index
        String sql1 = "SELECT MAX(service_id) FROM services";
        List<Map<String, Object>> ret = jt.queryForList(sql1);
        Integer id = (Integer) ret.get(0).get("max");
        if (id == null) {
            id = 0;
        } else {
            id = id + 1;
        }
        String sql = "INSERT INTO services VALUES (?, ?, ?, ?);";
        jt.update(sql, id, s.getServices(), s.getFee(), s.getDescription());
        return id;
    }

    public Integer findServicePrice(Services s) {
        String sql = "SELECT * FROM services WHERE service_name = ?;";
        List<Map<String, Object>> ret = jt.queryForList(sql,s.getServices(),s.getFee());
        if(ret.size() > 0) {
            Integer index =  (Integer) ret.get(0).get("service_id");
            return index;
        }
        return -1;
    }

    public List<Map<String, Object>> getAllGuests() {
        String sql = "SELECT * FROM services;";
        List<Map<String, Object>> ret = jt.queryForList(sql);
        return ret;
    }

}
