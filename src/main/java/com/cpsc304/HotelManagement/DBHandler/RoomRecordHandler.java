package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Controller.GuestController;
import com.cpsc304.HotelManagement.Model.ReservationRequest;
import com.cpsc304.HotelManagement.Utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class RoomRecordHandler {
    @Autowired
    JdbcTemplate jt;
    @Autowired
    GuestController guestController;
    @Autowired
    RoomHandler roomHandler;
    @Autowired
    ReservationRequestHandler reservationRequestHandler;
    @Autowired
    ReserveWithHandler reserveWithHandler;

    public void reserveRoomRecords(ArrayList<ReservationRequest> reservationRequests, ArrayList<Integer> rids) {
        String sql = "";
        for (int i = 0; i < reservationRequests.size(); i++) {
            Integer rid = rids.get(i);
            ReservationRequest rq = reservationRequests.get(i);
            Integer rm_number = rq.getRm_number();
            Date date = rq.getDate();
            String dateString = "'" + DateFormatter.dateToString(date) + "'";
            String singleSql = "UPDATE rm_record SET last_req = " + rid + " WHERE rm_number = " + rm_number +
                    " AND date = " + dateString + ";";
            String singleSql2 = "UPDATE reservation_req SET req_status =  1 WHERE rid = " + rid + ";";
            sql = sql + singleSql + singleSql2;
        }
        //System.out.println(sql);
        jt.execute(sql);
    }

    public List<List<Map<String,Object>>> getRoomReservations(Double year, Double month) {
        List<Map<String, Object>>  rooms = roomHandler.getAllRooms();
        List<List<Map<String, Object>>> reservationRecords = new ArrayList<>();
        for(int i = 0; i < rooms.size(); i++) {
            reservationRecords.add(new ArrayList<>());
            Integer rm_number = (Integer) rooms.get(i).get("rm_number");
            //System.out.println(rm_number);
            String type = (String) rooms.get(i).get("type");
            String sql = "SELECT rm_number, last_req, date FROM rm_record rr WHERE rr.rm_number = ? " +
                    "AND EXTRACT(ISOYEAR FROM rr.DATE) = ? AND EXTRACT(MONTH FROM rr.DATE) = ? AND (rr.last_req is not null OR " +
                    "EXTRACT(DAY FROM rr.DATE) = 1) " +
                    "ORDER BY date ASC ";
            List<Map<String, Object>> singleRoomReservation = jt.queryForList(sql, rm_number, year, month);
            singleRoomReservation.add(0, rooms.get(i));
            reservationRecords.get(i).add(singleRoomReservation.get(0));
            for (int j = 0; j < singleRoomReservation.size(); j++) {
                Object last_req = singleRoomReservation.get(j).get("last_req");
                if (last_req != null) {
                    Integer rid = (Integer)last_req;
                    Date date = (Date)singleRoomReservation.get(j).get("date");
                    Integer dos = date.getDate();
                    //System.out.println(date);
                    //System.out.println(dos);
                    Map<String, Object> reservationGuest = reservationRequestHandler.getReservationGuest(rid).get(0);
                    List<Map<String, Object>> inHouseGuests = reserveWithHandler.getInHouseGuests(rid);
                    singleRoomReservation.get(j).put("index",dos - 1);
                    singleRoomReservation.get(j).put("reservation_guest", reservationGuest);
                    singleRoomReservation.get(j).put("in_house_guests", inHouseGuests);
                    reservationRecords.get(i).add(singleRoomReservation.get(j));
                }
            }
        }
        return reservationRecords;
    }

    public List<Map<String, Object>> findAvailableRooms(Integer cnt, String inDate, String outDate) {
        String sql;
        inDate = "'" + inDate + "'";
        outDate = "'" + outDate + "'";
        if (cnt == 1) {
            sql = "WITH selected_records AS (" +
                    "SELECT * FROM " +
                    "rm_record rr " +
                    "WHERE rr.date >= date "+ inDate + "  AND rr.date <= date "+ outDate +" AND rr.last_req is NULL " +
                    ")SELECT sr.rm_number AS room_number, room.type, CAST (AVG (sr.price) AS INTEGER ) AS average_price FROM " +
                    "selected_records sr, room " +
                    "WHERE sr.rm_number = room.rm_number AND room.status_id = 1 AND room.type = ? " +
                    "GROUP BY sr.rm_number, room.type " +
                    "HAVING  COUNT (*) = date " + outDate + " - date " + inDate + " + 1;";
        } else {
            sql = "WITH selected_records AS (" +
                    "SELECT * FROM " +
                    "rm_record rr " +
                    "WHERE rr.date >= date "+ inDate + "  AND rr.date <= date "+ outDate +" AND rr.last_req is NULL " +
                    ")SELECT sr.rm_number AS room_number, room.type, CAST (AVG (sr.price) AS INTEGER ) AS average_price FROM " +
                    "selected_records sr, room " +
                    "WHERE sr.rm_number = room.rm_number AND room.status_id = 1 AND room.type != ? " +
                    "GROUP BY sr.rm_number, room.type " +
                    "HAVING  COUNT (*) = date " + outDate + " - date " + inDate + " + 1;";
        }
        return jt.queryForList(sql, "1BED");
    }

    public List<Map<String, Object>> findCheapAvailableRooms(Integer cnt, String inD, String outD) {
        String sql;
        String inDate = "'" + inD + "'";
        String outDate = "'" + outD + "'";
        if (cnt == 1) {
            sql = "WITH Temp AS (WITH selected_records AS ( SELECT * FROM rm_record rr " +
                    "WHERE rr.date >= DATE " + inDate +
                    " AND rr.date <=  DATE " + outDate +
                    " AND rr.last_req is NULL) " +
                    "SELECT sr.rm_number AS room_number, room.type, CAST (AVG (sr.price) AS INTEGER ) AS average_price FROM " +
                    "selected_records sr, room " +
                    "WHERE sr.rm_number = room.rm_number AND room.status_id = 1 AND room.type = ?" +
                    "GROUP BY sr.rm_number, room.type " +
                    "HAVING  COUNT (*) =  DATE " + outDate +
                    " - DATE " + inDate +
                    " + 1) " +
                    "SELECT * FROM Temp WHERE Temp.average_price = (SELECT MIN(average_price) FROM TEMP);";
        } else {
            sql = "WITH Temp AS (WITH selected_records AS ( SELECT * FROM rm_record rr " +
                    "WHERE rr.date >=  DATE " + inDate +
                    " AND rr.date <= DATE " + outDate +
                    " AND rr.last_req is NULL) " +
                    "SELECT sr.rm_number AS room_number, room.type, CAST (AVG (sr.price) AS INTEGER ) AS average_price FROM " +
                    "selected_records sr, room " +
                    "WHERE sr.rm_number = room.rm_number AND room.status_id = 1 AND room.type != ? " +
                    "GROUP BY sr.rm_number, room.type " +
                    "HAVING  COUNT (*) =  DATE " + outDate +
                    " -  DATE" + inDate +
                    " + 1) " +
                    "SELECT * FROM Temp WHERE Temp.average_price = (SELECT MIN(average_price) FROM TEMP);";
        }
        return jt.queryForList(sql, "1BED");
    }

    public List<Map<String, Object>> getRoomRecordsByCheckId(Integer ck_id) {
        String sql = "SELECT rr.rm_number, rr.price, rr.date FROM rm_record rr, checked_in_out_rec cr " +
                "WHERE rr.date >= cr.in_date AND rr.date <= cr.out_date AND rr.rm_number = cr.rm_number AND cr.ck_id = ? " +
                "ORDER BY rr.date ASC;";
        return jt.queryForList(sql,ck_id);
    }

    public List<Map<String, Object>> findRoomsInGroup(String query) {
        String sql = "SELECT * FROM rm_record ORDER BY rm_number, date;";
        if (query.equals("min")) {
            sql = "SELECT rm_number, MIN(price) from rm_record GROUP BY rm_number ORDER BY rm_number;";
        }
        if (query.equals("max")) {
            sql = "SELECT rm_number, MAX(price) from rm_record GROUP BY rm_number ORDER BY rm_number;";
        }
        if (query.equals("avg")) {
            sql = "SELECT rm_number, CAST(AVG(price) AS INTEGER) from rm_record GROUP BY rm_number ORDER BY rm_number;";
        }
        return jt.queryForList(sql);

    }


}
