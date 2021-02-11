package com.cpsc304.HotelManagement.Controller;

import com.cpsc304.HotelManagement.DBHandler.HouseKeepRecordHandler;
import com.cpsc304.HotelManagement.Model.HouseKeep_Record;
import com.cpsc304.HotelManagement.Utils.DateFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hk_record")
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class HouseKeepRecordController {
    @Autowired
    HouseKeepRecordHandler HouseKeepRecordHandler;

    final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    @GetMapping(value = "/list")
    public List<Map<String,Object>> getHKRecord() {
        return HouseKeepRecordHandler.getAllHouseKeepRecord();
    }

    @PostMapping(value = "/add_hkrecord")
    public void addCharges(@RequestBody Map<String,Object> jsonStr) {
        //System.out.println(jsonStr);
        Integer kp_id = (Integer)(jsonStr.get("kp_id"));
        String dates = (String) (jsonStr.get("date"));
        String times = (String) (jsonStr.get("time"));
        Integer sid = (Integer)(jsonStr.get("sid"));
        Integer rm_number = (Integer)(jsonStr.get("rm_number"));
        Date d;
        Date t;

        if(kp_id != null && dates != null && times != null && sid != null && rm_number != null) {
            d = DateFormatter.stringToDate(dates);
            t = DateFormatter.stringToTime(times);

            HouseKeep_Record hk_record = new HouseKeep_Record(kp_id, d, t, sid, rm_number);
            HouseKeepRecordHandler.insertHouseKeepRecord(hk_record);
        }
    }
}
