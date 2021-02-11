package com.cpsc304.HotelManagement.Controller;


import com.cpsc304.HotelManagement.DBHandler.CheckedInOutRecordHandler;
import com.cpsc304.HotelManagement.Utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checked-in-out-records")
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class CheckedInOutRecordController {

    @Autowired
    CheckedInOutRecordHandler checkedInOutRecordHandler;

    @GetMapping("/{year}/{month}")
    public List<List<Map<String, Object>>> getRoomRecords(@PathVariable Double year, @PathVariable Double month) {
        return checkedInOutRecordHandler.getCheckedInOutRecords(year, month);
    }

    @PostMapping("/check-in")
    public void addCheckIn(@RequestBody Map<String, Object>data) {
        Integer rm_number = (Integer) data.get("rm_number");
        Integer guest_id  = (Integer) data.get("guest_id");
        Integer ck_in = (Integer)data.get("receptionist");
        String dateStr = (String)data.get("date");
        Date in_date = DateFormatter.stringToDate(dateStr);
        Date in_time = new Date();
        checkedInOutRecordHandler.addCheckInRecord(in_date, in_time, ck_in, guest_id, rm_number);
    }

    @PostMapping("/check-out")
    public void addCheckOut(@RequestBody Map<String, Object>data) {
        Integer ck_id = (Integer)data.get("ck_id");
        Integer ck_out = (Integer)data.get("receptionist");
        String dateStr = (String)data.get("date");
        Date out_date = DateFormatter.stringToDate(dateStr);
        Date out_time = new Date();
        checkedInOutRecordHandler.addCheckOutRecord(ck_id, out_date, out_time, ck_out);
    }

    @PostMapping("/del-check-out")
    public void delCheckOut(@RequestBody Map<String, Object>data) {
        Integer ck_id = (Integer)data.get("ck_id");
        checkedInOutRecordHandler.deleteCheckOut(ck_id);
    }

    @DeleteMapping("/del-check-in-and-out/{ck_id}")
    public void delCheckInAndOut(@PathVariable Integer ck_id) {
        checkedInOutRecordHandler.deleteRecord(ck_id);
    }

}
