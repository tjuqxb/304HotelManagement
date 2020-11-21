package com.cpsc304.HotelManagement.Controller;

import com.cpsc304.HotelManagement.DBHandler.HotelStaffHandler;
import com.cpsc304.HotelManagement.Model.HotelStaff;
import com.cpsc304.HotelManagement.Utils.DateFormatter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel_staff")
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class HotelStaffController {
    @Autowired
    HotelStaffHandler hotelStaffHandler;

    final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    @GetMapping(value = "/list")
    public List<Map<String,Object>> getHotelStaff() {
        return hotelStaffHandler.getAllHotelStaff();
    }

    @GetMapping(value = "/receptionists/list")
    public List<Map<String,Object>> getRecepionists() {
        return hotelStaffHandler.getAllHotelStaff();
    }

    @PostMapping(value = "/add_hotelstaff")
    public void addCharges(@RequestBody Map<String,Object> jsonStr) {
        //System.out.println(jsonStr);
        Integer sid = (Integer)(jsonStr.get("sid"));
        String phone = (String)(jsonStr.get("phone"));
        String name = (String)(jsonStr.get("name"));
        if(sid != null && name != null) {
            HotelStaff hs = new HotelStaff(sid,phone,name);
            hotelStaffHandler.insertHotelStaff(hs);
        }
    }

    @PostMapping(value = "/find_eligible_staff")
    public List<Map<String, Object>> findEligible(@RequestBody Map<String,Object> jsonStr) {
        String dateStr1 = (String) jsonStr.get("date1");
        String dateStr2 = (String)jsonStr.get("date2");
        Date date1 = DateFormatter.stringToDate(dateStr1);
        Date date2 = DateFormatter.stringToDate(dateStr2);
        return hotelStaffHandler.getEligibleHotelStaff(date1, date2);
    }
}
