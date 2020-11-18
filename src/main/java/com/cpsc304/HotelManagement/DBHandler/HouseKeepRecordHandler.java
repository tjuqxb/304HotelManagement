package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Model.HouseKeep_Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class HouseKeepRecordHandler {
    @Autowired
    JdbcTemplate jt;

    public Integer insertHouseKeepRecord(HouseKeep_Record hkr) {
        // auto index
        String sql1 = "SELECT MAX(kp_id) FROM housekeep_record";
        List<Map<String, Object>> ret = jt.queryForList(sql1);
        Integer id = (Integer) ret.get(0).get("max");
        if (id == null) {
            id = 0;
        } else {
            id = id + 1;
        }
        String sql = "INSERT INTO housekeep_record VALUES ( ?, ?, ?, ?, ?);";
        jt.update(sql, id, hkr.getKp_Date(), hkr.getKp_Time(), hkr.getKp_sid(), hkr.getRm_number());
        return id;
    }

    public List<Map<String, Object>> getAllHouseKeepRecord() {
        String sql = "SELECT * FROM housekeep_record;";
        List<Map<String, Object>> ret = jt.queryForList(sql);
        return ret;
    }
}
