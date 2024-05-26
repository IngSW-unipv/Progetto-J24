create database warehouse;

CREATE TABLE inventory (
    sku VARCHAR(25) PRIMARY KEY NOT NULL,
    description VARCHAR(50) NOT NULL,
    price double NOT NULL,
    qty INT NOT NULL,
	stdLevel INT NOT NULL,
    line VARCHAR(10) NOT NULL,
    pod VARCHAR(10) NOT NULL,
    bin VARCHAR(10) NOT NULL,
    fragility int NOT NULL,
    dimension int NOT NULL,
    category varchar(25) not null,
    CONSTRAINT unique_line_pod_bin UNIQUE (line, pod, bin)
);

create table supplier(
	ids varchar(25) PRIMARY KEY,
    fullname VARCHAR(25) NOT NULL,
    address VARCHAR(30) NOT NULL,
    email VARCHAR(50) NOT NULL
);

create table supply(
	idsupply varchar(25) primary key, 
    sku varchar(25) not null,
    ids varchar(25) not null,
    price double not null, 
    maxqty int not null,
    CONSTRAINT sku_ids UNIQUE (sku, ids)
);
alter table supply add foreign key (sku) references inventory (sku);
alter table supply add foreign key (ids) references  supplier (ids);

create table supplyOrders (
	ids_order int primary key,
    idsupply varchar(25) not null,
	qty int not null,
    price double not null,
    date datetime not null
);
alter table supplyOrders add foreign key (idsupply) references supply (idsupply);

-- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- 

CREATE TABLE `clients` (
  `name` varchar(20) DEFAULT NULL,
  `surname` varchar(20) DEFAULT NULL,
  `email` varchar(40) NOT NULL,
  `address` varchar(30) DEFAULT NULL,
  `password` varchar(18) DEFAULT NULL,
  `wallet_id`  INT DEFAULT NULL,
  primary key(`email`)
);

CREATE TABLE `clientorders` (
  `id` int not NULL,
  `sku` varchar(25) not NULL,
  `qty` int DEFAULT NULL,
  `email` varchar(40) NOT NULL,
  `Odate` varchar(30) DEFAULT NULL,
  primary key(`id`,`sku`,`email`),
  unique key `Odate` (`Odate`),
  key `sku` (`sku`),
  key `email` (`email`),
  constraint `clientorders_ibfk_1` foreign key(`sku`) references `inventory` (`sku`),
  constraint `clientorders_ibfk_2` foreign key(`email`) references `clients` (`email`)
);

CREATE TABLE `returnservice` (
  `OrderID` int DEFAULT NULL,
  `Item` varchar(255) DEFAULT NULL,
  `Reason` varchar(255) DEFAULT NULL,
  `MoneyAlreadyReturned` double DEFAULT NULL
);

CREATE TABLE `refundmode` (
  `OrderID` int DEFAULT NULL,
  `RefundMode` varchar(255) DEFAULT NULL,
  `Date` timestamp NULL DEFAULT NULL
);


