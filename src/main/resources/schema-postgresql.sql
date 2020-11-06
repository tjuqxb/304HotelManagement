DROP TABLE IF EXISTS guest CASCADE;
DROP TABLE IF EXISTS reservation_guest CASCADE;
DROP TABLE IF EXISTS in_house_guest CASCADE;

CREATE TABLE guest (
    guest_id INT NOT NULL,
    name CHAR(50)  NOT NULL,
    phone CHAR(20),
    PRIMARY KEY (guest_id)
);

CREATE TABLE reservation_guest(
    guest_id INT NOT NULL,
    credit_card CHAR(30)  NOT NULL,
    photo_identity CHAR(30),
    email CHAR(40),
    membership CHAR(20),
    PRIMARY KEY (guest_id),
    UNIQUE (photo_identity, credit_card),
    FOREIGN KEY (guest_id) REFERENCES Guest(guest_id)
);

CREATE TABLE in_house_guest(
    guest_id INT NOT NULL,
    PRIMARY KEY (guest_id),
    FOREIGN KEY (guest_id) REFERENCES guest(guest_id)
);


