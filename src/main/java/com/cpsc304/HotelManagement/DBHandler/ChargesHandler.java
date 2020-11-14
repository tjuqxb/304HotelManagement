package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Model.Charges;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ChargesHandler {

    @Autowired
    JdbcTemplate jt;
    public Integer InsertCharge(Charges c) {
        // auto index
        String sql1 = "SELECT MAX(cid) FROM charges";
        List<Map<String, Object>> ret = jt.queryForList(sql1);
        Integer id = (Integer) ret.get(0).get("max");
        if (id == null) {
            id = 0;
        } else {
            id = id + 1;
        }
        String sql = "INSERT INTO charges VALUES (?, ?, ?);";
        jt.update(sql, c.getBillNum(), id, c.getServices());
        return id;
    }

    public List<Map<String, Object>> getAllCharges() {
        String sql = "SELECT * FROM charges;";
        List<Map<String, Object>> ret = jt.queryForList(sql);
        return ret;
    }

}
