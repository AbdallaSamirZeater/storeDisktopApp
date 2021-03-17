-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 19, 2019 at 12:31 AM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 5.6.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `stocks`
--

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `id` int(20) NOT NULL,
  `name` varchar(20) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`id`, `name`) VALUES
(1, 'التجاري');

-- --------------------------------------------------------

--
-- Table structure for table `clients`
--

CREATE TABLE `clients` (
  `ID` int(3) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Email` mediumtext NOT NULL,
  `Mobile` varchar(15) NOT NULL,
  `dept` float NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `clients`
--

INSERT INTO `clients` (`ID`, `Name`, `Email`, `Mobile`, `dept`, `date`) VALUES
(-1, 'Edit', '', '', 0, '0000-00-00'),
(0, 'manager', '', '', 0, '0000-00-00'),
(1, 'unknown', 'null', '01', 2035, '2019-05-30');

-- --------------------------------------------------------

--
-- Table structure for table `cons`
--

CREATE TABLE `cons` (
  `date` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `name` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `cons`
--

INSERT INTO `cons` (`date`, `id`, `name`, `quantity`) VALUES
(495, 1, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `day`
--

CREATE TABLE `day` (
  `start` tinyint(1) NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `day`
--

INSERT INTO `day` (`start`, `total`) VALUES
(1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `daytable`
--

CREATE TABLE `daytable` (
  `id` int(20) NOT NULL,
  `item` varchar(20) COLLATE utf8_bin NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `multipurchases`
--

CREATE TABLE `multipurchases` (
  `ID` bigint(20) NOT NULL,
  `Item` varchar(30) COLLATE utf8_bin NOT NULL,
  `Quant` int(11) NOT NULL,
  `Price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `multisales`
--

CREATE TABLE `multisales` (
  `ID` bigint(20) NOT NULL,
  `Item` varchar(30) COLLATE utf8_bin NOT NULL,
  `Quant` int(11) NOT NULL,
  `Price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `ID` int(3) NOT NULL,
  `Client_name` varchar(30) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Quantity` int(15) NOT NULL,
  `Price` float NOT NULL,
  `Total` float NOT NULL,
  `Paid` float NOT NULL,
  `Rest` float NOT NULL,
  `Date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `outgoings`
--

CREATE TABLE `outgoings` (
  `check_id` int(20) NOT NULL,
  `stock_name` varchar(30) CHARACTER SET utf8 NOT NULL,
  `client_id` int(3) NOT NULL,
  `total` double NOT NULL,
  `paid` double NOT NULL,
  `rest` double NOT NULL,
  `note` text CHARACTER SET utf8 NOT NULL,
  `inProfits` tinyint(1) NOT NULL,
  `date` date NOT NULL,
  `user` varchar(20) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `purchases`
--

CREATE TABLE `purchases` (
  `check_id` int(3) NOT NULL,
  `Total` float NOT NULL,
  `Paid` float NOT NULL,
  `Rest` float NOT NULL,
  `client_id` int(3) NOT NULL,
  `stock_id` bigint(20) NOT NULL,
  `quantity` int(20) NOT NULL,
  `date` date NOT NULL,
  `user` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `purchases`
--

INSERT INTO `purchases` (`check_id`, `Total`, `Paid`, `Rest`, `client_id`, `stock_id`, `quantity`, `date`, `user`) VALUES
(0, 1000, 1000, 0, 0, 1, 100, '2019-06-15', 'Hash'),
(1, 2000, 2000, 0, 0, 2, 100, '2019-06-15', 'Hash');

-- --------------------------------------------------------

--
-- Table structure for table `rebellions`
--

CREATE TABLE `rebellions` (
  `start` tinyint(1) NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `rebellions`
--

INSERT INTO `rebellions` (`start`, `total`) VALUES
(1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `rebelliontable`
--

CREATE TABLE `rebelliontable` (
  `id` int(11) NOT NULL,
  `item` varchar(20) COLLATE utf8_bin NOT NULL,
  `total` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `returns`
--

CREATE TABLE `returns` (
  `id` int(10) NOT NULL,
  `client` int(3) NOT NULL,
  `name` bigint(20) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` float NOT NULL,
  `total` float NOT NULL,
  `paid` float NOT NULL,
  `rest` float NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `sales`
--

CREATE TABLE `sales` (
  `check_id` int(3) NOT NULL,
  `Total` float NOT NULL,
  `Paid` float NOT NULL,
  `client_id` int(3) NOT NULL,
  `stock_id` bigint(20) NOT NULL,
  `quantity` int(20) NOT NULL,
  `Rest` float NOT NULL,
  `date` date NOT NULL,
  `user` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `sales`
--

INSERT INTO `sales` (`check_id`, `Total`, `Paid`, `client_id`, `stock_id`, `quantity`, `Rest`, `date`, `user`) VALUES
(0, 120, 120, 1, 1, 10, 0, '2019-06-15', 'Hash'),
(1, 260, 230, 1, 2, 5, 0, '2019-06-15', 'Hash'),
(2, 260, 230, 1, 2, 5, 0, '2019-06-15', 'Hash'),
(3, 120, 120, 1, 1, 10, 0, '2019-06-15', 'Hash'),
(4, 520, 520, 1, 2, 10, 0, '2019-06-15', 'Hash'),
(5, 120, 100, 1, 1, 10, 0, '2019-06-15', 'Hash'),
(6, 520, 200, 1, 2, 10, 0, '2019-06-15', 'Hash'),
(7, 52, 50, 1, 2, 1, 0, '2019-06-15', 'Hash'),
(8, 24, 20, 1, 1, 2, 0, '2019-06-18', 'Hash');

-- --------------------------------------------------------

--
-- Table structure for table `stock`
--

CREATE TABLE `stock` (
  `ID` bigint(20) NOT NULL,
  `Name` varchar(30) NOT NULL,
  `Quantity` int(10) NOT NULL,
  `Price` float NOT NULL,
  `cons` int(10) NOT NULL,
  `salesPrice` float NOT NULL,
  `branch` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `stock`
--

INSERT INTO `stock` (`ID`, `Name`, `Quantity`, `Price`, `cons`, `salesPrice`, `branch`) VALUES
(-1, 'multisales', 0, 0, 0, 0, 1),
(0, 'Miscellaneous sales', 0, 0, 0, 0, 1),
(1, 'apple', 68, 10, 10, 12, 1),
(2, 'orang', 69, 20, 10, 52, 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(3) NOT NULL,
  `User_name` varchar(30) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `logged` tinyint(1) DEFAULT NULL,
  `Type` varchar(10) NOT NULL,
  `1` tinyint(1) NOT NULL,
  `2` tinyint(1) NOT NULL,
  `3` tinyint(1) NOT NULL,
  `4` tinyint(1) NOT NULL,
  `5` tinyint(1) NOT NULL,
  `6` tinyint(1) NOT NULL,
  `7` tinyint(1) NOT NULL,
  `8` tinyint(1) NOT NULL,
  `9` tinyint(1) NOT NULL,
  `10` tinyint(1) NOT NULL,
  `branch` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `User_name`, `Password`, `logged`, `Type`, `1`, `2`, `3`, `4`, `5`, `6`, `7`, `8`, `9`, `10`, `branch`) VALUES
(1, 'user', '1234', 0, 'User', 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1),
(2, 'ahmed', '0', 0, 'User', 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `clients`
--
ALTER TABLE `clients`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `cons`
--
ALTER TABLE `cons`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `outgoings`
--
ALTER TABLE `outgoings`
  ADD PRIMARY KEY (`check_id`),
  ADD KEY `client_id` (`client_id`);

--
-- Indexes for table `purchases`
--
ALTER TABLE `purchases`
  ADD PRIMARY KEY (`check_id`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `stock_id` (`stock_id`) USING BTREE;

--
-- Indexes for table `returns`
--
ALTER TABLE `returns`
  ADD PRIMARY KEY (`id`),
  ADD KEY `clientId` (`client`),
  ADD KEY `name` (`name`);

--
-- Indexes for table `sales`
--
ALTER TABLE `sales`
  ADD PRIMARY KEY (`check_id`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `stock_id` (`stock_id`);

--
-- Indexes for table `stock`
--
ALTER TABLE `stock`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `branch` (`branch`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `User_name` (`User_name`),
  ADD KEY `branch` (`branch`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `branch`
--
ALTER TABLE `branch`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `outgoings`
--
ALTER TABLE `outgoings`
  MODIFY `check_id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(3) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `outgoings`
--
ALTER TABLE `outgoings`
  ADD CONSTRAINT `outgoings_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`ID`);

--
-- Constraints for table `purchases`
--
ALTER TABLE `purchases`
  ADD CONSTRAINT `purchases_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `clients` (`ID`),
  ADD CONSTRAINT `purchases_ibfk_2` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`ID`);

--
-- Constraints for table `returns`
--
ALTER TABLE `returns`
  ADD CONSTRAINT `returns_ibfk_1` FOREIGN KEY (`name`) REFERENCES `stock` (`ID`);

--
-- Constraints for table `sales`
--
ALTER TABLE `sales`
  ADD CONSTRAINT `sales_ibfk_1` FOREIGN KEY (`stock_id`) REFERENCES `stock` (`ID`);

--
-- Constraints for table `stock`
--
ALTER TABLE `stock`
  ADD CONSTRAINT `stock_ibfk_1` FOREIGN KEY (`branch`) REFERENCES `branch` (`id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`branch`) REFERENCES `branch` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
