-- Cargo part
INSERT INTO `cargo_type` (
    `name`
) VALUES 
    ('FOOD'), 
    ('CLOTHERS');
    
INSERT INTO `cargo` (
    `name`, `weight`, `cargo_type`, 
    `size`, `material`, 
    `expiration_date`, `store_temperature`
) VALUES 
    ('APPLE', '100', '1', NULL, NULL, '2025-12-20', '-50'),
    ('JEANS', '200', '2', 'XXL', 'COTTON', NULL, NULL),
    ('ORANGE', '300', '1', NULL, NULL, '2020-11-11', '-20');
    
-- Carrier part
INSERT INTO `carrier_type` (
    `name`
) VALUES 
    ('SHIP'), 
    ('PLANE'), 
    ('CAR'), 
    ('TRAIN');
        
INSERT INTO `carrier` (
    `name`, `address`, `carrier_type`
) VALUES 
    ('YOUR_DELIVERY', 'Spb, Nevskiy 800', '1'),
    ('FASTEST EVER', 'Sp, Prosveheniya 900', '2');
        
-- Transportation part
INSERT INTO `transportation` (
    `cargo`, `carrier`, `description`, `bill_to`, `begin_date`
) VALUES 
    ('1', '1', 'Vip order', 'Ivanov', '2010-11-11'),
    ('2', '1', 'Fragile', 'Petrov', '2011-11-11'),
    ('3', '2', 'Fragile', 'Sidorov', '2017-01-01');
