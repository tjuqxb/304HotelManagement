WITH selected_records AS (
    SELECT * FROM
        rm_record rr
    WHERE rr.date >= date'2020-11-17' AND rr.date <= date'2020-11-20' AND rr.last_req is NULL
)SELECT sr.rm_number AS room_number, room.type, CAST (AVG (sr.price) AS INTEGER ) AS average_price FROM
                                                                                                       selected_records sr, room
WHERE sr.rm_number = room.rm_number AND room.status_id = 1
GROUP BY sr.rm_number, room.type
HAVING  COUNT (*) = date'2020-11-20' - date'2020-11-17'+ 1;