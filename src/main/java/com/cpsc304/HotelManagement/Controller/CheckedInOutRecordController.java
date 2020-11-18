package com.cpsc304.HotelManagement.Controller;


import com.cpsc304.HotelManagement.DBHandler.CheckedInOutRecordHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/checked-in-out-records")
@CrossOrigin(origins = {"*"}, maxAge = 3600L)
public class CheckedInOutRecordController {

    @Autowired
    CheckedInOutRecordHandler checkedInOutRecordHandler;

    @GetMapping("/{year}/{month}")
    public List<List<Map<String, Object>>> getRoomRecords(@PathVariable Double year, @PathVariable Double month) {
        return checkedInOutRecordHandler.getCheckedInOutRecords(year, month);
    }



}
