SELECT DISTINCT (`expiration_date`)
FROM `cargo`
WHERE `expiration_date` > '2020-02-05'
ORDER BY `expiration_date`;