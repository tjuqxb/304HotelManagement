package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Model.HotelStaff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
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

    public List<Map<String, Object>> getAllReceptionists() {
        String sql = "SELECT * FROM hotel_staff, receptionist WHERE hotel_staff.sid = receptionist.sid;";
        List<Map<String, Object>> ret = jt.queryForList(sql);
        return ret;
    }

    public List<Map<String, Object>> getEligibleHotelStaff(Date date1, Date date2) {
        String sql = "SELECT *" +
                "FROM hotel_staff hs " +
                "WHERE " +
                "NOT EXISTS ( " +
                "(SELECT rm_number  FROM room) " +
                "EXCEPT " +
                "(SELECT " +
                " DISTINCT rm_number FROM checked_in_out_rec cr " +
                "WHERE  " +
                "(cr.in_date >= ? AND " +
                "cr.in_date <= ? AND cr.ck_in = hs.sid) OR (cr.out_date >= ? AND " +
                "cr.out_date <= ? AND cr.ck_out = hs.sid)));";
        List<Map<String, Object>> ret = jt.queryForList(sql, date1, date2,date1, date2);
        return ret;
    }

}
