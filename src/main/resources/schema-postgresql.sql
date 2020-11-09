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

CREATE TABLE in_house_guest(
    guest_id INT NOT NULL,
    PRIMARY KEY (guest_id),
    FOREIGN KEY (guest_id) REFERENCES guest(guest_id)
);

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

CREATE TABLE rm_record(
    rm_number INT NOT NULL,
    price INT NOT NULL,
    date DATE NOT NULL,
    last_req INT,
    PRIMARY KEY (rm_number, date),
    FOREIGN KEY (rm_number) REFERENCES room(rm_number),
    FOREIGN KEY (last_req) REFERENCES reservation_req(rid) ON DELETE CASCADE
);


CREATE TABLE reserve_with(
    rid INT,
    guest_id INT,
    PRIMARY KEY (rid, guest_id),
    FOREIGN KEY (rid) REFERENCES reservation_req(rid) ON DELETE CASCADE,
    FOREIGN KEY (guest_id) REFERENCES in_house_guest(guest_id) ON DELETE CASCADE
);

CREATE TABLE hotel_staff(
    sid INT,
    phone CHAR(50),
    name CHAR(20) NOT NULL,
    PRIMARY KEY (sid)
);

CREATE TABLE bill(
    bill_num INT NOT NULL,
    bill_date DATE NOT NULL,
    rm_number INT NOT NULL,
    date DATE NOT NULL,
    PRIMARY KEY (bill_num),
    UNIQUE (rm_number, date),
    FOREIGN KEY (rm_number,date) REFERENCES rm_record(rm_number,date)
);

CREATE TABLE services(
    service_name CHAR(20),
    description CHAR(40),
    fee INT,
    PRIMARY KEY (service_name)
);

CREATE TABLE charges(
    bill_num INT NOT NULL,
    cid INT,
    service_name CHAR(20),
    PRIMARY KEY (cid),
    FOREIGN KEY (bill_num) REFERENCES bill(bill_num),
    FOREIGN KEY (service_name) REFERENCES services(service_name)
);







/*WITH selected_records AS (
SELECT * FROM
rm_record rr
WHERE rr.date >= date'2020-11-17' AND rr.date <= date'2020-11-20' AND rr.last_req is NULL
)SELECT sr.rm_number AS room_number, room.type, CAST (AVG (sr.price) AS INTEGER ) AS average_price FROM
selected_records sr, room
WHERE sr.rm_number = room.rm_number AND room.status_id = 1
GROUP BY sr.rm_number, room.type
HAVING  COUNT (*) = date'2020-11-20' - date'2020-11-17'+ 1;*/




















