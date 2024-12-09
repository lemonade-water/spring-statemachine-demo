CREATE TABLE `order` (
     `ID` varchar(20) COLLATE utf8mb4_bin NOT NULL,
     `STATUS` int(2) DEFAULT NULL,
     `USER` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
     PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `order_event` (
       `ID` varchar(20) COLLATE utf8mb4_bin NOT NULL,
       `ORDER_ID` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL,
       `OPERATE` int(1) DEFAULT NULL,
       PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;