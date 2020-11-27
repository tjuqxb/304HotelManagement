package com.cpsc304.HotelManagement.DBHandler;

import com.cpsc304.HotelManagement.Utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class CheckedInOutRecordHandler {
    @Autowired
    JdbcTemplate jt;
    @Autowired
    RoomHandler roomHandler;
    @Autowired
    RoomRecordHandler roomRecordHandler;
    @Autowired
    ReservationRequestHandler reservationRequestHandler;


    public void addCheckInRecord(Date in_date, Date in_time, Integer ck_in, Integer guest_id, Integer rm_number) {
        String sql1 = "SELECT MAX(ck_id) FROM checked_in_out_rec";
        List<Map<String, Object>> ret = jt.queryForList(sql1);
        Integer id = (Integer) ret.get(0).get("max");
        if (id == null) {
            id = 0;
        } else {
            id = id + 1;
        }
        String sql2 = "INSERT INTO checked_in_out_rec VALUES (?,?,?, NULL, NULL, ?, NULL, ?, ?);";
        jt.update(sql2, id, in_date, in_time, ck_in, guest_id, rm_number);
    }

    public void addCheckOutRecord(Integer ck_id, Date out_date, Date out_time, Integer ck_out) {
        String sql = "UPDATE checked_in_out_rec SET out_date = ?, out_time = ?, ck_out = ? WHERE ck_id = ?;";
        jt.update(sql, out_date, out_time, ck_out, ck_id);
    }

    public void deleteCheckOut(Integer ck_id) {
        String sql = "UPDATE checked_in_out_rec SET out_date = NULL, out_time = NULL, ck_out = NULL WHERE ck_id = ?;";
        jt.update(sql,ck_id);
    }

    public void deleteRecord(Integer ck_id) {
        String sql = "DELETE FROM checked_in_out_rec WHERE ck_id = ?;";
        jt.update(sql,ck_id);
    }

    public List<List<Map<String, Object>>> getCheckedInOutRecords(Double year, Double month) {
        List<Map<String, Object>>  rooms = roomHandler.getAllRooms();
        List<List<Map<String, Object>>> checkedInOutRecords = new ArrayList<>();
        for (int i = 0; i < rooms.size(); i++) {
            Integer rm_number = (Integer) rooms.get(i).get("rm_number");
            String sql = "SELECT * FROM checked_in_out_rec cr " +
                    "WHERE cr.rm_number = ? AND ((EXTRACT(ISOYEAR FROM cr.in_date) = ? AND EXTRACT(MONTH FROM cr.in_date) = ?) OR " +
                    "(EXTRACT(ISOYEAR FROM cr.out_date) = ? AND EXTRACT(MONTH FROM cr.out_date) = ?)) ORDER BY cr.in_date ASC; ";
            List<Map<String, Object>> singleRecords = jt.queryForList(sql, rm_number, year, month, year, month);
            for (int j = 0; j < singleRecords.size(); j++) {
                Date in_date = (Date) singleRecords.get(j).get("in_date");
                Date out_date = (Date)singleRecords.get(j).get("out_date");
                if (in_date != null) {
                    singleRecords.get(j).put("index", in_date.getDate()-1);
                    singleRecords.get(j).put("in_index",in_date.getDate()-1);
                }
                if (out_date != null) {
                    singleRecords.get(j).put("out_index", out_date.getDate() - 1);
                }
            }
            Date head = DateFormatter.IntegerToDate(year.intValue(), month.intValue(), 1);
            List<Map<String, Object>> first = reservationRequestHandler.findReqByRoomAndDate(rm_number, head);
            //System.out.println(first);
            if (!first.isEmpty()) {
                Integer guest_id = (Integer) first.get(0).get("guest_id");
                List<Map<String, Object>> starRec = getMonthEndsCheckedInOutRecords(year.intValue(), month.intValue(), 1, rm_number, guest_id);
                if (!starRec.isEmpty()) {
                    singleRecords.add(0, starRec.get(0));
                }
            }
            Integer last_day = DateFormatter.getLastDayOfMonth(year, month);
            Date tail = DateFormatter.IntegerToDate(year.intValue(), month.intValue(), last_day);
            List<Map<String, Object>> last = reservationRequestHandler.findReqByRoomAndDate(rm_number, tail);
            //System.out.println(last);
            if (!last.isEmpty()) {
                Integer guest_id = (Integer)last.get(0).get("guest_id");
                List<Map<String, Object>> endRec = getMonthEndsCheckedInOutRecords(year.intValue(), month.intValue(), last_day, rm_number, guest_id);
                if (!endRec.isEmpty()) {
                    singleRecords.add(singleRecords.size(), endRec.get(0));
                }
            }
            checkedInOutRecords.add(singleRecords);
        }
        return checkedInOutRecords;
    }

    // Handle the corner case of check in/out records cross months
    public List<Map<String, Object>> getMonthEndsCheckedInOutRecords(Integer year, Integer month, Integer day,
                                                                   Integer rm_number, Integer guest_id) {
        Date comp = DateFormatter.IntegerToDate(year, month, day);
        //System.out.println(comp);
        List<Map<String, Object>> ret = new ArrayList<>();
        if (day == 1) {
            //System.out.println(rm_number);
            //System.out.println(guest_id);
            String sql1 = "SELECT MAX(cr.in_date) FROM checked_in_out_rec cr WHERE cr.in_date < " +
                    "date " + "'" + DateFormatter.dateToString(comp) + "' " +
                    "AND cr.rm_number = ? AND cr.guest_id = ?;";
            List<Map<String, Object>> result1 = jt.queryForList(sql1, rm_number, guest_id);
            //System.out.println(result1);
            Date startDate = (Date)result1.get(0).get("max");
            if (startDate != null) {
                if (reservationRequestHandler.allReserved(startDate, comp, rm_number, guest_id)) {
                    String sqlA = "SELECT * FROM checked_in_out_rec cr WHERE cr.in_date = ? AND cr.rm_number = ? AND cr.guest_id = ?;";
                    List<Map<String, Object>> startRec = jt.queryForList(sqlA, startDate, rm_number, guest_id);
                    Integer vrIndex = DateFormatter.getDateDifference(comp, startDate);
                    Date out_date = (Date)startRec.get(0).get("out_date");
                    if (out_date != null) {
                        Integer vrIndex2 = DateFormatter.getDateDifference(comp, out_date);
                        startRec.get(0).put("out_index", vrIndex2);
                    }
                    startRec.get(0).put("in_index", vrIndex);
                    startRec.get(0).put("index", 0);
                    ret = startRec;
                }
            }
        } else {
            String sql2 = "SELECT MIN(cr.in_date) FROM checked_in_out_rec cr WHERE cr.in_date > " +
                    "date " + "'" + DateFormatter.dateToString(comp) + "' " +
                    " AND cr.rm_number = ? AND cr.guest_id = ?;";
            //System.out.println(rm_number);
            //System.out.println(guest_id);
            List<Map<String, Object>> result1 = jt.queryForList(sql2,rm_number, guest_id);
            //System.out.println(result1);
            Date endDate = (Date)result1.get(0).get("min");
            //System.out.println(endDate);
            if(endDate != null) {
                if (reservationRequestHandler.allReserved(comp, endDate, rm_number, guest_id)) {
                    String sqlB = "SELECT * FROM checked_in_out_rec cr WHERE cr.in_date = ? AND cr.rm_number = ? AND cr.guest_id = ?;";
                    List<Map<String, Object>> endRec = jt.queryForList(sqlB, endDate, rm_number, guest_id);
                    Integer vrIndex = day - 1 + DateFormatter.getDateDifference(comp, endDate);
                    endRec.get(0).put("index", day - 1);
                    endRec.get(0).put("in_index", vrIndex);
                    Date out_date = (Date)endRec.get(0).get("out_date");
                    if (out_date != null) {
                        Integer vrIndex2 = day - 1 + DateFormatter.getDateDifference(comp, out_date);
                        endRec.get(0).put("out_index", vrIndex2);
                    }
                    ret = endRec;
                }
            }
        }
        return ret;
    }
}
