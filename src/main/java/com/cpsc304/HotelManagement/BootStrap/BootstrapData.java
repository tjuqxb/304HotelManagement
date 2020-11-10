package com.cpsc304.HotelManagement.BootStrap;

import com.cpsc304.HotelManagement.DBHandler.GuestHandler;
import com.cpsc304.HotelManagement.DBHandler.ReservationGuestHandler;
import com.cpsc304.HotelManagement.Model.ReservationGuest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Component

public class BootstrapData implements CommandLineRunner{
    @Autowired
    private JdbcTemplate jt;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private ReservationGuestHandler rh;
    @Autowired
    private GuestHandler gh;

    public void createDefaultDB(DataSource dataSource) {
        Resource resource = new ClassPathResource("schema-postgresql.sql");
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator(resource);
        databasePopulator.execute(dataSource);
    }

    @Override
    public void run(String... args) throws Exception {
        createDefaultDB(dataSource);
        ReservationGuest rg = new ReservationGuest("1","2",3L,"4","5","6","7");
        gh.insertGuest(rg);
        gh.insertGuest(rg);
        for (int i = 0; i < 90; i++) {
            Date date= new Date(); //get time
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            calendar.add(calendar.DATE,i); //add i days
            date=calendar.getTime();
            String strDateFormat = "yyyy-MM-dd";
            SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
            //System.out.println(sdf.format(date));
            String sql = "INSERT INTO rm_record VALUES(?,?,?,?)";
            jt.update(sql, 101,80, date,null);
            jt.update(sql,102,100,date, null);
            jt.update(sql,103,120,date, null);
            jt.update(sql,104,140,date, null);
            jt.update(sql,105,160,date, null);
        }


       // System.out.println(gh.getAllGuests());
    }
}
