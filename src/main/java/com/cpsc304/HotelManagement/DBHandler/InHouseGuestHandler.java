package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Model.Guest;
import com.cpsc304.HotelManagement.Model.InHouseGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class InHouseGuestHandler {
    @Autowired
    JdbcTemplate jt;
    @Autowired
    GuestHandler gh;

    public Integer insertInHouseGuest(InHouseGuest ig) {
        Integer id = gh.insertGuest(ig);
        String sql2 = "INSERT INTO in_house_guest VALUES (?);";
        jt.update(sql2, id);
        return id;
    }
}
