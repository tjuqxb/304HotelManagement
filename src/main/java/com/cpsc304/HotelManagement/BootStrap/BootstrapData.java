package com.cpsc304.HotelManagement.BootStrap;

import com.cpsc304.HotelManagement.DBHandler.GuestHandler;
import com.cpsc304.HotelManagement.DBHandler.ReservationGuestHandler;
import com.cpsc304.HotelManagement.Model.ReservationGuest;
import com.cpsc304.HotelManagement.Utils.DateFormatter;
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
    }

}
