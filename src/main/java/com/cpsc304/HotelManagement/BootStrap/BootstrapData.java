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
        ReservationGuest rg = new ReservationGuest("1","2",3,"4","5","6","7");
        gh.insertGuest(rg);
        gh.insertGuest(rg);
        String sql = "do $$ " +
                "    declare " +
                "        v_idx DATE := current_date - 1;" +
                "    begin " +
                "        while v_idx != current_date + 182 loop " +
                "                v_idx := v_idx + 1; " +
                "                insert into rm_record " +
                "                values (101, 100 + (SELECT random()*(25-10)+10), v_idx, null), " +
                "                       (102, 120 + (SELECT random()*(25-10)+10), v_idx, null), " +
                "                       (103, 135 + (SELECT random()*(25-10)+10), v_idx, null), " +
                "                       (104, 115 + (SELECT random()*(25-10)+10), v_idx, null), " +
                "                       (105, 105 + (SELECT random()*(25-10)+10), v_idx, null); " +
                "            end loop; " +
                "    end; $$;";
        jt.execute(sql);

    }

}
