DROP TABLE IF EXISTS guest CASCADE;
DROP TABLE IF EXISTS reservation_guest CASCADE;
DROP TABLE IF EXISTS in_house_guest CASCADE;
DROP TABLE IF EXISTS room CASCADE;
DROP TABLE IF EXISTS room_status CASCADE;
DROP TABLE IF EXISTS reservation_req CASCADE;
DROP TABLE IF EXISTS rm_record CASCADE;
DROP TABLE IF EXISTS reserve_with CASCADE;
DROP TABLE IF EXISTS hotel_staff CASCADE;
DROP TABLE IF EXISTS bill CASCADE;
DROP TABLE IF EXISTS services CASCADE;
DROP TABLE IF EXISTS charges CASCADE;

CREATE TABLE guest (
    guest_id INT NOT NULL,
    name VARCHAR  NOT NULL,
    phone VARCHAR,
    PRIMARY KEY (guest_id)
);

INSERT INTO guest VALUES
(00001,'Lucy','696-535-6164'),
(00002,'James','345-100-7932'),
(00003,'Bob','923-344-1213'),
(00004,'Amanda','297-142-6267'),
(00005,'Tom','418-298-5989'),
(00006,'Edison','987-442-1632'),
(00007,'Charles','445-575-1484'),
(00008,'Richard','184-589-8844'),
(00009,'Julia','175-183-3661'),
(00010,'David','455-650-9440');

CREATE TABLE reservation_guest(
    guest_id INT NOT NULL,
    credit_card CHAR(30)  NOT NULL,
    photo_identity CHAR(30),
    email VARCHAR,
    membership CHAR(10),
    PRIMARY KEY (guest_id),
    UNIQUE (photo_identity, credit_card),
    FOREIGN KEY (guest_id) REFERENCES Guest(guest_id)
);

INSERT INTO reservation_guest VALUES
(00001,'36702422025519','392-39-7773','zdegiorgio9@edublogs.org',NULL),
(00002,'3577037006739620','778-21-0746','icaveya@youtube.com','NORMAL'),
(00003,'5602221789293420000','480-07-4246', 'cmcinallyb@sohu.com','VIP'),
(00004,'3563861329178490','556-21-8622','lcrossmanc@guardian.co.uk', NULL),
(00005,'201940347143160','392-39-7773','fcle@na.com','NORMAL');

CREATE TABLE in_house_guest(
    guest_id INT NOT NULL,
    PRIMARY KEY (guest_id),
    FOREIGN KEY (guest_id) REFERENCES guest(guest_id)
);

INSERT INTO in_house_guest VALUES
(00006),(00007),(00008),(00009),(00010);

CREATE TABLE room_status(
     status_id INT NOT NULL,
     available BOOLEAN NOT NULL,
     out_of_service BOOLEAN NOT NULL,
     clean BOOLEAN NOT NULL,
     occupied BOOLEAN NOT NULL,
     PRIMARY KEY (status_id),
     UNIQUE(out_of_service, occupied, clean)
);

INSERT INTO room_status VALUES
(1, true ,false,true,false),
(2, false,false,false,false),
(3, false,false,false,true),
(4, false,false,true,true ),
(5, false,true,false,false ),
(6, false,true,false,true ),
(7, false,true,true ,false ),
(8, false,true,true ,true );

CREATE TABLE room(
    rm_number INT NOT NULL,
    type VARCHAR NOT NULL,
    status_id INT,
    PRIMARY KEY (rm_number),
    FOREIGN KEY (status_id) REFERENCES room_status(status_id)
);
INSERT INTO room VALUES (101,'1BED', 1),(102, '2BED', 1),(103,'1BED',1),(104,'2BED',1),(105,'1BED',1);

CREATE TABLE reservation_req(
    rid INT,
    guest_id INT NOT NULL,
    m_date DATE NOT NULL,
    m_time TIME NOT NULL,
    req_code INT NOT NULL,
    req_status INT NOT NULL,
    date DATE NOT NULL,
    rm_number INT NOT NULL,
    UNIQUE (guest_id, date),
    PRIMARY KEY (rid),
    FOREIGN KEY (guest_id) REFERENCES reservation_guest(guest_id)
);

INSERT INTO reservation_req VALUES
(00001,00001,'2020-10-22','09:00',1,1,'2020-10-23', 101),
(00002,00002,'2020-10-23','10:00',1,1,'2020-10-24', 102),
(00003,00003,'2020-10-24','11:00',1,0,'2020-10-25',103),
(00004,00004,'2020-10-25','12:00',1,1,'2020-10-26',104),
(00005,00005,'2020-10-26','13:00',1,0,'2020-10-27',105);

CREATE TABLE rm_record(
    rm_number INT NOT NULL,
    price INT NOT NULL,
    date DATE NOT NULL,
    last_req INT,
    PRIMARY KEY (rm_number, date),
    FOREIGN KEY (rm_number) REFERENCES room(rm_number),
    FOREIGN KEY (last_req) REFERENCES reservation_req(rid) ON DELETE CASCADE
);

INSERT INTO rm_record VALUES
(101,100,'2020-10-22',00001),
(102,200,'2020-10-23',00002),
(103,100,'2020-10-24',00003),
(104,200,'2020-10-25',00004),
(105,100,'2020-10-26',00005);


CREATE TABLE reserve_with(
    rid INT,
    guest_id INT,
    PRIMARY KEY (rid, guest_id),
    FOREIGN KEY (rid) REFERENCES reservation_req(rid) ON DELETE CASCADE,
    FOREIGN KEY (guest_id) REFERENCES in_house_guest(guest_id) ON DELETE CASCADE
);

INSERT INTO reserve_with VALUES
(00001,00006),
(00002,00007),
(00003,00008),
(00004,00009),
(00005,00010);

CREATE TABLE hotel_staff(
    sid INT,
    phone CHAR(50),
    name CHAR(20) NOT NULL,
    PRIMARY KEY (sid)
);

INSERT INTO hotel_staff VALUES
(00001,'946-646-9159', 'Darci'),
(00002,'310-175-9817', 'Worden'),
(00003,'642-857-7383', 'Sander'),
(00004,'185-599-6769','Luce'),
(00005,'139-873-6306','Monica');

CREATE TABLE bill(
    bill_num INT NOT NULL,
    bill_date DATE NOT NULL,
    rm_number INT NOT NULL,
    date DATE NOT NULL,
    PRIMARY KEY (bill_num),
    UNIQUE (rm_number, date),
    FOREIGN KEY (rm_number,date) REFERENCES rm_record(rm_number,date)
);


INSERT INTO bill VALUES
(00001,'2020-10-25',101, '2020-10-22'),
(00002,'2020-10-24',102, '2020-10-23'),
(00003,'2020-10-27',103, '2020-10-24'),
(00004,'2020-10-28',104,'2020-10-25'),
(00005,'2020-10-30',105,'2020-10-26');

CREATE TABLE services(
    service_name CHAR(20),
    description CHAR(60),
    fee INT,
    PRIMARY KEY (service_name)
);

INSERT INTO services VALUES
('full_clean','Linen replacement,vacuum,mop,dust,kitchen wipe,dishes', 90),
('partial_clean','Linen replacement,vacuum and mop', 45),
('lost_key','Guest lost their key', 25),
('lost_towel','Towel is missing from suite',10),
('water_bottle','Guest bought a water bottle',5);

CREATE TABLE charges(
    bill_num INT NOT NULL,
    cid INT,
    service_name CHAR(20),
    PRIMARY KEY (cid),
    FOREIGN KEY (bill_num) REFERENCES bill(bill_num),
    FOREIGN KEY (service_name) REFERENCES services(service_name)
);

INSERT INTO charges VALUES
(00001,00001, 'partial_clean'),
(00002,00002, 'lost_key'),
(00003,00003, 'lost_towel'),
(00004,00004,'lost_towel'),
(00005,00005,'water_bottle');






/*WITH selected_records AS (
SELECT * FROM
rm_record rr
WHERE rr.date >= date'2020-11-17' AND rr.date <= date'2020-11-20' AND rr.last_req is NULL
)SELECT sr.rm_number AS room_number, room.type, CAST (AVG (sr.price) AS INTEGER ) AS average_price FROM
selected_records sr, room
WHERE sr.rm_number = room.rm_number AND room.status_id = 1
GROUP BY sr.rm_number, room.type
HAVING  COUNT (*) = date'2020-11-20' - date'2020-11-17'+ 1;*/




















