package com.cpsc304.HotelManagement.Controller;

import com.cpsc304.HotelManagement.DBHandler.RoomHandler;
import com.cpsc304.HotelManagement.DBHandler.RoomRecordHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class RoomRecordsController {
    @Autowired
    RoomHandler roomHandler;
    @Autowired
    RoomRecordHandler roomRecordHandler;

    @GetMapping (value = "/all")
    public List<Map<String, Object>> getAllRooms() {
        return  roomHandler.getAllRooms();
    }

    @GetMapping (value ="/room-records/{year}/{month}")
    public List<List<Map<String, Object>>> getRoomRecords(@PathVariable Double year, @PathVariable Double month) {
        return roomRecordHandler.getRoomReservations(year, month);
    }

    @GetMapping(value = "/room-records-in-checkout/{ck_id}")
    public List<Map<String, Object>> getRoomRecordsInCheckOut(@PathVariable Integer ck_id) {
        return roomRecordHandler.getRoomRecordsByCheckId(ck_id);
    }

}
