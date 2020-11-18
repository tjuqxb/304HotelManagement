package com.cpsc304.HotelManagement.Controller;

import com.cpsc304.HotelManagement.DBHandler.HotelStaffHandler;
import com.cpsc304.HotelManagement.Model.HotelStaff;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/hotel_staff")
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class HotelStaffController {
    @Autowired
    HotelStaffHandler HotelStaffHandler;

    final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    @GetMapping(value = "/list")
    public List<Map<String,Object>> getHotelStaff() {
        return HotelStaffHandler.getAllHotelStaff();
    }

    @GetMapping(value = "/receptionists/list")
    public List<Map<String,Object>> getRecepionists() {
        return HotelStaffHandler.getAllHotelStaff();
    }

    @PostMapping(value = "/add_hotelstaff")
    public void addCharges(@RequestBody Map<String,Object> jsonStr) {
        //System.out.println(jsonStr);
        Integer sid = (Integer)(jsonStr.get("sid"));
        String phone = (String)(jsonStr.get("phone"));
        String name = (String)(jsonStr.get("name"));
        if(sid != null && name != null) {
            HotelStaff hs = new HotelStaff(sid,phone,name);
            HotelStaffHandler.insertHotelStaff(hs);
        }
    }
}
