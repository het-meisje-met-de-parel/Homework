-- Database part
CREATE DATABASE `epam_transportations`;

-- Cargo part
CREATE TABLE `cargo_type` (
    `id` bigint NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(128) NOT NULL
) ENGINE='InnoDB';

CREATE TABLE `cargo` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(128) NOT NULL,
    `weight` int NOT NULL,
    `cargo_type` bigint(20) NOT NULL,
    `size` varchar(16) NULL,
    `material` text NULL,
    `expiration_date` datetime NULL,
    `store_temperature` int NULL,
    FOREIGN KEY (`cargo_type`) REFERENCES `cargo_type` (`id`)
) ENGINE='InnoDB';

-- Carrier part
CREATE TABLE `carrier_type` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(128) NOT NULL
) ENGINE='InnoDB';

CREATE TABLE `carrier` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(128) NOT NULL,
    `address` varchar(256) NOT NULL,
    `carrier_type` int(11) NOT NULL,
    FOREIGN KEY (`carrier_type`) REFERENCES `carrier_type` (`id`)
) ENGINE='InnoDB';

-- Transportation part
CREATE TABLE `transportation` (
    `id` int NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `cargo` int(11) NOT NULL,
    `carrier` int(11) NOT NULL,
    `description` text NULL,
    `bill_to` varchar(256) NOT NULL,
    `begin_date` datetime NOT NULL,
    FOREIGN KEY (`cargo`) REFERENCES `cargo` (`id`),
    FOREIGN KEY (`carrier`) REFERENCES `carrier` (`id`)
) ENGINE='InnoDB';
