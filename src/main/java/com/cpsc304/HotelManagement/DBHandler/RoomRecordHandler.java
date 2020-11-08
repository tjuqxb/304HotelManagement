package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Controller.GuestController;
import com.cpsc304.HotelManagement.Model.ReservationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class RoomRecordHandler {
    @Autowired
    JdbcTemplate jt;
    @Autowired
    GuestController gc;

    public void reserveRoomRecords(ArrayList<ReservationRequest> reservationRequests, ArrayList<Integer> rids) {
        String sql = "BEGIN; ";
        for (int i = 0; i < reservationRequests.size(); i++) {
            Integer rid = rids.get(i);
            ReservationRequest rq = reservationRequests.get(i);
            Integer rm_number = rq.getRm_number();
            Date date = rq.getDate();
            String dateString = "'" + gc.dateToString(date) + "'";
            String singleSql = "UPDATE rm_record SET last_req = " + rid + " WHERE rm_number = " + rm_number +
                    " AND date = " + dateString + ";";
            String singleSql2 = "UPDATE reservation_req SET req_status =  1 WHERE rid = " + rid + ";";
            sql = sql + singleSql + singleSql2;
        }
        sql = sql + "COMMIT;";
        System.out.println(sql);
        jt.execute(sql);
    }

}
