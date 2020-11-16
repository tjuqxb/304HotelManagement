package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Controller.GuestController;
import com.cpsc304.HotelManagement.Model.ReservationRequest;
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
            String dateString = "'" + guestController.dateToString(date) + "'";
            String singleSql = "UPDATE rm_record SET last_req = " + rid + " WHERE rm_number = " + rm_number +
                    " AND date = " + dateString + ";";
            String singleSql2 = "UPDATE reservation_req SET req_status =  1 WHERE rid = " + rid + ";";
            sql = sql + singleSql + singleSql2;
        }
        System.out.println(sql);
        jt.execute(sql);
    }

    public List<List<Map<String,Object>>> getRoomReservations(Double year, Double month) {
        List<Map<String, Object>>  rooms = roomHandler.getAllRooms();
        List<List<Map<String, Object>>> reservationRecords = new ArrayList<>();
        for(int i = 0; i < rooms.size(); i++) {
            reservationRecords.add(new ArrayList<>());
            Integer rm_number = (Integer) rooms.get(i).get("rm_number");
            String type = (String) rooms.get(i).get("type");
            String sql = "SELECT rm_number, last_req, date FROM rm_record rr WHERE rr.rm_number = ? " +
                    "AND EXTRACT(ISOYEAR FROM rr.DATE) = ? AND EXTRACT(MONTH FROM rr.DATE) = ? AND rr.last_req is not null OR " +
                    "EXTRACT(DAY FROM rr.DATE) = 1 " +
                    "ORDER BY date ASC ";
            String sql2 = "SELECT COUNT(*) FROM rm_record rr WHERE rr.rm_number = ? " +
                    "AND EXTRACT(ISOYEAR FROM rr.DATE) = ? AND EXTRACT(MONTH FROM rr.DATE) = ? ";
            List<Map<String, Object>> singleRoomReservation = jt.queryForList(sql, rm_number, year, month);
            List<Map<String, Object>> countArr = jt.queryForList(sql2, rm_number, year, month);
            singleRoomReservation.get(0).put("size", countArr.get(0).get("COUNT"));
            singleRoomReservation.get(0).put("type",type);
            reservationRecords.get(i).add(singleRoomReservation.get(0));
            for (int j = 0; j < singleRoomReservation.size(); j++) {
                Object last_req = singleRoomReservation.get(j).get("last_req");
                if (last_req != null) {
                    Integer rid = (Integer)last_req;
                    Date date = (Date)singleRoomReservation.get(j).get("date");
                    Integer dos = date.getDate();
                    System.out.println(date);
                    System.out.println(dos);
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

}
