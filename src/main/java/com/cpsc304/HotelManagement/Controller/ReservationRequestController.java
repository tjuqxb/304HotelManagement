package com.cpsc304.HotelManagement.Controller;

import com.cpsc304.HotelManagement.DBHandler.ReservationRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservations")
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class ReservationRequestController {
    @Autowired
    ReservationRequestHandler reservationRequestHandler;

    @PostMapping("/projection")
    public List<Map<String, Object>> getProjection(@RequestBody List<String> arr) {
        return reservationRequestHandler.getProjectionQuery(arr);
    }

}
