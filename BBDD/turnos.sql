-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         10.11.5-MariaDB - mariadb.org binary distribution
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Volcando estructura de base de datos para reservas
CREATE DATABASE IF NOT EXISTS `reservas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `reservas`;

-- Volcando estructura para tabla reservas.flight
CREATE TABLE IF NOT EXISTS `flight` (
  `id` bigint(20) NOT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `flight_number` varchar(255) DEFAULT NULL,
  `flight_price` double NOT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `outward_date` date DEFAULT NULL,
  `seat_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.flight: ~12 rows (aproximadamente)
INSERT INTO `flight` (`id`, `destination`, `flight_number`, `flight_price`, `origin`, `outward_date`, `seat_type`) VALUES
	(1, 'Miami', 'BAMI-1235', 650, 'Barcelona', '2024-02-10', 'Economy'),
	(2, 'Madrid', 'MIMA-1420', 4320, 'Miami', '2024-02-10', 'Business'),
	(3, 'Madrid', 'MIMA-1420', 2573, 'Miami', '2024-02-10', 'Economy'),
	(4, 'Buenos Aires', 'BABU-5536', 732, 'Barcelona', '2024-02-10', 'Economy'),
	(5, 'Barcelona', 'IGBA-3369', 540, 'Iguazú', '2024-01-02', 'Business'),
	(6, 'Cartagena', 'BOCA-4213', 800, 'Bogotá', '2024-01-23', 'Economy'),
	(7, 'Medellín', 'CAME-0321', 780, 'Cartagena', '2024-01-23', 'Economy'),
	(8, 'Iguazú', 'BOIG-6567', 570, 'Bogotá', '2024-02-15', 'Business'),
	(9, 'Buenos Aires', 'BOBA-6567', 398, 'Bogotá', '2024-03-01', 'Economy'),
	(10, 'Madrid', 'BOMA-4442', 1100, 'Bogotá', '2024-02-10', 'Economy'),
	(11, 'Miami', 'MEMI-9986', 1164, 'Medellín', '2024-04-17', 'Business'),
	(12, 'Barcelona', 'BUBA-3369', 1253, 'Buenos Aires', '2024-02-12', 'Business');

-- Volcando estructura para tabla reservas.flight-generator
CREATE TABLE IF NOT EXISTS `flight-generator` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.flight-generator: ~1 rows (aproximadamente)
INSERT INTO `flight-generator` (`next_val`) VALUES
	(13);

-- Volcando estructura para tabla reservas.flight_reservation
CREATE TABLE IF NOT EXISTS `flight_reservation` (
  `id` bigint(20) NOT NULL,
  `peopleq` int(11) NOT NULL,
  `flight` bigint(20) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `destination` varchar(255) DEFAULT NULL,
  `flight_booker` varchar(255) DEFAULT NULL,
  `origin` varchar(255) DEFAULT NULL,
  `seat_type` varchar(255) DEFAULT NULL,
  `flight_code` varchar(255) DEFAULT NULL,
  `total_price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2q8oqnwapk86g7f0wecdoxdp` (`flight`),
  CONSTRAINT `FK2q8oqnwapk86g7f0wecdoxdp` FOREIGN KEY (`flight`) REFERENCES `flight` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.flight_reservation: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reservas.fr-generator
CREATE TABLE IF NOT EXISTS `fr-generator` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.fr-generator: ~1 rows (aproximadamente)
INSERT INTO `fr-generator` (`next_val`) VALUES
	(1);

-- Volcando estructura para tabla reservas.hotel
CREATE TABLE IF NOT EXISTS `hotel` (
  `id` bigint(20) NOT NULL,
  `hotel_code` varchar(255) DEFAULT NULL,
  `hotel_name` varchar(255) DEFAULT NULL,
  `hotel_place` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.hotel: ~12 rows (aproximadamente)
INSERT INTO `hotel` (`id`, `hotel_code`, `hotel_name`, `hotel_place`) VALUES
	(1, 'AR-0002', 'Atlantis Resort', 'Miami'),
	(2, 'AR-0003', 'Atlantis Resort 2', 'Miami'),
	(3, 'RC-0001', 'Ritz-Carlton', 'Buenos Aires'),
	(4, 'RC-0002', 'Ritz-Carlton 2', 'Medellín'),
	(5, 'GH-0001', 'Grand Hyatt', 'Madrid'),
	(6, 'GH-0002', 'Grand Hyatt 2', 'Buenos Aires'),
	(7, 'HL-0001', 'Hilton', 'Barcelona'),
	(8, 'HL-0002', 'Hilton 2', 'Barcelona'),
	(9, 'MT-0003', 'Marriott', 'Barcelona'),
	(10, 'SH-0004', 'Sheraton', 'Madrid'),
	(11, 'SH-0002', 'Sheraton 2', 'Iguazú'),
	(12, 'IR-0004', 'InterContinental', 'Cartagena');

-- Volcando estructura para tabla reservas.hotel-generator
CREATE TABLE IF NOT EXISTS `hotel-generator` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.hotel-generator: ~1 rows (aproximadamente)
INSERT INTO `hotel-generator` (`next_val`) VALUES
	(13);

-- Volcando estructura para tabla reservas.hotel_reservation
CREATE TABLE IF NOT EXISTS `hotel_reservation` (
  `id` bigint(20) NOT NULL,
  `nights` int(11) NOT NULL,
  `peopleq` int(11) NOT NULL,
  `room` bigint(20) DEFAULT NULL,
  `date_from` date DEFAULT NULL,
  `date_to` date DEFAULT NULL,
  `hotel_booker` varchar(255) DEFAULT NULL,
  `hotel_code` varchar(255) DEFAULT NULL,
  `place` varchar(255) DEFAULT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `total_price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKi7lcirgceheofmxvu0htx1l3m` (`room`),
  CONSTRAINT `FKi7lcirgceheofmxvu0htx1l3m` FOREIGN KEY (`room`) REFERENCES `room` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.hotel_reservation: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reservas.hr-generator
CREATE TABLE IF NOT EXISTS `hr-generator` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.hr-generator: ~1 rows (aproximadamente)
INSERT INTO `hr-generator` (`next_val`) VALUES
	(1);

-- Volcando estructura para tabla reservas.person
CREATE TABLE IF NOT EXISTS `person` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `surname` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.person: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reservas.person-generator
CREATE TABLE IF NOT EXISTS `person-generator` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.person-generator: ~1 rows (aproximadamente)
INSERT INTO `person-generator` (`next_val`) VALUES
	(1);

-- Volcando estructura para tabla reservas.reservation_flight_person
CREATE TABLE IF NOT EXISTS `reservation_flight_person` (
  `reservation_flightid` bigint(20) NOT NULL,
  `personid` bigint(20) NOT NULL,
  KEY `FKowrv03l8s9n4ksnwfix1r0y1o` (`personid`),
  KEY `FKremw6qweyh4dt6214xk0poqgb` (`reservation_flightid`),
  CONSTRAINT `FKowrv03l8s9n4ksnwfix1r0y1o` FOREIGN KEY (`personid`) REFERENCES `person` (`id`),
  CONSTRAINT `FKremw6qweyh4dt6214xk0poqgb` FOREIGN KEY (`reservation_flightid`) REFERENCES `flight_reservation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.reservation_flight_person: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reservas.reservation_room_person
CREATE TABLE IF NOT EXISTS `reservation_room_person` (
  `reservation_roomid` bigint(20) NOT NULL,
  `personid` bigint(20) NOT NULL,
  KEY `FK4fsk0qi4xao51spn0rv5djwnc` (`personid`),
  KEY `FKa8d71td4t45lr6hs1xg4hq8md` (`reservation_roomid`),
  CONSTRAINT `FK4fsk0qi4xao51spn0rv5djwnc` FOREIGN KEY (`personid`) REFERENCES `person` (`id`),
  CONSTRAINT `FKa8d71td4t45lr6hs1xg4hq8md` FOREIGN KEY (`reservation_roomid`) REFERENCES `hotel_reservation` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.reservation_room_person: ~0 rows (aproximadamente)

-- Volcando estructura para tabla reservas.room
CREATE TABLE IF NOT EXISTS `room` (
  `id` bigint(20) NOT NULL,
  `date_from` date DEFAULT NULL,
  `date_to` date DEFAULT NULL,
  `is_booked` bit(1) NOT NULL,
  `room_price` double NOT NULL,
  `room_type` varchar(255) DEFAULT NULL,
  `hotelid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7bt2oc7b3h1cqba9crblkx1c4` (`hotelid`),
  CONSTRAINT `FK7bt2oc7b3h1cqba9crblkx1c4` FOREIGN KEY (`hotelid`) REFERENCES `hotel` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.room: ~12 rows (aproximadamente)
INSERT INTO `room` (`id`, `date_from`, `date_to`, `is_booked`, `room_price`, `room_type`, `hotelid`) VALUES
	(1, '2024-02-10', '2024-03-20', b'0', 630, 'Doble', 1),
	(2, '2024-02-10', '2024-03-23', b'0', 820, 'Triple', 2),
	(3, '2024-02-10', '2024-03-19', b'0', 543, 'Single', 3),
	(4, '2024-02-12', '2024-04-17', b'0', 720, 'Doble', 4),
	(5, '2024-04-17', '2024-05-23', b'0', 579, 'Doble', 5),
	(6, '2024-01-02', '2024-02-19', b'0', 415, 'Single', 6),
	(7, '2024-01-23', '2024-11-23', b'0', 390, 'Single', 7),
	(8, '2024-01-23', '2024-10-15', b'0', 584, 'Doble', 8),
	(9, '2024-02-15', '2024-03-27', b'0', 702, 'Doble', 9),
	(10, '2024-03-01', '2024-04-17', b'0', 860, 'Múltiple', 10),
	(11, '2024-02-10', '2024-03-20', b'0', 640, 'Doble', 11),
	(12, '2024-04-17', '2024-06-12', b'0', 937, 'Múltiple', 12);

-- Volcando estructura para tabla reservas.room-generator
CREATE TABLE IF NOT EXISTS `room-generator` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Volcando datos para la tabla reservas.room-generator: ~1 rows (aproximadamente)
INSERT INTO `room-generator` (`next_val`) VALUES
	(13);

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
