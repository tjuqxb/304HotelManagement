package com.cpsc304.HotelManagement.Controller;

import com.cpsc304.HotelManagement.DBHandler.*;
import com.cpsc304.HotelManagement.Model.ReservationRequest;
import com.cpsc304.HotelManagement.RequestModel.DoubleDates;
import com.cpsc304.HotelManagement.Model.InHouseGuest;
import com.cpsc304.HotelManagement.Model.ReservationGuest;
import com.cpsc304.HotelManagement.Utils.DateFormatter;
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
    ReservationGuestHandler reservationGuestHandler;
    @Autowired
    GuestHandler guestHandler;
    @Autowired
    InHouseGuestHandler inHouseGuestHandler;
    @Autowired
    ReservationRequestHandler reservationRequestHandler;
    @Autowired
    ReserveWithHandler reserveWithHandler;
    @Autowired
    RoomRecordHandler roomRecordHandler;

    final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    @GetMapping(value = "/list")
    public List<Map<String,Object>> getGuests() {
        return guestHandler.getAllGuests();
    }

    @PostMapping(value = "/query_rooms")
    public List<Map<String,Object>> queryRoom(@RequestBody Map<String,Object> jsonStr) {
        List<Map<String,Object>> ret = new ArrayList<>();
        Map<String, Object> rgm = (Map<String, Object>) (jsonStr.get("reservationGuest"));
        List<Map<String, Object>> guests = (List<Map<String, Object>>) (jsonStr.get("inHouseGuests"));
        Map<String, Object> dates = (Map<String, Object>) (jsonStr.get("date"));
        if(rgm != null && dates != null) {
            DoubleDates dd = mapper.convertValue(dates, DoubleDates.class);
            ReservationGuest rg1 = mapper.convertValue(rgm, ReservationGuest.class);
            String inDate = dd.getInDate();
            String outDate = dd.getOutDate();
            Integer numberOfGuests = guests.size() + 1;
            ret =  roomRecordHandler.findAvailableRooms(numberOfGuests, inDate, outDate);
        }
        return ret;
    }

    @PostMapping(value = "/query_cheap_rooms")
    public List<Map<String,Object>> queryCheapRoom(@RequestBody Map<String,Object> jsonStr) {
        List<Map<String,Object>> ret = new ArrayList<>();
        Map<String, Object> rgm = (Map<String, Object>) (jsonStr.get("reservationGuest"));
        List<Map<String, Object>> guests = (List<Map<String, Object>>) (jsonStr.get("inHouseGuests"));
        Map<String, Object> dates = (Map<String, Object>) (jsonStr.get("date"));
        if(rgm != null && dates != null) {
            DoubleDates dd = mapper.convertValue(dates, DoubleDates.class);
            ReservationGuest rg1 = mapper.convertValue(rgm, ReservationGuest.class);
            String inDate = dd.getInDate();
            String outDate = dd.getOutDate();
            Integer numberOfGuests = guests.size() + 1;
            ret =  roomRecordHandler.findCheapAvailableRooms(numberOfGuests, inDate, outDate);
        }
        return ret;
    }

    @PostMapping(value = "/reserve_room")
    public List<Map<String,Object>> reserveRoom(@RequestBody Map<String,Object> jsonStr) {
        List<Map<String,Object>> ret = new ArrayList<>();
        //System.out.println(jsonStr);
        Map<String, Object> rgm = (Map<String, Object>) (jsonStr.get("reservationGuest"));
        List<Map<String, Object>> guests = (List<Map<String, Object>>) (jsonStr.get("inHouseGuests"));
        Map<String, Object> dates = (Map<String, Object>) (jsonStr.get("date"));
        Integer rm_number = (Integer)(jsonStr.get("rm_number"));
        //System.out.println(rm_number);
        if(rgm != null && dates != null && rm_number != null) {
            DoubleDates dd = mapper.convertValue(dates, DoubleDates.class);
            ReservationGuest rg1 = mapper.convertValue(rgm, ReservationGuest.class);
            Integer req_code = 1;
            Integer req_status = 0;
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
            Integer rgId = reservationGuestHandler.insertReservationGuest(rg1);
            Long lengthOfDays = (outDate.getTime()-inDate.getTime())/(24*60*60*1000) + 1;
            //System.out.println("days"+lengthOfDays);
            ArrayList<ReservationRequest> rra = new ArrayList<>();
            Date cal = inDate;
            ArrayList<Integer> rids = new ArrayList<>();
            try {
                for (int i = 0; i < lengthOfDays; i++) {
                    ReservationRequest rr = new ReservationRequest(rgId, m_date, m_time, req_code, req_status, cal, rm_number);
                    rids.add(reservationRequestHandler.insertReservationRequest(rr));
                    rra.add(rr);
                    cal = DateFormatter.addOneDay(cal);
                }
            } catch (RuntimeException e) {
                HashMap<String, Object> error = new HashMap<>();
                error.put("Messages","One photo identity proof with credit card can only book one room for one date.");
                ret.add(error);
                return ret;
            }

            Integer numberOfGuests = guests.size() + 1;
            ArrayList<Integer> gids = new ArrayList<>();
            for (int i = 0; i < guests.size(); i++) {
                InHouseGuest ig = mapper.convertValue(guests.get(i), InHouseGuest.class);
                gids.add(inHouseGuestHandler.insertInHouseGuest(ig));
            }
            reserveWithHandler.insertReserveWith(rids, gids);
            roomRecordHandler.reserveRoomRecords(rra,rids);
            return reservationRequestHandler.findSuccessRequests(rgId);
        }
        return ret;
    }

    @DeleteMapping(value = "/reservations/{rid}")
    public void cancelReservation(@PathVariable Integer rid) {
        if (rid != null) {
            reservationRequestHandler.cancelReservation(rid);
        }
    }

    @GetMapping(value = "/reservations/{photoID}/{creditCard}")
    public List<Map<String,Object>> getReservations(@PathVariable String photoID, @PathVariable String creditCard) {
        return reservationRequestHandler.findSuccessRequests(photoID, creditCard);
    }

    @GetMapping(value = "/reservations_greater_count/{n}")
    public List<Map<String, Object>> getGuestWithMoreReservations(@PathVariable Integer n) {
        return reservationRequestHandler.getGuestsWithMoreReservations(n);
    }

}
