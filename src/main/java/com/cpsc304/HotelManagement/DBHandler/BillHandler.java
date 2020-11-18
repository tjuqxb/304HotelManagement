package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Model.Bill;
import com.cpsc304.HotelManagement.Model.Bill;
import com.cpsc304.HotelManagement.Model.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Component
public class BillHandler {
    @Autowired
    JdbcTemplate jt;

    public Integer insertBill(Bill b) {
        // auto index
        String sql1 = "SELECT MAX(bill_num) FROM bill";
        List<Map<String, Object>> ret = jt.queryForList(sql1);
        Integer bill_num = (Integer) ret.get(0).get("max");
        if (bill_num == null) {
            bill_num= 0;
        } else {
            bill_num = bill_num + 1;
        }
        String sql = "INSERT INTO bill VALUES (?, ?, ?, ?);";
        jt.update(sql, bill_num, b.getBillDate(), b.getDate(), b.getRm_number());
        return bill_num;
    }

    public List<Map<String, Object>> getAllBill() {
        String sql = "SELECT * FROM bill;";
        List<Map<String, Object>> ret = jt.queryForList(sql);
        return ret;
    }


}
