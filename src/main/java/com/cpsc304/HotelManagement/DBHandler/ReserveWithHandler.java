package com.cpsc304.HotelManagement.DBHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ReserveWithHandler {
    @Autowired
    JdbcTemplate jt;

    public void insertReserveWith(ArrayList<Integer> rids, ArrayList<Integer> gids) {
        for (int i = 0; i < rids.size(); i++) {
            Integer rid = rids.get(i);
            for (int j = 0; j < gids.size(); j++) {
                Integer gid = gids.get(j);
                String sql = "INSERT INTO reserve_with VALUES(?,?)";
                jt.update(sql,rid,gid);
            }
        }
    }

    public List<Map<String, Object>> getInHouseGuests(Integer rid) {
        String sql  = "SELECT g.guest_id, g.name FROM " +
                "reserve_with rw, guest g " +
                "WHERE rw.rid = ? AND rw.guest_id = g.guest_id;";
        return jt.queryForList(sql, rid);
    }
}
