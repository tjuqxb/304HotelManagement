package com.cpsc304.HotelManagement.Controller;

import com.cpsc304.HotelManagement.DBHandler.GuestHandler;
import com.cpsc304.HotelManagement.DBHandler.ReservationGuestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping(value = "/list")
    public List<Map<String,Object>> getGuests() {
        return gh.getAllGuests();
    }
}
