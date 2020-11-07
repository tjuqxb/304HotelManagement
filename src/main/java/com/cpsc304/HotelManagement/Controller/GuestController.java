package com.cpsc304.HotelManagement.Controller;

import com.cpsc304.HotelManagement.DBHandler.GuestHandler;
import com.cpsc304.HotelManagement.DBHandler.ReservationGuestHandler;
import com.cpsc304.HotelManagement.Model.ReservationGuest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/guest")
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class GuestController {
    @Autowired
    ReservationGuestHandler rh;
    @Autowired
    GuestHandler gh;
    final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    @GetMapping(value = "/list")
    public List<Map<String,Object>> getGuests() {
        return gh.getAllGuests();
    }

    @PostMapping(value = "/reservation_guest")
    public List<Map<String,Object>> createReservationGuest(@RequestBody Map<String,Object> jsonStr) {
        Map<String, Object> rgm = (Map<String, Object>) jsonStr.get("reservationGuest");
        ReservationGuest rg1 = mapper.convertValue(rgm, ReservationGuest.class);
        rh.insertReservationGuest(rg1);
        return gh.getAllGuests();
    }
}
