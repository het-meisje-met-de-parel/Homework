UPDATE `cargo`
SET `size` = 'fake size'
WHERE `size` IS NULL
    AND `store_temperature` > -30;

UPDATE `cargo`
SET `size` = NULL
WHERE `size` = 'fake size'
    AND `store_temperature` > -30;