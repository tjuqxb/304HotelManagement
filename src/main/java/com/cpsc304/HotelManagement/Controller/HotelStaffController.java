package com.cpsc304.HotelManagement.Controller;

import com.cpsc304.HotelManagement.DBHandler.HotelStaffHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
