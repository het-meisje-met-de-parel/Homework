SELECT *
FROM `carrier`
WHERE `carrier_type` = (
    SELECT `id`
    FROM `carrier_type`
    WHERE `name` = 'SHIP'
);