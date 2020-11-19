WITH Temp AS (WITH selected_records AS (
    SELECT * FROM
        rm_record rr
    WHERE rr.date >= date'2020-11-17' AND rr.date <= date'2020-11-20' AND rr.last_req is NULL
)SELECT sr.rm_number AS room_number, room.type, CAST (AVG (sr.price) AS INTEGER ) AS average_price FROM
selected_records sr, room
WHERE sr.rm_number = room.rm_number AND room.status_id = 1
GROUP BY sr.rm_number, room.type
HAVING  COUNT (*) = date'2020-11-20' - date'2020-11-17'+ 1)
SELECT *
FROM Temp
WHERE Temp.average_price = (SELECT MIN(average_price) FROM TEMP);


SELECT * FROM
rm_record rr
WHERE EXTRACT(ISOYEAR FROM rr.DATE) = 2020 AND EXTRACT(MONTH FROM rr.DATE) = 11 AND rr.rm_number = 101;
-- Cast to Object;
-- last_req ！= NULL -> Integer rid;

SELECT g.guest_id, g.name FROM
reservation_req req, guest g
WHERE req.rid = ? AND req.guest_id = g.guest_id;

SELECT g.guest_id, g.name FROM
reserve_with rw, guest g
WHERE rw.rid = ? AND g.guest_id = rw.guest_id

do $$
    declare
        v_idx DATE := current_date;
    begin
        while v_idx < current_date + 180
            loop
                v_idx = v_idx + 1;
                insert into rm_record
                values (101, 80, v_idx, null),
                       (102, 80, v_idx, null),
                       (103, 80, v_idx, null),
                       (104, 80, v_idx, null),
                       (105, 80, v_idx, null);
            end loop;
    end$$;

SELECT *
FROM checked_in_out_rec cr
WHERE cr.rm_number = 101 AND ((EXTRACT(ISOYEAR FROM cr.in_date) = 2020 AND EXTRACT(MONTH FROM cr.in_date) = 10) OR
(EXTRACT(ISOYEAR FROM cr.out_date) = 2020 AND EXTRACT(MONTH FROM cr.out_date) = 10));