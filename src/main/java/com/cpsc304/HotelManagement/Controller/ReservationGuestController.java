package com.cpsc304.HotelManagement.Controller;

import com.cpsc304.HotelManagement.DBHandler.ReservationGuestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReservationGuestController {
    @Autowired
    ReservationGuestHandler rh;


}
