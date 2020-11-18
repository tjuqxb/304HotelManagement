package com.cpsc304.HotelManagement.Controller;

import com.cpsc304.HotelManagement.DBHandler.HouseKeepRecordHandler;
import com.cpsc304.HotelManagement.RequestModel.DoubleDates;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequestMapping("/hk_record")
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
        Map<String, Object> dates = (Map<String, Object>) (jsonStr.get("date"));
        Map<String, Object> times = (Map<String, Object>) (jsonStr.get("time"));
        Integer sid = (Integer)(jsonStr.get("sid"));
        Integer rm_number = (Integer)(jsonStr.get("rm_number"));

        if(kp_id != null && dates != null && times != null && sid != null && rm_number != null) {
            DoubleDates dd = mapper.convertValue(dates, DoubleDates.class);
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
            Date inDate = new Date();
            Date outDate = new Date();

            try {
                inDate = sdf.parse(dd.getInDate());
                outDate = sdf.parse(dd.getOutDate());
            } catch (Exception e) {

            }

            Date m_date = new Date();
            Date m_time = new Date();

            Long lengthOfDays = (outDate.getTime()-inDate.getTime())/(24*60*60*1000) + 1;

            Date cal = inDate;

            HotelStaff hs = new HotelStaff(sid,phone,name);
            HotelStaffHandler.insertHotelStaff(hs);
        }
    }
}
