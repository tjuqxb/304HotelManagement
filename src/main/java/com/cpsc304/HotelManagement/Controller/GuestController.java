package com.cpsc304.HotelManagement.Controller;

import com.cpsc304.HotelManagement.DBHandler.GuestHandler;
import com.cpsc304.HotelManagement.DBHandler.InHouseGuestHandler;
import com.cpsc304.HotelManagement.DBHandler.ReservationGuestHandler;
import com.cpsc304.HotelManagement.RequestFormat.DoubleDates;
import com.cpsc304.HotelManagement.Model.InHouseGuest;
import com.cpsc304.HotelManagement.Model.ReservationGuest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/guest")
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class GuestController {
    @Autowired
    ReservationGuestHandler rh;
    @Autowired
    GuestHandler gh;
    @Autowired
    InHouseGuestHandler ih;
    final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    @GetMapping(value = "/list")
    public List<Map<String,Object>> getGuests() {
        return gh.getAllGuests();
    }

    @PostMapping(value = "/reservation_guest")
    public List<Map<String,Object>> createReservationGuest(@RequestBody Map<String,Object> jsonStr) {
        System.out.println(jsonStr);
        Map<String, Object> rgm = (Map<String, Object>) (jsonStr.get("reservationGuest"));
        List<Map<String, Object>> guests = (List<Map<String, Object>>) (jsonStr.get("inHouseGuests"));
        Map<String, Object> dates = (Map<String, Object>) (jsonStr.get("date"));
        DoubleDates dd = mapper.convertValue(dates, DoubleDates.class);
        if(rgm != null) {
            ReservationGuest rg1 = mapper.convertValue(rgm, ReservationGuest.class);
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
            Date s1 = new Date();
            try {
                s1 = sdf.parse(dd.getInDate());
            } catch (Exception e) {

            }
            System.out.println(s1);
            System.out.println(rg1.getCredit_card());
            Integer rid = rh.insertReservationGuest(rg1);
            ArrayList<Integer> gids = new ArrayList<>();
            for (int i = 0; i < guests.size(); i++) {
                InHouseGuest ig = mapper.convertValue(guests.get(i), InHouseGuest.class);
                gids.add(ih.insertInHouseGuest(ig));
            }

        }
        return gh.getAllGuests();
    }
}
