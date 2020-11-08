package com.cpsc304.HotelManagement.DBHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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
}
