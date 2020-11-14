package com.cpsc304.HotelManagement.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/charges")
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class ChargesController {
    @Autowired
    com.cpsc304.HotelManagement.DBHandler.ChargesHandler ChargesHandler;

    final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    @GetMapping(value = "/list")
    public List<Map<String,Object>> getGuests() {
        return ChargesHandler.getAllCharges();
    }
}
